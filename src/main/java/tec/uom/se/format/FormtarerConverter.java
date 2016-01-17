package tec.uom.se.format;

import tec.uom.se.AbstractConverter;
import tec.uom.se.function.*;
import tec.uom.se.unit.MetricPrefix;

import javax.measure.UnitConverter;
import java.math.BigInteger;
import java.util.Formattable;
import java.util.Formatter;

import static java.lang.StrictMath.E;

/**
 * Created by otaviojava on 17/01/16.
 */
enum FormtarerConverter {

    INSTANCE;
    private static final String LocalFormat_Pattern = "%s";

    /**
     * Formats the given converter to the given StringBuilder and returns the
     * operator precedence of the converter's mathematical operation. This is
     * the default implementation, which supports all built-in UnitConverter
     * implementations. Note that it recursively calls itself in the case of a
     * {@link tec.units.ri.AbstractConverter.converter.UnitConverter.Compound
     * Compound} converter.
     *
     * @param converter
     *            the converter to be formatted
     * @param continued
     *            <code>true</code> if the converter expression should begin
     *            with an operator, otherwise <code>false</code>.
     * @param unitPrecedence
     *            the operator precedence of the operation expressed by the unit
     *            being modified by the given converter.
     * @param buffer
     *            the <code>StringBuffer</code> to append to.
     * @return the operator precedence of the given UnitConverter
     */
    int formatConverter(UnitConverter converter, boolean continued,
                                int unitPrecedence, StringBuilder buffer, SymbolMap symbolMap) {
        final MetricPrefix prefix = symbolMap
                .getPrefix((AbstractConverter) converter);
        if ((prefix != null) && (unitPrecedence == InternalFormater.NOOP_PRECEDENCE)) {
            buffer.insert(0, symbolMap.getSymbol(prefix));
            return InternalFormater.NOOP_PRECEDENCE;
        } else if (converter instanceof AddConverter) {
            if (unitPrecedence < InternalFormater.ADDITION_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            double offset = ((AddConverter) converter).getOffset();
            if (offset < 0) {
                buffer.append("-"); //$NON-NLS-1$
                offset = -offset;
            } else if (continued) {
                buffer.append("+"); //$NON-NLS-1$
            }
            long lOffset = (long) offset;
            if (lOffset == offset) {
                buffer.append(lOffset);
            } else {
                buffer.append(offset);
            }
            return InternalFormater.ADDITION_PRECEDENCE;
        } else if (converter instanceof LogConverter) {
            double base = ((LogConverter) converter).getBase();
            StringBuilder expr = new StringBuilder();
            if (base == E) {
                expr.append("ln"); //$NON-NLS-1$
            } else {
                expr.append("log"); //$NON-NLS-1$
                if (base != 10) {
                    expr.append((int) base);
                }
            }
            expr.append("("); //$NON-NLS-1$
            buffer.insert(0, expr);
            buffer.append(")"); //$NON-NLS-1$
            return InternalFormater.EXPONENT_PRECEDENCE;
        } else if (converter instanceof ExpConverter) {
            if (unitPrecedence < InternalFormater.EXPONENT_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            StringBuilder expr = new StringBuilder();
            double base = ((ExpConverter) converter).getBase();
            if (base == E) {
                expr.append('e');
            } else {
                expr.append((int) base);
            }
            expr.append('^');
            buffer.insert(0, expr);
            return InternalFormater.EXPONENT_PRECEDENCE;
        } else if (converter instanceof MultiplyConverter) {
            if (unitPrecedence < InternalFormater.PRODUCT_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            if (continued) {
                buffer.append(ExponentFormater.MIDDLE_DOT);
            }
            double factor = ((MultiplyConverter) converter).getFactor();
            long lFactor = (long) factor;
            if (lFactor == factor) {
                buffer.append(lFactor);
            } else {
                buffer.append(factor);
            }
            return InternalFormater.PRODUCT_PRECEDENCE;
        } else if (converter instanceof RationalConverter) {
            if (unitPrecedence < InternalFormater.PRODUCT_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            RationalConverter rationalConverter = (RationalConverter) converter;
            if (rationalConverter.getDividend() != BigInteger.ONE) {
                if (continued) {
                    buffer.append(ExponentFormater.MIDDLE_DOT);
                }
                buffer.append(rationalConverter.getDividend());
            }
            if (rationalConverter.getDivisor() != BigInteger.ONE) {
                buffer.append('/');
                buffer.append(rationalConverter.getDivisor());
            }
            return InternalFormater.PRODUCT_PRECEDENCE;
        } else if (converter instanceof AbstractConverter.Pair) {
            AbstractConverter.Pair compound = (AbstractConverter.Pair) converter;
            if (compound.getLeft() == AbstractConverter.IDENTITY) {
                return formatConverter(compound.getRight(), true,
                        unitPrecedence, buffer);
            } else {
                if (compound.getLeft() instanceof Formattable) {
                    return formatFormattable((Formattable)compound.getLeft(), unitPrecedence, buffer);
                } else if (compound.getRight() instanceof Formattable) {
                    return formatFormattable((Formattable)compound.getRight(), unitPrecedence, buffer);
                } else {
                    return formatConverter(compound.getLeft(), true,
                            unitPrecedence, buffer);
                    // FIXME use getRight() here, too
                }
            }
            // return formatConverter(compound.getRight(), true,
            // unitPrecedence, buffer);

        } else {
            if (converter != null) {
//				throw new IllegalArgumentException(
//						"Unable to format the given UnitConverter: " + converter.getClass()); //$NON-NLS-1$
                buffer.replace(0, 1, converter.toString());
                return InternalFormater.NOOP_PRECEDENCE;
            } else
                throw new IllegalArgumentException(
                        "Unable to format, no UnitConverter given"); //$NON-NLS-1$
        }
    }

    /**
     * Formats the given <code>Formattable</code> to the given StringBuffer and returns the
     * given precedence of the converter's mathematical operation.
     *
     * @param f
     *            the formattable to be formatted
     * @param unitPrecedence
     *            the operator precedence of the operation expressed by the unit
     *            being modified by the given converter.
     * @param buffer
     *            the <code>StringBuffer</code> to append to.
     * @return the given operator precedence
     */
    private int formatFormattable(Formattable f, int unitPrecedence, StringBuilder buffer) {
        Formatter fmt = new Formatter();
        fmt.format(LocalFormat_Pattern, f);
        buffer.replace(0, 1, fmt.toString());
        fmt.close(); // XXX try Java 7 with res, but for now let's leave J6 compliant
        return unitPrecedence;
    }
}
