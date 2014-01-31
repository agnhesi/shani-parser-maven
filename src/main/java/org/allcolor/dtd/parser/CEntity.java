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
import org.allcolor.xml.parser.CShaniDomParser;
import org.allcolor.xml.parser.dom.ADocument;
import org.allcolor.xml.parser.dom.CElement;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

//import org.xml.sax.ext.EntityResolver2;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CEntity
	extends CElement
	implements Entity {
	/** DOCUMENT ME! */
	private static final long serialVersionUID = -8485506528975073023L;

	/** DOCUMENT ME! */
	private ADocument docValue;

	/** DOCUMENT ME! */
	private String delimiter;

	/** DOCUMENT ME! */
	private String in;

	/** DOCUMENT ME! */
	private String inputEncoding;

	/** DOCUMENT ME! */
	private String ndata;

	/** DOCUMENT ME! */
	private String publicId;

	/** DOCUMENT ME! */
	private String systemId;

	/** DOCUMENT ME! */
	private String value;

	/** DOCUMENT ME! */
	private String xmlEncoding;

	/** DOCUMENT ME! */
	private String xmlVersion;

	/**
	 * Creates a new CEntity object.
	 *
	 * @param in DOCUMENT ME!
	 * @param name DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 * @param publicId DOCUMENT ME!
	 * @param systemId DOCUMENT ME!
	 * @param dtdInternal DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CEntity(
		final String    in,
		final String    name,
		final String    value,
		final String    publicId,
		final String    systemId,
		final boolean   dtdInternal,
		final ADocument ownerDocument) {
		this(in, name, null, value, publicId, systemId, dtdInternal,
			ownerDocument);
	} // end CEntity()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param name
	 * @param ndata DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 * @param publicId DOCUMENT ME!
	 * @param systemId DOCUMENT ME!
	 * @param dtdInternal DOCUMENT ME!
	 * @param ownerDocument
	 */
	public CEntity(
		final String    in,
		final String    name,
		final String    ndata,
		final String    value,
		final String    publicId,
		final String    systemId,
		final boolean   dtdInternal,
		final ADocument ownerDocument) {
		super(name, ownerDocument);
		this.in			    = in;
		this.value		    = value;
		this.publicId	    = publicId;
		this.systemId	    = systemId;
		this.delimiter	    = dtdInternal
			? "%"
			: "&";
		this.ndata		    = ndata;
		if (ndata != null) {
			this.isReadOnly     = true;
			this.value		    = null;

			return;
		} // end if

		if ((value == null) && (systemId != null)) {
			try {
				EntityResolver resolver = CDTDParser.getEntityResolver();

				if (resolver != null) {
					InputSource source = resolver.resolveEntity(publicId,
							systemId);

					if (source != null) {
						InputStream i = source.getByteStream();

						if (i != null) {
							this.value = CDTDParser.load(new BufferedReader(
										new InputStreamReader(i)));
						} // end if
						else {
							Reader r = source.getCharacterStream();

							if (r != null) {
								this.value = CDTDParser.load(new BufferedReader(
											r));
							} // end if
						} // end else
					} // end if
					else {
						URL u = new URL(systemId);
						this.value = CDTDParser.load(u);
					} // end else
				} // end if
				else {
					URL u = new URL(systemId);
					this.value = CDTDParser.load(u);
				} // end else

				List doctypeList   = new ArrayList(0);
				Map  knownEntities = new HashMap(0);
				Map  knownElement  = new HashMap(0);

				// Guessing contents 
				CDTDParser.parse(this.value, this.systemId,
					doctypeList, knownEntities, knownElement,
					ownerDocument);

				if (knownEntities.size() > 0) {
					StringBuffer result = new StringBuffer();

					for (Iterator it = knownEntities.entrySet()
														.iterator();
							it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						result.append(entry.getValue().toString());
					} // end for

					this.value = result.toString();
				} // end if
				else {
					CShaniDomParser parser = new CShaniDomParser();

					if (this.value.startsWith("http://") ||
							this.value.startsWith("https://") ||
							this.value.startsWith("ftp://")) {
						this.docValue = (ADocument) parser.parse(this.value);
					} // end if
					else {
						this.docValue = (ADocument) parser.parse(new StringReader(
									this.value));
					} // end else
					this.docValue.setDocumentURI(this.systemId);
					inputEncoding     = docValue.getInputEncoding();
					xmlEncoding		  = docValue.getXmlEncoding();
					xmlVersion		  = docValue._getXmlVersion();

					if (inputEncoding == null && xmlEncoding != null) {
						inputEncoding = xmlEncoding.toUpperCase();
					} else
					if (inputEncoding == null) {
						inputEncoding = "UTF-8";
					} // end if

					if (docValue.getDocumentElement().getNodeType() != Node.ELEMENT_NODE) {
						this.docValue     = null;
					} // end if
					else {
						this.value = null;
					} // end else
				} // end else
			} // end try
			catch (final Exception ignore) {
				this.value = delimiter + name + ";";
			} // end catch
		} // end if
		else {
			CShaniDomParser parser = new CShaniDomParser();
			if (this.value.startsWith("http://") ||
					this.value.startsWith("https://") ||
					this.value.startsWith("ftp://")) {
				this.docValue = (ADocument) parser.parse(this.value);
			} // end if
			else {
				this.docValue = (ADocument) parser.parse(new StringReader(
							this.value));
			} // end else
			this.docValue.setDocumentURI(this.systemId);

			if ((docValue.getDocumentElement() == null) ||
					(docValue.getDocumentElement().getNodeType() != Node.ELEMENT_NODE)) {
				this.docValue = null;
			} // end if
			else {
				this.value = null;
			} // end else
		} // end else

		if (this.docValue != null) {
			NodeList child = this.docValue.getChildNodes();
			for (int i = 0; i < child.getLength(); i++) {
				appendChild(ownerDocument.importNode(child.item(i)
															  .cloneNode(true),
						true));
			} // end for
		} // end if
		else {
			appendChild(ownerDocument.createTextNode(this.value));
		} // end else
		this.isReadOnly = true;
	} // end CEntity()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public NamedNodeMap getAttributes() {
		return null;
	} // end getAttributes()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Document getDOMValue() {
		return docValue;
	} // end getDOMValue()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getDelimiter() {
		return delimiter;
	} // end getDelimiter()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getEntityName() {
		return name;
	} // end getEntityName()

	/* (non-Javadoc)
	 * @see org.w3c.dom.Entity#getInputEncoding()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getInputEncoding() {
		return inputEncoding;
	} // end getInputEncoding()

	/* (non-Javadoc)
	 * @see org.w3c.dom.Node#getNodeType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getNodeType() {
		return Node.ENTITY_NODE;
	} // end getNodeType()

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
		return null;
	} // end getNodeValue()

	/* (non-Javadoc)
	 * @see org.w3c.dom.Entity#getNotationName()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getNotationName() {
		return ndata;
	} // end getNotationName()

	/**
	 * DOCUMENT ME!
	 *
	 * @param publicId DOCUMENT ME!
	 */
	public void setPublicId(final String publicId) {
		this.publicId = publicId;
	} // end setPublicId()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPublicId() {
		return publicId;
	} // end getPublicId()

	/**
	 * DOCUMENT ME!
	 *
	 * @param systemId DOCUMENT ME!
	 */
	public void setSystemId(final String systemId) {
		this.systemId = systemId;
	} // end setSystemId()

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
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String getTextContent()
		throws DOMException {
		return getValue();
	} // end getTextContent()

	/**
	 * DOCUMENT ME!
	 *
	 * @param nodeValue DOCUMENT ME!
	 */
	public void setValue(final String nodeValue) {
		this.value = nodeValue;
	} // end setValue()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getValue() {
		return value;
	} // end getValue()

	/* (non-Javadoc)
	 * @see org.w3c.dom.Entity#getXmlEncoding()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getXmlEncoding() {
		return xmlEncoding;
	} // end getXmlEncoding()

	/* (non-Javadoc)
	 * @see org.w3c.dom.Entity#getXmlVersion()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getXmlVersion() {
		return xmlVersion;
	} // end getXmlVersion()

	/**
	 * DOCUMENT ME!
	 *
	 * @param ownerDocument DOCUMENT ME!
	 */
	public void resetOwner(final ADocument ownerDocument) {
		this.ownerDocument = ownerDocument;
	} // end resetOwner()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.allcolor.xml.parser.dom.INode#toString()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		return "<" + in + ">\n";
	} // end toString()
} // end CEntity
