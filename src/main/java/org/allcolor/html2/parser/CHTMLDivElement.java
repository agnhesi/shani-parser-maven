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

import org.w3c.dom.html2.HTMLDivElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLDivElement
    extends CHTMLElement
    implements HTMLDivElement {
    static final long serialVersionUID = -8608539496649527866L;
    private static final List ve = 
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

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLDivElement(ADocument ownerDocument) {
        super("div", ownerDocument);
        validElement = ve;
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
     * @see org.w3c.dom.html.HTMLDivElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLDivElement#setAlign(java.lang.String)
     */
    public void setAlign(String align) {
        setAttribute(
            "align",
            align
        );
    }
}