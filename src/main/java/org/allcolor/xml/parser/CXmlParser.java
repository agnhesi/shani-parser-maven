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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.URL;

import org.allcolor.dtd.parser.CDTDParser;
import org.allcolor.dtd.parser.CDocType;
import org.allcolor.dtd.parser.CNotation;
import org.allcolor.xml.parser.dom.CAttr;
import org.allcolor.xml.parser.dom.CDom2HTMLDocument;
import org.w3c.dom.Notation;
import org.xml.sax.SAXException;

/**
 * The Xml parser implementation.
 * 
 * @author Quentin Anciaux
 */
public final class CXmlParser {
	/** DOCUMENT ME! */
	public static final CDocType dtFr = CXmlParser.loadDTD("/dtd-fr.ser");

	// dtdparser.parse("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0
	// Transitional//EN\"
	// \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">","http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
	// dtdparser.parse("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0
	// Frameset//EN\"
	// \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">","http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd");
	/** DOCUMENT ME! */
	public static final CDocType dtTr = CXmlParser.loadDTD("/dtd-tr.ser");

	static {
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", "60000");
			System.setProperty("sun.net.client.defaultReadTimeout", "60000");
			System.setProperty("file.encoding", "utf-8");
		} // end try
		catch (final Throwable ignore) {
			final Throwable t = ignore;
			if (t.getClass() == ThreadDeath.class) {
				throw (ThreadDeath) t;
			}
			Throwable cause = ignore.getCause();
			while (cause != null) {
				if (cause.getClass() == ThreadDeath.class) {
					throw (ThreadDeath) cause;
				}
				cause = cause.getCause();
			}
		}
	} // end static

	/**
	 * DOCUMENT ME!
	 * 
	 * @param toTest
	 *            DOCUMENT ME!
	 * @param tester
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private static boolean compareByteArray(final byte toTest[],
			final byte tester[]) {
		if (toTest.length < tester.length) {
			return false;
		} // end if

		for (int i = 0; i < tester.length; i++) {
			if (toTest[i] != tester[i]) {
				return false;
			} // end if
		} // end for

		return true;
	} // end compareByteArray()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws IOException
	 *             DOCUMENT ME!
	 */
	public static Reader getReader(final InputStream in) throws IOException {
		final CInputStreamBuffer buffer = new CInputStreamBuffer(in);
		buffer.mark(-1);

		byte bbuffer[] = new byte[2048];
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int inb = -1;
		int count = 0;

		while (((inb = buffer.read(bbuffer)) != -1) && (count < 2048)) {
			bout.write(bbuffer, 0, inb);
			count += inb;
		} // end while

		bbuffer = bout.toByteArray();

		final int length = bbuffer.length;
		buffer.reset();

		char cbuffer[] = new char[length];
		int index = 0;
		String preEnc = null;

		if (CXmlParser.compareByteArray(bbuffer, new byte[] { 0x4c, 0x6f,
				(byte) 0xa7, (byte) 0x94 })) {
			// EBCDIC ?
			preEnc = "Cp1047";
			cbuffer = new String(bbuffer, "Cp1047").toCharArray();
			index = cbuffer.length;
		} // end if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { 0, 0,
				(byte) 0xfe, (byte) 0xff })) {
			preEnc = "UTF-32BE";
			cbuffer = new String(bbuffer, "UTF-32BE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { (byte) 0xff,
				(byte) 0xfe, 0, 0 })) {
			preEnc = "UTF-32LE";
			cbuffer = new String(bbuffer, "UTF-32LE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { (byte) 0xfe,
				(byte) 0xff })) {
			preEnc = "UTF-16BE";
			cbuffer = new String(bbuffer, "UTF-16BE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { (byte) 0xff,
				(byte) 0xfe })) {
			preEnc = "UTF-16LE";
			cbuffer = new String(bbuffer, "UTF-16LE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { 0, 0, 0,
				0x3c })) {
			preEnc = "UTF-32BE";
			cbuffer = new String(bbuffer, "UTF-32BE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { 0x3c, 0, 0,
				0 })) {
			preEnc = "UTF-32LE";
			cbuffer = new String(bbuffer, "UTF-32LE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { 0, 0x3c, 0,
				0x3f })) {
			preEnc = "UTF-16BE";
			cbuffer = new String(bbuffer, "UTF-16BE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { 0x3c, 0,
				0x3f, 0 })) {
			preEnc = "UTF-16LE";
			cbuffer = new String(bbuffer, "UTF-16LE").toCharArray();
			index = cbuffer.length;
		} // end else if
		else if (CXmlParser.compareByteArray(bbuffer, new byte[] { (byte) 0xef,
				(byte) 0xbb, (byte) 0xbf })) {
			preEnc = "UTF-8";
			cbuffer = new String(bbuffer, "UTF-8").toCharArray();
			index = cbuffer.length;
		} // end else if
		else {
			for (int i = 0; i < length; i++) {
				if (bbuffer[i] > 0) {
					cbuffer[index++] = (char) bbuffer[i];
				} // end if
			} // end for
		} // end else

		final String value = new String(cbuffer, 0, index);

		if (value.indexOf("<?xml ", 0) != -1) {
			String pi = value.substring(value.indexOf("<?xml ", 0));

			if (pi.indexOf("?>", 0) != -1) {
				pi = pi.substring(0, pi.indexOf("?>", 0)) + 2;
			} // end if

			int indexEncoding = pi.indexOf("encoding=\"", 0);

			if (indexEncoding != -1) {
				String encoding = pi.substring(indexEncoding + 10);
				indexEncoding = encoding.indexOf("\"", 0);

				if (indexEncoding != -1) {
					encoding = encoding.substring(0, indexEncoding);
				} // end if

				try {
					if (!"utf-8".equalsIgnoreCase(preEnc)) {
						"test".getBytes(preEnc);

						return new CInputStreamReader(buffer, preEnc);
					} // end if
				} // end try
				catch (final Exception ignore) {
					preEnc = "utf-8";
				} // end catch

				try {
					"test".getBytes(encoding);

					return new CInputStreamReader(buffer, encoding);
				} // end try
				catch (final Exception ignore) {
				} // end catch

				return new CInputStreamReader(buffer, preEnc);
			} // end if

			indexEncoding = pi.indexOf("encoding='", 0);

			if (indexEncoding != -1) {
				String encoding = pi.substring(indexEncoding + 10);
				indexEncoding = encoding.indexOf("'", 0);

				if (indexEncoding != -1) {
					encoding = encoding.substring(0, indexEncoding);
				} // end if

				try {
					if (!"utf-8".equalsIgnoreCase(preEnc)) {
						"test".getBytes(preEnc);

						return new CInputStreamReader(buffer, preEnc);
					} // end if
				} // end try
				catch (final Exception ignore) {
					preEnc = "utf-8";
				} // end catch

				try {
					"test".getBytes(encoding);

					return new CInputStreamReader(buffer, encoding);
				} // end try
				catch (final Exception ignore) {
				}

				return new CInputStreamReader(buffer, preEnc);
			} // end if

			try {
				"test".getBytes(preEnc);

				return new CInputStreamReader(buffer, preEnc);
			} // end try
			catch (final Exception ignore) {
				preEnc = "utf-8";
			} // end catch

			return new CInputStreamReader(buffer, preEnc);
		} // end if

		int indexEncoding = value.indexOf("charset=", 0);

		if (indexEncoding != -1) {
			String encoding = value.substring(indexEncoding + 8);
			indexEncoding = encoding.indexOf("\"", 0);

			if (indexEncoding != -1) {
				encoding = encoding.substring(0, indexEncoding);
			} // end if
			else {
				indexEncoding = encoding.indexOf("'", 0);

				if (indexEncoding != -1) {
					encoding = encoding.substring(0, indexEncoding);
				} // end if
			} // end else

			try {
				if (!"utf-8".equalsIgnoreCase(preEnc)) {
					"test".getBytes(preEnc);

					return new CInputStreamReader(buffer, preEnc);
				} // end if
			} // end try
			catch (final Exception ignore) {
				preEnc = "utf-8";
			} // end catch

			try {
				"test".getBytes(encoding);

				return new CInputStreamReader(buffer, encoding);
			} // end try
			catch (final Exception ignore) {
			}

			return new CInputStreamReader(buffer, preEnc);
		} // end if

		try {
			"test".getBytes(preEnc);

			return new CInputStreamReader(buffer, preEnc);
		} // end try
		catch (final Exception ignore) {
			preEnc = "utf-8";
		} // end catch

		return new CInputStreamReader(buffer, preEnc);
	} // end getReader()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static CDocType loadDTD(final String name) {
		try {
			final ObjectInputStream in = new ObjectInputStream(CXmlParser.class
					.getResourceAsStream(name));
			final CDocType dt = (CDocType) in.readObject();
			in.close();

			return dt;
		} // end try
		catch (final Throwable e) {
			final Throwable t = e;
			if (t.getClass() == ThreadDeath.class) {
				throw (ThreadDeath) t;
			}
			Throwable cause = e.getCause();
			while (cause != null) {
				if (cause.getClass() == ThreadDeath.class) {
					throw (ThreadDeath) cause;
				}
				cause = cause.getCause();
			}
			// System.err.println("reading local dtd if found...");
			if ("/dtd-tr.ser".equals(name)) {
				final URL u = CXmlParser.class.getResource("/dtd-tr.dtd");

				if (u != null) {
					return CDTDParser
							.parse(
									"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">",
									u.toString(), new CDom2HTMLDocument());
				} // end if
				else {
					return CDTDParser
							.parse(
									"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">",
									"http://www.w3.org/TRxhtml1/DTD/xhtml1-transitional.dtd",
									new CDom2HTMLDocument());
				} // end else
			} // end if
			else if ("/dtd-fr.ser".equals(name)) {
				final URL u = CXmlParser.class.getResource("/dtd-fr.dtd");

				if (u != null) {
					return CDTDParser
							.parse(
									"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">",
									u.toString(), new CDom2HTMLDocument());
				} // end if
				else {
					return CDTDParser
							.parse(
									"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">",
									"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd",
									new CDom2HTMLDocument());
				} // end else
			} // end else if

			return null;
		} // end catch
	} // end loadDTD()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param c
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static final boolean VALID_XML11_FIRST_LETTERS(final char c) {
		if ((c >= 'A') && (c <= 'Z')) {
			return true;
		} // end if

		if ((c >= 'a') && (c <= 'z')) {
			return true;
		} // end if

		if ((c == '_') || (c == ':')) {
			return true;
		} // end if

		if ((c >= 0x00C0) && (c <= 0x00D6)) {
			return true;
		} // end if

		if ((c >= 0x00D8) && (c <= 0x00F6)) {
			return true;
		} // end if

		if ((c >= 0x00F8) && (c <= 0x02FF)) {
			return true;
		} // end if

		if ((c >= 0x0370) && (c <= 0x037D)) {
			return true;
		} // end if

		if ((c >= 0x037F) && (c <= 0x1FFF)) {
			return true;
		} // end if

		if ((c >= 0x200C) && (c <= 0x200D)) {
			return true;
		} // end if

		if ((c >= 0x2070) && (c <= 0x218F)) {
			return true;
		} // end if

		if ((c >= 0x2C00) && (c <= 0x2FEF)) {
			return true;
		} // end if

		if ((c >= 0x3001) && (c <= 0xD7FF)) {
			return true;
		} // end if

		if ((c >= 0xF900) && (c <= 0xFDCF)) {
			return true;
		} // end if

		if ((c >= 0xFDF0) && (c <= 0xFFFD)) {
			return true;
		} // end if

		if ((c >= 0x10000) && (c <= 0xEFFFF)) {
			return true;
		} // end if

		return false;
	} // end VALID_XML11_FIRST_LETTERS()

	/** DOCUMENT ME! */
	private boolean caseInsensitive = false;

	/** DOCUMENT ME! */
	private boolean caseInsensitiveBackup = false;

	/** DOCUMENT ME! */
	private CDocType dt = null;

	/** DOCUMENT ME! */
	private boolean firstTag = false;

	/** DOCUMENT ME! */
	protected boolean htmlDocument = false;

	private Reader in = null;

	private int iprev = -1;

	/** DOCUMENT ME! */
	private final IParseHandler parser;

	private final CStringBuilder tagcontent = new CStringBuilder(128);

	/**
	 * Creates a new CXmlParser object.
	 * 
	 * @param caseInsensitive
	 *            DOCUMENT ME!
	 * @param parser
	 *            DOCUMENT ME!
	 */
	public CXmlParser(final boolean caseInsensitive, final IParseHandler parser) {
		super();
		this.caseInsensitive = caseInsensitive;
		this.caseInsensitiveBackup = caseInsensitive;
		this.parser = parser;
	} // end CXmlParser()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param elemName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private boolean canHaveChild(final String elemName) {
		if (this.dt == null) {
			return true;
		} // end if

		final Notation nt = (Notation) this.dt.getNotations().getNamedItem(
				elemName);

		try {
			final CNotation cnt = (CNotation) nt;

			return cnt.getElement().getValidNodes().size() > 0;
		} // end try
		catch (final Throwable ignore) {
			final Throwable t = ignore;
			if (t.getClass() == ThreadDeath.class) {
				throw (ThreadDeath) t;
			}
			Throwable cause = ignore.getCause();
			while (cause != null) {
				if (cause.getClass() == ThreadDeath.class) {
					throw (ThreadDeath) cause;
				}
				cause = cause.getCause();
			}
		}

		return true;
	} // end canHaveChild()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param in
	 *            DOCUMENT ME!
	 * 
	 * @throws SAXException
	 *             DOCUMENT ME!
	 */
	public void parse(final Reader in) {
		try {
			this.htmlDocument = false;
			this.caseInsensitive = this.caseInsensitiveBackup;
			this.firstTag = true;
			this.dt = null;

			boolean element = false;
			boolean cdata = false;
			boolean doctype = false;
			boolean pi = false;
			boolean comment = false;
			this.in = in;
			this.iprev = -1;
			int ic = -1;

			if (in.getClass() == CInputStreamReader.class) {
				this.parser
						.setEncoding(((CInputStreamReader) in).getEncoding());
			} // end if

			this.parser.documentStart();

			while ((ic = this.read()) != -1) {
				final char c = (char) ic;
				ic = this.read();

				if (ic != -1) {
					char n = (char) ic;

					if (c == '<') {
						if (n == '?') {
							pi = true;
						} // end if
						else if (n == '!') {
							ic = this.read();

							if (ic == -1) {
								break;
							} // end if

							n = (char) ic;

							if (n == '-') {
								comment = true;
							} // end if
							else if (n == '[') {
								cdata = true;
							} // end else if
							else if ((n == 'd') || (n == 'D')) {
								doctype = true;
							} // end else if
						} // end else if
						else {
							if (CXmlParser.VALID_XML11_FIRST_LETTERS(n)
									|| (n == '/')) {
								element = true;
							} // end if
						} // end else
					} // end if
					else if (n == '<') {
						this.parser.parseText("" + c, false);
						this.iprev = n;
						// struct.hasprev = true;

						continue;
					} // end else if

					if (element) {
						element = false;
						this.tagcontent.reset();

						final char firstChar = n;
						this.tagcontent.append(n);
						int index = 0;
						int indexSep = -1;
						while ((ic = this.read()) != -1) {
							n = (char) ic;
							index++;

							if (n == '>') {
								break;
							} // end if

							if (n == ':') {
								indexSep = index;
							}

							if ((n == ' ') || (n == '/') || (n == '\n')
									|| (n == '\r') || (n == '\t') || (n == 160)) {
								break;
							} // end if

							this.tagcontent.append(n);
						} // end while
						final String tag = this.tagcontent.toString();
						this.tagcontent.reset();
						// parse attributes.
						while ((n == ' ') || (n == '\n') || (n == '\r')
								|| (n == '\t') || (n == 160)) {
							if ((ic = this.read()) == -1) {
								break;
							}
							n = (char) ic;
						}
						if (n == '/') {
							this.parseEMPTYTag(tag, null, 0, indexSep);
							if ((ic = this.read()) == -1) {
								break;
							}
							continue;
						} else if (n == '>') {
							if (firstChar == '/') {
								this.parseENDTag(tag, indexSep);
							} else {
								this.parseSTARTTag(tag, null, 0, indexSep);
							}
							continue;
						} else {
							// got attributes
							CAttr[] attrs = null;
							int count = 0;
							this.tagcontent.reset();
							boolean has1stLetter = false;
							if (CXmlParser.VALID_XML11_FIRST_LETTERS(n)) {
								this.tagcontent.append(n);
								has1stLetter = true;
							}
							while ((ic = this.read()) != -1) {
								n = (char) ic;
								if ((n == ' ') || (n == '\r') || (n == '\n')
										|| (n == '\t') || (n == 160)) {
									if (has1stLetter) {
										final String name = this.tagcontent
												.toString();
										if (attrs == null) {
											attrs = new CAttr[2];
										}
										attrs[count++] = new CAttr(name, name,
												null, null, true);
										attrs[count - 1].needToDecode = false;
										this.parseSTARTTag(tag, attrs, count,
												indexSep);
										this.tagcontent.reset();
										has1stLetter = false;
										continue;
									}
									continue;
								} // end if
								else if (CXmlParser
										.VALID_XML11_FIRST_LETTERS(n)
										|| (n == '=')) {
									has1stLetter = false;
									// attribute found
									if (n != '=') {
										this.tagcontent.append(n);
										while ((ic = this.read()) != -1) {
											n = (char) ic;
											if ((n == ' ') || (n == '\r')
													|| (n == '\n')
													|| (n == '\t')
													|| (n == '=') || (n == 160)) {
												break;
											} // end if
											this.tagcontent.append(n);
										}
									}
									// bon on a le nom
									final String name = this.tagcontent
											.toString();
									this.tagcontent.reset();
									if (ic == -1) {
										if (attrs == null) {
											attrs = new CAttr[1];
										}
										attrs[count++] = new CAttr(name, name,
												null, null, true);
										attrs[count - 1].needToDecode = false;
										this.parseSTARTTag(tag, attrs, count,
												indexSep);
										break;
									}
									while ((n == ' ') || (n == '\r')
											|| (n == '\n') || (n == '\t')
											|| (n == 160)) {
										if ((ic = this.read()) == -1) {
											break;
										}
										n = (char) ic;
									}
									if (ic == -1) {
										break;
									}
									if (n == '=') {
										// go to read value
										if ((ic = this.read()) == -1) {
											break;
										}
										n = (char) ic;
										while ((n == ' ') || (n == '\r')
												|| (n == '\n') || (n == '\t')
												|| (n == 160)) {
											if ((ic = this.read()) == -1) {
												break;
											}
											n = (char) ic;
										}
										if (ic == -1) {
											break;
										}
										if (n == '\"') {
											// read till next "
											if ((ic = this.read()) == -1) {
												break;
											}
											n = (char) ic;
											boolean needToDecode = false;
											if (n == '&') {
												needToDecode = true;
											}
											while (n != '\"') {
												this.tagcontent.append(n);
												if ((ic = this.read()) == -1) {
													break;
												}
												n = (char) ic;
												if (n == '&') {
													needToDecode = true;
												}
											}
											if (attrs == null) {
												attrs = new CAttr[2];
											}
											if (count == attrs.length) {
												final CAttr[] nattrs = new CAttr[attrs.length * 2 + 2];
												System.arraycopy(attrs, 0,
														nattrs, 0, count);
												attrs = nattrs;
											}
											attrs[count++] = new CAttr(name,
													this.tagcontent.toString(),
													null, null, true);
											if (!needToDecode) {
												attrs[count - 1].needToDecode = false;
											}
											this.tagcontent.reset();
											continue;
										} else if (n == '\'') {
											// read till next '
											if ((ic = this.read()) == -1) {
												break;
											}
											n = (char) ic;
											boolean needToDecode = false;
											if (n == '&') {
												needToDecode = true;
											}
											while (n != '\'') {
												this.tagcontent.append(n);
												if ((ic = this.read()) == -1) {
													break;
												}
												n = (char) ic;
												if (n == '&') {
													needToDecode = true;
												}
											}
											if (attrs == null) {
												attrs = new CAttr[2];
											}
											if (count == attrs.length) {
												final CAttr[] nattrs = new CAttr[attrs.length * 2 + 2];
												System.arraycopy(attrs, 0,
														nattrs, 0, count);
												attrs = nattrs;
											}
											attrs[count++] = new CAttr(name,
													this.tagcontent.toString(),
													null, null, true);
											if (!needToDecode) {
												attrs[count - 1].needToDecode = false;
											}
											this.tagcontent.reset();
											continue;
										} else {
											this.tagcontent.append(n);
											boolean needToDecode = false;
											if (n == '&') {
												needToDecode = true;
											}
											while ((ic = this.read()) != -1) {
												n = (char) ic;
												if ((n == ' ') || (n == '\r')
														|| (n == '\n')
														|| (n == '\t')
														|| (n == '>')
														|| (n == 160)) {
													break;
												}
												this.tagcontent.append(n);
												if (n == '&') {
													needToDecode = true;
												}
											}
											if (attrs == null) {
												attrs = new CAttr[2];
											}
											if (count == attrs.length) {
												final CAttr[] nattrs = new CAttr[attrs.length * 2 + 2];
												System.arraycopy(attrs, 0,
														nattrs, 0, count);
												attrs = nattrs;
											}
											attrs[count++] = new CAttr(name,
													this.tagcontent.toString(),
													null, null, true);
											if (!needToDecode) {
												attrs[count - 1].needToDecode = false;
											}
											this.tagcontent.reset();
											if (n == '>') {
												break;
											}
											continue;
										}
									} else if (n == '/') {
										if (attrs == null) {
											attrs = new CAttr[1];
										}
										if (count == attrs.length) {
											final CAttr[] nattrs = new CAttr[attrs.length * 2 + 2];
											System.arraycopy(attrs, 0, nattrs,
													0, count);
											attrs = nattrs;
										}
										attrs[count++] = new CAttr(name, name,
												null, null, true);
										attrs[count - 1].needToDecode = false;
										this.parseEMPTYTag(tag, attrs, count,
												indexSep);
										ic = this.read();
										break;
									} else if (n == '<') {
										// struct.hasprev = true;
										this.iprev = n;
										break;
									} else {
										if (attrs == null) {
											attrs = new CAttr[2];
										}
										if (count == attrs.length) {
											final CAttr[] nattrs = new CAttr[attrs.length * 2 + 2];
											System.arraycopy(attrs, 0, nattrs,
													0, count);
											attrs = nattrs;
										}
										attrs[count++] = new CAttr(name, name,
												null, null, true);
										// struct.hasprev = true;
										this.iprev = n;
										continue;
									}
								} else if (n == '>') {
									break;
								} else if (n == '/') {
									this.parseEMPTYTag(tag, attrs, count,
											indexSep);
									ic = this.read();
									break;
								} else if (n == '<') {
									// struct.hasprev = true;
									this.iprev = n;
									break;
								}
							}
							if (ic == -1) {
								break;
							}
							if (firstChar == '/') {
								this.parseENDTag(tag, indexSep);
							} else if (n != '/') {
								this.parseSTARTTag(tag, attrs, count, indexSep);
							}
							continue;
						}
					} // end if
					else if (pi) {
						pi = false;
						this.tagcontent.reset();

						while ((ic = this.read()) != -1) {
							n = (char) ic;

							if (n == '?') {
								if ((ic = this.read()) == -1) {
									break;
								} // end if

								n = (char) ic;

								if (n == '>') {
									break;
								} // end if
								else {
									this.tagcontent.append('?');
									this.tagcontent.append(n);
									continue;
								} // end else
							} // end if
							else if ((n == ' ') || (n == '\n') || (n == '\r')
									|| (n == '\t') || (n == 160)) {
								break;
							} // end else if

							this.tagcontent.append(n);
						} // end while
						final String tag = this.tagcontent.toString();
						this.tagcontent.reset();
						if (n == '>') {
							this.parser.parsePI(tag, "");

							if (ic == -1) {
								break;
							} // end if
						} // end if
						else {
							if (ic == -1) {
								break;
							} // end if

							while ((ic = this.read()) != -1) {
								n = (char) ic;

								if (n == '?') {
									if ((ic = this.read()) == -1) {
										break;
									} // end if

									n = (char) ic;

									if (n == '>') {
										break;
									} // end if
									else {
										this.tagcontent.append('?');
										this.tagcontent.append(n);
										continue;
									} // end else
								} else if (n == '/') {
									if ((ic = this.read()) == -1) {
										break;
									} // end if

									n = (char) ic;

									if (n == '>') {
										break;
									} // end if
									else {
										this.tagcontent.append('/');
										this.tagcontent.append(n);
										continue;
									} // end else
								}

								this.tagcontent.append(n);
							} // end while

							this.parser
									.parsePI(tag, this.tagcontent.toString());

							if (ic == -1) {
								break;
							} // end if
						} // end else

						continue;
					} // end else if
					else if (comment) {
						comment = false;

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						this.tagcontent.reset();

						while ((ic = this.read()) != -1) {
							n = (char) ic;

							if (n == '-') {
								if ((ic = this.read()) == -1) {
									break;
								} // end if

								n = (char) ic;

								if (n == '-') {
									if ((ic = this.read()) == -1) {
										break;
									} // end if

									n = (char) ic;

									if (n == '>') {
										break;
									} // end if
									else {
										this.tagcontent.append('-');
										this.tagcontent.append('-');
										this.tagcontent.append(n);
										continue;
									} // end else
								} // end if
								else {
									this.tagcontent.append('-');
									this.tagcontent.append(n);
									continue;
								} // end else
							} // end if

							this.tagcontent.append(n);
						} // end while

						this.parser.parseComment(this.tagcontent.toString());

						if (ic == -1) {
							break;
						} // end if

						continue;
					} // end else if
					else if (cdata) {
						cdata = false;

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						this.tagcontent.reset();

						while ((ic = this.read()) != -1) {
							n = (char) ic;

							if (n == ']') {
								if ((ic = this.read()) == -1) {
									break;
								} // end if

								n = (char) ic;

								if (n == ']') {
									if ((ic = this.read()) == -1) {
										break;
									} // end if

									n = (char) ic;

									if (n == '>') {
										break;
									} // end if
									else {
										this.tagcontent.append(']');
										this.tagcontent.append(']');
										this.tagcontent.append(n);
										continue;
									} // end else
								} // end if
								else {
									this.tagcontent.append(']');
									this.tagcontent.append(n);
									continue;
								} // end else
							} // end if

							this.tagcontent.append(n);
						} // end while

						this.parser.parseCDATA(this.tagcontent.toString());

						if (ic == -1) {
							break;
						} // end if

						continue;
					} // end else if
					else if (doctype) {
						doctype = false;

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						if ((ic = this.read()) == -1) {
							break;
						} // end if

						this.tagcontent.reset();
						this.tagcontent.append("!DOCTYPE");

						while ((ic = this.read()) != -1) {
							n = (char) ic;

							if (n == '>') {
								break;
							} // end if

							if (n == '[') {
								break;
							} // end if

							this.tagcontent.append(n);
						} // end while

						if (n == '[') {
							this.tagcontent.append(n);

							if (ic == -1) {
								break;
							} // end if

							while ((ic = this.read()) != -1) {
								n = (char) ic;

								if (n == ']') {
									this.tagcontent.append(n);

									boolean ableToStop = true;

									while ((ic = this.read()) != -1) {
										n = (char) ic;

										if ((n != '>') && (n != ' ')
												&& (n != '\n') && (n != '\r')
												&& (n != ']')) {
											ableToStop = false;
											this.tagcontent.append(n);

											continue;
										} // end if
										else if (n == ']') {
											ableToStop = true;
										} // end else if
										else if (ableToStop && (n == '>')) {
											break;
										} // end else if

										this.tagcontent.append(n);
									} // end while

									if ((ic == -1) || (n == '>')) {
										break;
									} // end if
								} // end if

								this.tagcontent.append(n);
							} // end while
						} // end if

						this.parser.parseDoctype(this.tagcontent.toString());

						if (ic == -1) {
							break;
						} // end if

						continue;
					} // end else if
					else {
						this.tagcontent.reset();

						boolean decode = false;

						if ((n == '&') || (c == '&')) {
							decode = true;
						} // end if

						this.tagcontent.append(c);
						this.tagcontent.append(n);

						while ((ic = this.read()) != -1) {
							n = (char) ic;

							if (n == '<') {
								break;
							} // end if

							this.tagcontent.append(n);

							if (n == '&') {
								decode = true;
							} // end if
						} // end while

						if (n == '<') {
							this.iprev = n;
							// struct.hasprev = true;
						} // end if

						this.parser.parseText(this.tagcontent.toString(),
								decode);

						if (ic == -1) {
							break;
						} // end if

						continue;
					} // end else
				} // end if
				else {
					this.parser.parseText("" + c, false);
				} // end else
			} // end while

			this.parser.documentEnd();
		} // end try
		catch (final RuntimeException e) {
			throw e;
		} // end catch
		catch (final Exception e) {
			throw new RuntimeException(new SAXException(e));
		} // end catch
		finally {
			this.dt = null;
		} // end finally
	} // end parse()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param tagName
	 *            DOCUMENT ME!
	 * @param tagContent
	 *            DOCUMENT ME!
	 * @param maxoffset
	 *            DOCUMENT ME!
	 */
	private void parseEMPTYTag(String tagName, final CAttr[] attributes,
			final int count, final int indexSep) {
		if (this.caseInsensitive) {
			tagName = tagName.toLowerCase();
		} // end if

		this.firstTag = false;
		this.parser.parseEmptyTag(tagName, attributes, count, indexSep);
	} // end parseEMPTYTag()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param tagName
	 *            DOCUMENT ME!
	 */
	private void parseENDTag(String tagName, final int indexSep) {
		tagName = tagName.substring(1);

		if (this.caseInsensitive) {
			tagName = tagName.toLowerCase();
		} // end if

		this.parser.parseEndTag(tagName, indexSep);
	} // end parseENDTag()

	/**
	 * DOCUMENT ME!
	 * 
	 * @param tagName
	 *            DOCUMENT ME!
	 * @param tagContent
	 *            DOCUMENT ME!
	 * @param maxoffset
	 *            DOCUMENT ME!
	 */
	private void parseSTARTTag(String tagName, final CAttr[] attributes,
			final int count, final int indexSep) {
		if (this.caseInsensitive) {
			tagName = tagName.toLowerCase();
		} // end if

		if (this.firstTag && !this.htmlDocument
				&& tagName.equalsIgnoreCase("html")) {
			if (!this.caseInsensitive) {
				tagName = tagName.toLowerCase();
				this.caseInsensitive = true;
			} // end if
			this.htmlDocument = true;
			this.dt = CXmlParser.dtTr;
		} // end if
		else if (this.htmlDocument && tagName.equalsIgnoreCase("frameset")) {
			this.dt = CXmlParser.dtFr;
		} // end else if

		this.firstTag = false;

		if (this.htmlDocument) {
			if (this.canHaveChild(tagName)) {
				this.parser.parseStartTag(tagName, attributes, count, indexSep);
			} // end if
			else {
				this.parser.parseEmptyTag(tagName, attributes, count, indexSep);
			} // end else
		} // end if
		else {
			this.parser.parseStartTag(tagName, attributes, count, indexSep);
		} // end else
	} // end parseSTARTTag()

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 */
	private int read() throws Exception {
		if (this.iprev != -1) {
			final int ret = this.iprev;
			this.iprev = -1;
			return ret;
		} // end if
		else {
			return this.in.read();
		} // end else
	} // end read()

} // end CXmlParser
