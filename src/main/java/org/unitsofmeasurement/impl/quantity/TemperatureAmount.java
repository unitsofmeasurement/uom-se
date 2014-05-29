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

import javax.measure.Measurement;
import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import org.unitsofmeasurement.impl.BaseQuantity;

/**
 * @author Werner Keil
 * @version 1.5, $Date: 2014-04-03 $
 */
public final class TemperatureAmount extends BaseQuantity<Temperature>
		implements Temperature {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444768963576192753L;

	private final Double scalar; // value in reference unit

	private final Double value; // value in unit (Unit unit)

	public TemperatureAmount(Number number, Unit<Temperature> unit) {
		super(number, unit);
		scalar = (double) 0;
		value = (double) 0;
	}

	public boolean isZero() {
		return (value != null) && 0d == (value);
	}

	public TemperatureAmount add(TemperatureAmount d1) {
		final TemperatureAmount dn = new TemperatureAmount(
                this.value
                        + d1.value, getUnit());
		return dn;
	}

	public TemperatureAmount subtract(TemperatureAmount d1) {
		final TemperatureAmount dn = new TemperatureAmount(
                this.value - d1.value, getUnit());
		return dn;
	}

	protected boolean eq(TemperatureAmount dq) {
		return dq != null && dq.getValue().equals(getValue())
				&& dq.getUnit().equals(getUnit())
				&& dq.getScalar().equals(getScalar());
	}

	boolean ne(TemperatureAmount d1) {
		return ne((TemperatureAmount) d1);
	}

	boolean gt(TemperatureAmount d1) {
		return gt((TemperatureAmount) d1);
	}

	public boolean lt(TemperatureAmount d1) {
		return lt((TemperatureAmount) d1);
	}

	public boolean ge(TemperatureAmount d1) {
		return ge((TemperatureAmount) d1);
	}

	public boolean le(TemperatureAmount d1) {
		return le((TemperatureAmount) d1);
	}

	public TemperatureAmount divide(Double v) {
		return new TemperatureAmount(value / v,
				getUnit());
	}

	//
	// protected TemperatureAmount convert(TemperatureUnit newUnit) {
	// return new TemperatureAmount(value.doubleValue() /
	// newUnit.getFactor(), newUnit);
	// }

	public Double getScalar() {
		return scalar;
	}

	// @Override
	// public String toString(boolean withUnit, boolean withSpace, int
	// precision) {
	// final StringBuilder sb = new StringBuilder();
	// sb.append(getValue());
	// if(withUnit) {
	// if(withSpace) sb.append(" ");
	// sb.append(getUnit().getSymbol());
	// }
	// return sb.toString();
	// }

	// @Override
	// public String showInUnit(Unit<?> u, int precision,
	// SimpleFormat.Show showWith) {
	// return showInUnit(u, value, precision, showWith);
	// }
	//
	// @Override
	// public Number getValue() {
	// return value;
	// }
	//
	// @Override
	// public Unit<Temperature> getUnit() {
	// return unit;
	// }

	@Override
	public BaseQuantity<?> multiply(Number that) {
		return new TemperatureAmount(value * that.doubleValue(),
				getUnit());
	}

	@Override
	public Measurement<?, Number> divide(Number that) {
		return divide((Double) that);
	}

	@Override
	public boolean isBig() {
		return true;
	}

//	@Override
//	public BigDecimal decimalValue(Unit<Temperature> unit, MathContext ctx)
//			throws ArithmeticException {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
