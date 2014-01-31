/*
 * Copyright (C) 2005 by Quentin Anciaux
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Library General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Library General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *	@author Quentin Anciaux
 */
package org.allcolor.dtd.parser;
import org.allcolor.xml.parser.CStringBuilder;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CElement
	implements Serializable,
	Cloneable {
	public static final long serialVersionUID = -2638735856988827330L;
	
	/** DOCUMENT ME! */
	private CContentParticle cp = null;

	/** DOCUMENT ME! */
	private List validNodesList = new ArrayList(0);

	/** DOCUMENT ME! */
	private String name;

	/** DOCUMENT ME! */
	private String validNodes;

	/**
	 * Creates a new CElement object.
	 *
	 * @param name DOCUMENT ME!
	 * @param validNodes DOCUMENT ME!
	 */
	public CElement(
		final String name,
		final String validNodes) {
		this.name		    = name;
		this.validNodes     = validNodes;
	} // end CElement()

	public Object clone() throws CloneNotSupportedException {
		CElement elem = (CElement)super.clone();
		elem.mapAttributes = new HashMap(mapAttributes.size());
		elem.validNodesList = validNodesList;
		elem.validNodes = validNodes;
		elem.name = name;
		if (idAttr != null)
			elem.idAttr = (CAttr)idAttr.clone();
		else
			elem.idAttr = null;
		elem.cp = cp;
		for (Iterator it = mapAttributes.entrySet().iterator();it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			Object key = entry.getKey();
			CAttr attr = (CAttr)entry.getValue();
			attr = (CAttr)attr.clone();
			elem.mapAttributes.put(key,attr);
		}
		return elem;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CContentParticle getContentParticle() {
		if (cp == null) {
			analyzeValidNodes(validNodes);
		}
		return cp;
	} // end getContentParticle()

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	} // end getName()

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the validNodes.
	 */
	public List getValidNodes() {
		if (cp == null) {
			analyzeValidNodes(validNodes);
		}
		return validNodesList;
	} // end getValidNodes()

	public static final int CDATA = 1;
	public static final int LOV = 2;
	public static final int ID = 3;
	public static final int IDREF = 4;
	public static final int IDREFS = 5;
	public static final int NMTOKEN = 6;
	public static final int NMTOKENS = 7;
	public static final int ENTITY = 8;
	public static final int ENTITIES = 9;
	public static final int NOTATION = 10;
	public static final int XML = 11;
	
	public static final class CAttr
	implements Serializable,
		Cloneable {
		static final long serialVersionUID = -5234845929118823056L;
		public final int type;
		public final String name;
		public final String defaultValue;
		public final Pattern LOV;
		public final boolean isRequired;
		public CAttr(String name,int type,String defaultValue,String lov) {
			this.name = name;
			this.type = type;
			if ("#IMPLIED".equals(defaultValue)) {
				this.isRequired = false;
				this.defaultValue = null;
			} else if ("#REQUIRED".equals(defaultValue)){
				this.isRequired = true;
				this.defaultValue = null;
			} else if (defaultValue != null){
				this.isRequired = false;
				if (defaultValue.startsWith("#FIXED")) {
					defaultValue = defaultValue.substring("#FIXED".length()+1).trim();
					int index = defaultValue.indexOf("\"");
					if (index == -1 || (defaultValue.indexOf("'") < index && defaultValue.indexOf("'") > -1)) {
						defaultValue = defaultValue.substring(0,index);
					}
					this.defaultValue = defaultValue;
				} else {
					this.defaultValue = defaultValue;
				}
			} else {
				this.isRequired = false;
				this.defaultValue = null;
			}
			if (lov != null) {
				this.LOV = Pattern.compile(lov);
			} else {
				this.LOV = null;
			}
		}
		
		public Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}
	}
	
	private Map mapAttributes = new HashMap(0);
	private CAttr idAttr = new CAttr("id",ID,null,null);
	
	public void addAttribute(String name,int type,String defaultValue,String lov) {
		CAttr attr = new CAttr(name,type,defaultValue,lov);
		if (attr.type == ID) {
			idAttr = attr;
		}
		mapAttributes.put(name,attr);
	}
	
	public CAttr getIDAttr() {
		return idAttr;
	}
	
	public Map getAttributes() {
		return mapAttributes;
	}
	
	public CAttr getAttr(String name) {
		return (CAttr)mapAttributes.get(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder result = new CStringBuilder();
		result.append("<!ELEMENT ");
		result.append(name);
		result.append(" ");
		result.append(validNodes);
		result.append(">\n");

		return result.toString();
	} // end toString()

	/**
	 * DOCUMENT ME!
	 *
	 * @param validNodes DOCUMENT ME!
	 */
	private void analyzeValidNodes(final String validNodes) {
		if (validNodes == null) {
			return;
		}

		if ("EMPTY".equals(validNodes.trim())) {
			return;
		}

		cp = new CContentParticle(validNodes, null);
		fillList(cp);
	} // end analyzeValidNodes()

	/**
	 * DOCUMENT ME!
	 *
	 * @param cp DOCUMENT ME!
	 */
	private void fillList(final CContentParticle cp) {
		if (cp.getType() == CContentParticle.NAME) {
			String name = cp.getParticleAsString();

			if (!validNodesList.contains(name)) {
				validNodesList.add(name);
			} // end if
		} // end if

		for (Iterator it = cp.getChildParticles().iterator();
				it.hasNext();) {
			CContentParticle child = (CContentParticle) it.next();
			fillList(child);
		} // end for
	} // end fillList()
} // end CElement
