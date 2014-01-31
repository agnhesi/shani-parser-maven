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

import org.w3c.dom.html2.HTMLMetaElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLMetaElement
    extends CHTMLElement
    implements HTMLMetaElement {
    static final long serialVersionUID = -9168520995786657370L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLMetaElement(ADocument ownerDocument) {
        super("meta", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLMetaElement#getContent()
     */
    public String getContent() {
        return getAttribute("content");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#setContent(java.lang.String)
     */
    public void setContent(String content) {
        setAttribute(
            "content",
            content
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#getHttpEquiv()
     */
    public String getHttpEquiv() {
        return getAttribute("http-equiv");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#setHttpEquiv(java.lang.String)
     */
    public void setHttpEquiv(String httpEquiv) {
        setAttribute(
            "http-equiv",
            httpEquiv
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#setName(java.lang.String)
     */
    public void setName(String name) {
        setAttribute(
            "name",
            name
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#getScheme()
     */
    public String getScheme() {
        return getAttribute("scheme");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLMetaElement#setScheme(java.lang.String)
     */
    public void setScheme(String scheme) {
        setAttribute(
            "scheme",
            scheme
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#toString()
     */
    public String toString() {
        if (getAttribute("http-equiv")
                    .equalsIgnoreCase("content-type")) {
            setAttribute(
                "content",
                "text/html; charset=utf-8"
            );
        }

        return super.toString();
    }
}