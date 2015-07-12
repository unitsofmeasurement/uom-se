package tec.uom.se.format;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParsePosition;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;

import tec.uom.se.AbstractUnit;
import tec.uom.se.ComparableQuantity;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

/**
 * Holds standard implementation
 */
@SuppressWarnings("rawtypes")
class DefaultQuantityFormat extends QuantityFormat {

    /**
     *
     */
    private static final long serialVersionUID = 2758248665095734058L;

    @Override
    public Appendable format(Quantity measure, Appendable dest)
            throws IOException {
        Unit unit = measure.getUnit();

        dest.append(measure.getValue().toString());
        if (measure.getUnit().equals(Units.ONE))
            return dest;
        dest.append(' ');
        return LocalUnitFormat.getInstance().format(unit, dest);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ComparableQuantity<?> parse(CharSequence csq, ParsePosition cursor)
            throws ParserException {
        int startDecimal = cursor.getIndex();
        while ((startDecimal < csq.length())
                && Character.isWhitespace(csq.charAt(startDecimal))) {
            startDecimal++;
        }
        int endDecimal = startDecimal + 1;
        while ((endDecimal < csq.length())
                && !Character.isWhitespace(csq.charAt(endDecimal))) {
            endDecimal++;
        }
        BigDecimal decimal = new BigDecimal(csq.subSequence(startDecimal,
                endDecimal).toString());
        cursor.setIndex(endDecimal + 1);
        Unit unit = LocalUnitFormat.getInstance().parse(csq, cursor);
        return Quantities.getQuantity(decimal, unit);
    }

    @Override
    public ComparableQuantity<?> parse(CharSequence csq)
            throws ParserException {
        return parse(csq, new ParsePosition(0));
    }
}