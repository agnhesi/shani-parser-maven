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

import org.w3c.dom.Element;
import org.w3c.dom.html2.HTMLFormElement;
import org.w3c.dom.html2.HTMLIsIndexElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLIsindexElement
    extends CHTMLElement
    implements HTMLIsIndexElement {
    static final long serialVersionUID = 5602209841795539961L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLIsindexElement(ADocument ownerDocument) {
        super("isindex", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLIsIndexElement#getForm()
     */
    public HTMLFormElement getForm() {
        Element elem = this;

        while (elem.getParentNode() != null) {
            elem = (Element) elem.getParentNode();

            if (elem instanceof HTMLFormElement)
                return (HTMLFormElement) elem;
        }

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIsIndexElement#getPrompt()
     */
    public String getPrompt() {
        return getAttribute("prompt");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIsIndexElement#setPrompt(java.lang.String)
     */
    public void setPrompt(String prompt) {
        setAttribute(
            "prompt",
            prompt
        );
    }
}