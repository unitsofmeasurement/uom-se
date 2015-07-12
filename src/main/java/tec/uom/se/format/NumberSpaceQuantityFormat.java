package tec.uom.se.format;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import tec.uom.se.AbstractUnit;
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