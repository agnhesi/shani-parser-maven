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

import org.allcolor.xml.parser.CDocumentBuilderFactory;
import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.Document;
import org.w3c.dom.html2.HTMLIFrameElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLIframeElement
    extends CHTMLElement
    implements HTMLIFrameElement {
    static final long serialVersionUID = 6265305651142910939L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLIframeElement(ADocument ownerDocument) {
        super("iframe", ownerDocument);
        validElement =
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
     * @see org.w3c.dom.html.HTMLIFrameElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setAlign(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLIFrameElement#getFrameBorder()
     */
    public String getFrameBorder() {
        return getAttribute("frameborder");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setFrameBorder(java.lang.String)
     */
    public void setFrameBorder(String frameBorder) {
        setAttribute(
            "frameborder",
            frameBorder
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#getHeight()
     */
    public String getHeight() {
        return getAttribute("height");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setHeight(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLIFrameElement#getLongDesc()
     */
    public String getLongDesc() {
        return getAttribute("longdesc");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setLongDesc(java.lang.String)
     */
    public void setLongDesc(String longDesc) {
        setAttribute(
            "longdesc",
            longDesc
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#getMarginHeight()
     */
    public String getMarginHeight() {
        return getAttribute("marginheight");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setMarginHeight(java.lang.String)
     */
    public void setMarginHeight(String marginHeight) {
        setAttribute(
            "marginheight",
            marginHeight
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#getMarginWidth()
     */
    public String getMarginWidth() {
        return getAttribute("marginwidth");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setMarginWidth(java.lang.String)
     */
    public void setMarginWidth(String marginWidth) {
        setAttribute(
            "marginwidth",
            marginWidth
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLIFrameElement#getScrolling()
     */
    public String getScrolling() {
        return getAttribute("scrolling");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setScrolling(java.lang.String)
     */
    public void setScrolling(String scrolling) {
        setAttribute(
            "scrolling",
            scrolling
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#getSrc()
     */
    public String getSrc() {
        return getAttribute("src");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setSrc(java.lang.String)
     */
    public void setSrc(String src) {
        setAttribute(
            "src",
            src
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLIFrameElement#setWidth(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLIFrameElement#getContentDocument()
     */
    public Document getContentDocument() {
        try {
            return CDocumentBuilderFactory.newParser()
                                          .newDocumentBuilder()
                                          .parse(getSrc());
        } catch (Exception e) {
            try {
                return CDocumentBuilderFactory.newParser()
                                              .newDocumentBuilder()
                                              .newDocument();
            } catch (Exception ie) {
                return null;
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#toString()
     */
    public String toString() {
        if (getAttribute("src")
                    .equals(""))
            return "";

        return super.toString();
    }
}