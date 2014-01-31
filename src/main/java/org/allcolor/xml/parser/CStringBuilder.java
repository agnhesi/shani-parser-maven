/*
 * Copyright (C) 2005
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
 */
package org.allcolor.xml.parser;
import java.io.Serializable;


/**
 * <code>StringBuilder</code> represents a changeable
 * <code>String</code>. It provides the operations required to modify
 * the <code>StringBuilder</code>, including insert, replace, delete,
 * append, and reverse. It like <code>StringBuffer</code>, but is not
 * synchronized.  It is ideal for use when it is known that the object
 * will only be used from a single thread.
 * 
 * <p>
 * <code>StringBuilder</code>s are variable-length in nature, so even
 * if you initialize them to a certain size, they can still grow
 * larger than that. <em>Capacity</em> indicates the number of
 * characters the <code>StringBuilder</code> can have in it before it
 * has to grow (growing the char array is an expensive operation
 * involving <code>new</code>).
 * </p>
 * 
 * <p>
 * Incidentally, compilers often implement the String operator "+" by
 * using a <code>StringBuilder</code> operation:<br>
 * <code>a + b</code><br>
 * is the same as<br>
 * <code>new StringBuilder().append(a).append(b).toString()</code>.
 * </p>
 * 
 * <p>
 * Classpath's StringBuilder is capable of sharing memory with Strings
 * for efficiency.  This will help when a StringBuilder is converted
 * to a String and the StringBuilder is not changed after that (quite
 * common when performing string concatenation).
 * </p>
 *
 * @author Paul Fisher
 * @author John Keiser
 * @author Tom Tromey
 * @author Eric Blake ebb9-at-email.byu.edu
 *
 * @see String
 * @see StringBuffer
 * @since 1.5
 */

//FIX15: Implement Appendable when co-variant methods are available
public final class CStringBuilder
	implements Serializable {
	static final long serialVersionUID = 7548325870937714812L;

	/** The default capacity of a buffer. */
	private final static int DEFAULT_CAPACITY = 16;

	/**
	 * The buffer.  Note that this has permissions set this way so that
	 * String can get the value.
	 *
	 * @serial the buffer
	 */
	char value[];

	// Implementation note: if you change this class, you usually will
	// want to change StringBuffer as well.
	/**
	 * Index of next available character (and thus the size of the
	 * current string contents).  Note that this has permissions set
	 * this way so that String can get the value.
	 *
	 * @serial the number of characters in the buffer
	 */
	int count;

	
	public void reset() {
		count = 0;
	}
	
	/**
	 * Create a new StringBuilder with default capacity 16.
	 */
	public CStringBuilder() {
		this(DEFAULT_CAPACITY);
	} // end CStringBuilder()

	/**
	 * Create an empty <code>StringBuilder</code> with the specified
	 * initial capacity.
	 *
	 * @param capacity the initial capacity
	 */
	public CStringBuilder(final int capacity) {
		value = new char[capacity];
	} // end CStringBuilder()

	public CStringBuilder delete(
		final int start,
		int end) {
		if (end > count) {
			end = count;
		}
		// This will unshare if required.
		int minimumCapacity = count;
		if (minimumCapacity > value.length) {
			int max = (value.length * 2) + 2;
			minimumCapacity = ((minimumCapacity < max)
				? max
				: minimumCapacity);

			char nb[] = new char[minimumCapacity];
			System.arraycopy(value, 0, nb, 0, count);
			value = nb;
		} // end if
		if ((count - end) != 0) {
			System.arraycopy(value, end, value, start, count - end);
		}
		count -= (end - start);
		return this;
	} // end delete()
	
	public CStringBuilder insert(
		final int offset,
		final char str[],
		final int str_offset,
		final int len) {
		int minimumCapacity = count+len;
		if (minimumCapacity > value.length) {
			int max = (value.length * 2) + 2;
			minimumCapacity = ((minimumCapacity < max)
				? max
				: minimumCapacity);

			char nb[] = new char[minimumCapacity];
			System.arraycopy(value, 0, nb, 0, count);
			value = nb;
		} // end if
		System.arraycopy(value, offset, value, offset + len,
			count - offset);
		System.arraycopy(str, str_offset, value, offset, len);
		count += len;
		return this;
	} // end insert()	

	public CStringBuilder append(final Object obj) {
		if (obj == null) {
			return this;
		}
		return append(obj.toString());
	}
	
	public CStringBuilder append(final int i) {
		return append(String.valueOf(i));
	}

	public CStringBuilder append(final String string) {
		if (string == null) {
			return this;
		}
		int len = string.length();
		int minimumCapacity = count + len;
		if (minimumCapacity > value.length) {
			int max = (value.length * 2) + 2;
			minimumCapacity = ((minimumCapacity < max)
				? max
				: minimumCapacity);

			char nb[] = new char[minimumCapacity];
			System.arraycopy(value, 0, nb, 0, count);
			value = nb;
		} // end if
		System.arraycopy(string.toCharArray(), 0, value, this.count, len);
		this.count += len;
		return this;
	} // end append()

	/**
	 * Append the <code>char</code> to this <code>StringBuilder</code>.
	 *
	 * @param ch the <code>char</code> to append
	 *
	 * @return this <code>StringBuilder</code>
	 */
	public CStringBuilder append(final char ch) {
		if (count == value.length) {
			final char nb[] = new char[(value.length * 2) + 2];
			System.arraycopy(value, 0, nb, 0, count);
			value = nb;
		} // end if
		value[count++] = ch;

		return this;
	} // end append()

	/**
	 * Get the character at the specified index.
	 *
	 * @param index the index of the character to get, starting at 0
	 *
	 * @return the character at the specified index
	 *
	 * @throws StringIndexOutOfBoundsException if index is negative or
	 * 		   &gt;= length() (while unspecified, this is a
	 * 		   StringIndexOutOfBoundsException)
	 */
	public char charAt(final int index) {
		return value[index];
	} // end charAt()

	/**
	 * Finds the first instance of a substring in this StringBuilder.
	 *
	 * @param str String to find
	 *
	 * @return location (base 0) of the String, or -1 if not found
	 *
	 * @see #indexOf(String, int)
	 */
	public int indexOf(final String str) {
		return indexOf(str, 0);
	} // end indexOf()

	/**
	 * Finds the first instance of a String in this StringBuilder,
	 * starting at a given index.  If starting index is less than 0,
	 * the search starts at the beginning of this String.  If the
	 * starting index is greater than the length of this String, or
	 * the substring is not found, -1 is returned.
	 *
	 * @param str String to find
	 * @param fromIndex index to start the search
	 *
	 * @return location (base 0) of the String, or -1 if not found
	 */
	public int indexOf(
		final String str,
		int fromIndex) {
		if (fromIndex < 0) {
			fromIndex = 0;
		}

		int limit = count - str.length();

		for (; fromIndex <= limit; fromIndex++) {
			if (regionMatches(fromIndex, str)) {
				return fromIndex;
			}
		} // end for

		return -1;
	} // end indexOf()

	/**
	 * Finds the last instance of a substring in this StringBuilder.
	 *
	 * @param str String to find
	 *
	 * @return location (base 0) of the String, or -1 if not found
	 *
	 * @see #lastIndexOf(String, int)
	 */
	public int lastIndexOf(final String str) {
		return lastIndexOf(str, count - str.length());
	} // end lastIndexOf()

	/**
	 * Finds the last instance of a String in this StringBuilder,
	 * starting at a given index.  If starting index is greater than
	 * the maximum valid index, then the search begins at the end of
	 * this String.  If the starting index is less than zero, or the
	 * substring is not found, -1 is returned.
	 *
	 * @param str String to find
	 * @param fromIndex index to start the search
	 *
	 * @return location (base 0) of the String, or -1 if not found
	 */
	public int lastIndexOf(
		final String str,
		int fromIndex) {
		fromIndex = Math.min(fromIndex, count - str.length());

		for (; fromIndex >= 0; fromIndex--) {
			if (regionMatches(fromIndex, str)) {
				return fromIndex;
			}
		} // end for

		return -1;
	} // end lastIndexOf()

	/**
	 * Get the length of the <code>String</code> this
	 * <code>StringBuilder</code> would create. Not to be confused
	 * with the <em>capacity</em> of the <code>StringBuilder</code>.
	 *
	 * @return the length of this <code>StringBuilder</code>
	 *
	 * @see #capacity()
	 * @see #setLength(int)
	 */
	public int length() {
		return count;
	} // end length()

	/**
	 * Creates a substring of this StringBuilder, starting at a
	 * specified index and ending at the end of this StringBuilder.
	 *
	 * @param beginIndex index to start substring (base 0)
	 *
	 * @return new String which is a substring of this StringBuilder
	 *
	 * @see #substring(int, int)
	 */
	public String substring(final int beginIndex) {
		return substring(beginIndex, count);
	} // end substring()

	/**
	 * Creates a substring of this StringBuilder, starting at a
	 * specified index and ending at one character before a specified
	 * index.
	 *
	 * @param beginIndex index to start at (inclusive, base 0)
	 * @param endIndex index to end at (exclusive)
	 *
	 * @return new String which is a substring of this StringBuilder
	 *
	 * @throws StringIndexOutOfBoundsException if beginIndex or
	 * 		   endIndex is out of bounds
	 */
	public String substring(
		final int beginIndex,
		final int endIndex) {
		int len = endIndex - beginIndex;

		if (len == 0) {
			return "";
		}

		return new String(value, beginIndex, len);
	} // end substring()

	/**
	 * Convert this <code>StringBuilder</code> to a
	 * <code>String</code>. The String is composed of the characters
	 * currently in this StringBuilder. Note that the result is a
	 * copy, and that future modifications to this buffer do not
	 * affect the String.
	 *
	 * @return the characters in this StringBuilder
	 */
	public String toString() {
		return new String(value, 0, count);
	} // end toString()

	/**
	 * Predicate which determines if a substring of this matches
	 * another String starting at a specified offset for each String
	 * and continuing for a specified length. This is more efficient
	 * than creating a String to call indexOf on.
	 *
	 * @param toffset index to start comparison at for this String
	 * @param other non-null String to compare to region of this
	 *
	 * @return true if regions match, false otherwise
	 *
	 * @see #indexOf(String, int)
	 * @see #lastIndexOf(String, int)
	 * @see String#regionMatches(boolean, int, String, int, int)
	 */
	private boolean regionMatches(
		int toffset,
		final String other) {
		int  len	  = other.length();
		char ovalue[] = other.toCharArray();
		int  index    = 0;

		while (--len >= 0) {
			if (value[toffset++] != ovalue[index++]) {
				return false;
			}
		} // end while

		return true;
	} // end regionMatches()
} // end CStringBuilder
