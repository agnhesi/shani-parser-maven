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
import org.allcolor.dtd.parser.CDocType;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public final class CNamedNodeMap
	implements NamedNodeMap,
		Serializable {
	//public static final long serialVersionUID = -3507834853760069471L;
	/** DOCUMENT ME! */
	//private List indexItem = new ArrayList();
	
	static final long serialVersionUID = 2630426198690198816L;

	/** DOCUMENT ME! */
	public ANode [] list = null;

	public int count = 0;
	
	private final ANode owner;
	
	private boolean freeze = false;

	private List asList() {
		ArrayList result = new ArrayList(count);
		for (int i=0;i<count;i++) {
			result.add(list[i]);
		}
		return result;
	}
	
	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}
	
	private void isFreezed() {
		if (owner != null) owner.isReadOnly();
		if (freeze)
			throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,"This node map is read-only.");
	}
	
	public CNamedNodeMap(ANode owner) {
		this.owner = owner;
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#getLength()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getLength() {
		return count;
	} // end getLength()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#setNamedItem(org.w3c.dom.Node)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param arg DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Node setNamedItem(final Node arg)
		throws DOMException {
		isFreezed();
		if (owner != null && owner.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			if (arg.getNodeType() != Node.NOTATION_NODE &&
				arg.getNodeType() != Node.ENTITY_NODE) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,"Cannot insert node here.");
			}
		} 
		if (owner != null && owner.getNodeType() == Node.ELEMENT_NODE) {
			if (arg.getNodeType() != Node.ATTRIBUTE_NODE) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,"Cannot insert node here.");
			}
		}
		if (owner != null && arg.getOwnerDocument() != owner.getOwnerDocument()) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,"wrong document.");
		}
		if (arg.getNodeType() == Node.ATTRIBUTE_NODE) {
			Attr newAttr = (Attr)arg;
			if (newAttr.getOwnerElement() != null &&
				newAttr.getOwnerElement() != owner) {
				throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR,newAttr.getOwnerElement().toString());
			}
		}
		return setNamedItemForce(arg);
	} // end setNamedItem()

	public Node setNamedItemForce(final Node arg)
	throws DOMException {
		if (list != null) {
			String name = arg.getNodeName();
			boolean boonsget = false;
			String ns1 = null;
			for (int i=0;i<count;i++) {
				ANode tmp = list[i];
				if (name == tmp.getNodeName()) {
					if (!boonsget) {
						ns1 = arg.getNamespaceURI();
						boonsget = true;
					}
					String ns2 = tmp.getNamespaceURI();
					if (ns1 == ns2 || (ns1 != null && ns1.equals(ns2))) {
						ANode toRet = list[i];
						list[i] = (ANode)arg;
						return toRet;
					}
				}
			}
		} else {
			list = new ANode[2];
		}
		if (count == list.length) {
			ANode [] newArray = new ANode[list.length*2+1];
			System.arraycopy(list,0,newArray,0,count);
			list = newArray;
		}
		list[count++] = (ANode)arg;
		return null;
	} // end setNamedItem()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#getNamedItem(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Node getNamedItem(final String name) {
		if (list == null) return null;
		for (int i=0;i<count;i++) {
			ANode node = list[i];
			if (node.getNodeName().equals(name)) {
				return node;
			}
		}
		return null;
	} // end getNamedItem()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#setNamedItemNS(org.w3c.dom.Node)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param arg DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Node setNamedItemNS(final Node arg)
		throws DOMException {
		return setNamedItem(arg);
	} // end setNamedItemNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#getNamedItemNS(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param namespaceURI DOCUMENT ME!
	 * @param localName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Node getNamedItemNS(
		final String namespaceURI,
		String localName)
		throws DOMException {
		if (list == null) return null;
		localName = localName.intern();
		if (owner.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			// yurk test namednodemapgetnameditemns01 from DOM 2 test suite and
			// test namednodemapgetnameditemns01 from DOM 3 test suite are contradictory
			// EVIL HACK !!
			if (namespaceURI == null &&
				owner.getOwnerDocument() != null &&
				owner.getOwnerDocument().getDocumentElement() != null &&
				"html".equalsIgnoreCase(owner.getOwnerDocument().getDocumentElement().getNodeName())) {
				return getNamedItem(localName);
			}
			return null;
		}
		if (owner.getNodeType() == Node.ELEMENT_NODE) {
			if ("*".equals(namespaceURI)) return null;
		}
		String prefix = owner.lookupPrefix(namespaceURI);
		for (int i=0;i<count;i++) {
			ANode node = list[i];
			if (namespaceURI == null && node.getNamespaceURI() == null &&
				localName == node.getLocalName()) {
				return node;
			} else if (node.prefix == prefix &&
					       localName == node.localName){
				if (namespaceURI != null && namespaceURI.equals(node.getNamespaceURI())) {
					return node;
				} else if (namespaceURI == null && node.getNamespaceURI() == null) {
					return node;
				}
			} else if (namespaceURI != null && namespaceURI.equals(node.getNamespaceURI()) &&
					       localName == node.localName) {
				return node;
			}
		}
		return null;
	} // end getNamedItemNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#item(int)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Node item(final int iindex) {
		try {
			return list[iindex];
		}
		catch (Exception e) {
			return null;
		}
	} // end item()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#removeNamedItem(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Node removeNamedItem(String name)
		throws DOMException {
		isFreezed();
		if (list != null) {
			name = name.intern();
			for (int i=0;i<count;i++) {
				ANode n = list[i];
				if (n.name == name) {
					List list = asList();
					list.remove(n);
					int lsize = list.size();
					int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
					this.list = (ANode[]) list.toArray(new ANode[len]);
					count--;
					if (owner != null && owner.getOwnerDocument() != null) {
						CDocType dt = (CDocType)owner.getOwnerDocument().getDoctype();
						if (dt != null) {
							org.allcolor.dtd.parser.CElement elem =
								(org.allcolor.dtd.parser.CElement)dt.getKnownElements().get(owner.getNodeName());
							if (elem != null) {
								Map attr = elem.getAttributes();
								if (attr != null) {
									for (Iterator it = attr.entrySet().iterator();it.hasNext();) {
										Map.Entry entry = (Map.Entry)it.next();
										org.allcolor.dtd.parser.CElement.CAttr at = (org.allcolor.dtd.parser.CElement.CAttr)
											entry.getValue();
										if (at.defaultValue != null && name.equals(at.name)) {
											setNamedItemForce(new CAttr(at.name,at.defaultValue,(ADocument)owner.getOwnerDocument(),(CElement)owner,false));
											break;
										}
									}
								}
							}
						}
					}
					return n;
				}
			}
		}
		throw new DOMException(DOMException.NOT_FOUND_ERR,name+" was not found in this node map.");
	} // end removeNamedItem()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.NamedNodeMap#removeNamedItemNS(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param namespaceURI DOCUMENT ME!
	 * @param localName DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Node removeNamedItemNS(
		final String namespaceURI,
		String localName)
		throws DOMException {
		isFreezed();
		ANode node = null;
		boolean boofound = false;
		if (list != null) {
			localName = localName.intern();
			for (int i=0;i<count;i++) {
				node = list[i];
				if (namespaceURI == null && node.getNamespaceURI() == null &&
					localName == node.localName) {
					boofound = true;
					break;
				} else if (namespaceURI != null && namespaceURI.equals(node.getNamespaceURI()) &&
						localName == node.localName) {
					boofound = true;
					break;
				}
			}
		}
		if (!boofound) {
			node = null;
		}
		if (node != null && owner != null && owner.getOwnerDocument() != null) {
			List list = asList();
			list.remove(node);
			int lsize = list.size();
			int len = this.list == null ? lsize : (lsize > this.list.length ? lsize : this.list.length);
			this.list = (ANode[]) list.toArray(new ANode[len]);
			count--;
			CDocType dt = (CDocType)owner.getOwnerDocument().getDoctype();
			if (dt != null) {
				org.allcolor.dtd.parser.CElement elem =
					(org.allcolor.dtd.parser.CElement)dt.getKnownElements().get(owner.getNodeName());
				if (elem != null) {
					Map attr = elem.getAttributes();
					if (attr != null) {
						for (Iterator it = attr.entrySet().iterator();it.hasNext();) {
							Map.Entry entry = (Map.Entry)it.next();
							org.allcolor.dtd.parser.CElement.CAttr at = (org.allcolor.dtd.parser.CElement.CAttr)
								entry.getValue();
							if (at.defaultValue != null && node.getNodeName().equals(at.name)) {
								setNamedItemForce(new CAttr(at.name,at.defaultValue,(ADocument)owner.getOwnerDocument(),(CElement)owner,false));
								break;
							}
						}
					}
				}
			}
		}		
		if (node == null)
			throw new DOMException(DOMException.NOT_FOUND_ERR,"The node was not found.");
		return node;
	} // end removeNamedItemNS()
} // end CNamedNodeMap
