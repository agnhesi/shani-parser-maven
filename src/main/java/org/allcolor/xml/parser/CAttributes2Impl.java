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
// Attributes2Impl.java - extended AttributesImpl
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: CAttributes2Impl.java,v 1.4 2005/08/07 18:49:44 allcolor Exp $
package org.allcolor.xml.parser;
import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.helpers.AttributesImpl;


/**
 * SAX2 extension helper for additional Attributes information,
 * implementing the {@link Attributes2}interface.
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 * 
 * <p>
 * This is not part of core-only SAX2 distributions.
 * </p>
 * 
 * <p>
 * The <em>specified</em> flag for each attribute will always be true,
 * unless it has been set to false in the copy constructor or using
 * {@link #setSpecified}. Similarly, the <em>declared</em> flag for
 * each attribute will always be false, except for defaulted
 * attributes ( <em>specified</em> is false), non-CDATA attributes, or
 * when it is set to true using {@link #setDeclared}. If you change an
 * attribute's type by hand, you may need to modify its
 * <em>declared</em> flag to match.
 * </p>
 *
 * @author David Brownell
 * @version TBS
 *
 * @since SAX 2.0 (extensions 1.1 alpha)
 */
public class CAttributes2Impl
	extends AttributesImpl
	implements Attributes2 {
	/** DOCUMENT ME! */
	private boolean declared[];

	/** DOCUMENT ME! */
	private boolean specified[];

	/**
	 * Construct a new, empty Attributes2Impl object.
	 */
	public CAttributes2Impl() {}

	/**
	 * Copy an existing Attributes or Attributes2 object. If the object
	 * implements Attributes2, values of the <em>specified</em> and
	 * <em>declared</em> flags for each attribute are copied.
	 * Otherwise the flag values are defaulted to assume no DTD was
	 * used, unless there is evidence to the contrary (such as
	 * attributes with type other than CDATA, which must have been
	 * <em>declared</em>).
	 * 
	 * <p>
	 * This constructor is especially useful inside a {@link
	 * org.xml.sax.ContentHandler#startElement startElement}event.
	 * </p>
	 *
	 * @param atts The existing Attributes object.
	 */
	public CAttributes2Impl(final Attributes atts) {
		super(atts);
	} // end CAttributes2Impl()

	////////////////////////////////////////////////////////////////////
	// Manipulators
	////////////////////////////////////////////////////////////////////
	/**
	 * Copy an entire Attributes object. The "specified" flags are
	 * assigned as true, and "declared" flags as false (except when an
	 * attribute's type is not CDATA), unless the object is an
	 * Attributes2 object. In that case those flag values are all
	 * copied.
	 *
	 * @see AttributesImpl#setAttributes
	 */
	public void setAttributes(final Attributes atts) {
		int length = atts.getLength();

		super.setAttributes(atts);
		declared	  = new boolean[length];
		specified     = new boolean[length];

		if (atts instanceof Attributes2) {
			Attributes2 a2 = (Attributes2) atts;

			for (int i = 0; i < length; i++) {
				declared[i]		 = a2.isDeclared(i);
				specified[i]     = a2.isSpecified(i);
			} // end for
		} // end if
		else {
			for (int i = 0; i < length; i++) {
				declared[i]		 = !"CDATA".equals(atts.getType(i));
				specified[i]     = true;
			} // end for
		} // end else
	} // end setAttributes()

	/**
	 * Assign a value to the "declared" flag of a specific attribute.
	 * This is normally needed only for attributes of type CDATA,
	 * including attributes whose type is changed to or from CDATA.
	 *
	 * @param index The index of the attribute (zero-based).
	 * @param value The desired flag value.
	 *
	 * @exception ArrayIndexOutOfBoundsException When the supplied
	 * 			  index does not identify an attribute.
	 *
	 * @see #setType
	 */
	public void setDeclared(
		final int index,
		final boolean value) {
		if ((index < 0) || (index >= getLength())) {
			throw new ArrayIndexOutOfBoundsException(
				"No attribute at index: " + index);
		}

		declared[index] = value;
	} // end setDeclared()

	////////////////////////////////////////////////////////////////////
	// Implementation of Attributes2
	////////////////////////////////////////////////////////////////////
	/**
	 * Returns the current value of the attribute's "declared" flag.
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws ArrayIndexOutOfBoundsException DOCUMENT ME!
	 */

	// javadoc mostly from interface
	public boolean isDeclared(final int index) {
		if ((index < 0) || (index >= getLength())) {
			throw new ArrayIndexOutOfBoundsException(
				"No attribute at index: " + index);
		}

		return declared[index];
	} // end isDeclared()

	/**
	 * Returns the current value of the attribute's "declared" flag.
	 *
	 * @param uri DOCUMENT ME!
	 * @param localName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IllegalArgumentException DOCUMENT ME!
	 */

	// javadoc mostly from interface
	public boolean isDeclared(
		final String uri,
		final String localName) {
		int index = getIndex(uri, localName);

		if (index < 0) {
			throw new IllegalArgumentException(
				"No such attribute: local=" + localName +
				", namespace=" + uri);
		}

		return declared[index];
	} // end isDeclared()

	/**
	 * Returns the current value of the attribute's "declared" flag.
	 *
	 * @param qName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IllegalArgumentException DOCUMENT ME!
	 */

	// javadoc mostly from interface
	public boolean isDeclared(final String qName) {
		int index = getIndex(qName);

		if (index < 0) {
			throw new IllegalArgumentException("No such attribute: " +
				qName);
		}

		return declared[index];
	} // end isDeclared()

	/**
	 * Assign a value to the "specified" flag of a specific attribute.
	 * This is the only way this flag can be cleared, except clearing
	 * by initialization with the copy constructor.
	 *
	 * @param index The index of the attribute (zero-based).
	 * @param value The desired flag value.
	 *
	 * @exception ArrayIndexOutOfBoundsException When the supplied
	 * 			  index does not identify an attribute.
	 */
	public void setSpecified(
		final int index,
		final boolean value) {
		if ((index < 0) || (index >= getLength())) {
			throw new ArrayIndexOutOfBoundsException(
				"No attribute at index: " + index);
		}

		specified[index] = value;
	} // end setSpecified()

	/**
	 * Returns the current value of an attribute's "specified" flag.
	 *
	 * @param index The attribute index (zero-based).
	 *
	 * @return current flag value
	 *
	 * @exception ArrayIndexOutOfBoundsException When the supplied
	 * 			  index does not identify an attribute.
	 */
	public boolean isSpecified(final int index) {
		if ((index < 0) || (index >= getLength())) {
			throw new ArrayIndexOutOfBoundsException(
				"No attribute at index: " + index);
		}

		return specified[index];
	} // end isSpecified()

	/**
	 * Returns the current value of an attribute's "specified" flag.
	 *
	 * @param uri The Namespace URI, or the empty string if the name
	 * 		  has no Namespace URI.
	 * @param localName The attribute's local name.
	 *
	 * @return current flag value
	 *
	 * @exception IllegalArgumentException When the supplied names do
	 * 			  not identify an attribute.
	 */
	public boolean isSpecified(
		final String uri,
		final String localName) {
		int index = getIndex(uri, localName);

		if (index < 0) {
			throw new IllegalArgumentException(
				"No such attribute: local=" + localName +
				", namespace=" + uri);
		}

		return specified[index];
	} // end isSpecified()

	/**
	 * Returns the current value of an attribute's "specified" flag.
	 *
	 * @param qName The XML qualified (prefixed) name.
	 *
	 * @return current flag value
	 *
	 * @exception IllegalArgumentException When the supplied name does
	 * 			  not identify an attribute.
	 */
	public boolean isSpecified(final String qName) {
		int index = getIndex(qName);

		if (index < 0) {
			throw new IllegalArgumentException("No such attribute: " +
				qName);
		}

		return specified[index];
	} // end isSpecified()

	/**
	 * Add an attribute to the end of the list, setting its "specified"
	 * flag to true. To set that flag's value to false, use {@link
	 * #setSpecified}.
	 * 
	 * <p>
	 * Unless the attribute <em>type</em> is CDATA, this attribute is
	 * marked as being declared in the DTD. To set that flag's value
	 * to true for CDATA attributes, use {@link #setDeclared}.
	 * </p>
	 *
	 * @see AttributesImpl#addAttribute
	 */
	public void addAttribute(
		final String uri,
		final String localName,
		final String qName,
		final String type,
		final String value) {
		super.addAttribute(uri, localName, qName, type, value);

		int length = getLength();

		if ((specified == null) || (length >= specified.length)) {
			boolean newFlags[];

			newFlags = new boolean[length];

			if (declared != null) {
				System.arraycopy(declared, 0, newFlags, 0,
					declared.length);
			}

			declared	  = newFlags;

			newFlags	  = new boolean[length];

			if (specified != null) {
				System.arraycopy(specified, 0, newFlags, 0,
					specified.length);
			}

			specified = newFlags;
		} // end if

		specified[length - 1]     = true;
		declared[length - 1]	  = !"CDATA".equals(type);
	} // end addAttribute()

	// javadoc entirely from superclass
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 */
	public void removeAttribute(final int index) {
		int origMax = getLength() - 1;

		super.removeAttribute(index);

		if (index != origMax) {
			System.arraycopy(declared, index + 1, declared, index,
				origMax - index);
			System.arraycopy(specified, index + 1, specified, index,
				origMax - index);
		} // end if
	} // end removeAttribute()
} // end CAttributes2Impl
