package tec.uom.se.spi;

import tec.uom.se.ComparableQuantity;

import javax.measure.Quantity;


public interface MeasurementComparableQ<Q extends Quantity<Q>> extends Measurement<Q> {

	/**
	 * Returns the measurement quantity.
	 *
	 * @return the quantity.
	 */
	@Override
	ComparableQuantity<Q> getQuantity();

}
