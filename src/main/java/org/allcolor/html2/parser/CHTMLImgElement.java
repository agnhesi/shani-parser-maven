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

import org.w3c.dom.html2.HTMLImageElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLImgElement
    extends CHTMLElement
    implements HTMLImageElement {
    static final long serialVersionUID = 3081982711083982103L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLImgElement(ADocument ownerDocument) {
        super("img", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLImageElement#getLowSrc()
     */
    public String getLowSrc() {
        return getAttribute("lowsrc");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setLowSrc(java.lang.String)
     */
    public void setLowSrc(String lowSrc) {
        setAttribute(
            "lowsrc",
            lowSrc
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setAlign(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#getAlt()
     */
    public String getAlt() {
        return getAttribute("alt");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setAlt(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#getBorder()
     */
    public String getBorder() {
        return getAttribute("border");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setBorder(java.lang.String)
     */
    public void setBorder(String border) {
        setAttribute(
            "border",
            border
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setHeight(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#setHspace(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#getIsMap()
     */
    public boolean getIsMap() {
        try {
            return Boolean.valueOf(getAttribute("ismap"))
                          .booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setIsMap(boolean)
     */
    public void setIsMap(boolean isMap) {
        setAttribute(
            "ismap",
            "" + isMap
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#getLongDesc()
     */
    public String getLongDesc() {
        return getAttribute("longdesc");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setLongDesc(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#getSrc()
     */
    public String getSrc() {
        return getAttribute("src");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setSrc(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLImageElement#getUseMap()
     */
    public String getUseMap() {
        return getAttribute("usemap");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setUseMap(java.lang.String)
     */
    public void setUseMap(String useMap) {
        setAttribute(
            "usemap",
            useMap
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#getHeight()
     */
    public int getHeight() {
        try {
            return Integer.parseInt(getAttribute("height"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#getHspace()
     */
    public int getHspace() {
        try {
            return Integer.parseInt(getAttribute("hspace"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#getVspace()
     */
    public int getVspace() {
        try {
            return Integer.parseInt(getAttribute("vspace"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#getWidth()
     */
    public int getWidth() {
        try {
            return Integer.parseInt(getAttribute("width"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setHeight(int)
     */
    public void setHeight(int height) {
        setAttribute(
            "height",
            "" + height
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setHspace(int)
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
     * @see org.w3c.dom.html.HTMLImageElement#setVspace(int)
     */
    public void setVspace(int vspace) {
        setAttribute(
            "vspace",
            "" + vspace
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLImageElement#setWidth(int)
     */
    public void setWidth(int width) {
        setAttribute(
            "width",
            "" + width
        );
    }
}