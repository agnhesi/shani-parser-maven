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
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCSSRuleList
	implements CSSRuleList,Serializable {
	static final long serialVersionUID = -634721936139060874L;
	/** DOCUMENT ME! */
	private List ruleList = new ArrayList();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRuleList#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		return ruleList.size();
	} // end getLength()

	/**
	 * DOCUMENT ME!
	 *
	 * @param rule DOCUMENT ME!
	 */
	public void add(final CSSRule rule) {
		ruleList.add(rule);
	} // end add()

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 * @param rule DOCUMENT ME!
	 */
	public void insertAt(
		final int index,
		final CSSRule rule) {
		ruleList.add(index, rule);
	} // end insertAt()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.CSSRuleList#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSRule item(final int index) {
		return (CSSRule) ruleList.get(index);
	} // end item()

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 */
	public void remove(final int index) {
		ruleList.remove(index);
	} // end remove()
} // end CCSSRuleList
