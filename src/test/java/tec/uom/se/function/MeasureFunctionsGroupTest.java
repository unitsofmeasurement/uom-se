package tec.uom.se.function;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.QuantityFactory;
import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tec.uom.se.quantity.QuantityFactoryProvider;
import tec.uom.se.util.SI;

public class MeasureFunctionsGroupTest {

    private QuantityFactory<Time> timeFactory;
    private Quantity<Time> day;
    private Quantity<Time> hours;
    private Quantity<Time> minutes;
    private Quantity<Time> seconds;

    @Before
    public void init() {
        timeFactory = QuantityFactoryProvider.getQuantityFactory(Time.class);
        minutes = timeFactory.create(BigDecimal.valueOf(15), SI.MINUTE);
        hours = timeFactory.create(BigDecimal.valueOf(18), SI.HOUR);
        day = timeFactory.create(BigDecimal.ONE, SI.DAY);
        seconds = timeFactory.create(BigDecimal.valueOf(100), SI.SECOND);
    }


    @Test
    public void groupByTest() {
        List<Quantity<Time>> times = createTimes();
        Map<Unit<Time>, List<Quantity<Time>>> timeMap = times.stream().collect(
                Collectors.groupingBy(QuantityFunctions.groupByUnit()));

        Assert.assertEquals(4, timeMap.keySet().size());
        Assert.assertEquals(1, timeMap.get(SI.MINUTE).size());
        Assert.assertEquals(1, timeMap.get(SI.HOUR).size());
        Assert.assertEquals(1, timeMap.get(SI.DAY).size());
        Assert.assertEquals(1, timeMap.get(SI.SECOND).size());

    }

    @Test
    public void groupBsyTest() {
        List<Quantity<Time>> times = createTimes();
        Map<Boolean, List<Quantity<Time>>> timeMap = times.stream().collect(
                Collectors.partitioningBy(QuantityFunctions.fiterByUnit(SI.MINUTE)));

        Assert.assertEquals(2, timeMap.keySet().size());
        Assert.assertEquals(1, timeMap.get(Boolean.TRUE).size());
        Assert.assertEquals(3, timeMap.get(Boolean.FALSE).size());
    }


    @Test
    public void summaryTest() {
        List<Quantity<Time>> times = createTimes();
        QuantitySummaryStatistics<Time> summary = times.stream().collect(QuantityFunctions.summarizingMeasure(SI.HOUR));

        Assert.assertEquals(4, summary.getCount());
        Assert.assertNotNull(summary.getAverage());
        Assert.assertNotNull(summary.getCount());
        Assert.assertNotNull(summary.getMax());
        Assert.assertNotNull(summary.getMin());
        Assert.assertNotNull(summary.getSum());
    }

    private List<Quantity<Time>> createTimes() {
        List<Quantity<Time>> times = new ArrayList<>();
        times.add(day);
        times.add(hours);
        times.add(minutes);
        times.add(seconds);
        return times;
    }

}
