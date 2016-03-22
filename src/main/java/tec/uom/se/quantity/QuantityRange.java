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

import java.util.Objects;

import javax.measure.Quantity;

import tec.uom.se.spi.Range;

/**
 * A Quantity Range is a pair of {@link Quantity} items that represent a range
 * of values.
 * <p>
 * Range limits MUST be presented in the same scale and have the same unit as
 * measured data values.<br/>
 * Subclasses of Range should be immutable.
 * 
 * @param <T>
 *            The value of the range.
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2, December 17, 2014
 * @see <a
 *      href="http://www.botts-inc.com/SensorML_1.0.1/schemaBrowser/SensorML_QuantityRange.html">
 *      SensorML: QuantityRange</a>
 */
public class QuantityRange<Q extends Quantity<Q>> extends Range<Quantity<Q>> {
	private Quantity<Q> res;

	protected QuantityRange(Quantity<Q> min, Quantity<Q> max,
			Quantity<Q> resolution) {
		super(min, max);
		this.res = resolution;
	}

	protected QuantityRange(Quantity<Q> min, Quantity<Q> max) {
		super(min, max);
	}

	/**
	 * Returns an {@code QuantityRange} with the specified values.
	 *
	 * @param minimum
	 *            The minimum value for the measurement range.
	 * @param maximum
	 *            The maximum value for the measurement range.
	 * @param resolution
	 *            The resolution of the measurement range.
	 * @return an {@code MeasurementRange} with the given values
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static QuantityRange of(Quantity minimum, Quantity maximum,
			Quantity resolution) {
		return new QuantityRange(minimum, maximum, resolution);
	}

	/**
	 * Returns the resolution of the measurement range. The value is the same as
	 * that given as the constructor parameter for the largest value.
	 * 
	 * @return resolution of the range, the value is the same as that given as
	 *         the constructor parameter for the resolution
	 */
	public Quantity<Q> getResolution() {
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tec.units.ri.util.Range#contains()
	 */
	@Override
	public boolean contains(Quantity<Q> q) {
		if (q != null && q.getValue() != null) {
			// TODO use hasMinimum() and hasMaximum() to avoid null of the
			// range, too
			if (q.getValue().doubleValue() >= getMinimum().getValue()
					.doubleValue()
					&& q.getValue().doubleValue() <= getMaximum().getValue()
							.doubleValue()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof QuantityRange<?>) {
			@SuppressWarnings("unchecked")
			final QuantityRange<Q> other = (QuantityRange<Q>) obj;
			return Objects.equals(getMinimum(), other.getMinimum())
					&& Objects.equals(getMaximum(), other.getMaximum())
					&& Objects.equals(getResolution(), other.getResolution());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder().append("min= ")
				.append(getMinimum()).append(", max= ").append(getMaximum());
		if (res != null) {
			sb.append(", res= ").append(getResolution());
		}
		return sb.toString();
	}
}
