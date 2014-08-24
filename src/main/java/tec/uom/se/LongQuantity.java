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
 * @version 0.1, $Date: 2014-08-24 $
 */
final class LongQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

	final long value;

	public LongQuantity(long value, Unit<T> unit) {
		super(unit);
		this.value = value;
	}

	@Override
	public Long getValue() {
		return value;
	}

	public double doubleValue(Unit<T> unit) {
		return (super.getUnit().equals(unit)) ? value : super.getUnit()
				.getConverterTo(unit).convert(value);
	}

	@Override
	public long longValue(Unit<T> unit) {
		double result = doubleValue(unit);
		if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
			throw new ArithmeticException("Overflow (" + result + ")");
		}
		return (long) result;
	}

	@Override
	public Quantity<T> add(Quantity<T> that) {
		return of(value + that.getValue().longValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Quantity<T> subtract(Quantity<T> that) {
		return of(value - that.getValue().longValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return of(value * that.getValue().longValue(), getUnit());
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value * that.intValue(), getUnit());
	}

	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return of((double) value / that.getValue().doubleValue(), getUnit()
				.divide(that.getUnit()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
	public Quantity<T> divide(Number that) {
		return of(value / that.doubleValue(), getUnit());
	}

	@Override
	public boolean isBig() {

		return false;
	}

	@Override
	public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
			throws ArithmeticException {
		return BigDecimal.valueOf(doubleValue(unit));
	}

}