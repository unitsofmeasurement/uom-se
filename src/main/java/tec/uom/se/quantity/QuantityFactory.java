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
package tec.uom.se.quantity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.BiFactory;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.quantity.QuantityFactories.QuantityBuilder;


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
 * @version 0.5.5, $Date: 2014-07-22 $
 */
abstract class QuantityFactory<Q extends Quantity<Q>> implements BiFactory<Number, Unit<Q>, Q> {
	
    /**
     * Holds the current instances.
     */
    @SuppressWarnings("rawtypes")
	private static final Map<Class, QuantityFactory> INSTANCES = new ConcurrentHashMap<>();
    
    /**
     * Returns the default instance for the specified quantity type.
     *
     * @param <Q> The type of the quantity
     * @param type the quantity type
     * @return the quantity factory for the specified type
     */
	public static <Q extends Quantity<Q>>  QuantityFactory<Q> getInstance(final Class<Q> type) {
    	QuantityBuilder builder = QuantityFactories.of(type);
    	return builder.build(type, INSTANCES);
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
        if (!AbstractQuantity.class.isAssignableFrom(type))
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
    

}
