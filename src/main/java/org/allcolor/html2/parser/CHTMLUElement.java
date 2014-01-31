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

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLUElement
    extends CHTMLElement {
    static final long serialVersionUID = -2978786737239465064L;
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
    public CHTMLUElement(ADocument ownerDocument) {
        super("u", ownerDocument);
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
     * @see org.allcolor.xml.parser.dom.CElement#toString()
     */
    public String toString() {
        return super.toString2();
    }
}