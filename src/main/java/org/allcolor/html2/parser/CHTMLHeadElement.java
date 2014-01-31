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

import org.w3c.dom.html2.HTMLHeadElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLHeadElement
    extends CHTMLElement
    implements HTMLHeadElement {
    static final long serialVersionUID = -7305201812905302521L;
    private static final List ve =             
    	Arrays.asList(
                new String[] {
                    "base",
                    "title",
                    "script",
                    "style",
                    "meta",
                    "link",
                    "object",
                    "isindex"
                }
            );

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLHeadElement(ADocument ownerDocument) {
        super("head", ownerDocument);
        validElement = ve;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHeadElement#getProfile()
     */
    public String getProfile() {
        return getAttribute("profile");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHeadElement#setProfile(java.lang.String)
     */
    public void setProfile(String profile) {
        setAttribute(
            "profile",
            profile
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "html";
    }
}