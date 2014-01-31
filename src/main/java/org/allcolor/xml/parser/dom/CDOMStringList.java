package org.allcolor.xml.parser.dom;

import java.io.Serializable;
import java.util.Map;

import org.w3c.dom.DOMStringList;

public class CDOMStringList implements DOMStringList, Serializable {

	static final long serialVersionUID = -2072769851828120005L;
	private final Map parameters;
	public CDOMStringList(Map parameters) {
		this.parameters = parameters;
	}
	
	public String item(int index) {
		try {
			return (String)parameters.keySet().toArray()[index];
		}
		catch (Exception e) {
			return null;
		}
	}

	public int getLength() {
		return parameters.size();
	}

	public boolean contains(String str) {
		return parameters.containsKey(str);
	}

}
