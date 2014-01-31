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

import java.io.StringReader;
import java.net.URL;

import org.allcolor.html2.parser.CHTMLCollection;
import org.allcolor.html2.parser.CHTMLHeadElement;
import org.allcolor.html2.parser.CHTMLParser;
import org.allcolor.html2.parser.CHTMLTitleElement;
import org.allcolor.xml.parser.CShaniDomParser;
import org.allcolor.xml.parser.CStringBuilder;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.html2.HTMLCollection;
import org.w3c.dom.html2.HTMLDocument;
import org.w3c.dom.html2.HTMLElement;
import org.w3c.dom.html2.HTMLHeadElement;
import org.w3c.dom.html2.HTMLHtmlElement;
import org.w3c.dom.html2.HTMLTitleElement;

/**
 * DOCUMENT ME!
 * 
 * @author Quentin Anciaux
 */
public class CDom2HTMLDocument extends ADocument implements Document, HTMLDocument,
		DocumentCSS {
	/** DOCUMENT ME! */
	public final static long serialVersionUID = 3800321148234974275L;

	/** DOCUMENT ME! */
	protected HTMLElement body = null;

	/** DOCUMENT ME! */
	protected HTMLElement head = null;

	/**
	 * Creates a new CDom2HTMLDocument object.
	 */
	public CDom2HTMLDocument() {
		super("#document", null);
		this.prefix = null;
		this.localName = null;
		this.nameSpace = "  ";
		this.isDom1 = true;
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public CDom2HTMLDocument(final boolean isHTMLDocument, final String documentURI) {
		this(isHTMLDocument, documentURI, null);
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
	 * 
	 * @param isHTMLDocument
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 * @param cookie
	 *            DOCUMENT ME!
	 */
	public CDom2HTMLDocument(final boolean isHTMLDocument,
			final String documentURI, final String cookie) {
		this(isHTMLDocument, documentURI, cookie, null, null);
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
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
	public CDom2HTMLDocument(final boolean isHTMLDocument,
			final String documentURI, final String cookie, final String referrer) {
		this(isHTMLDocument, documentURI, cookie, referrer, null);
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
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
	public CDom2HTMLDocument(final boolean isHTMLDocument,
			final String documentURI, final String cookie,
			final String referrer, final CSSStyleSheet defaultStyleSheet) {
		this(null, documentURI, defaultStyleSheet);
		this.isHTMLDocument = isHTMLDocument;

		if (isHTMLDocument) {
			this.cookie = cookie;
			this.referrer = referrer;
			this.getHead();
		} // end if
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
	 * 
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public CDom2HTMLDocument(final String documentURI) {
		this(null, documentURI, null);
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
	 * 
	 * @param stylesheetPI
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 */
	public CDom2HTMLDocument(final String stylesheetPI, final String documentURI) {
		this(stylesheetPI, documentURI, null);
	} // end CDom2HTMLDocument()

	/**
	 * Creates a new CDom2HTMLDocument object.
	 * 
	 * @param stylesheetPI
	 *            DOCUMENT ME!
	 * @param documentURI
	 *            DOCUMENT ME!
	 * @param defaultStyleSheet
	 *            DOCUMENT ME!
	 */
	public CDom2HTMLDocument(final String stylesheetPI, final String documentURI,
			final CSSStyleSheet defaultStyleSheet) {
		this();
		this.stylesheetPI = stylesheetPI;
		this.defaultStyleSheet = defaultStyleSheet;
		this.documentURI = documentURI;
	} // end CDom2HTMLDocument()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#close()
	 */
	/**
	 * DOCUMENT ME!
	 */
	public void close() {
		if (this.documentOpen) {
			Document doc = new CShaniDomParser().parse(new StringReader(this.docBuffer
					.toString()));
			this.docBuffer = null;
			this.emptyDocument();
			this.isBuildStage = true;
			NodeList newNl = doc.getChildNodes();

			for (int i = 0; i < newNl.getLength(); i++) {
				Node node = newNl.item(i);
				this.adoptNode(node);
				this.appendChild(node);
			} // end for
			setBuildStageDone();
			this.documentOpen = false;
		} // end if
	} // end close()

	public CElement createElementInternal(final String tagName, int indexSep)
			throws DOMException {
		if (this.isHTMLDocument) {
			return CHTMLParser.createElement(this, tagName, indexSep);
		} // end if

		CElement elem = new CElement(tagName, this, indexSep);
		return elem;
	} // end createElement()

	private CHTMLCollection anchorsCol = null;
	
	private CNodeList createAnchorsCol() {
		CNodeList l = new CNodeList(true);
		NodeList list = getElementsByTagName("a");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			l.addItem(n);
		}
		return l;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getAnchors()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLCollection getAnchors() {
		if (this.isHTMLDocument) {
			if (anchorsCol != null) return anchorsCol;
			return anchorsCol = new CHTMLCollection(createAnchorsCol());
		}
		return new CHTMLCollection(this.getElementsByTagName("a"));
	} // end getAnchors()

	private CHTMLCollection appletsCol = null;
	
	private CNodeList createAppletsCol() {
		CNodeList l = new CNodeList(true);
		NodeList list = getElementsByTagName("object");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			if (n.getElementsByTagName("applet").getLength() > 0)
				l.addItem(n);
		}
		list = getElementsByTagName("applet");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			l.addItem(n);
		}
		return l;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getApplets()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLCollection getApplets() {
		if (this.isHTMLDocument) {
			if (appletsCol != null) return appletsCol;
			return appletsCol = new CHTMLCollection(createAppletsCol());
		}
		return new CHTMLCollection(new CNodeList(true));
	} // end getApplets()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getBody()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLElement getBody() {
		if (this.isHTMLDocument) {
			if (this.body == null) {
				NodeList nl = this.getElementsByTagName("body");

				if (nl.getLength() > 0) {
					this.body = (HTMLElement) nl.item(0);
				} // end if
				else {
					nl = this.getElementsByTagName("frameset");

					if (nl.getLength() > 0) {
						this.body = (HTMLElement) nl.item(0);
					} // end if
				} // end else
			} // end if

			return this.body;
		} // end if

		return null;
	} // end getBody()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getCookie()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getCookie() {
		return this.cookie == null ? "" : this.cookie;
	} // end getCookie()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getDomain()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getDomain() {
		try {
			URL url = new URL(getDocumentURI());
			String host = url.getHost();
			return host;
		}
		catch (Exception ignore){}
		return "";
	} // end getDomain()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getElementsByName(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param elementName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public NodeList getElementsByName(final String elementName) {
		if (this.isHTMLDocument) {
			CNodeList result = new CNodeList(true);
			NodeList inputnl = this.getElementsByTagName("input");
			NodeList selectnl = this.getElementsByTagName("select");
			NodeList textareanl = this.getElementsByTagName("textarea");
			NodeList mapnl = this.getElementsByTagName("map");

			for (int i = 0; i < inputnl.getLength(); i++) {
				Element elem = (Element) inputnl.item(i);

				if (elementName.equals(elem.getAttribute("name")) ||
					elementName.equals(elem.getAttribute("id"))) {
					result.addItem(elem);
				}
			} // end for

			for (int i = 0; i < selectnl.getLength(); i++) {
				Element elem = (Element) selectnl.item(i);

				if (elementName.equals(elem.getAttribute("name")) ||
					elementName.equals(elem.getAttribute("id"))) {
					result.addItem(elem);
				}
			} // end for

			for (int i = 0; i < textareanl.getLength(); i++) {
				Element elem = (Element) textareanl.item(i);

				if (elementName.equals(elem.getAttribute("name")) ||
					elementName.equals(elem.getAttribute("id"))) {
					result.addItem(elem);
				}
			} // end for

			for (int i = 0; i < mapnl.getLength(); i++) {
				Element elem = (Element) mapnl.item(i);

				if (elementName.equals(elem.getAttribute("name")) ||
					elementName.equals(elem.getAttribute("id"))) {
					result.addItem(elem);
				}
			} // end for

			return result;
		} // end if

		return new CNodeList(true);
	} // end getElementsByName()

	private CHTMLCollection formsCol = null;
	
	private CNodeList createFormsCol() {
		CNodeList l = new CNodeList(true);
		NodeList list = getElementsByTagName("form");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			l.addItem(n);
		}
		return l;
	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getForms()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLCollection getForms() {
		if (this.isHTMLDocument) {
			if (formsCol != null) return formsCol;
			return formsCol = new CHTMLCollection(createFormsCol());
		}
		return new CHTMLCollection(new CNodeList(true));
	} // end getForms()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLElement getHead() {
		if (this.isHTMLDocument) {
			if (this.head == null) {
				NodeList nl = this.getElementsByTagName("head");

				if (nl.getLength() > 0) {
					this.head = (HTMLElement) nl.item(0);
				} // end if
				else {
					this.head = new CHTMLHeadElement(this);
					nl = this.getElementsByTagName("html");

					if (nl.getLength() > 0) {
						HTMLHtmlElement html = (HTMLHtmlElement) nl.item(0);
						html.appendChild(this.head);
					} // end if
				} // end else
			} // end if

			return this.head;
		} // end if

		return null;
	} // end getHead()

	private CHTMLCollection imagesCol = null;
	
	private CNodeList createImagesCol() {
		CNodeList l = new CNodeList(true);
		NodeList list = getElementsByTagName("img");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			l.addItem(n);
		}
		return l;
	}		
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getImages()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLCollection getImages() {
		if (this.isHTMLDocument) {
			if (imagesCol != null) return imagesCol;
			return imagesCol = new CHTMLCollection(this.getElementsByTagName("img"));
		}
		return new CHTMLCollection(new CNodeList(true));
	} // end getImages()

	private CHTMLCollection linksCol = null;
	
	public final Node appendChild(Node newChild) throws DOMException {
		Node n = super.appendChild(newChild);
		if (this.isHTMLDocument) {
			if (linksCol != null) {
				linksCol.setNl(createLinksCol());
			}
			if (anchorsCol != null) {
				anchorsCol.setNl(createAnchorsCol());
			}
			if (appletsCol != null) {
				appletsCol.setNl(createAppletsCol());
			}
			if (formsCol != null) {
				formsCol.setNl(createFormsCol());
			}
			if (imagesCol != null) {
				imagesCol.setNl(createImagesCol());
			}
		}
		return n;
	}
	
	public final Node removeChild(Node oldChild) throws DOMException {
		Node n = super.removeChild(oldChild);
		if (this.isHTMLDocument) {
			if (linksCol != null) {
				linksCol.setNl(createLinksCol());
			}
			if (anchorsCol != null) {
				anchorsCol.setNl(createAnchorsCol());
			}
			if (appletsCol != null) {
				appletsCol.setNl(createAppletsCol());
			}
			if (formsCol != null) {
				formsCol.setNl(createFormsCol());
			}
			if (imagesCol != null) {
				imagesCol.setNl(createImagesCol());
			}
		}
		return n;
	}
	
	private CNodeList createLinksCol() {
		CNodeList l = new CNodeList(true);
		NodeList list = getElementsByTagName("area");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			if (!"".equals(n.getAttribute("href"))) {
				l.addItem(n);
			}
		}
		list = getElementsByTagName("a");
		for (int i=0;i<list.getLength();i++) {
			Element n = (Element)list.item(i);
			if (!"".equals(n.getAttribute("href"))) {
				l.addItem(n);
			}
		}
		return l;
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getLinks()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public HTMLCollection getLinks() {
		if (this.isHTMLDocument) {
			if (linksCol != null) return linksCol;
			return linksCol = new CHTMLCollection(createLinksCol());
		}
		return new CHTMLCollection(new CNodeList(true));
	} // end getLinks()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getReferrer()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getReferrer() {
		if (this.isHTMLDocument) {
			return this.referrer == null ? "" : this.referrer;
		}

		return "";
	} // end getReferrer()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getTitle()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getTitle() {
		if (this.isHTMLDocument) {
			NodeList nl = this.getElementsByTagName("title");

			if (nl.getLength() > 0) {
				HTMLTitleElement title = (HTMLTitleElement) nl.item(0);

				return title.getText();
			} // end if

			return "";
		} // end if

		return null;
	} // end getTitle()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#getURL()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getURL() {
		return this.documentURI;
	} // end getURL()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#open()
	 */
	/**
	 * DOCUMENT ME!
	 */
	public void open() {
		if (!this.documentOpen) {
			this.docBuffer = new CStringBuilder();
			this.documentOpen = true;
		} // end if
	} // end open()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#setBody(org.w3c.dom.html.HTMLElement)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param body
	 *            DOCUMENT ME!
	 */
	public void setBody(final HTMLElement body) {
		if (this.isHTMLDocument) {
			if (this.body != null) {
				this.body.getParentNode().insertBefore(body, this.body);
				this.body.getParentNode().removeChild(this.body);
			} // end if

			this.body = body;
		} // end if
	} // end setBody()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#setCookie(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param cookie
	 *            DOCUMENT ME!
	 */
	public void setCookie(final String cookie) {
		if (this.isHTMLDocument) {
			this.cookie = cookie;
		} // end if
	} // end setCookie()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#setTitle(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param title
	 *            DOCUMENT ME!
	 */
	public void setTitle(final String title) {
		if (this.isHTMLDocument) {
			NodeList nl = this.getElementsByTagName("title");
			HTMLTitleElement nTitle = null;

			if (nl.getLength() > 0) {
				nTitle = (HTMLTitleElement) nl.item(0);
				nTitle.setTitle(title);
			} // end if
			else {
				nTitle = new CHTMLTitleElement(this);
				nTitle.setTitle(title);
				nl = this.getElementsByTagName("head");

				HTMLHeadElement head = null;

				if (nl.getLength() > 0) {
					head = (HTMLHeadElement) nl.item(0);

					if (head.getChildNodes().getLength() > 0) {
						head.insertBefore(nTitle, head.getFirstChild());
					} // end if
					else {
						head.appendChild(nTitle);
					} // end else
				} // end if
			} // end else
		} // end if
	} // end setTitle()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#write(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param text
	 *            DOCUMENT ME!
	 */
	public void write(final String text) {
		if (this.documentOpen) {
			this.docBuffer.append(text);
		} // end if
	} // end write()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLDocument#writeln(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param text
	 *            DOCUMENT ME!
	 */
	public void writeln(final String text) {
		if (this.documentOpen) {
			this.docBuffer.append(text);
			this.docBuffer.append("\n");
		} // end if
	} // end writeln()
} // end ADocument
