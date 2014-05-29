/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2010-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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
package org.unitsofmeasurement.impl.util;

import java.math.BigInteger;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.UnitConverter;

import org.unitsofmeasurement.impl.function.ConverterSupplier;
import org.unitsofmeasurement.impl.function.RationalConverter;

/**
 * <p> This class provides support for the 20 SI prefixes used in the metric
 *     system (decimal multiples and submultiples of SI units).
 *     For example:<pre><code>
 *     import static org.unitsofmeasurement.impl.util.SI.*;  // Static import.
 *     import static org.unitsofmeasurement.impl.util.SIPrefix.*; // Static import.
 *     import org.unitsofmeasurement.quantity.*;
 *     ...
 *     Unit<Pressure> HECTOPASCAL = HECTO(PASCAL);
 *     Unit<Length> KILOMETRE = KILO(METRE);
 *     </code></pre>
 * </p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/SI_prefix">Wikipedia: SI Prefix</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.4, $Date: 2014-04-09 $
 */
public enum SIPrefix implements ConverterSupplier {

    YOTTA(new RationalConverter(10^24L, 1L)), // TODO if this works, we could avoid BigInteger for a "normal" range from ATTO to YOCTO
    ZETTA(new RationalConverter(BigInteger.TEN.pow(21), BigInteger.ONE)),
    EXA(new RationalConverter(BigInteger.TEN.pow(18), BigInteger.ONE)),
    PETA(new RationalConverter(BigInteger.TEN.pow(15), BigInteger.ONE)),
    TERA(new RationalConverter(BigInteger.TEN.pow(12), BigInteger.ONE)),
    GIGA(new RationalConverter(BigInteger.TEN.pow(9), BigInteger.ONE)),
    MEGA(new RationalConverter(BigInteger.TEN.pow(6), BigInteger.ONE)),
    KILO(new RationalConverter(BigInteger.TEN.pow(3), BigInteger.ONE)),
    HECTO(new RationalConverter(BigInteger.TEN.pow(2), BigInteger.ONE)),
    DEKA(new RationalConverter(BigInteger.TEN.pow(1), BigInteger.ONE)),
    DECI(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(1))),
    CENTI(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(2))),
    MILLI(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(3))),
    MICRO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(6))),
    NANO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(9))),
    PICO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(12))),
    FEMTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(15))),
    ATTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(18))),
    ZEPTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(21))),
    YOCTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(24)));

    private final RationalConverter converter;

    /**
     * Creates a new prefix.
     *
     * @param converter the associated unit converter.
     */
    private SIPrefix (RationalConverter converter) {
        this.converter = converter;
    }

    /**
     * Returns the corresponding unit converter.
     *
     * @return the unit converter.
     */
    public UnitConverter getConverter() {
        return converter;
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>24</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e24)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> YOTTA(Unit<Q> unit) {
        return unit.transform(YOTTA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>21</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e21)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> ZETTA(Unit<Q> unit) {
        return unit.transform(ZETTA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>18</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e18)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> EXA(Unit<Q> unit) {
        return unit.transform(EXA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>15</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e15)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> PETA(Unit<Q> unit) {
        return unit.transform(PETA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>12</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e12)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> TERA(Unit<Q> unit) {
        return unit.transform(TERA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>9</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e9)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> GIGA(Unit<Q> unit) {
        return unit.transform(GIGA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>6</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e6)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> MEGA(Unit<Q> unit) {
        return unit.transform(MEGA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>3</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e3)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> KILO(Unit<Q> unit) {
        return unit.transform(KILO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>2</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e2)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> HECTO(Unit<Q> unit) {
        return unit.transform(HECTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>1</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e1)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> DEKA(Unit<Q> unit) {
        return unit.transform(DEKA.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-1</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-1)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> DECI(Unit<Q> unit) {
        return unit.transform(DECI.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-2</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-2)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> CENTI(Unit<Q> unit) {
        return unit.transform(CENTI.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-3</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-3)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> MILLI(Unit<Q> unit) {
        return unit.transform(MILLI.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-6</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-6)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> MICRO(Unit<Q> unit) {
        return unit.transform(MICRO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-9</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-9)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> NANO(Unit<Q> unit) {
        return unit.transform(NANO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-12</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-12)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> PICO(Unit<Q> unit) {
        return unit.transform(PICO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-15</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-15)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> FEMTO(Unit<Q> unit) {
        return unit.transform(FEMTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-18</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-18)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> ATTO(Unit<Q> unit) {
        return unit.transform(ATTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-21</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-21)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> ZEPTO(Unit<Q> unit) {
        return unit.transform(ZEPTO.getConverter());
    }

    /**
     * Returns the specified unit multiplied by the factor
     * <code>10<sup>-24</sup></code>
     *
     * @param <Q> The type of the quantity measured by the unit.
     * @param unit any unit.
     * @return <code>unit.times(1e-24)</code>.
     */
    public static <Q extends Quantity<Q>> Unit<Q> YOCTO(Unit<Q> unit) {
        return unit.transform(YOCTO.getConverter());
    }

}
