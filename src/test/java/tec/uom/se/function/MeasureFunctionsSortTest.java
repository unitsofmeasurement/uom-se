package tec.uom.se.function;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.measure.function.QuantityFactory;
import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tec.uom.se.quantity.QuantityFactoryProvider;
import tec.uom.se.util.SI;

public class MeasureFunctionsSortTest {

    private QuantityFactory<Time> timeFactory;
    private Quantity<Time> day;
    private Quantity<Time> hours;
    private Quantity<Time> minutes;
    private Quantity<Time> seconds;

    @Before
    public void init() {
        timeFactory = QuantityFactoryProvider.of(Time.class);
        minutes = timeFactory.create(15, SI.MINUTE);
        hours = timeFactory.create(18, SI.HOUR);
        day = timeFactory.create(1, SI.DAY);
        seconds = timeFactory.create(100, SI.SECOND);
    }

    @Test
    public void sortByNumberTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(MeasureFunctions.sortByNumber())
                .collect(Collectors.toList());

        Assert.assertEquals(day, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(seconds, times.get(3));

    }

    @Test
    public void sortByNumberDescTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(MeasureFunctions.sortByNumberDesc())
                .collect(Collectors.toList());

        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(hours, times.get(1));
        Assert.assertEquals(minutes, times.get(2));
        Assert.assertEquals(day, times.get(3));
    }

    @Test
    public void sortBySymbolTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(MeasureFunctions.sortBySymbol())
                .collect(Collectors.toList());

        Assert.assertEquals(day, times.get(0));
        Assert.assertEquals(hours, times.get(1));
        Assert.assertEquals(minutes, times.get(2));
        Assert.assertEquals(seconds, times.get(3));

    }

    @Test
    public void sortBySymbolDesctTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(MeasureFunctions.sortBySymbolDesc())
                .collect(Collectors.toList());
        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(day, times.get(3));
    }

    @Test
    public void sortNaturalTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(MeasureFunctions.sortNatural())
                .collect(Collectors.toList());
        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(day, times.get(3));
    }

    @Test
    public void sortNaturalDescTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(MeasureFunctions.sortNaturalDesc())
                .collect(Collectors.toList());

        Assert.assertEquals(day, times.get(0));
        Assert.assertEquals(hours, times.get(1));
        Assert.assertEquals(minutes, times.get(2));
        Assert.assertEquals(seconds, times.get(3));
    }

    private List<Quantity<Time>> getTimes() {
        return Arrays.asList(day, hours, minutes, seconds);
    }
}
