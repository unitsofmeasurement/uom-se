package tec.uom.se.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    public void sortNumberTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(QuantityFunctions.sortNumber())
                .collect(Collectors.toList());

        Assert.assertEquals(day, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(seconds, times.get(3));

    }

    @Test
    public void sortNumberDescTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(QuantityFunctions.sortNumberDesc())
                .collect(Collectors.toList());

        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(hours, times.get(1));
        Assert.assertEquals(minutes, times.get(2));
        Assert.assertEquals(day, times.get(3));
    }

    @Test
    public void sortSymbolTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(QuantityFunctions.sortSymbol())
                .collect(Collectors.toList());

        Assert.assertEquals(day, times.get(0));
        Assert.assertEquals(hours, times.get(1));
        Assert.assertEquals(minutes, times.get(2));
        Assert.assertEquals(seconds, times.get(3));

    }

    @Test
    public void sortSymbolDesctTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(QuantityFunctions.sortSymbolDesc())
                .collect(Collectors.toList());
        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(day, times.get(3));
    }

    @Test
    public void sortNaturalTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(QuantityFunctions.sortNatural())
                .collect(Collectors.toList());
        Assert.assertEquals(seconds, times.get(0));
        Assert.assertEquals(minutes, times.get(1));
        Assert.assertEquals(hours, times.get(2));
        Assert.assertEquals(day, times.get(3));
    }

    @Test
    public void sortNaturalDescTest() {
        List<Quantity<Time>> times = getTimes().stream()
                .sorted(QuantityFunctions.sortNaturalDesc())
                .collect(Collectors.toList());

        Assert.assertEquals(day, times.get(0));
        Assert.assertEquals(hours, times.get(1));
        Assert.assertEquals(minutes, times.get(2));
        Assert.assertEquals(seconds, times.get(3));
    }

    @Test
    public void sortNaturalAndSymbolTest() {
        List<Quantity<Time>> times = new ArrayList<>(getTimes());
        Quantity<Time> dayinHour = timeFactory.create(24, SI.HOUR);
        times.add(dayinHour);

        Comparator<Quantity<Time>> sortNatural = QuantityFunctions.sortNatural();
        Comparator<Quantity<Time>> sortSymbol = QuantityFunctions.sortSymbol();

        List<Quantity<Time>> result = times.stream()
                .sorted(sortNatural.thenComparing(sortSymbol))
                .collect(Collectors.toList());
        Assert.assertEquals(seconds, result.get(0));
        Assert.assertEquals(minutes, result.get(1));
        Assert.assertEquals(hours, result.get(2));
        Assert.assertEquals(day, result.get(3));
        Assert.assertEquals(dayinHour, result.get(4));
    }
    private List<Quantity<Time>> getTimes() {
        return Arrays.asList(day, hours, minutes, seconds);
    }
}
