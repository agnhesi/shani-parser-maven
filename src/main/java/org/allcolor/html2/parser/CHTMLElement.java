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
import org.allcolor.xml.parser.IHtmlValidChild;
import org.allcolor.xml.parser.dom.CAttr;
import org.allcolor.xml.parser.dom.ADocument;
import org.allcolor.xml.parser.dom.CElement;
import org.allcolor.xml.parser.dom.INode;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html2.HTMLElement;

import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public abstract class CHTMLElement
    extends CElement
    implements HTMLElement,IHtmlValidChild {
    static final long serialVersionUID = -6143760334640392223L;


    /** DOCUMENT ME! */
    public List validElement = null;

    public boolean canHaveChild() {
    	return validElement != null ? validElement.size() > 0 : false;
    }
    
    public boolean isValidChild(String name) {
    	return validElement == null ? false : validElement.contains(name);
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param name
     * @param ownerDocument
     */
    public CHTMLElement(
        String name,
        ADocument ownerDocument
    ) {
        super(name, ownerDocument);
    }
    
    public void setNameOverride(String name) {
    	if (this.name.equals(name)) return;
		this.name = name.intern();

		int iIndex = name.indexOf(':',1);

		if (iIndex != -1) {
			prefix		  = name.substring(0, iIndex).intern();
			localName     = name.substring(iIndex + 1).intern();
		} // end if
		else {
			prefix		  = null;
			localName     = this.name;
		} // end else
		if (getNodeType() != ELEMENT_NODE && getNodeType() != ATTRIBUTE_NODE 
			&& getNodeType() != ENTITY_NODE && getNodeType() != NOTATION_NODE) {
			prefix = null;
			localName = null;
			nameSpace = "  ";
			isDom1 = true;
		}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#getId()
     */
    public String getId() {
        return getAttribute("id");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#setId(java.lang.String)
     */
    public void setId(String id) {
        setAttribute(
            "id",
            id
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#getTitle()
     */
    public String getTitle() {
        return getAttribute("title");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#setTitle(java.lang.String)
     */
    public void setTitle(String title) {
        setAttribute(
            "title",
            title
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#getLang()
     */
    public String getLang() {
        return getAttribute("lang");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#setLang(java.lang.String)
     */
    public void setLang(String lang) {
        setAttribute(
            "lang",
            lang
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#getDir()
     */
    public String getDir() {
        return getAttribute("dir");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#setDir(java.lang.String)
     */
    public void setDir(String dir) {
        setAttribute(
            "dir",
            dir
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#getClassName()
     */
    public String getClassName() {
        return getAttribute("class");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLElement#setClassName(java.lang.String)
     */
    public void setClassName(String className) {
        setAttribute(
            "class",
            className
        );
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public abstract String getDefaultParentType();

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.xml.parser.dom.CElement#toString()
     */
    public String toString() {
        String id = getAttribute("id");

        if (!"".equals(id.trim())) {
            String startVal = id.substring(
                    0,
                    1
                );

            try {
                Integer.parseInt(startVal);
                setAttribute(
                    "id",
                    "_" + id
                );
            } catch (Exception ignore) {
            }
        }

        return toString2(false);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.xml.parser.dom.CElement#toString()
     */
    public String toString2() {
        return toString2(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param close DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString2(boolean close) {
        CStringBuilder result = new CStringBuilder();
        result.append("<");
        result.append(name.toLowerCase());
        if (listAttributes != null) {
			if (listAttributes.getLength() > 0) {
				result.append(" ");
			} // end if
	
			for (int i=0;i<listAttributes.getLength();i++) {
				CAttr attr = (CAttr) listAttributes.item(i);
				result.append(attr.toString());
	
				if (i < listAttributes.getLength()-1) {
					result.append(" ");
				} // end if
			} // end while
        }
        
         if (hasChildNodes()) {
            result.append(">");

            NodeList nl = getChildNodes();

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);

                if (node instanceof INode)
                    result.append(node.toString());
            }

            result.append("</");
            result.append(name.toLowerCase());
            result.append(">");
        } else {
            if (close) {
                result.append("></");
                result.append(name.toLowerCase());
                result.append(">");
            } else {
                result.append("/>");
            }
        }

        return result.toString();
    }
}