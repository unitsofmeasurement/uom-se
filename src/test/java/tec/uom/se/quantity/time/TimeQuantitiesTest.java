package tec.uom.se.quantity.time;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.TimeUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Test;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.quantity.time.TimeQuantities;
import tec.uom.se.quantity.time.TimeUnitQuantity;
import tec.uom.se.util.SI;

public class TimeQuantitiesTest {



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
        Assert.assertEquals(TimeQuantities.SECOND, timeQuantity.toUnit());
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

        Assert.assertEquals(TimeQuantities.DAY, day.toUnit());
        Assert.assertEquals(TimeQuantities.HOUR, hour.toUnit());
        Assert.assertEquals(TimeQuantities.MINUTE, minute.toUnit());

        Assert.assertEquals(TimeQuantities.SECOND, second.toUnit());

        Assert.assertEquals(TimeQuantities.MICROSECOND, microSecond.toUnit());
        Assert.assertEquals(TimeQuantities.MILLISECOND, milliSecond.toUnit());
        Assert.assertEquals(TimeQuantities.NANOSECOND, nanoSecond.toUnit());
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

        verifyQuantity(day.toQuantity(), TimeQuantities.DAY, 1);
        verifyQuantity(hour.toQuantity(), TimeQuantities.HOUR, 1);
        verifyQuantity(minute.toQuantity(), TimeQuantities.MINUTE, 1);
        verifyQuantity(second.toQuantity(), TimeQuantities.SECOND, 1);
        verifyQuantity(microSecond.toQuantity(), TimeQuantities.MICROSECOND, 1);
        verifyQuantity(milliSecond.toQuantity(), TimeQuantities.MILLISECOND, 1);
        verifyQuantity(nanoSecond.toQuantity(), TimeQuantities.NANOSECOND, 1);

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

    @Test
    public void ofTemporalTest() {

        LocalDate a = Year.of(2015).atMonth(Month.JANUARY).atDay(9);
        LocalDate b = Year.of(2015).atMonth(Month.JANUARY).atDay(10);
        Quantity<Time> time = TimeQuantities.getQuantity(a, b);
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(time.getValue().intValue()));
        Assert.assertEquals(SI.DAY, time.getUnit());
    }

    @Test
    public void ofLocalTimeTest() {

        LocalTime a = LocalTime.of(0, 0);
        LocalTime b = LocalTime.of(12, 0);
        Quantity<Time> time = TimeQuantities.getQuantity(a, b);
        Assert.assertEquals(Double.valueOf(12.0), Double.valueOf(time.getValue().doubleValue()));
        Assert.assertEquals(SI.HOUR, time.getUnit());
    }

    @Test
    public void ofTemporalAdjustTest() {

        LocalDate a = Year.of(2015).atMonth(Month.JANUARY).atDay(9);

        Quantity<Time> time = TimeQuantities.getQuantity(a, () -> TemporalAdjusters.next(DayOfWeek.SUNDAY));
        Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(time.getValue().intValue()));
        Assert.assertEquals(SI.DAY, time.getUnit());
    }

    @Test
    public void ofLocalTimeTemporalAdjustTest() {

        LocalTime a = LocalTime.MIDNIGHT;
        TemporalAdjuster temporalAdjuster = (temporal) -> temporal.plus(12L , ChronoUnit.HOURS);

        Quantity<Time> time = TimeQuantities.getQuantity(a, () -> temporalAdjuster);
        Assert.assertEquals(Integer.valueOf(12), Integer.valueOf(time.getValue().intValue()));
        Assert.assertEquals(SI.HOUR, time.getUnit());
    }
}
