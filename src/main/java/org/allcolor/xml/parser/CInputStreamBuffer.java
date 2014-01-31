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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CInputStreamBuffer
	extends InputStream {
	/** DOCUMENT ME! */
	boolean booMark = false;

	/** DOCUMENT ME! */
	private ByteArrayOutputStream bout = new ByteArrayOutputStream(2048);

	/** DOCUMENT ME! */
	private InputStream in;

	/**
	 * Creates a new CInputStreamBuffer object.
	 *
	 * @param in DOCUMENT ME!
	 */
	public CInputStreamBuffer(final InputStream in) {
		super();
		this.in = in;
	} // end CInputStreamBuffer()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#available()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public int available()
		throws IOException {
		return in.available() + bout.toByteArray().length;
	} // end available()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#close()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public void close()
		throws IOException {
		in.close();
	} // end close()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#mark(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param readlimit DOCUMENT ME!
	 */
	public void mark(final int readlimit) {
		booMark = true;
	} // end mark()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#markSupported()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean markSupported() {
		return true;
	} // end markSupported()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#read()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public int read()
		throws IOException {
		byte buffer[] = new byte[1];
		int  read = read(buffer, 0, 1);

		if (read != -1) {
			return buffer[0];
		} // end if

		return -1;
	} // end read()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param b DOCUMENT ME!
	 * @param off DOCUMENT ME!
	 * @param len DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public int read(
		final byte b[],
		final int off,
		int len)
		throws IOException {
		if (!booMark && (bout.toByteArray().length > 0)) {
			byte array[] = bout.toByteArray();

			if (len > array.length) {
				len = array.length;
			}

			System.arraycopy(array, 0, b, off, len);
			bout.reset();

			if (array.length > len) {
				bout.write(array, len, array.length - len);
			} // end if

			return len;
		} // end if

		int read = in.read(b, off, len);

		if (booMark && (read != -1)) {
			bout.write(b, off, read);
		} // end if

		return read;
	} // end read()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#read(byte[])
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param b DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public int read(final byte b[])
		throws IOException {
		return read(b, 0, b.length);
	} // end read()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#reset()
	 */
	/**
	 * DOCUMENT ME!
	 */
	public void reset() {
		booMark = false;
	} // end reset()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.InputStream#skip(long)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param n DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws IOException DOCUMENT ME!
	 */
	public long skip(final long n)
		throws IOException {
		return in.skip(n);
	} // end skip()
} // end CInputStreamBuffer
