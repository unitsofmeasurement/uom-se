/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.uom.se.quantity;

import static tec.uom.se.unit.Units.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.*;
import javax.measure.spi.QuantityFactory;

/**
 * A factory producing simple quantities instances (tuples {@link Number}/{@link Unit}).
 *
 * For example:<br/>
 * <code>
 *      Mass m = Quantities.getInstance(Mass.class).create(23.0, KILOGRAM); // 23.0 kg<br/>
 *      Time m = Quantities.getInstance(Time.class).create(124, MILLI(SECOND)); // 124 ms
 * </code>
 * 
 * @param <Q>
 *          The type of the quantity.
 *
 * @author <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:otaviojava@java.net">Otavio Santana</a>
 * @version 0.7.1, $Date: 2015-09-27 $
 */
public class DefaultQuantityFactory<Q extends Quantity<Q>> implements QuantityFactory<Q> {
  /**
   * The type of the quantities created by this factory.
   */
  private final Class<Q> type;

  /**
   * The metric unit for quantities created by this factory.
   */
  private final Unit<Q> metricUnit;

  @SuppressWarnings("rawtypes")
  static final Map<Class, Unit> CLASS_TO_METRIC_UNIT = new HashMap<>();
  static {
    CLASS_TO_METRIC_UNIT.put(Dimensionless.class, ONE);
    CLASS_TO_METRIC_UNIT.put(ElectricCurrent.class, AMPERE);
    CLASS_TO_METRIC_UNIT.put(LuminousIntensity.class, CANDELA);
    CLASS_TO_METRIC_UNIT.put(Temperature.class, KELVIN);
    CLASS_TO_METRIC_UNIT.put(Mass.class, KILOGRAM);
    CLASS_TO_METRIC_UNIT.put(Length.class, METRE);
    CLASS_TO_METRIC_UNIT.put(AmountOfSubstance.class, MOLE);
    CLASS_TO_METRIC_UNIT.put(Time.class, SECOND);
    CLASS_TO_METRIC_UNIT.put(Angle.class, RADIAN);
    CLASS_TO_METRIC_UNIT.put(SolidAngle.class, STERADIAN);
    CLASS_TO_METRIC_UNIT.put(Frequency.class, HERTZ);
    CLASS_TO_METRIC_UNIT.put(Force.class, NEWTON);
    CLASS_TO_METRIC_UNIT.put(Pressure.class, PASCAL);
    CLASS_TO_METRIC_UNIT.put(Energy.class, JOULE);
    CLASS_TO_METRIC_UNIT.put(Power.class, WATT);
    CLASS_TO_METRIC_UNIT.put(ElectricCharge.class, COULOMB);
    CLASS_TO_METRIC_UNIT.put(ElectricPotential.class, VOLT);
    CLASS_TO_METRIC_UNIT.put(ElectricCapacitance.class, FARAD);
    CLASS_TO_METRIC_UNIT.put(ElectricResistance.class, OHM);
    CLASS_TO_METRIC_UNIT.put(ElectricConductance.class, SIEMENS);
    CLASS_TO_METRIC_UNIT.put(MagneticFlux.class, WEBER);
    CLASS_TO_METRIC_UNIT.put(MagneticFluxDensity.class, TESLA);
    CLASS_TO_METRIC_UNIT.put(ElectricInductance.class, HENRY);
    CLASS_TO_METRIC_UNIT.put(LuminousFlux.class, LUMEN);
    CLASS_TO_METRIC_UNIT.put(Illuminance.class, LUX);
    CLASS_TO_METRIC_UNIT.put(Radioactivity.class, BECQUEREL);
    CLASS_TO_METRIC_UNIT.put(RadiationDoseAbsorbed.class, GRAY);
    CLASS_TO_METRIC_UNIT.put(RadiationDoseEffective.class, SIEVERT);
    CLASS_TO_METRIC_UNIT.put(CatalyticActivity.class, KATAL);
    CLASS_TO_METRIC_UNIT.put(Speed.class, METRES_PER_SECOND);
    CLASS_TO_METRIC_UNIT.put(Acceleration.class, METRES_PER_SQUARE_SECOND);
    CLASS_TO_METRIC_UNIT.put(Area.class, SQUARE_METRE);
    CLASS_TO_METRIC_UNIT.put(Volume.class, CUBIC_METRE);
  }

  @SuppressWarnings("unchecked")
  DefaultQuantityFactory(Class<Q> quantity) {
    type = quantity;
    metricUnit = CLASS_TO_METRIC_UNIT.get(type);
  }

  /**
   * Returns the default instance for the specified quantity type.
   *
   * @param <Q>
   *          The type of the quantity
   * @param type
   *          the quantity type
   * @return the quantity factory for the specified type
   */
  @SuppressWarnings("unchecked")
  public static <Q extends Quantity<Q>> QuantityFactory<Q> getInstance(final Class<Q> type) {
    return new DefaultQuantityFactory(type);
  }

  public String toString() {
    return "tec.uom.se.DefaultQuantityFactory <" + type.getName() + '>';
  }

  public boolean equals(Object obj) {
    if (DefaultQuantityFactory.class.isInstance(obj)) {
      @SuppressWarnings("rawtypes")
      DefaultQuantityFactory other = DefaultQuantityFactory.class.cast(obj);
      return Objects.equals(type, other.type);
    }
    return false;
  }

  public int hashCode() {
    return type.hashCode();
  }

  @SuppressWarnings("unchecked")
  public Quantity<Q> create(Number value, Unit<Q> unit) {
    return (Q) tec.uom.se.quantity.Quantities.getQuantity(value, unit);
  }

  public Unit<Q> getSystemUnit() {
    return metricUnit;
  }
}
