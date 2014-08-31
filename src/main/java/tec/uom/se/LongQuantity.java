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

import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * An amount of quantity, consisting of a double and a Unit. LongQuantity
 * objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:werner@uom.technology">Werner Keil</a>
 * @param <Q>
 *            The type of the quantity.
 * @version 0.2, $Date: 2014-09-01 $
 */
final class LongQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> {

	final long value;

	public LongQuantity(long value, Unit<Q> unit) {
		super(unit);
		this.value = value;
	}

	@Override
	public Long getValue() {
		return value;
	}

	public double doubleValue(Unit<Q> unit) {
		return (super.getUnit().equals(unit)) ? value : super.getUnit()
				.getConverterTo(unit).convert(value);
	}

	@Override
	public long longValue(Unit<Q> unit) {
		double result = doubleValue(unit);
		if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
			throw new ArithmeticException("Overflow (" + result + ")");
		}
		return (long) result;
	}

	@Override
	public Quantity<Q> add(Quantity<Q> that) {
		return of(value + that.getValue().longValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Quantity<Q> subtract(Quantity<Q> that) {
		return of(value - that.getValue().longValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new LongQuantity(value * that.getValue().longValue(), getUnit());
	}

	@Override
	public Quantity<Q> multiply(Number that) {
		return new LongQuantity(value * that.intValue(), getUnit());
	}

	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new DoubleQuantity((double) value / that.getValue().doubleValue(), getUnit()
				.divide(that.getUnit()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<Q> inverse() {
		return (AbstractQuantity<Q>) of(value, getUnit().inverse());
	}

	@Override
	public Quantity<Q> divide(Number that) {
		return of(value / that.doubleValue(), getUnit());
	}

	@Override
	public boolean isBig() {

		return false;
	}

	@Override
	public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
			throws ArithmeticException {
		return BigDecimal.valueOf(doubleValue(unit));
	}

}