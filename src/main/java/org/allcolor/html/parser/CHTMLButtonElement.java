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

import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLButtonElement;
import org.w3c.dom.html.HTMLFormElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLButtonElement
    extends CHTMLElement
    implements HTMLButtonElement {
    static final long serialVersionUID = 291988936973309345L;
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
                    "table",
                    "br",
                    "span",
                    "bdo",
                    "object",
                    "applet",
                    "img",
                    "map",
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
    public CHTMLButtonElement(ADocument ownerDocument) {
        super("button", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLButtonElement#getForm()
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
     * @see org.w3c.dom.html.HTMLButtonElement#getAccessKey()
     */
    public String getAccessKey() {
        return getAttribute("accesskey");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLButtonElement#setAccessKey(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLButtonElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLButtonElement#setDisabled(boolean)
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
     * @see org.w3c.dom.html.HTMLButtonElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLButtonElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLButtonElement#getTabIndex()
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
     * @see org.w3c.dom.html.HTMLButtonElement#setTabIndex(int)
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
     * @see org.w3c.dom.html.HTMLButtonElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLButtonElement#getValue()
     */
    public String getValue() {
        return getAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLButtonElement#setValue(java.lang.String)
     */
    public void setValue(String value) {
        setAttribute(
            "value",
            value
        );
    }
}