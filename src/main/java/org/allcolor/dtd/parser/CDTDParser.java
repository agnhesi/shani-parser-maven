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
import org.allcolor.dtd.parser.CElement.CAttr;
import org.allcolor.xml.parser.CDOM2SAX;
import org.allcolor.xml.parser.CStringBuilder;
import org.allcolor.xml.parser.CStringTokenizer;
import org.allcolor.xml.parser.CXmlParser;
import org.allcolor.xml.parser.dom.ADocument;
import org.allcolor.xml.parser.dom.CDom2HTMLDocument;

import org.w3c.dom.Node;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CDTDParser {
	/** DOCUMENT ME! */
	private static Map internalEntities = null;

	/** DOCUMENT ME! */
	private static ThreadLocal localEntityResolver = new ThreadLocal();

	static {
		Map internalEntities = new HashMap();
		ADocument doc = new CDom2HTMLDocument();
		CEntity ent;
		ent = new CEntity("", "amp", "&", null, null, false, doc	);
		ent.appendChild(doc.createTextNode("&"));
		internalEntities.put("amp",ent);
		ent = new CEntity("", "lt", "<", null, null, false, doc	);
		ent.appendChild(doc.createTextNode("<"));
		internalEntities.put("lt",ent);
		ent = new CEntity("", "gt", ">", null, null, false, doc	);
		ent.appendChild(doc.createTextNode(">"));
		internalEntities.put("gt",ent);
		ent = new CEntity("", "apos", "'", null, null, false, doc	);
		ent.appendChild(doc.createTextNode("'"));
		internalEntities.put("apos",ent);
		ent = new CEntity("", "quot", "\"", null, null, false, doc	);
		ent.appendChild(doc.createTextNode("\""));
		internalEntities.put("quot",ent);
		CDTDParser.internalEntities = Collections.unmodifiableMap(internalEntities);
	} 
	
	private static long maxEntityContentSize = 1024*1024*10;

	/**
	 * DOCUMENT ME!
	 *
	 * @param resolver DOCUMENT ME!
	 */
	public static void setEntityResolver(final EntityResolver resolver) {
		localEntityResolver.set(resolver);
	} // end setEntityResolver()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static EntityResolver getEntityResolver() {
		return (EntityResolver) localEntityResolver.get();
	} // end getEntityResolver()

	/**
	 * DOCUMENT ME!
	 *
	 * @param u DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public static String load(final URL u)
		throws Exception {
		BufferedReader reader = null;

		try {
			URLConnection connection = u.openConnection();

			// only 1.5
			// connection.setReadTimeout(5000);
			reader = new BufferedReader(CXmlParser.getReader(
						connection.getInputStream()));

			return load(reader);
		} // end try
		catch (Exception e) {
			throw e;
		}
		finally {
			try {
				reader.close();
			} // end try
			catch (final Exception ignore) {}
		} // end finally
	} // end load()

	/**
	 * DOCUMENT ME!
	 *
	 * @param reader DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public static String load(final BufferedReader reader)
		throws Exception {
		try {
			CStringBuilder result = new CStringBuilder();
			String		   line = null;

			while ((line = reader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			} // end while

			return result.toString();
		} // end try
		finally {
			try {
				reader.close();
			} // end try
			catch (final Exception ignore) {}
		} // end finally
	} // end load()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param documentURI DOCUMENT ME!
	 * @param doctypeList DOCUMENT ME!
	 * @param knownEntities DOCUMENT ME!
	 * @param knownElement DOCUMENT ME!
	 * @param document DOCUMENT ME!
	 */
	public static void parse(
		final String in,
		String documentURI,
		final List doctypeList,
		final Map knownEntities,
		final Map knownElement,
		final ADocument document) {
		if (documentURI != null) {
			documentURI = documentURI.substring(0,
					documentURI.lastIndexOf("/") + 1);
		}

		boolean booContinue = true;
		String  tmp = in;
		tmp = resolveKnownEntities(tmp, knownEntities);
		String tmpbef = tmp;
		int start = 0;
		while (booContinue) {
			// resolve known entities
			CP2StringTokenizer tokenizer     = new CP2StringTokenizer(tmp);
			if (start > 0 && tmpbef.equals(tmp)) break;
			start++;
			boolean			   hasMoreTokens = tokenizer.hasMoreTokens();

			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				if (token.equals("<")) {
					String content   = null;
					String nextToken = null;

					if (tokenizer.hasMoreTokens()) {
						content = tokenizer.nextToken();

						if (content.startsWith("!--")) {
							while ((!(content.endsWith("--"))) &&
									(tokenizer.hasMoreTokens())) {
								content = content +
									tokenizer.nextToken();
							} // end while
						}
					} // end if

					if (tokenizer.hasMoreTokens()) {
						nextToken = tokenizer.nextToken();
					} // end if

					hasMoreTokens = tokenizer.hasMoreTokens();
					while (!">".equals(nextToken) &&
							tokenizer.hasMoreTokens()) {
						if (content.endsWith("\"")) {
							content += nextToken;
							while (!content.endsWith("\"") &&
									tokenizer.hasMoreTokens()) {
								content += tokenizer.nextToken();
							}
							nextToken = null;
						} else {
							if (nextToken != null)
								content += nextToken;
						}
						if (tokenizer.hasMoreTokens())
							nextToken = tokenizer.nextToken();
						if (">".equals(nextToken)) {
							char [] array = content.toCharArray();
							int o = 0,c = 0;
							for (int i=0;i<array.length;i++) {
								if (array[i] == '<') o++;
								if (array[i] == '>') c++;
							}
							if (o != c) {
								content += nextToken;
								if (tokenizer.hasMoreTokens())
									nextToken = tokenizer.nextToken();
							}
						}
					}
					if (">".equals(nextToken)) {
						if (content.startsWith("!--")) {
							continue;
						} // end if

						String befToken = content;
						content = resolveKnownEntities(content,
								knownEntities);

						if (content.startsWith("!DOCTYPE")) {
							// found a doctype definition
							CDocType doctype = parseDoctype(content,
									documentURI, document, true);
							doctypeList.add(doctype);

							if (doctype.getSystemId() != null) {
								try {
									String URI   = doctype.getSystemId();
									int    index = URI.lastIndexOf("/");

									if (index != -1) {
										URI = URI.substring(0, index +
												1);
									} // end if

									String		   val	    = null;
									EntityResolver resolver = CDTDParser.getEntityResolver();

									if (resolver != null) {
										InputSource source = resolver.resolveEntity(doctype.getPublicId(),
												doctype.getSystemId());

										if (source != null) {
											InputStream inStream = source.getByteStream();

											if (inStream == null) {
												BufferedReader reader = new BufferedReader((Reader) source.getCharacterStream());

												if (reader != null) {
													val = CDTDParser.load(reader);
												} // end if
											} // end if
											else {
												val = CDTDParser.load(new BufferedReader(
															CXmlParser.getReader(
																inStream)));
											} // end else
										} // end if
										else {
											URL u = new URL(doctype.getSystemId());
											val = CDTDParser.load(u);
										} // end else
									} // end if
									else {
										URL u = new URL(doctype.getSystemId());
										val = CDTDParser.load(u);
									} // end else

									parse(val, URI, doctypeList,
										knownEntities, knownElement,
										document);
								} // end try
								catch (final Exception ignore) {}
							} // end if
						} // end if
						else if (content.startsWith("!ENTITY")) {
							// found an entity definition
							CEntity entity = parseEntity(content,
									documentURI, document);

							if (entity != null) {
								if (!knownEntities.containsKey(entity.getEntityName()))
									knownEntities.put(entity.getEntityName(),
											entity);
								else {
									CEntity ent = (CEntity)knownEntities.get(entity.getEntityName());
									if (ent.toString().equals(entity.toString())) {
										return;
									}
								}
							} // end if
						} // end else if
						else if (content.startsWith("!NOTATION")) {
							// found an entity definition
							CNotation notation = parseNotation(content,
									documentURI, document);

							if (notation != null) {
								knownElement.put(notation.getNodeName(),
									notation);
							} // end if
						} // end else if
						else if (content.startsWith("!ELEMENT")) {
							// found an element definition
							CElement elem = parseElement(content);
							if (elem != null) {
								knownElement.put(elem.getName(), elem);
							} // end if
						} // end else if
						else if (content.startsWith("!ATTLIST")) {
							// found an attribute definition
							parseAttList(content,knownElement);
						} // end else if
						else if (content.startsWith("![")) {
							// found an include/exclude definition
						} // end else if
						if (!befToken.equals(content)) {
							tmp = getRest(tokenizer);

							break;
						} // end if
					}
				} // end if
				else {
					hasMoreTokens = tokenizer.hasMoreTokens();

					String befToken = token;
					token = resolveKnownEntities(token, knownEntities);
					if (!befToken.equals(token)) {
						tmp = token + getRest(tokenizer);

						break;
					} // end if
				} // end else
			} // end while
			if (!hasMoreTokens) {
				booContinue = false;
			}
		} // end while
	} // end parse()

	private static void parseAttList(String in,final Map knownElement) {
		in = in.substring("!ATTLIST".length()).trim();
		String elementName = new CStringTokenizer(in," \n\r\t",false).nextToken();
		CElement elem = (CElement)knownElement.get(elementName);
		if (elem == null) {
			elem = new CElement(elementName,"*");
			knownElement.put(elementName,elem);
		}
		in = in.substring(in.indexOf(elementName)+elementName.length()).trim();
		while (in.length() > 0) {
			String attrName = new CStringTokenizer(in," \n\r\t",false).nextToken();
			in = in.substring(in.indexOf(attrName)+attrName.length()).trim();
			String type = null;
			if (in.startsWith("(")) {
				type = in.substring(0,in.indexOf(")")+1);
			} else {
				type = new CStringTokenizer(in," \n\r\t",false).nextToken();
			}
			in = in.substring(in.indexOf(type)+type.length()).trim();
			String defaultValue = null;
			if (in.startsWith("\"") ||
				in.startsWith("'")) {
				in = in.substring(1);
				int index = in.indexOf("\"");
				if (index == -1 || (in.indexOf("'") > -1 && in.indexOf("'") < index))
					index = in.indexOf("'");
				defaultValue = in.substring(0,index);
				in = in.substring(index+1);
			} else {
				defaultValue = new CStringTokenizer(in," \n\r\t",false).nextToken();
				in = in.substring(in.indexOf(defaultValue)+defaultValue.length()).trim();
			}
			if (defaultValue.startsWith("#FIXED")) {
				if (in.startsWith("\"") ||
					in.startsWith("'")) {
					in = in.substring(1);
					int index = in.indexOf("\"");
					if (index == -1 || (in.indexOf("'") > -1 && in.indexOf("'") < index))
						index = in.indexOf("'");
					defaultValue = in.substring(0,index);
					in = in.substring(index+1);
				} else {
					defaultValue = new CStringTokenizer(in," \n\r\t",false).nextToken();
					in = in.substring(in.indexOf(defaultValue)+defaultValue.length()).trim();
				}
			}
			if ("CDATA".equals(type)) {
				elem.addAttribute(attrName,CElement.CDATA,defaultValue,null);
			} else if ("ID".equals(type)) {
				elem.addAttribute(attrName,CElement.ID,defaultValue,null);
			} else if (type.trim().startsWith("(")) {
				elem.addAttribute(attrName,CElement.CDATA,defaultValue,type);
			} else if ("NMTOKEN".equals(type)) {
				elem.addAttribute(attrName,CElement.NMTOKEN,defaultValue,null);
			} else if ("NMTOKENS".equals(type)) {
				elem.addAttribute(attrName,CElement.NMTOKENS,defaultValue,null);
			} else if ("IDREF".equals(type)) {
				elem.addAttribute(attrName,CElement.IDREF,defaultValue,null);
			} else {
				elem.addAttribute(attrName,CElement.CDATA,defaultValue,type);
			}
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param documentURI DOCUMENT ME!
	 * @param document DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static CDocType parse(
		final String in,
		final String documentURI,
		final ADocument document) {
		Map  knownEntities = new HashMap();
		Map  knownElement = new HashMap();
		List doctypeList  = new ArrayList();
		parse(in, documentURI, doctypeList, knownEntities,
			knownElement, document);

		if (doctypeList.size() > 0) {
			CDocType doctype = (CDocType) doctypeList.get(0);
			doctype.setKnownEntities(knownEntities);
			doctype.setKnownElements(knownElement);

			return doctype;
		} // end if

		CDocType doctype = parseDoctype(in, documentURI,	document, true);
		doctype.setKnownEntities(knownEntities);
		doctype.setKnownElements(knownElement);

		return doctype;
	} // end parse()

	private static final Map DTD_CACHE = new Hashtable();
	
	private static class CCacheEntry {
		CDocType dt;
		String in;
		
		public CCacheEntry(CDocType dt,String in) {
			this.dt = dt;
			this.in = in;
		}
		
		public void finalize() throws Throwable {
			super.finalize();
			this.dt = null;
			DTD_CACHE.remove(in);
		}
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param documentURI DOCUMENT ME!
	 * @param doc DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static CDocType parseDoctype(
		final String in,
		final String documentURI,
		final ADocument doc) {
		SoftReference ref = (SoftReference)DTD_CACHE.get(in);
		if (ref != null) {
			CCacheEntry entry = (CCacheEntry)ref.get();
			if (entry == null) {
				DTD_CACHE.remove(in);
			} else {
				try {
					CDocType dt = entry.dt;
					dt = (CDocType)dt.clone();
					dt.setOwnerDocument(doc);
					dt.setParent(null);
					return dt;
				}
				catch (CloneNotSupportedException e) {
					DTD_CACHE.remove(in);
				}
			}
		}
		CDocType dt = parseDoctype(in, documentURI, doc, false);
		DTD_CACHE.put(in,new SoftReference(new CCacheEntry(dt,in)));
		return dt;
	} // end parseDoctype()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param documentURI DOCUMENT ME!
	 * @param doc DOCUMENT ME!
	 * @param returnDirectlyIfNotFound DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static CDocType parseDoctype(
		String in,
		String documentURI,
		final ADocument doc,
		final boolean returnDirectlyIfNotFound) {
		if (documentURI != null) {
			documentURI = documentURI.substring(0,
					documentURI.lastIndexOf("/") + 1);
		}
		
		String name     = null;
		String systemId = null;
		String publicId = null;
		String tmp	    = in.substring(8).trim();
		int    iSp	    = tmp.indexOf(" ");
		int    iSp2     = tmp.indexOf("\n");

		if ((iSp2 < iSp) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\r");

		if ((iSp2 < iSp) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		if (iSp != -1) {
			name     = tmp.substring(0, iSp).trim();
			tmp		 = tmp.substring(iSp + 1).trim();

			if (tmp.startsWith("PUBLIC")) {
				tmp = tmp.substring(6).trim();

				if (tmp.startsWith("\"")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("\"");

					if (iSp != -1) {
						publicId     = tmp.substring(0, iSp);
						tmp			 = tmp.substring(iSp + 1).trim();

						if (tmp.startsWith("\"")) {
							tmp     = tmp.substring(1);
							iSp     = tmp.indexOf("\"");

							if (iSp != -1) {
								systemId = tmp.substring(0, iSp);
							} // end if
						} // end if
					} // end if
				} // end if
			} // end if
			else if (tmp.startsWith("SYSTEM")) {
				tmp = tmp.substring(6).trim();

				if (tmp.startsWith("\"")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("\"");

					if (iSp != -1) {
						systemId = tmp.substring(0, iSp);
					} // end if
				} // end if
			} // end else if
		} // end if

		if ((systemId != null) && (systemId.indexOf(":/") != -1)) {
			try {
				String		   val	    = null;
				EntityResolver resolver = CDTDParser.getEntityResolver();

				if (resolver != null) {
					InputSource source = resolver.resolveEntity(publicId,
							systemId);

					if (source != null) {
						InputStream inStream = source.getByteStream();

						if (inStream == null) {
							BufferedReader reader = new BufferedReader((Reader) source.getCharacterStream());

							if (reader != null) {
								val = CDTDParser.load(reader);
							} // end if
						} // end if
						else {
							val = CDTDParser.load(new BufferedReader(
										CXmlParser.getReader(inStream)));
						} // end else
					} // end if
					else {
						URL u = new URL(systemId);
						val = CDTDParser.load(u);
					} // end else
				} // end if
				else {
					URL u = new URL(systemId);
					val = CDTDParser.load(u);
				} // end else

				CDocType dt = parse(val,
						systemId.substring(0,
							systemId.lastIndexOf("/") + 1), doc);

				if ((in.indexOf('[') != -1) &&
						(in.lastIndexOf(']') != -1)) {
					in = in.substring(in.indexOf('[') + 1,
							in.lastIndexOf(']'));

					if (documentURI == null) {
						documentURI = systemId.substring(0,
								systemId.lastIndexOf("/") + 1);
					} // end if

					CDocType dt2   = parse(in, documentURI, doc);
					Map		 map   = dt2.getKnownElements();
					Map		 toAdd = dt.getKnownElements();

					for (Iterator it = map.entrySet().iterator();
					it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						if (toAdd.containsKey(entry.getKey())) {
							if (entry.getValue() instanceof CElement) {
								CElement elem = (CElement)entry.getValue();
								CElement t = (CElement)toAdd.get(entry.getKey());
								Map m = elem.getAttributes();
								for (Iterator iit = m.entrySet().iterator();iit.hasNext();) {
									entry = (Map.Entry)iit.next();
									t.getAttributes().put(entry.getKey(),entry.getValue());
								}
							}
						} else {
							toAdd.put(entry.getKey(), entry.getValue());
						}
					} // end for

					dt.setKnownElements(toAdd);
					map		  = dt2.getKnownEntities();
					toAdd     = dt.getKnownEntities();

					for (Iterator it = map.entrySet().iterator();
							it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						if (toAdd.containsKey(entry.getKey())) continue;
						toAdd.put(entry.getKey(), entry.getValue());
					} // end for

					dt.setKnownEntities(toAdd);
				} // end if

				dt.setName(name);
				dt.setContent(in);
				dt.setSystemId(systemId);
				dt.setPublicId(publicId);
				return dt;
			} // end try
			catch (final Exception ignore) {
				try {
					if (in.indexOf("[") != -1) {
						CDocType dt = parse("!DOCTYPE " + name + " " +
								in.substring(in.indexOf("[")),
								documentURI, doc);
						dt.setName(name);
						dt.setContent(in);
						dt.setSystemId(systemId);
						dt.setPublicId(publicId);

						return dt;
					} // end if
					else {
						CDocType dt = new CDocType(in, name, publicId,
								systemId, doc);
						dt.setName(name);
						dt.setContent(in);
						dt.setSystemId(systemId);
						dt.setPublicId(publicId);

						return dt;
					} // end else
				} // end try
				catch (final Exception innerIgnore) {
					CDocType dt = new CDocType(in, name, publicId,
							systemId, doc);
					dt.setName(name);
					dt.setContent(in);
					dt.setSystemId(systemId);
					dt.setPublicId(publicId);

					return dt;
				} // end catch
			} // end catch
		} // end if

		if ((documentURI != null) && (systemId != null)) {
			String URI   = documentURI;
			int    index = URI.lastIndexOf("/");

			if (index != -1) {
				URI = URI.substring(0, index + 1);
			}
			try {
				String		   val	    = null;
				EntityResolver resolver = CDTDParser.getEntityResolver();

				if (resolver != null) {
					InputSource source = resolver.resolveEntity(publicId,
							URI + systemId);

					if (source != null) {
						InputStream inStream = source.getByteStream();

						if (inStream == null) {
							BufferedReader reader = new BufferedReader((Reader) source.getCharacterStream());

							if (reader != null) {
								val = CDTDParser.load(reader);
							} // end if
						} // end if
						else {
							val = CDTDParser.load(new BufferedReader(
										CXmlParser.getReader(inStream)));
						} // end else
					} // end if
					else {
						URL u = new URL(URI + systemId);
						val = CDTDParser.load(u);
					} // end else
				} // end if
				else {
					URL u = new URL(URI + systemId);
					val = CDTDParser.load(u);
				} // end else

				if ((in.indexOf('[') != -1) &&
						(in.lastIndexOf(']') != -1)) {
					val = val +
						in.substring(in.indexOf('[') + 1,
							in.lastIndexOf(']'));
				} // end if

				CDocType dt = parse(val, documentURI, doc);

				if ((in.indexOf('[') != -1) &&
						(in.lastIndexOf(']') != -1)) {
					in = in.substring(in.indexOf('[') + 1,
							in.lastIndexOf(']'));

					CDocType dt2   = parse(in, documentURI, doc);
					Map		 map   = dt2.getKnownElements();
					Map		 toAdd = dt.getKnownElements();

					for (Iterator it = map.entrySet().iterator();
							it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						if (toAdd.containsKey(entry.getKey())) {
							if (entry.getValue() instanceof CElement) {
								CElement elem = (CElement)entry.getValue();
								CElement t = (CElement)toAdd.get(entry.getKey());
								Map m = elem.getAttributes();
								for (Iterator iit = m.entrySet().iterator();iit.hasNext();) {
									entry = (Map.Entry)iit.next();
									t.getAttributes().put(entry.getKey(),entry.getValue());
								}
							}
						} else {
							toAdd.put(entry.getKey(), entry.getValue());
						}
					} // end for
					dt.setKnownElements(toAdd);
					map		  = dt2.getKnownEntities();
					toAdd     = dt.getKnownEntities();

					for (Iterator it = map.entrySet().iterator();
							it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						if (toAdd.containsKey(entry.getKey())) continue;
						toAdd.put(entry.getKey(), entry.getValue());
					} // end for

					dt.setKnownEntities(toAdd);
				} // end if

				dt.setName(name);
				dt.setContent(in);
				dt.setSystemId(systemId);
				dt.setPublicId(publicId);
				return dt;
			} // end try
			catch (final Exception e) {
				try {
					if (in.indexOf("[") != -1) {
						CDocType dt = parse("!DOCTYPE " + name + " " +
								in.substring(in.indexOf("[")),
								documentURI, doc);
						dt.setName(name);
						dt.setContent(in);
						dt.setSystemId(systemId);
						dt.setPublicId(publicId);

						return dt;
					} // end if

					return new CDocType(in, name, publicId,	systemId, doc);
				} // end try
				catch (final Exception innerIgnore) {
					return new CDocType(in, name, publicId, systemId, doc);
				} // end catch
			} // end catch
		} // end if

		if (!returnDirectlyIfNotFound) {
			CDocType dt = parse(in, documentURI, doc);
			dt.setName(name);
			dt.setContent(in);
			dt.setSystemId(systemId);
			dt.setPublicId(publicId);
			return dt;
		} // end if

		return new CDocType(in, name, publicId, systemId, doc);
	} // end parseDoctype()

	/**
	 * DOCUMENT ME!
	 *
	 * @param toDecode DOCUMENT ME!
	 * @param entityMap DOCUMENT ME!
	 * @param resolveInternalEntities DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static String replaceEntities(
		final String toDecode,
		final Map entityMap,
		final boolean resolveInternalEntities) {
		return replaceEntities(toDecode, entityMap,
			resolveInternalEntities, null, null, null, true);
	} // end replaceEntities()

	/**
	 * DOCUMENT ME!
	 *
	 * @param toDecode DOCUMENT ME!
	 * @param entityMap DOCUMENT ME!
	 * @param resolveInternalEntities DOCUMENT ME!
	 * @param cHandler DOCUMENT ME!
	 * @param dHandler DOCUMENT ME!
	 * @param dtdHandler DOCUMENT ME!
	 * @param replace DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static String replaceEntities(
		final String    toDecode,
		final Map		entityMap,
		final boolean   resolveInternalEntities,
		final ContentHandler cHandler,
		final DocumentHandler dHandler,
		final DTDHandler dtdHandler,
		final boolean   replace) {
		try {
		if (toDecode == null) {
			return "";
		}
		if (entityMap.size() == 0) {
			if (cHandler != null) {
				cHandler.characters(toDecode.toCharArray(), 0,
						toDecode.length());
			} // end if

			if (dHandler != null) {
				dHandler.characters(toDecode.toCharArray(), 0,
						toDecode.length());
			} // end if
			return toDecode;
		}

		CPStringTokenizer tokenizer     = new CPStringTokenizer(toDecode);
		String			  previousToken = null;
		CStringBuilder    result	    = new CStringBuilder();

		while (tokenizer.hasMoreTokens()) {
			if (result.length() > (maxEntityContentSize)) {
				throw new RuntimeException("Too BIG entity content, stop processing.");
			}
			String token = null;

			if (previousToken != null) {
				token			  = previousToken;
				previousToken     = null;
			} // end if
			else {
				token = tokenizer.nextToken();
			} // end else

			if ("&".equals(token)) {
				String content   = null;
				String nextToken = null;

				if (tokenizer.hasMoreTokens()) {
					content = tokenizer.nextToken();

					if ((content.equals("%")) || (content.equals("&"))) {
						previousToken = content;
						result.append(token);

						if (cHandler != null) {
							cHandler.characters(token.toCharArray(), 0,
								token.length());
						} // end if

						if (dHandler != null) {
							dHandler.characters(token.toCharArray(), 0,
								token.length());
						} // end if

						continue;
					} // end if
				} // end if

				if (tokenizer.hasMoreTokens()) {
					nextToken = tokenizer.nextToken();

					if ((nextToken.equals("%")) ||
							(nextToken.equals("&"))) {
						previousToken = nextToken;
						result.append(token);
						result.append(content);

						if (cHandler != null) {
							cHandler.characters((token + content).toCharArray(),
								0, content.length() + token.length());
						} // end if

						if (dHandler != null) {
							dHandler.characters((token + content).toCharArray(),
								0, content.length() + token.length());
						} // end if

						continue;
					} // end if
				} // end if

				if ((content != null) && (nextToken != null)) {
					if (nextToken.equals(";")) {
						if (content.startsWith("#x")) {
							// hexa ref
							try {
								char value = ((char) Integer.parseInt(content.substring(
											2), 16));

								if (dtdHandler != null) {
									dtdHandler.notationDecl(content,
										"" + value, null);
								} // end if

								if ((cHandler != null) &&
										cHandler instanceof DefaultHandler2) {
									((DefaultHandler2) cHandler).startEntity(content);
								} // end if

								if (replace) {
									result.append(value);

									if (cHandler != null) {
										cHandler.characters(new char [] {
												value
											}, 0, 1);
									} // end if

									if (dHandler != null) {
										dHandler.characters(new char [] {
												value
											}, 0, 1);
									} // end if
								} // end if
								else {
									result.append(content);

									if (cHandler != null) {
										cHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if

									if (dHandler != null) {
										dHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if
								} // end else

								if ((cHandler != null) &&
										cHandler instanceof DefaultHandler2) {
									((DefaultHandler2) cHandler).endEntity(content);
								} // end if
							} // end try
							catch (final Exception ignore) {
								result.append("&");
								result.append(content);
								result.append(";");

								if (cHandler != null) {
									cHandler.characters(content.toCharArray(),
										0, content.length());
								} // end if

								if (dHandler != null) {
									dHandler.characters(content.toCharArray(),
										0, content.length());
								} // end if
							} // end catch
						} // end if
						else if (content.startsWith("#")) {
							// numeric ref
							try {
								char value = ((char) Integer.parseInt(content.substring(
											1)));
								if (dtdHandler != null) {
									dtdHandler.notationDecl(content,
										"" + value, null);
								} // end if

								if ((cHandler != null) &&
										cHandler instanceof DefaultHandler2) {
									((DefaultHandler2) cHandler).startEntity(content);
								} // end if

								if (replace) {
									result.append(value);

									if (cHandler != null) {
										cHandler.characters(new char [] {
												value
											}, 0, 1);
									} // end if

									if (dHandler != null) {
										dHandler.characters(new char [] {
												value
											}, 0, 1);
									} // end if
								} // end if
								else {
									result.append(content);

									if (cHandler != null) {
										cHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if

									if (dHandler != null) {
										dHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if
								} // end else

								if ((cHandler != null) &&
										cHandler instanceof DefaultHandler2) {
									((DefaultHandler2) cHandler).endEntity(content);
								} // end if
							} // end try
							catch (final Exception ignore) {
								result.append("&");
								result.append(content);
								result.append(";");

								if (cHandler != null) {
									cHandler.characters(content.toCharArray(),
										0, content.length());
								} // end if

								if (dHandler != null) {
									dHandler.characters(content.toCharArray(),
										0, content.length());
								} // end if
							} // end catch
						} // end else if
						else {
							if (resolveInternalEntities &&
									internalEntities.containsKey(
										content)) {
								CEntity value = (CEntity) internalEntities.get(content);

								if (dtdHandler != null) {
									dtdHandler.notationDecl(content,
										value.getValue(), null);
								} // end if

								if ((cHandler != null) &&
										cHandler instanceof DefaultHandler2) {
									((DefaultHandler2) cHandler).startEntity(content);
								} // end if

								if (replace) {
									if ((value.getValue() == null) &&
											(value.getDOMValue() != null)) {
										result.append(value.getDOMValue().toString());
										SaxIt(value.getDOMValue(), cHandler, dHandler,
											dtdHandler);
									} // end if
									else {
										result.append(value.getValue());

										if (cHandler != null) {
											cHandler.characters(value.getValue()
																		 .toCharArray(),
												0,
												value.getValue().length());
										} // end if

										if (dHandler != null) {
											dHandler.characters(value.getValue()
																		 .toCharArray(),
												0,
												value.getValue().length());
										} // end if
									} // end else
								} // end if
								else {
									result.append(content);

									if (cHandler != null) {
										cHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if

									if (dHandler != null) {
										dHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if
								} // end else

								if ((cHandler != null) &&
										cHandler instanceof DefaultHandler2) {
									((DefaultHandler2) cHandler).endEntity(content);
								} // end if
							} // end if
							else {
								CEntity value = (CEntity) entityMap.get(content);

								if ((value == null) ||
										(!resolveInternalEntities &&
										internalEntities.containsKey(
											content))) {
									if (dtdHandler != null) {
										dtdHandler.notationDecl(content,
											"&" + content + ";", null);
									} // end if

									if ((cHandler != null) &&
											cHandler instanceof DefaultHandler2) {
										((DefaultHandler2) cHandler).startEntity(content);
									} // end if

									result.append("&");
									result.append(content);
									result.append(";");

									if (cHandler != null) {
										cHandler.characters(("&" +
											content + ";").toCharArray(),
											0, content.length() + 2);
									} // end if

									if (dHandler != null) {
										dHandler.characters(("&" +
											content + ";").toCharArray(),
											0, content.length() + 2);
									} // end if

									if ((cHandler != null) &&
											cHandler instanceof DefaultHandler2) {
										((DefaultHandler2) cHandler).endEntity(content);
									} // end if
								} // end if
								else {
									if (dtdHandler != null) {
										dtdHandler.notationDecl(content,
											value.getValue(), null);
									} // end if

									if ((cHandler != null) &&
											cHandler instanceof DefaultHandler2) {
										((DefaultHandler2) cHandler).startEntity(content);
									} // end if

									if (replace) {
										if ((value.getValue() == null) &&
												(value.getDOMValue() != null)) {
											result.append(value.getDOMValue().toString());
											SaxIt(value.getDOMValue(), cHandler,
												dHandler, dtdHandler);
										} // end if
										else if (value.getValue() != null) {
											result.append(value.getValue());
											
											if (cHandler != null) {
												cHandler.characters(value.getValue()
																			 .toCharArray(),
													0,
													value.getValue()
															 .length());
											} // end if

											if (dHandler != null) {
												dHandler.characters(value.getValue()
																			 .toCharArray(),
													0,
													value.getValue()
															 .length());
											} // end if
										} // end else
									} // end if
									else {
										result.append(content);

										if (cHandler != null) {
											cHandler.characters(content.toCharArray(),
												0, content.length());
										} // end if

										if (dHandler != null) {
											dHandler.characters(content.toCharArray(),
												0, content.length());
										} // end if
									} // end else

									if ((cHandler != null) &&
											cHandler instanceof DefaultHandler2) {
										((DefaultHandler2) cHandler).endEntity(content);
									} // end if
								} // end else
							} // end else
						} // end else
					} // end if
					else {
						result.append(token);
						result.append(content);
						result.append(nextToken);

						if (cHandler != null) {
							cHandler.characters((token + content +
								nextToken).toCharArray(), 0,
								token.length() + content.length() +
								nextToken.length());
						} // end if

						if (dHandler != null) {
							dHandler.characters((token + content +
								nextToken).toCharArray(), 0,
								token.length() + content.length() +
								nextToken.length());
						} // end if
					} // end else
				} // end if
				else if (content != null) {
					result.append(token);
					result.append(content);

					if (cHandler != null) {
						cHandler.characters((token + content).toCharArray(),
							0, token.length() + content.length());
					} // end if

					if (dHandler != null) {
						dHandler.characters((token + content).toCharArray(),
							0, token.length() + content.length());
					} // end if
				} // end else if
				else {
					result.append(token);

					if (cHandler != null) {
						cHandler.characters(token.toCharArray(), 0,
							token.length());
					} // end if

					if (dHandler != null) {
						dHandler.characters(token.toCharArray(), 0,
							token.length());
					} // end if
				} // end else
			} // end if
			else if ("%".equals(token)) {
				String content   = null;
				String nextToken = null;

				if (tokenizer.hasMoreTokens()) {
					content = tokenizer.nextToken();

					if ((content.equals("%")) || (content.equals("&"))) {
						previousToken = content;
						result.append(token);

						if (cHandler != null) {
							cHandler.characters(token.toCharArray(), 0,
								token.length());
						} // end if

						if (dHandler != null) {
							dHandler.characters(token.toCharArray(), 0,
								token.length());
						} // end if

						continue;
					} // end if
				} // end if

				if (tokenizer.hasMoreTokens()) {
					nextToken = tokenizer.nextToken();

					if ((nextToken.equals("%")) ||
							(nextToken.equals("&"))) {
						previousToken = nextToken;
						result.append(token);
						result.append(content);

						if (cHandler != null) {
							cHandler.characters((token + content).toCharArray(),
								0, token.length() + content.length());
						} // end if

						if (dHandler != null) {
							dHandler.characters((token + content).toCharArray(),
								0, token.length() + content.length());
						} // end if

						continue;
					} // end if
				} // end if

				if ((content != null) && (nextToken != null)) {
					if (nextToken.equals(";")) {
						if (resolveInternalEntities &&
								internalEntities.containsKey(content)) {
							CEntity value = (CEntity) internalEntities.get(content);

							if (replace) {
								if ((value.getValue() == null) &&
										(value.getDOMValue() != null)) {
									result.append(value.getDOMValue().toString());
									SaxIt(value.getDOMValue(), cHandler, dHandler,
										dtdHandler);
								} // end if
								else {
									result.append(value.getValue());

									if (cHandler != null) {
										cHandler.characters(value.getValue()
																	 .toCharArray(),
											0, value.getValue().length());
									} // end if

									if (dHandler != null) {
										dHandler.characters(value.getValue()
																	 .toCharArray(),
											0, value.getValue().length());
									} // end if
								} // end else
							} // end if
							else {
								result.append(content);

								if (cHandler != null) {
									cHandler.characters(content.toCharArray(),
										0, content.length());
								} // end if

								if (dHandler != null) {
									dHandler.characters(content.toCharArray(),
										0, content.length());
								} // end if
							} // end else
						} // end if
						else {
							CEntity value = (CEntity) entityMap.get(content);

							if ((value == null) ||
									(!resolveInternalEntities &&
									internalEntities.containsKey(
										content))) {

								result.append("%");
								result.append(content);
								result.append(";");

								if (cHandler != null) {
									cHandler.characters(("%" + content +
										";").toCharArray(), 0,
										content.length() + 2);
								} // end if

								if (dHandler != null) {
									dHandler.characters(("%" + content +
										";").toCharArray(), 0,
										content.length() + 2);
								} // end if

							} // end if
							else {

								if (replace) {
									if ((value.getValue() == null) &&
											(value.getDOMValue() != null)) {
										result.append(value.getDOMValue().toString());
										SaxIt(value.getDOMValue(), cHandler, dHandler,
											dtdHandler);
									} // end if
									else {
										result.append(value.getValue());

										if (cHandler != null) {
											cHandler.characters(value.getValue()
																		 .toCharArray(),
												0,
												value.getValue().length());
										} // end if

										if (dHandler != null) {
											dHandler.characters(value.getValue()
																		 .toCharArray(),
												0,
												value.getValue().length());
										} // end if
									} // end else
								} // end if
								else {
									result.append(content);

									if (cHandler != null) {
										cHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if

									if (dHandler != null) {
										dHandler.characters(content.toCharArray(),
											0, content.length());
									} // end if
								} // end else
							} // end else
						} // end else
					} // end if
					else {
						result.append(token);
						result.append(content);
						result.append(nextToken);

						if (cHandler != null) {
							cHandler.characters((token + content +
								nextToken).toCharArray(), 0,
								token.length() + content.length() +
								nextToken.length());
						} // end if

						if (dHandler != null) {
							dHandler.characters((token + content +
								nextToken).toCharArray(), 0,
								token.length() + content.length() +
								nextToken.length());
						} // end if
					} // end else
				} // end if
				else if (content != null) {
					result.append(token);
					result.append(content);

					if (cHandler != null) {
						cHandler.characters((token + content).toCharArray(),
							0, token.length() + content.length());
					} // end if

					if (dHandler != null) {
						dHandler.characters((token + content).toCharArray(),
							0, token.length() + content.length());
					} // end if
				} // end else if
				else {
					result.append(token);

					if (cHandler != null) {
						cHandler.characters(token.toCharArray(), 0,
							token.length());
					} // end if

					if (dHandler != null) {
						dHandler.characters(token.toCharArray(), 0,
							token.length());
					} // end if
				} // end else
			} // end else if
			else {
				result.append(token);

				if (cHandler != null) {
					cHandler.characters(token.toCharArray(), 0,
						token.length());
				} // end if

				if (dHandler != null) {
					dHandler.characters(token.toCharArray(), 0,
						token.length());
				} // end if
			} // end else
		} // end while

		if (previousToken != null) {
			result.append(previousToken);

			if (cHandler != null) {
				cHandler.characters(previousToken.toCharArray(), 0,
					previousToken.length());
			} // end if

			if (dHandler != null) {
				dHandler.characters(previousToken.toCharArray(), 0,
					previousToken.length());
			} // end if
		} // end if

		return result.toString();
		}
		catch (SAXException e) {
			return toDecode;
		}
	} // end replaceEntities()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param entityMap DOCUMENT ME!
	 * @param resolveInternalEntities DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static String resolveKnownEntities(
		final String in,
		final Map entityMap,
		final boolean resolveInternalEntities) {
		// remove comment
		CStringBuilder     buffer    = new CStringBuilder();
		CP2StringTokenizer tokenizer = new CP2StringTokenizer(in);

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			if (token.equals("<")) {
				buffer.append(token);

				String content = null;

				if (tokenizer.hasMoreTokens()) {
					content = tokenizer.nextToken();

					if (content.startsWith("!--")) {
						while ((!(content.endsWith("--"))) &&
								(tokenizer.hasMoreTokens())) {
							content = content + tokenizer.nextToken();
						} // end while

						buffer.append(content);

						continue;
					} // end if

					if (content.startsWith("!CDATA")) {
						while ((!(content.endsWith("]]"))) &&
								(tokenizer.hasMoreTokens())) {
							content = content + tokenizer.nextToken();
						} // end while

						buffer.append(content);

						continue;
					} // end if

					String tmp = content;
					content = replaceEntities(content, entityMap,
							resolveInternalEntities);
					buffer.append(content);

					if (!tmp.equals(content)) {
						while (tokenizer.hasMoreTokens()) {
							buffer.append(tokenizer.nextToken());
						} // end while
					} // end if

					continue;
				} // end if
			} // end if
			else if (token.equals(">")) {
				buffer.append(token);

				continue;
			} // end else if
			else {
				String tmp = token;
				token = replaceEntities(token, entityMap,
						resolveInternalEntities);
				buffer.append(token);

				if (!tmp.equals(token)) {
					while (tokenizer.hasMoreTokens()) {
						buffer.append(tokenizer.nextToken());
					} // end while
				} // end if

				continue;
			} // end else
		} // end while

		return buffer.toString();
	} // end resolveKnownEntities()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param entityMap DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static String resolveKnownEntities(
		final String in,
		final Map entityMap) {
		return resolveKnownEntities(in, entityMap, false);
	} // end resolveKnownEntities()

	/**
	 * DOCUMENT ME!
	 *
	 * @param tokenizer DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static String getRest(final CP2StringTokenizer tokenizer) {
		CStringBuilder result = new CStringBuilder();

		while (tokenizer.hasMoreTokens()) {
			result.append(tokenizer.nextToken());
		} // end while

		return result.toString();
	} // end getRest()

	/**
	 * DOCUMENT ME!
	 *
	 * @param node DOCUMENT ME!
	 * @param cHandler DOCUMENT ME!
	 * @param dHandler DOCUMENT ME!
	 * @param dtdHandler DOCUMENT ME!
	 */
	private static void SaxIt(
		final Node		node,
		final ContentHandler cHandler,
		final DocumentHandler dHandler,
		final DTDHandler dtdHandler) {
		CDOM2SAX converter = new CDOM2SAX(node);
		converter.setContentHandler(cHandler);
		converter.setDocumentHandler(dHandler);
		converter.setDTDHandler(dtdHandler);
		converter.serialize();
	} // end SaxIt()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static CElement parseElement(final String in) {
		String name  = null;
		String value = null;
		String tmp   = in.substring(8).trim();
		int    iSp   = tmp.indexOf(" ");
		int    iSp2  = tmp.indexOf("\n");

		if ((iSp2 < iSp) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\r");

		if ((iSp2 < iSp) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		if (iSp != -1) {
			name	  = tmp.substring(0, iSp).trim();
			value     = tmp.substring(iSp + 1).trim();
		} // end if
		if ((name != null) && (value != null)) {
			return new CElement(name, value);
		} // end if

		return null;
	} // end parseElement()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param documentURI DOCUMENT ME!
	 * @param document DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static CEntity parseEntity(
		final String in,
		final String documentURI,
		final ADocument document) {
		boolean dtdInternal = false;
		String  name	    = null;
		String  entityValue = null;
		String  systemId    = null;
		String  publicId    = null;
		String  ndata	    = null;
		String  tmp		    = in.substring(7).trim();
		int     iSp		    = tmp.indexOf(" ");
		int     iSp2	    = tmp.indexOf("\n");

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\r");

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\t");

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\"") - 1;

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 > 0)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("'") - 1;

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 > 0)) {
			iSp = iSp2;
		}

		if (iSp != -1) {
			name     = tmp.substring(0, iSp).trim();
			tmp		 = tmp.substring(iSp + 1).trim();

			if (name.equals("%")) {
				dtdInternal     = true;
				iSp			    = tmp.indexOf(" ");
				iSp2		    = tmp.indexOf("\n");

				if ((iSp2 < iSp) && (iSp2 != -1)) {
					iSp = iSp2;
				}

				iSp2 = tmp.indexOf("\r");

				if ((iSp2 < iSp) && (iSp2 != -1)) {
					iSp = iSp2;
				}

				if (iSp != -1) {
					name     = tmp.substring(0, iSp).trim();
					tmp		 = tmp.substring(iSp + 1).trim();
				} // end if
				else {

					return null;
				}
			} // end if

			if (tmp.startsWith("PUBLIC")) {
				tmp = tmp.substring(6).trim();

				if (tmp.startsWith("\"")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("\"");

					if (iSp != -1) {
						publicId     = tmp.substring(0, iSp);
						tmp			 = tmp.substring(iSp + 1).trim();

						if (tmp.startsWith("\"")) {
							tmp     = tmp.substring(1);
							iSp     = tmp.indexOf("\"");

							if (iSp != -1) {
								systemId = tmp.substring(0, iSp);
							} // end if
						} // end if
					} // end if
				} // end if
				else if (tmp.startsWith("'")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("'");

					if (iSp != -1) {
						publicId     = tmp.substring(0, iSp);
						tmp			 = tmp.substring(iSp + 1).trim();

						if (tmp.startsWith("'")) {
							tmp     = tmp.substring(1);
							iSp     = tmp.indexOf("'");

							if (iSp != -1) {
								systemId = tmp.substring(0, iSp);
							} // end if
						} // end if
					} // end if
				} // end else if
			} // end if
			else if (tmp.startsWith("SYSTEM")) {
				tmp = tmp.substring(6).trim();

				if (tmp.startsWith("\"")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("\"");

					if (iSp != -1) {
						systemId = tmp.substring(0, iSp);
					} // end if
				} // end if
				else if (tmp.startsWith("'")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("'");

					if (iSp != -1) {
						systemId = tmp.substring(0, iSp);
					} // end if
				} // end else if
			} // end else if
			else {
				entityValue = tmp;
				int index = entityValue.indexOf("\"");
				int index2 = entityValue.indexOf("'");
				if ((index2 > index && index != -1) || (index != -1 && index2 == -1)) {
					entityValue = entityValue.substring(index+1);
					index = entityValue.indexOf("\"");
					if (index != -1)
					entityValue = entityValue.substring(0,index);
				} else if ((index > index2 && index2 != -1) || index2 != -1){
					entityValue = entityValue.substring(index2+1);
					index = entityValue.indexOf("'");
					if (index != -1)
					entityValue = entityValue.substring(0,index);
				}
			} // end else

			if (tmp.indexOf("NDATA") != -1) {
				tmp		  = tmp.substring(tmp.indexOf("NDATA") + 5);
				ndata     = tmp.trim();
			} // end if
		} // end if

		if (systemId != null) {
			if ((systemId.indexOf(":/") == -1) &&
					(documentURI != null)) {
				systemId = documentURI + systemId;
			} // end if
		} // end if

		return new CEntity(in, name, ndata, entityValue, publicId,
			systemId, dtdInternal, document);
	} // end parseEntity()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param documentURI DOCUMENT ME!
	 * @param document DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static CNotation parseNotation(
		final String in,
		final String documentURI,
		final ADocument document) {
		String name     = null;
		String systemId = null;
		String publicId = null;
		String tmp	    = in.substring(9).trim();
		int    iSp	    = tmp.indexOf(" ");
		int    iSp2     = tmp.indexOf("\n");

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\r");

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\t");

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 != -1)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("\"") - 1;

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 > 0)) {
			iSp = iSp2;
		}

		iSp2 = tmp.indexOf("'") - 1;

		if (((iSp2 < iSp) || (iSp == -1)) && (iSp2 > 0)) {
			iSp = iSp2;
		}

		if (iSp != -1) {
			name     = tmp.substring(0, iSp).trim();
			tmp		 = tmp.substring(iSp + 1).trim();

			if (tmp.startsWith("PUBLIC")) {
				tmp = tmp.substring(6).trim();

				if (tmp.startsWith("\"")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("\"");

					if (iSp != -1) {
						publicId     = tmp.substring(0, iSp);
						tmp			 = tmp.substring(iSp + 1).trim();

						if (tmp.startsWith("\"")) {
							tmp     = tmp.substring(1);
							iSp     = tmp.indexOf("\"");

							if (iSp != -1) {
								systemId = tmp.substring(0, iSp);
							} // end if
						} // end if
					} // end if
				} // end if
				else if (tmp.startsWith("'")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("'");

					if (iSp != -1) {
						publicId     = tmp.substring(0, iSp);
						tmp			 = tmp.substring(iSp + 1).trim();

						if (tmp.startsWith("'")) {
							tmp     = tmp.substring(1);
							iSp     = tmp.indexOf("'");

							if (iSp != -1) {
								systemId = tmp.substring(0, iSp);
							} // end if
						} // end if
					} // end if
				} // end else if
			} // end if
			else if (tmp.startsWith("SYSTEM")) {
				tmp = tmp.substring(6).trim();

				if (tmp.startsWith("\"")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("\"");

					if (iSp != -1) {
						systemId = tmp.substring(0, iSp);
					} // end if
				} // end if
				else if (tmp.startsWith("'")) {
					tmp     = tmp.substring(1);
					iSp     = tmp.indexOf("'");

					if (iSp != -1) {
						systemId = tmp.substring(0, iSp);
					} // end if
				} // end else if
			} // end else if
		} // end if

		if (systemId != null) {
			if ((systemId.indexOf(":/") == -1) &&
					(documentURI != null)) {
				systemId = documentURI + systemId;
			} // end if
		} // end if
		return new CNotation(in, name, publicId, systemId, document);
	} // end parseNotation()

	public static Map getInternalEntities() {
		return internalEntities;
	}

	public static long getMaxEntityContentSize() {
		return maxEntityContentSize;
	}

	public static void setMaxEntityContentSize(long maxEntityContentSize) {
		CDTDParser.maxEntityContentSize = maxEntityContentSize;
	}
} // end CDTDParser
