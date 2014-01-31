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

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLBRElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLBrElement
    extends CHTMLElement
    implements HTMLBRElement {
    static final long serialVersionUID = -7530860262289319248L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLBrElement(ADocument ownerDocument) {
        super("br", ownerDocument);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBRElement#getClear()
     */
    public String getClear() {
        return getAttribute("clear");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBRElement#setClear(java.lang.String)
     */
    public void setClear(String clear) {
        setAttribute(
            "clear",
            clear
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "body";
    }

    /**
     * DOCUMENT ME!
     *
     * @param newChild DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Node appendChild(Node newChild)
        throws DOMException {
        return null;
    }
}