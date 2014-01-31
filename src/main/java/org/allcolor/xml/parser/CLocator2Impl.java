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
// Locator2Impl.java - extended LocatorImpl
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: CLocator2Impl.java,v 1.4 2005/08/07 18:49:44 allcolor Exp $
package org.allcolor.xml.parser;
import org.xml.sax.Locator;
import org.xml.sax.ext.Locator2;
import org.xml.sax.helpers.LocatorImpl;


/**
 * SAX2 extension helper for holding additional Entity information,
 * implementing the {@link Locator2}interface.
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 * 
 * <p>
 * This is not part of core-only SAX2 distributions.
 * </p>
 *
 * @author David Brownell
 * @version TBS
 *
 * @since SAX 2.0.2
 */
public class CLocator2Impl
	extends LocatorImpl
	implements Locator2 {
	/** DOCUMENT ME! */
	private String encoding;

	/** DOCUMENT ME! */
	private String version;

	/**
	 * Construct a new, empty Locator2Impl object. This will not
	 * normally be useful, since the main purpose of this class is to
	 * make a snapshot of an existing Locator.
	 */
	public CLocator2Impl() {}

	/**
	 * Copy an existing Locator or Locator2 object. If the object
	 * implements Locator2, values of the <em>encoding</em> and
	 * <em>version</em> strings are copied, otherwise they set to
	 * <em>null</em>.
	 *
	 * @param locator The existing Locator object.
	 */
	public CLocator2Impl(final Locator locator) {
		super(locator);

		if (locator instanceof Locator2) {
			Locator2 l2 = (Locator2) locator;

			version		 = l2.getXMLVersion();
			encoding     = l2.getEncoding();
		} // end if
	} // end CLocator2Impl()

	/**
	 * Assigns the current value of the encoding property.
	 *
	 * @param encoding the new "encoding" value
	 *
	 * @see #getEncoding
	 */
	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	} // end setEncoding()

	/**
	 * Returns the current value of the encoding property.
	 *
	 * @see #setEncoding
	 */
	public String getEncoding() {
		return encoding;
	} // end getEncoding()

	////////////////////////////////////////////////////////////////////
	// Setters
	////////////////////////////////////////////////////////////////////
	/**
	 * Assigns the current value of the version property.
	 *
	 * @param version the new "version" value
	 *
	 * @see #getXMLVersion
	 */
	public void setXMLVersion(final String version) {
		this.version = version;
	} // end setXMLVersion()

	////////////////////////////////////////////////////////////////////
	// Locator2 method implementations
	////////////////////////////////////////////////////////////////////
	/**
	 * Returns the current value of the version property.
	 *
	 * @see #setXMLVersion
	 */
	public String getXMLVersion() {
		return version;
	} // end getXMLVersion()
} // end CLocator2Impl
