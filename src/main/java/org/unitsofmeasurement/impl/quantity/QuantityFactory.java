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
package org.unitsofmeasurement.impl.quantity;

import static org.unitsofmeasurement.impl.util.SI.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.BiFactory;
import javax.measure.quantity.*;

import org.unitsofmeasurement.impl.AbstractMeasurement;
import org.unitsofmeasurement.impl.BaseQuantity;
import org.unitsofmeasurement.impl.util.SI;


/**
 * A factory producing simple quantities instances (tuples {@link Number}/{@link Unit}).
 *
 * For example:<br/><code>
 *      Mass m = QuantityFactory.getInstance(Mass.class).create(23.0, KILOGRAM); // 23.0 kg<br/>
 *      Time m = QuantityFactory.getInstance(Time.class).create(124, MILLI(SECOND)); // 124 ms
 * </code>
 * @param <Q> The type of the quantity.
 *
 * @author  <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 0.5.3, $Date: 2014-05-28 $
 */
public abstract class QuantityFactory<Q extends Quantity<Q>> implements BiFactory<Number, Unit<Q>, Q> {
	
    /**
     * Holds the current instances.
     */
    @SuppressWarnings("rawtypes")
	private static final Map<Class, QuantityFactory> INSTANCES = new ConcurrentHashMap<>();
    
    private static final Logger logger = Logger.getLogger(QuantityFactory.class.getName());
    
    private static final Level LOG_LEVEL = Level.FINE;
    
    /**
     * Returns the default instance for the specified quantity type.
     *
     * @param <Q> The type of the quantity
     * @param type the quantity type
     * @return the quantity factory for the specified type
     */
    @SuppressWarnings("unchecked")
	public static <Q extends Quantity<Q>>  QuantityFactory<Q> getInstance(final Class<Q> type) {
        
         logger.log(LOG_LEVEL, "Type: " + type + ": " + type.isInterface());
         QuantityFactory<Q> factory;
         if (!type.isInterface()) {
        	 if (type != null && type.getInterfaces() != null & type.getInterfaces().length > 0) {
	        	 logger.log(LOG_LEVEL, "Type0: " + type.getInterfaces()[0]);
	             Class<?> type2 = type.getInterfaces()[0];
	
	            factory = INSTANCES.get(type2);
	            if (factory != null) return factory;
	            if (!AbstractMeasurement.class.isAssignableFrom(type2))
	                // This exception is not documented because it should never happen if the
	                // user don't try to trick the Java generic types system with unsafe cast.
	                throw new ClassCastException();
	            factory = new Default<Q>((Class<Q>)type2);
	            INSTANCES.put(type2, factory);
        	 } else {
                 factory = INSTANCES.get(type);
                 if (factory != null) return factory;
                 if (!AbstractMeasurement.class.isAssignableFrom(type))
                     // This exception is not documented because it should never happen if the
                     // user don't try to trick the Java generic types system with unsafe cast.
                     throw new ClassCastException();
                 factory = new Default<Q>(type);
                 INSTANCES.put(type, factory);
        	 }
         } else {
            factory = INSTANCES.get(type);
            if (factory != null) return factory;
            if (!Quantity.class.isAssignableFrom(type))
                // This exception is not documented because it should never happen if the
                // user don't try to trick the Java generic types system with unsafe cast.
                throw new ClassCastException();
            factory = new Default<Q>(type);
            INSTANCES.put(type, factory);
         }
        return factory;
    }

    /**
     * Overrides the default implementation of the factory for the specified
     * quantity type.
     *
     * @param <Q> The type of the quantity
     * @param type the quantity type
     * @param factory the quantity factory
     */
    protected static <Q extends Quantity<Q>>  void setInstance(final Class<Q> type, QuantityFactory<Q> factory) {
        if (!AbstractMeasurement.class.isAssignableFrom(type))
            // This exception is not documented because it should never happen if the
            // user don't try to trick the Java generic types system with unsafe cast.
            throw new ClassCastException();
        INSTANCES.put(type, factory);
    }

    /**
     * Returns the quantity for the specified number stated in the specified unit.
     *
     * @param value the value stated in the specified unit
     * @param unit the unit
     * @return the corresponding quantity
     */
    public abstract Q create(Number value, Unit<Q> unit);

    /**
     * Returns the metric unit for quantities produced by this factory
     * or <code>null</code> if unknown.
     *
     * @return the metric units for this factory quantities.
     */
    public abstract Unit<Q> getMetricUnit();
    
    /**
     * The default factory implementation. This factory uses reflection for providing
     * a default implementation for every {@link AbstractMeasurement} sub-types.
     *
     * @param <Q> The type of the quantity
     */
    private static final class Default<Q extends Quantity<Q>>  extends QuantityFactory<Q> {

        /**
         * The type of the quantities created by this factory.
         */
        @SuppressWarnings("unused")
		private final Class<Q> type;

        /**
         * The metric unit for quantities created by this factory.
         */
        private final Unit<Q> metricUnit;

        /**
         * Creates a new factory for quantities of the given type.
         *
         * @param type The type of the quantities created by this factory.
         */
        @SuppressWarnings("unchecked")
		Default(final Class<Q> type) {
            this.type = type;
            metricUnit = CLASS_TO_METRIC_UNIT.get(type);
        }
        @SuppressWarnings("rawtypes")
		static final HashMap<Class, Unit> CLASS_TO_METRIC_UNIT = new HashMap<Class, Unit>();
        static {
            CLASS_TO_METRIC_UNIT.put(Dimensionless.class, SI.ONE);
            CLASS_TO_METRIC_UNIT.put(ElectricCurrent.class, AMPERE);
            CLASS_TO_METRIC_UNIT.put(LuminousIntensity.class, CANDELA);
            CLASS_TO_METRIC_UNIT.put(Temperature.class, KELVIN);
            CLASS_TO_METRIC_UNIT.put(Mass.class, KILOGRAM);
            CLASS_TO_METRIC_UNIT.put(Length.class, METRE);
            CLASS_TO_METRIC_UNIT.put(AmountOfSubstance.class, MOLE);
            CLASS_TO_METRIC_UNIT.put(Time.class, SECOND);
            CLASS_TO_METRIC_UNIT.put(MagnetomotiveForce.class, AMPERE_TURN);
            CLASS_TO_METRIC_UNIT.put(Angle.class, RADIAN);
            CLASS_TO_METRIC_UNIT.put(SolidAngle.class, STERADIAN);
            CLASS_TO_METRIC_UNIT.put(Information.class, BIT);
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
 
        @Override
        @SuppressWarnings("unchecked")
        public Q create(final Number value, final Unit<Q> unit) {
            return (Q) new BaseQuantity<Q>(value, unit);
        }

        @Override
        public Unit<Q> getMetricUnit() {
            return metricUnit;
        }
    }

}
