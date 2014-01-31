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
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author qan TODO To change the template for this generated type
 * 		   comment go to Window - Preferences - Java - Code Style -
 * 		   Code Templates
 */
public abstract class CCSSRule
	implements CSSRule,Serializable {
	static final long serialVersionUID = -50137911721961237L;

	/** DOCUMENT ME! */
	protected CCSSRule parentRule = null;

	/** DOCUMENT ME! */
	protected CSSStyleSheet parentSheet = null;

	/** DOCUMENT ME! */
	protected List childList = new ArrayList();

	/** DOCUMENT ME! */
	protected String cssText = null;

	/**
	 * Creates a new CCSSRule object.
	 *
	 * @param cssText DOCUMENT ME!
	 * @param parentSheet DOCUMENT ME!
	 * @param parentRule DOCUMENT ME!
	 */
	public CCSSRule(
		String		cssText,
		final CSSStyleSheet parentSheet,
		final CCSSRule		parentRule) {
		if (cssText == null) {
			cssText = "";
		} // end if

		this.cssText		 = cssText;
		this.parentSheet     = parentSheet;
		this.parentRule		 = parentRule;

		if (parentRule != null) {
			parentRule.addChild(this);
		} // end if
	} // end CCSSRule()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public List getChildList() {
		return childList;
	} // end getChildList()

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
	public abstract short getType();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRule#getCssText()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String getCssText() {
		return cssText;
	} // end getCssText()

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
	public abstract void setCssText(final String cssText)
		throws DOMException;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRule#getParentRule()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final CSSRule getParentRule() {
		return parentRule;
	} // end getParentRule()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRule#getParentStyleSheet()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final CSSStyleSheet getParentStyleSheet() {
		return parentSheet;
	} // end getParentStyleSheet()

	/**
	 * DOCUMENT ME!
	 *
	 * @param rule DOCUMENT ME!
	 */
	public void addChild(final CCSSRule rule) {
		childList.add(rule);
	} // end addChild()
} // end CCSSRule
