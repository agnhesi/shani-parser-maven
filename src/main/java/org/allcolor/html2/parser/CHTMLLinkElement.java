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

import org.w3c.dom.html2.HTMLLinkElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLLinkElement
    extends CHTMLElement
    implements HTMLLinkElement {
    static final long serialVersionUID = -7070063999705660939L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLLinkElement(ADocument ownerDocument) {
        super("link", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLLinkElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setDisabled(boolean)
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
     * @see org.w3c.dom.html.HTMLLinkElement#getCharset()
     */
    public String getCharset() {
        return getAttribute("charset");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setCharset(java.lang.String)
     */
    public void setCharset(String charset) {
        setAttribute(
            "charset",
            charset
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#getHref()
     */
    public String getHref() {
        return getAttribute("href");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setHref(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLLinkElement#getHreflang()
     */
    public String getHreflang() {
        return getAttribute("hreflang");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setHreflang(java.lang.String)
     */
    public void setHreflang(String hreflang) {
        setAttribute(
            "hreflang",
            hreflang
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#getMedia()
     */
    public String getMedia() {
        return getAttribute("media");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setMedia(java.lang.String)
     */
    public void setMedia(String media) {
        setAttribute(
            "media",
            media
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#getRel()
     */
    public String getRel() {
        return getAttribute("rel");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setRel(java.lang.String)
     */
    public void setRel(String rel) {
        setAttribute(
            "rel",
            rel
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#getRev()
     */
    public String getRev() {
        return getAttribute("rev");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setRev(java.lang.String)
     */
    public void setRev(String rev) {
        setAttribute(
            "rev",
            rev
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#getTarget()
     */
    public String getTarget() {
        return getAttribute("target");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setTarget(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLLinkElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLLinkElement#setType(java.lang.String)
     */
    public void setType(String type) {
        setAttribute(
            "type",
            type
        );
    }
}