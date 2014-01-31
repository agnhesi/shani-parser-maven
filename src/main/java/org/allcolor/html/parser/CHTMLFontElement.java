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

import org.w3c.dom.html.HTMLFontElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLFontElement
    extends CHTMLElement
    implements HTMLFontElement {
    static final long serialVersionUID = 3843277059445784634L;
    private static final List ve = 
        Arrays.asList(
                new String[] {
                    "#PCDATA",
                    "a",
                    "br",
                    "span",
                    "bdo",
                    "object",
                    "applet",
                    "img",
                    "map",
                    "iframe",
                    "tt",
                    "i",
                    "b",
                    "u",
                    "s",
                    "strike",
                    "big",
                    "small",
                    "font",
                    "basefont",
                    "em",
                    "strong",
                    "dfn",
                    "code",
                    "q",
                    "samp",
                    "kbd",
                    "var",
                    "cite",
                    "abbr",
                    "acronym",
                    "sub",
                    "sup",
                    "input",
                    "select",
                    "textarea",
                    "label",
                    "button",
                    "ins",
                    "del",
                    "script"
                }
            );

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLFontElement(ADocument ownerDocument) {
        super("font", ownerDocument);
        validElement = ve;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFontElement#getColor()
     */
    public String getColor() {
        return getAttribute("color");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFontElement#setColor(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLFontElement#getFace()
     */
    public String getFace() {
        return getAttribute("face");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFontElement#setFace(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLFontElement#getSize()
     */
    public String getSize() {
        return getAttribute("size");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFontElement#setSize(java.lang.String)
     */
    public void setSize(String size) {
        setAttribute(
            "size",
            size
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.xml.parser.dom.CElement#toString()
     */
    public String toString() {
        return super.toString2();
    }
}