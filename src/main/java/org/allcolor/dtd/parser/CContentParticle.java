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
import org.allcolor.xml.parser.CStringTokenizer;
import org.allcolor.xml.parser.dom.CText;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

//import java.util.StringTokenizer;
import java.util.regex.Pattern;


/**
 * DOCUMENT ME!
 *
 * @author Quentin Anciaux
 */
public class CContentParticle
	implements Serializable,
	Cloneable {
	static final long serialVersionUID = -2032317025113405097L;

	/** DOCUMENT ME! */
	public final static short CARDINAL_00 = 0;

	/** DOCUMENT ME! */
	public final static short CARDINAL_11 = 1;

	/** DOCUMENT ME! */
	public final static short CARDINAL_01 = 2;

	/** DOCUMENT ME! */
	public final static short CARDINAL_1N = 3;

	/** DOCUMENT ME! */
	public final static short CARDINAL_0N = 4;

	/** DOCUMENT ME! */
	public final static short SEQ = 0;

	/** DOCUMENT ME! */
	public final static short CHOICE = 1;

	/** DOCUMENT ME! */
	public final static short NAME = 2;

	/** DOCUMENT ME! */
	private CContentParticle parent;

	/** DOCUMENT ME! */
	private List childCP = new ArrayList();

	/** DOCUMENT ME! */
	private Stack stack = new Stack();

	/** DOCUMENT ME! */
	private String cp;

	/** DOCUMENT ME! */
	private short cardinality = CARDINAL_11;

	/** DOCUMENT ME! */
	private short type = CHOICE;

	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	/**
	 * Creates a new CContentParticle object.
	 *
	 * @param cp DOCUMENT ME!
	 * @param parent DOCUMENT ME!
	 *
	 * @throws DOMException DOCUMENT ME!
	 */
	public CContentParticle(
		final String	 cp,
		final CContentParticle parent) {
		this.parent = parent;

		CStringBuilder cpBuffer = new CStringBuilder().append(cp.trim());
		correctParenthesis(cpBuffer);
		this.cardinality     = calculateCardinality(cpBuffer);
		this.type			 = analyzeType(cpBuffer);
		this.cp				 = cpBuffer.toString();

		if (this.type == NAME) {
			if ((this.cp.indexOf("(") != -1) ||
					(this.cp.indexOf(")") != -1) ||
					(this.cp.indexOf(",") != -1) ||
					(this.cp.indexOf("|") != -1) ||
					(this.cp.indexOf("*") != -1) ||
					(this.cp.indexOf("+") != -1) ||
					(this.cp.indexOf("?") != -1)) {
				analyzeCP();
				/*
				 * use to work... but in fact no... FIXME
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
					"Name content particle is invalid ! :\"" + cp +
					"\"");
					*/
			}
		} // end if
		else {
			analyzeCP();
		}

		if (parent == null) {
			try {
				generateRegExpPattern();
			} // end try
			catch (final Exception e) {
				throw new DOMException(DOMException.SYNTAX_ERR,
					"Content model syntax error !\n" + e.getMessage());
			} // end catch
		} // end if
	} // end CContentParticle()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getCardinality() {
		return cardinality;
	} // end getCardinality()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public List getChildParticles() {
		return childCP;
	} // end getChildParticles()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public CContentParticle getParent() {
		return parent;
	} // end getParent()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getParticleAsString() {
		return cp;
	} // end getParticleAsString()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Stack getRegExp() {
		return (Stack) stack.clone();
	} // end getRegExp()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public short getType() {
		return type;
	} // end getType()

	/**
	 * DOCUMENT ME!
	 *
	 * @param cp DOCUMENT ME!
	 */
	public void appendChild(final CContentParticle cp) {
		childCP.add(cp);
	} // end appendChild()

	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 */
	public static void main(final String args[]) {
		CContentParticle cp = new CContentParticle("(p*,b,f?,#PCDATA?,(d|c)+)",
				null);
		System.out.println("Particle :\n" + cp);

		//System.out.println("gr : "+cp.gr());
		System.out.println("p p p b f d d d c c c complete match " +
			cp.matches("p p p b f d d d c c c", false));
		System.out.println("p p p b f d d d c c c partial match " +
			cp.matches("p p p b f d d d c c c"));
		cp = new CContentParticle("(head,body)", null);
		System.out.println("Particle :\n" + cp);

		//System.out.println("gr : "+cp.gr());
		System.out.println("head complete match " +
			cp.matches("head", false));
		System.out.println("head partial match " + cp.matches("head"));
		cp = new CContentParticle("(b, c) | (b, d)", null);
		System.out.println("Particle :\n" + cp);

		//System.out.println("gr : "+cp.gr());
		System.out.println("b c complete match " +
			cp.matches("b c", false));
		System.out.println("b d complete match " +
			cp.matches("b d", false));
		System.out.println("b c b complete match " +
			cp.matches("b c b", false));
		System.out.println("b d c complete match " +
			cp.matches("b d c", false));
		cp = new CContentParticle("(b, (c | d))", null);
		System.out.println("Particle :\n" + cp);

		//System.out.println("gr : "+cp.gr());
		System.out.println("b c complete match " +
			cp.matches("b c", false));
		System.out.println("b d complete match " +
			cp.matches("b d", false));
		System.out.println("b c b complete match " +
			cp.matches("b c b", false));
		System.out.println("b d c complete match " +
			cp.matches("b d c", false));
		cp = new CContentParticle("((whitemove, blackmove)*, whitemove?)",
				null);
		System.out.println("Particle :\n" + cp);

		//System.out.println("gr : "+cp.gr());
		System.out.println(
			"whitemove blackmove whitemove blackmove complete match " +
			cp.matches(
				"whitemove blackmove whitemove blackmove whitemove",
				false));
		cp = new CContentParticle("(a,(b|c)?,b)", null);
		System.out.println("Particle :\n" + cp);

		//System.out.println("gr : "+cp.gr());
		System.out.println("a b b complete match " +
			cp.matches("a b b", false));
		System.out.println("a c b complete match " +
			cp.matches("a c b", false));
		System.out.println("a b complete match " +
			cp.matches("a b", false));
		System.out.println("a b c b complete match " +
			cp.matches("a b c b", false));
		System.out.println("a b c complete match " +
			cp.matches("a b c", false));
	} // end main()

	/**
	 * DOCUMENT ME!
	 *
	 * @param elements DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean matches(final String elements) {
		return matches(elements, true);
	} // end matches()

	/**
	 * DOCUMENT ME!
	 *
	 * @param elements DOCUMENT ME!
	 * @param partialMatch DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean matches(
		String elements,
		final boolean partialMatch) {
		CContentParticle cp = this;

		if (!elements.endsWith(" ")) {
			elements = elements + " ";
		}

		Stack stack = cp.getRegExp();

		while (stack.size() > 0) {
			Pattern regExp = (Pattern) stack.pop();

			if (regExp.matcher(elements).matches()) {
				return true;
			}

			if (!partialMatch) {
				break;
			}
		} // end while

		return false;
	} // end matches()

	/**
	 * DOCUMENT ME!
	 *
	 * @param elements DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean matches(final List elements) {
		return matches(elements, true);
	} // end matches()

	/**
	 * DOCUMENT ME!
	 *
	 * @param elements DOCUMENT ME!
	 * @param partialMatch DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean matches(
		final List elements,
		final boolean partialMatch) {
		CStringBuilder toMatch = new CStringBuilder();

		for (Iterator it = elements.iterator(); it.hasNext();) {
			Object next = it.next();

			if (next instanceof Node) {
				Node   node = (Node) next;
				String name = node.getNodeName();

				if ((node.getNodeType() != Node.TEXT_NODE) &&
						(node.getNodeType() != Node.ELEMENT_NODE) &&
						(node.getNodeType() != Node.CDATA_SECTION_NODE)) {
					continue;
				} // end if
				else if (node.getNodeType() == Node.TEXT_NODE) {
					if (CText.isElementContentWhitespace((Text) node)) {
						continue;
					}

					name = "#PCDATA";
				} // end else if
				else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
					name = "#PCDATA";
				} // end else if

				toMatch.append(name);
				toMatch.append(" ");
			} // end if
			else if (next instanceof String) {
				toMatch.append(next);
				toMatch.append(" ");
			} // end else if
		} // end for

		return matches(toMatch.toString(), partialMatch);
	} // end matches()

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		int				 tabs = 0;
		CContentParticle part = this;

		while (part.getParent() != null) {
			part = part.getParent();
			tabs++;
		} // end while

		CStringBuilder result = new CStringBuilder();

		for (int i = 0; i < tabs; i++) {
			result.append("\t");
		} // end for

		if (type == CHOICE) {
			result.append("Choice cp : '");
		} else if (type == NAME) {
			result.append("Name cp : '");
		} else {
			result.append("Sequence cp : '");
		}

		result.append(cp);
		result.append("' cardinality : ");

		if (cardinality == CARDINAL_01) {
			result.append("?\n");
		}

		if (cardinality == CARDINAL_0N) {
			result.append("*\n");
		}

		if (cardinality == CARDINAL_1N) {
			result.append("+\n");
		}

		if (cardinality == CARDINAL_11) {
			result.append("1\n");
		}

		for (Iterator it = childCP.iterator(); it.hasNext();) {
			CContentParticle child = (CContentParticle) it.next();
			result.append(child);
		} // end for

		if (this.getParent() == null) {
			Stack stack = getRegExp();
			int   iNum = 0;

			while (stack.size() > 0) {
				result.append("Regexp");
				result.append(iNum++);
				result.append(" : \"");
				result.append(((Pattern) stack.pop()).pattern());
				result.append("\"\n");
			} // end while
		} // end if

		return result.toString();
	} // end toString()

	/**
	 * DOCUMENT ME!
	 *
	 * @param tokenizer DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private static String getRest(final CStringTokenizer tokenizer) {
		CStringBuilder result = new CStringBuilder();

		while (tokenizer.hasMoreTokens()) {
			result.append(tokenizer.nextToken());
		} // end while

		return result.toString();
	} // end getRest()

	/**
	 * DOCUMENT ME!
	 *
	 * @param cpBuffer DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private boolean isSequence(final CStringBuilder cpBuffer) {
		CStringTokenizer tokenizer  = new CStringTokenizer(cpBuffer.toString(),
				"()", true);
		boolean			 isSequence = false;
		int				 iOpen	    = 0;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();

			if (token.equals("(")) {
				iOpen++;
			} else if (token.equals(")")) {
				iOpen--;
			} else if (iOpen == 0) {
				int indexSeparatorPipe = token.indexOf("|");
				int indexSeparatorComa = token.indexOf(",");

				if ((indexSeparatorComa != -1) &&
						(indexSeparatorPipe != -1) &&
						(indexSeparatorComa < indexSeparatorPipe)) {
					isSequence = true;
				} else if ((indexSeparatorComa != -1) &&
						(indexSeparatorPipe == -1)) {
					isSequence = true;
				}

				break;
			} // end else if
		} // end while

		return isSequence;
	} // end isSequence()

	/**
	 * DOCUMENT ME!
	 */
	private void analyzeCP() {
		String delimiter = (getType() == CHOICE)
			? "|"
			: ((getType() == SEQ)
			? ","
			: "");
		String cp = getParticleAsString();

		while (cp.length() > 0) {
			CStringTokenizer tokenizer = new CStringTokenizer(cp,
					"()" + delimiter, true);
			int				 iOpen    = 0;
			boolean			 booBreak = false;
			;

			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();

				if (token.equals("(")) {
					CStringBuilder result = new CStringBuilder();
					result.append("(");

					while ((token.equals("(")) &&
							(tokenizer.hasMoreTokens())) {
						iOpen++;

						if (iOpen > 1) {
							result.append(token);
						}

						token = tokenizer.nextToken();
					} // end while

					while (((!token.equals(")")) || (iOpen > 0)) &&
							(tokenizer.hasMoreTokens())) {
						result.append(token);
						token = tokenizer.nextToken();

						if (token.equals(")")) {
							iOpen--;
						}

						if (token.equals("(")) {
							iOpen++;
						}
					} // end while

					result.append(")");

					if (tokenizer.hasMoreTokens()) {
						token = tokenizer.nextToken();

						if ((token.startsWith("*")) ||
								(token.startsWith("?")) ||
								(token.startsWith("+"))) {
							result.append(token.substring(0, 1));
							cp			 = (token.substring(1) +
								getRest(tokenizer).trim()).trim();
							booBreak     = true;
						} // end if
					} // end if

					String child = result.toString().trim();

					if (!getParticleAsString().equals(child)) {
						CContentParticle nCp = new CContentParticle(child,
								this);
						appendChild(nCp);
					} // end if

					if (booBreak) {
						break;
					}
				} // end if
				else if (!token.equals(delimiter)) {
					if ((!token.equals(cp)) && (!token.equals(""))) {
						CContentParticle nCp = new CContentParticle(token,
								this);
						appendChild(nCp);
					} // end if
				} // end else if
			} // end while

			if (!booBreak) {
				cp = getRest(tokenizer).trim();
			}
		} // end while
	} // end analyzeCP()

	/**
	 * DOCUMENT ME!
	 *
	 * @param cpBuffer DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private short analyzeType(final CStringBuilder cpBuffer) {
		boolean isSequence = isSequence(cpBuffer);
		short   type = NAME;

		if (isSequence) {
			type = SEQ;
		} // end if
		else {
			if (cpBuffer.toString().indexOf("|") != -1) {
				type = CHOICE;
			} // end if
			else {
				type = NAME;
			} // end else
		} // end else

		return type;
	} // end analyzeType()

	/**
	 * DOCUMENT ME!
	 *
	 * @param cpBuffer DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	private short calculateCardinality(final CStringBuilder cpBuffer) {
		short cardinality = CARDINAL_11;

		if ((cpBuffer.toString().startsWith("(")) &&
				(cpBuffer.toString().endsWith(")"))) {
			cpBuffer.delete(0, 1);
			cpBuffer.delete(cpBuffer.length() - 1, cpBuffer.length());
		} // end if
		else if ((cpBuffer.toString().startsWith("(")) &&
				(cpBuffer.toString().endsWith(")*"))) {
			cpBuffer.delete(0, 1);
			cpBuffer.delete(cpBuffer.length() - 2, cpBuffer.length());
			cardinality = CARDINAL_0N;
		} // end else if
		else if ((cpBuffer.toString().startsWith("(")) &&
				(cpBuffer.toString().endsWith(")?"))) {
			cpBuffer.delete(0, 1);
			cpBuffer.delete(cpBuffer.length() - 2, cpBuffer.length());
			cardinality = CARDINAL_01;
		} // end else if
		else if ((cpBuffer.toString().startsWith("(")) &&
				(cpBuffer.toString().endsWith(")+"))) {
			cpBuffer.delete(0, 1);
			cpBuffer.delete(cpBuffer.length() - 2, cpBuffer.length());
			cardinality = CARDINAL_1N;
		} // end else if
		else if (cpBuffer.toString().endsWith("*")) {
			cpBuffer.delete(cpBuffer.length() - 1, cpBuffer.length());
			cardinality = CARDINAL_0N;
		} // end else if
		else if (cpBuffer.toString().endsWith("+")) {
			cpBuffer.delete(cpBuffer.length() - 1, cpBuffer.length());
			cardinality = CARDINAL_1N;
		} // end else if
		else if (cpBuffer.toString().endsWith("?")) {
			cpBuffer.delete(cpBuffer.length() - 1, cpBuffer.length());
			cardinality = CARDINAL_01;
		} // end else if

		return cardinality;
	} // end calculateCardinality()

	/**
	 * DOCUMENT ME!
	 *
	 * @param cpBuffer DOCUMENT ME!
	 */
	private void correctParenthesis(final CStringBuilder cpBuffer) {
		CStringTokenizer tokenizer			   = new CStringTokenizer(cpBuffer.toString(),
				"()", true);
		int				 iOpen				   = 0;
		boolean			 insertOpenAndClosingP = false;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();

			if (token.equals("(")) {
				iOpen++;
			} else if (token.equals(")")) {
				iOpen--;

				if (iOpen < 0) {
					cpBuffer.insert(0, "(".toCharArray(), 0, 1);
					iOpen++;
				} // end if

				if ((iOpen == 0) && (tokenizer.hasMoreTokens())) {
					insertOpenAndClosingP = true;
				} // end if
			} // end else if
			else if (insertOpenAndClosingP) {
				insertOpenAndClosingP = false;

				if ((!token.startsWith("*")) &&
						(!token.startsWith("?")) &&
						(!token.startsWith("+"))) {
					cpBuffer.insert(0, "(".toCharArray(), 0, 1);
					cpBuffer.append(")");
				} // end if
			} // end else if
		} // end while

		while (iOpen > 0) {
			iOpen--;
			cpBuffer.append(")");
		} // end while
	} // end correctParenthesis()

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	private String generateRegExpPattern()
		throws Exception {
		CStringBuilder result = new CStringBuilder();

		if ((getType() == CHOICE) || (getType() == SEQ)) {
			result.append("(");

			for (Iterator it = getChildParticles().iterator();
					it.hasNext();) {
				result.append(((CContentParticle) it.next()).generateRegExpPattern());

				if (getParent() == null) {
					stack.push(Pattern.compile(result.toString() + ")"));
				} // end if

				if ((it.hasNext()) && (getType() == CHOICE)) {
					result.append("|");
				}
			} // end for

			result.append(")");

			if (getCardinality() == CARDINAL_0N) {
				result.append("*");
			} else if (getCardinality() == CARDINAL_1N) {
				result.append("+");
			} else if (getCardinality() == CARDINAL_01) {
				result.append("?");
			}
		} // end if
		else if (getType() == NAME) {
			result.append("(");
			result.append(cp);
			result.append("\\s");
			result.append(")");

			if (getCardinality() == CARDINAL_0N) {
				result.append("*");
			} else if (getCardinality() == CARDINAL_1N) {
				result.append("+");
			} else if (getCardinality() == CARDINAL_01) {
				result.append("?");
			}
		} // end else if

		return result.toString();
	} // end generateRegExpPattern()
} // end CContentParticle
