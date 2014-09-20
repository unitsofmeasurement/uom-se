package tec.uom.se.function;

import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Test;

import tec.uom.se.Quantities;
import tec.uom.se.util.SI;

public class QuantitySummaryStatisticsTest {

    @Test
    public void shouldBeEmpty() {
        QuantitySummaryStatistics<Time> summary = new QuantitySummaryStatistics<>(
                SI.DAY);
        Assert.assertEquals(0L, summary.getCount());
        Assert.assertEquals(0L, summary.getMin().getValue().longValue());
        Assert.assertEquals(0L, summary.getMax().getValue().longValue());
        Assert.assertEquals(0L, summary.getSum().getValue().longValue());
        Assert.assertEquals(0L, summary.getAverage().getValue().longValue());

    }

    @Test(expected = NullPointerException.class)
    public void shouldErrorWhenIsNull() {
        QuantitySummaryStatistics<Time> summary = new QuantitySummaryStatistics<>(
                SI.DAY);
        summary.accept(null);
    }


    @Test
    public void shouldBeSameValueWhenOneMonetaryIsAdded() {
        QuantitySummaryStatistics<Time> summary = new QuantitySummaryStatistics<>(
                SI.DAY);

        summary.accept(Quantities.getQuantity(10, SI.DAY));
        Assert.assertEquals(1L, summary.getCount());
        Assert.assertEquals(10L, summary.getMin().getValue().longValue());
        Assert.assertEquals(10L, summary.getMax().getValue().longValue());
        Assert.assertEquals(10L, summary.getSum().getValue().longValue());
        Assert.assertEquals(10L, summary.getAverage().getValue().longValue());

        Assert.assertEquals(240L, summary.getMin(SI.HOUR).getValue().longValue());
        Assert.assertEquals(240L, summary.getMax(SI.HOUR).getValue().longValue());
        Assert.assertEquals(240L, summary.getSum(SI.HOUR).getValue().longValue());
        Assert.assertEquals(240L, summary.getAverage(SI.HOUR).getValue().longValue());
    }

    @Test
    public void shouldBeSameEquivalentValueWhenisConverted() {
        QuantitySummaryStatistics<Time> summary = new QuantitySummaryStatistics<>(
                SI.DAY);

        summary.accept(Quantities.getQuantity(10, SI.DAY));

        Assert.assertEquals(240L, summary.getMin(SI.HOUR).getValue().longValue());
        Assert.assertEquals(240L, summary.getMax(SI.HOUR).getValue().longValue());
        Assert.assertEquals(240L, summary.getSum(SI.HOUR).getValue().longValue());
        Assert.assertEquals(240L, summary.getAverage(SI.HOUR).getValue().longValue());
    }

    @Test
    public void convertSummaryTest() {

        QuantitySummaryStatistics<Time> summary = new QuantitySummaryStatistics<>(
                SI.DAY);

        summary.accept(Quantities.getQuantity(10, SI.DAY));
        QuantitySummaryStatistics<Time> summaryHour = summary.to(SI.HOUR);
        Assert.assertEquals(240L, summaryHour.getMin().getValue().longValue());
        Assert.assertEquals(240L, summaryHour.getMax().getValue().longValue());
        Assert.assertEquals(240L, summaryHour.getSum().getValue().longValue());
        Assert.assertEquals(240L, summaryHour.getAverage().getValue().longValue());

    }
    @Test
    public void addTest() {
        QuantitySummaryStatistics<Time> summary = createSummaryTime();
        Assert.assertEquals(3L, summary.getCount());
        Assert.assertEquals(1L, summary.getMin().getValue().longValue());
        Assert.assertEquals(9L, summary.getMax().getValue().longValue());
        Assert.assertEquals(12L, summary.getSum().getValue().longValue());
        Assert.assertEquals(4L, summary.getAverage().getValue().longValue());
    }


    @Test
    public void combineTest() {
        QuantitySummaryStatistics<Time> summaryA = createSummaryTime();
        QuantitySummaryStatistics<Time> summaryB = createSummaryTime();
        QuantitySummaryStatistics<Time> summary = summaryA.combine(summaryB);

        Assert.assertEquals(6L, summary.getCount());
        Assert.assertEquals(1L, summary.getMin().getValue().longValue());
        Assert.assertEquals(9L, summary.getMax().getValue().longValue());
        Assert.assertEquals(24L, summary.getSum().getValue().longValue());
        Assert.assertEquals(4L, summary.getAverage().getValue().longValue());
    }

    private QuantitySummaryStatistics<Time> createSummaryTime() {
        QuantitySummaryStatistics<Time> summary = new QuantitySummaryStatistics<>(
                SI.DAY);

        summary.accept(Quantities.getQuantity(9, SI.DAY));
        summary.accept(Quantities.getQuantity(48, SI.HOUR));
        summary.accept(Quantities.getQuantity(1440, SI.MINUTE));
        return summary;
    }


}
