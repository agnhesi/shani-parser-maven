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
import org.allcolor.xml.parser.CStringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSParser {
	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 * @param list DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static String getInnerRule(
		final String content,
		final List list) {
		CStringTokenizer tokenizer     = new CStringTokenizer(content,
				"{}@", true);
		CStringBuilder   resultContent = new CStringBuilder();
		String			 previousToken = null;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();

			if (token.equals("@")) {
				if (tokenizer.hasMoreTokens()) {
					String		   selector = "@" +
						tokenizer.nextToken();
					CStringBuilder rule = new CStringBuilder();

					if (selector.indexOf(" ") != -1) {
						rule.append(selector.substring(selector.indexOf(
									" ")).trim());
						selector = selector.substring(0,
								selector.indexOf(" ")).trim();
					} // end if

					if (rule.indexOf(";") != -1) {
						previousToken = rule.substring(rule.indexOf(";"));
						list.add(selector +
							rule.substring(0, rule.indexOf(";")));

						continue;
					} // end if

					int iOpen = 0;

					while (tokenizer.hasMoreTokens()) {
						String nToken = tokenizer.nextToken();

						if (nToken.equals("}")) {
							iOpen--;

							if (iOpen > 0) {
								rule.append(nToken);
							}
						} // end if
						else if (nToken.equals("{")) {
							rule.append(nToken);
							iOpen++;
						} // end else if
						else {
							rule.append(nToken);
						} // end else

						if (iOpen == 0) {
							break;
						}
					} // end while

					list.add(selector + " {" + rule.toString() + "}");
				} // end if

				previousToken = null;
			} // end if
			else if (token.equals("{")) {
				if (previousToken != null) {
					String		   selector = previousToken;
					CStringBuilder rule = new CStringBuilder();

					if (previousToken.lastIndexOf("\n") != -1) {
						selector = previousToken.substring(previousToken.lastIndexOf(
									"\n") + 1).trim();
						resultContent.append(previousToken.substring(
								0,
								previousToken.lastIndexOf("\n")));
					} // end if

					int iOpen = 1;

					while (tokenizer.hasMoreTokens()) {
						String nToken = tokenizer.nextToken();

						if (nToken.equals("}")) {
							iOpen--;

							if (iOpen > 0) {
								rule.append(nToken);
							}
						} // end if
						else if (nToken.equals("{")) {
							rule.append(nToken);
							iOpen++;
						} // end else if
						else {
							rule.append(nToken);
						} // end else

						if (iOpen == 0) {
							break;
						}
					} // end while

					list.add(selector + " {" + rule.toString() + "}");
				} // end if

				previousToken = null;
			} // end else if
			else {
				previousToken = token.trim();
			} // end else
		} // end while

		if (previousToken != null) {
			resultContent.append(previousToken);
		}

		return resultContent.toString();
	} // end getInnerRule()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 * @param ownerNode DOCUMENT ME!
	 * @param ownerRule DOCUMENT ME!
	 * @param uri DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static CSSStyleSheet parse(
		String  in,
		final Element ownerNode,
		final CSSImportRule ownerRule,
		final String  uri) {
		String title = "";
		String href = "";

		if (ownerNode != null) {
			title     = ownerNode.getAttribute("title");
			href	  = ownerNode.getAttribute("href");
		} // end if
		else if (ownerRule != null) {
			href = ownerRule.getHref();
		} // end else if
		else {
			href = uri;
		} // end else

		CCSSStyleSheet css = new CCSSStyleSheet(ownerNode, ownerRule,
				false, href, title, null);
		in = removeComments(in);

		List strRulesList = new ArrayList();
		getInnerRule(in, strRulesList);

		List     rulesList = new ArrayList();
		Iterator it = strRulesList.iterator();

		while (it.hasNext()) {
			String rule = (String) it.next();
			rulesList.add(parseRule(rule, css, null));
		} // end while

		it = rulesList.iterator();

		while (it.hasNext()) {
			css.addRule((CSSRule) it.next());
		} // end while

		return css;
	} // end parse()

	/**
	 * DOCUMENT ME!
	 *
	 * @param rule DOCUMENT ME!
	 * @param css DOCUMENT ME!
	 * @param parentRule DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static CCSSRule parseRule(
		final String  rule,
		final CSSStyleSheet css,
		final CCSSRule parentRule) {
		CCSSRule result = null;

		// check if there are any @ rule
		if (rule.startsWith("@import")) {
			String importRule = rule.substring(rule.indexOf("@import"));

			if (importRule.indexOf(";") != -1) {
				importRule = importRule.substring(0,
						importRule.indexOf(";"));
			}

			importRule     = importRule.substring(7);
			result		   = parseRule("@import", importRule, css,
					parentRule);
		} // end if
		else if (rule.startsWith("@charset")) {
			String charsetRule = rule.substring(rule.indexOf("@charset"));

			if (charsetRule.indexOf(";") != -1) {
				charsetRule = charsetRule.substring(0,
						charsetRule.indexOf(";"));
			}

			charsetRule     = charsetRule.substring(7);
			result		    = parseRule("@charset", charsetRule, css,
					parentRule);
		} // end else if
		else {
			String selector = rule.substring(0, rule.indexOf("{"));
			String content = rule.substring(rule.indexOf("{") + 1,
					rule.lastIndexOf("}"));
			result = parseRule(selector, content, css, parentRule);
		} // end else

		return result;
	} // end parseRule()

	/**
	 * DOCUMENT ME!
	 *
	 * @param selector DOCUMENT ME!
	 * @param content DOCUMENT ME!
	 * @param css DOCUMENT ME!
	 * @param rule DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static CCSSRule parseRule(
		final String  selector,
		String  content,
		final CSSStyleSheet css,
		final CCSSRule rule) {
		List innerRule = new ArrayList();
		content = getInnerRule(content, innerRule);

		CCSSRule result = null;

		// check if there are any @ rule
		if (selector.startsWith("@import")) {
			result = new CCSSImportRule(content, css, rule);
		} // end if
		else if (selector.startsWith("@charset")) {}
		else if (selector.startsWith("@media")) {}
		else if (selector.startsWith("@page")) {}
		else if (selector.startsWith("@font-face")) {}
		else if (selector.startsWith("@")) {}
		else {
			result = new CCSSStyleRule(selector, content, css, rule);
		} // end else

		for (int i = 0; i < innerRule.size(); i++) {
			parseRule((String) innerRule.get(i), css, result);
		} // end for

		return result;
	} // end parseRule()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static String removeComments(String in) {
		int    iOpen = in.indexOf("/*");
		String inTmp = in;

		while (iOpen != -1) {
			String tmp = inTmp.substring(0, iOpen);
			inTmp = inTmp.substring(iOpen + 2);

			int iClose = inTmp.indexOf("*/");

			if (iClose != -1) {
				inTmp = tmp + inTmp.substring(iClose + 2);
			} // end if
			else {
				inTmp = tmp;
			} // end else

			iOpen = inTmp.indexOf("/*");
		} // end while

		iOpen = inTmp.indexOf("<!--");

		while (iOpen != -1) {
			String tmp = inTmp.substring(0, iOpen);
			inTmp = inTmp.substring(iOpen + 2);

			int iClose = inTmp.indexOf("-->");

			if (iClose != -1) {
				inTmp = tmp + inTmp.substring(iClose + 3);
			} else {
				inTmp = tmp;
			}

			iOpen = inTmp.indexOf("<!--");
		} // end while

		in = inTmp;

		return in;
	} // end removeComments()
} // end CCSSParser
