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

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLOptionsCollection;
import org.w3c.dom.html.HTMLSelectElement;
import org.w3c.dom.html.HTMLOptionElement;

import java.util.Arrays;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLSelectElement
    extends CHTMLElement
    implements HTMLSelectElement {
    static final long serialVersionUID = 6373587946709723984L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLSelectElement(ADocument ownerDocument) {
        super("select", ownerDocument);
        validElement = Arrays.asList(new String[] {
                    "optgroup",
                    "option"
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
     * @see org.w3c.dom.html.HTMLSelectElement#getType()
     */
    public String getType() {
        if ("multiple".equalsIgnoreCase(getAttribute("multiple")))
            return "select-multiple";

        return "select-one";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#getSelectedIndex()
     */
    public int getSelectedIndex() {
    	NodeList nl = getElementsByTagName("option");
    	for (int i=0;i<nl.getLength();i++) {
    		HTMLOptionElement elem = (HTMLOptionElement)nl.item(i);
    		if (elem.getSelected()) return i;
    	}
    	return -1;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#setSelectedIndex(int)
     */
    public void setSelectedIndex(int selectedIndex) {
    	NodeList nl = getElementsByTagName("option");
    	for (int i=0;i<nl.getLength();i++) {
    		HTMLOptionElement elem = (HTMLOptionElement)nl.item(i);
    		elem.setSelected(false);
    		if (selectedIndex == i)
        		elem.setSelected(true);
    	}
    }


    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#getValue()
     */
    public String getValue() {
    	NodeList nl = getElementsByTagName("option");
    	for (int i=0;i<nl.getLength();i++) {
    		HTMLOptionElement elem = (HTMLOptionElement)nl.item(i);
    		if (elem.getSelected()) return elem.getValue();
    	}
        return "";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#setValue(java.lang.String)
     */
    public void setValue(String value) {
    	NodeList nl = getElementsByTagName("option");
    	for (int i=0;i<nl.getLength();i++) {
    		HTMLOptionElement elem = (HTMLOptionElement)nl.item(i);
    		elem.setSelected(false);
    		if (value.equals(elem.getValue())) {
        		elem.setSelected(true);
    		}
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#getLength()
     */
    public int getLength() {
    	return getOptions().getLength();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#getForm()
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
     * @see org.w3c.dom.html.HTMLSelectElement#getDisabled()
     */
    public boolean getDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#setDisabled(boolean)
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
     * @see org.w3c.dom.html.HTMLSelectElement#getMultiple()
     */
    public boolean getMultiple() {
        return "multiple".equals(getAttribute("multiple"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#setMultiple(boolean)
     */
    public void setMultiple(boolean multiple) {
        if (multiple) {
            setAttribute(
                "multiple",
                "multiple"
            );
        } else {
            removeAttribute("multiple");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLSelectElement#getSize()
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
     * @see org.w3c.dom.html.HTMLSelectElement#setSize(int)
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
     * @see org.w3c.dom.html.HTMLSelectElement#getTabIndex()
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
     * @see org.w3c.dom.html.HTMLSelectElement#setTabIndex(int)
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
     * @see org.w3c.dom.html.HTMLSelectElement#add(org.w3c.dom.html.HTMLElement,
     *      org.w3c.dom.html.HTMLElement)
     */
    public void add(
        HTMLElement element,
        HTMLElement before
    )
        throws DOMException {
        insertBefore(
            element,
            before
        );
    	if (optionsCol != null) {
    		optionsCol.setNl(createOptionsCol());
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#remove(int)
     */
    public void remove(int index) {
    	Node n = getOptions().item(index);
    	if (n != null) {
    		n.getParentNode().removeChild(n);
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#blur()
     */
    public void blur() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#focus()
     */
    public void focus() {
    }

    private CHTMLCollection optionsCol = null;
    
    private CNodeList createOptionsCol() {
    	CNodeList nl = new CNodeList(true);
    	NodeList list = getElementsByTagName("option");
    	for (int i=0;i<list.getLength();i++) {
    		nl.addItem(list.item(i));
    	}
    	return nl;
    }
    
    public Node appendChild(Node newChild) throws DOMException {
    	Node n = super.appendChild(newChild);
    	if (optionsCol != null) {
    		optionsCol.setNl(createOptionsCol());
    	}
    	return n;
    }
    
    public Node removeChild(Node oldChild) throws DOMException {
    	Node n = super.removeChild(oldChild);
    	if (optionsCol != null) {
    		optionsCol.setNl(createOptionsCol());
    	}
    	return n;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#getOptions()
     */
    public HTMLCollection getOptions() {
    	if (optionsCol != null) return optionsCol;
        return optionsCol = new CHTMLCollection(createOptionsCol());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLSelectElement#setLength(int)
     */
    public void setLength(int length)
        throws DOMException {
    	throw new DOMException(DOMException.NOT_SUPPORTED_ERR,"not supported");
    }
}