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

import org.w3c.dom.html2.HTMLAreaElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLAreaElement
    extends CHTMLElement
    implements HTMLAreaElement{
    static final long serialVersionUID = -4244544239487534267L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLAreaElement(ADocument ownerDocument) {
        super("area", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLAreaElement#getAccessKey()
     */
    public String getAccessKey() {
        return getAttribute("accesskey");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setAccessKey(java.lang.String)
     */
    public void setAccessKey(String accessKey) {
        setAttribute(
            "accesskey",
            accessKey
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#getAlt()
     */
    public String getAlt() {
        return getAttribute("alt");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setAlt(java.lang.String)
     */
    public void setAlt(String alt) {
        setAttribute(
            "alt",
            alt
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#getCoords()
     */
    public String getCoords() {
        return getAttribute("coords");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setCoords(java.lang.String)
     */
    public void setCoords(String coords) {
        setAttribute(
            "coords",
            coords
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#getHref()
     */
    public String getHref() {
        return getAttribute("href");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setHref(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAreaElement#getNoHref()
     */
    public boolean getNoHref() {
        return "nohref".equals(getAttribute("nohref"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setNoHref(boolean)
     */
    public void setNoHref(boolean noHref) {
        if (noHref) {
            setAttribute(
                "nohref",
                "nohref"
            );
        } else {
            removeAttribute("nohref");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#getShape()
     */
    public String getShape() {
        return getAttribute("shape");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setShape(java.lang.String)
     */
    public void setShape(String shape) {
        setAttribute(
            "shape",
            shape
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#getTabIndex()
     */
    public int getTabIndex() {
        try {
            return Integer.parseInt(getAttribute("tabindex"));
        } catch (Exception ignore) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setTabIndex(int)
     */
    public void setTabIndex(int tabIndex) {
        setAttribute(
            "tabindex",
            "" + tabIndex
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#getTarget()
     */
    public String getTarget() {
        return getAttribute("target");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAreaElement#setTarget(java.lang.String)
     */
    public void setTarget(String target) {
        setAttribute(
            "target",
            target
        );
    }
}