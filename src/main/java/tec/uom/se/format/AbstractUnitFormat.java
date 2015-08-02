/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.uom.se.format;

import java.io.IOException;
import java.text.ParsePosition;

import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import tec.uom.se.AbstractUnit;

/**
 * <p> This class provides the interface for formatting and parsing {@link
 *     AbstractUnit units}.</p>
 *
 * <p> For all metric units, the 20 SI prefixes used to form decimal
 *     multiples and sub-multiples of SI units are recognized.
 *     {@link USCustomary US Customary} units are directly recognized. For example:[code]
 *        Unit.valueOf("m°C").equals(Units.MILLI(Units.CELSIUS))
 *        Unit.valueOf("kW").equals(Units.KILO(Units.WATT))
 *        Unit.valueOf("ft").equals(Units.METRE.multiply(3048).divide(10000))[/code]</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.5.2, $Date: 2015-08-02 $
 * 
 */
public abstract class AbstractUnitFormat implements UnitFormat {

   /**
     * Returns the {@link SymbolMap} for this unit format.
     *
     * @return the symbol map used by this format.
     */
    protected abstract SymbolMap getSymbols();

    /**
     * Formats the specified unit.
     *
     * @param unit the unit to format.
     * @param appendable the appendable destination.
     * @return The appendable destination passed in as {@code appendable},
     *         with formatted text appended.
     * @throws IOException if an error occurs.
     */
    public abstract Appendable format(Unit<?> unit, Appendable appendable)
            throws IOException;
    
    /**
	 * Formats an object to produce a string. This is equivalent to <blockquote>
	 * {@link #format(Unit, StringBuilder) format}<code>(unit,
	 *         new StringBuilder()).toString();</code>
	 * </blockquote>
	 *
	 * @param obj
	 *            The object to format
	 * @return Formatted string.
	 * @exception IllegalArgumentException
	 *                if the Format cannot format the given object
	 */
	public final String format(Unit<?> unit) {
		if (unit instanceof AbstractUnit) {
			return format((AbstractUnit<?>) unit, new StringBuilder())
					.toString();
		} else {
			try {
				return (this.format(unit, new StringBuilder()))
						.toString();
			} catch (IOException ex) {
				throw new ParserException(ex); // Should never happen.
			}
		}
	}

    /**
     * Parses a portion of the specified <code>CharSequence</code> from the
     * specified position to produce a unit. If there is no unit to parse
     * {@link AbstractUnit#ONE} is returned.
     *
     * @param csq the <code>CharSequence</code> to parse.
     * @param cursor the cursor holding the current parsing index.
     * @return the unit parsed from the specified character sub-sequence.
     * @throws IllegalArgumentException if any problem occurs while parsing the
     *         specified character sequence (e.g. illegal syntax).
     */
    protected abstract Unit<?> parse(CharSequence csq, ParsePosition cursor)
            throws IllegalArgumentException;
    
    /**
     * Convenience method equivalent to {@link #format(AbstractUnit, Appendable)}
     * except it does not raise an IOException.
     *
     * @param unit the unit to format.
     * @param dest the appendable destination.
     * @return the specified <code>StringBuilder</code>.
     */
    final StringBuilder format(AbstractUnit<?> unit, StringBuilder dest) {
        try {
            return (StringBuilder) this.format(unit, (Appendable) dest);
        } catch (IOException ex) {
            throw new Error(ex); // Can never happen.
        }
    }

    /**
     * serialVersionUID
     */
//    private static final long serialVersionUID = -2046025267890654321L;
}
