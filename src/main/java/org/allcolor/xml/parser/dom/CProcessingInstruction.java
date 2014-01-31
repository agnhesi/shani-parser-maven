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

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CProcessingInstruction
	extends ANode
	implements ProcessingInstruction,Serializable {
	static final long serialVersionUID = -6129542443174695786L;
	/** DOCUMENT ME! */
	private String data;

	/**
	 * Creates a new CProcessingInstruction object.
	 *
	 * @param name DOCUMENT ME!
	 * @param data DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CProcessingInstruction(
		final String name,
		final String data,
		final ADocument ownerDocument) {
		super(ownerDocument);
		this.name = name.intern();
		prefix = null;
		localName = null;
		nameSpace = "  ";
		isDom1 = true;
		this.data = data;
		if (this.data != null) {
			while (this.data.startsWith(" ") || this.data.startsWith("\t"))
				this.data = this.data.substring(1);
		}
	} // end CProcessingInstruction()

	public NamedNodeMap getAttributes() {
		return null;
	}
	
	public String getTextContent() throws DOMException {
		return getData();
	}
	
	public void setTextContent(String textContent) throws DOMException {
		setData(textContent);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.ProcessingInstruction#setData(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param data DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setData(String data)
		throws DOMException {
		isReadOnly();
		CStringBuilder result = new CStringBuilder();

		while (data.indexOf("?>",0) != -1) {
			result.append(data.substring(0, data.indexOf("?>",0)));
			data = data.substring(data.indexOf("?>",0) + 2);
		} // end while

		result.append(data);
		this.data = result.toString();
	} // end setData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.ProcessingInstruction#getData()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getData() {
		return data;
	} // end getData()

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
		return Node.PROCESSING_INSTRUCTION_NODE;
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
		setData(nodeValue);
	} // end setNodeValue()

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
		return data;
	} // end getNodeValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.ProcessingInstruction#getTarget()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getTarget() {
		return name;
	} // end getTarget()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		if (name.equals("xml")) {
			return "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
		} // end if

		CStringBuilder result = new CStringBuilder();
		result.append("<?");
		result.append(name);
		result.append(" ");
		result.append(data.trim());
		result.append(" ?>");

		return result.toString();
	} // end toString()
} // end CProcessingInstruction
