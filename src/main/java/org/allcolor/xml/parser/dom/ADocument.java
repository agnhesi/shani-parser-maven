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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.allcolor.css.parser.CCSSParser;
import org.allcolor.css.parser.CStyleSheetList;
import org.allcolor.css.parser.CStyler;
import org.allcolor.dtd.parser.CDocType;
import org.allcolor.xml.parser.CDomImplementation;
import org.allcolor.xml.parser.CStringBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.StyleSheetList;

/**
 * DOCUMENT ME!
 * 
 * @author Quentin Anciaux
 */
public abstract class ADocument extends CElement implements Document,
		DocumentCSS {
	/** DOCUMENT ME! */
	public final static long serialVersionUID = 3800321148234974275L;

	// letters, digits, hyphens, underscores, colons, and periods
	public static final String VALID_XML_FIRST_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_:";

	public static final String VALID_XML_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_:.-";

	public final static void checkNameValidXML(final String name,
			final String xmlVersion) {
		if ((name == null) || (name.trim().length() == 0)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
					"Invalid characted in name : " + name);
		}
		char[] array = name.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				if ("1.0".equals(xmlVersion) || (xmlVersion == null)) {
					if (ADocument.VALID_XML_FIRST_LETTERS.indexOf(array[i], 0) == -1) {
						throw new DOMException(
								DOMException.INVALID_CHARACTER_ERR,
								"Invalid characted in name : " + name + " - '"
										+ array[i] + "'");
					}
				} else if ("1.1".equals(xmlVersion)) {
					if (!ADocument.VALID_XML11_FIRST_LETTERS(array[i])) {
						throw new DOMException(
								DOMException.INVALID_CHARACTER_ERR,
								"Invalid characted in name : " + name + " - '"
										+ array[i] + "'");
					}
				}
			} else {
				if ("1.0".equals(xmlVersion) || (xmlVersion == null)) {
					if (ADocument.VALID_XML_LETTERS.indexOf(array[i], 0) == -1) {
						throw new DOMException(
								DOMException.INVALID_CHARACTER_ERR,
								"Invalid characted in name : " + name + " - '"
										+ array[i] + "'");
					}
				} else if ("1.1".equals(xmlVersion)) {
					if (!ADocument.VALID_XML11_LETTERS(array[i])) {
						throw new DOMException(
								DOMException.INVALID_CHARACTER_ERR,
								"Invalid characted in name : " + name + " - '"
										+ array[i] + "'");
					}
				}
			}
		}
	}

	public final static boolean checkNameValidXMLInternal(final String name,
			final String xmlVersion) {
		if ((name == null) || (name.trim().length() == 0)) {
			return false;
		}
		char[] array = name.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				if ("1.0".equals(xmlVersion) || (xmlVersion == null)) {
					if (ADocument.VALID_XML_FIRST_LETTERS.indexOf(array[i], 0) == -1) {
						return false;
					}
				} else if ("1.1".equals(xmlVersion)) {
					if (!ADocument.VALID_XML11_FIRST_LETTERS(array[i])) {
						return false;
					}
				}
			} else {
				if ("1.0".equals(xmlVersion) || (xmlVersion == null)) {
					if (ADocument.VALID_XML_LETTERS.indexOf(array[i], 0) == -1) {
						return false;
					}
				} else if ("1.1".equals(xmlVersion)) {
					if (!ADocument.VALID_XML11_LETTERS(array[i])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static final boolean VALID_XML11_FIRST_LETTERS(char c) {
		if ((c >= 'A') && (c <= 'Z')) {
			return true;
		}
		if ((c >= 'a') && (c <= 'z')) {
			return true;
		}
		if ((c == '_') || (c == ':')) {
			return true;
		}
		if ((c >= 0x00C0) && (c <= 0x00D6)) {
			return true;
		}
		if ((c >= 0x00D8) && (c <= 0x00F6)) {
			return true;
		}
		if ((c >= 0x00F8) && (c <= 0x02FF)) {
			return true;
		}
		if ((c >= 0x0370) && (c <= 0x037D)) {
			return true;
		}
		if ((c >= 0x037F) && (c <= 0x1FFF)) {
			return true;
		}
		if ((c >= 0x200C) && (c <= 0x200D)) {
			return true;
		}
		if ((c >= 0x2070) && (c <= 0x218F)) {
			return true;
		}
		if ((c >= 0x2C00) && (c <= 0x2FEF)) {
			return true;
		}
		if ((c >= 0x3001) && (c <= 0xD7FF)) {
			return true;
		}
		if ((c >= 0xF900) && (c <= 0xFDCF)) {
			return true;
		}
		if ((c >= 0xFDF0) && (c <= 0xFFFD)) {
			return true;
		}
		if ((c >= 0x10000) && (c <= 0xEFFFF)) {
			return true;
		}
		return false;
	}

	public static final boolean VALID_XML11_LETTERS(char c) {
		if ((c >= 'A') && (c <= 'Z')) {
			return true;
		}
		if ((c >= 'a') && (c <= 'z')) {
			return true;
		}
		if ((c >= '0') && (c <= '9')) {
			return true;
		}
		if ((c == '_') || (c == ':') || (c == '-') || (c == '.')
				|| (c == 0x00B7)) {
			return true;
		}
		if ((c >= 0x0300) && (c <= 0x036F)) {
			return true;
		}
		if ((c >= 0x203F) && (c <= 0x2040)) {
			return true;
		}
		if ((c >= 0x00C0) && (c <= 0x00D6)) {
			return true;
		}
		if ((c >= 0x00D8) && (c <= 0x00F6)) {
			return true;
		}
		if ((c >= 0x00F8) && (c <= 0x02FF)) {
			return true;
		}
		if ((c >= 0x0370) && (c <= 0x037D)) {
			return true;
		}
		if ((c >= 0x037F) && (c <= 0x1FFF)) {
			return true;
		}
		if ((c >= 0x200C) && (c <= 0x200D)) {
			return true;
		}
		if ((c >= 0x2070) && (c <= 0x218F)) {
			return true;
		}
		if ((c >= 0x2C00) && (c <= 0x2FEF)) {
			return true;
		}
		if ((c >= 0x3001) && (c <= 0xD7FF)) {
			return true;
		}
		if ((c >= 0xF900) && (c <= 0xFDCF)) {
			return true;
		}
		if ((c >= 0xFDF0) && (c <= 0xFFFD)) {
			return true;
		}
		if ((c >= 0x10000) && (c <= 0xEFFFF)) {
			return true;
		}
		return false;
	}

	/** DOCUMENT ME! */
	protected CEntityCoDec codec = null;

	protected CDOMConfiguration config = null;

	/** DOCUMENT ME! */
	protected String cookie = null;

	/** DOCUMENT ME! */
	protected CStyleSheetList cssList = null;

	/** DOCUMENT ME! */
	protected CSSStyleSheet defaultStyleSheet = null;

	/** DOCUMENT ME! */
	protected CStringBuilder docBuffer = null;

	/** DOCUMENT ME! */
	protected DocumentType doctype = null;

	protected Element documentElement = null;

	public boolean documentElementSet = false;

	/** DOCUMENT ME! */
	protected boolean documentOpen = false;

	/** DOCUMENT ME! */
	protected String documentURI = null;

	protected String encoding = null;

	public boolean hasNS = false;

	/** DOCUMENT ME! */
	protected DOMImplementation impl = null;

	protected boolean isBuildStage = true;

	/** DOCUMENT ME! */
	protected boolean isHTMLDocument = false;

	/** DOCUMENT ME! */
	protected String referrer = null;

	/** DOCUMENT ME! */
	protected boolean strictErrorChecking = true;

	protected CStyler styler = null;

	/** DOCUMENT ME! */
	protected String stylesheetPI = null;

	protected String xmlEncoding = null;

	/** DOCUMENT ME! */
	protected boolean xmlStandalone = false;

	/** DOCUMENT ME! */
	protected String xmlVersion = null;

	/**
	 * Creates a new ADocument object.
	 */
	public ADocument() {
		super("#document", null);
		this.prefix = null;
		this.localName = null;
		this.nameSpace = "  ";
		this.isDom1 = true;
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public ADocument(final boolean isHTMLDocument, final String documentURI) {
		this(isHTMLDocument, documentURI, null);
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 * @param cookie
	 *            DOCUMENT ME!
	 */
	public ADocument(final boolean isHTMLDocument, final String documentURI,
			final String cookie) {
		this(isHTMLDocument, documentURI, cookie, null, null);
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 * @param cookie
	 *            DOCUMENT ME!
	 * @param referrer
	 *            DOCUMENT ME!
	 */
	public ADocument(final boolean isHTMLDocument, final String documentURI,
			final String cookie, final String referrer) {
		this(isHTMLDocument, documentURI, cookie, referrer, null);
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 * @param cookie
	 *            DOCUMENT ME!
	 * @param referrer
	 *            DOCUMENT ME!
	 * @param defaultStyleSheet
	 *            DOCUMENT ME!
	 */
	public ADocument(final boolean isHTMLDocument, final String documentURI,
			final String cookie, final String referrer,
			final CSSStyleSheet defaultStyleSheet) {
		this(null, documentURI, defaultStyleSheet);
		this.isHTMLDocument = isHTMLDocument;
		if (isHTMLDocument) {
			this.cookie = cookie;
			this.referrer = referrer;
		} // end if
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public ADocument(final String documentURI) {
		this(null, documentURI, null);
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param stylesheetPI
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public ADocument(final String stylesheetPI, final String documentURI) {
		this(stylesheetPI, documentURI, null);
	} // end ADocument()

	/**
	 * Creates a new ADocument object.
	 * 
	 * @param stylesheetPI
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 * @param defaultStyleSheet
	 *            DOCUMENT ME!
	 */
	public ADocument(final String stylesheetPI, final String documentURI,
			final CSSStyleSheet defaultStyleSheet) {
		this();
		this.stylesheetPI = stylesheetPI;
		this.defaultStyleSheet = defaultStyleSheet;
		this.documentURI = documentURI;
	} // end ADocument()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param id
	 *            DOCUMENT ME!
	 * @param elem
	 *            DOCUMENT ME!
	 * @param idAttribute
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	protected Element _GetElementById(final String id, final Element elem) {
		if (id == null) {
			return null;
		}

		if (elem == null) {
			return null;
		}

		NamedNodeMap nnm = elem.getAttributes();
		if (nnm != null) {
			for (int i = 0; i < nnm.getLength(); i++) {
				Attr attr = (Attr) nnm.item(i);
				if (attr.isId()) {
					if (id.equals(attr.getValue())) {
						return elem;
					}
					break;
				}
			}
		}

		NodeList nl = elem.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element result = this._GetElementById(id, (Element) node);

				if (result != null) {
					return result;
				}
			} // end if
		} // end for

		return null;
	} // end _GetElementById()

	public final String _getXmlVersion() {
		return this.xmlVersion;
	} // end getXmlVersion()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param sheet
	 *            DOCUMENT ME!
	 */
	public final void addStyleSheets(final CSSStyleSheet sheet) {
		if (this.cssList == null) {
			this.cssList = new CStyleSheetList();
		} // end if

		this.cssList.add(sheet);
	} // end addStyleSheets()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#adoptNode(org.w3c.dom.Node)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param source
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Node adoptNode(final Node source) throws DOMException {
		if ((source.getNodeType() == Node.DOCUMENT_NODE)
				|| (source.getNodeType() == Node.DOCUMENT_TYPE_NODE)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"This operation is not supported.");
		}
		if (source instanceof ANode) {
			((ANode) source).isReadOnly();
		}
		if (source.getParentNode() != null) {
			source.getParentNode().removeChild(source);
		} else if (source.getNodeType() == Node.ATTRIBUTE_NODE) {
			Attr attr = (Attr) source;
			if (attr.getOwnerElement() != null) {
				attr.getOwnerElement().removeAttributeNode(attr);
			}
		}
		ANode result = null;
		if (source instanceof ANode) {
			result = (ANode) source;
			((ANode) result).ownerDocument = this;
			if (source.getNodeType() == Node.ATTRIBUTE_NODE) {
				if (!((CAttr) source).getSpecified()
						&& (source.getPrefix() == null)) {
					((CAttr) result).setNamespaceURI(" ");
				}
				((CAttr) result).setSpecified(true);
			}
		} else {
			result = (ANode) this._CloneNode(this, source, true, false);
			if (source.getNodeType() == Node.ATTRIBUTE_NODE) {
				((CAttr) result).setSpecified(true);
			}
		}
		if (result.userDataMap != null) {
			for (Iterator it = result.userDataMap.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				Object[] o = (Object[]) entry.getValue();
				if (o[1] != null) {
					UserDataHandler udh = (UserDataHandler) o[1];
					udh.handle(UserDataHandler.NODE_ADOPTED, key, o[0], result,
							null);
				}
			}
		}
		return result;
	} // end adoptNode()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#appendChild(org.w3c.dom.Node)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param newChild
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public Node appendChild(final Node newChild) throws DOMException {
		if ((this.getDocumentElement() == this)
				&& (newChild.getNodeType() == Node.TEXT_NODE)
				&& (this.listChild == null)) {
			if ((newChild.getNodeValue() == null)
					|| "".equals(newChild.getNodeValue().trim())) {
				return null;
			}
		}
		if (!this.isBuildStage) {
			if ((newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE)
					&& (this.getDoctype() != null)) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
						"Cannot insert node here.");
			}
			if ((newChild.getNodeType() == Node.ELEMENT_NODE)
					&& (this.getDocumentElement() != this)) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
						"Cannot insert node here.");
			}
		}
		return super.appendChild(newChild);
	} // end appendChild()

	public final void appendChildInternal(ANode newChild) {
		if (!this.documentElementSet) {
			if (newChild.getNodeType() == Node.TEXT_NODE) {
				if (this.listChild == null) {
					if ((newChild.getNodeValue() == null)
							|| "".equals(newChild.getNodeValue().trim())) {
						return;
					}
				}
			} else if (newChild.getNodeType() == Node.ELEMENT_NODE) {
				this.documentElement = (CElement) newChild;
				this.documentElementSet = true;
			}
		}
		newChild.parentNode = this;
		if (this.listChild == null) {
			this.listChild = new CNodeList(false);
		} // end if
		this.listChild.addItem(newChild);
	}

	public final void checkNameValidXML(final String name) throws DOMException {
		if (this.isBuildStage) {
			return;
		}
		ADocument.checkNameValidXML(name, this.xmlVersion);
	}

	public final Object clone() throws CloneNotSupportedException {
		ADocument doc = (ADocument) super.clone();
		doc.resetOwner(doc);
		CDocType docType = (CDocType) doc.getDoctype();
		if (docType != null) {
			NodeList nl = doc.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
					doc.doctype = (DocumentType) n;
					break;
				}
			}
			doc.setEntityCodec(new CEntityCoDec(docType.getKnownEntities()));
		}
		return doc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createAttribute(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Attr createAttribute(final String name) throws DOMException {
		this.checkNameValidXML(name);
		CAttr attr = new CAttr(name, "", this, null, false);
		if (!this.isBuildStage) {
			attr.dom1Nullify();
		}
		return attr;
	} // end createAttribute()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createAttributeNS(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param namespaceURI
	 *            DOCUMENT ME!
	 * @param qualifiedName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Attr createAttributeNS(String namespaceURI,
			final String qualifiedName) throws DOMException {
		if ("*".equals(namespaceURI)) {
			namespaceURI = null;
		}
		if (qualifiedName.indexOf(':', 0) != -1) {
			if ((namespaceURI == null)
					|| (qualifiedName.indexOf(':', 0) != qualifiedName
							.lastIndexOf(':'))) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Malformed name " + qualifiedName);
			}
			String prefix = qualifiedName.substring(0, qualifiedName.indexOf(
					':', 0));
			if (prefix.equals("xml")
					&& !"http://www.w3.org/XML/1998/namespace"
							.equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal prefix for URI " + qualifiedName + " - "
								+ namespaceURI);
			}
			if ("".equals(prefix.trim())) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal prefix, prefix must be non-empty.");
			}
			String tagName = qualifiedName.substring(qualifiedName.indexOf(':',
					0) + 1);
			if ((prefix.length() > 0) && (tagName.length() == 0)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal tag name.");
			}
			if ("xmlns".equals(tagName)
					&& !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"xmlns is a reserved namespace.");
			}
			if ("xmlns".equals(prefix)
					&& !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"xmlns is a reserved namespace.");
			}
			this.checkNameValidXML(tagName);
			// checkNamespaceExist(namespaceURI,prefix);
		} else {
			if ("xmlns".equals(qualifiedName)
					&& !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"xmlns is a reserved namespace.");
			}
			this.checkNameValidXML(qualifiedName);
			// checkNamespaceExist(namespaceURI,null);
		}

		CAttr attr = new CAttr(qualifiedName, "", this, null, false,
				namespaceURI);
		return attr;
	} // end createAttributeNS()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createCDATASection(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param data
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final CDATASection createCDATASection(final String data)
			throws DOMException {
		return new CCDATASection(data, this);
	} // end createCDATASection()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createComment(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param data
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final Comment createComment(final String data) {
		return new CComment(data, this);
	} // end createComment()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createDocumentFragment()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final DocumentFragment createDocumentFragment() {
		return new CDocumentFragment(this);
	} // end createDocumentFragment()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createElement(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param tagName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Element createElement(final String tagName)
			throws DOMException {
		this.checkNameValidXML(tagName);
		CElement elem = (CElement) this.createElementInternal(tagName, -1);
		if (!this.isBuildStage) {
			elem.dom1Nullify();
		}
		return elem;
	} // end createElement()

	public abstract CElement createElementInternal(final String tagName, int indexSep)
			throws DOMException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createElementNS(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param namespaceURI
	 *            DOCUMENT ME!
	 * @param qualifiedName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Element createElementNS(final String namespaceURI,
			final String qualifiedName) throws DOMException {
		if (qualifiedName.indexOf(':', 0) != -1) {
			if ((namespaceURI == null)
					|| (qualifiedName.indexOf(':', 0) != qualifiedName
							.lastIndexOf(':'))) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Malformed name " + qualifiedName);
			}
			String prefix = qualifiedName.substring(0, qualifiedName.indexOf(
					':', 0));
			if (prefix.equals("xml")
					&& !"http://www.w3.org/XML/1998/namespace"
							.equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal prefix for URI " + qualifiedName + " - "
								+ namespaceURI);
			}
			if ("".equals(prefix.trim())) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Prefix must be non-empty.");
			}
			String tagName = qualifiedName.substring(qualifiedName.indexOf(':',
					0) + 1);
			if ((prefix.length() > 0) && (tagName.length() == 0)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal tag name.");
			}
			this.checkNameValidXML(tagName);
		} else {
			this.checkNameValidXML(qualifiedName);
		}

		CElement elem = new CElement(qualifiedName, this);
		if ((namespaceURI == null) || "".equals(namespaceURI.trim())) {
			elem.setNamespaceURI(" ");
		} else {
			elem.setNamespaceURI(namespaceURI);
		}
		return elem;
	} // end createElementNS()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createEntityReference(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final EntityReference createEntityReference(final String name)
			throws DOMException {
		this.checkNameValidXML(name);
		/*
		 * if (isHTMLDocument) { if (getDoctype() != null &&
		 * getDoctype().getEntities().getNamedItem(name) == null) throw new
		 * DOMException(DOMException.NOT_SUPPORTED_ERR,"Cannot add
		 * entityReference '"+name+"' in an html document. : "+getDoctype()); }
		 */
		return new CEntityReference(name, null, this);
	} // end createEntityReference()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createProcessingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param target
	 *            DOCUMENT ME!
	 * @param data
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final ProcessingInstruction createProcessingInstruction(
			final String target, final String data) throws DOMException {
		this.checkNameValidXML(target);
		return new CProcessingInstruction(target, data, this);
	} // end createProcessingInstruction()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#createTextNode(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param data
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final Text createTextNode(String data) {
		data = CEntityCoDec.encode(data);

		return new CText(data, this);
	} // end createTextNode()

	/**
	 * DOCUMENT ME!
	 */
	protected final void emptyDocument() {
		CNodeList nl = (CNodeList) this.getChildNodes();
		List nodeToRemove = new ArrayList();

		for (int i = 0; i < nl.getLength(); i++) {
			nodeToRemove.add(nl.item(i));
		} // end for

		for (int i = 0; i < nodeToRemove.size(); i++) {
			nl.removeItem((Node) nodeToRemove.get(i));
		} // end for
	} // end emptyDocument()

	public final NamedNodeMap getAttributes() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getDoctype()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final DocumentType getDoctype() {
		return this.doctype;
	} // end getDoctype()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getDocumentElement()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final Element getDocumentElement() {
		if (!this.documentElementSet) {
			CNodeList nl = this.listChild;
			if (nl != null) {
				for (int i = 0; i < nl.count; i++) {
					Node n = nl.item(i);

					if (n.getNodeType() == Node.ELEMENT_NODE) {
						this.documentElement = (Element) n;
						this.documentElementSet = true;
						return this.documentElement;
					} // end if
				} // end for
			}
		} else {
			return this.documentElement;
		}
		return this;
	} // end getDocumentElement()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getDocumentURI()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final String getDocumentURI() {
		return this.documentURI;
	} // end getDocumentURI()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getDomConfig()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final DOMConfiguration getDomConfig() {
		if (this.config == null) {
			this.config = new CDOMConfiguration();
		}
		return this.config;
	} // end getDomConfig()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getElementById(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param elementId
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final Element getElementById(final String elementId) {
		return this._GetElementById(elementId, this);
	} // end getElementById()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final CEntityCoDec getEntityCodec() {
		if (this.codec == null) {
			this.codec = new CEntityCoDec(new HashMap(0));
		}
		return this.codec;
	} // end getEntityCodec()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param cssList
	 *            DOCUMENT ME!
	 */
	protected final void getHTMLCSS(final CStyleSheetList cssList) {
		NodeList nl = this.getElementsByTagName("link");
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			String rel = e.getAttribute("rel");
			String href = e.getAttribute("href");

			if ("stylesheet".equalsIgnoreCase(rel)) {
				if (href.indexOf("//") == -1) {
					String base = this.getDocumentURI();

					if (base != null) {
						if (href.startsWith("/")) {
							String scheme = base.substring(0, base
									.indexOf("://"));
							base = base.substring(base.indexOf("://") + 3);

							if (base.indexOf('/') != -1) {
								String host = base.substring(0, base
										.indexOf('/'));
								href = scheme + "://" + host + href;
							} // end if
							else {
								href = scheme + "://" + base + href;
							} // end else
						} // end if
						else {
							if (href.startsWith("../")) {
								String scheme = base.substring(0, base
										.indexOf("://"));
								String host = base.substring(base
										.indexOf("://") + 3);

								if (host.indexOf('/') != -1) {
									host = host.substring(0, host.indexOf('/'));
								}

								if (base.indexOf('/') != -1) {
									base = base.substring(0, base
											.lastIndexOf('/'));
								}

								while (href.startsWith("../")) {
									href = href.substring(3);

									if (base.lastIndexOf('/') != -1) {
										base = base.substring(0, base
												.lastIndexOf('/'));
									}
								} // end while

								if (base.indexOf(host) == -1) {
									base = scheme + "://" + host + "/";
								} else {
									base = base + "/";
								}

								href = base + href;
							} // end if
							else {
								base = base.substring(0,
										base.lastIndexOf('/') + 1);
								href = base + href;
							} // end else
						} // end else
					} // end if
				} else if (href.startsWith("//")) {
					href = "http:" + href;
				}

				try {
					InputStream in = new URL(href).openStream();
					ByteArrayOutputStream bOut = new ByteArrayOutputStream();
					int iNbByteRead = -1;
					byte buffer[] = new byte[16384];

					while ((iNbByteRead = in.read(buffer)) != -1) {
						bOut.write(buffer, 0, iNbByteRead);
					} // end while

					String document = new String(bOut.toByteArray(), "utf-8");
					CSSStyleSheet sheet = CCSSParser.parse(document, null,
							null, href);

					if (sheet != null) {
						cssList.add(sheet);
					}
				} // end try
				catch (final Exception ignore) {
					System.err.println("WARNING: could not load CSS : " + href);
				} // end catch
			} // end if
		} // end for
		nl = this.getElementsByTagName("style");

		StringBuffer sbuffer = new StringBuffer();

		for (int i = 0; i < nl.getLength(); i++) {
			Element style = (Element) nl.item(i);
			NodeList c = style.getChildNodes();

			for (int j = 0; j < c.getLength(); j++) {
				Node node = c.item(j);

				if ((node.getNodeType() == Node.TEXT_NODE)
						|| (node.getNodeType() == Node.CDATA_SECTION_NODE)
						|| (node.getNodeType() == Node.COMMENT_NODE)
						|| (node.getNodeType() == Node.ENTITY_REFERENCE_NODE)) {
					sbuffer.append(node.toString());
				} // end if
			} // end for
		} // end for

		try {
			String document = sbuffer.toString();
			CSSStyleSheet sheet = CCSSParser.parse(document, null, null, this
					.getDocumentURI());

			if (sheet != null) {
				cssList.add(sheet);
			}
		} // end try
		catch (final Exception ignore) {
		} // end catch
	} // end getHTMLCSS()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean getHTMLDocument() {
		return this.isHTMLDocument;
	} // end getHTMLDocument()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getImplementation()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final DOMImplementation getImplementation() {
		if (this.impl == null) {
			this.impl = new CDomImplementation();
		}
		return this.impl;
	} // end getImplementation()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getInputEncoding()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final String getInputEncoding() {
		return this.encoding;
	} // end getInputEncoding()

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
	public final short getNodeType() {
		return Node.DOCUMENT_NODE;
	} // end getNodeType()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.DocumentCSS#getOverrideStyle(org.w3c.dom.Element,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param elt
	 *            DOCUMENT ME!
	 * @param pseudoElt
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final CSSStyleDeclaration getOverrideStyle(final Element elt,
			final String pseudoElt) {
		return this.getStyler().getStyle(elt, pseudoElt);
	} // end getOverrideStyle()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param cssList
	 *            DOCUMENT ME!
	 */
	protected final void getPICSS(final CStyleSheetList cssList) {
		// TODO
	} // end getPICSS()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getStrictErrorChecking()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	} // end getStrictErrorChecking()

	public final CStyler getStyler() {
		if (this.styler == null) {
			this.styler = new CStyler(this.getStyleSheets());
		}
		return this.styler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.DocumentStyle#getStyleSheets()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final StyleSheetList getStyleSheets() {
		if ((this.isHTMLDocument) || (this.stylesheetPI != null)) {
			if ((this.cssList == null) && (this.isHTMLDocument)) {
				this.cssList = new CStyleSheetList();

				if (this.defaultStyleSheet != null) {
					this.cssList.add(this.defaultStyleSheet);
				}

				this.getHTMLCSS(this.cssList);
			} // end if
			else if (this.cssList == null) {
				this.cssList = new CStyleSheetList();

				if (this.defaultStyleSheet != null) {
					this.cssList.add(this.defaultStyleSheet);
				}

				this.getPICSS(this.cssList);
			} // end else if
		} // end if
		else if (this.cssList == null) {
			this.cssList = new CStyleSheetList();
		} // end else if

		return this.cssList;
	} // end getStyleSheets()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getXmlEncoding()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final String getXmlEncoding() {
		return this.xmlEncoding;
	} // end getXmlEncoding()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getXmlStandalone()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean getXmlStandalone() {
		return this.xmlStandalone;
	} // end getXmlStandalone()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#getXmlVersion()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final String getXmlVersion() {
		return this.xmlVersion == null ? "1.0" : this.xmlVersion;
	} // end getXmlVersion()

	// FIXME TODO
	public final Document getXSD(String namespaceURI) {
		/*
		 * if (hasXSD()) return (Document)xsd.get(namespaceURI);
		 */
		return null;
	}

	// FIXME TODO
	public final boolean hasXSD() {
		return false;
		/*
		 * if (xsd.size() == 0) loadXSD(); return xsd.size() > 0;
		 */
	}

	public final DocumentFragment importAnyNode(Node node) {
		CDocumentFragment frag = new CDocumentFragment(this);
		if (node.getNodeType() == Node.DOCUMENT_NODE) {
			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				ANode n = (ANode) this._CloneNode(this, nl.item(i), true, true);
				n.setParent(null);
				frag.appendChild(n);
			}
		} else {
			frag.appendChild(this._CloneNode(this, node, true, true));
		}
		return frag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#importNode(org.w3c.dom.Node, boolean)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param importedNode
	 *            DOCUMENT ME!
	 * @param deep
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Node importNode(final Node importedNode, final boolean deep)
			throws DOMException {
		if ((importedNode.getNodeType() == Node.DOCUMENT_NODE)
				|| (importedNode.getNodeType() == Node.DOCUMENT_TYPE_NODE)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"This operation is not supported.");
		}
		ANode result = (ANode) this._CloneNode(this, importedNode, deep, false);
		if (result.getNodeType() == Node.ATTRIBUTE_NODE) {
			((CAttr) result).setSpecified(true);
		}
		if (result.userDataMap != null) {
			for (Iterator it = result.userDataMap.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				Object[] o = (Object[]) entry.getValue();
				if (o[1] != null) {
					UserDataHandler udh = (UserDataHandler) o[1];
					udh.handle(UserDataHandler.NODE_IMPORTED, key, o[0],
							importedNode, result);
				}
			}
		}
		return result;
	} // end importNode()

	public final boolean isBuildStage() {
		return this.isBuildStage || this.ignoreAll;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param newChild
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final Node isInHeaderElement(final Node newChild) {
		Node result = null;

		if ("head".equals(newChild.getNodeName())
				|| "link".equals(newChild.getNodeName())
				|| "meta".equals(newChild.getNodeName())
				|| "base".equals(newChild.getNodeName())
				|| "style".equals(newChild.getNodeName())
				|| "title".equals(newChild.getNodeName())) {
			result = this.getParentNode().appendChild(newChild);
		}

		if (result == null) {
			if ("frameset".equals(newChild.getNodeName())
					|| "frame".equals(newChild.getNodeName())) {
				result = this.getParentNode().appendChild(newChild);
			}
		} // end if

		return result;
	} // end isInHeaderElement()

	// FIXME TODO
	public final void loadXSD() {
		// loadXSD(getDocumentElement());
	}

	// FIXME TODO
	public final void loadXSD(Element elem) {
		/*
		 * if (elem != null && elem != this) { Attr schemaLocation =
		 * elem.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance","schemaLocation");
		 * if (schemaLocation != null) { String nslocation =
		 * schemaLocation.getValue(); CStringTokenizer tokenizer = new
		 * CStringTokenizer(nslocation," \t\n\r",false); while
		 * (tokenizer.hasMoreTokens()) { String namespaceURI =
		 * tokenizer.nextToken(); if (tokenizer.hasMoreTokens()) { String
		 * location = tokenizer.nextToken(); if (location.indexOf(":/") == -1) {
		 * if (documentURI != null && documentURI.indexOf('/') != -1) location =
		 * documentURI.substring(0,documentURI.lastIndexOf('/')+1)+location; }
		 * if (xsd.containsKey(namespaceURI)) continue; CShaniDomParser parser =
		 * new CShaniDomParser(); // FIXME try { Document doc = parser.parse(new
		 * URL(location)); xsd.put(namespaceURI,doc); NodeList nl =
		 * doc.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema","import");
		 * if (nl.getLength() > 0) { for (int i=0;i<nl.getLength();i++) {
		 * Element imp = (Element)nl.item(i); namespaceURI =
		 * imp.getAttribute("namespace"); if (xsd.containsKey(namespaceURI))
		 * continue; location = imp.getAttribute("schemaLocation"); if
		 * (location.indexOf(":/") == -1) { if (doc.getDocumentURI() != null &&
		 * doc.getDocumentURI().indexOf('/') != -1) location =
		 * doc.getDocumentURI().substring(0,doc.getDocumentURI().lastIndexOf('/')+1)+location; }
		 * doc = parser.parse(new URL(location)); xsd.put(namespaceURI,doc); } } }
		 * catch (MalformedURLException e){} catch (IOException e){} } } } }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#normalizeDocument()
	 */
	/**
	 * DOCUMENT ME!
	 */
	public final void normalizeDocument() {
		this._normalizeDocument();
	}

	public Node removeChild(Node oldChild) throws DOMException {
		Node n = super.removeChild(oldChild);
		if (oldChild == this.documentElement) {
			this.documentElement = null;
		}
		if (oldChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			this.setDocumentType(null);
		}
		return n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#renameNode(org.w3c.dom.Node, java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param n
	 *            DOCUMENT ME!
	 * @param namespaceURI
	 *            DOCUMENT ME!
	 * @param qualifiedName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final Node renameNode(final Node n, String namespaceURI,
			final String qualifiedName) throws DOMException {
		if ((n.getOwnerDocument() != null) && (n.getOwnerDocument() != this)) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
					"The owner document of both node are different.");
		}
		if ((n.getNodeType() != Node.ELEMENT_NODE)
				&& (n.getNodeType() != Node.ATTRIBUTE_NODE)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"This operation is not supported.");
		}
		if (n.getOwnerDocument() != this) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
					"The owner document of both node are different.");
		}

		if ((namespaceURI == null) || "".equals(namespaceURI.trim())) {
			namespaceURI = " ";
		}

		ANode elem = (ANode) n;
		elem.nameSpace = namespaceURI;

		if (qualifiedName.indexOf(':', 0) != -1) {
			elem.localName = qualifiedName.substring(qualifiedName.indexOf(':',
					0) + 1);
			elem.prefix = qualifiedName.substring(0, qualifiedName.indexOf(':',
					0));
			if (namespaceURI.equals(" ")) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal namespace.");
			}
			if (qualifiedName.indexOf(':', 0) != qualifiedName.lastIndexOf(':')) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Malformed name " + qualifiedName);
			}
			if (elem.prefix.equals("xml")
					&& !"http://www.w3.org/XML/1998/namespace"
							.equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal prefix for URI " + qualifiedName + " - "
								+ namespaceURI);
			}
			if ("".equals(elem.prefix.trim())) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Prefix must be non-empty.");
			}
			if ((elem.prefix.length() > 0) && (elem.localName.length() == 0)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"Illegal tag name.");
			}
			if ("xmlns".equals(elem.localName)
					&& !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"xmlns is a reserved namespace.");
			}
			if ("xmlns".equals(elem.prefix)
					&& !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"xmlns is a reserved namespace.");
			}
			this.checkNameValidXML(elem.localName);
		} // end if
		else {
			elem.localName = qualifiedName.substring(qualifiedName.indexOf(':',
					0) + 1);
			elem.prefix = null;
			if ("xmlns".equals(elem.localName)
					&& !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
						"xmlns is a reserved namespace.");
			}
			this.checkNameValidXML(elem.localName);
		} // end else

		elem.name = qualifiedName;
		if (elem.userDataMap != null) {
			for (Iterator it = elem.userDataMap.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				Object[] o = (Object[]) entry.getValue();
				if (o[1] != null) {
					UserDataHandler udh = (UserDataHandler) o[1];
					udh.handle(UserDataHandler.NODE_RENAMED, key, o[0], elem,
							null);
				}
			}
		}
		return n;
	} // end renameNode()

	public final void setBuildStage() {
		this.isBuildStage = true;
	}

	public final void setBuildStageDone() {
		this.isBuildStage = false;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param docType
	 *            DOCUMENT ME!
	 */
	public final void setDocumentType(final DocumentType docType) {
		this.doctype = docType;
	} // end setDocumentType()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#setDocumentURI(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public final void setDocumentURI(final String documentURI) {
		this.documentURI = documentURI;
	} // end setDocumentURI()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param codec
	 *            DOCUMENT ME!
	 */
	public final void setEntityCodec(final CEntityCoDec codec) {
		this.codec = codec;
	} // end setEntityCodec()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 */
	public final void setHTMLDocument(final boolean isHTMLDocument) {
		this.isHTMLDocument = isHTMLDocument;
	} // end setHTMLDocument()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param impl
	 *            DOCUMENT ME!
	 */
	public final void setImplementation(final DOMImplementation impl) {
		this.impl = impl;
	} // end setImplementation()

	public final void setInputEncoding(String encoding) {
		this.encoding = encoding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#setStrictErrorChecking(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param strictErrorChecking
	 *            DOCUMENT ME!
	 */
	public final void setStrictErrorChecking(final boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	} // end setStrictErrorChecking()

	public final void setTextContent(String textContent) throws DOMException {
	}

	// FIXME TODO
	// protected Map xsd = new HashMap();

	public final void setXmlEncoding(String xmlEncoding) {
		this.xmlEncoding = xmlEncoding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#setXmlStandalone(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param xmlStandalone
	 *            DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final void setXmlStandalone(final boolean xmlStandalone)
			throws DOMException {
		this.xmlStandalone = xmlStandalone;
	} // end setXmlStandalone()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Document#setXmlVersion(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param xmlVersion
	 *            DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public final void setXmlVersion(final String xmlVersion)
			throws DOMException {
		if (!"1.0".equals(xmlVersion) && !"1.1".equals(xmlVersion)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"Only xml document version 1.0 or 1.1 are supported.");
		}
		this.xmlVersion = xmlVersion;
	} // end setXmlVersion()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final String toString() {
		CStringBuilder result = new CStringBuilder();
		if (this.hasChildNodes()) {
			NodeList nl = this.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				result.append(node.toString());
			} // end for
		} // end if

		return result.toString();
	} // end toString()
} // end ADocument
