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
import java.io.Serializable;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.Counter;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.css.Rect;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSValue
	implements CSSValue,
		CSSPrimitiveValue,
		CSSValueList,
		Serializable {
	static final long serialVersionUID = 2059068625115466121L;

	/** DOCUMENT ME! */
	private CSSStyleDeclaration styleDef = null;

	/** DOCUMENT ME! */
	private String cssText = null;

	/** DOCUMENT ME! */
	private String pName = null;

	/** DOCUMENT ME! */
	private short type = CSSPrimitiveValue.CSS_STRING;

	/**
	 * Creates a new CCSSValue object.
	 *
	 * @param styleDef DOCUMENT ME!
	 * @param pName DOCUMENT ME!
	 * @param cssText DOCUMENT ME!
	 */
	public CCSSValue(
		final CSSStyleDeclaration styleDef,
		final String		pName,
		final String		cssText) {
		this.styleDef     = styleDef;
		this.cssText	  = cssText;
		this.pName		  = pName;
		parse(cssText);
	} // end CCSSValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#getCounterValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Counter getCounterValue()
		throws DOMException {
		// TODO Auto-generated method stub
		return null;
	} // end getCounterValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSValue#setCssText(java.lang.String)
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
		styleDef.setProperty(pName, cssText, null);
	} // end setCssText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSValue#getCssText()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getCssText() {
		return cssText;
	} // end getCssText()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSValue#getCssValueType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getCssValueType() {
		return type;
	} // end getCssValueType()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#setFloatValue(short, float)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param unitType DOCUMENT ME!
	 * @param floatValue DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setFloatValue(
		final short unitType,
		final float floatValue)
		throws DOMException {
		// TODO Auto-generated method stub
	} // end setFloatValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#getFloatValue(short)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param unitType DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public float getFloatValue(final short unitType)
		throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	} // end getFloatValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSValueList#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	} // end getLength()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#getPrimitiveType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getPrimitiveType() {
		// TODO Auto-generated method stub
		return 0;
	} // end getPrimitiveType()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#getRGBColorValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public RGBColor getRGBColorValue()
		throws DOMException {
		// TODO Auto-generated method stub
		return null;
	} // end getRGBColorValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#getRectValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Rect getRectValue()
		throws DOMException {
		// TODO Auto-generated method stub
		return null;
	} // end getRectValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#setStringValue(short,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param stringType DOCUMENT ME!
	 * @param stringValue DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setStringValue(
		final short stringType,
		final String stringValue)
		throws DOMException {
		// TODO Auto-generated method stub
	} // end setStringValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSPrimitiveValue#getStringValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String getStringValue()
		throws DOMException {
		// TODO Auto-generated method stub
		return null;
	} // end getStringValue()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSValueList#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSValue item(final int index) {
		// TODO Auto-generated method stub
		return null;
	} // end item()

	/**
	 * DOCUMENT ME!
	 *
	 * @param in DOCUMENT ME!
	 */
	private void parse(final String in) {
		// check if it is a CSS2 attribute
		// if not set CUSTOM_VALUE
	} // end parse()
} // end CCSSValue
