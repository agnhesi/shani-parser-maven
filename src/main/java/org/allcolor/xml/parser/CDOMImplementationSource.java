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
import org.w3c.dom.DOMImplementationSource;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CDOMImplementationSource
	implements DOMImplementationSource {
	/**
	 * Creates a new CDOMImplementationSource object.
	 */
	public CDOMImplementationSource() {
		super();

		// TODO Auto-generated constructor stub
	} // end CDOMImplementationSource()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DOMImplementationSource#getDOMImplementation(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param features DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public DOMImplementation getDOMImplementation(
		final String features) {
		CStringTokenizer tokenizer = new CStringTokenizer(features,
				" ", false);
		
		CDomImplementation impl = new CDomImplementation();
		while (tokenizer.hasMoreTokens()) {
			String name    = tokenizer.nextToken();
			String version = "";

			if (tokenizer.hasMoreTokens()) {
				version = tokenizer.nextToken();
			}
			if (!impl.hasFeature(name,version))
				return null;
		} // end while
		impl.setReqFeatures(features);
		return impl;
	} // end getDOMImplementation()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DOMImplementationSource#getDOMImplementationList(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param features DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public DOMImplementationList getDOMImplementationList(
		final String features) {
		return new CDOMImplementationList(getDOMImplementation(features));
	} // end getDOMImplementationList()
} // end CDOMImplementationSource
