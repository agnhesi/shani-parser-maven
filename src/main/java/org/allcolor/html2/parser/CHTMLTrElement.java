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
import org.allcolor.xml.parser.dom.ANode;
import org.allcolor.xml.parser.dom.CNodeList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html2.HTMLCollection;
import org.w3c.dom.html2.HTMLElement;
import org.w3c.dom.html2.HTMLTableElement;
import org.w3c.dom.html2.HTMLTableRowElement;

import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLTrElement
    extends CHTMLTableElement
    implements HTMLTableRowElement {
    static final long serialVersionUID = 2076519462151689274L;
    private static final List ve = 
    	Arrays.asList(new String[] {
                "th",
                "td"
            }
        );
	/**
     * DOCUMENT ME!
     *
     * @param ownerDocument
     */
    public CHTMLTrElement(ADocument ownerDocument) {
        super("tr", ownerDocument);
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
     * @see org.w3c.dom.html.HTMLTableRowElement#getRowIndex()
     */
    public int getRowIndex() {
    	ANode parent = null;
    	parent = parentNode;
    	while (parent != null && parent.name != "table") {
    		parent = parent.parentNode;
    	}
    	if (parent == null) return -1;
    	HTMLCollection nl = ((HTMLTableElement)parent).getRows();
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = nl.item(i);
    		if (n == this) return i;
    	}
        return -1;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableRowElement#getSectionRowIndex()
     */
    public int getSectionRowIndex() {
    	ANode parent = null;
    	parent = parentNode;
    	if (parent == null) return -1;
    	NodeList nl = ((Element)parent).getElementsByTagName("tr");
    	for (int i=0;i<nl.getLength();i++) {
    		Node n = nl.item(i);
    		if (n == this) return i;
    	}
        return -1;
    }

    private CHTMLCollection cellsCol = null;
    
    private CNodeList createCellsCol() {
    	CNodeList nli = new CNodeList(true);
    	NodeList nl1 = getChildNodes();
    	for (int i=0;i<nl1.getLength();i++) {
    		Node n = nl1.item(i);
    		if ("th".equalsIgnoreCase(n.getNodeName()) ||
    			"td".equalsIgnoreCase(n.getNodeName()))
    		nli.addItem(n);
    	}
    	return nli;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableRowElement#getCells()
     */
    public HTMLCollection getCells() {
    	if (cellsCol != null) return cellsCol;
        return cellsCol = new CHTMLCollection(createCellsCol());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableRowElement#insertCell(int)
     */
    public HTMLElement insertCell(int index)
        throws DOMException {
		CHTMLTdElement tr = new CHTMLTdElement(ownerDocument);
		HTMLCollection nl = getCells();
		if (index == -1) index = nl.getLength();
		if (index < 0 || index > nl.getLength()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR,index+" > "+nl.getLength()+" ,array index out of bound.");
		}
		if (nl.getLength() == 0) {
			appendChild(tr);
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
		if (cellsCol != null) {
			cellsCol.setNl(createCellsCol());
		}
		return tr;    	
    }
    
    public Node appendChild(Node newChild) throws DOMException {
    	Node n = super.appendChild(newChild);
    	if (cellsCol != null)  {
    		cellsCol.setNl(createCellsCol());
    	}
    	return n;
    }
    
    public Node removeChild(Node oldChild) throws DOMException {
    	Node n =  super.removeChild(oldChild);
    	if (cellsCol != null)  {
    		cellsCol.setNl(createCellsCol());
    	}
    	return n;
    }
    
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
    	Node n =  super.insertBefore(newChild, refChild);
    	if (cellsCol != null)  {
    		cellsCol.setNl(createCellsCol());
    	}
    	return n;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.html.HTMLTableRowElement#deleteCell(int)
     */
    public void deleteCell(int index)
        throws DOMException {
		HTMLCollection nl = getCells();
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
		if (cellsCol != null) {
			cellsCol.setNl(createCellsCol());
		}
    }
}