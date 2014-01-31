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
package org.allcolor.xml.parser;

import java.io.IOException;
import java.util.Locale;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.ext.LexicalHandler;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision: 1.4 $
 */
public class CDOM2SAX implements XMLReader, Parser {
	/** DOCUMENT ME! */
	private ContentHandler chandler = null;

	/** DOCUMENT ME! */
	private DTDHandler dtdhandler = null;

	/** DOCUMENT ME! */
	private DocumentHandler dhandler = null;

	/** DOCUMENT ME! */
	private LexicalHandler lHandler = null;

	/** DOCUMENT ME! */
	private Node nodeToSerialize;

	/**
	 * Creates a new CDOM2SAX object.
	 * 
	 * @param nodeToSerialize
	 *            DOCUMENT ME!
	 */
	public CDOM2SAX(final Node nodeToSerialize) {
		super();
		this.nodeToSerialize = nodeToSerialize;
	} // end CDOM2SAX()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param handler
	 *            DOCUMENT ME!
	 */
	public void setContentHandler(final ContentHandler handler) {
		this.chandler = handler;
	} // end setContentHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public ContentHandler getContentHandler() {
		return this.chandler;
	} // end getContentHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param handler
	 *            DOCUMENT ME!
	 */
	public void setDTDHandler(final DTDHandler handler) {
		this.dtdhandler = handler;
	} // end setDTDHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public DTDHandler getDTDHandler() {
		return this.dtdhandler;
	} // end getDTDHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param handler
	 *            DOCUMENT ME!
	 */
	public void setDocumentHandler(final DocumentHandler handler) {
		this.dhandler = handler;
	} // end setDocumentHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param resolver
	 *            DOCUMENT ME!
	 * 
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public void setEntityResolver(final EntityResolver resolver) {
		throw new RuntimeException(new SAXNotSupportedException());
	} // end setEntityResolver()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public EntityResolver getEntityResolver() {
		throw new RuntimeException(new SAXNotSupportedException());
	} // end getEntityResolver()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param handler
	 *            DOCUMENT ME!
	 * 
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public void setErrorHandler(final ErrorHandler handler) {
		throw new RuntimeException(new SAXNotSupportedException());
	} // end setErrorHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public ErrorHandler getErrorHandler() {
		throw new RuntimeException(new SAXNotSupportedException());
	} // end getErrorHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * @param value
	 *            DOCUMENT ME!
	 * 
	 * @throws SAXNotRecognizedException
	 *             DOCUMENT ME!
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public void setFeature(final String name, final boolean value)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new SAXNotSupportedException();
	} // end setFeature()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXNotRecognizedException
	 *             DOCUMENT ME!
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public boolean getFeature(final String name)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new SAXNotSupportedException();
	} // end getFeature()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param locale
	 *            DOCUMENT ME!
	 * 
	 * @throws SAXException
	 *             DOCUMENT ME!
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public void setLocale(final Locale locale) throws SAXException {
		throw new SAXNotSupportedException();
	} // end setLocale()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * @param value
	 *            DOCUMENT ME!
	 * 
	 * @throws SAXNotRecognizedException
	 *             DOCUMENT ME!
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public void setProperty(final String name, final Object value)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		if ((this.lHandler == null)
				&& name.equals("http://xml.org/sax/properties/lexical-handler")) {
			try {
				this.lHandler = (LexicalHandler) value;
			} // end try
			catch (final Exception ignore) {
				this.lHandler = null;
			} // end catch
		} // end if
	} // end setProperty()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXNotRecognizedException
	 *             DOCUMENT ME!
	 * @throws SAXNotSupportedException
	 *             DOCUMENT ME!
	 */
	public Object getProperty(final String name)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new SAXNotSupportedException();
	} // end getProperty()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param input
	 *            DOCUMENT ME!
	 * 
	 * @throws IOException
	 *             DOCUMENT ME!
	 * @throws SAXException
	 *             DOCUMENT ME!
	 */
	public void parse(final InputSource input) throws IOException, SAXException {
		this.serialize();
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param systemId
	 *            DOCUMENT ME!
	 * 
	 * @throws IOException
	 *             DOCUMENT ME!
	 * @throws SAXException
	 *             DOCUMENT ME!
	 */
	public void parse(final String systemId) throws IOException, SAXException {
		this.serialize();
	} // end parse()

	/**
	 * DOCUMENT ME!
	 */
	public void serialize() {
		this.serialize(this.nodeToSerialize);
	} // end serialize()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param node
	 *            DOCUMENT ME!
	 */
	private void serialize(final Node node) {
		try {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				CAttr attr = new CAttr(node.getAttributes());

				if (this.chandler != null) {
					String nsUri = node.getNamespaceURI();

					if (nsUri == null) {
						nsUri = "";
					}

					this.chandler.startElement(nsUri, node.getLocalName(), node
							.getNodeName(), attr);
				} // end if

				if (this.dhandler != null) {
					this.dhandler.startElement(node.getNodeName(), attr);
				} // end if
			} // end else if
			else if (node.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				if (this.chandler instanceof DefaultHandler2) {
					DefaultHandler2 d2 = (DefaultHandler2) this.chandler;
					d2.startEntity(node.getNodeName());
				} // end if

				if (node.getChildNodes().getLength() == 0) {
					String value = node.getNodeValue();

					if (this.chandler != null) {
						this.chandler.characters(value.toCharArray(), 0, value
								.length());
					} // end if

					if (this.dhandler != null) {
						this.dhandler.characters(value.toCharArray(), 0, value
								.length());
					} // end if
				} // end if
			} // end else if
			else if (node.getNodeType() == Node.TEXT_NODE) {
				String value = node.getNodeValue();

				if (this.chandler != null) {
					this.chandler.characters(value.toCharArray(), 0, value
							.length());
				} // end if

				if (this.dhandler != null) {
					this.dhandler.characters(value.toCharArray(), 0, value
							.length());
				} // end if
			} // end else if
			else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
				String value = node.getNodeValue();

				if (this.chandler != null) {
					this.chandler.characters(value.toCharArray(), 0, value
							.length());
				} // end if

				if (this.dhandler != null) {
					this.dhandler.characters(value.toCharArray(), 0, value
							.length());
				} // end if
			} // end else if
			else if (node.getNodeType() == Node.COMMENT_NODE) {
				if (this.lHandler != null) {
					String value = node.getNodeValue();
					this.lHandler.comment(value.toCharArray(), 0, value
							.length());
				} // end if
			} else if (node.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) {
				if (this.chandler != null) {
					ProcessingInstruction pi = (ProcessingInstruction) node;
					this.chandler.processingInstruction(pi.getTarget(), pi
							.getData());
				} // end if

				if (this.dhandler != null) {
					ProcessingInstruction pi = (ProcessingInstruction) node;
					this.dhandler.processingInstruction(pi.getTarget(), pi
							.getData());
				} // end if
			} // end else if

			NodeList nl = node.getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				Node child = nl.item(i);
				this.serialize(child);
			} // end for

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (this.chandler != null) {
					String nsUri = node.getNamespaceURI();

					if (nsUri == null) {
						nsUri = "";
					}

					this.chandler.endElement(nsUri, node.getLocalName(), node
							.getNodeName());
				} // end if

				if (this.dhandler != null) {
					this.dhandler.endElement(node.getNodeName());
				} // end if
			} // end else if
			else if (node.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				if (this.chandler instanceof DefaultHandler2) {
					DefaultHandler2 d2 = (DefaultHandler2) this.chandler;
					d2.endEntity(node.getNodeName());
				} // end if
			} // end else if
		} catch (SAXException ignore) {
		}
	} // end serialize()

	/**
	 * DOCUMENT ME!
	 * 
	 * @author $author$
	 * @version $Revision: 1.4 $
	 */
	private static class CAttr implements Attributes, AttributeList {
		/** DOCUMENT ME! */
		private NamedNodeMap nnm;

		/**
		 * Creates a new CAttr object.
		 * 
		 * @param nnm
		 *            DOCUMENT ME!
		 */
		public CAttr(final NamedNodeMap nnm) {
			this.nnm = nnm;
		} // end CAttr()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param uri
		 *            DOCUMENT ME!
		 * @param localName
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public int getIndex(final String uri, final String localName) {
			Attr attr = (Attr) this.nnm.getNamedItemNS(uri, localName);

			for (int i = 0; i < this.nnm.getLength(); i++) {
				if (this.nnm.item(i) == attr) {
					return i;
				}
			} // end for

			return -1;
		} // end getIndex()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param qName
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public int getIndex(final String qName) {
			Attr attr = (Attr) this.nnm.getNamedItem(qName);

			for (int i = 0; i < this.nnm.getLength(); i++) {
				if (this.nnm.item(i) == attr) {
					return i;
				}
			} // end for

			return -1;
		} // end getIndex()

		/**
		 * DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public int getLength() {
			return this.nnm.getLength();
		} // end getLength()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param index
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getLocalName(final int index) {
			Attr attr = (Attr) this.nnm.item(index);

			return attr.getLocalName();
		} // end getLocalName()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param i
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getName(final int i) {
			Attr attr = (Attr) this.nnm.item(i);

			return attr.getNodeName();
		} // end getName()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param index
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getQName(final int index) {
			Attr attr = (Attr) this.nnm.item(index);

			return attr.getNodeName();
		} // end getQName()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param index
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getType(final int index) {
			return "CDATA";
		} // end getType()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param uri
		 *            DOCUMENT ME!
		 * @param localName
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getType(final String uri, final String localName) {
			return "CDATA";
		} // end getType()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param qName
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getType(final String qName) {
			return "CDATA";
		} // end getType()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param index
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getURI(final int index) {
			Attr attr = (Attr) this.nnm.item(index);

			return attr.getNamespaceURI();
		} // end getURI()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param index
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getValue(final int index) {
			Attr attr = (Attr) this.nnm.item(index);
			if (attr == null) {
				return "";
			}
			if ("xml".equals(attr.getPrefix())) {
				System.err.println(index + " - " + attr.getName() + " - "
						+ attr.getValue() + " - " + attr.getNamespaceURI()
						+ " - " + attr.getPrefix() + " - "
						+ attr.getLocalName());
			}
			return attr.getValue();
		} // end getValue()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param uri
		 *            DOCUMENT ME!
		 * @param localName
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getValue(final String uri, final String localName) {
			Attr attr = (Attr) this.nnm.getNamedItemNS(uri, localName);
			if (attr == null) {
				return "";
			}
			return attr.getValue();
		} // end getValue()

		/**
		 * DOCUMENT ME!
		 * 
		 * @param qName
		 *            DOCUMENT ME!
		 * 
		 * @return DOCUMENT ME!
		 */
		public String getValue(final String qName) {
			Attr attr = (Attr) this.nnm.getNamedItem(qName);
			if (attr == null) {
				return "";
			}
			return attr.getValue();
		} // end getValue()
	} // end CAttr
} // end CDOM2SAX
