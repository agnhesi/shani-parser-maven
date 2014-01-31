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
package org.allcolor.dtd.parser;

/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CPStringTokenizer {
	/** DOCUMENT ME! */
	private final char [] in;

	/** DOCUMENT ME! */
	private Token token = new Token(0);

	/** DOCUMENT ME! */
	private int length = 0;

	/** DOCUMENT ME! */
	private int mTokenLength = 0;

	/** DOCUMENT ME! */
	private int mTokenStartIndex = 0;

	/**
	 * Creates a new CPStringTokenizer object.
	 *
	 * @param in DOCUMENT ME!
	 */
	public CPStringTokenizer(final String in) {
		this.in		    = in.toCharArray();
		this.length     = in.length();
		token		    = analyze(token);
	} // end CPStringTokenizer()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasMoreTokens() {
		return token.length > 0;
	} // end hasMoreTokens()

	/**
	 * DOCUMENT ME!
	 */
	public void mark() {
		mTokenStartIndex     = token.startIndex;
		mTokenLength		 = token.length;
	} // end mark()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String nextToken() {
		String result = new String(in,token.startIndex, token.length);
		token.startIndex += token.length;
		token.length = 0;
		analyze(token);

		return result;
	} // end nextToken()

	/**
	 * DOCUMENT ME!
	 */
	public void reset() {
		token.startIndex     = mTokenStartIndex;
		token.length		 = mTokenLength;
	} // end reset()

	/**
	 * DOCUMENT ME!
	 *
	 * @param token DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private Token analyze(final Token token) {
		for (int i = token.startIndex + token.length; i < length;
				i++) {
			char c = in[i];

			if ((c == '&') || (c == '%') || (c == ';')) {
				if (token.length > 0) {
					return token;
				} // end if

				token.length++;

				return token;
			} // end if
			else {
				token.length++;
			} // end else
		} // end for

		if (token.length > 0) {
			return token;
		} // end if

		return token;
	} // end analyze()

	/**
	 * DOCUMENT ME!
	 *
	 * @author $author$
	 * @version $Revision: 1.4 $
	 */
	private final static class Token {
		/** DOCUMENT ME! */
		int length = 0;

		/** DOCUMENT ME! */
		int startIndex;

		/**
		 * Creates a new Token object.
		 *
		 * @param startIndex DOCUMENT ME!
		 */
		public Token(final int startIndex) {
			this.startIndex = startIndex;
		} // end Token()
	} // end Token
} // end CPStringTokenizer
