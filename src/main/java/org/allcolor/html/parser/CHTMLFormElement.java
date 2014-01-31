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
import org.allcolor.xml.parser.dom.CNodeList;

import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLFormElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLFormElement
    extends CHTMLElement
    implements HTMLFormElement {
    static final long serialVersionUID = -4401059869870548507L;
	/** DOCUMENT ME! */
    private CHTMLCollection elements = null;
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
    public CHTMLFormElement(ADocument ownerDocument) {
        super("form", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLFormElement#getElements()
     */
    public HTMLCollection getElements() {
        if (elements == null) {
            NodeList nlInput = getElementsByTagName("input");
            NodeList nlButton = getElementsByTagName("button");
            NodeList nlTextarea = getElementsByTagName("textarea");
            NodeList nlSelect = getElementsByTagName("select");
            CNodeList cnl = new CNodeList(true);

            for (int i = 0; i < nlInput.getLength(); i++) {
                cnl.addItem(nlInput.item(i));
            }

            for (int i = 0; i < nlButton.getLength(); i++) {
                cnl.addItem(nlButton.item(i));
            }

            for (int i = 0; i < nlTextarea.getLength(); i++) {
                cnl.addItem(nlTextarea.item(i));
            }

            for (int i = 0; i < nlSelect.getLength(); i++) {
                cnl.addItem(nlSelect.item(i));
            }

            elements = new CHTMLCollection(cnl);
        }

        return elements;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#getLength()
     */
    public int getLength() {
        return getElements()
                   .getLength();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLFormElement#getAcceptCharset()
     */
    public String getAcceptCharset() {
        return getAttribute("accept-charset");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#setAcceptCharset(java.lang.String)
     */
    public void setAcceptCharset(String acceptCharset) {
        setAttribute(
            "accept-charset",
            acceptCharset
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#getAction()
     */
    public String getAction() {
        return getAttribute("action");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#setAction(java.lang.String)
     */
    public void setAction(String action) {
        setAttribute(
            "action",
            action
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#getEnctype()
     */
    public String getEnctype() {
        return getAttribute("enctype");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#setEnctype(java.lang.String)
     */
    public void setEnctype(String enctype) {
        setAttribute(
            "enctype",
            enctype
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#getMethod()
     */
    public String getMethod() {
        return getAttribute("method");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#setMethod(java.lang.String)
     */
    public void setMethod(String method) {
        setAttribute(
            "method",
            method
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#getTarget()
     */
    public String getTarget() {
        return getAttribute("target");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#setTarget(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLFormElement#submit()
     */
    public void submit() {
        // do nothing here.
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFormElement#reset()
     */
    public void reset() {
        // TODO Auto-generated method stub
    }
}