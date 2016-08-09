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
package tec.uom.se;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * An abstract base class for unit systems.
 * </p>
 *
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0, August 9, 2016
 */
public abstract class AbstractSystemOfUnits implements SystemOfUnits {
  /**
   * Holds the units.
   */
  protected final Set<Unit<?>> units = new HashSet<>();

  /**
   * Holds the mapping quantity to unit.
   */
  @SuppressWarnings("rawtypes")
  protected final Map<Class<? extends Quantity>, AbstractUnit> quantityToUnit = new HashMap<>(); // Diamond (Java 7+)

  /**
   * Adds a new named unit to the collection.
   * 
   * @param unit
   *          the unit being added.
   * @param name
   *          the name of the unit.
   * @return <code>unit</code>.
   */
  @SuppressWarnings("unchecked")
  protected <U extends Unit<?>> U addUnit(U unit, String name) {
    if (name != null && unit instanceof AbstractUnit) {
      AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
      aUnit.setName(name);
      units.add(aUnit);
      return (U) aUnit;
    }
    units.add(unit);
    return unit;
  }

  /**
   * The natural logarithm.
   **/
  protected static final double E = 2.71828182845904523536028747135266;

  /*
   * (non-Javadoc)
   * 
   * @see SystemOfUnits#getName()
   */
  public abstract String getName();

  // ///////////////////
  // Collection View //
  // ///////////////////
  @Override
  public Set<Unit<?>> getUnits() {
    return Collections.unmodifiableSet(units);
  }

  @Override
    public Set<? extends Unit<?>> getUnits(Dimension dimension) {
        return this.getUnits().stream().filter(unit -> dimension.equals(unit.getDimension())).collect(Collectors.toSet());
    }

  @SuppressWarnings("unchecked")
  @Override
  public <Q extends Quantity<Q>> AbstractUnit<Q> getUnit(Class<Q> quantityType) {
    return quantityToUnit.get(quantityType);
  }

  protected static class Helper {
    static Set<Unit<?>> getUnitsOfDimension(final Set<Unit<?>> units, 
				Dimension dimension) {
			if (dimension != null) {
				return units.stream().filter(u -> dimension.equals(u.getDimension())).collect(Collectors.toSet());

			}
			return null;
		}

    /**
     * Adds a new named unit to the collection.
     * 
     * @param unit
     *          the unit being added.
     * @param name
     *          the name of the unit.
     * @return <code>unit</code>.
     */
    @SuppressWarnings("unchecked")
    public static <U extends Unit<?>> U addUnit(Set<Unit<?>> units, U unit, String name) {
      if (name != null && unit instanceof AbstractUnit) {
        AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
        aUnit.setName(name);
        units.add(aUnit);
        return (U) aUnit;
      }
      units.add(unit);
      return unit;
    }

    /**
     * Adds a new named unit to the collection.
     * 
     * @param unit
     *          the unit being added.
     * @param name
     *          the name of the unit.
     * @param name
     *          the symbol of the unit.
     * @return <code>unit</code>.
     */
    @SuppressWarnings("unchecked")
    public static <U extends Unit<?>> U addUnit(Set<Unit<?>> units, U unit, String name, String symbol) {
      if (name != null && symbol != null && unit instanceof AbstractUnit) {
        AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
        aUnit.setName(name);
        aUnit.setSymbol(symbol);
        units.add(aUnit);
        return (U) aUnit;
      }
      if (name != null && unit instanceof AbstractUnit) {
        AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
        aUnit.setName(name);
        units.add(aUnit);
        return (U) aUnit;
      }
      units.add(unit);
      return unit;
    }
  }
}
