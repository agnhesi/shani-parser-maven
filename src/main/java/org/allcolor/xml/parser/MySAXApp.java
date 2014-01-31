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
import org.allcolor.utils.CCmdLineOptions;

import org.allcolor.xml.parser.dom.CEntityCoDec;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.17 $
 */
public class MySAXApp
	extends DefaultHandler2
	implements LexicalHandler {
	/**
	 * Creates a new MySAXApp object.
	 */
	public MySAXApp() {
		super();
	} // end MySAXApp()

	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public static void main(final String args[])
		throws Exception {
		if (CCmdLineOptions.isOptionPresent("-url", args)) {
			System.setProperty("org.xml.sax.driver",
				"org.allcolor.xml.parser.CShaniSaxParser");

			XMLReader xr	  = XMLReaderFactory.createXMLReader();
			MySAXApp  handler = new MySAXApp();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			xr.parse(new InputSource(CCmdLineOptions.getOptionValue(
						"-url",
						args)));
		} // end if
		else {
			printUsage();
		} // end else
	} // end main()

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
		System.out.print(CEntityCoDec.encode(
				new String(ch, start, length)));
	} // end characters()

	/**
	 * DOCUMENT ME!
	 *
	 * @param ch DOCUMENT ME!
	 * @param start DOCUMENT ME!
	 * @param length DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void comment(
		final char ch[],
		final int start,
		final int length)
		throws SAXException {
		System.out.print("<!--" + new String(ch, start, length) +
			"-->");
	} // end comment()

	/**
	 * DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void endCDATA()
		throws SAXException {
		// TODO Auto-generated method stub
	} // end endCDATA()

	/**
	 * DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void endDTD()
		throws SAXException {
		// TODO Auto-generated method stub
	} // end endDTD()

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
	 * @param name DOCUMENT ME!
	 * @param qName DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void endElement(
		final String uri,
		final String name,
		final String qName)
		throws SAXException {
		if ("".equals(uri)) {
			System.out.print("</");
			System.out.print(qName);
			System.out.print(">");
		} // end if
		else {
			System.out.print("</");
			System.out.print(name);
			System.out.print(">");
		} // end else
	} // end endElement()

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void endEntity(final String name)
		throws SAXException {
		// TODO Auto-generated method stub
	} // end endEntity()

	/**
	 * DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void startCDATA()
		throws SAXException {
		// TODO Auto-generated method stub
	} // end startCDATA()

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param publicId DOCUMENT ME!
	 * @param systemId DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void startDTD(
		final String name,
		final String publicId,
		final String systemId)
		throws SAXException {
		// TODO Auto-generated method stub
	} // end startDTD()

	////////////////////////////////////////////////////////////////////
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
	 * @param name DOCUMENT ME!
	 * @param qName DOCUMENT ME!
	 * @param atts DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void startElement(
		final String uri,
		final String name,
		final String qName,
		final Attributes atts)
		throws SAXException {
		if ("".equals(uri)) {
			System.out.print("<");
			System.out.print(qName);
			System.out.print(" ");

			if (atts != null) {
				for (int i = 0; i < atts.getLength(); i++) {
					String attName = atts.getQName(i);
					String value = atts.getValue(i);
					System.out.print(attName);
					System.out.print("=\"");
					System.out.print(CEntityCoDec.encode(value, true));
					System.out.print("\" ");
				} // end for
			} // end if

			System.out.print(">");
		} // end if
		else {
			System.out.print("<");
			System.out.print(name);
			System.out.print(" xmlns=\"");
			System.out.print(uri);
			System.out.print("\" ");

			if (atts != null) {
				for (int i = 0; i < atts.getLength(); i++) {
					String attName = atts.getQName(i);
					String value = atts.getValue(i);
					System.out.print(attName);
					System.out.print("=\"");
					System.out.print(value);
					System.out.print("\" ");
				} // end for
			} // end if

			System.out.print(">");
		} // end else
	} // end startElement()

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @throws SAXException DOCUMENT ME!
	 */
	public void startEntity(final String name)
		throws SAXException {
		// TODO Auto-generated method stub
	} // end startEntity()

	/**
	 * DOCUMENT ME!
	 */
	private static void printUsage() {
		System.out.println("Usage :");
		System.out.println("\t$MySAXApp -url SOME_URL");
	} // end printUsage()
} // end MySAXApp
