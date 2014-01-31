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
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CDocumentBuilderFactory
	extends DocumentBuilderFactory {
	/** DOCUMENT ME! */
	public final static String XML_PARSER = "org.allcolor.xml.parser";

	/** DOCUMENT ME! */
	public final static String HTML_PARSER = "org.allcolor.html.parser";

	/** DOCUMENT ME! */
	public final static String AUTO_DOCTYPE = "org.allcolor.doctype.auto";

	/** DOCUMENT ME! */
	public final static String DISALLOW_DOCTYPE_DECL = "http://apache.org/xml/features/disallow-doctype-decl";
	
	/** DOCUMENT ME! */
	public final static String CSS_PARSER = "org.allcolor.css.parser";

	/** DOCUMENT ME! */
	public final static String DTD_PARSER = "org.allcolor.dtd.parser";

	/** DOCUMENT ME! */
	public final static String SCHEMA_PARSER = "org.allcolor.xml.parser.schema";

	/** DOCUMENT ME! */
	boolean expandEntityRef = true;

	/** DOCUMENT ME! */
	boolean ignoringElementContentWhitespace = false;

	/** DOCUMENT ME! */
	private Map userProp = new HashMap();

	/**
	 * DOCUMENT ME!
	 */
	private boolean XIncludeAware = false;

	/** DOCUMENT ME! */
	private boolean coalescing = false;

	/** DOCUMENT ME! */
	private boolean ignoringComments = false;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 *
	 * @throws IllegalArgumentException DOCUMENT ME!
	 */
	public void setAttribute(
		final String name,
		final Object value)
		throws IllegalArgumentException {
		userProp.put(name, value);
	} // end setAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#getAttribute(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IllegalArgumentException DOCUMENT ME!
	 */
	public Object getAttribute(final String name)
		throws IllegalArgumentException {
		return userProp.get(name);
	} // end getAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setCoalescing(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param coalescing DOCUMENT ME!
	 */
	public void setCoalescing(final boolean coalescing) {
		this.coalescing = coalescing;
	} // end setCoalescing()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isCoalescing()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isCoalescing() {
		return coalescing;
	} // end isCoalescing()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setExpandEntityReferences(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param expandEntityRef DOCUMENT ME!
	 */
	public void setExpandEntityReferences(
		final boolean expandEntityRef) {
		this.expandEntityRef = expandEntityRef;
	} // end setExpandEntityReferences()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isExpandEntityReferences()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isExpandEntityReferences() {
		return expandEntityRef;
	} // end isExpandEntityReferences()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setFeature(java.lang.String,
	 *      boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 */
	public void setFeature(
		final String name,
		final boolean value) {
		setAttribute(name, ""+value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#getFeature(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws ParserConfigurationException DOCUMENT ME!
	 */
	public boolean getFeature(final String name)
		throws ParserConfigurationException {
		return getAttribute(name) != null;
	} // end getFeature()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setIgnoringComments(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param ignoreComments DOCUMENT ME!
	 */
	public void setIgnoringComments(final boolean ignoreComments) {
		this.ignoringComments = ignoreComments;
	} // end setIgnoringComments()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isIgnoringComments()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isIgnoringComments() {
		return ignoringComments;
	} // end isIgnoringComments()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setIgnoringElementContentWhitespace(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param whitespace DOCUMENT ME!
	 */
	public void setIgnoringElementContentWhitespace(
		final boolean whitespace) {
		this.ignoringElementContentWhitespace = whitespace;
	} // end setIgnoringElementContentWhitespace()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isIgnoringElementContentWhitespace()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isIgnoringElementContentWhitespace() {
		return ignoringElementContentWhitespace;
	} // end isIgnoringElementContentWhitespace()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setNamespaceAware(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param awareness DOCUMENT ME!
	 */
	public void setNamespaceAware(final boolean awareness) {}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isNamespaceAware()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isNamespaceAware() {
		return true;
	} // end isNamespaceAware()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setValidating(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param validating DOCUMENT ME!
	 */
	public void setValidating(final boolean validating) {}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isValidating()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isValidating() {
		return false;
	} // end isValidating()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#setXIncludeAware(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param state DOCUMENT ME!
	 */
	public void setXIncludeAware(final boolean state) {
		XIncludeAware = state;
	} // end setXIncludeAware()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#isXIncludeAware()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isXIncludeAware() {
		return XIncludeAware;
	} // end isXIncludeAware()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.DocumentBuilderFactory#newDocumentBuilder()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public DocumentBuilder newDocumentBuilder() {
		if (userProp.get(HTML_PARSER) != null) {
			return new CShaniDomParser(true);
		}

		CShaniDomParser parser = new CShaniDomParser();
		parser.setExpandEntity(isExpandEntityReferences());
		parser.setCoalescing(isCoalescing());
		parser.setIgnoringElementContentWhitespace(isIgnoringElementContentWhitespace());
		parser.setIgnoringComments(isIgnoringComments());
		parser.setXIncludeAware(isXIncludeAware());
		if (userProp.get(AUTO_DOCTYPE) != null) {
			parser.setAutodoctype(true);
		}
		if (userProp.get(DISALLOW_DOCTYPE_DECL) != null) {
			parser.setIgnoreDTD(true);
		}
		return parser;
	} // end newDocumentBuilder()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws ParserConfigurationException DOCUMENT ME!
	 */
	public static DocumentBuilderFactory newParser()
		throws ParserConfigurationException {
		return newParser(
			"org.allcolor.xml.parser.CDocumentBuilderFactory");
	} // end newParser()

	/**
	 * DOCUMENT ME!
	 *
	 * @param builderClassName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws ParserConfigurationException DOCUMENT ME!
	 */
	public static DocumentBuilderFactory newParser(
		final String builderClassName)
		throws ParserConfigurationException {
		try {
			DocumentBuilderFactory factory = (DocumentBuilderFactory) Class.forName(builderClassName)
																			   .newInstance();

			return factory;
		} // end try
		catch (final Exception ignore) {
			ignore.printStackTrace();
			throw new ParserConfigurationException(
				"A problem occurs while instantiating builder : " +
				builderClassName + "\n" + ignore.getMessage());
		} // end catch
	} // end newParser()
} // end CDocumentBuilderFactory
