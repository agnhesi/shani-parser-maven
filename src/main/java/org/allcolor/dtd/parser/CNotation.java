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
import org.allcolor.xml.parser.dom.ANode;
import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CNotation
	extends ANode
	implements Notation {
	/** DOCUMENT ME! */
	public final static long serialVersionUID = 6485102428098355595L;

	/** DOCUMENT ME! */
	private CElement element = null;

	/** DOCUMENT ME! */
	private String in = null;

	/** DOCUMENT ME! */
	private String publicId = null;

	/** DOCUMENT ME! */
	private String systemId = null;

	public NamedNodeMap getAttributes() {
		return null;
	}
	
	/**
	 * Creates a new CNotation object.
	 *
	 * @param element DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CNotation(
		final CElement element,
		final ADocument ownerDocument) {
		super(element.getName(), ownerDocument);
		this.element = element;
		isReadOnly = true;
	} // end CNotation()

	public Object clone() throws CloneNotSupportedException {
		CNotation not = (CNotation) super.clone();
		if (element != null)
			not.element = (CElement)not.element.clone();
		else
			not.element = null;
		return not;
	}
	
	/**
	 * Creates a new CNotation object.
	 *
	 * @param in DOCUMENT ME!
	 * @param name DOCUMENT ME!
	 * @param publicId DOCUMENT ME!
	 * @param systemId DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CNotation(
		final String in,
		final String name,
		final String publicId,
		final String systemId,
		final ADocument ownerDocument) {
		super(name, ownerDocument);
		this.publicId     = publicId;
		this.systemId     = systemId;
		this.in			  = in;
		isReadOnly = true;
	} // end CNotation()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CElement getElement() {
		return element;
	} // end getElement()

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
		return Node.NOTATION_NODE;
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
		throws DOMException {}

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
		return (element == null)
		? null
		: element.getContentParticle().toString();
	} // end getNodeValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Notation#getPublicId()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPublicId() {
		return publicId;
	} // end getPublicId()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Notation#getSystemId()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getSystemId() {
		return systemId;
	} // end getSystemId()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		if (in != null) {
			return "<" + in + ">\n";
		}

		return ((element != null)
		? element.toString()
		: ("<!NOTATION " + getNodeName() + " SYSTEM \"" +
		getSystemId() + "\" >"));
	} // end toString()
} // end CNotation
