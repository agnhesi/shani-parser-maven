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
import org.allcolor.xml.parser.dom.CElement;
/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CHTMLParser {
    /**
     * DOCUMENT ME!
     *
     * @param ownerDocument DOCUMENT ME!
     * @param type DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static CElement createElement(
        ADocument ownerDocument,
        String type,
        int indexSep
    ) {
    	String itype = type.toLowerCase().intern();
    	if (itype == "html") {
            CHTMLElement elem = new CHTMLHtmlElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
    	} else
        if (itype == "p") {
            CHTMLElement elem = new CHTMLPElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "h1") {
            CHTMLElement elem = new CHTMLH1Element(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "h2") {
            CHTMLElement elem = new CHTMLH2Element(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "h3") {
            CHTMLElement elem = new CHTMLH3Element(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "h4") {
            CHTMLElement elem = new CHTMLH4Element(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "h5") {
            CHTMLElement elem = new CHTMLH5Element(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "h6") {
            CHTMLElement elem = new CHTMLH6Element(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "area") {
            CHTMLElement elem = new CHTMLAreaElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "div") {
            CHTMLElement elem = new CHTMLDivElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "ul") {
            CHTMLElement elem = new CHTMLUlElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "ol") {
            CHTMLElement elem = new CHTMLOlElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "li") {
            CHTMLElement elem = new CHTMLLiElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "dl") {
            CHTMLElement elem = new CHTMLDlElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "body") {
            CHTMLElement elem = new CHTMLBodyElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "menu") {
            CHTMLElement elem = new CHTMLMenuElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "dir") {
            CHTMLElement elem = new CHTMLDirectoryElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "pre") {
            CHTMLElement elem = new CHTMLPreElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "hr") {
            CHTMLElement elem = new CHTMLHrElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "blockquote") {
            CHTMLElement elem = new CHTMLBlockquoteElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "dt") {
            CHTMLElement elem = new CHTMLDtElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "dd") {
            CHTMLElement elem = new CHTMLDdElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "address") {
            CHTMLElement elem = new CHTMLAddressElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "center") {
            CHTMLElement elem = new CHTMLCenterElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "noframes") {
            CHTMLElement elem = new CHTMLNoframesElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "isindex") {
            CHTMLElement elem = new CHTMLIsindexElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "fieldset") {
            CHTMLElement elem = new CHTMLFieldsetElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "table") {
            CHTMLElement elem = new CHTMLTableElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "tbody") {
            CHTMLElement elem = new CHTMLTbodyElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "tfoot") {
            CHTMLElement elem = new CHTMLTfootElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "thead") {
            CHTMLElement elem = new CHTMLTheadElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "tr") {
            CHTMLElement elem = new CHTMLTrElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "th") {
            CHTMLElement elem = new CHTMLThElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "td") {
            CHTMLElement elem = new CHTMLTdElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "caption") {
            CHTMLElement elem = new CHTMLCaptionElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "col") {
            CHTMLElement elem = new CHTMLColElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "colgroup") {
            CHTMLElement elem = new CHTMLColgroupElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "form") {
            CHTMLElement elem = new CHTMLFormElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "a") {
            CHTMLElement elem = new CHTMLAElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "br") {
            CHTMLElement elem = new CHTMLBrElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "span") {
            CHTMLElement elem = new CHTMLSpanElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "bdo") {
            CHTMLElement elem = new CHTMLBdoElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "object") {
            CHTMLElement elem = new CHTMLObjectElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "applet") {
            CHTMLElement elem = new CHTMLAppletElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "param") {
            CHTMLElement elem = new CHTMLParamElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "img") {
            CHTMLElement elem = new CHTMLImgElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "map") {
            CHTMLElement elem = new CHTMLMapElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "iframe") {
            CHTMLElement elem = new CHTMLIframeElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "tt") {
            CHTMLElement elem = new CHTMLTtElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "i") {
            CHTMLElement elem = new CHTMLIElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "b") {
            CHTMLElement elem = new CHTMLBElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "u") {
            CHTMLElement elem = new CHTMLUElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "s") {
            CHTMLElement elem = new CHTMLSElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "strike") {
            CHTMLElement elem = new CHTMLStrikeElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "big") {
            CHTMLElement elem = new CHTMLBigElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "small") {
            CHTMLElement elem = new CHTMLSmallElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "font") {
            CHTMLElement elem = new CHTMLFontElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "basefont") {
            CHTMLElement elem = new CHTMLBasefontElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "em") {
            CHTMLElement elem = new CHTMLEmElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "strong") {
            CHTMLElement elem = new CHTMLStrongElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "dfn") {
            CHTMLElement elem = new CHTMLDfnElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "code") {
            CHTMLElement elem = new CHTMLCodeElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "q") {
            CHTMLElement elem = new CHTMLQElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "samp") {
            CHTMLElement elem = new CHTMLSampElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "kbd") {
            CHTMLElement elem = new CHTMLKbdElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "var") {
            CHTMLElement elem = new CHTMLVarElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "cite") {
            CHTMLElement elem = new CHTMLCiteElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "abbr") {
            CHTMLElement elem = new CHTMLAbbrElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "acronym") {
            CHTMLElement elem = new CHTMLAcronymElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "sub") {
            CHTMLElement elem = new CHTMLSubElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "sup") {
            CHTMLElement elem = new CHTMLSupElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "input") {
            CHTMLElement elem = new CHTMLInputElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "select") {
            CHTMLElement elem = new CHTMLSelectElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "optgroup") {
            CHTMLElement elem = new CHTMLOptgroupElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "option") {
            CHTMLElement elem = new CHTMLOptionElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "textarea") {
            CHTMLElement elem = new CHTMLTextareaElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "label") {
            CHTMLElement elem = new CHTMLLabelElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "button") {
            CHTMLElement elem = new CHTMLButtonElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "noscript") {
            CHTMLElement elem = new CHTMLNoscriptElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "ins") {
            CHTMLElement elem = new CHTMLInsElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "del") {
            CHTMLElement elem = new CHTMLDelElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "script") {
            CHTMLElement elem = new CHTMLScriptElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "frameset") {
            CHTMLElement elem = new CHTMLFramesetElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "frame") {
            CHTMLElement elem = new CHTMLFrameElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "title") {
            CHTMLElement elem = new CHTMLTitleElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "head") {
            CHTMLElement elem = new CHTMLHeadElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "meta") {
            CHTMLElement elem = new CHTMLMetaElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "link") {
            CHTMLElement elem = new CHTMLLinkElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "base") {
            CHTMLElement elem = new CHTMLBaseElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "style") {
            CHTMLElement elem = new CHTMLStyleElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else 
        if (itype == "legend") {
            CHTMLElement elem = new CHTMLLegendElement(
                    ownerDocument
                );
            elem.setNameOverride(type);
            return elem;
        } else {
        	if (indexSep == -1) {
	            return new CElement(
	                    type,
	                    ownerDocument
	                );
        	} else {
	            return new CElement(
	                    type,
	                    ownerDocument,
	                    indexSep
	                );
        	}
        }
    }
}