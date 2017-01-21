/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2017, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.uom.se.unit;

import java.util.Map;
import java.util.Objects;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import tec.uom.lib.common.function.UnitConverterSupplier;
import tec.uom.se.AbstractUnit;

/**
 * <p>
 * This class represents the units derived from other units using {@linkplain UnitConverter converters}.
 * </p>
 *
 * <p>
 * Examples of transformed units:<code>
 *         CELSIUS = KELVIN.shift(273.15);
 *         FOOT = METRE.multiply(3048).divide(10000);
 *         MILLISECOND = MILLI(SECOND);
 *     </code>
 * </p>
 *
 * <p>
 * Transformed units have no symbol. But like any other units, they may have labels attached to them (see
 * {@link javax.measure.format.UnitFormat#label(Unit, String) UnitFormat.label}
 * </p>
 *
 * <p>
 * Instances of this class are created through the {@link AbstractUnit#transform} method.
 * </p>
 *
 * @param <Q>
 *          The type of the quantity measured by this unit.
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.7, December 28, 2015
 */
public final class TransformedUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> implements UnitConverterSupplier {

  /**
	 *
	 */
  private static final long serialVersionUID = 1L;

  /**
   * Holds the parent unit (always a system unit).
   */
  private final AbstractUnit<Q> parentUnit;

  /**
   * Holds the converter to the parent unit.
   */
  private final UnitConverter converter;

  /**
   * Holds the symbol.
   */
  private String symbol;

  /**
   * Creates a transformed unit from the specified system unit. using the parent as symbol
   * 
   * @param parentUnit
   *          the system unit from which this unit is derived.
   * @param converter
   *          the converter to the parent units.
   * @throws IllegalArgumentException
   *           if the specified parent unit is not an {@link AbstractUnit#isSystemUnit() system unit}
   */
  public TransformedUnit(Unit<Q> parentUnit, UnitConverter unitConverter) {
    this(parentUnit.getSymbol(), parentUnit, unitConverter);
  }

  public TransformedUnit(String symbol, Unit<Q> parentUnit, UnitConverter unitConverter) {
    if (parentUnit instanceof AbstractUnit) {
      final AbstractUnit<Q> abParent = (AbstractUnit<Q>) parentUnit;
      if (!abParent.isSystemUnit()) {
        throw new IllegalArgumentException("The parent unit: " + abParent + " is not a system unit");
      }
      this.parentUnit = abParent;
      this.converter = unitConverter;
      this.symbol = symbol;
      // this.symbol = symbol; //TODO see
      // https://github.com/unitsofmeasurement/uom-se/issues/54
    } else {
      throw new IllegalArgumentException("The parent unit: " + parentUnit + " is not an abstract unit.");
    }
  }

  @Override
  public Dimension getDimension() {
    return parentUnit.getDimension();
  }

  @Override
  public UnitConverter getSystemConverter() {
    return parentUnit.getSystemConverter().concatenate(converter);
  }

  @Override
  public UnitConverter getConverter() {
    return converter;
  }

  @Override
  public AbstractUnit<Q> toSystemUnit() {
    return parentUnit.getSystemUnit();
  }

  @Override
  public Map<? extends Unit<?>, Integer> getBaseUnits() {
    return parentUnit.getBaseUnits();
  }

  @Override
  public int hashCode() {
    return Objects.hash(parentUnit, converter);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof TransformedUnit) {
      TransformedUnit<?> other = (TransformedUnit<?>) obj;
      return Objects.equals(parentUnit, other.parentUnit) && Objects.equals(converter, other.converter);
    }
    return false;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }

  public Unit<Q> getParentUnit() {
    return parentUnit;
  }
}
