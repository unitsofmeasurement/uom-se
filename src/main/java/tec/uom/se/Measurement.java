/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
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

import java.time.Instant;

import javax.measure.Quantity;

import tec.uom.se.function.QuantitySupplier;

/**
 *
 * A Measurement contains a {@link Quantity} and a {@linkplain Instant} as timestamp.
 * 
 * <p>
 * A {@code Measurement} object is used for maintaining the tuple of quantity and time-stamp.
 * The value is represented as {@linkplain Quantity}
 * and the time as {@linkplain Instant}
 * <p>

 * 
 * @see {@link QuantitySupplier}
 * @author werner
 * @version 0.1
 * @param <Q>
 */
public interface Measurement<Q extends Quantity<Q>> extends QuantitySupplier<Q>,
		Comparable<Measurement<Q>> {

	/**
	 * Compares two instances of {@link Quantity<Q>}, doing the conversion of
	 * unit if necessary.
	 * 
	 * @param that
	 *            the {@code quantity<Q>} to be compared with this instance.
	 * @return {@code true} if {@code that < this}.
	 * @throws NullPointerException
	 *             if the quantity is null
	 */
	Instant getInstant();
}
