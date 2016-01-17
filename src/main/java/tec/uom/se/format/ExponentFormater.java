/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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

import javax.measure.Unit;
import java.io.IOException;

/**
 * Class that has the responsability to exponent format.
 * @author otaviojava
 */
enum ExponentFormater {

    INSTANCE;

    static final char MIDDLE_DOT = '\u00b7'; //$NON-NLS-1$

    /** Exponent 1 character */
    static final char EXPONENT_1 = '\u00b9'; //$NON-NLS-1$

    /** Exponent 2 character */
    static final char EXPONENT_2 = '\u00b2'; //$NON-NLS-1$


    /**
     * Format the given unit raised to the given fractional power to the given
     * <code>StringBuffer</code>.
     *
     * @param unit
     *            Unit the unit to be formatted
     * @param pow
     *            int the numerator of the fractional power
     * @param root
     *            int the denominator of the fractional power
     * @param continued
     *            boolean <code>true</code> if the converter expression should
     *            begin with an operator, otherwise <code>false</code>. This
     *            will always be true unless the unit being modified is equal to
     *            Unit.ONE.
     * @param buffer
     *            StringBuffer the buffer to append to. No assumptions should be
     *            made about its content.
     */
    void formatExponent(Unit<?> unit, int pow, int root,
                                boolean continued, Appendable buffer, SymbolMap symbolMap) throws IOException {

        if (continued) {
            buffer.append(MIDDLE_DOT);
        }
        final StringBuilder temp = new StringBuilder();
        int unitPrecedence = InternalFormater.INSTANCE.formatInternal(unit, temp, symbolMap);

        if (unitPrecedence < InternalFormater.PRODUCT_PRECEDENCE) {
            temp.insert(0, '('); //$NON-NLS-1$
            temp.append(')'); //$NON-NLS-1$
        }
        buffer.append(temp);
        if ((root == 1) && (pow == 1)) {
            // do nothing
        } else if ((root == 1) && (pow > 1)) {
            String powStr = Integer.toString(pow);
            for (int i = 0; i < powStr.length(); i += 1) {
                char c = powStr.charAt(i);
                switch (c) {
                    case '0':
                        buffer.append('\u2070'); //$NON-NLS-1$
                        break;
                    case '1':
                        buffer.append(EXPONENT_1); //$NON-NLS-1$
                        break;
                    case '2':
                        buffer.append(EXPONENT_2);
                        break;
                    case '3':
                        buffer.append('\u00b3'); //$NON-NLS-1$
                        break;
                    case '4':
                        buffer.append('\u2074'); //$NON-NLS-1$
                        break;
                    case '5':
                        buffer.append('\u2075'); //$NON-NLS-1$
                        break;
                    case '6':
                        buffer.append('\u2076'); //$NON-NLS-1$
                        break;
                    case '7':
                        buffer.append('\u2077'); //$NON-NLS-1$
                        break;
                    case '8':
                        buffer.append('\u2078'); //$NON-NLS-1$
                        break;
                    case '9':
                        buffer.append('\u2079'); //$NON-NLS-1$
                        break;
                }
            }
        } else if (root == 1) {
            buffer.append('^'); //$NON-NLS-1$
            buffer.append(String.valueOf(pow));
        } else {
            buffer.append("^("); //$NON-NLS-1$
            buffer.append(String.valueOf(pow));
            buffer.append('/'); //$NON-NLS-1$
            buffer.append(String.valueOf(root));
            buffer.append(')'); //$NON-NLS-1$
        }
    }
}
