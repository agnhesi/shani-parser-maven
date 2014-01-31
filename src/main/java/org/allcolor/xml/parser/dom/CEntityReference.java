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
package org.allcolor.xml.parser.dom;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import org.allcolor.dtd.parser.CDTDParser;
import org.allcolor.dtd.parser.CDocType;
import org.allcolor.dtd.parser.CEntity;
import org.allcolor.xml.parser.CShaniDomParser;
import org.allcolor.xml.parser.CXmlParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CEntityReference
	extends CElement
	implements EntityReference,Serializable {
	static final long serialVersionUID = -2303692061358475148L;
	/** DOCUMENT ME! */
	private String representation;

	public CEntityReference(
			final String name,
			final String value,
			final ADocument ownerDocument) {
		super(name, ownerDocument);
		prefix = null;
		localName = null;
		nameSpace = "  ";
		isDom1 = true;
		if (ownerDocument != null) {
			CDocType dt = (CDocType)ownerDocument.doctype;
			CEntity ent = dt != null ? (CEntity)dt.getEntities().getNamedItem(name) : null;
			if (ent == null) {
				ent = (CEntity)CDTDParser.getInternalEntities().get(name);
			}
			if (ent == null) {
				ent = (CEntity)CXmlParser.dtTr.getEntities().getNamedItem(name);
			}
			if (ent != null) {
				Document val = ent.getDOMValue();
				if (val != null) {
					NodeList child = val.getChildNodes();
					for (int i=0;i<child.getLength();i++) {
						appendChild(ownerDocument.importNode(child.item(i),true));
					}
				} else {
					CShaniDomParser parser = new CShaniDomParser();
					Document docValue = null;
					if (ent.getValue().equals("&"+ent.getNodeName()+";")) {
						representation     = "&" + name + ";";
						return;
					} else
					if ( ent.getValue().startsWith("http://") ||
							 ent.getValue().startsWith("https://") ||
							 ent.getValue().startsWith("ftp://")) {
						docValue = parser.parse(ent.getValue());
					} else {
						docValue = parser.parse(new StringReader(
								 ent.getValue()));
					}
					NodeList nl = docValue.getChildNodes();
					for (int i=0;i<nl.getLength();i++) {
						appendChild(ownerDocument.importNode(nl.item(i),true));
					}
				}
				isReadOnly = true;
			} else if (name.startsWith("#x")) {
				char val = ((char) Integer.parseInt(name.substring(
						2), 16));
				appendChild(ownerDocument.createTextNode(""+val));
				isReadOnly = true;
			}	else if (name.startsWith("#")) {
				char val = ((char) Integer.parseInt(name.substring(
						1)));
				appendChild(ownerDocument.createTextNode(""+val));
				isReadOnly = true;
			}
		}
		representation     = "&" + name + ";";
	} // end CEntityReference()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Node#getNodeType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getNodeType() {
		return Node.ENTITY_REFERENCE_NODE;
	} // end getNodeType()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Node#setNodeValue(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param nodeValue DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setNodeValue(final String nodeValue)
		throws DOMException {
		NodeList nl = getChildNodes();
		ArrayList l = new ArrayList();
		for (int i=0;i<nl.getLength();i++) {
			l.add(nl.item(i));
		}
		isReadOnly = false;
		for (int i=0;i<l.size();i++) {
			removeChild((Node)l.get(i));
		}
		if (ownerDocument != null) {
			CShaniDomParser parser = new CShaniDomParser();
			Document docValue = null;
			if (nodeValue.startsWith("http://") ||
					nodeValue.startsWith("https://") ||
					nodeValue.startsWith("ftp://")) {
				docValue = parser.parse(nodeValue);
			} else {
				docValue = parser.parse(new StringReader(
						nodeValue));
			}
			nl = docValue.getChildNodes();
			for (int i=0;i<nl.getLength();i++) {
				appendChild(ownerDocument.importNode(nl.item(i).cloneNode(true),true));
			}
		}
		isReadOnly = true;
	} // end setNodeValue()

	public NamedNodeMap getAttributes() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Node#getNodeValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String getNodeValue()
		throws DOMException {
		return null;
	} // end getNodeValue()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		NodeList nl = getChildNodes();

		if (nl.getLength() > 0) {
			StringBuffer result = new StringBuffer();

			for (int i = 0; i < nl.getLength(); i++) {
				result.append(nl.item(i).toString());
			} // end for
			return result.toString();
		} // end if
		else {

			return representation;
		}
	} // end toString()
} // end CEntityReference
