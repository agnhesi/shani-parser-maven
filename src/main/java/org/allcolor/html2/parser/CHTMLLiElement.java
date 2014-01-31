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

package org.allcolor.html2.parser;

import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.html2.HTMLLIElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLLiElement
    extends CHTMLElement
    implements HTMLLIElement {
    static final long serialVersionUID = 6505279767605275541L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLLiElement(ADocument ownerDocument) {
        super("li", ownerDocument);
        validElement =
            Arrays.asList(
                new String[] {
                    "#PCDATA",
                    "p",
                    "h1",
                    "h2",
                    "h3",
                    "h4",
                    "h5",
                    "h6",
                    "div",
                    "ul",
                    "ol",
                    "dl",
                    "menu",
                    "dir",
                    "pre",
                    "hr",
                    "blockquote",
                    "address",
                    "center",
                    "noframes",
                    "isindex",
                    "fieldset",
                    "table",
                    "form",
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
                    "noscript",
                    "ins",
                    "del",
                    "script"
                }
            );
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
     * @see org.w3c.dom.html.HTMLLIElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLIElement#setType(java.lang.String)
     */
    public void setType(String type) {
        setAttribute(
            "type",
            type
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLIElement#getValue()
     */
    public int getValue() {
        try {
            return Integer.parseInt(getAttribute("value"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLIElement#setValue(int)
     */
    public void setValue(int value) {
        setAttribute(
            "value",
            "" + value
        );
    }
}