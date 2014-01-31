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

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CText
	extends ANode
	implements Text,
		INode,Serializable {
	static final long serialVersionUID = 6910381187592428499L;

	/** DOCUMENT ME! */
	private String data;

	/** DOCUMENT ME! */
	private String nodeValue = null;

	/** DOCUMENT ME! */
	private boolean entityEncoded = true;

	/**
	 * Creates a new CText object.
	 *
	 * @param data DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CText(
		final String data,
		final ADocument ownerDocument) {
		this(data, ownerDocument, true);
	} // end CText()

	public String getBaseURI() {
		return null;
	}
	
	/**
	 * Creates a new CText object.
	 *
	 * @param data DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 * @param entityEncoded DOCUMENT ME!
	 */
	public CText(
		String data,
		final ADocument ownerDocument,
		final boolean entityEncoded) {
		super(ownerDocument);
		name = "#text";
		prefix = null;
		localName = null;
		nameSpace = "  ";
		isDom1 = true;

		if (data == null) {
			data = "";
		}
		this.data			   = data;
		this.entityEncoded     = entityEncoded;
	} // end CText()

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
	public void setData(String data)
		throws DOMException {
		isReadOnly();
		if (data == null) {
			data = "";
		}

		this.data		   = CEntityCoDec.encode(data);
		this.nodeValue     = ownerDocument.getEntityCodec().decode(this.data);
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
		if ((nodeValue == null) &&
				(parentNode != null) &&
				(parentNode.getNodeType() != Node.ENTITY_REFERENCE_NODE)) {
			if (data.indexOf('&') == -1) {
				nodeValue = data;
			} else
			if (ownerDocument != null) {
				nodeValue = ownerDocument.getEntityCodec().decode(data);
			} else {
				nodeValue = data;
			}
		} // end if
		else {
			if (data != null && data.startsWith("&") && data.endsWith(";")) {
				nodeValue = ownerDocument.getEntityCodec().decode(data);
			} else {
				nodeValue = data;
			}
		} // end else

		return nodeValue;
	} // end getData()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Text#isElementContentWhitespace()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isElementContentWhitespace() {
		return isElementContentWhitespace(this);
	} // end isElementContentWhitespace()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Text#isElementContentWhitespace()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param node DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static boolean isElementContentWhitespace(final Text node) {
		if (node.getParentNode() == null) return false;
		return node.getData().trim().length() == 0;
	} // end isElementContentWhitespace()

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
		return getData().length();
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
		return Node.TEXT_NODE;
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

	public void setTextContent(final String textContent) {
		setData(textContent);
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
		return getData();
	} // end getNodeValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Text#getWholeText()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getWholeText() {
		StringBuffer buffer = new StringBuffer();
		Node n = this;
		while (n != null) {
			Node bn = n;
			n = n.getPreviousSibling();
			if (n == null) {
				n = bn;
				break;
			}
		}
		while (n != null) {
			if (n.getNodeType() == Node.TEXT_NODE) {
				buffer.append(n.getNodeValue());
			} else
			if (n.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				buffer.append(n.toString());
			} 
			n = n.getNextSibling();
		}
		
		return buffer.toString();
	} // end getWholeText()

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
		isReadOnly();
		if (arg == null) {
			arg = "";
		}

		String tmp = getData();
		
		this.data     = tmp + arg;
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
		int offset,
		int count)
		throws DOMException {
		isReadOnly();
		try {
			if (count < 0) {
				throw new Exception("Count must be positive !");
			}
			String tmp = getData();
			if (offset+count > tmp.length())
				count = tmp.length()-offset;
			tmp		 = tmp.substring(0, offset) +
				tmp.substring(offset + count);
			tmp		 = CEntityCoDec.encode(tmp);
			data     = tmp;
		}
		catch (Exception e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,e.getMessage());
		}
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
		int offset,
		String arg)
		throws DOMException {
		isReadOnly();
		try {
			if (arg == null) {
				arg = "";
			}
			String tmp = getData();
			tmp		 = tmp.substring(0, offset) + arg +
				tmp.substring(offset);

			this.data     = tmp;
		}
		catch (Exception e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,e.getMessage());
		}
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
		int offset,
		int count,
		String arg)
		throws DOMException {
		isReadOnly();
		try {
			if (count < 0) {
				throw new Exception("Count must be positive !");
			}
			if (arg == null) {
				arg = "";
			}
			String tmp = getData();
			if (offset+count > tmp.length())
				count = tmp.length()-offset;
			tmp		 = tmp.substring(0, offset) + arg +
				tmp.substring(offset + count);

			this.data     = tmp;
		}
		catch (Exception e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,e.getMessage());
		}
	} // end replaceData() 
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Text#replaceWholeText(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Text replaceWholeText(final String content)
		throws DOMException {
		Node n = this;
		while (n != null) {
			Node bn = n;
			n = n.getPreviousSibling();
			if (n == null || 
			   (n.getNodeType() != Node.TEXT_NODE && n.getNodeType() != Node.CDATA_SECTION_NODE)) {
				n = bn;
				break;
			}
		}
		Node first = n;
		StringBuffer buffer = new StringBuffer();
		while (n != null) {
			if (n.getNodeType() == Node.TEXT_NODE ||
				n.getNodeType() == Node.CDATA_SECTION_NODE) {
				((ANode)n).isReadOnly();
				buffer.append(n.getNodeValue());
			} else if (n.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				NodeList nl = n.getChildNodes();
				StringBuffer buf = new StringBuffer();
				boolean notlink = false;
				for (int i=0;i<nl.getLength();i++) {
					short type = nl.item(i).getNodeType();
					if (type != Node.ENTITY_REFERENCE_NODE &&
						type != Node.CDATA_SECTION_NODE &&
						type != Node.TEXT_NODE) {
						if (buf.toString().trim().length() == 0) {
							notlink = true;
							break;
						}
						((ANode)n).isReadOnly();
					} else {
						buf.append(nl.item(i).getNodeValue().trim());
					}
				}
				if (!notlink)
					((ANode)n).parentNode.removeChild(n);
			} else {
				break;
			}
			n = n.getNextSibling();
		}
		n = first;
		if ("".equals(content)) {
			while (n != null) {
				Node bn = n.getNextSibling();
				if (n.getNodeType() == Node.TEXT_NODE ||
					n.getNodeType() == Node.CDATA_SECTION_NODE) {
					if (n.getParentNode() != null)
						n.getParentNode().removeChild(n);
				} else {
					break;
				}
				n = bn;
			}
			 return null;
		}
		Text t = this;
		if (this != first && parentNode != null) {
			t = (Text)first;
		}
		t.setData(content);
		n = first.getNextSibling();
		while (n != null) {
			Node bn = n.getNextSibling();
			if (n.getNodeType() == Node.TEXT_NODE ||
				n.getNodeType() == Node.CDATA_SECTION_NODE) {
				if (n.getParentNode() != null)
					n.getParentNode().removeChild(n);
			} else {
				break;
			}
			n = bn;
		}
		return t;
	} // end replaceWholeText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Text#splitText(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param offset DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Text splitText(int offset)
		throws DOMException {
		isReadOnly();
		try {
			String tmp = getData();
			setData(tmp.substring(0, offset));
	
			Text tNode = new CText(tmp.substring(offset),
					ownerDocument);
	
			if (this.getNextSibling() != null) {
				this.getParentNode()
						.insertBefore(tNode, this.getNextSibling());
			} else {
				this.getParentNode().appendChild(tNode);
			}
	
			return tNode;
		}
		catch (Exception e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,e.getMessage());
		}
	} // end splitText()

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
		int offset,
		int count)
		throws DOMException {
		isReadOnly();
		try {
			if (count < 0) {
				throw new Exception("Count must be positive !");
			}
			String tmp = getData();
			if (offset+count > tmp.length())
				count = tmp.length()-offset;
			return tmp.substring(offset, offset + count);
		}
		catch (Exception e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,e.getMessage());
		}
	} // end substringData()

	public NamedNodeMap getAttributes() {
		return null;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		if (entityEncoded) {
			String tmp = getData();
			tmp = CEntityCoDec.encode(tmp);
			return tmp;
		} // end if

		return getData();
	} // end toString()
} // end CText
