/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.functions.cache;

import java.util.ArrayList;

import lucee.commons.lang.StringUtil;
import lucee.runtime.PageContext;
import lucee.runtime.cache.CacheConnection;
import lucee.runtime.config.ConfigImpl;
import lucee.runtime.exp.PageException;
import lucee.runtime.exp.SecurityException;
import lucee.runtime.op.Caster;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.KeyImpl;
import lucee.runtime.type.Struct;
import lucee.runtime.type.util.ListUtil;
import lucee.commons.io.cache.exp.CacheException;

public class CacheSetProperties {

	private static final Key OBJECT_TYPE = KeyImpl.intern("objecttype");

	public static Object call(PageContext pc,Struct properties) throws PageException {
		try {
			Object obj=properties.removeEL(OBJECT_TYPE); 
			String objectType=Caster.toString(obj);
			
			CacheConnection[] conns=getCaches(pc,objectType);
			for(int i=0;i<conns.length;i++){
				setProperties(conns[i],properties);
			}
		} catch (CacheException e) {
			throw Caster.toPageException(e);
		}
		
		
		return call(pc, null);
	}

	private static void setProperties(CacheConnection cc, Struct properties) throws SecurityException {
		throw new SecurityException("it is not allowed to change cache connection setting this way, please use the tag cfadmin or the lucee administrator frontend instead ");
	}

	private static CacheConnection[] getCaches(PageContext pc,String cacheName) throws CacheException {
		ConfigImpl config=(ConfigImpl) pc.getConfig();
		if(StringUtil.isEmpty(cacheName)){
			
			return new CacheConnection[]{
					config.getCacheDefaultConnection(ConfigImpl.CACHE_DEFAULT_OBJECT),
					config.getCacheDefaultConnection(ConfigImpl.CACHE_DEFAULT_TEMPLATE)
			}
			;
			// MUST which one is first
		}
		
		ArrayList<CacheConnection> list=new ArrayList<CacheConnection>();
		String name;
		String[] names=ListUtil.listToStringArray(cacheName, ',');
		for(int i=0;i<names.length;i++){
			name=names[i].trim().toLowerCase();
			if(name.equalsIgnoreCase("template"))
				list.add(config.getCacheDefaultConnection(ConfigImpl.CACHE_DEFAULT_TEMPLATE));
			else if(name.equalsIgnoreCase("object"))
				list.add(config.getCacheDefaultConnection(ConfigImpl.CACHE_DEFAULT_OBJECT));
			else{
				CacheConnection cc= config.getCacheConnections().get(name);
				if(cc==null) throw new CacheException("there is no cache defined with name ["+name+"]");
				list.add(cc);
			}
		}
		return list.toArray(new CacheConnection[list.size()]);
	}
}
