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

import org.w3c.dom.html2.HTMLOptGroupElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLOptgroupElement
    extends CHTMLElement
    implements HTMLOptGroupElement {
    static final long serialVersionUID = -7412599727608108377L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLOptgroupElement(ADocument ownerDocument) {
        super("optgroup", ownerDocument);
        validElement = Arrays.asList(new String[] {
                    "option"
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
     * @see org.w3c.dom.html.HTMLOptGroupElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptGroupElement#setDisabled(boolean)
     */
    public void setDisabled(boolean disabled) {
        if (disabled) {
            setAttribute(
                "disabled",
                "disabled"
            );
        } else {
            removeAttribute("disabled");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptGroupElement#getLabel()
     */
    public String getLabel() {
        return getAttribute("label");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptGroupElement#setLabel(java.lang.String)
     */
    public void setLabel(String label) {
        setAttribute(
            "label",
            label
        );
    }
}