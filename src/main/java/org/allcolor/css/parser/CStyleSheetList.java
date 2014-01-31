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
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CStyleSheetList
	implements StyleSheetList,Serializable {
	static final long serialVersionUID = -2114501298647246364L;
	/**
	 * DOCUMENT ME!
	 */
	List list = new ArrayList();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheetList#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		return list.size();
	} // end getLength()

	/**
	 * DOCUMENT ME!
	 *
	 * @param css DOCUMENT ME!
	 */
	public void add(final StyleSheet css) {
		list.add(css);
	} // end add()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.stylesheets.StyleSheetList#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public StyleSheet item(final int index) {
		return (list.size() > index)
		? (StyleSheet) list.get(index)
		: null;
	} // end item()
} // end CStyleSheetList
