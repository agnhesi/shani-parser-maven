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
import org.allcolor.xml.parser.dom.CAttr;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public interface IParseHandler {
	public void setEncoding(String encoding);
	/**
	 * DOCUMENT ME!
	 *
	 * @param handler DOCUMENT ME!
	 */
	public void documentEnd();

	/**
	 * DOCUMENT ME!
	 *
	 * @param handler DOCUMENT ME!
	 */
	public void documentStart();

	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseCDATA(
		final String	   content
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseComment(
		final String	   content
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseDoctype(
		final String	   content
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param attributes DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseEmptyTag(
		final String	   name,
		final CAttr [] attributes,
		final int count,
		final int   indexSep
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseEndTag(
		final String	   name,
		final int   indexSep
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param content DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parsePI(
		final String	   name,
		final String	   content
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param attributes DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseStartTag(
		final String	   name,
		final CAttr [] attributes,
		final int count,
		final int   indexSep
		);

	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 * @param handler DOCUMENT ME!
	 */
	public void parseText(
		final String	   content,
		final boolean decode
		);
} // end IParseHandler
