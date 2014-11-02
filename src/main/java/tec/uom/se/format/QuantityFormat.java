/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
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
import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.Parser;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.util.SI;



/**
 * <p> This class provides the interface for formatting and parsing {@link AbstractQuantity
 *     measurements}.</p>
 *
 * <p> Instances of this class should be able to format measurements stated in
 *     {@link CompoundUnit}. See {@link #formatCompound formatCompound(...)}.
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, $Date: 2014-11-02 $
 */
@SuppressWarnings("rawtypes")
public abstract class QuantityFormat extends Format implements Parser<CharSequence, Quantity> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4628006924354248662L;

	/**
	 * Holds the default format instance.
	 */
	private static final NumberSpaceUnit DEFAULT = new NumberSpaceUnit(
			NumberFormat.getInstance(), LocalUnitFormat.getInstance());

	/**
	 * Holds the standard format instance.
	 */
	private static final Standard STANDARD = new Standard();

	/**
	 * Returns the measure format for the default locale. The default format
	 * assumes the measure is composed of a decimal number and a {@link Unit}
	 * separated by whitespace(s).
	 *
	 * @return <code>MeasureFormat.getInstance(NumberFormat.getInstance(), UnitFormat.getInstance())</code>
	 */
	public static QuantityFormat getInstance() {
		return DEFAULT;
	}

	/**
	 * Returns the measure format using the specified number format and unit
	 * format (the number and unit are separated by one space).
	 *
	 * @param numberFormat the number format.
	 * @param unitFormat the unit format.
	 * @return the corresponding format.
	 */
	public static QuantityFormat getInstance(NumberFormat numberFormat,
			UnitFormat unitFormat) {
		return new NumberSpaceUnit(numberFormat, unitFormat);
	}

	/**
	 * Returns the culture invariant format based upon {@link BigDecimal}
	 * canonical format and the {@link UnitFormat#getStandardInstance() standard} unit
	 * format. This format <b>is not</b> locale-sensitive and can be used for
	 * unambiguous electronic communication of quantities together with their
	 * units without loss of information. For example:
	 * <code>"1.23456789 kg.m/s2"</code> returns
	 * <code>Measure.valueOf(new BigDecimal("1.23456789"), Unit.valueOf("kg.m/s2")));</code>
	 *
	 * @param style the format style to apply.
	 * @return the desired format.
	 */
	public static QuantityFormat getInstance(FormatBehavior style) {
		switch (style) {
		case LOCALE_NEUTRAL:
			return STANDARD;
		case LOCALE_SENSITIVE:
			return DEFAULT;
		default:
			return DEFAULT;
		}
	}

	/**
	 * Formats the specified measure into an <code>Appendable</code>.
	 *
	 * @param measure the measure to format.
	 * @param dest the appendable destination.
	 * @return the specified <code>Appendable</code>.
	 * @throws IOException if an I/O exception occurs.
	 */
	public abstract Appendable format(Quantity<?> measure, Appendable dest)
			throws IOException;

	/**
	 * Parses a portion of the specified <code>CharSequence</code> from the
	 * specified position to produce an object. If parsing succeeds, then the
	 * index of the <code>cursor</code> argument is updated to the index after
	 * the last character used.
	 *
	 * @param csq the <code>CharSequence</code> to parse.
	 * @param cursor the cursor holding the current parsing index.
	 * @return the object parsed from the specified character sub-sequence.
	 * @throws IllegalArgumentException
	 *             if any problem occurs while parsing the specified character
	 *             sequence (e.g. illegal syntax).
	 */
	public abstract Quantity<?> parse(CharSequence csq, ParsePosition cursor)
			throws IllegalArgumentException, ParserException;

	/**
	 * Parses a portion of the specified <code>CharSequence</code> from the
	 * specified position to produce an object. If parsing succeeds, then the
	 * index of the <code>cursor</code> argument is updated to the index after
	 * the last character used.
	 *
	 * @param csq the <code>CharSequence</code> to parse.
	 * @param cursor the cursor holding the current parsing index.
	 * @return the object parsed from the specified character sub-sequence.
	 * @throws IllegalArgumentException
	 *             if any problem occurs while parsing the specified character
	 *             sequence (e.g. illegal syntax).
	 */
	@Override
    public abstract Quantity<?> parse(CharSequence csq)
			throws IllegalArgumentException, ParserException;

	/**
	 * Formats the specified value using {@link CompoundUnit} compound units}.
	 * The default implementation is locale sensitive and does not use space to
	 * separate units. For example:[code]
	 *     Unit<Length> FOOT_INCH = FOOT.compound(INCH);
	 *     Measure<Length> height = Measure.valueOf(1.81, METER);
	 *     System.out.println(height.to(FOOT_INCH));
	 *
	 *     > 5ft11,26in // French Local
	 *
	 *     Unit<Angle> DMS = DEGREE_ANGLE.compound(MINUTE_ANGLE).compound(SECOND_ANGLE);
	 *     Measure<Angle> rotation = Measure.valueOf(35.857497, DEGREE_ANGLE);
	 *     System.out.println(rotation.to(DMS));
	 *
	 *     > 35°51'26,989" // French Local
	 * [/code]
	 *
	 * @param value the value to format using compound units.
	 * @param unit the compound unit.
	 * @param dest the appendable destination.
	 * @return the specified <code>Appendable</code>.
	 * @throws IOException if an I/O exception occurs.
	 */
//	@SuppressWarnings("unchecked")
//	protected Appendable formatCompound(double value, CompoundUnit<?> unit,
//			Appendable dest) throws IOException {
//		Unit high = unit.getHigh();
//		Unit low = unit.getLow(); // The unit in which the value is stated.
//		long highValue = (long) low.getConverterTo(high).convert(value);
//		double lowValue = value - high.getConverterTo(low).convert(highValue);
//		if (high instanceof CompoundUnit)
//			formatCompound(highValue, (CompoundUnit) high, dest);
//		else {
//			dest.append(DEFAULT._numberFormat.format(highValue));
//			DEFAULT._unitFormat.format(high, dest);
//		}
//		dest.append(DEFAULT._numberFormat.format(lowValue));
//		return DEFAULT._unitFormat.format(low, dest);
//	}

	@Override
	public final StringBuffer format(Object obj, final StringBuffer toAppendTo,
			FieldPosition pos) {
		if (!(obj instanceof AbstractQuantity<?>))
			throw new IllegalArgumentException(
					"obj: Not an instance of Measure");
		if ((toAppendTo == null) || (pos == null))
			throw new NullPointerException();
		try {
			return (StringBuffer) format((AbstractQuantity<?>) obj,
                    toAppendTo);
		} catch (IOException ex) {
			throw new Error(ex); // Cannot happen.
		}
	}

	@Override
	public final Quantity<?> parseObject(String source, ParsePosition pos) {
		try {
			return parse(source, pos);
		} catch (IllegalArgumentException | ParserException e) {
			return null; // Unfortunately the message why the parsing failed
		} // is lost; but we have to follow the Format spec.

	}

	/**
	 * Convenience method equivalent to {@link #format(AbstractQuantity, Appendable)}
	 * except it does not raise an IOException.
	 *
	 * @param measure the measure to format.
	 * @param dest the appendable destination.
	 * @return the specified <code>StringBuilder</code>.
	 */
	public final StringBuilder format(AbstractQuantity<?> measure, StringBuilder dest) {
		try {
			return (StringBuilder) this.format(measure, (Appendable) dest);
		} catch (IOException ex) {
			throw new RuntimeException(ex); // Should not happen.
		}
	}

	// Holds default implementation.
	private static final class NumberSpaceUnit extends QuantityFormat {

		private final NumberFormat numberFormat;

		private final UnitFormat unitFormat;

		private NumberSpaceUnit(NumberFormat numberFormat, UnitFormat unitFormat) {
			this.numberFormat = numberFormat;
			this.unitFormat = unitFormat;
		}

		@Override
		public Appendable format(Quantity<?> measure, Appendable dest)
				throws IOException {
//			Unit unit = measure.getUnit();
//			if (unit instanceof CompoundUnit)
//				return formatCompound(measure.doubleValue(unit),
//						(CompoundUnit) unit, dest);
//			else {
				dest.append(numberFormat.format(measure.getValue()));
				if (measure.getUnit().equals(SI.ONE))
					return dest;
				dest.append(' ');
				return unitFormat.format(measure.getUnit(), dest);
//			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Quantity<?> parse(CharSequence csq, ParsePosition cursor)
				throws IllegalArgumentException, ParserException {
			String str = csq.toString();
			Number number = numberFormat.parse(str, cursor);
			if (number == null)
				throw new IllegalArgumentException("Number cannot be parsed");
			Unit unit = unitFormat.parse(csq);
			if (number instanceof BigDecimal) {
				return Quantities.getQuantity((BigDecimal) number, unit);
			}
			if (number instanceof Long) {
				return Quantities.getQuantity(number.longValue(), unit);
			}
			else if (number instanceof Double) {
				return Quantities.getQuantity(number.doubleValue(), unit);
			}
			else if (number instanceof Integer) {
				return Quantities.getQuantity(number.intValue(), unit);
			}
			else {
				throw new UnsupportedOperationException("Number of type "
						+ number.getClass() + " are not supported");
			}
		}

		@Override
        public Quantity<?> parse(CharSequence csq) throws IllegalArgumentException, ParserException {
			return parse(csq, new ParsePosition(0));
		}

		private static final long serialVersionUID = 1L;

	}

	// Holds standard implementation.
	private static final class Standard extends QuantityFormat {

		/**
		 *
		 */
		private static final long serialVersionUID = 2758248665095734058L;

		@Override
        public Appendable format(Quantity measure, Appendable dest)
                throws IOException {
            Unit unit = measure.getUnit();

            dest.append(measure.getValue().toString());
            if (measure.getUnit().equals(SI.ONE))
                return dest;
            dest.append(' ');
            return LocalUnitFormat.getInstance().format(unit, dest);
        }

		@SuppressWarnings("unchecked")
		@Override
		public Quantity<?> parse(CharSequence csq, ParsePosition cursor)
				throws ParserException {
			int startDecimal = cursor.getIndex();
			while ((startDecimal < csq.length())
					&& Character.isWhitespace(csq.charAt(startDecimal))) {
				startDecimal++;
			}
			int endDecimal = startDecimal + 1;
			while ((endDecimal < csq.length())
					&& !Character.isWhitespace(csq.charAt(endDecimal))) {
				endDecimal++;
			}
			BigDecimal decimal = new BigDecimal(csq.subSequence(startDecimal,
					endDecimal).toString());
			cursor.setIndex(endDecimal + 1);
			Unit unit = LocalUnitFormat.getInstance().parse(csq, cursor);
			return Quantities.getQuantity(decimal, unit);
		}

		@Override
        public Quantity<?> parse(CharSequence csq)
				throws ParserException {
			return parse(csq, new ParsePosition(0));
		}
	}
}