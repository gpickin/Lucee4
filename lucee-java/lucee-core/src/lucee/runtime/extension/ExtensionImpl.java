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
package lucee.runtime.extension;

import lucee.commons.lang.StringUtil;
import lucee.runtime.PageContext;
import lucee.runtime.converter.ConverterException;
import lucee.runtime.converter.ScriptConverter;
import lucee.runtime.exp.PageException;
import lucee.runtime.type.Struct;
import lucee.runtime.type.StructImpl;
import lucee.runtime.type.dt.DateTime;

public class ExtensionImpl implements Extension {

	private String provider;
	private String id;
	private String strConfig;
	private Struct config;
	private String version;
	private String name;
	private String label;
	private String description;
	private String category;
	private String image;
	private String author;
	private String codename;
	private String video;
	private String support;
	private String documentation;
	private String forum;
	private String mailinglist;
	private String network;
	private DateTime created;
	private String type;
	
	
	public ExtensionImpl(String strConfig, String id, String provider, String version, 
			String name, String label, String description, String category, String image,
			String author,String codename,String video,String support,String documentation,
			String forum,String mailinglist,String network,DateTime created,String type) {
		this.strConfig=strConfig;
		this.id = id;
		this.provider = provider;
		this.version = version;
		this.name = name;
		this.label = label;
		this.description = description;
		this.category = category;
		this.image = image;

		this.author = author;
		this.codename = codename;
		this.video = video;
		this.support = support;
		this.documentation = documentation;
		this.forum = forum;
		this.mailinglist = mailinglist;
		this.network = network;
		this.created = created;

		if("server".equalsIgnoreCase(type))this.type="server";
		else if("all".equalsIgnoreCase(type))this.type="all";
		else this.type="web";
		
	}
	
	public ExtensionImpl(Struct config, String id, String provider, String version, 
			String name, String label, String description, String category, String image,
			String author,String codename,String video,String support,String documentation,
			String forum,String mailinglist,String network,DateTime created,String type) {
		if(config==null)	this.config=new StructImpl();
		else 				this.config = config;
		this.id = id;
		this.provider = provider;
		this.version = version;
		this.name = name;
		this.label = label;
		this.description = description;
		this.category = category;
		this.image = image;

		this.author = author;
		this.codename = codename;
		this.video = video;
		this.support = support;
		this.documentation = documentation;
		this.forum = forum;
		this.mailinglist = mailinglist;
		this.network = network;
		this.created = created;
		if("server".equalsIgnoreCase(type))this.type="server";
		else if("all".equalsIgnoreCase(type))this.type="all";
		else this.type="web";
	}
	
	
	
	public String getAuthor() {
		return author;
	}



	public String getCodename() {
		return codename;
	}



	public String getVideo() {
		return video;
	}



	public String getSupport() {
		return support;
	}



	public String getDocumentation() {
		return documentation;
	}



	public String getForum() {
		return forum;
	}



	public String getMailinglist() {
		return mailinglist;
	}



	public String getNetwork() {
		return network;
	}



	public DateTime getCreated() {
		return created;
	}



	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getImage() {
		return image;
	}

	public String getVersion() {
		return version;
	}

	public String getProvider() {
		return provider;
	}
	public String getId() {
		return id;
	}
	public Struct getConfig(PageContext pc) {
		if(config==null) {
			if(StringUtil.isEmpty(strConfig)) config= new StructImpl();
			else  {
				try {
					config= (Struct)pc.evaluate(strConfig);
				} catch (PageException e) {
					return new StructImpl();
				}
			}
		}
		return config;
	}
	
	public String getStrConfig()  {
		if(config!=null && strConfig==null) {
			try {
				strConfig=new ScriptConverter().serialize(config);
			} catch (ConverterException e) {
				return "";
			}
		}
		return strConfig;
	}

	public String getType() {
		return type;
	}
}
