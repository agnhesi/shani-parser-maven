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

import java.util.Arrays;
import java.util.List;

import org.allcolor.xml.parser.dom.ADocument;
import org.w3c.dom.html2.HTMLTableSectionElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLTbodyElement
    extends CHTMLTableElement
    implements HTMLTableSectionElement {
    static final long serialVersionUID = 6248466297527484959L;
    private static final List ve = 
        Arrays.asList(new String[] {
                "tr"
            }
        );

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLTbodyElement(ADocument ownerDocument) {
        super("tbody", ownerDocument);
        validElement = ve;
    }
}