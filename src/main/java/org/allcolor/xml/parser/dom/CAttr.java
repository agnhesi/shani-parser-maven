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
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CAttr
	extends CElement
	implements Attr,Serializable {

	static final long serialVersionUID = 2319615502224750876L;

	private boolean specified = false;
	
	private boolean defaults = false;
	
	private boolean ignore = false;
	
	public boolean needToDecode = true;

	public String deferredValue = null;
	
	public String cachedValue = null;
	
	private boolean inud = false;
	
	private void updateDeferred() {
		if (deferredValue == null) return;
		if (ownerDocument == null) return;
		if (inud) {
			deferredValue = null;
			return;
		}
		inud = true;
		String value = deferredValue;
		deferredValue = null;
		if (!needToDecode) {
			needToDecode = true;
			appendChild(new CText(value,ownerDocument,true));
			cachedValue = value;
		} else {
			cachedValue = null;
			ignore = true;
			contentHandler h = new contentHandler(this, ownerDocument);
			entityHandler  e = new entityHandler(h);
			ownerDocument.getEntityCodec()
												  .decodeInternal(value, h,
						null, e, true);
			ignore = false;
		}
		inud = false;
	}
	
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		cachedValue = null;
		return super.insertBefore(newChild, refChild);
	};
	
	public Node removeChild(Node oldChild) throws DOMException {
		cachedValue = null;
		return super.removeChild(oldChild);
	}
	
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		cachedValue = null;
		return super.replaceChild(newChild, oldChild);
	}
	
	/**
	 * Creates a new CAttr object.
	 *
	 * @param name DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CAttr(
		final String name,
		final String value,
		final ADocument ownerDocument,
		final CElement parentNode,
		final boolean specified) {
		super(name, ownerDocument);
		if (ownerDocument != null && !ownerDocument.isBuildStage)
			ownerDocument.checkNameValidXML(name);
		this.specified = specified;
		this.deferredValue = value == null ? "" : value;
		this.parentNode = parentNode;
		if (prefix == null) {
			this.nameSpace = "";
		}
		if (this.name == "xmlns" || this.prefix == "xmlns") {
			this.nameSpace = "http://www.w3.org/2000/xmlns/";
			if (parentNode != null) {
				parentNode.notifyNSChange(this.localName);
			}
		}
	} // end CAttr()
	
	public CAttr(
			final String name,
			String value,
			final ADocument ownerDocument,
			final CElement parentNode,
			final boolean specified,
			final String nameSpace) {
		this (name,value,ownerDocument,parentNode,specified);
		this.nameSpace = nameSpace;
		if (this.nameSpace == null)
			this.nameSpace = "";
	}
	public Node getParentNode() {
		return null;
	}
	
	public NamedNodeMap getAttributes() {
		return null;
	}
	
	// FIXME TODO
	public void normalizeValue() {
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#isId()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isId() {
		return parentNode == null ? false : 
			(parentNode.idAttribute != null ?
			parentNode.idAttribute.equals(name)
			: false);
	} // end isId()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#getName()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getName() {
		return name;
	} // end getName()

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
		return Node.ATTRIBUTE_NODE;
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
	public void setNodeValue(String nodeValue)
		throws DOMException {
		isReadOnly();
		needToDecode = true;
		cachedValue = null;
		if (nodeValue == null) {
			nodeValue = "";
		}
		NodeList nl = getChildNodes();
		List toRemove = new ArrayList(nl.getLength());
		for (int i=0;i<nl.getLength();i++)
			toRemove.add(nl.item(i));
		for (int i=0;i<toRemove.size();i++)
			removeChild((Node)toRemove.get(i));
		nodeValue = CEntityCoDec.encode(nodeValue, true);
		contentHandler h = new contentHandler(this, ownerDocument);
		entityHandler  e = new entityHandler(h);
		ownerDocument.getEntityCodec()
											  .decodeInternal(nodeValue, h,
					null, e, true);
	} // end setNodeValue()

	
	public Node getFirstChild() {
		updateDeferred();
		return super.getFirstChild();
	}

	public Node getLastChild() {
		updateDeferred();
		return super.getLastChild();
	}

	public NodeList getChildNodes() {
		updateDeferred();
		return super.getChildNodes();
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
		updateDeferred();
		if (cachedValue != null) {
			return cachedValue;
		}
		NodeList nl = getChildNodes();
		StringBuffer buffer = new StringBuffer();
		for (int i=0;i<nl.getLength();i++) {
			buffer.append(nl.item(i).toString());
		}
		if (ownerDocument == null) {
			return cachedValue = buffer.toString();
		} else {
			return cachedValue = ownerDocument.getEntityCodec().decode(buffer.toString());
		}
	} // end getNodeValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#getOwnerElement()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Element getOwnerElement() {
		return parentNode;
	} // end getOwnerElement()

	public final void isReadOnly() throws DOMException {
		if (ignore) return;
		ANode current = this;
		while (current != null) {
			if (current.isReadOnly)
				throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,"The node is read-only.");
			current = current.parentNode;
		}
	}
	
	private CTypeInfo info = null;
	
	public Object clone() throws CloneNotSupportedException {
		CAttr attr = (CAttr)super.clone();
		attr.info = null;
		return attr;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#getSchemaTypeInfo()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public TypeInfo getSchemaTypeInfo() {
		if (info != null) return info;
		// FIXME TODO
		if (ownerDocument.hasXSD())
			info = new CTypeInfo(this,true);
		else
			info = new CTypeInfo(this,false);
		return info;
	} // end getSchemaTypeInfo()

	public String getBaseURI() {
		return null;
	}
	
	public String getTextContent() throws DOMException {
		return getValue();
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#getSpecified()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getSpecified() {
		return specified;
	} // end getSpecified()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#setValue(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param value DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setValue(final String value)
		throws DOMException {
		setNodeValue(value);
	} // end setValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Attr#getValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getValue() {
		return getNodeValue();
	} // end getValue()

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param obj DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean equals(final Object obj) {
		if (obj instanceof CAttr) {
			CAttr attr = (CAttr) obj;

			if (attr.name  == this.name &&
				attr.getValue().equals(getValue())) {
				return true;
			} // end if
		} // end if

		return false;
	} // end equals()

	public Node appendChild(Node newChild) throws DOMException {
		updateDeferred();
		cachedValue = null;
		if (newChild.getNodeType() != Node.TEXT_NODE &&
			newChild.getNodeType() != Node.ENTITY_REFERENCE_NODE &&
			newChild.getNodeType() != Node.DOCUMENT_FRAGMENT_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,"Cannot insert node here.");
		}
		return super.appendChild(newChild);
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		String tmp = getValue();
		tmp = CEntityCoDec.encode(tmp, true);

		return name + "=\"" + tmp + "\"";
	} // end toString()

	/**
	 * DOCUMENT ME!
	 *
	 * @author $author$
	 * @version $Revision: 1.20 $
	 */
	private static class contentHandler
		implements ContentHandler {
		/** DOCUMENT ME! */
		ADocument document;

		/** DOCUMENT ME! */
		Node currentElement;

		/** DOCUMENT ME! */
		String entity = null;

		/**
		 * Creates a new contentHandler object.
		 *
		 * @param currentElement DOCUMENT ME!
		 * @param document DOCUMENT ME!
		 * @param dh DOCUMENT ME!
		 */
		public contentHandler(
			final Node  currentElement,
			final ADocument document) {
			this.currentElement     = currentElement;
			this.document		    = document;
		} // end contentHandler()

		/**
		 * DOCUMENT ME!
		 *
		 * @param locator DOCUMENT ME!
		 */
		public void setDocumentLocator(final Locator locator) {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param entity DOCUMENT ME!
		 */
		public void setEntity(final String entity) {
			this.entity = entity;
		} // end setEntity()

		/**
		 * DOCUMENT ME!
		 *
		 * @param ch DOCUMENT ME!
		 * @param start DOCUMENT ME!
		 * @param length DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void characters(
			final char ch[],
			final int start,
			final int length)
			throws SAXException {
			String value =  new String(ch, start, length);

			if (entity == null) {
				Text text = new CText(value, document);
				currentElement.appendChild(text);
			} // end if
			else {
				CEntityReference ref = new CEntityReference(entity,
						value, document);
				currentElement.appendChild(ref);
				entity = null;
			} // end else
		} // end characters()

		/**
		 * DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void endDocument()
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param uri DOCUMENT ME!
		 * @param localName DOCUMENT ME!
		 * @param qName DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void endElement(
			final String uri,
			final String localName,
			final String qName)
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param prefix DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void endPrefixMapping(final String prefix)
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param ch DOCUMENT ME!
		 * @param start DOCUMENT ME!
		 * @param length DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void ignorableWhitespace(
			final char ch[],
			final int start,
			final int length)
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param target DOCUMENT ME!
		 * @param data DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void processingInstruction(
			final String target,
			final String data)
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param name DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void skippedEntity(final String name)
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void startDocument()
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param uri DOCUMENT ME!
		 * @param localName DOCUMENT ME!
		 * @param qName DOCUMENT ME!
		 * @param atts DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void startElement(
			final String uri,
			final String localName,
			final String qName,
			final Attributes atts)
			throws SAXException {}

		/**
		 * DOCUMENT ME!
		 *
		 * @param prefix DOCUMENT ME!
		 * @param uri DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void startPrefixMapping(
			final String prefix,
			final String uri)
			throws SAXException {}
	} // end contentHandler

	/**
	 * DOCUMENT ME!
	 *
	 * @author $author$
	 * @version $Revision: 1.20 $
	 */
	private static class entityHandler
		implements DTDHandler {
		/** DOCUMENT ME! */
		contentHandler h;

		/**
		 * Creates a new entityHandler object.
		 *
		 * @param h DOCUMENT ME!
		 */
		public entityHandler(final contentHandler h) {
			this.h = h;
		} // end entityHandler()

		/**
		 * DOCUMENT ME!
		 *
		 * @param name DOCUMENT ME!
		 * @param publicId DOCUMENT ME!
		 * @param systemId DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void notationDecl(
			final String name,
			final String publicId,
			final String systemId)
			throws SAXException {
			h.setEntity(name);
		} // end notationDecl()

		/**
		 * DOCUMENT ME!
		 *
		 * @param name DOCUMENT ME!
		 * @param publicId DOCUMENT ME!
		 * @param systemId DOCUMENT ME!
		 * @param notationName DOCUMENT ME!
		 *
		 * @throws SAXException DOCUMENT ME!
		 */
		public void unparsedEntityDecl(
			final String name,
			final String publicId,
			final String systemId,
			final String notationName)
			throws SAXException {}
	} // end entityHandler

	public void setSpecified(boolean specified) {
		this.specified = specified;
	}

	public boolean isDefaults() {
		return defaults;
	}

	public void setDefaults(boolean defaults) {
		this.defaults = defaults;
	}

	public boolean hasChildNodes() {
		updateDeferred();
		return super.hasChildNodes();
	}
} // end CAttr
