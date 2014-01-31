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

package org.allcolor.html.parser;

import java.io.Serializable;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLOptionsCollection;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLCollection
    implements HTMLCollection,HTMLOptionsCollection,Serializable {
    public void setLength(int length) throws DOMException {
    	throw new DOMException(DOMException.NOT_SUPPORTED_ERR,"Not supported.");
    }

	static final long serialVersionUID = -1555803760955282636L;
	/** DOCUMENT ME! */
    private NodeList nl;

    /**
     * Creates a new CHTMLCollection object.
     *
     * @param nl DOCUMENT ME!
     */
    public CHTMLCollection(NodeList nl) {
        this.nl = nl;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLCollection#getLength()
     */
    public int getLength() {
        return nl.getLength();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLCollection#item(int)
     */
    public Node item(int index) {
        return nl.item(index);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLCollection#namedItem(java.lang.String)
     */
    public Node namedItem(String name) {
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                if (name.equals(elem.getAttribute("name")) ||
                	 name.equals(elem.getAttribute("id")))
                    return elem;
            }
        }

        return null;
    }

	public final NodeList getNl() {
		return nl;
	}

	public final void setNl(NodeList nl) {
		this.nl = nl;
	}
}