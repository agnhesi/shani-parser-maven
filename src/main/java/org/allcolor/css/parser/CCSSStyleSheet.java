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

import org.allcolor.xml.parser.CStringBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;
import org.w3c.dom.stylesheets.StyleSheet;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSStyleSheet
	implements CSSStyleSheet,Serializable {
	static final long serialVersionUID = 2184587778379854885L;

	/** DOCUMENT ME! */
	private CCSSRuleList ruleList = new CCSSRuleList();

	/** DOCUMENT ME! */
	private CSSRule ownerRule = null;

	/** DOCUMENT ME! */
	private MediaList mediaList = null;

	/** DOCUMENT ME! */
	private Node ownerNode = null;

	/** DOCUMENT ME! */
	private String href = "";

	/** DOCUMENT ME! */
	private String title = "";

	/** DOCUMENT ME! */
	private boolean booDisabled = false;

	/**
	 * Creates a new CCSSStyleSheet object.
	 *
	 * @param ownerNode DOCUMENT ME!
	 * @param ownerRule DOCUMENT ME!
	 * @param booDisabled DOCUMENT ME!
	 * @param href DOCUMENT ME!
	 * @param title DOCUMENT ME!
	 * @param mediaList DOCUMENT ME!
	 */
	public CCSSStyleSheet(
		final Node ownerNode,
		final CSSRule ownerRule,
		final boolean booDisabled,
		String href,
		String title,
		final MediaList mediaList) {
		this.ownerNode		 = ownerNode;
		this.ownerRule		 = ownerRule;
		this.booDisabled     = booDisabled;

		if (href == null) {
			href = "";
		}

		this.href = href;

		if (title == null) {
			title = "";
		}

		this.title		   = title;
		this.mediaList     = mediaList;
	} // end CCSSStyleSheet()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleSheet#getCssRules()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSRuleList getCssRules() {
		return ruleList;
	} // end getCssRules()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#setDisabled(boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param disabled DOCUMENT ME!
	 */
	public void setDisabled(final boolean disabled) {
		booDisabled = disabled;
	} // end setDisabled()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#getDisabled()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getDisabled() {
		return booDisabled;
	} // end getDisabled()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#getHref()
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
	 * @see org.w3c.dom.stylesheets.StyleSheet#getMedia()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public MediaList getMedia() {
		return mediaList;
	} // end getMedia()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#getOwnerNode()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Node getOwnerNode() {
		return ownerNode;
	} // end getOwnerNode()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleSheet#getOwnerRule()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSRule getOwnerRule() {
		return ownerRule;
	} // end getOwnerRule()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#getParentStyleSheet()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public StyleSheet getParentStyleSheet() {
		if (ownerRule != null) {
			return ownerRule.getParentStyleSheet();
		}

		return null;
	} // end getParentStyleSheet()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#getTitle()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getTitle() {
		return title;
	} // end getTitle()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheet#getType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getType() {
		return "text/css";
	} // end getType()

	/**
	 * DOCUMENT ME!
	 *
	 * @param rule DOCUMENT ME!
	 */
	public void addRule(final CSSRule rule) {
		ruleList.add(rule);
	} // end addRule()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleSheet#deleteRule(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void deleteRule(final int index)
		throws DOMException {
		ruleList.remove(index);
	} // end deleteRule()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSStyleSheet#insertRule(java.lang.String, int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param rule DOCUMENT ME!
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public int insertRule(
		final String rule,
		final int index)
		throws DOMException {
		ruleList.insertAt(index, CCSSParser.parseRule(rule, this, null));

		return index;
	} // end insertRule()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder buffer = new CStringBuilder();
		CSSRuleList    list = getCssRules();

		for (int i = 0; i < list.getLength(); i++) {
			buffer.append(list.item(i));
		} // end for

		return buffer.toString();
	} // end toString()
} // end CCSSStyleSheet
