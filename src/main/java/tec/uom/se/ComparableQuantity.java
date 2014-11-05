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

import javax.measure.Quantity;

/**
 * Quantity specialized to SE platform.
 * @see {@link Quantity}
 * @author otaviojava
 * @author werner
 * @param <Q>
 */
public interface ComparableQuantity<Q extends Quantity<Q>> extends Quantity<Q>,
		Comparable<Quantity<Q>> {

	/**
	 * Compares two instances of {@link Quantity<Q>}. Conversion of unit can
	 * happen if necessary
	 * 
	 * @param that
	 *            the {@code quantity<Q>} to be compared with this instance.
	 * @return {@code true} if {@code that > this}.
	 * @throws NullPointerException
	 *             if the that is null
	 */
	boolean isGreaterThan(Quantity<Q> that);

	/**
	 * Compares two instances of {@link Quantity<Q>}, doing the conversion of
	 * unit if necessary.
	 * 
	 * @param that
	 *            the {@code quantity<Q>} to be compared with this instance.
	 * @return {@code true} if {@code that >= this}.
	 * @throws NullPointerException
	 *             if the that is null
	 */
	boolean isGreaterThanOrEqualTo(Quantity<Q> that);

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
	boolean isLessThan(Quantity<Q> that);

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
	boolean isLessThanOrEqualTo(Quantity<Q> that);

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
	boolean isEquivalentTo(Quantity<Q> that);

}
