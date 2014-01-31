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

import org.w3c.dom.html.HTMLBaseElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLBaseElement
    extends CHTMLElement
    implements HTMLBaseElement {
    static final long serialVersionUID = -3884416256677200666L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLBaseElement(ADocument ownerDocument) {
        super("base", ownerDocument);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseElement#getHref()
     */
    public String getHref() {
        return getAttribute("href");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseElement#setHref(java.lang.String)
     */
    public void setHref(String href) {
        setAttribute(
            "href",
            href
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseElement#getTarget()
     */
    public String getTarget() {
        return getAttribute("target");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBaseElement#setTarget(java.lang.String)
     */
    public void setTarget(String target) {
        setAttribute(
            "target",
            target
        );
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