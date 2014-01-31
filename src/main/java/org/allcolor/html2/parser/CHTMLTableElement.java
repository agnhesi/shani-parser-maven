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
import org.allcolor.xml.parser.dom.CNodeList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html2.HTMLCollection;
import org.w3c.dom.html2.HTMLElement;
import org.w3c.dom.html2.HTMLTableCaptionElement;
import org.w3c.dom.html2.HTMLTableElement;
import org.w3c.dom.html2.HTMLTableSectionElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLTableElement
    extends CHTMLElement
    implements HTMLTableElement,
    HTMLTableSectionElement {
    static final long serialVersionUID = -4462127924593639313L;
    private static final List ve = 
        Arrays.asList(
                new String[] {
                    "caption",
                    "col",
                    "colgroup",
                    "thead",
                    "tfoot",
                    "tbody",
                    "tr"
                }
            );
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#getAlign()
     */
    public String getAlign() {
        return getAttribute("align");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#setAlign(java.lang.String)
     */
    public void setAlign(String align) {
    	setAttribute("align",align);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#getCh()
     */
    public String getCh() {
        return getAttribute("char");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#setCh(java.lang.String)
     */
    public void setCh(String ch) {
    	setAttribute("char",ch);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#getChOff()
     */
    public String getChOff() {
        return getAttribute("charoff");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#setChOff(java.lang.String)
     */
    public void setChOff(String chOff) {
    	setAttribute("charoff",chOff);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#getVAlign()
     */
    public String getVAlign() {
        return getAttribute("valign");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableSectionElement#setVAlign(java.lang.String)
     */
    public void setVAlign(String vAlign) {
    	setAttribute("valign",vAlign);
    }
	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLTableElement(ADocument ownerDocument) {
        super("table", ownerDocument);
        validElement = ve;
    }

    public CHTMLTableElement(String type,ADocument ownerDocument) {
        super(type, ownerDocument);
    }
    /*
     * (non-Javadoc)
     *
     * @see org.allcolor.html.parser.CHTMLElement#getDefaultParentType()
     */
    public String getDefaultParentType() {
        return null;
    }

    private HTMLTableCaptionElement _getCaption(Node node) {
    	if (node instanceof HTMLTableCaptionElement)
    		return (HTMLTableCaptionElement)node;
    	NodeList nl = node.getChildNodes();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = _getCaption(nl.item(i));
    		if (n != null)
    			return (HTMLTableCaptionElement)n;
    	}
    	return null;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getCaption()
     */
    public HTMLTableCaptionElement getCaption() {
    	return _getCaption(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setCaption(org.w3c.dom.html.HTMLTableCaptionElement)
     */
    public void setCaption(HTMLTableCaptionElement caption) {
    	appendChild(caption);
    }

    private HTMLTableSectionElement _getTHead(Node node) {
    	if (node instanceof HTMLTableSectionElement && "thead".equals(node.getNodeName()))
    		return (HTMLTableSectionElement)node;
    	NodeList nl = node.getChildNodes();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = _getTHead(nl.item(i));
    		if (n != null)
    			return (HTMLTableSectionElement)n;
    	}
    	return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getTHead()
     */
    public HTMLTableSectionElement getTHead() {
        return _getTHead(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setTHead(org.w3c.dom.html.HTMLTableSectionElement)
     */
    public void setTHead(HTMLTableSectionElement tHead) {
    	appendChild(tHead);
    }

    private HTMLTableSectionElement _getTFoot(Node node) {
    	if (node instanceof HTMLTableSectionElement && "tfoot".equals(node.getNodeName()))
    		return (HTMLTableSectionElement)node;
    	NodeList nl = node.getChildNodes();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = _getTFoot(nl.item(i));
    		if (n != null)
    			return (HTMLTableSectionElement)n;
    	}
    	return null;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getTFoot()
     */
    public HTMLTableSectionElement getTFoot() {
        return _getTFoot(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setTFoot(org.w3c.dom.html.HTMLTableSectionElement)
     */
    public void setTFoot(HTMLTableSectionElement tFoot) {
    	appendChild(tFoot);
    }
    
    private CHTMLCollection rows = null;
    
    private NodeList createRowsList() {
    	CNodeList nl = new CNodeList(true);
    	NodeList children = getChildNodes();
    	for (int i=0;i<children.getLength();i++) {
    		Node n = children.item(i);
    		if ("tr".equalsIgnoreCase(n.getNodeName())) {
    			nl.addItem(n);
    		}
    		if ("tbody".equalsIgnoreCase(n.getNodeName())) {
    	    	NodeList list = n.getChildNodes();
    	    	for (int j=0;j<list.getLength();j++) {
    	    		n = list.item(j);
    	    		if ("tr".equalsIgnoreCase(n.getNodeName())) {
    	    			nl.addItem(n);
    	    		}
    	    	}
    		}
    		if ("thead".equalsIgnoreCase(n.getNodeName())) {
    	    	NodeList list = n.getChildNodes();
    	    	for (int j=0;j<list.getLength();j++) {
    	    		n = list.item(j);
    	    		if ("tr".equalsIgnoreCase(n.getNodeName())) {
    	    			nl.addItem(n);
    	    		}
    	    	}
    		}
    		if ("tfoot".equalsIgnoreCase(n.getNodeName())) {
    	    	NodeList list = n.getChildNodes();
    	    	for (int j=0;j<list.getLength();j++) {
    	    		n = list.item(j);
    	    		if ("tr".equalsIgnoreCase(n.getNodeName())) {
    	    			nl.addItem(n);
    	    		}
    	    	}
    		}
    	}
    	return nl;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getRows()
     */
    public HTMLCollection getRows() {
    	if (rows != null) return rows;
    	return rows = new CHTMLCollection(createRowsList());
    }

    private CHTMLCollection bodies = null;
    
    private NodeList createBodiesList() {
    	CNodeList nl = new CNodeList(true);
    	NodeList children = getChildNodes();
    	for (int i=0;i<children.getLength();i++) {
    		Node n = children.item(i);
    		if ("tbody".equalsIgnoreCase(n.getNodeName())) {
    			nl.addItem(n);
    		}
    	}
    	return nl;
    }    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getTBodies()
     */
    public HTMLCollection getTBodies() {
    	if (bodies != null) return bodies;
    	return bodies = new CHTMLCollection(createBodiesList());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getBgColor()
     */
    public String getBgColor() {
        return getAttribute("bgcolor");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setBgColor(java.lang.String)
     */
    public void setBgColor(String bgColor) {
    	setAttribute("bgcolor",bgColor);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getBorder()
     */
    public String getBorder() {
        return getAttribute("border");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setBorder(java.lang.String)
     */
    public void setBorder(String border) {
    	setAttribute("border",border);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getCellPadding()
     */
    public String getCellPadding() {
        return getAttribute("cellpadding");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setCellPadding(java.lang.String)
     */
    public void setCellPadding(String cellPadding) {
    	setAttribute("cellpadding",cellPadding);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getCellSpacing()
     */
    public String getCellSpacing() {
        return getAttribute("cellspacing");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setCellSpacing(java.lang.String)
     */
    public void setCellSpacing(String cellSpacing) {
    	setAttribute("cellspacing",cellSpacing);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getFrame()
     */
    public String getFrame() {
        return getAttribute("frame");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setFrame(java.lang.String)
     */
    public void setFrame(String frame) {
    	setAttribute("frame",frame);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getRules()
     */
    public String getRules() {
        return getAttribute("rules");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setRules(java.lang.String)
     */
    public void setRules(String rules) {
    	setAttribute("rules",rules);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getSummary()
     */
    public String getSummary() {
        return getAttribute("summary");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setSummary(java.lang.String)
     */
    public void setSummary(String summary) {
    	setAttribute("summary",summary);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#getWidth()
     */
    public String getWidth() {
        return getAttribute("width");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#setWidth(java.lang.String)
     */
    public void setWidth(String width) {
    	setAttribute("width",width);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#createTHead()
     */
    public HTMLElement createTHead() {
    	CHTMLTheadElement elem = new CHTMLTheadElement(ownerDocument);
    	appendChild(elem);
    	return elem;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#deleteTHead()
     */
    public void deleteTHead() {
    	List l = new ArrayList();
    	NodeList nl = getChildNodes();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = nl.item(i);
    		if (n.getNodeName().equals("thead"))
    			l.add(nl.item(i));
    	}
    	for (Iterator it = l.iterator();it.hasNext();) {
    		removeChild((Node)it.next());
    	}
		if (bodies != null) {
			bodies.setNl(createBodiesList());
		}
		if (rows != null) {
			rows.setNl(createRowsList());
		}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#createTFoot()
     */
    public HTMLElement createTFoot() {
    	CHTMLTfootElement elem = new CHTMLTfootElement(ownerDocument);
    	appendChild(elem);
    	return elem;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#deleteTFoot()
     */
    public void deleteTFoot() {
    	List l = new ArrayList();
    	NodeList nl = getChildNodes();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = nl.item(i);
    		if (n.getNodeName().equals("tfoot"))
    			l.add(nl.item(i));
    	}
    	for (Iterator it = l.iterator();it.hasNext();) {
    		removeChild((Node)it.next());
    	}
		if (bodies != null) {
			bodies.setNl(createBodiesList());
		}
		if (rows != null) {
			rows.setNl(createRowsList());
		}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#createCaption()
     */
    public HTMLElement createCaption() {
    	CHTMLCaptionElement elem = new CHTMLCaptionElement(ownerDocument);
    	appendChild(elem);
    	return elem;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#deleteCaption()
     */
    public void deleteCaption() {
    	List l = new ArrayList();
    	NodeList nl = getChildNodes();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = nl.item(i);
    		if (n.getNodeName().equals("caption"))
    			l.add(nl.item(i));
    	}
    	for (Iterator it = l.iterator();it.hasNext();) {
    		removeChild((Node)it.next());
    	}
    }
    
    public Node appendChild(Node newChild) throws DOMException {
    	Node n = super.appendChild(newChild);
		if (bodies != null) {
			bodies.setNl(createBodiesList());
		}
		if (rows != null) {
			rows.setNl(createRowsList());
		}
    	return n;
    }
    
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
    	Node n = super.insertBefore(newChild, refChild);
		if (bodies != null) {
			bodies.setNl(createBodiesList());
		}
		if (rows != null) {
			rows.setNl(createRowsList());
		}
    	return n;
    }
    
    public Node removeChild(Node oldChild) throws DOMException {
    	Node n = super.removeChild(oldChild);
		if (bodies != null) {
			bodies.setNl(createBodiesList());
		}
		if (rows != null) {
			rows.setNl(createRowsList());
		}
    	return n;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#insertRow(int)
     */
    public HTMLElement insertRow(int index)
        throws DOMException {
    		CHTMLTrElement tr = new CHTMLTrElement(ownerDocument);
    		HTMLCollection nl = getRows();
    		if (index == -1) index = nl.getLength();
    		if (index < 0 || index > nl.getLength()) {
    			throw new DOMException(DOMException.INDEX_SIZE_ERR,index+" > "+nl.getLength()+" ,array index out of bound.");
    		}
    		if (nl.getLength() == 0) {
    			CHTMLTbodyElement body = new CHTMLTbodyElement(ownerDocument);
    			appendChild(body);
    			body.appendChild(tr);
    		} else {
	    		boolean inserted = false;
	    		Node lastNode = null;
	    		for (int i=0;i<nl.getLength();i++) {
	    			Node n = nl.item(i);
	    			if (i == index) {
	    				n.getParentNode().insertBefore(tr,n);
	    				inserted = true;
	    				lastNode = null;
	    				break;
	    			}
	    			lastNode = n;
	    		}
	    		if (!inserted && lastNode != null) {
	    			lastNode.getParentNode().appendChild(tr);
	    		} else if (!inserted) {
	    			appendChild(tr);
	    		}
    		}
    		if (rows != null) {
    			rows.setNl(createRowsList());
    		}
    		return tr;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableElement#deleteRow(int)
     */
    public void deleteRow(int index)
        throws DOMException {
		HTMLCollection nl = getRows();
		if (index == -1) index = nl.getLength()-1;
		if (index < 0 || index >= nl.getLength()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,index+" > "+nl.getLength()+" ,array index out of bound.");
		}
  		for (int i=0;i<nl.getLength();i++) {
  			Node n = nl.item(i);
  			if (i == index) {
  				n.getParentNode().removeChild(n);
  				break;
  			}
  		}
		if (rows != null) {
			rows.setNl(createRowsList());
		}
    }
}