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

import org.w3c.dom.html.HTMLHRElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLHrElement
    extends CHTMLElement
    implements HTMLHRElement {
    static final long serialVersionUID = 5267796053238956832L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLHrElement(ADocument ownerDocument) {
        super("hr", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLHeadingElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHeadingElement#setAlign(java.lang.String)
     */
    public void setAlign(String align) {
        setAttribute(
            "align",
            align
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHRElement#getNoShade()
     */
    public boolean getNoShade() {
        return "noshade".equals(getAttribute("noshade"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHRElement#setNoShade(boolean)
     */
    public void setNoShade(boolean noShade) {
        if (noShade) {
            setAttribute(
                "noshade",
                "noshade"
            );
        } else {
            removeAttribute("noshade");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHRElement#getSize()
     */
    public String getSize() {
        return getAttribute("size");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHRElement#setSize(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLHRElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLHRElement#setWidth(java.lang.String)
     */
    public void setWidth(String width) {
        setAttribute(
            "width",
            width
        );
    }
}