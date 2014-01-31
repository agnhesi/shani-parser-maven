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

import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.html.HTMLBaseFontElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLBasefontElement
    extends CHTMLElement
    implements HTMLBaseFontElement {
    static final long serialVersionUID = 7016263324338113119L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLBasefontElement(ADocument ownerDocument) {
        super("basefont", ownerDocument);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "body";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseFontElement#getColor()
     */
    public String getColor() {
        return getAttribute("color");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseFontElement#setColor(java.lang.String)
     */
    public void setColor(String color) {
        setAttribute(
            "color",
            color
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseFontElement#getFace()
     */
    public String getFace() {
        return getAttribute("face");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseFontElement#setFace(java.lang.String)
     */
    public void setFace(String face) {
        setAttribute(
            "face",
            face
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseFontElement#getSize()
     */
    public String getSize() {
        try {
            return getAttribute("size");
        } catch (Exception ignore) {
            return ""+0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseFontElement#setSize(int)
     */
    public void setSize(String size) {
        setAttribute(
            "size",
            size
        );
    }
}