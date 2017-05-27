/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2017, Jean-Marie Dautelle, Werner Keil, V2COM.
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Utility class for number conversions
 */
final class NumberUtils {

  /**
   * Converts a number to {@link BigDecimal}
   *
   * @param value
   *          the value to be converted
   * @return the value converted
   */
  static BigDecimal toBigDecimal(Number value) {
    if (BigDecimal.class.isInstance(value)) {
      return BigDecimal.class.cast(value);
    } else if (BigInteger.class.isInstance(value)) {
      return new BigDecimal(BigInteger.class.cast(value));
    }
    return BigDecimal.valueOf(value.doubleValue());
  }

  /**
   * Check if the both value has equality number, in other words, 1 is equals to 1.0000 and 1.0.
   * 
   * If the first value is a <type>Number</type> of either <type>Double</type>, <type>Float</type>, <type>Integer</type>, <type>Long</type>,
   * <type>Short</type> or <type>Byte</type> it is compared using the respective <code>*value()</code> method of <type>Number</type>. Otherwise it is
   * checked, if {@link BigDecimal#compareTo(Object)} is equal to zero.
   *
   * @param valueA
   *          the value a
   * @param valueB
   *          the value B
   * @return {@link BigDecimal#compareTo(Object)} == zero
   */
  static boolean hasEquality(Number valueA, Number valueB) {
    Objects.requireNonNull(valueA);
    Objects.requireNonNull(valueB);

    if (valueA instanceof Double) {
      return valueA.doubleValue() == valueB.doubleValue();
    } else if (valueA instanceof Float) {
      return valueA.floatValue() == valueB.floatValue();
    } else if (valueA instanceof Integer) {
      return valueA.intValue() == valueB.intValue();
    } else if (valueA instanceof Long) {
      return valueA.longValue() == valueB.longValue();
    } else if (valueA instanceof Short) {
      return valueA.shortValue() == valueB.shortValue();
    } else if (valueA instanceof Byte) {
      return valueA.byteValue() == valueB.byteValue();
    }
    return toBigDecimal(valueA).compareTo(toBigDecimal(valueB)) == 0;
  }
}
