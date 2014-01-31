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
package org.allcolor.xml.parser;
import org.allcolor.utils.CCmdLineOptions;

import org.allcolor.xml.parser.dom.ADocument;

import org.w3c.dom.Document;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Notation;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class MyDOMApp {
	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public static void main(final String args[])
		throws Exception {
		if (CCmdLineOptions.isOptionPresent("-url", args)) {
			Document doc = CDocumentBuilderFactory.newParser()
													  .newDocumentBuilder()
													  .parse(CCmdLineOptions.getOptionValue(
							"-url",
							args));
			System.out.println(doc);

			if (doc.getDoctype() != null) {
				System.out.println(doc.getDoctype().getName());
				System.out.println(doc.getDoctype().getPublicId());
				System.out.println(doc.getDoctype().getSystemId());
				System.out.println(((ADocument) doc).getDocumentURI());

				NamedNodeMap entities = doc.getDoctype().getEntities();

				for (int i = 0; i < entities.getLength(); i++) {
					Entity entity = (Entity) entities.item(i);
					System.out.println(entity.getPublicId() + " - " +
						entity.getSystemId());
					System.out.println(entity);
				} // end for

				NamedNodeMap elements = doc.getDoctype().getNotations();

				for (int i = 0; i < elements.getLength(); i++) {
					Notation note = (Notation) elements.item(i);
					System.out.println(note.getPublicId() + " - " +
						note.getSystemId());
					System.out.println(note);
				} // end for
			} // end if
		} // end if
		else {
			printUsage();
		} // end else
	} // end main()

	/**
	 * DOCUMENT ME!
	 */
	private static void printUsage() {
		System.out.println("Usage :");
		System.out.println("\t$MyDOMApp -url SOME_URL");
	} // end printUsage()
} // end MyDOMApp
