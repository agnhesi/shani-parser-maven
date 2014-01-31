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
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CSaxParserFactory
	extends SAXParserFactory {
	/**
	 * Creates a new CSaxParserFactory object.
	 */
	public CSaxParserFactory() {
		super();
	} // end CSaxParserFactory()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.SAXParserFactory#setFeature(java.lang.String,
	 *      boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 *
	 * @throws SAXNotRecognizedException DOCUMENT ME!
	 * @throws SAXNotSupportedException DOCUMENT ME!
	 */
	public void setFeature(
		final String name,
		final boolean value)
		throws SAXNotRecognizedException, 
			SAXNotSupportedException {}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.SAXParserFactory#getFeature(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws SAXNotRecognizedException DOCUMENT ME!
	 * @throws SAXNotSupportedException DOCUMENT ME!
	 */
	public boolean getFeature(final String name)
		throws SAXNotRecognizedException, 
			SAXNotSupportedException {
		return false;
	} // end getFeature()

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.xml.parsers.SAXParserFactory#newSAXParser()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public SAXParser newSAXParser()
		throws SAXException {
		return new CShaniSaxParser();
	} // end newSAXParser()
} // end CSaxParserFactory
