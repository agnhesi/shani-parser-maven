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
import org.allcolor.xml.parser.dom.ANode;

import org.w3c.dom.Node;
import org.w3c.dom.html2.HTMLCollection;
import org.w3c.dom.html2.HTMLTableCellElement;
import org.w3c.dom.html2.HTMLTableRowElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLThElement
    extends CHTMLElement
    implements HTMLTableCellElement {
    static final long serialVersionUID = 3223495478987197447L;
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
    public CHTMLThElement(ADocument ownerDocument) {
        super("th", ownerDocument);
        validElement = ve;
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
     * @see org.w3c.dom.html.HTMLTableCellElement#getCellIndex()
     */
    public int getCellIndex() {
    	ANode parent = null;
    	parent = parentNode;
    	while (parent != null && parent.name != "tr") {
    		parent = parent.parentNode;
    	}
    	if (parent == null) return -1;
    	HTMLCollection nl = ((HTMLTableRowElement)parent).getCells();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = nl.item(i);
    		if (n == this) return i;
    	}
        return -1;
    }
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getAbbr()
     */
    public String getAbbr() {
        return getAttribute("abbr");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setAbbr(java.lang.String)
     */
    public void setAbbr(String abbr) {
    	setAttribute("abbr", abbr);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setAlign(java.lang.String)
     */
    public void setAlign(String align) {
    	setAttribute("align", align);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getAxis()
     */
    public String getAxis() {
        return getAttribute("axis");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setAxis(java.lang.String)
     */
    public void setAxis(String axis) {
    	setAttribute("axis", axis);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getBgColor()
     */
    public String getBgColor() {
        return getAttribute("bgcolor");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setBgColor(java.lang.String)
     */
    public void setBgColor(String bgColor) {
    	setAttribute("bgcolor", bgColor);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getCh()
     */
    public String getCh() {
        return getAttribute("char");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setCh(java.lang.String)
     */
    public void setCh(String ch) {
    	setAttribute("char", ch);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getChOff()
     */
    public String getChOff() {
        return getAttribute("charoff");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setChOff(java.lang.String)
     */
    public void setChOff(String chOff) {
    	setAttribute("charoff", chOff);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getColSpan()
     */
    public int getColSpan() {
    	try {
    		return Integer.parseInt(getAttribute("colspan"));
    	}
    	catch (Exception e) {
    		return 0;
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setColSpan(int)
     */
    public void setColSpan(int colSpan) {
    	setAttribute("colspan", ""+colSpan);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getHeaders()
     */
    public String getHeaders() {
        return getAttribute("headers");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setHeaders(java.lang.String)
     */
    public void setHeaders(String headers) {
    	setAttribute("headers", headers);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getHeight()
     */
    public String getHeight() {
        return getAttribute("height");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setHeight(java.lang.String)
     */
    public void setHeight(String height) {
    	setAttribute("height", height);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getNoWrap()
     */
    public boolean getNoWrap() {
        return "nowrap".equals(getAttribute("nowrap"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setNoWrap(boolean)
     */
    public void setNoWrap(boolean noWrap) {
    	if (noWrap) {
    		setAttribute("nowrap", "nowrap");
    	} else {
    		removeAttribute("nowrap");
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getRowSpan()
     */
    public int getRowSpan() {
    	try {
    		return Integer.parseInt(getAttribute("rowspan"));
    	}
    	catch (Exception e) {
    		return 0;
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setRowSpan(int)
     */
    public void setRowSpan(int rowSpan) {
    	setAttribute("rowspan", ""+rowSpan);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getScope()
     */
    public String getScope() {
        return getAttribute("scope");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setScope(java.lang.String)
     */
    public void setScope(String scope) {
    	setAttribute("scope", scope);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getVAlign()
     */
    public String getVAlign() {
        return getAttribute("valign");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setVAlign(java.lang.String)
     */
    public void setVAlign(String vAlign) {
    	setAttribute("valign", vAlign);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableCellElement#setWidth(java.lang.String)
     */
    public void setWidth(String width) {
    	setAttribute("width", width);
    }
}