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

import org.w3c.dom.html2.HTMLTableColElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLColElement
    extends CHTMLElement
    implements HTMLTableColElement {
    static final long serialVersionUID = 9057935069972979865L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLColElement(ADocument ownerDocument) {
        super("col", ownerDocument);
    }

    public CHTMLColElement(String type,ADocument ownerDocument) {
        super(type, ownerDocument);
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
     * @see org.w3c.dom.html.HTMLTableColElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#setAlign(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLTableColElement#getCh()
     */
    public String getCh() {
        return getAttribute("char");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#setCh(java.lang.String)
     */
    public void setCh(String ch) {
        setAttribute(
            "char",
            ch
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#getChOff()
     */
    public String getChOff() {
        return getAttribute("charoff");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#setChOff(java.lang.String)
     */
    public void setChOff(String chOff) {
        setAttribute(
            "charoff",
            chOff
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#getSpan()
     */
    public int getSpan() {
        try {
            return Integer.parseInt(getAttribute("span"));
        } catch (Exception ignore) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#setSpan(int)
     */
    public void setSpan(int span) {
        setAttribute(
            "span",
            "" + span
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#getVAlign()
     */
    public String getVAlign() {
        return getAttribute("valign");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#setVAlign(java.lang.String)
     */
    public void setVAlign(String vAlign) {
        setAttribute(
            "valign",
            vAlign
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableColElement#setWidth(java.lang.String)
     */
    public void setWidth(String width) {
        setAttribute(
            "width",
            width
        );
    }
}