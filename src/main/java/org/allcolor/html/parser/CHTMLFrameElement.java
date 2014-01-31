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
import org.w3c.dom.html.HTMLFrameElement;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLFrameElement
    extends CHTMLElement
    implements HTMLFrameElement {
    static final long serialVersionUID = -5319778418511089736L;

	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLFrameElement(ADocument ownerDocument) {
        super("frame", ownerDocument);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return "frameset";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getFrameBorder()
     */
    public String getFrameBorder() {
        return getAttribute("frameborder");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setFrameBorder(java.lang.String)
     */
    public void setFrameBorder(String frameBorder) {
        setAttribute(
            "frameborder",
            frameBorder
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getLongDesc()
     */
    public String getLongDesc() {
        return getAttribute("longdesc");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setLongDesc(java.lang.String)
     */
    public void setLongDesc(String longDesc) {
        setAttribute(
            "longdesc",
            longDesc
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getMarginHeight()
     */
    public String getMarginHeight() {
        return getAttribute("marginheight");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setMarginHeight(java.lang.String)
     */
    public void setMarginHeight(String marginHeight) {
        setAttribute(
            "marginheight",
            marginHeight
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getMarginWidth()
     */
    public String getMarginWidth() {
        return getAttribute("marginwidth");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setMarginWidth(java.lang.String)
     */
    public void setMarginWidth(String marginWidth) {
        setAttribute(
            "marginwidth",
            marginWidth
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getName()
     */
    public String getName() {
        return getAttribute("name");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setName(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLFrameElement#getNoResize()
     */
    public boolean getNoResize() {
        return "noresize".equals(getAttribute("noresize"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setNoResize(boolean)
     */
    public void setNoResize(boolean noResize) {
        if (noResize) {
            setAttribute(
                "noresize",
                "noresize"
            );
        } else {
            removeAttribute("noresize");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getScrolling()
     */
    public String getScrolling() {
        return getAttribute("scrolling");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setScrolling(java.lang.String)
     */
    public void setScrolling(String scrolling) {
        setAttribute(
            "scrolling",
            scrolling
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#getSrc()
     */
    public String getSrc() {
        return getAttribute("src");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLFrameElement#setSrc(java.lang.String)
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
     * @see org.w3c.dom.html.HTMLFrameElement#getContentDocument()
     */
    public Document getContentDocument() {
        try {
        	if (getSrc().indexOf(":") == -1) {
        		if (ownerDocument != null) {
        			String uri = ownerDocument.getDocumentURI();
        			int index = uri.lastIndexOf('/');
        			if (index != -1) {
        				uri = uri.substring(0,index+1);
        			}
        			uri = uri+getSrc();
        			return CDocumentBuilderFactory.newParser()
                    .newDocumentBuilder()
                    .parse(uri);
        		}
        	}
            return CDocumentBuilderFactory.newParser()
                                          .newDocumentBuilder()
                                          .parse(getSrc());
        } catch (Exception e) {
            try {
                return CDocumentBuilderFactory.newParser()
                                              .newDocumentBuilder()
                                              .newDocument();
            } catch (Exception ie) {
                return null;
            }
        }
    }
}