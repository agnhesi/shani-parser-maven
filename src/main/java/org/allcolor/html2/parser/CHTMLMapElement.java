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

import org.w3c.dom.html2.HTMLCollection;
import org.w3c.dom.html2.HTMLMapElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLMapElement
    extends CHTMLElement
    implements HTMLMapElement {
    static final long serialVersionUID = -7870294253607514116L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLMapElement(ADocument ownerDocument) {
        super("map", ownerDocument);
        validElement =
            Arrays.asList(
                new String[] {
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
                    "noscript",
                    "ins",
                    "del",
                    "script",
                    "area"
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
     * @see org.w3c.dom.html.HTMLMapElement#getAreas()
     */
    public HTMLCollection getAreas() {
        return new CHTMLCollection(getElementsByTagName("area"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMapElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMapElement#setName(java.lang.String)
     */
    public void setName(String name) {
        setAttribute(
            "name",
            name
        );
    }
}