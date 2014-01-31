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
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CDOMImplementationList
	implements DOMImplementationList {
	/** DOCUMENT ME! */
	DOMImplementation impl;

	/**
	 * Creates a new CDOMImplementationList object.
	 *
	 * @param impl DOCUMENT ME!
	 */
	public CDOMImplementationList(final DOMImplementation impl) {
		this.impl = impl;
	} // end CDOMImplementationList()

	/* (non-Javadoc)
	 * @see org.w3c.dom.DOMImplementationList#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		return impl == null ? 0 : 1;
	} // end getLength()

	/* (non-Javadoc)
	 * @see org.w3c.dom.DOMImplementationList#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public DOMImplementation item(final int index) {
		return impl;
	} // end item()
} // end CDOMImplementationList
