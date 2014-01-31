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

import org.w3c.dom.html.HTMLAnchorElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLAElement
    extends CHTMLElement
    implements HTMLAnchorElement {
    static final long serialVersionUID = 1153708309041359390L;
    private static final List ve = 
        Arrays.asList(
                new String[] {
                    "#PCDATA",
                    "br",
                    "span",
                    "bdo",
                    "object",
                    "applet",
                    "img",
                    "map",
                    "iframe",
                    "tt",
                    "i",
                    "b",
                    "u",
                    "s",
                    "strike",
                    "big",
                    "small",
                    "font",
                    "basefont",
                    "em",
                    "strong",
                    "dfn",
                    "code",
                    "q",
                    "samp",
                    "kbd",
                    "var",
                    "cite",
                    "abbr",
                    "acronym",
                    "sub",
                    "sup",
                    "input",
                    "select",
                    "textarea",
                    "label",
                    "button",
                    "ins",
                    "del",
                    "script"
                }
            );

	/** DOCUMENT ME! */
    public static final int NSTATE = 0;

    /** DOCUMENT ME! */
    public static final int BSTATE = 1;

    /** DOCUMENT ME! */
    public static final int FSTATE = 2;

    /** DOCUMENT ME! */
    private int state = NSTATE;

    /**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLAElement(ADocument ownerDocument) {
        super("a", ownerDocument);
        validElement = ve;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "body";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#getAccessKey()
     */
    public String getAccessKey() {
        return getAttribute("accesskey");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setAccessKey(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getCharset()
     */
    public String getCharset() {
        return getAttribute("charset");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setCharset(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getCoords()
     */
    public String getCoords() {
        return getAttribute("coords");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setCoords(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getHref()
     */
    public String getHref() {
        return getAttribute("href");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setHref(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getHreflang()
     */
    public String getHreflang() {
        return getAttribute("hreflang");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setHreflang(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getRel()
     */
    public String getRel() {
        return getAttribute("rel");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setRel(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getRev()
     */
    public String getRev() {
        return getAttribute("rev");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setRev(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getShape()
     */
    public String getShape() {
        return getAttribute("shape");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setShape(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getTabIndex()
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
     * @see org.w3c.dom.html.HTMLAnchorElement#setTabIndex(int)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getTarget()
     */
    public String getTarget() {
        return getAttribute("target");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setTarget(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAnchorElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#setType(java.lang.String)
     */
    public void setType(String type) {
        setAttribute(
            "type",
            type
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#blur()
     */
    public void blur() {
        state = (state == BSTATE)
            ? NSTATE
            : BSTATE;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAnchorElement#focus()
     */
    public void focus() {
        state = (state == FSTATE)
            ? NSTATE
            : FSTATE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getState() {
        return state;
    }
}