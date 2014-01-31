package org.allcolor.xml.parser.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import org.allcolor.xml.parser.CShaniDomParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CRandomXmlFileGenerator {
	static Random r = new Random(System.currentTimeMillis());
	
	private static int gn(int max) {
		if (max == 0) return 0;
		return Math.abs(r.nextInt()) % max;
	}
	
	static int MAX_NODE = 30;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	throws Exception {
		CShaniDomParser p = new CShaniDomParser();
		for (int i=0;i<100;i++) {
			System.out.println("Creating doc "+i);
			Document doc = p.getDOMImplementation().createDocument(null,null,null);
			Element root = doc.createElement("root");
			doc.appendChild(root);
			System.out.println("Append random elements on doc "+i);
			createDoc(doc,root,MAX_NODE,0);
			File result = new File("/home/qan/tmp/xml/xml_test_"+MAX_NODE+"_"+i+".xml");
			System.out.println("writing : "+result.getAbsolutePath());
			FileOutputStream fout = new FileOutputStream(result);
			fout.write(doc.toString().getBytes("utf-8"));
			fout.close();
		}
	}
	
	private static void createDoc(Document doc,Element elem,int nbnode,int level) {
		if (level > 5) return;
		for (int i=0;i<nbnode;i++) {
			Element child = doc.createElement("child"+gn(100));
			elem.appendChild(child);
			StringBuffer buffer = new StringBuffer();
			buffer.append("\n");
			for (int j=0;j<level;j++) {
				buffer.append("\t");
			}
			child.appendChild(doc.createTextNode(buffer.toString()));
			createDoc(doc,child,gn(MAX_NODE/(level+1)),(level+1));
			child.appendChild(doc.createTextNode("content"+gn(500)));
			child.appendChild(doc.createTextNode(buffer.toString()));
			if (gn(4) == 0) {
				for (int j=0;j<gn(10);j++) {
					child.setAttribute("attr"+j,"value"+gn(100));
				}
			}
		}
	}

}
