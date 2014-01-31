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
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSStyleDeclaration
	implements CSSStyleDeclaration,Serializable {
	static final long serialVersionUID = -5737318970575567987L;

	/** DOCUMENT ME! */
	private CSSRule parentRule = null;

	/** DOCUMENT ME! */
	private Map priorityMap = new HashMap();

	/** DOCUMENT ME! */
	private Map propertyIndexMap = new HashMap();

	/** DOCUMENT ME! */
	private Map propertyMap = new HashMap();

	/** DOCUMENT ME! */
	private Map propertyNameMap = new HashMap();

	/**
	 * Creates a new CCSSStyleDeclaration object.
	 *
	 * @param parentRule DOCUMENT ME!
	 */
	public CCSSStyleDeclaration(final CSSRule parentRule) {
		this.parentRule = parentRule;
	} // end CCSSStyleDeclaration()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#setCssText(java.lang.String)
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
		if (parentRule != null) {
			parentRule.setCssText(cssText);
		}
	} // end setCssText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getCssText()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getCssText() {
		if (parentRule == null) {
			return "";
		}

		return parentRule.getCssText();
	} // end getCssText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		return propertyMap.size();
	} // end getLength()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getParentRule()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSRule getParentRule() {
		return parentRule;
	} // end getParentRule()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#setProperty(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param propertyName DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 * @param priority DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setProperty(
		final String propertyName,
		final String value,
		final String priority)
		throws DOMException {
		propertyNameMap.put("" + propertyMap.size(), propertyName);
		propertyIndexMap.put(propertyName, "" + propertyMap.size());
		propertyMap.put(propertyName,
			new CCSSValue(this, propertyName, value));
		priorityMap.put(propertyName, priority);
	} // end setProperty()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyCSSValue(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param propertyName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSValue getPropertyCSSValue(final String propertyName) {
		return (CSSValue) propertyMap.get(propertyName);
	} // end getPropertyCSSValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyPriority(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param propertyName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPropertyPriority(final String propertyName) {
		return (String) priorityMap.get(propertyName);
	} // end getPropertyPriority()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyValue(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param propertyName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPropertyValue(final String propertyName) {
		CSSValue value = (CSSValue) propertyMap.get(propertyName);

		if (value == null) {
			return null;
		}

		return value.getCssText();
	} // end getPropertyValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String item(final int index) {
		return (String) propertyNameMap.get("" + index);
	} // end item()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleDeclaration#removeProperty(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param propertyName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String removeProperty(final String propertyName)
		throws DOMException {
		String iKey = (String) propertyIndexMap.remove(propertyName);
		propertyNameMap.remove(iKey);
		priorityMap.remove(propertyName);

		return ((CSSValue) propertyMap.remove(propertyName)).getCssText();
	} // end removeProperty()
} // end CCSSStyleDeclaration
