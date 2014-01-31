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

import org.w3c.dom.html.HTMLHtmlElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLHtmlElement
    extends CHTMLElement
    implements HTMLHtmlElement {
    static final long serialVersionUID = -399867662641241034L;
    private static final List ve =             
        Arrays.asList(new String[] {
                "head",
                "body",
                "frameset"
            }
        );

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLHtmlElement(ADocument ownerDocument) {
        super("html", ownerDocument);
        validElement = ve;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHtmlElement#getVersion()
     */
    public String getVersion() {
        return getAttribute("version");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHtmlElement#setVersion(java.lang.String)
     */
    public void setVersion(String version) {
        setAttribute(
            "version",
            version
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
}