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

import org.allcolor.xml.parser.CStringBuilder;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CDocumentFragment
	extends CElement
	implements DocumentFragment,Serializable {
	static final long serialVersionUID = -7284765922664974682L;
	private final DOMImplementation impl;
	private final ADocument realOwner;
	/**
	 * DOCUMENT ME!
	 *
	 * @param ownerDocument
	 */
	public CDocumentFragment(final ADocument ownerDocument) {
		super(null);
		name = "#document-fragment";
		prefix = null;
		localName = null;
		nameSpace = "  ";
		isDom1 = true;
		this.impl = ownerDocument.getImplementation();
		this.realOwner = ownerDocument;
	} // end CDocumentFragment()

	public Document getOwnerDocument() {
		return realOwner;
	} // end getOwnerDocument()

	public NamedNodeMap getAttributes() {
		return null;
	}
	
	public DOMImplementation getImplementation() {
		return impl;
	} // end getImplementation()
	
	
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
		return Node.DOCUMENT_FRAGMENT_NODE;
	} // end getNodeType()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder result = new CStringBuilder();

		if (hasChildNodes()) {
			NodeList nl = getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				result.append(node.toString());
			} // end for
		} // end if

		return result.toString();
	} // end toString()
} // end CDocumentFragment
