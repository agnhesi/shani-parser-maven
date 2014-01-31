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

import org.w3c.dom.html2.HTMLUListElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLUlElement
    extends CHTMLElement
    implements HTMLUListElement {
    static final long serialVersionUID = -3261025663210631207L;
    private static final List ve = 
    	Arrays.asList(new String[] {
                "li"
            }
        );
	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLUlElement(ADocument ownerDocument) {
        super("ul", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLUListElement#getCompact()
     */
    public boolean getCompact() {
        return "compact".equals(getAttribute("compact"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLUListElement#setCompact(boolean)
     */
    public void setCompact(boolean compact) {
    	if (compact) {
    		setAttribute("compact", "compact");
    	} else {
    		setAttribute("compact", "");
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLUListElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLUListElement#setType(java.lang.String)
     */
    public void setType(String type) {
        setAttribute(
            "type",
            type
        );
    }
}