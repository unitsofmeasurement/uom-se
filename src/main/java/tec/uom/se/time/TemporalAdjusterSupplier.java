package tec.uom.se.time;

import java.time.temporal.TemporalAdjuster;
import java.util.function.Supplier;

/**
 * Represents a supplier of {@link TemporalAdjuster}
 */
@FunctionalInterface
public interface TemporalAdjusterSupplier extends Supplier<TemporalAdjuster> {

}
