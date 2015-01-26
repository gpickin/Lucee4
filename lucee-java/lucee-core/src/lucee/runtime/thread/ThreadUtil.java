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
package lucee.runtime.thread;

import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lucee.commons.io.DevNullOutputStream;
import lucee.commons.lang.Pair;
import lucee.runtime.CFMLFactory;
import lucee.runtime.CFMLFactoryImpl;
import lucee.runtime.PageContext;
import lucee.runtime.PageContextImpl;
import lucee.runtime.config.Config;
import lucee.runtime.config.ConfigImpl;
import lucee.runtime.config.ConfigWeb;
import lucee.runtime.net.http.HTTPServletRequestWrap;
import lucee.runtime.net.http.HttpServletRequestDummy;
import lucee.runtime.net.http.HttpServletResponseDummy;
import lucee.runtime.type.Struct;

public class ThreadUtil {
	

	public static  PageContextImpl clonePageContext(PageContext pc, OutputStream os, boolean stateless,boolean registerPC,boolean isChild)  {
		// TODO stateless
		CFMLFactoryImpl factory = (CFMLFactoryImpl) ((ConfigImpl)pc.getConfig()).getFactory();
        HttpServletRequest	req=new HTTPServletRequestWrap(cloneHttpServletRequest(pc));
        HttpServletResponse	rsp=createHttpServletResponse(os);
        
        // copy state
        PageContextImpl pci = (PageContextImpl) pc;
		PageContextImpl dest = factory.getPageContextImpl(factory.getServlet(), req, rsp, null, false, -1, false,registerPC, isChild);
		pci.copyStateTo(dest);
		return dest;
	}

	public static  PageContextImpl createPageContext(ConfigWeb config,OutputStream os,String serverName,String requestURI,String queryString,Cookie[] cookies,Pair[] headers,Pair[] parameters,Struct attributes)  {
		CFMLFactory factory = config.getFactory();
        HttpServletRequest	req = new HttpServletRequestDummy(
				config.getRootDirectory(),
				serverName,
				requestURI,
				queryString,
				cookies,
				headers,
				parameters,
				attributes,
				null
			);
		
		
		req=new HTTPServletRequestWrap(req);
        HttpServletResponse	rsp=createHttpServletResponse(os);
        
        return (PageContextImpl) factory.getLuceePageContext(
        		factory.getServlet(), 
        		req, rsp, null, false, -1, false);
		
	}
	

	public static HttpServletRequest cloneHttpServletRequest(PageContext pc) {
		Config config = pc.getConfig();
		HttpServletRequest req = pc.getHttpServletRequest();
		HttpServletRequestDummy dest = HttpServletRequestDummy.clone(config,config.getRootDirectory(),req);
		return dest;
	}
	
	public static HttpServletResponse createHttpServletResponse(OutputStream os) {
		if(os==null) os = DevNullOutputStream.DEV_NULL_OUTPUT_STREAM;
		
		HttpServletResponseDummy dest = new HttpServletResponseDummy(os);
		return dest;
	}

	/**
	 * return priority as a String representation
	 * @param priority Thread priority
	 * @return String defintion of priority (null when input is invalid)
	 */
	public static String toStringPriority(int priority) {
		if(priority==Thread.NORM_PRIORITY) return "NORMAL";
		if(priority==Thread.MAX_PRIORITY) return "HIGH";
		if(priority==Thread.MIN_PRIORITY) return "LOW";
		return null;
	}
	
	/** 
	 * return priority as a int representation
	 * @param priority Thread priority as String definition
	 * @return int defintion of priority (-1 when input is invalid)
	 */
	public static int toIntPriority(String strPriority) {
		strPriority=strPriority.trim().toLowerCase();
		
		if("low".equals(strPriority))		return Thread.MIN_PRIORITY;
		if("min".equals(strPriority))		return Thread.MIN_PRIORITY;
		if("high".equals(strPriority))		return Thread.MAX_PRIORITY;
		if("max".equals(strPriority))		return Thread.MAX_PRIORITY;
		if("normal".equals(strPriority))	return Thread.NORM_PRIORITY;
		if("norm".equals(strPriority))		return Thread.NORM_PRIORITY;
		return -1;
	}
}
