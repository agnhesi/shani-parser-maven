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


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLStrikeElement
    extends CHTMLElement {
    static final long serialVersionUID = -3444423585140384131L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLStrikeElement(ADocument ownerDocument) {
        super("strike", ownerDocument);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        // TODO Auto-generated method stub
        return null;
    }
}