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
/**
 * This package provides supports for physics units, in conformity with the
 * <a href="http://www.unitsofmeasurement.org/">Units of Measurement API</a>.
 *
 *
 * <h3>Usage:</h3>
 * <code>
 *
 * import javax.measure.quantity.*; // Holds quantity types.
 * 
 * import org.unitsofmeasurement.impl.AbstractUnit;
 * import org.unitsofmeasurement.impl.function.AbstractConverter;
 * 
 * import static org.unitsofmeasurement.impl.util.SI.*; // Standard Units.
 * import static org.unitsofmeasurement.impl.util.SIPrefix.*;
 * import static org.unitsofmeasurement.impl.util.UCUM.*; // Standard & Non-Standard Units.
 *
 * public class Main {
 *     public void main(String[] args) {
 *
 *         // Conversion between units (explicit way).
 *         AbstractUnit<Length> sourceUnit = KILO(METRE);
 *         AbstractUnit<Length> targetUnit = MILE;
 *         PhysicsConverter uc = sourceUnit.getConverterTo(targetUnit);
 *         System.out.println(uc.convert(10)); // Converts 10 km to miles.
 *
 *         // Same conversion than above, packed in one line.
 *         System.out.println(KILO(METRE).getConverterTo(MILE).convert(10));
 *
 *         // Retrieval of the SI unit (identifies the measurement type).
 *         System.out.println(REVOLUTION.divide(MINUTE).toSI());
 *
 *         // Dimension checking (allows/disallows conversions)
 *         System.out.println(ELECTRON_VOLT.isCompatible(WATT.times(HOUR)));
 *
 *         // Retrieval of the unit dimension (depends upon the current model).
 *         System.out.println(ELECTRON_VOLT.getDimension());
 *     }
 * }
 *
 * > 6.2137119223733395
 * > 6.2137119223733395
 * > rad/s
 * > true
 * > [L]²·[M]/[T]²
 * </code>
 *
 * <h3>Unit Parameterization</h3>
 *
 *     Units are parameterized enforce compile-time checks of units/measures consistency, for example:[code]
 *
 *     AbstractUnit<Time> MINUTE = SECOND.times(60); // Ok.
 *     AbstractUnit<Time> MINUTE = METRE.times(60); // Compile error.
 *
 *     AbstractUnit<Pressure> HECTOPASCAL = HECTO(PASCAL); // Ok.
 *     AbstractUnit<Pressure> HECTOPASCAL = HECTO(NEWTON); // Compile error.
 *
 *     Quantity<Time> duration = AbstractQuantity.of(2, MINUTE); // Ok.
 *     Quantity<Time> duration = AbstractQuantity.of(2, CELSIUS); // Compile error.
 *
 *     long milliseconds = duration.longValue(MILLI(SECOND)); // Ok.
 *     long milliseconds = duration.longValue(POUND); // Compile error.
 *     [/code]
 *
 *     Runtime checks of dimension consistency can be done for more complex cases.
 *
 *     [code]
 *     AbstractUnit<Area> SQUARE_FOOT = FOOT.times(FOOT).asType(Area.class); // Ok.
 *     AbstractUnit<Area> SQUARE_FOOT = FOOT.times(KELVIN).asType(Area.class); // Runtime error.
 *
 *     AbstractUnit<Temperature> KELVIN = AbstractUnit.of("K").asType(Temperature.class); // Ok.
 *     AbstractUnit<Temperature> KELVIN = AbstractUnit.of("kg").asType(Temperature.class); // Runtime error.
 *     [/code]
 *     </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2
 */
package org.unitsofmeasurement.impl;

