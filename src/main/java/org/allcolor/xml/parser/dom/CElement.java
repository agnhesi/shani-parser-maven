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
import org.allcolor.css.parser.CCSSStyleRule;
import org.allcolor.dtd.parser.CDocType;
import org.allcolor.xml.parser.dom.CAttr;

import org.allcolor.xml.parser.CStringBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.ElementCSSInlineStyle;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CElement
	extends ANode
	implements Element,
		ElementCSSInlineStyle {
	/** DOCUMENT ME! */
	public final static long serialVersionUID = 6632642327168876194L;

	/** DOCUMENT ME! */
	protected String idAttribute = "id";

	/** DOCUMENT ME! */
	private CSSStyleDeclaration decl = null;
	private boolean init = true;

	public CElement(
		final ADocument ownerDocument) {
		super(ownerDocument);
	}
	/**
	 * Creates a new CElement object.
	 *
	 * @param name DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CElement(
		final String    name,
		final ADocument ownerDocument) {
		super(name, ownerDocument);
		if (getNodeType() == Node.ELEMENT_NODE) {
			CDocType dt = (CDocType)ownerDocument.doctype;
			if (dt != null) {
				org.allcolor.dtd.parser.CElement elem =
					(org.allcolor.dtd.parser.CElement)dt.getKnownElements().get(name);
				if (elem != null) {
					Map attr = elem.getAttributes();
					if (attr != null) {
						for (Iterator it = attr.entrySet().iterator();it.hasNext();) {
							Map.Entry entry = (Map.Entry)it.next();
							org.allcolor.dtd.parser.CElement.CAttr at = (org.allcolor.dtd.parser.CElement.CAttr)
								entry.getValue();
							if (at.defaultValue != null) {
								setAttributeAsDefault(at.name,at.defaultValue);
							}
						}
					}
					setIdAttribute(elem.getIDAttr().name,true);
				}
			}
		}
		init = false;
	} // end CElement()

	public CElement(
		final String    name,
		final ADocument ownerDocument,
		final int indexSep) {
		super(name, ownerDocument,indexSep);
		if (getNodeType() == Node.ELEMENT_NODE) {
			CDocType dt = (CDocType)ownerDocument.doctype;
			if (dt != null) {
				org.allcolor.dtd.parser.CElement elem =
					(org.allcolor.dtd.parser.CElement)dt.getKnownElements().get(name);
				if (elem != null) {
					Map attr = elem.getAttributes();
					if (attr != null) {
						for (Iterator it = attr.entrySet().iterator();it.hasNext();) {
							Map.Entry entry = (Map.Entry)it.next();
							org.allcolor.dtd.parser.CElement.CAttr at = (org.allcolor.dtd.parser.CElement.CAttr)
								entry.getValue();
							if (at.defaultValue != null) {
								setAttributeAsDefault(at.name,at.defaultValue);
							}
						}
					}
					setIdAttribute(elem.getIDAttr().name,true);
				}
			}
		}
		init = false;
	} // end CElement()	
	public final boolean hasAttribute(String name) {
		if (listAttributes == null) return false;
		Attr attr = (Attr)listAttributes.getNamedItem(name);
		return attr != null;
	} // end hasAttributes()
	
	
	protected final void setAttributeAsDefault(
			final String name,
			final String value)
			throws DOMException {
			CAttr attr = new CAttr(name, value, ownerDocument,this, false);
			attr.setDefaults(true);
			setAttributeNode(attr);
			if ("xmlns".equals(attr.getPrefix()) ||
				"xmlns".equals(name)) {
				((ANode)attr).notifyNSChange(attr.getLocalName());
			}
		} // end setAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setAttribute(java.lang.String, java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void setAttribute(
		final String name,
		final String value)
		throws DOMException {
		CAttr attr = new CAttr(name, value, ownerDocument,this, true);
		if (!ownerDocument.isBuildStage && !"xmlns".equals(name)) attr.dom1Nullify();
		setAttributeNode(attr);
	} // end setAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String getAttribute(final String name) {
		if (listAttributes == null) return "";
		Attr attr = (Attr) listAttributes.getNamedItem(name);

		if (attr != null) {
			return attr.getValue();
		} // end if

		return "";
	} // end getAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setAttributeNS(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param namespaceURI DOCUMENT ME!
	 * @param qualifiedName DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void setAttributeNS(
		final String namespaceURI,
		final String qualifiedName,
		final String value)
		throws DOMException {
		if (ownerDocument != null) {
			if (namespaceURI != null && namespaceURI.equals("*")) {
				Attr attr = ownerDocument.createAttributeNS(null,qualifiedName);
				attr.setValue(value);
				setAttributeNodeNS(attr);
			} else {
				Attr attr = ownerDocument.createAttributeNS(namespaceURI,qualifiedName);
				attr.setValue(value);
				setAttributeNodeNS(attr);
			}
		}
	} // end setAttributeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getAttributeNS(java.lang.String,
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
	public final String getAttributeNS(
		final String namespaceURI,
		final String localName)
		throws DOMException {
		Attr attr = getAttributeNodeNS(namespaceURI,localName);
		return attr == null ? "" : attr.getValue();
	} // end getAttributeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getAttributeNode(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final Attr getAttributeNode(final String name) {
		if (listAttributes == null) return null;
		Attr attr = (Attr) listAttributes.getNamedItem(name);
		if (attr == null && name.equals("xmlns")) {
			List	   nsList = getNamespaceList();
			CNamespace ns = null;
			for (Iterator it = nsList.iterator(); it.hasNext();) {
				ns = (CNamespace) it.next();

				if (ns.getPrefix() == null) {
					break;
				} // end if
			} // end for

			if (ns != null && ns.getPrefix() == null) {
				if (ns.getNamespaceURI().trim().length() > 0)
					return new CAttr("xmlns", ns.getNamespaceURI(),
						ownerDocument,this,hasAttribute(name));
			} // end if
		} // end if
		return attr;
	} // end getAttributeNode()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setAttributeNodeNS(org.w3c.dom.Attr)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param newAttr DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final Attr setAttributeNodeNS(final Attr newAttr)
		throws DOMException {
		if (newAttr == null) {
			return null;
		} // end if
		Attr oldAttr = null;
		if (listAttributes != null) {
			oldAttr = (Attr)listAttributes.getNamedItemNS(newAttr.getNamespaceURI(),newAttr.getLocalName());
			if (oldAttr != null) {
				if (!oldAttr.getNodeName().equals(newAttr.getNodeName())) {
					isReadOnly();
					removeAttributeNode(oldAttr);
				}
			}
		}
		setAttributeNode(newAttr);
		return  oldAttr;
	} // end setAttributeNodeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getAttributeNodeNS(java.lang.String,
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
	public final Attr getAttributeNodeNS(
		final String namespaceURI,
		final String localName)
		throws DOMException {
		return (Attr)(listAttributes == null ? null : listAttributes.getNamedItemNS(namespaceURI,localName));
	} // end getAttributeNodeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getElementsByTagName(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final NodeList getElementsByTagName(final String name) {
		CNodeList list = new CNodeList(true);
		_GetElementsByTagName(name, list, this);
		return list;
	} // end getElementsByTagName()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getElementsByTagNameNS(java.lang.String,
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
	public final NodeList getElementsByTagNameNS(
		final String namespaceURI,
		final String localName)
		throws DOMException {
		CNodeList list = new CNodeList(true);
		_GetElementsByTagNameNS(localName, namespaceURI, list, this);

		return list;
	} // end getElementsByTagNameNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setIdAttribute(java.lang.String, boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param isId DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void setIdAttribute(
		final String  name,
		final boolean isId)
		throws DOMException {
		if (!init && !ownerDocument.isBuildStage && getAttributeNode(name) == null && isId)
			throw new DOMException(DOMException.NOT_FOUND_ERR,"The attribute "+name+" was not found.");
		if (!init && !ownerDocument.isBuildStage) {
			isReadOnly();
		}
		if ((isId) && (name != null)) {
			idAttribute = name;
		} // end if
		if (!isId) {
			if (idAttribute != null && idAttribute.equals(name)) {
				idAttribute = null;
			}
		}
	} // end setIdAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setIdAttributeNS(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param namespaceURI DOCUMENT ME!
	 * @param localName DOCUMENT ME!
	 * @param isId DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void setIdAttributeNS(
		String  namespaceURI,
		final String  localName,
		final boolean isId)
		throws DOMException {
		if (namespaceURI.equals("*")) {
			if (listAttributes != null) {
				for (int i=0;i<listAttributes.getLength();i++) {
					Attr attr = (Attr)listAttributes.item(i);
					if (attr.getLocalName() != null && attr.getLocalName().equals(localName)) {
						setIdAttributeNode(attr,isId);
						return;
					}
				}
			}
			throw new DOMException(DOMException.NOT_FOUND_ERR,"{"+namespaceURI+"}:"+localName+" not found !");
		}
		Attr attr = getAttributeNodeNS(namespaceURI,localName);
		if (attr == null)
			throw new DOMException(DOMException.NOT_FOUND_ERR,"{"+namespaceURI+"}:"+localName+" not found !");
		setIdAttributeNode(attr,isId);
	} // end setIdAttributeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setIdAttributeNode(org.w3c.dom.Attr, boolean)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param idAttr DOCUMENT ME!
	 * @param isId DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void setIdAttributeNode(
		final Attr    idAttr,
		final boolean isId)
		throws DOMException {
		if (!init && !ownerDocument.isBuildStage && idAttr.getOwnerElement() != this && isId)
			throw new DOMException(DOMException.NOT_FOUND_ERR,"The attribute "+idAttr.getName()+" was not found.");
		if (!init && !ownerDocument.isBuildStage) {
			isReadOnly();
		}
		if ((isId) && (idAttr != null)) {
			idAttribute = idAttr.getName();
		} // end if
		if (!isId) {
			if (idAttribute != null && idAttribute.equals(idAttr.getName())) {
				idAttribute = null;
			}
		}
	} // end setIdAttributeNode()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getTagName()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final String getTagName() {
		return name;
	} // end getTagName()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#hasAttributeNS(java.lang.String,
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
	public final boolean hasAttributeNS(
		final String namespaceURI,
		final String localName)
		throws DOMException {
		if (listAttributes == null) return false;
		Attr attr = (Attr)listAttributes.getNamedItemNS(namespaceURI,localName);
		return attr != null;
	} // end hasAttributeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#removeAttribute(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void removeAttribute(final String name)
		throws DOMException {
		Attr attr = getAttributeNode(name);
		removeAttributeNode(attr);
	} // end removeAttribute()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#setAttributeNode(org.w3c.dom.Attr)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param newAttr DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public Attr setAttributeNode(final Attr anewAttr)
		throws DOMException {
		isReadOnly();
		CAttr newAttr = (CAttr)anewAttr;
		if (newAttr == null) {
			return null;
		} // end if
		if (newAttr.parentNode != null &&
			newAttr.parentNode != this) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR,newAttr.getOwnerElement().toString());
		}
		
		if (newAttr.ownerDocument != ownerDocument && newAttr.ownerDocument != null)
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,"Invalid parent document.");
		Attr oldAttr = null;
		if (newAttr.localName != null)
			oldAttr = (Attr) (listAttributes == null ? null : listAttributes.getNamedItemNS(newAttr.getNamespaceURI(),newAttr.localName));
		else
			oldAttr = (Attr) getAttributeNode(newAttr.name);

		if (oldAttr == null && newAttr.localName == null)
			oldAttr = (Attr)(listAttributes == null ? null : listAttributes.getNamedItem(newAttr.name));
		if (listAttributes == null)
			listAttributes = new CNamedNodeMap(this);
		listAttributes.setNamedItemForce(newAttr);
		((INode) newAttr).setParent(this);
		if ("xmlns" == newAttr.prefix ||
			"xmlns" == newAttr.name) {
			notifyNSChange(newAttr.localName);
		}

		return oldAttr;
	} // end setAttributeNode()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#removeAttributeNS(java.lang.String,
	 *      java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param namespaceURI DOCUMENT ME!
	 * @param localName DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final void removeAttributeNS(
		final String namespaceURI,
		final String localName)
		throws DOMException {
		Attr attr = getAttributeNodeNS(namespaceURI,localName);
		removeAttributeNode(attr);
	} // end removeAttributeNS()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#removeAttributeNode(org.w3c.dom.Attr)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param oldAttr DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public final Attr removeAttributeNode(final Attr oldAttr)
		throws DOMException {
		isReadOnly();
		if (oldAttr == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,"Attribute is null.");
		} // end if

		CAttr attri = null;
		if (oldAttr.getLocalName() == null) {
			attri = (CAttr)getAttributeNode(oldAttr.getName());
		} else {
			attri = (CAttr)getAttributeNodeNS(oldAttr.getNamespaceURI(),oldAttr.getLocalName());
		}
		if (attri == null ||
			attri != oldAttr)
			throw new DOMException(DOMException.NOT_FOUND_ERR,"The attribute "+oldAttr.getName()+" was not found.");

		attri.parentNode = null;
		
		if (listAttributes != null) {
			if (oldAttr.getLocalName() == null) {
				listAttributes.removeNamedItem(oldAttr.getName());
			} else {
				listAttributes.removeNamedItemNS(oldAttr.getNamespaceURI(),oldAttr.getLocalName());
			}
		}
		if (ownerDocument != null) {
			CDocType dt = (CDocType)ownerDocument.getDoctype();
			if (dt != null) {
				org.allcolor.dtd.parser.CElement elem =
					(org.allcolor.dtd.parser.CElement)dt.getKnownElements().get(name);
				if (elem != null) {
					Map attr = elem.getAttributes();
					if (attr != null) {
						for (Iterator it = attr.entrySet().iterator();it.hasNext();) {
							Map.Entry entry = (Map.Entry)it.next();
							org.allcolor.dtd.parser.CElement.CAttr at = (org.allcolor.dtd.parser.CElement.CAttr)
								entry.getValue();
							if (at.defaultValue != null && oldAttr.getName().equals(at.name)) {
								setAttributeAsDefault(at.name,at.defaultValue);
								break;
							}
						}
					}
				}
			}
		}
		return oldAttr;
		
	} // end removeAttributeNode()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Node#getNodeType()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	} // end getNodeType()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Node#setNodeValue(java.lang.String)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param nodeValue DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public void setNodeValue(final String nodeValue)
		throws DOMException {}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Node#getNodeValue()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public String getNodeValue()
		throws DOMException {
		return null;
	} // end getNodeValue()

	private CTypeInfo info = null;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.Element#getSchemaTypeInfo()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public TypeInfo getSchemaTypeInfo() {
		if (info != null) return info;
		// FIXME TODO
		if (ownerDocument.hasXSD())
			info = new CTypeInfo(this,true);
		else
			info = new CTypeInfo(this,false);
		return info;
	} // end getSchemaTypeInfo()

	public void setTextContent(String textContent) throws DOMException {
		NodeList nl = getChildNodes();
		while (nl.getLength() > 0)
			removeChild(nl.item(0));
		if (ownerDocument != null)
			appendChild(ownerDocument.createTextNode(textContent));
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.css.ElementCSSInlineStyle#getStyle()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CSSStyleDeclaration getStyle() {
		if (decl == null) {
			String csstext = getAttribute("style");

			if ((csstext != null) && !"".equals(csstext)) {
				CCSSStyleRule rule = new CCSSStyleRule(null,
						csstext, null, null);
				decl = rule.getStyle();
			} // end if

		} // end if

		return decl;
	} // end getStyle()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		CStringBuilder result = new CStringBuilder();
		result.append("<");
		result.append(name);
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
				result.append(node.toString());
			} // end for

			result.append("</");
			result.append(name);
			result.append(">");
		} // end if
		else {
			result.append(" />");
		} // end else

		return result.toString();
	} // end toString()

	/**
	 * DOCUMENT ME!
	 *
	 * @param tagName DOCUMENT ME!
	 * @param list DOCUMENT ME!
	 * @param elem DOCUMENT ME!
	 */
	private final void _GetElementsByTagName(
		final String    tagName,
		final CNodeList list,
		final Element   elem) {
		if (elem == null) {
			return;
		} // end if
		if ((elem.getNodeName().equals(tagName)) ||
				(tagName.equals("*"))) {
			if (elem != this)
				list.addItem(elem);
		} // end if

		NodeList nl = elem.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				_GetElementsByTagName(tagName, list, (Element) node);
			} // end if
			else if (node.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				NodeList inner = node.getChildNodes();
				for (int j=0;j<inner.getLength();j++) {
					Node inode = inner.item(j);
					if (inode.getNodeType() == Node.ELEMENT_NODE) {
						_GetElementsByTagName(tagName, list, (Element) inode);
					} // end if
				}
			}
		} // end for
	} // end _GetElementsByTagName()

	/**
	 * DOCUMENT ME!
	 *
	 * @param tagName DOCUMENT ME!
	 * @param namespaceURI DOCUMENT ME!
	 * @param list DOCUMENT ME!
	 * @param elem DOCUMENT ME!
	 */
	private final void _GetElementsByTagNameNS(
		final String    tagName,
		final String    namespaceURI,
		final CNodeList list,
		final Element   elem) {
		if (elem == null) {
			return;
		} // end if
		if (
			(
				(tagName.equals(elem.getLocalName())) &&
				(namespaceURI == null) &&
				(namespaceURI == elem.getNamespaceURI())
		    ) ||
			(
				(tagName.equals("*")) &&
				(namespaceURI == null) &&
				(namespaceURI == elem.getNamespaceURI())
		    ) ||
			(
				(tagName.equals(elem.getLocalName())) &&
				(namespaceURI != null) &&
				(
				 namespaceURI.equals(elem.getNamespaceURI()) ||
				 namespaceURI.equals("*"))
		    ) ||
			(
				(tagName.equals("*")) &&
				(namespaceURI != null) &&
				(
				 namespaceURI.equals(elem.getNamespaceURI()) ||
				 namespaceURI.equals("*"))
			)) {
			if (elem != this)
				list.addItem(elem);
		} // end if

		NodeList nl = elem.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				_GetElementsByTagNameNS(tagName, namespaceURI, list,
					(Element) node);
			} // end if
			else if (node.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
				NodeList inner = node.getChildNodes();
				for (int j=0;j<inner.getLength();j++) {
					Node inode = inner.item(j);
					if (inode.getNodeType() == Node.ELEMENT_NODE) {
						_GetElementsByTagNameNS(tagName, namespaceURI, list, (Element) inode);
					} // end if
				}
			}
		} // end for
	} // end _GetElementsByTagNameNS()
} // end CElement
