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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.allcolor.xml.parser.dom.ADocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.html2.HTMLTitleElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLTitleElement
    extends CHTMLElement
    implements HTMLTitleElement {
    static final long serialVersionUID = -7714584981741654930L;
    private static final List ve = 
    	Arrays.asList(new String[] {
                "#PCDATA"
            }
        );
	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLTitleElement(ADocument ownerDocument) {
        super("title", ownerDocument);
        validElement = ve;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTitleElement#getText()
     */
    public String getText() {
    	return getTextContent().trim();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTitleElement#setText(java.lang.String)
     */
    public void setText(String text) {
        text = text.trim();

        List toRemove = new ArrayList();
        NodeList nl = getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            toRemove.add(nl.item(i));
        }

        for (int i = 0; i < toRemove.size(); i++) {
            removeChild((Node) toRemove.get(i));
        }

        Text textNode = ownerDocument.createTextNode(text);
        appendChild(textNode);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "head";
    }
}