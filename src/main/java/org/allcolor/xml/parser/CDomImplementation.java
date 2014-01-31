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
import org.allcolor.dtd.parser.CDocType;

import org.allcolor.xml.parser.dom.ANode;
import org.allcolor.xml.parser.dom.ADocument;
import org.allcolor.xml.parser.dom.CDom1HTMLDocument;
import org.allcolor.xml.parser.dom.CDom2HTMLDocument;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CDomImplementation
	implements DOMImplementation,
		Serializable {
	static final long serialVersionUID = 3584142368443099970L;

	/**
	 * DOCUMENT ME!
	 */
	public static final String INVALID_CHAR = "{}~'!@#$%^&*()+=[]\\/;`<>, \"";

	/** DOCUMENT ME! */
	public static final String PARSER_HTML = "http://www.allcolor.org/shaniparser/html";

	/** DOCUMENT ME! */
	private final Map features;

	private Map reqFeatures;
	
	public void setReqFeatures(String reqFeatures) {
		this.reqFeatures = new HashMap();
		CStringTokenizer tokenizer = new CStringTokenizer(reqFeatures,
				" ", false);
		while (tokenizer.hasMoreTokens()) {
			String name    = tokenizer.nextToken();
			String version = "";

			if (tokenizer.hasMoreTokens()) {
				version = tokenizer.nextToken();
			}
			this.reqFeatures.put(name+version,this);
		} // end while
	}

	
	
	public CDomImplementation() {
		this(new HashMap());
	}
	/**
	 * Creates a new CDomImplementation object.
	 */
	public CDomImplementation(final Map features) {
		this.features = features;
		features.put("xml1.0", this);
		features.put("xml2.0", this);
		features.put("xml3.0", this);
		features.put("xml", this);
		features.put("xmlnull", this);
		features.put("core2.0", this);
		features.put("core3.0", this);
		features.put("core", this);
		features.put("corenull", this);
		features.put("html1.0", this);
		features.put("html2.0", this);
		features.put("xhtml2.0", this);
		features.put("html", this);
		features.put("htmlnull", this);
		features.put("xhtml", this);
		features.put("xhtmlnull", this);
		features.put("css2.0", this);
		features.put("css", this);
		features.put("cssnull", this);
		features.put("css22.0", this);
		features.put("css2", this);
		features.put("css2null", this);
		features.put("stylesheets2.0", this);
		features.put("stylesheets", this);
		features.put("stylesheetsnull", this);
		
	} // end CDomImplementation()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DOMImplementation#createDocument(java.lang.String,
	 *      java.lang.String, org.w3c.dom.DocumentType)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param namespaceURI DOCUMENT ME!
	 * @param qualifiedName DOCUMENT ME!
	 * @param doctype DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final Document createDocument(
		final String	   namespaceURI,
		final String	   qualifiedName,
		final DocumentType doctype)
		throws DOMException {
		if ((qualifiedName == null) || "".equals(qualifiedName)) {
			if (!((namespaceURI == null) &&
					(qualifiedName == null) &&
					(doctype == null))) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
					"Invalid char : null qualified name.");
			}
		} // end if

		if ((qualifiedName != null) &&
				(qualifiedName.indexOf(":") != -1)) {
			if ((namespaceURI == null) ||
					(qualifiedName.indexOf(":") != qualifiedName.lastIndexOf(
						":"))) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
					"Malformed name " + qualifiedName);
			}

			String prefix = qualifiedName.substring(0,
					qualifiedName.indexOf(":"));

			if (prefix.equals("xml") &&
					!"http://www.w3.org/XML/1998/namespace".equals(
						namespaceURI)) {
				throw new DOMException(DOMException.NAMESPACE_ERR,
					"Illegal prefix for URI " + qualifiedName + " - " +
					namespaceURI);
			} // end if

			String name    = qualifiedName.substring(qualifiedName.indexOf(
						":") + 1);
			char   array[] = name.toCharArray();

			for (int i = 0; i < array.length; i++) {
				if (INVALID_CHAR.indexOf(array[i]) != -1) {
					throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
						"Invalid char : " + array[i]);
				}
			} // end for
		} // end if
		else if ((qualifiedName != null) &&
				(qualifiedName.indexOf(":") == -1)) {
			String name    = qualifiedName;
			char   array[] = name.toCharArray();

			for (int i = 0; i < array.length; i++) {
				if (INVALID_CHAR.indexOf(array[i]) != -1) {
					throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
						"Invalid char : " + array[i]);
				}
			} // end for
		} // end else if

		if ((doctype != null) && (doctype.getOwnerDocument() != null)) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
				"Doctype already used.");
		} // end if

		ADocument doc = createNewDocument(doctype);

		if (doctype != null) {
			((ANode) doctype).setOwnerDocument(doc);
		}

		if (qualifiedName != null) {
			if (namespaceURI != null) {
				doc.appendChild(doc.createElementNS(namespaceURI,
						qualifiedName));
				doc.hasNS = true;
			} // end if
			else {
				doc.appendChild(doc.createElement(qualifiedName));
				doc.hasNS = true;
			} // end else
		} // end if

		doc.setDocumentType(doctype);
		doc.setBuildStageDone();
		return doc;
	} // end createDocument()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DOMImplementation#createDocumentType(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param qualifiedName DOCUMENT ME!
	 * @param publicId DOCUMENT ME!
	 * @param systemId DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final DocumentType createDocumentType(
		final String qualifiedName,
		final String publicId,
		final String systemId)
		throws DOMException {
		if ((qualifiedName == null) || "".equals(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
				"Invalid char : qualified name is null.");
		} // end if

		if (qualifiedName.indexOf(":") != qualifiedName.lastIndexOf(":")) {
			throw new DOMException(DOMException.NAMESPACE_ERR,
				"Malformed name " + qualifiedName);
		}

		if (qualifiedName.indexOf(":") != -1) {
			String name    = qualifiedName.substring(qualifiedName.indexOf(
						":") + 1);
			char   array[] = name.toCharArray();

			for (int i = 0; i < array.length; i++) {
				if (INVALID_CHAR.indexOf(array[i]) != -1) {
					throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
						"Invalid char : " + array[i]);
				}
			} // end for
		} // end if
		else if (qualifiedName.indexOf(":") == -1) {
			String name    = qualifiedName;
			char   array[] = name.toCharArray();

			for (int i = 0; i < array.length; i++) {
				if (INVALID_CHAR.indexOf(array[i]) != -1) {
					throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
						"Invalid char : " + array[i]);
				}
			} // end for
		} // end else if

		return new CDocType("!DOCTYPE " + qualifiedName + " " +
			((publicId != null)
			? ("PUBLIC \"" + publicId + "\" ")
			: "") +
			((systemId != null)
			? ("SYSTEM \"" + systemId + "\"")
			: ""), qualifiedName, publicId, systemId, null);
	} // end createDocumentType()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DOMImplementation#hasFeature(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param feature DOCUMENT ME!
	 * @param version DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final boolean hasFeature(
		final String feature,
		final String version) {
		if (feature.startsWith("+"))
			return features.containsKey(feature.toLowerCase().substring(1)+version);
		return features.containsKey(feature.toLowerCase() + version);
	} // end hasFeature()

	public final boolean hasRequestedFeature(
			final String feature,
			final String version) {
			if (reqFeatures == null) return false;
			if (feature.startsWith("+"))
				return reqFeatures.containsKey(feature.toLowerCase().substring(1)+version);
			return reqFeatures.containsKey(feature.toLowerCase() + version);
		} // end hasFeature()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DOMImplementation#getFeature(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param feature DOCUMENT ME!
	 * @param version DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Object getFeature(
		final String feature,
		final String version) {
		return features.get(feature.toLowerCase() + version);
	} // end getFeature()

	/**
	 * DOCUMENT ME!
	 *
	 * @param doctype DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private final ADocument createNewDocument(
		final DocumentType doctype) {
		if ((hasFeature(PARSER_HTML, "")) ||
				((doctype != null) && (doctype.getPublicId() != null) &&
				((doctype.getPublicId().indexOf("XHTML") != -1) ||
				((doctype.getPublicId().indexOf("HTML") != -1) &&
				(doctype.getPublicId().indexOf("//W3C//DTD ") != -1))))) {
			ADocument doc = null;
			if ((hasRequestedFeature("HTML", "1.0")) || 
				 (hasRequestedFeature("HTML", null))) {
				doc = new CDom1HTMLDocument(true, null);
			} else {
				doc = new CDom2HTMLDocument(true, null);
			}
			doc.setImplementation(this);
			if (doctype != null) {
				doc.appendChild(doctype);
			}
			return doc;
		} // end if

		ADocument doc = null;
		if ((hasRequestedFeature("HTML", "1.0")) || 
			 (hasRequestedFeature("HTML", null))) {
			doc = new CDom1HTMLDocument(true, null);
		} else {
			doc = new CDom2HTMLDocument();
		}
		doc.setImplementation(this);
		if (doctype != null) {
			doc.appendChild(doctype);
		}
		return doc;
	} // end createNewDocument()
} // end CDomImplementation
