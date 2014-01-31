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
import org.allcolor.xml.parser.dom.ANode;

import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLTextAreaElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLTextareaElement
    extends CHTMLElement
    implements HTMLTextAreaElement {
    static final long serialVersionUID = 4569834085854053181L;
    private static final List ve = 
    	Arrays.asList(new String[] {
                "#PCDATA"
            }
        );
	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLTextareaElement(ADocument ownerDocument) {
        super("textarea", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLTextAreaElement#getDefaultValue()
     */
    public String getDefaultValue() {
        return getValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setDefaultValue(java.lang.String)
     */
    public void setDefaultValue(String defaultValue) {
    	setValue(defaultValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getForm()
     */
    public HTMLFormElement getForm() {
    	ANode parent = parentNode;
    	while (parent != null && !(parent instanceof HTMLFormElement)) {
    		parent = parent.parentNode;
    	}
        return parent instanceof HTMLFormElement ? (HTMLFormElement)parent : null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getAccessKey()
     */
    public String getAccessKey() {
        return getAttribute("accesskey");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setAccessKey(java.lang.String)
     */
    public void setAccessKey(String accessKey) {
    	setAttribute("accesskey", accessKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getCols()
     */
    public int getCols() {
    	try {
    		return Integer.parseInt(getAttribute("cols"));
    	}
    	catch (Exception e) {
    		return 0;
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setCols(int)
     */
    public void setCols(int cols) {
    	setAttribute("cols", ""+cols);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setDisabled(boolean)
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
     * @see org.w3c.dom.html.HTMLTextAreaElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setName(java.lang.String)
     */
    public void setName(String name) {
    	setAttribute("name", name);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getReadOnly()
     */
    public boolean getReadOnly() {
        return "readonly".equals(getAttribute("readonly"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setReadOnly(boolean)
     */
    public void setReadOnly(boolean readOnly) {
        if (readOnly) {
            setAttribute(
                "readonly",
                "readonly"
            );
        } else {
            removeAttribute("readonly");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getRows()
     */
    public int getRows() {
    	try {
    		return Integer.parseInt(getAttribute("rows"));
    	}
    	catch (Exception e) {
    		return 0;
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setRows(int)
     */
    public void setRows(int rows) {
    	setAttribute("rows", ""+rows);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getTabIndex()
     */
    public int getTabIndex() {
    	try {
    		return Integer.parseInt(getAttribute("tabindex"));
    	}
    	catch (Exception e) {
    		return 0;
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setTabIndex(int)
     */
    public void setTabIndex(int tabIndex) {
    	setAttribute("tabindex", ""+tabIndex);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getType()
     */
    public String getType() {
        return "textarea";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#getValue()
     */
    public String getValue() {
        return getTextContent();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#setValue(java.lang.String)
     */
    public void setValue(String value) {
    	setTextContent(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#blur()
     */
    public void blur() {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#focus()
     */
    public void focus() {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTextAreaElement#select()
     */
    public void select() {
        // TODO Auto-generated method stub
    }
}