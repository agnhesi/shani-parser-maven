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
package org.allcolor.xml.parser.dom;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CNodeList
	implements NodeList,Serializable {
	static final long serialVersionUID = -2918233150387867448L;
	/** DOCUMENT ME! */
	private ANode [] list = null;

	protected int count = 0;
	
	private boolean detach = false;
	
	public CNodeList(boolean detach) {
		this.detach = detach;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NodeList#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		if (detach) {
			for (int i=0;i<count;i++) {
				ANode n = list[i];
				if (n.parentNode == null) {
					List list = asList();
					if (list.remove(i) != null) {
						int lsize = list.size();
						int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
						this.list = (ANode[])list.toArray(new ANode[len]);
						count--;
					}
				}
			}
		}
		return count;
	} // end getLength()

	/**
	 * DOCUMENT ME!
	 *
	 * @param node DOCUMENT ME!
	 */
	public void addItem(final Node node) {
		if (list == null) list = new ANode[2];
		if (count == list.length) {
			ANode [] newArray = new ANode[list.length*2+1];
			System.arraycopy(list,0,newArray,0,count);
			list = newArray;
		}
		list[count] = (ANode)node;
		count++;
	} // end addItem()

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 * @param node DOCUMENT ME!
	 */
	public void insertAt(
		final int index,
		final Node node) {
		if (index < 0) {
			if (this.list == null) {
				list = new ANode[2];
				list[count] = (ANode)node;
			} else {
				ANode [] newArray = new ANode[list.length+1];
				System.arraycopy(list,0,newArray,1,count);
			}
			list[0] = (ANode)node;
			count++;
			return;
		} else
		if (index >= getLength()) {
			List list = this.list == null ? new ArrayList(1) : asList();
			list.add(node);
			int lsize = list.size();
			int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
			this.list = (ANode[]) list.toArray(new ANode[len]);
			count++;
			return;
		} else
		if (this.list != null) {
			List list = asList();
			List before = list.subList(0, index);
			List after = list.subList(index, list.size());
			list = new ArrayList(list.size()+1);
	
			for (int i = 0; i < before.size(); i++) {
				list.add(before.get(i));
			} // end for
			list.add(node);
	
			for (int i = 0; i < after.size(); i++) {
				list.add(after.get(i));
			} // end for
			int lsize = list.size();
			int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
			this.list = (ANode[]) list.toArray(new ANode[len]);
			count++;
			return;
		} else {
			list = new ANode[2];
			list[count] = (ANode)node;
			count++;
			return;
		}
	} // end insertAt()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NodeList#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Node item(final int index) {
		try {
			if (detach) {
				ANode n = list[index];
				if (n.parentNode == null) {
					List list = asList();
					if (list.remove(index) != null) {
						int lsize = list.size();
						int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
						this.list = (ANode[])list.toArray(new ANode[len]);
						count = list.size();
						return item(index);
					}
				}
				return n;
			}
			return list[index];
		}
		catch (Exception e) {
			return null;
		}
	} // end item()

	private List asList() {
		ArrayList result = new ArrayList(count);
		for (int i=0;i<count;i++) {
			result.add(list[i]);
		}
		return result;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param node DOCUMENT ME!
	 */
	public void removeItem(final Node node) {
		if (list == null) return;
		List list = asList();
		if (list.remove(node)) {
			int lsize = list.size();
			int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
			this.list = (ANode[])list.toArray(new ANode[len]);
			count--;
		}
	} // end removeItem()

	public boolean hasItem(final Node node) {
		for (int i=0;i<count;i++) {
			if (list[i] == node) return true;
		}
		return false;
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 * @param newNode DOCUMENT ME!
	 */
	public void replace(
		final int index,
		final Node newNode) {
		try {
			list[index] = (ANode)newNode;
		}
		catch (Exception e) {
		}
	} // end replace()
} // end CNodeList
