package org.allcolor.xml.parser.dom;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.allcolor.dtd.parser.CDocType;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

public class CTypeInfo 
extends CElement
implements TypeInfo,Serializable {
	static final long serialVersionUID = 4381637120688663474L;
	private final Node owner;
	private final boolean hasXSD;
	private final Document xsd;
	private final CDocType dt;
	
	public CTypeInfo(Node owner, boolean hasXSD) {
		super(owner.getNodeName(),(ADocument)owner.getOwnerDocument());
		this.owner = owner;
		this.hasXSD = hasXSD;
		this.dt = (CDocType)owner.getOwnerDocument().getDoctype();
		if (hasXSD) {
			String namespaceURI = owner.getNamespaceURI();
			if (namespaceURI != null) {
				this.xsd = ((ADocument)owner.getOwnerDocument()).getXSD(namespaceURI);
			} else {
				this.xsd = null;
			}
		} else {
			this.xsd = null;
		}
	}

	private String convert(int type) {
		if (type == org.allcolor.dtd.parser.CElement.CDATA) {
			return "CDATA";
		}
		if (type == org.allcolor.dtd.parser.CElement.ID) {
			return "ID";
		}
		if (type == org.allcolor.dtd.parser.CElement.NMTOKEN) {
			return "NMTOKEN";
		}
		return null;
	}

	private String _GetDtdTypeName(Attr attr) {
		Element ownerElement = attr.getOwnerElement();
		if (ownerElement != null && dt != null) {
			org.allcolor.dtd.parser.CElement elem =
				(org.allcolor.dtd.parser.CElement)dt.getKnownElements().get(ownerElement.getNodeName());
			if (elem != null) {
				Map attrs = elem.getAttributes();
				if (attrs != null) {
					for (Iterator it = attrs.entrySet().iterator();it.hasNext();) {
						Map.Entry entry = (Map.Entry)it.next();
						org.allcolor.dtd.parser.CElement.CAttr at = (org.allcolor.dtd.parser.CElement.CAttr)
							entry.getValue();
						if (at.name.equals(attr.getNodeName())) {
							return convert(at.type);
						}
					}
				}
			}
		}
		return null;
	}
	
//	private Element xsdElem = null;
	
	public String getTypeName() {
		if (!hasXSD || this.xsd == null) {
			if (owner.getNodeType() == Node.ATTRIBUTE_NODE) {
				Attr attr = (Attr)owner;
				return _GetDtdTypeName(attr);
			} else
			if (owner.getNodeType() == Node.ELEMENT_NODE) {
				return null;
			}
			return null;
		} else {
			/*
			if (owner.getNodeType() == Node.ATTRIBUTE_NODE) {
				Attr attr = (Attr)owner;
				String xsdName = null;
				//
				if (this.xsd != null) {
					if (xsdElem == null && attr.getOwnerElement() != null) {
						NodeList nl = this.xsd.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema","element");
						for (int i=0;i<nl.getLength();i++) {
							Element elem = (Element)nl.item(i);
							if (attr.getOwnerElement().getNodeName().equals(elem.getAttribute("name"))) {
								NodeList attributes = elem.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema","attribute");
								for (int j=0;j<attributes.getLength();j++) {
									Element pxsdElem = (Element)attributes.item(j);
									if (attr.getName().equals(pxsdElem.getAttribute("name"))) {
										xsdElem = pxsdElem;
										break;
									}
								}
							}
							if (xsdElem != null)
								break;
						}
					}
					if (xsdElem != null) {
						String type = xsdElem.getAttribute("type");
						if (type.indexOf(":") != -1) {
							xsdName = type.substring(type.indexOf(":")+1);
						} else {
							xsdName = type;
						}
					}
				}
				if (xsdName == null) {
					return _GetDtdTypeName(attr);
				}
				return xsdName;
			}*/
			return null;
		}
	}

	public String getTypeNamespace() {
		if (!hasXSD || this.xsd == null) {
			if (owner.getNodeType() == Node.ATTRIBUTE_NODE) {
				if (this.dt != null && getTypeName() != null)
					return "http://www.w3.org/TR/REC-xml";
				return null;
			} else
			if (owner.getNodeType() == Node.ELEMENT_NODE) {
				return null;
			}
			return null;
		} else {
			/*
			if (owner.getNodeType() == Node.ATTRIBUTE_NODE) {
				String type = getTypeName();
				type = xsdElem != null ? xsdElem.getAttribute("type") : type;
				String xsdNS = null;
				//
				if (type != null && xsdElem != null) {
					if (type.indexOf(":") != -1) {
						String prefix = type.substring(0,type.indexOf(":"));
						xsdNS = xsdElem.lookupNamespaceURI(prefix);
					} else {
						xsdNS = xsdElem.lookupNamespaceURI(null);
					}
				}
				if (xsdNS == null && this.dt != null && type != null)
					return "http://www.w3.org/TR/REC-xml";
				return xsdNS;
			}
			*/
			return null;
		}
	}

	public boolean isDerivedFrom(String typeNamespaceArg, String typeNameArg,
			int derivationMethod) {
		if (!hasXSD || this.xsd == null) {
			return false;
		} else {
			return false;
		}
	}

}
