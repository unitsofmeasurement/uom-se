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
package tec.uom.se;

import static javax.measure.format.FormatBehavior.LOCALE_NEUTRAL;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParsePosition;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;

import tec.uom.se.format.MeasurementFormat;
import tec.uom.se.format.QuantityFormat;

/**
 * Singleton class for accessing {@link Quantity} instances.
 * @author werner
 * @author otaviojava
 */
public final class Quantities {
	/**
     * Private singleton constructor.
     */
    private Quantities() {}
    /**
     * Returns the
     * {@link #valueOf(java.math.BigDecimal, javax.measure.unit.Unit) decimal}
     * measure of unknown type corresponding to the specified representation.
     * This method can be used to parse dimensionless quantities.<br/><code>
     *     Measurement<Number, Dimensionless> proportion = Measure.valueOf("0.234").asType(Dimensionless.class);
     * </code>
     *
     * <p> Note: This method handles only
     * {@link javax.measure.unit.UnitFormat#getStandard standard} unit format
     * (<a href="http://unitsofmeasure.org/">UCUM</a> based). Locale-sensitive
     * measure formatting and parsing are handled by the {@link MeasurementFormat}
     * class and its subclasses.</p>
     *
     * @param csq the decimal value and its unit (if any) separated by space(s).
     * @return <code>MeasureFormat.getStandard().parse(csq, new ParsePosition(0))</code>
     */
    public static Quantity<?> getQuantity(CharSequence csq) {
        try {
            return QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq, new ParsePosition(0));
        } catch (IllegalArgumentException | ParserException e) {
            throw new IllegalArgumentException(e); // TODO could we handle this differently?
        }
    }

    /**
     * Returns the scalar measure.
     * in the specified unit.
     * @param value the measurement value.
     * @param unit the measurement unit.
     * @return the corresponding <code>double</code> measure.
     * @throws NullPointerException when value or unit were null
     */
    public static <Q extends Quantity<Q>> Quantity<Q> getQuantity(Number value,
            Unit<Q> unit) {

        Objects.requireNonNull(value);
        Objects.requireNonNull(unit);
        if (BigDecimal.class.isInstance(value)) {
            return new DecimalQuantity<>(BigDecimal.class.cast(value), unit);
        } else if (BigInteger.class.isInstance(value)) {
            return new DecimalQuantity<>(new BigDecimal(
                    BigInteger.class.cast(value)), unit);
        }
        return new DoubleQuantity<>(value.doubleValue(), unit);
    }
}
