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

import org.allcolor.xml.parser.CDocumentBuilderFactory;
import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLObjectElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLObjectElement
    extends CHTMLElement
    implements HTMLObjectElement {
    static final long serialVersionUID = 1776813936029015675L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLObjectElement(ADocument ownerDocument) {
        super("object", ownerDocument);
        validElement =
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
     * @see org.w3c.dom.html.HTMLObjectElement#getForm()
     */
    public HTMLFormElement getForm() {
        Element elem = this;

        while (elem.getParentNode() != null) {
            elem = (Element) elem.getParentNode();

            if (elem instanceof HTMLFormElement)
                return (HTMLFormElement) elem;
        }

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#getCode()
     */
    public String getCode() {
        return getAttribute("code");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setCode(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setAlign(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getArchive()
     */
    public String getArchive() {
        return getAttribute("archive");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setArchive(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getBorder()
     */
    public String getBorder() {
        return getAttribute("border");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setBorder(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getCodeBase()
     */
    public String getCodeBase() {
        return getAttribute("codebase");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setCodeBase(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getCodeType()
     */
    public String getCodeType() {
        return getAttribute("codetype");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setCodeType(java.lang.String)
     */
    public void setCodeType(String codeType) {
        setAttribute(
            "codetype",
            codeType
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#getData()
     */
    public String getData() {
        return getAttribute("data");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setData(java.lang.String)
     */
    public void setData(String data) {
        setAttribute(
            "data",
            data
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#getDeclare()
     */
    public boolean getDeclare() {
    	return "declare".equals(getAttribute("declare"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setDeclare(boolean)
     */
    public void setDeclare(boolean declare) {
    	if (declare) {
    		setAttribute("declare", "declare");
    	} else {
    		setAttribute("declare", "");
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getStandby()
     */
    public String getStandby() {
        return getAttribute("standby");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setStandby(java.lang.String)
     */
    public void setStandby(String standby) {
        setAttribute(
            "standby",
            standby
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#getTabIndex()
     */
    public int getTabIndex() {
        try {
            return Integer.parseInt(getAttribute("tabindex"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setTabIndex(int)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setType(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getUseMap()
     */
    public String getUseMap() {
        return getAttribute("usemap");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setUseMap(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getHeight()
     */
    public String getHeight() {
        return getAttribute("height");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#getHspace()
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
     * @see org.w3c.dom.html.HTMLObjectElement#getVspace()
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
     * @see org.w3c.dom.html.HTMLObjectElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setHeight(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#setHspace(int)
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
     * @see org.w3c.dom.html.HTMLObjectElement#setHspace(int)
     */
    public void setHspace(String hspace) {
        setAttribute(
            "hspace",
            "" + hspace
        );
    }


    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setVspace(int)
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
     * @see org.w3c.dom.html.HTMLObjectElement#setVspace(int)
     */
    public void setVspace(String vspace) {
        setAttribute(
            "vspace",
            "" + vspace
        );
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLObjectElement#setWidth(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLObjectElement#getContentDocument()
     */
    public Document getContentDocument() {
        try {
            return CDocumentBuilderFactory.newParser()
                                          .newDocumentBuilder()
                                          .parse(getCodeBase());
        } catch (Exception e) {
            try {
            	if (!"".equals(getCodeBase()) && getCodeBase() != null)
	                return CDocumentBuilderFactory.newParser()
	                                              .newDocumentBuilder()
	                                              .newDocument();
            	return null;
            } catch (Exception ie) {
                return null;
            }
        }
    }
}