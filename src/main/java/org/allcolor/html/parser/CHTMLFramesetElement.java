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

import org.w3c.dom.html.HTMLFrameSetElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLFramesetElement
    extends CHTMLElement
    implements HTMLFrameSetElement {
    static final long serialVersionUID = 6906553529151496337L;
    private static final List ve = 
        Arrays.asList(new String[] {
                "frameset",
                "frame"
            }
        );

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLFramesetElement(ADocument ownerDocument) {
        super("frameset", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLFrameSetElement#getCols()
     */
    public String getCols() {
        return getAttribute("cols");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameSetElement#setCols(java.lang.String)
     */
    public void setCols(String cols) {
        setAttribute(
            "cols",
            cols
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameSetElement#getRows()
     */
    public String getRows() {
        return getAttribute("rows");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameSetElement#setRows(java.lang.String)
     */
    public void setRows(String rows) {
        setAttribute(
            "rows",
            rows
        );
    }
}