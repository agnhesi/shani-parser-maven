/**
 * 
 */
package org.allcolor.dtd.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.allcolor.xml.parser.dom.CDom2HTMLDocument;

/**
 * @author qan
 *
 */
public class DtdSerializer {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	throws Exception {
		CDocType transitional = CDTDParser
	    .parse(
		    "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">",
		    "http://www.w3.org/TRxhtml1/DTD/xhtml1-transitional.dtd",
		    new CDom2HTMLDocument());
		CDocType frameset = CDTDParser
	    .parse(
			    "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">",
			    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd",
			    new CDom2HTMLDocument());
		File dtd_tr_ser = new File("src/dtd-tr.ser");
		System.out.println(dtd_tr_ser.getAbsolutePath());
		ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(dtd_tr_ser));
		oout.writeObject(transitional);
		oout.close();
		File dtd_fr_ser = new File("src/dtd-fr.ser");
		System.out.println(dtd_fr_ser.getAbsolutePath());
		oout = new ObjectOutputStream(new FileOutputStream(dtd_fr_ser));
		oout.writeObject(frameset);
		oout.close();
		System.out.println("done.");
	}

}
