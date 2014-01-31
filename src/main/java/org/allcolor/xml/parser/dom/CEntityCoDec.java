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
import org.allcolor.dtd.parser.CDTDParser;

import org.allcolor.xml.parser.CStringBuilder;
import org.allcolor.xml.parser.CXmlParser;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CEntityCoDec
	implements Serializable {
	/** DOCUMENT ME! */
	public final static long serialVersionUID = -7972654975385405335L;

	/** DOCUMENT ME! */
	Map entities;

	/**
	 * Creates a new CEntityCoDec object.
	 *
	 * @param entities DOCUMENT ME!
	 */
	public CEntityCoDec(final Map entities) {
		this.entities = entities;
	} // end CEntityCoDec()

	/**
	 * DOCUMENT ME!
	 *
	 * @param toDecode DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String decode(final String toDecode) {
		return decodeInternal(toDecode, null, null, null, true);
	} // end decode()

	/**
	 * DOCUMENT ME!
	 *
	 * @param toDecode DOCUMENT ME!
	 * @param cHandler DOCUMENT ME!
	 * @param dHandler DOCUMENT ME!
	 * @param dtdHandler DOCUMENT ME!
	 * @param replace DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String decodeInternal(
		final String    toDecode,
		final ContentHandler cHandler,
		final DocumentHandler dHandler,
		final DTDHandler dtdHandler,
		final boolean   replace) {
		return CDTDParser.replaceEntities(toDecode,
			(entities.size() == 0)
			? (CXmlParser.dtTr != null ? CXmlParser.dtTr.getKnownEntities() : new HashMap())
			: entities, replace, cHandler, dHandler, dtdHandler, replace);
	} // end decodeInternal()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final static String encode(final String in) {
		return encode(in, false);
	} // end encode()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param attribute DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final static String encode(
		final String in,
		final boolean attribute) {
		return encode(in, attribute, true);
	} // end encode()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param attribute DOCUMENT ME!
	 * @param amp DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final static String encode(
		String in,
		final boolean attribute,
		final boolean amp) {
		if (amp) {
			in = stringReplace(in, "&", "&amp;");
		}

		in     = stringReplace(in, "<", "&lt;");
		in     = stringReplace(in, ">", "&gt;");

		if (attribute) {
			in = stringReplace(in, "\"", "&quot;");
		}

		return in;
	} // end encode()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String encodeI(final String in) {
		return encode(in);
	} // end encodeI()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param attribute DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String encodeI(
		final String in,
		final boolean attribute) {
		return encode(in, attribute);
	} // end encodeI()

	/**
	 * DOCUMENT ME!
	 *
	 * @param toBeReplaced DOCUMENT ME!
	 * @param toReplace DOCUMENT ME!
	 * @param replacement DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final static String stringReplace(
		String toBeReplaced,
		final String toReplace,
		final String replacement) {
		CStringBuilder result = new CStringBuilder();
		int			   index = toBeReplaced.indexOf(toReplace,0);

		if (index == -1) {
			return toBeReplaced;
		}

		while (index != -1) {
			result.append(toBeReplaced.substring(0, index));
			result.append(replacement);
			toBeReplaced     = toBeReplaced.substring(index +
					toReplace.length());
			index			 = toBeReplaced.indexOf(toReplace,0);
		} // end while

		result.append(toBeReplaced);

		return result.toString();
	} // end stringReplace()
} // end CEntityCoDec
