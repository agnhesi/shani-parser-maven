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
package org.allcolor.utils;
import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CCmdLineOptions {
	/** DOCUMENT ME! */
	List lArgs;

	/**
	 * Creates a new CCmdLineOptions object.
	 *
	 * @param options DOCUMENT ME!
	 */
	private CCmdLineOptions(final List options) {
		this.lArgs = options;
	} // end CCmdLineOptions()

	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static CCmdLineOptions getInstance(final String args[]) {
		return new CCmdLineOptions(Arrays.asList(args));
	} // end getInstance()

	/**
	 * DOCUMENT ME!
	 *
	 * @param option DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isOptionPresent(final String option) {
		if (lArgs.contains(option)) {
			int index = lArgs.indexOf(option);

			if ((index + 1) < lArgs.size()) {
				return true;
			}
		} // end if

		return false;
	} // end isOptionPresent()

	/**
	 * DOCUMENT ME!
	 *
	 * @param option DOCUMENT ME!
	 * @param args DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static boolean isOptionPresent(
		final String option,
		final String args[]) {
		List lArgs = Arrays.asList(args);

		if (lArgs.contains(option)) {
			int index = lArgs.indexOf(option);

			if ((index + 1) < lArgs.size()) {
				return true;
			}
		} // end if

		return false;
	} // end isOptionPresent()

	/**
	 * DOCUMENT ME!
	 *
	 * @param option DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOptionValue(final String option) {
		if (isOptionPresent(option)) {
			int index = lArgs.indexOf(option);

			return (String) lArgs.get(index + 1);
		} // end if

		return null;
	} // end getOptionValue()

	/**
	 * DOCUMENT ME!
	 *
	 * @param option DOCUMENT ME!
	 * @param args DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static String getOptionValue(
		final String option,
		final String args[]) {
		List lArgs = Arrays.asList(args);

		if (lArgs.contains(option)) {
			int index = lArgs.indexOf(option);

			if ((index + 1) < lArgs.size()) {
				return (String) lArgs.get(index + 1);
			}
		} // end if

		return null;
	} // end getOptionValue()

	/**
	 * DOCUMENT ME!
	 *
	 * @param option DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isSingleOptionPresent(final String option) {
		if (lArgs.contains(option)) {
			return true;
		} // end if

		return false;
	} // end isSingleOptionPresent()

	/**
	 * DOCUMENT ME!
	 *
	 * @param option DOCUMENT ME!
	 * @param args DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static boolean isSingleOptionPresent(
		final String option,
		final String args[]) {
		List lArgs = Arrays.asList(args);

		if (lArgs.contains(option)) {
			return true;
		} // end if

		return false;
	} // end isSingleOptionPresent()
} // end CCmdLineOptions
