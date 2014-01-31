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

import org.w3c.dom.html2.HTMLMenuElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLMenuElement
    extends CHTMLElement
    implements HTMLMenuElement {
    static final long serialVersionUID = -6877177036682168404L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLMenuElement(ADocument ownerDocument) {
        super("menu", ownerDocument);
        validElement = Arrays.asList(new String[] {
                    "li"
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
     * @see org.w3c.dom.html.HTMLMenuElement#getCompact()
     */
    public boolean getCompact() {
        return "compact".equals(getAttribute("compact"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMenuElement#setCompact(boolean)
     */
    public void setCompact(boolean compact) {
    	if (compact) {
    		setAttribute("compact", "compact");
    	} else {
    		setAttribute("compact", "");
    	}
    }
}