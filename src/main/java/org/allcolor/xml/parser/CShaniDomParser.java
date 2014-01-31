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

import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * DOCUMENT ME!
 * 
 * @author Quentin Anciaux Dedicated to my daughter Shani born the 31st August
 *         2004 at 9.23 am.
 */
public class CShaniDomParser extends DocumentBuilder {
	/** DOCUMENT ME! */
	public final static String CASE_INSENSITIVE = "http://www.allcolor.org/xml/caseinsensitive/";

	/** DOCUMENT ME! */
	public final static String STRICT_MODE = "http://www.allcolor.org/xml/strictmode/";

	/**
	 * DOCUMENT ME!
	 * 
	 * @param args
	 *            DOCUMENT ME!
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 */
	public static void main(final String args[]) throws Exception {
		CShaniDomParser.testParser();
	} // end main()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param factory
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 */
	public static long testIntegParser(final DocumentBuilderFactory factory)
			throws Exception {
		long domParserTime = 0;
		System.out.println("Starting integrated parser : "
				+ factory.newDocumentBuilder().getClass());

		{
			Random rand = new Random(System.currentTimeMillis());
			long beginTime = System.currentTimeMillis();
			factory.setNamespaceAware(true);
			factory.setValidating(false);
			factory.setExpandEntityReferences(true);
			factory.setCoalescing(false);
			factory.setIgnoringComments(false);
			factory.setIgnoringElementContentWhitespace(false);

			DocumentBuilder build = factory.newDocumentBuilder();
			for (int i = 0; i < 2500; i++) {
				CShaniDomParser.traverseNode(build.parse(new InputSource(
						new StringReader(
								"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
										+ "<!-- this is a comment -->\n"
										+ "<!-- this is another comment -->\n"
										+ "<test xmlns:html=\"http://toto\">\n"
										+ "	<a id=\"toto\">hello"
										+ rand.nextDouble() + "</a>\n"
										+ "	<!-- and one more comment -->\n"
										+ "	<html:div>toto</html:div>\n"
										+ "	<b>totot <u>li&amp;li</u></b>\n"
										+ "	<ul><li>lol</li></ul>\n"
										+ "</test>"))));
			} // end for

			long endTime = System.currentTimeMillis();
			domParserTime = endTime - beginTime;
		}

		return domParserTime;
	} // end testIntegParser()

	/**
	 * DOCUMENT ME!
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 */
	public static void testParser() throws Exception {
		DocumentBuilderFactory internalFactory = DocumentBuilderFactory
				.newInstance();
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.allcolor.xml.parser.CDocumentBuilderFactory");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		long domParserTime = 0;
		long integratedParser = 0;

		{
			factory.setAttribute(CDocumentBuilderFactory.AUTO_DOCTYPE, "");
			DocumentBuilder build = factory.newDocumentBuilder();
			System.out
					.println("Testing ShaniDomParser - parsing invalid xml :");
			System.out.println("Before parsing : ");
			System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
					+ "<!DOCTYPE note [\n"
					+ "	<!ELEMENT note (to,from,heading,body)>\n"
					+ "	<!ELEMENT to      (#PCDATA)>\n"
					+ "	<!ELEMENT from    (#PCDATA)>\n"
					+ "	<!ELEMENT heading (#PCDATA)>\n"
					+ "	<!ELEMENT body    (#PCDATA)>\n" + "	]>\n"
					+ "<!-- this is a comment -->\n"
					+ "<!-- this is another comment -->\n" + "<test>\n"
					+ "	<a a=b id=toto href=blabla>hello</a>\n"
					+ "	<!-- and one more comment -->\n"
					+ "	<html:div html:id=\"t>est\" >toto</html:div>\n"
					+ "	<b>totot <u>li&li&Atilde;&TOTO</b></u>\n"
					+ "	<![CDATA[ & \n\t\t< > \n\t\ttoto ]]>\n"
					+ "	<ul><li>lol</li></ul>\n" + "</test>");

			Document doc = build.parse(new InputSource(new StringReader(
					"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
							+ "<!DOCTYPE note [\n"
							+ "	<!ELEMENT note (to,from,heading,body)>\n"
							+ "	<!ELEMENT to      (#PCDATA)>\n"
							+ "	<!ELEMENT from    (#PCDATA)>\n"
							+ "	<!ELEMENT heading (#PCDATA)>\n"
							+ "	<!ELEMENT body    (#PCDATA)>\n" + "	]>\n"
							+ "<!-- this is a comment -->\n"
							+ "<!-- this is another comment -->\n" + "<test>\n"
							+ "	<a a=b id=toto href=blabla>hello</a>\n"
							+ "	<!-- and one more comment -->\n"
							+ "	<html:div html:id=\"t>est\" >toto</html:div>\n"
							+ "	<b>totot <u>li&li&Atilde;&TOTO</b></u>\n"
							+ "	<![CDATA[ & \n\t\t< > \n\t\ttoto ]]>\n"
							+ "	<ul><li>lol</li></ul>\n" + "</test>")));
			System.out.println("After parsing : ");
			System.out.println(doc);
			factory.setAttribute(CDocumentBuilderFactory.AUTO_DOCTYPE, null);
		}

		System.out.println("Comparing ShaniDomParser to internal jdk parser");
		System.out
				.println("Parsing the following xml 24x2500 times + traverse all the dom tree and display respective time : ");
		System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
				+ "<!-- this is a comment -->\n"
				+ "<!-- this is another comment -->\n"
				+ "<test xmlns:html=\"http://toto\">\n"
				+ "	<a id=\"toto\">hello{random double number}</a>\n"
				+ "	<!-- and one more comment -->\n"
				+ "	<html:div>toto</html:div>\n"
				+ "	<b>totot <u>li&amp;li</u></b>\n"
				+ "	<ul><li>lol</li></ul>\n" + "</test>");

		for (int i = 0; i < 58; i++) {
			if ((i % 2) == 0) {
				if (i < 10) {
					CShaniDomParser.testShaniParser(factory);
				} else {
					domParserTime += CShaniDomParser.testShaniParser(factory);
				}
			} // end if
			else {
				if (i < 10) {
					CShaniDomParser.testIntegParser(internalFactory);
				} else {
					integratedParser += CShaniDomParser
							.testIntegParser(internalFactory);
				}
			} // end else
		} // end for

		System.out.println("ShaniDomParser Time = " + (domParserTime / 24));
		System.out.println("integrated parser Time = "
				+ (integratedParser / 24));
	} // end testParser()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param factory
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 */
	public static long testShaniParser(final DocumentBuilderFactory factory)
			throws Exception {
		long domParserTime = 0;
		System.out.println("Starting ShaniDomParser : "
				+ factory.newDocumentBuilder().getClass());

		{
			Random rand = new Random(System.currentTimeMillis());
			long beginTime = System.currentTimeMillis();
			factory.setNamespaceAware(true);
			factory.setValidating(false);
			factory.setExpandEntityReferences(true);
			factory.setCoalescing(false);
			factory.setIgnoringComments(false);
			factory.setIgnoringElementContentWhitespace(false);

			DocumentBuilder build = factory.newDocumentBuilder();

			for (int i = 0; i < 2500; i++) {
				CShaniDomParser.traverseNode(build.parse(new InputSource(
						new StringReader(
								"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"
										+ "<!-- this is a comment -->\n"
										+ "<!-- this is another comment -->\n"
										+ "<test xmlns:html=\"http://toto\">\n"
										+ "	<a id=\"toto\">hello"
										+ rand.nextDouble() + "</a>\n"
										+ "	<!-- and one more comment -->\n"
										+ "	<html:div>toto</html:div>\n"
										+ "	<b>totot <u>li&amp;li</u></b>\n"
										+ "	<ul><li>lol</li></ul>\n"
										+ "</test>"))));
			} // end for

			long endTime = System.currentTimeMillis();
			domParserTime = endTime - beginTime;
		}

		return domParserTime;
	} // end testShaniParser()

	private static void traverseNode(Node node) {
		// it happens in classpath o_O ???!
		if (node == null) {
			return;
		}
		node.getNodeName();
		node.getNodeValue();
		node.getNamespaceURI();
		node.getPrefix();
		NamedNodeMap nnm = node.getAttributes();
		if (nnm != null) {
			for (int i = 0; i < nnm.getLength(); i++) {
				Attr attr = (Attr) nnm.item(i);
				attr.getName();
				attr.getValue();
				attr.getNamespaceURI();
				attr.getPrefix();
			}
		}
		NodeList nl = node.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			CShaniDomParser.traverseNode(nl.item(i));
		}
	}

	/** DOCUMENT ME! */
	private boolean autodoctype = false;

	/** DOCUMENT ME! */
	private boolean coalescing = false;

	/** DOCUMENT ME! */
	private String documentURI = null;

	/** DOCUMENT ME! */
	private ErrorHandler eh = null;

	/** DOCUMENT ME! */
	private EntityResolver er = null;

	/** DOCUMENT ME! */
	private boolean expandEntity = true;

	/** DOCUMENT ME! */
	private Map features = new HashMap(0);

	/** DOCUMENT ME! */
	private CDomHandler handler = null;

	/** DOCUMENT ME! */
	private boolean ignoringComments = false;

	private boolean ignoreDTD = false;
	
	/** DOCUMENT ME! */
	private boolean ignoringElementContentWhitespace = false;

	/**
	 * DOCUMENT ME!
	 */
	private boolean XIncludeAware = false;

	/**
	 * Creates a new CShaniDomParser object.
	 */
	public CShaniDomParser() {
		this(false, false);
	} // end CShaniDomParser()

	/**
	 * Creates a new CShaniDomParser object.
	 * 
	 * @param htmlParser
	 *            DOCUMENT ME!
	 */
	public CShaniDomParser(final boolean htmlParser) {
		this(htmlParser, false);
	} // end CShaniDomParser()

	/**
	 * Creates a new CShaniDomParser object.
	 * 
	 * @param htmlParser
	 *            DOCUMENT ME!
	 * @param validate
	 *            DOCUMENT ME!
	 */
	public CShaniDomParser(final boolean htmlParser, final boolean validate) {
		if (htmlParser) {
			this.features.put(CDomImplementation.PARSER_HTML, "" + htmlParser);
			this.features
					.put(CShaniDomParser.CASE_INSENSITIVE, "" + htmlParser);
		} // end if

		if (validate) {
			this.features.put(CDomHandler.VALIDATE, "" + validate);
		}

		this.handler = new CDomHandler(this.features);
	} // end CShaniDomParser()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#getDOMImplementation()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public DOMImplementation getDOMImplementation() {
		return this.handler.getDOMImplementation();
	} // end getDOMImplementation()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public EntityResolver getEntityResolver() {
		return this.er;
	} // end getEntityResolver()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public ErrorHandler getErrorHandler() {
		return this.eh;
	} // end getErrorHandler()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.XMLReader#getFeature(java.lang.String)
	 */
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
		String value = (String) this.features.get(name);

		return ((value == null) || !value.equals("true")) ? false : Boolean
				.valueOf(value).booleanValue();
	} // end getFeature()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param attrib
	 *            DOCUMENT ME!
	 * @param tagName
	 *            DOCUMENT ME!
	 * @param mapMerge
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private Object[] getMerge(final String attrib, final String tagName,
			final List mapMerge) {
		if (mapMerge == null) {
			return null;
		}

		Iterator it = mapMerge.iterator();

		while (it.hasNext()) {
			Object value[] = (Object[]) it.next();
			String tmpTagName = (String) value[0];

			if ((tmpTagName.equalsIgnoreCase(tagName))
					|| (tmpTagName.equals("*"))) {
				String attribName = (String) value[1];

				if (attribName.equalsIgnoreCase(attrib)) {
					String srcAttribNameInDest = (String) value[2];
					String destAttribName = (String) value[3];
					String separator = (String) value[4];
					List match = (List) value[5];
					List valueStr = (List) value[6];

					return new Object[] { destAttribName, srcAttribNameInDest,
							separator, match, valueStr };
				} // end if
			} // end if
		} // end while

		return null;
	} // end getMerge()

	public boolean isAutodoctype() {
		return this.autodoctype;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean isCoalescing() {
		return this.coalescing;
	} // end isCoalescing()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean isExpandEntity() {
		return this.expandEntity;
	} // end isExpandEntity()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean isIgnoringComments() {
		return this.ignoringComments;
	} // end isIgnoringComments()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public final boolean isIgnoringElementContentWhitespace() {
		return this.ignoringElementContentWhitespace;
	} // end isIgnoringElementContentWhitespace()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#isNamespaceAware()
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
	 * @see javax.xml.parsers.DocumentBuilder#isValidating()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isValidating() {
		return false;
	} // end isValidating()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param attrib
	 *            DOCUMENT ME!
	 * @param tagName
	 *            DOCUMENT ME!
	 * @param mapTag
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private boolean isValidAttributeFor(final String attrib,
			final String tagName, final List mapTag) {
		if (mapTag == null) {
			return true;
		}

		Iterator it = mapTag.iterator();

		while (it.hasNext()) {
			Object value[] = (Object[]) it.next();
			String tmpTagName = (String) value[0];

			if ((tmpTagName.equals(tagName)) || (tmpTagName.equals("*"))) {
				List attributes = (List) value[1];
				Iterator itAttrib = attributes.iterator();

				while (itAttrib.hasNext()) {
					String tmpAttrib = (String) itAttrib.next();

					if (tmpAttrib.equalsIgnoreCase(attrib)) {
						return true;
					}
				} // end while
			} // end if
		} // end while

		return false;
	} // end isValidAttributeFor()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#isXIncludeAware()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isXIncludeAware() {
		return this.XIncludeAware;
	} // end isXIncludeAware()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param attrib
	 *            DOCUMENT ME!
	 * @param tagName
	 *            DOCUMENT ME!
	 * @param mapMerge
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private boolean needMerge(final String attrib, final String tagName,
			final List mapMerge) {
		if (mapMerge == null) {
			return false;
		}

		Iterator it = mapMerge.iterator();

		while (it.hasNext()) {
			Object value[] = (Object[]) it.next();
			String tmpTagName = (String) value[0];

			if ((tmpTagName.equalsIgnoreCase(tagName))
					|| (tmpTagName.equals("*"))) {
				String attribName = (String) value[1];

				if (attribName.equalsIgnoreCase(attrib)) {
					return true;
				}
			} // end if
		} // end while

		return false;
	} // end needMerge()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#newDocument()
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Document newDocument() {
		return this.handler.getDOMImplementation().createDocument(null, null,
				null);
	} // end newDocument()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#parse(java.io.File)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param f
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXException
	 *             DOCUMENT ME!
	 * @throws IOException
	 *             DOCUMENT ME!
	 */
	public Document parse(final File f) throws SAXException, IOException {
		Document doc = this.parse(f.toURI().toURL());

		return doc;
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws UnsupportedEncodingException
	 *             DOCUMENT ME!
	 * @throws IOException
	 *             DOCUMENT ME!
	 */
	public Document parse(final InputSource in)
			throws UnsupportedEncodingException, IOException, SAXException {
		this.documentURI = in.getSystemId();
		if (in.getByteStream() != null) {
			Document doc = this.parse(in.getByteStream());
			((ADocument) doc).setDocumentURI(this.documentURI);
			return doc;
		}

		if (in.getCharacterStream() != null) {
			Document doc = this.parse(in.getCharacterStream());
			((ADocument) doc).setDocumentURI(this.documentURI);
			return doc;
		}

		if (in.getSystemId() != null) {
			try {
				URL u = new URL(in.getSystemId());

				return this.parse(u);
			} // end try
			catch (final Exception e) {
				File f = new File(in.getSystemId());

				if (f.exists()) {
					return this.parse(f);
				}
			} // end catch
		} // end if

		throw new IOException("InputSource is not resolvable.");
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws UnsupportedEncodingException
	 *             DOCUMENT ME!
	 * @throws IOException
	 *             DOCUMENT ME!
	 */
	public Document parse(final InputStream in)
			throws UnsupportedEncodingException, IOException {
		// get Encoding
		return this.parse(CXmlParser.getReader(in));
	} // end parse()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#parse(java.io.InputStream,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param is
	 *            DOCUMENT ME!
	 * @param systemId
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXException
	 *             DOCUMENT ME!
	 * @throws IOException
	 *             DOCUMENT ME!
	 */
	public Document parse(final InputStream is, final String systemId)
			throws SAXException, IOException {
		this.documentURI = systemId;
		Document doc = this.parse(is);
		((ADocument) doc).setDocumentURI(this.documentURI);
		return doc;
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Document parse(final Reader in) {
		return this.parse(in, null, null);
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * @param mapTag
	 *            DOCUMENT ME!
	 * @param mapMerge
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Document parse(final Reader in, final List mapTag,
			final List mapMerge) {
		try {
			Document doc = this.handler.getDocument(in, this.documentURI);
			if (mapTag != null) {
				this.validateAttributes(doc, mapTag, mapMerge);
			}

			return doc;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException(e);
			}
		}
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DOMException
	 *             DOCUMENT ME!
	 */
	public Document parse(final String in) {
		if (in.indexOf("://") != -1) {
			try {
				return this.parse(new URL(in));
			} // end try
			catch (final Exception e) {
				e.printStackTrace();

				if (e instanceof DOMException) {
					throw (DOMException) e;
				}

				throw new DOMException(DOMException.NOT_FOUND_ERR, e
						.getMessage());
			} // end catch
		} // end if

		return this.parse(new StringReader(in));
	} // end parse()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#parse(java.io.File)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param f
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws SAXException
	 *             DOCUMENT ME!
	 * @throws IOException
	 *             DOCUMENT ME!
	 */
	public Document parse(final URL f) throws SAXException, IOException {
		this.documentURI = f.toString();

		InputStream in = null;

		try {
			URLConnection connection = f.openConnection();
			// only 1.5
			// connection.setReadTimeout(5000);
			in = connection.getInputStream();

			Document doc = this.parse(in);
			((ADocument) doc).setDocumentURI(this.documentURI);

			return doc;
		} // end try
		finally {
			try {
				in.close();
			} // end try
			catch (final Exception ignore) {
			}
		} // end finally
	} // end parse()

	public void setAutodoctype(boolean autodoctype) {
		this.autodoctype = autodoctype;
		this.handler.setAutodoctype(autodoctype);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param coalescing
	 *            DOCUMENT ME!
	 */
	public final void setCoalescing(final boolean coalescing) {
		this.coalescing = coalescing;
		this.handler.setCoalescing(coalescing);
	} // end setCoalescing()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#setEntityResolver(org.xml.sax.EntityResolver)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param er
	 *            DOCUMENT ME!
	 */
	public void setEntityResolver(final EntityResolver er) {
		this.er = er;
	} // end setEntityResolver()

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.parsers.DocumentBuilder#setErrorHandler(org.xml.sax.ErrorHandler)
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param eh
	 *            DOCUMENT ME!
	 */
	public void setErrorHandler(final ErrorHandler eh) {
		this.eh = eh;
	} // end setErrorHandler()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param expandEntity
	 *            DOCUMENT ME!
	 */
	public final void setExpandEntity(final boolean expandEntity) {
		this.expandEntity = expandEntity;
		this.handler.setExpandEntity(expandEntity);
	} // end setExpandEntity()

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.XMLReader#setFeature(java.lang.String, boolean)
	 */
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
		this.features.put(name, "" + value);

		if (name.equals(CShaniDomParser.CASE_INSENSITIVE)) {
			this.handler = new CDomHandler(this.features);
		} // end if

		if (name.equals(CShaniDomParser.STRICT_MODE)) {
			this.handler = new CDomHandler(this.features);
		} // end if
	} // end setFeature()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param ignoringComments
	 *            DOCUMENT ME!
	 */
	public final void setIgnoringComments(final boolean ignoringComments) {
		this.ignoringComments = ignoringComments;
		this.handler.setIgnoringComments(ignoringComments);
	} // end setIgnoringComments()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param ignoringElementContentWhitespace
	 *            DOCUMENT ME!
	 */
	public final void setIgnoringElementContentWhitespace(
			final boolean ignoringElementContentWhitespace) {
		this.ignoringElementContentWhitespace = ignoringElementContentWhitespace;
		this.handler
				.setIgnoringElementContentWhitespace(ignoringElementContentWhitespace);
	} // end setIgnoringElementContentWhitespace()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param includeAware
	 *            DOCUMENT ME!
	 */
	public final void setXIncludeAware(final boolean includeAware) {
		this.XIncludeAware = includeAware;
		this.handler.setXIncludeAware(includeAware);
	} // end setXIncludeAware()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param currentNode
	 *            DOCUMENT ME!
	 * @param mapTag
	 *            DOCUMENT ME!
	 * @param mapMerge
	 *            DOCUMENT ME!
	 */
	private void validateAttributes(final Node currentNode, final List mapTag,
			final List mapMerge) {
		if ((mapTag == null) || (mapTag.size() == 0)) {
			return;
		}

		if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
			List attrToRemove = new ArrayList();
			String tagName = currentNode.getNodeName();
			NamedNodeMap nmn = currentNode.getAttributes();

			for (int i = 0; i < nmn.getLength(); i++) {
				Attr attr = (Attr) nmn.item(i);

				if (!this.isValidAttributeFor(attr.getName(), tagName, mapTag)) {
					attrToRemove.add(attr);

					continue;
				} // end if

				if (this.needMerge(attr.getName(), tagName, mapMerge)) {
					Object merge[] = this.getMerge(attr.getName(), tagName,
							mapMerge);
					Attr attrToMergeWith = (Attr) nmn
							.getNamedItem((String) merge[0]);
					String attribValue = "";

					if (attrToMergeWith != null) {
						attribValue = attrToMergeWith.getValue();
					} // end if

					if ((attribValue.length() > 0)
							&& (!attribValue.endsWith(((String) merge[2])))) {
						attribValue = attribValue + merge[2];
					} // end if

					if (((ArrayList) merge[3]).contains(attr.getValue())) {
						int iIndex = ((ArrayList) merge[3]).indexOf(attr
								.getValue());
						attribValue = attribValue + merge[1]
								+ ((String) ((ArrayList) merge[4]).get(iIndex));
					} // end if
					else {
						attribValue = attribValue + ((String) merge[1])
								+ attr.getValue();
					} // end else

					if (attrToMergeWith != null) {
						attrToMergeWith.setValue(attribValue);
					} // end if
					else {
						((Element) currentNode).setAttribute((String) merge[0],
								attr.getValue());
					} // end else
				} // end if
			} // end for

			for (int i = 0; i < attrToRemove.size(); i++) {
				Attr attr = (Attr) attrToRemove.get(i);
				((Element) currentNode).removeAttributeNode(attr);
			} // end for
		} // end if

		NodeList nl = currentNode.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			this.validateAttributes(nl.item(i), mapTag, mapMerge);
		} // end for
	} // end validateAttributes()

	public boolean isIgnoreDTD() {
		return ignoreDTD;
	}

	public void setIgnoreDTD(boolean ignoreDTD) {
		this.ignoreDTD = ignoreDTD;
		this.handler.setIgnoreDTD(ignoreDTD);
	}
} // end CShaniDomParser
