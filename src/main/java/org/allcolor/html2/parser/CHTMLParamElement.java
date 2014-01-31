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

import org.w3c.dom.html2.HTMLParamElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLParamElement
    extends CHTMLElement
    implements HTMLParamElement {
    static final long serialVersionUID = -682620146545161584L;

	/**
     * DOCUMENT ME!
     *
     * @param name
     * @param ownerDocument
     */
    public CHTMLParamElement(
        ADocument ownerDocument
    ) {
        super("param", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLParamElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLParamElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLParamElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLParamElement#setType(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLParamElement#getValue()
     */
    public String getValue() {
        return getAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLParamElement#setValue(java.lang.String)
     */
    public void setValue(String value) {
        setAttribute(
            "value",
            value
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLParamElement#getValueType()
     */
    public String getValueType() {
        return getAttribute("valuetype");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLParamElement#setValueType(java.lang.String)
     */
    public void setValueType(String valueType) {
        setAttribute(
            "valuetype",
            valueType
        );
    }
}