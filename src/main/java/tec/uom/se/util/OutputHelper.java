/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package tec.uom.se.util;

/**
 * A static helper class, checking e.g. if some tests require optional console
 * output XXX this could have options for using a logging framework or replaced by one
 * @version 0.9.4, $Date: 2014-05-28 $
 * @author Werner Keil
 */
public abstract class OutputHelper {
	static final String CONSOLE_OUTPUT = "consoleOutput"; //$NON-NLS-1$

	public static boolean isConsoleOutput() {
		return ("true".equals(System.getProperty(CONSOLE_OUTPUT))); //$NON-NLS-1$
	}

	public static void print(String message) {
		if (isConsoleOutput()) {
			System.out.print(message);
		}
	}

	public static void println(String message) {
		if (isConsoleOutput()) {
			System.out.println(message);
		}
	}

	public static void print(Object object) {
		print(String.valueOf(object));
	}

	public static void println(Object object) {
		println(String.valueOf(object));
	}

	/**
	 * This is a Fantom-style convenience method for console output
	 */
	public static void echo(Object obj) {
		println(obj);
	}

	/**
	 * This is a Fantom-style convenience method for console output
	 */
	public static void echo(String str) {
		println(str);
	}
}
