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

import org.w3c.dom.html.HTMLAppletElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLAppletElement
    extends CHTMLElement
    implements HTMLAppletElement {
    static final long serialVersionUID = 1961774701139273993L;
    private static final List ve = 
        Arrays.asList(
                new String[] {
                    "#PCDATA",
                    "param",
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
    public CHTMLAppletElement(ADocument ownerDocument) {
        super("applet", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLAppletElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setAlign(java.lang.String)
     */
    public void setAlign(String align) {
        setAttribute(
            "align",
            align
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getAlt()
     */
    public String getAlt() {
        return getAttribute("alt");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setAlt(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAppletElement#getArchive()
     */
    public String getArchive() {
        return getAttribute("archive");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setArchive(java.lang.String)
     */
    public void setArchive(String archive) {
        setAttribute(
            "archive",
            archive
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getCode()
     */
    public String getCode() {
        return getAttribute("code");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setCode(java.lang.String)
     */
    public void setCode(String code) {
        setAttribute(
            "code",
            code
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getCodeBase()
     */
    public String getCodeBase() {
        return getAttribute("codebase");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setCodeBase(java.lang.String)
     */
    public void setCodeBase(String codeBase) {
        setAttribute(
            "codebase",
            codeBase
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getHeight()
     */
    public String getHeight() {
        return getAttribute("height");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setHeight(java.lang.String)
     */
    public void setHeight(String height) {
        setAttribute(
            "height",
            height
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setHspace(java.lang.String)
     */
    public void setHspace(String hspace) {
        setAttribute(
            "hspace",
            hspace
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLAppletElement#getObject()
     */
    public String getObject() {
        return getAttribute("object");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setObject(java.lang.String)
     */
    public void setObject(String object) {
        setAttribute(
            "object",
            object
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setVspace(java.lang.String)
     */
    public void setVspace(String vspace) {
        setAttribute(
            "vspace",
            vspace
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setWidth(java.lang.String)
     */
    public void setWidth(String width) {
        setAttribute(
            "width",
            width
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getHspace()
     */
    public String getHspace() {
        try {
            return getAttribute("hspace");
        } catch (Exception e) {
            return ""+0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#getVspace()
     */
    public String getVspace() {
        try {
            return getAttribute("vspace");
        } catch (Exception e) {
            return ""+0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setHspace(int)
     */
    public void setHspace(int hspace) {
        setAttribute(
            "hspace",
            "" + hspace
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLAppletElement#setVspace(int)
     */
    public void setVspace(int vspace) {
        setAttribute(
            "vspace",
            "" + vspace
        );
    }
}