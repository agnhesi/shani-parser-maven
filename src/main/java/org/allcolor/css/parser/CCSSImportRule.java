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
package org.allcolor.css.parser;
import org.allcolor.xml.parser.CStringBuilder;
import org.allcolor.xml.parser.dom.INode;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.net.URL;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSImportRule
	extends CCSSRule
	implements CSSImportRule {
	static final long serialVersionUID = -4975074241934601348L;

	/** DOCUMENT ME! */
	private CSSStyleSheet sheet = null;

	/** DOCUMENT ME! */
	private String href = "";

	/**
	 * DOCUMENT ME!
	 *
	 * @param cssText
	 * @param parentSheet
	 * @param parentRule
	 */
	public CCSSImportRule(
		final String		cssText,
		final CSSStyleSheet parentSheet,
		final CCSSRule		parentRule) {
		super(cssText, parentSheet, parentRule);
		parse(cssText);
	} // end CCSSImportRule()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRule#setCssText(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param cssText DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setCssText(final String cssText)
		throws DOMException {
		this.cssText = cssText;
		parse(cssText);
	} // end setCssText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSImportRule#getHref()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getHref() {
		return href;
	} // end getHref()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSImportRule#getMedia()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public MediaList getMedia() {
		// TODO Auto-generated method stub
		return null;
	} // end getMedia()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSImportRule#getStyleSheet()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSStyleSheet getStyleSheet() {
		if (sheet == null) {
			String file = loadFile();
			sheet = CCSSParser.parse(file, null, this, getHref());
		} // end if

		return sheet;
	} // end getStyleSheet()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRule#getType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getType() {
		return CSSRule.IMPORT_RULE;
	} // end getType()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder result = new CStringBuilder();
		int			   level = 0;
		CSSRule		   rule  = this;

		while (rule.getParentRule() != null) {
			level++;
			rule = rule.getParentRule();
		} // end while

		for (int i = 0; i < level; i++) {
			result.append("\t");
		} // end for

		result.append("@import url(");
		result.append(href);
		result.append(");\n");

		return result.toString();
	} // end toString()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private String loadFile() {
		InputStream in = null;

		try {
			in = new URL(href).openStream();

			ByteArrayOutputStream bOut		  = new ByteArrayOutputStream();
			byte				  buffer[]    = new byte[16384];
			int					  iNbByteRead = -1;

			while ((iNbByteRead = in.read(buffer)) != -1) {
				bOut.write(buffer, 0, iNbByteRead);
			} // end while

			return new String(bOut.toByteArray(), "utf-8");
		} // end try
		catch (final Exception e) {
			return "";
		} // end catch
		finally {
			try {
				in.close();
			} // end try
			catch (final Exception ignore) {}
		} // end finally
	} // end loadFile()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 */
	private void parse(final String in) {
		int iOpen  = in.indexOf("url(");
		int iClose = in.indexOf(")");
		href = in;

		if ((iOpen != -1) && (iClose != -1)) {
			href = in.substring(iOpen + 4, iClose);
		} // end if

		if ((href.startsWith("'")) || (href.startsWith("\""))) {
			href = href.substring(1);
		} // end if

		if ((href.endsWith("'")) || (href.endsWith("\""))) {
			href = href.substring(0, href.length() - 1);
		} // end if

		if ((href.indexOf(":/") == -1) && (parentSheet != null)) {
			String base = parentSheet.getHref();

			if ((base == null) && (parentSheet.getOwnerNode() != null)) {
				base = ((INode) parentSheet.getOwnerNode()).getBaseURI();
			} // end if

			if (base != null) {
				int iIndexslash = base.lastIndexOf("/");

				if (iIndexslash != -1) {
					base = base.substring(0, iIndexslash + 1);
				} // end if

				href = base + href;
			} // end if
		} // end if
	} // end parse()
} // end CCSSImportRule
