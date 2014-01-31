package org.allcolor.xml.parser.dom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;

public class CDOMConfiguration implements DOMConfiguration, Serializable {

	private static class CDOMErrorHandler implements DOMErrorHandler,Serializable {
		static final long serialVersionUID = 1652012629768326768L;

		public boolean handleError(DOMError error) {
			return true;
		}
	}
	
	static final long serialVersionUID = -600002160939257470L;
	private Map parameters = new HashMap();
	private DOMStringList list = new CDOMStringList(parameters);
	public static final String SCHEMA_TYPE = "schema-type";
	public static final String CANONICAL_FORM = "canonical-form";
	public static final String ENTITIES = "entities";
	public static final String NORMALIZE_CHARACTERS = "normalize-characters";
	public static final String CDATA_SECTIONS = "cdata-sections";
	public static final String NAMESPACES = "namespaces";
	public static final String NAMESPACE_DECLARATIONS = "namespace-declarations";
	public static final String WELL_FORMED = "well-formed";
	public static final String ELEMENT_CONTENT_WHITESPACE = "element-content-whitespace";
	public static final String CHECK_CHARACTER_NORMALIZATION = "check-character-normalization";
	public static final String COMMENTS = "comments";
	public static final String DATATYPE_NORMALIZATION = "datatype-normalization";
	public static final String ERROR_HANDLER = "error-handler";
	public static final String INFOSET = "infoset";
	public static final String SPLIT_CDATA_SECTIONS = "split-cdata-sections";
	public static final String VALIDATE = "validate";
	public static final String VALIDATE_IF_SCHEMA = "validate-if-schema";
	public CDOMConfiguration() {
		parameters.put(SCHEMA_TYPE,"http://www.w3.org/TR/REC-xml");
		parameters.put(CANONICAL_FORM,Boolean.FALSE);
		parameters.put(CDATA_SECTIONS,Boolean.TRUE);
		parameters.put(NORMALIZE_CHARACTERS,Boolean.FALSE);
		parameters.put(ENTITIES,Boolean.TRUE);
		parameters.put(NAMESPACES,Boolean.TRUE);
		parameters.put(NAMESPACE_DECLARATIONS,Boolean.TRUE);
		parameters.put(WELL_FORMED,Boolean.TRUE);
		parameters.put(CHECK_CHARACTER_NORMALIZATION,Boolean.FALSE);
		parameters.put(COMMENTS,Boolean.TRUE);
		parameters.put(DATATYPE_NORMALIZATION,Boolean.FALSE);
		parameters.put(ELEMENT_CONTENT_WHITESPACE,Boolean.TRUE);
		parameters.put(ERROR_HANDLER,new CDOMErrorHandler());
		parameters.put(INFOSET,Boolean.FALSE);
		parameters.put(SPLIT_CDATA_SECTIONS,Boolean.TRUE);
		parameters.put(VALIDATE,Boolean.FALSE);
		parameters.put(VALIDATE_IF_SCHEMA,Boolean.FALSE);
	}
	
	public void setParameter(String name, Object value) throws DOMException {
		name = name.toLowerCase();
		if (!canSetParameter(name,value)) {
			if (INFOSET.equals(name))
				return;
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,"Cannot set "+name+" to "+value);
		}
		parameters.put(name,value);
		if (CANONICAL_FORM.equals(name)) {
			if (((Boolean)value).booleanValue()) {
				parameters.put(ENTITIES,Boolean.FALSE);
				parameters.put(NORMALIZE_CHARACTERS,Boolean.FALSE);
				parameters.put(CDATA_SECTIONS,Boolean.FALSE);
				parameters.put(NAMESPACES,Boolean.TRUE);
				parameters.put(NAMESPACE_DECLARATIONS,Boolean.TRUE);
				parameters.put(INFOSET,Boolean.TRUE);
				parameters.put(WELL_FORMED,Boolean.TRUE);
				parameters.put(ELEMENT_CONTENT_WHITESPACE,Boolean.TRUE);
			}
		}
		if (ENTITIES.equals(name)) {
			if (((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (NORMALIZE_CHARACTERS.equals(name)) {
			if (((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (CDATA_SECTIONS.equals(name)) {
			if (((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (NAMESPACES.equals(name)) {
			if (!((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (NAMESPACE_DECLARATIONS.equals(name)) {
			if (!((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (WELL_FORMED.equals(name)) {
			if (!((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (ELEMENT_CONTENT_WHITESPACE.equals(name)) {
			if (!((Boolean)value).booleanValue()) {
				parameters.put(CANONICAL_FORM,Boolean.FALSE);
			}
		}
		if (ENTITIES.equals(name)) {
			if (((Boolean)value).booleanValue()) {
				parameters.put(INFOSET,Boolean.FALSE);
			}
		}
		if (INFOSET.equals(name)) {
			if (((Boolean)value).booleanValue()) {
				parameters.put(VALIDATE_IF_SCHEMA,Boolean.FALSE);
				parameters.put(ENTITIES,Boolean.FALSE);
				parameters.put(DATATYPE_NORMALIZATION,Boolean.FALSE);
				parameters.put(CDATA_SECTIONS,Boolean.FALSE);
				parameters.put(NAMESPACE_DECLARATIONS,Boolean.TRUE);
				parameters.put(WELL_FORMED,Boolean.TRUE);
				parameters.put(ELEMENT_CONTENT_WHITESPACE,Boolean.TRUE);
				parameters.put(COMMENTS,Boolean.TRUE);
				parameters.put(NAMESPACES,Boolean.TRUE);
			} else {
				parameters.put(INFOSET,Boolean.TRUE);
			}
		} 
	}

	public Object getParameter(String name) throws DOMException {
		name = name.toLowerCase();
		if (!parameters.containsKey(name))
			throw new DOMException(DOMException.NOT_FOUND_ERR,name+" was not found !");
		return parameters.get(name);
	}

	public boolean canSetParameter(String name, Object value) {
		name = name.toLowerCase();
		if (DATATYPE_NORMALIZATION.equals(name)) {
			if (value instanceof Boolean && (!((Boolean)value).booleanValue())) {
				return true;
			}
			return false;
		} else
		if (VALIDATE.equals(name)) {
			if (value instanceof Boolean && (!((Boolean)value).booleanValue())) {
				return true;
			}
			return false;
		} else
		if (SCHEMA_TYPE.equals(name)) {
			if ("http://www.w3.org/TR/REC-xml".equals(value))
				return true;
			return false;
		}
		if (INFOSET.equals(name)) {
			if (value instanceof Boolean && (!((Boolean)value).booleanValue())) {
				Boolean bool = (Boolean)getParameter(INFOSET);
				if (bool.booleanValue()) return false;
			}
		}

		try {
			Object o = getParameter(name);
			if (o instanceof Boolean && (!(value instanceof Boolean)) )
				return false;
			return true;
		}
		catch (DOMException e) {
			return false;
		}
	}

	public DOMStringList getParameterNames() {
		return list;
	}

}
