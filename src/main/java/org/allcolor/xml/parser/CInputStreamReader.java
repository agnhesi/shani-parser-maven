package org.allcolor.xml.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CInputStreamReader extends BufferedReader {
	private final String charsetName;
	public CInputStreamReader(InputStream in, String charsetName) throws UnsupportedEncodingException {
		super(new InputStreamReader(in, charsetName),2048);
		this.charsetName = charsetName;
	}

	public String getEncoding() {
		return charsetName;
	}
	
}
