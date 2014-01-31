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

import org.allcolor.xml.parser.CStringBuilder;
import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.html2.HTMLScriptElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLScriptElement
    extends CHTMLElement
    implements HTMLScriptElement {
    static final long serialVersionUID = 6809913769761761647L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLScriptElement(ADocument ownerDocument) {
        super("script", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLScriptElement#getText()
     */
    public String getText() {
        CStringBuilder sb = new CStringBuilder();
        NodeList nl = getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            if (node.getNodeType() == Node.TEXT_NODE) {
                sb.append(node.getNodeValue());
            } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
                sb.append(node.getNodeValue());
            }
        }

        return sb.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setText(java.lang.String)
     */
    public void setText(String text) {
        NodeList nl = getChildNodes();
        List toRemove = new ArrayList();

        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            if (node.getNodeType() == Node.TEXT_NODE) {
                toRemove.add(node);
            } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
                toRemove.add(node);
            }
        }

        for (Iterator it = toRemove.iterator(); it.hasNext();) {
            removeChild((Node) it.next());
        }

        Text t = ownerDocument.createTextNode(text);
        appendChild(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#getHtmlFor()
     */
    public String getHtmlFor() {
        return getAttribute("htmlfor");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setHtmlFor(java.lang.String)
     */
    public void setHtmlFor(String htmlFor) {
        setAttribute(
            "htmlfor",
            htmlFor
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#getEvent()
     */
    public String getEvent() {
        return getAttribute("event");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setEvent(java.lang.String)
     */
    public void setEvent(String event) {
        setAttribute(
            "event",
            event
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#getCharset()
     */
    public String getCharset() {
        return getAttribute("charset");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setCharset(java.lang.String)
     */
    public void setCharset(String charset) {
        setAttribute(
            "charset",
            charset
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#getDefer()
     */
    public boolean getDefer() {
        return "defer".equals(getAttribute("defer"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setDefer(boolean)
     */
    public void setDefer(boolean defer) {
        if (defer) {
            setAttribute(
                "defer",
                "defer"
            );
        } else {
            removeAttribute("defer");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#getSrc()
     */
    public String getSrc() {
        return getAttribute("src");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setSrc(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLScriptElement#getType()
     */
    public String getType() {
        return getAttribute("type");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLScriptElement#setType(java.lang.String)
     */
    public void setType(String type) {
        setAttribute(
            "type",
            type
        );
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        if (getType()
                    .equals(""))
            setType("text/javascript");

        return super.toString2();
    }
}