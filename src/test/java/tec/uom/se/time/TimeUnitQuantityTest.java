package tec.uom.se.time;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Test;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.util.SI;

public class TimeUnitQuantityTest {



    @Test
    public void ofTest() {

        TimeUnitQuantity day = TimeUnitQuantity.of(DAYS, 1);
        TimeUnitQuantity hour = TimeUnitQuantity.of(HOURS, 1);
        TimeUnitQuantity minute = TimeUnitQuantity.of(MINUTES, 1);
        TimeUnitQuantity second = TimeUnitQuantity.of(SECONDS, 1);
        TimeUnitQuantity microSecond = TimeUnitQuantity.of(MICROSECONDS, 1);
        TimeUnitQuantity milliSecond = TimeUnitQuantity.of(MILLISECONDS, 1);
        TimeUnitQuantity nanoSecond = TimeUnitQuantity.of(NANOSECONDS, 1);

        Assert.assertEquals(DAYS, day.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), day.getValue());

        Assert.assertEquals(HOURS, hour.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), hour.getValue());

        Assert.assertEquals(MINUTES, minute.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), minute.getValue());

        Assert.assertEquals(SECONDS, second.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), second.getValue());

        Assert.assertEquals(MICROSECONDS, microSecond.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), microSecond.getValue());

        Assert.assertEquals(MILLISECONDS, milliSecond.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), milliSecond.getValue());

        Assert.assertEquals(NANOSECONDS, nanoSecond.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), nanoSecond.getValue());
    }

    @Test
    public void ofQuantityTest() {
        Quantity<Time> hour = Quantities.getQuantity(1, SI.HOUR);
        TimeUnitQuantity timeQuantity = TimeUnitQuantity.of(hour);

        Assert.assertEquals(TimeUnit.SECONDS, timeQuantity.getTimeUnit());
        Assert.assertEquals(TimeUnits.SECOND, timeQuantity.toUnit());
        Assert.assertEquals(Integer.valueOf(3600), timeQuantity.getValue());
    }

    @Test
    public void toUnitTest() {

        TimeUnitQuantity day = TimeUnitQuantity.of(DAYS, 1);
        TimeUnitQuantity hour = TimeUnitQuantity.of(HOURS, 1);
        TimeUnitQuantity minute = TimeUnitQuantity.of(MINUTES, 1);
        TimeUnitQuantity second = TimeUnitQuantity.of(SECONDS, 1);
        TimeUnitQuantity microSecond = TimeUnitQuantity.of(MICROSECONDS, 1);
        TimeUnitQuantity milliSecond = TimeUnitQuantity.of(MILLISECONDS, 1);
        TimeUnitQuantity nanoSecond = TimeUnitQuantity.of(NANOSECONDS, 1);

        Assert.assertEquals(TimeUnits.DAY, day.toUnit());
        Assert.assertEquals(TimeUnits.HOUR, hour.toUnit());
        Assert.assertEquals(TimeUnits.MINUTE, minute.toUnit());

        Assert.assertEquals(TimeUnits.SECOND, second.toUnit());

        Assert.assertEquals(TimeUnits.MICROSECOND, microSecond.toUnit());
        Assert.assertEquals(TimeUnits.MILLISECOND, milliSecond.toUnit());
        Assert.assertEquals(TimeUnits.NANOSECOND, nanoSecond.toUnit());
    }

    @Test
    public void toQuanityTest() {

        TimeUnitQuantity day = TimeUnitQuantity.of(DAYS, 1);
        TimeUnitQuantity hour = TimeUnitQuantity.of(HOURS, 1);
        TimeUnitQuantity minute = TimeUnitQuantity.of(MINUTES, 1);
        TimeUnitQuantity second = TimeUnitQuantity.of(SECONDS, 1);
        TimeUnitQuantity microSecond = TimeUnitQuantity.of(MICROSECONDS, 1);
        TimeUnitQuantity milliSecond = TimeUnitQuantity.of(MILLISECONDS, 1);
        TimeUnitQuantity nanoSecond = TimeUnitQuantity.of(NANOSECONDS, 1);

        verifyQuantity(day.toQuantity(), TimeUnits.DAY, 1);
        verifyQuantity(hour.toQuantity(), TimeUnits.HOUR, 1);
        verifyQuantity(minute.toQuantity(), TimeUnits.MINUTE, 1);
        verifyQuantity(second.toQuantity(), TimeUnits.SECOND, 1);
        verifyQuantity(microSecond.toQuantity(), TimeUnits.MICROSECOND, 1);
        verifyQuantity(milliSecond.toQuantity(), TimeUnits.MILLISECOND, 1);
        verifyQuantity(nanoSecond.toQuantity(), TimeUnits.NANOSECOND, 1);

    }

    @Test
    public void convertTest() {

        TimeUnitQuantity day = TimeUnitQuantity.of(DAYS, 1);
        TimeUnitQuantity hours = day.to(TimeUnit.HOURS);

        Assert.assertEquals(TimeUnit.HOURS, hours.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(24), hours.getValue());

        TimeUnitQuantity oneDay = hours.to(TimeUnit.DAYS);
        Assert.assertEquals(TimeUnit.DAYS, oneDay.getTimeUnit());
        Assert.assertEquals(Integer.valueOf(1), oneDay.getValue());
    }

    private void verifyQuantity(Quantity<Time> quantity, Unit<Time> unit, Number number) {
        Assert.assertEquals(unit, quantity.getUnit());
        Assert.assertEquals(Integer.valueOf(number.intValue()), Integer.valueOf(quantity.getValue().intValue()));
    }
}
