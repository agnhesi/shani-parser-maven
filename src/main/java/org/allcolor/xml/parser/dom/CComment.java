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

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CComment
	extends ANode
	implements Comment,Serializable {
	static final long serialVersionUID = -7602861300357219969L;
	/** DOCUMENT ME! */
	private String data;

	/**
	 * Creates a new CComment object.
	 *
	 * @param data DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CComment(
		String data,
		final ADocument ownerDocument) {
		super(ownerDocument);
		name = "#comment";
		prefix = null;
		localName = null;
		nameSpace = "  ";
		isDom1 = true;

		if (data == null) {
			data = "";
		}

		this.data = data;
	} // end CComment()

	public String getBaseURI() {
		return null;
	}
	
	public String getTextContent() throws DOMException {
		return getData();
	}
	
	public NamedNodeMap getAttributes() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#setData(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param data DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setData(final String data)
		throws DOMException {
		this.data = data;
	} // end setData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#getData()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String getData()
		throws DOMException {
		return data;
	} // end getData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		return data.length();
	} // end getLength()

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
		return Node.COMMENT_NODE;
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
		return getData();
	} // end getNodeValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#appendData(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param arg DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void appendData(String arg)
		throws DOMException {
		if (arg == null) {
			arg = "";
		}

		data = data + arg;
	} // end appendData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#deleteData(int, int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param offset DOCUMENT ME!
	 * @param count DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void deleteData(
		final int offset,
		final int count)
		throws DOMException {
		data = data.substring(0, offset) +
			data.substring(offset + count);
	} // end deleteData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#insertData(int, java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param offset DOCUMENT ME!
	 * @param arg DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void insertData(
		final int offset,
		final String arg)
		throws DOMException {
		data = data.substring(0, offset) + data.substring(offset);
	} // end insertData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#replaceData(int, int, java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param offset DOCUMENT ME!
	 * @param count DOCUMENT ME!
	 * @param arg DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void replaceData(
		final int offset,
		final int count,
		final String arg)
		throws DOMException {
		data = data.substring(0, offset) + arg +
			data.substring(offset + count);
	} // end replaceData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.CharacterData#substringData(int, int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param offset DOCUMENT ME!
	 * @param count DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String substringData(
		final int offset,
		final int count)
		throws DOMException {
		return data.substring(offset, offset + count);
	} // end substringData()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder result = new CStringBuilder();
		result.append("<!--");
		result.append(getData());
		result.append("-->");

		return result.toString();
	} // end toString()
} // end CComment
