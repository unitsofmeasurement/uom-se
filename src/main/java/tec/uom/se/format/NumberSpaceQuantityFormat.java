/*
 * Units of Measurement Implementation for Java SE
 * Copyright © 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.uom.se.format;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import tec.uom.se.ComparableQuantity;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

@SuppressWarnings({"rawtypes", "unchecked"})
class NumberSpaceQuantityFormat extends QuantityFormat {

    private final NumberFormat numberFormat;

    private final UnitFormat unitFormat;

    NumberSpaceQuantityFormat(NumberFormat numberFormat, UnitFormat unitFormat) {
        this.numberFormat = numberFormat;
        this.unitFormat = unitFormat;
    }

    @Override
    public Appendable format(Quantity<?> measure, Appendable dest)
            throws IOException {
            dest.append(numberFormat.format(measure.getValue()));
            if (measure.getUnit().equals(Units.ONE))
                return dest;
            dest.append(' ');
            return unitFormat.format(measure.getUnit(), dest);
    }

    @Override
    public ComparableQuantity<?> parse(CharSequence csq, ParsePosition cursor)
            throws IllegalArgumentException, ParserException {
        String str = csq.toString();
        Number number = numberFormat.parse(str, cursor);
        if (number == null)
            throw new IllegalArgumentException("Number cannot be parsed");

        Unit unit = unitFormat.parse(csq);
        return Quantities.getQuantity(number.longValue(), unit);
    }

    @Override
    public ComparableQuantity<?> parse(CharSequence csq) throws IllegalArgumentException, ParserException {
        return parse(csq, new ParsePosition(0));
    }

    private static final long serialVersionUID = 1L;

}