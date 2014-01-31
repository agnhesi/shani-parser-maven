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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.html2.HTMLFormElement;
import org.w3c.dom.html2.HTMLOptionElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLOptionElement
    extends CHTMLElement
    implements HTMLOptionElement {
    static final long serialVersionUID = 7477354510163393808L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLOptionElement(ADocument ownerDocument) {
        super("option", ownerDocument);
        validElement = Arrays.asList(new String[] {
                    "#PCDATA"
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
     * @see org.w3c.dom.html.HTMLOptionElement#getForm()
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
     * @see org.w3c.dom.html.HTMLOptionElement#getDefaultSelected()
     */
    public boolean getDefaultSelected() {
    	return "selected".equals(getAttribute("selected"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#setDefaultSelected(boolean)
     */
    public void setDefaultSelected(boolean defaultSelected) {
    	setSelected(defaultSelected);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#getText()
     */
    public String getText() {
        return getTextContent().trim();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#getIndex()
     */
    public int getIndex() {
    	NodeList nl = parentNode.getElementsByTagName("option");
    	for (int i=0;i<nl.getLength();i++) {
    		if (nl.item(i) == this) return i;
    	}
    	return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#setDisabled(boolean)
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
     * @see org.w3c.dom.html.HTMLOptionElement#getLabel()
     */
    public String getLabel() {
        return getAttribute("label");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#setLabel(java.lang.String)
     */
    public void setLabel(String label) {
        setAttribute(
            "label",
            label
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#getSelected()
     */
    public boolean getSelected() {
        return "selected".equals(getAttribute("selected"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#setSelected(boolean)
     */
    public void setSelected(boolean selected) {
        if (selected) {
            setAttribute(
                "selected",
                "selected"
            );
        } else {
            removeAttribute("selected");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#getValue()
     */
    public String getValue() {
        return getAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLOptionElement#setValue(java.lang.String)
     */
    public void setValue(String value) {
        setAttribute(
            "value",
            value
        );
    }
}