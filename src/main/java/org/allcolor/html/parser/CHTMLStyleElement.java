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
import org.allcolor.xml.parser.dom.CText;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLStyleElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLStyleElement
    extends CHTMLElement
    implements HTMLStyleElement {
    static final long serialVersionUID = -3706242048200078832L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLStyleElement(ADocument ownerDocument) {
        super("style", ownerDocument);
        validElement = Arrays.asList(new String[] {
                    "#PCDATA"
                }
            );
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

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLStyleElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLStyleElement#setDisabled(boolean)
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
     * @see org.w3c.dom.html.HTMLStyleElement#getMedia()
     */
    public String getMedia() {
        return getAttribute("media");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLStyleElement#setMedia(java.lang.String)
     */
    public void setMedia(String media) {
    	setAttribute("media",media);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLStyleElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLStyleElement#setType(java.lang.String)
     */
    public void setType(String type) {
    	setAttribute("type",type);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.xml.parser.dom.CElement#toString()
     */
    public String toString() {
        if (getAttribute("type")
                    .equals(""))
            setAttribute(
                "type",
                "text/css"
            );

        return super.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.Node#appendChild(org.w3c.dom.Node)
     */
    public Node appendChild(Node newChild)
        throws DOMException {
        if (newChild.getNodeType() == Node.TEXT_NODE) {
            if (CText.isElementContentWhitespace((Text) newChild)) {
                return newChild;
            }
        }

        return super.appendChild(newChild);
    }
}