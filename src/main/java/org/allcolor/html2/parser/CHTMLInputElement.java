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
import org.w3c.dom.html2.HTMLFormElement;
import org.w3c.dom.html2.HTMLInputElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLInputElement
    extends CHTMLElement
    implements HTMLInputElement {
    static final long serialVersionUID = 4564704289819326789L;

	/** DOCUMENT ME! */
    public static final int NSTATE = 0;

    /** DOCUMENT ME! */
    public static final int BSTATE = 1;

    /** DOCUMENT ME! */
    public static final int FSTATE = 2;

    /** DOCUMENT ME! */
    public static final int SSTATE = 3;

    /** DOCUMENT ME! */
    public static final int CSTATE = 4;

    /** DOCUMENT ME! */
    private int state = NSTATE;

    /**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLInputElement(ADocument ownerDocument) {
        super("input", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLInputElement#getDefaultValue()
     */
    public String getDefaultValue() {
        return getAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setDefaultValue(java.lang.String)
     */
    public void setDefaultValue(String defaultValue) {
        setAttribute(
            "value",
            defaultValue
        );
    }

    public boolean getDefaultChecked() {
        return "checked".equals(getAttribute("checked"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setDefaultChecked(boolean)
     */
    public void setDefaultChecked(boolean defaultChecked) {
    	if (defaultChecked) {
    		setAttribute("checked", "checked");
    	} else {
    		setAttribute("checked", "");
    	}
    }


    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#getForm()
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
     * @see org.w3c.dom.html.HTMLInputElement#getAccept()
     */
    public String getAccept() {
        return getAttribute("accept");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setAccept(java.lang.String)
     */
    public void setAccept(String accept) {
        setAttribute(
            "accept",
            accept
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#getAccessKey()
     */
    public String getAccessKey() {
        return getAttribute("accesskey");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setAccessKey(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setAlign(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getAlt()
     */
    public String getAlt() {
        return getAttribute("alt");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setAlt(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getChecked()
     */
    public boolean getChecked() {
        return "checked".equals(getAttribute("checked"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setChecked(boolean)
     */
    public void setChecked(boolean checked) {
        if (checked) {
            setAttribute(
                "checked",
                "checked"
            );
        } else {
            removeAttribute("checked");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setDisabled(boolean)
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
        * @see org.w3c.dom.html.HTMLInputElement#getMaxLength()
        */
    public int getMaxLength() {
        try {
            return Integer.parseInt(getAttribute("maxlength"));
        } catch (Exception e) {
            return -1;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setMaxLength(int)
     */
    public void setMaxLength(int maxLength) {
        setAttribute(
            "maxlength",
            "" + maxLength
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getReadOnly()
     */
    public boolean getReadOnly() {
        return "readonly".equals(getAttribute("readonly"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setReadOnly(boolean)
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
     * @see org.w3c.dom.html.HTMLInputElement#getSize()
     */
    public int getSize() {
        try {
            return Integer.parseInt(getAttribute("size"));
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setSize(int)
     */
    public void setSize(int size) {
        setAttribute(
            "size",
            "" + size
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setType(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getSrc()
     */
    public String getSrc() {
        return getAttribute("src");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setSrc(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getTabIndex()
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
     * @see org.w3c.dom.html.HTMLInputElement#setTabIndex(int)
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
     * @see org.w3c.dom.html.HTMLInputElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#getUseMap()
     */
    public String getUseMap() {
        return getAttribute("usemap");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setUseMap(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#getValue()
     */
    public String getValue() {
        return getAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#setValue(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLInputElement#blur()
     */
    public void blur() {
        state = (state == BSTATE)
            ? NSTATE
            : BSTATE;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#focus()
     */
    public void focus() {
        state = (state == FSTATE)
            ? NSTATE
            : FSTATE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getState() {
        return state;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#select()
     */
    public void select() {
        state = (state == SSTATE)
            ? NSTATE
            : SSTATE;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLInputElement#click()
     */
    public void click() {
        state = (state == CSTATE)
            ? NSTATE
            : CSTATE;
        setChecked(!getChecked());
    }
}