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
package org.allcolor.dtd.parser;
import org.allcolor.xml.parser.CStringBuilder;
import org.allcolor.xml.parser.dom.ANode;
import org.allcolor.xml.parser.dom.ADocument;
import org.allcolor.xml.parser.dom.CNamedNodeMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 * @author Jarle H. Næss- Patch quoted in code
 */
public final class CDocType
	extends ANode
	implements DocumentType {
	/** DOCUMENT ME! */
	public final static long serialVersionUID = 1957695709599916783L;

	/** DOCUMENT ME! */
	private CNamedNodeMap nnmElements = new CNamedNodeMap(this);

	/** DOCUMENT ME! */
	private CNamedNodeMap nnmEntities = new CNamedNodeMap(this);

	/** DOCUMENT ME! */
	private Map knownElements;

	/** DOCUMENT ME! */
	private Map knownEntities;

	/** DOCUMENT ME! */
	private String content;

	/** DOCUMENT ME! */
	private String internalSubset = null;

	/** DOCUMENT ME! */
	private String publicId;

	/** DOCUMENT ME! */
	private String qualifiedName;

	/** DOCUMENT ME! */
	private String systemId;

	public Object clone() throws CloneNotSupportedException {
		CDocType dt = (CDocType) super.clone();
		dt.nnmEntities = new CNamedNodeMap(dt);
		dt.nnmElements = new CNamedNodeMap(dt);
		dt.knownElements = null;
		dt.knownEntities = null;
		if (knownEntities != null) {
			Map k = new HashMap();
			for (Iterator it = knownEntities.entrySet().iterator();it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next();
				Object key = entry.getKey();
				CEntity val = (CEntity)entry.getValue();
				val = (CEntity)val.clone();
				k.put(key,val);
			}
			dt.setKnownEntities(k);
		}
		if (knownElements != null) {
			Map k = new HashMap();
			for (Iterator it = knownElements.entrySet().iterator();it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				if (val instanceof CNotation) {
					CNotation not = (CNotation)val;
					not = (CNotation)not.clone();
					k.put(key,not);
				} else if (val instanceof CElement) {
					CElement elem = (CElement)val;
					elem = (CElement)elem.clone();
					k.put(key,elem);
				}
			}
			dt.setKnownElements(k);
		}
		return dt;
	}
	
	/**
	 * Creates a new CDocType object.
	 *
	 * @param content DOCUMENT ME!
	 * @param qualifiedName DOCUMENT ME!
	 * @param publicId DOCUMENT ME!
	 * @param systemId DOCUMENT ME!
	 * @param ownerDocument DOCUMENT ME!
	 */
	public CDocType(
		final String content,
		final String qualifiedName,
		final String publicId,
		final String systemId,
		final ADocument ownerDocument) {
		super(qualifiedName, ownerDocument);
		this.content		   = content;
		this.publicId		   = publicId;
		this.systemId		   = systemId;
		this.qualifiedName     = qualifiedName;
		this.knownEntities     = new HashMap();
		this.knownElements     = new HashMap();
		if (ownerDocument != null)
			ownerDocument.setDocumentType(this);

		// Patch by Jarle H. Næss, Internal subset was returning null
		createInternalSubset(content);
		this.isReadOnly = true;
	} // end CDocType()

	public String getBaseURI() {
		return null;
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @param content DOCUMENT ME!
	 */
	public void setContent(final String content) {
		this.content = content;
	} // end setContent()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DocumentType#getEntities()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public NamedNodeMap getEntities() {
		return nnmEntities;
	} // end getEntities()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DocumentType#getInternalSubset()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getInternalSubset() {
		return internalSubset;
	} // end getInternalSubset()

	/**
	 * DOCUMENT ME!
	 *
	 * @param knownElements The knownElements to set.
	 */
	public void setKnownElements(final Map knownElements) {
		this.knownElements     = knownElements;
		nnmElements			   = new CNamedNodeMap(this);

		for (Iterator it = knownElements.entrySet().iterator();
				it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();

			if (entry.getValue() instanceof CNotation) {
				CNotation notation = (CNotation) entry.getValue();
				nnmElements.setNamedItemForce(notation);
				notation.setOwnerDocument(this.ownerDocument);
			} // end if
		} // end for
		nnmElements.setFreeze(true);
	} // end setKnownElements()

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the knownElements.
	 */
	public Map getKnownElements() {
		return knownElements;
	} // end getKnownElements()

	/**
	 * DOCUMENT ME!
	 *
	 * @param knownEntities The knownEntities to set.
	 */
	public void setKnownEntities(final Map knownEntities) {
		this.knownEntities     = knownEntities;
		nnmEntities			   = new CNamedNodeMap(this);

		for (Iterator it = knownEntities.entrySet().iterator();
				it.hasNext();) {
			Map.Entry entry  = (Map.Entry) it.next();
			CEntity   entity = (CEntity) entry.getValue();
			entity.resetOwner(ownerDocument);

			if (entity.getSystemId() == null) {
				entity.setPublicId(getPublicId());
				entity.setSystemId(getSystemId());
			} // end if

			nnmEntities.setNamedItemForce(entity);
			entity.setOwnerDocument(this.ownerDocument);
		} // end for
		nnmEntities.setFreeze(true);
	} // end setKnownEntities()

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the knownEntities.
	 */
	public Map getKnownEntities() {
		return knownEntities;
	} // end getKnownEntities()

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 */
	public void setName(final String name) {
		qualifiedName = name;
		this.name = name;
	} // end setName()

	public NamedNodeMap getAttributes() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DocumentType#getName()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getName() {
		return qualifiedName;
	} // end getName()

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
		return Node.DOCUMENT_TYPE_NODE;
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
		throws DOMException {
		this.content = nodeValue;
	}

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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DocumentType#getNotations()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public NamedNodeMap getNotations() {
		return nnmElements;
	} // end getNotations()

	/**
	 * DOCUMENT ME!
	 *
	 * @param doc DOCUMENT ME!
	 */
	public void setOwnerDocument(final ADocument doc) {
		super.setOwnerDocument(doc);
		for (int i=0;i<nnmElements.getLength();i++) {
			CNotation notation = (CNotation)nnmElements.item(i);
			notation.setOwnerDocument(doc);
		}
		for (int i=0;i<nnmEntities.getLength();i++) {
			CEntity entity = (CEntity)nnmEntities.item(i);
			entity.setOwnerDocument(doc);
		}
	} // end setOwnerDocument()

	public final String getContent() {
		return content;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DocumentType#getPublicId()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPublicId() {
		return publicId;
	} // end getPublicId()

	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.DocumentType#getSystemId()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getSystemId() {
		return systemId;
	} // end getSystemId()

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		if (content == null) {
			return "";
		}

		CStringBuilder result = new CStringBuilder();
		result.append("<");
		result.append(content);
		result.append(">");

		return result.toString();
	} // end toString()

	/**
	 * Patch by Jarle H. Næss, Internal subset was returning null
	 *
	 * @param content DOCUMENT ME!
	 */
	private void createInternalSubset(final String content) {
		if (content == null)
			internalSubset = null;
		if ((content.length() > 0) && (content.indexOf("[") > 0)) {
			int firstIndex = content.indexOf("[");
			int lastIndex = content.lastIndexOf("]");
			internalSubset = content.substring(firstIndex + 1, lastIndex);
		} // end if
	} // end createInternalSubset()
} // end CDocType
