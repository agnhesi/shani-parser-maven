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

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSStyleRule
	extends CCSSRule
	implements CSSStyleRule {
	static final long serialVersionUID = -4012631613429042994L;

	/** DOCUMENT ME! */
	private CSSStyleDeclaration styleDef = null;

	/** DOCUMENT ME! */
	private String selectorName = null;

	/**
	 * DOCUMENT ME!
	 *
	 * @param selectorName DOCUMENT ME!
	 * @param cssText
	 * @param parentSheet
	 * @param parentRule
	 */
	public CCSSStyleRule(
		final String  selectorName,
		final String  cssText,
		final CSSStyleSheet parentSheet,
		final CCSSRule parentRule) {
		super(cssText, parentSheet, parentRule);
		this.selectorName = selectorName;
		parse(this.cssText);
	} // end CCSSStyleRule()

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
	public void setCssText(String cssText)
		throws DOMException {
		if (cssText == null) {
			cssText = "";
		}

		this.cssText = cssText;
		parse(this.cssText);
	} // end setCssText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleRule#setSelectorText(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param selectorText DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setSelectorText(final String selectorText)
		throws DOMException {
		selectorName = selectorText;
	} // end setSelectorText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleRule#getSelectorText()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getSelectorText() {
		return selectorName;
	} // end getSelectorText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleRule#getStyle()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSStyleDeclaration getStyle() {
		return styleDef;
	} // end getStyle()

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
		return CSSRule.STYLE_RULE;
	} // end getType()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder result = new CStringBuilder();
		CSSRule		   rule  = this;
		int			   level = 0;

		while (rule.getParentRule() != null) {
			level++;
			rule = rule.getParentRule();
		} // end while

		for (int i = 0; i < level; i++) {
			result.append("\t");
		} // end for

		result.append(getSelectorText());
		result.append(" {\n");

		List     list = getChildList();
		Iterator it = list.iterator();

		while (it.hasNext()) {
			result.append(it.next());
		} // end while

		for (int i = 0; i < styleDef.getLength(); i++) {
			String name  = styleDef.item(i);
			String value = styleDef.getPropertyValue(name);

			for (int j = 0; j < (level + 1); j++) {
				result.append("\t");
			} // end for

			result.append(name);
			result.append(": ");
			result.append(value);
			result.append(";\n");
		} // end for

		for (int i = 0; i < level; i++) {
			result.append("\t");
		} // end for

		result.append("}\n");

		return result.toString();
	} // end toString()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 */
	private void parse(final String in) {
		styleDef = new CCSSStyleDeclaration(this);

		StringTokenizer tokenizer = new StringTokenizer(in, ":;", true);
		List		    arrToken = new ArrayList();

		while (tokenizer.hasMoreTokens()) {
			arrToken.add(tokenizer.nextToken());
		} // end while

		int i = 0;

		while (i < arrToken.size()) {
			String token = (String) arrToken.get(i);

			if (token.equals(":")) {
				if ((arrToken.size() > (i + 1)) && (i > 0)) {
					String propertyName = ((String) arrToken.get(i - 1)).trim();

					if (propertyName.startsWith("//")) {
						i += 2;
						continue;
					}

					int    index		 = i + 2;
					String propertyValue = "";

					if (arrToken.size() > index) {
						String test = (String) arrToken.get(index);

						while (!(test.equals(";"))) {
							index++;

							if (arrToken.size() > index) {
								test = (String) arrToken.get(index);
							} // end if
							else {
								break;
							} // end else
						} // end while

						propertyValue = "";

						for (int j = i + 1; j < index; j++) {
							propertyValue += ((String) arrToken
												  .get(j)).trim()
												  .replaceAll("\\n", "");
						} // end for
					} // end if
					else {
						propertyValue = ((String) arrToken
											 .get(i + 1)).trim()
											 .replaceAll("\\n", "");
					} // end else

					styleDef.setProperty(propertyName, propertyValue,
						null);
					i = index;

					continue;
				} // end if
			} // end if

			i++;
		} // end while
	} // end parse()
} // end CCSSStyleRule
