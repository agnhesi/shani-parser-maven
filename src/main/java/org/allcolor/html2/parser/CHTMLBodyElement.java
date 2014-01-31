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

import org.w3c.dom.html2.HTMLBodyElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLBodyElement
    extends CHTMLElement
    implements HTMLBodyElement {
    static final long serialVersionUID = -4577004079907568249L;
    private static final List ve = 
        Arrays.asList(
                new String[] {
                    "#PCDATA",
                    "p",
                    "h1",
                    "h2",
                    "h3",
                    "h4",
                    "h5",
                    "h6",
                    "div",
                    "ul",
                    "ol",
                    "dl",
                    "menu",
                    "dir",
                    "pre",
                    "hr",
                    "blockquote",
                    "address",
                    "center",
                    "noframes",
                    "isindex",
                    "fieldset",
                    "table",
                    "form",
                    "a",
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
                    "noscript",
                    "ins",
                    "del",
                    "script"
                }
            );

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLBodyElement(ADocument ownerDocument) {
        super("body", ownerDocument);
        validElement = ve;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#getALink()
     */
    public String getALink() {
        return getAttribute("alink");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#setALink(java.lang.String)
     */
    public void setALink(String aLink) {
        setAttribute(
            "alink",
            aLink
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#getBackground()
     */
    public String getBackground() {
        return getAttribute("background");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#setBackground(java.lang.String)
     */
    public void setBackground(String background) {
        setAttribute(
            "background",
            background
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#getBgColor()
     */
    public String getBgColor() {
        return getAttribute("bgcolor");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#setBgColor(java.lang.String)
     */
    public void setBgColor(String bgColor) {
        setAttribute(
            "bgcolor",
            bgColor
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#getLink()
     */
    public String getLink() {
        return getAttribute("link");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#setLink(java.lang.String)
     */
    public void setLink(String link) {
        setAttribute(
            "link",
            link
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#getText()
     */
    public String getText() {
        return getAttribute("text");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#setText(java.lang.String)
     */
    public void setText(String text) {
        setAttribute(
            "text",
            text
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#getVLink()
     */
    public String getVLink() {
        return getAttribute("vlink");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLBodyElement#setVLink(java.lang.String)
     */
    public void setVLink(String vLink) {
        setAttribute(
            "vlink",
            vLink
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "html";
    }
}