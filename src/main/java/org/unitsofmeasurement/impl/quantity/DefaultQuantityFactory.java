package org.unitsofmeasurement.impl.quantity;

import static org.unitsofmeasurement.impl.util.SI.AMPERE;
import static org.unitsofmeasurement.impl.util.SI.AMPERE_TURN;
import static org.unitsofmeasurement.impl.util.SI.BECQUEREL;
import static org.unitsofmeasurement.impl.util.SI.BIT;
import static org.unitsofmeasurement.impl.util.SI.CANDELA;
import static org.unitsofmeasurement.impl.util.SI.COULOMB;
import static org.unitsofmeasurement.impl.util.SI.CUBIC_METRE;
import static org.unitsofmeasurement.impl.util.SI.FARAD;
import static org.unitsofmeasurement.impl.util.SI.GRAY;
import static org.unitsofmeasurement.impl.util.SI.HENRY;
import static org.unitsofmeasurement.impl.util.SI.HERTZ;
import static org.unitsofmeasurement.impl.util.SI.JOULE;
import static org.unitsofmeasurement.impl.util.SI.KATAL;
import static org.unitsofmeasurement.impl.util.SI.KELVIN;
import static org.unitsofmeasurement.impl.util.SI.KILOGRAM;
import static org.unitsofmeasurement.impl.util.SI.LUMEN;
import static org.unitsofmeasurement.impl.util.SI.LUX;
import static org.unitsofmeasurement.impl.util.SI.METRE;
import static org.unitsofmeasurement.impl.util.SI.METRES_PER_SECOND;
import static org.unitsofmeasurement.impl.util.SI.METRES_PER_SQUARE_SECOND;
import static org.unitsofmeasurement.impl.util.SI.MOLE;
import static org.unitsofmeasurement.impl.util.SI.NEWTON;
import static org.unitsofmeasurement.impl.util.SI.OHM;
import static org.unitsofmeasurement.impl.util.SI.PASCAL;
import static org.unitsofmeasurement.impl.util.SI.RADIAN;
import static org.unitsofmeasurement.impl.util.SI.SECOND;
import static org.unitsofmeasurement.impl.util.SI.SIEMENS;
import static org.unitsofmeasurement.impl.util.SI.SIEVERT;
import static org.unitsofmeasurement.impl.util.SI.SQUARE_METRE;
import static org.unitsofmeasurement.impl.util.SI.STERADIAN;
import static org.unitsofmeasurement.impl.util.SI.TESLA;
import static org.unitsofmeasurement.impl.util.SI.VOLT;
import static org.unitsofmeasurement.impl.util.SI.WATT;
import static org.unitsofmeasurement.impl.util.SI.WEBER;

import java.util.HashMap;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Area;
import javax.measure.quantity.CatalyticActivity;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.ElectricCapacitance;
import javax.measure.quantity.ElectricCharge;
import javax.measure.quantity.ElectricConductance;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricInductance;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.ElectricResistance;
import javax.measure.quantity.Energy;
import javax.measure.quantity.Force;
import javax.measure.quantity.Frequency;
import javax.measure.quantity.Illuminance;
import javax.measure.quantity.Information;
import javax.measure.quantity.Length;
import javax.measure.quantity.LuminousFlux;
import javax.measure.quantity.LuminousIntensity;
import javax.measure.quantity.MagneticFlux;
import javax.measure.quantity.MagneticFluxDensity;
import javax.measure.quantity.MagnetomotiveForce;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Power;
import javax.measure.quantity.Pressure;
import javax.measure.quantity.RadiationDoseAbsorbed;
import javax.measure.quantity.RadiationDoseEffective;
import javax.measure.quantity.Radioactivity;
import javax.measure.quantity.SolidAngle;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;
import javax.measure.quantity.Time;
import javax.measure.quantity.Volume;

import org.unitsofmeasurement.impl.BaseQuantity;
import org.unitsofmeasurement.impl.util.SI;

/**
 * The default factory implementation. This factory uses reflection for providing
 * a default implementation for every {@link AbstractQuantity} sub-types.
 *
 * @param <Q> The type of the quantity
 */
class DefaultQuantityFactory<Q extends Quantity<Q>>  extends QuantityFactory<Q> {

    /**
     * The type of the quantities created by this factory.
     */
    @SuppressWarnings("unused")
	private final Class<Q> type;

    /**
     * The metric unit for quantities created by this factory.
     */
    private final Unit<Q> metricUnit;

    /**
     * Creates a new factory for quantities of the given type.
     *
     * @param type The type of the quantities created by this factory.
     */
    @SuppressWarnings("unchecked")
	DefaultQuantityFactory(final Class<Q> type) {
        this.type = type;
        metricUnit = CLASS_TO_METRIC_UNIT.get(type);
    }
    @SuppressWarnings("rawtypes")
	static final HashMap<Class, Unit> CLASS_TO_METRIC_UNIT = new HashMap<>();
    static {
        CLASS_TO_METRIC_UNIT.put(Dimensionless.class, SI.ONE);
        CLASS_TO_METRIC_UNIT.put(ElectricCurrent.class, AMPERE);
        CLASS_TO_METRIC_UNIT.put(LuminousIntensity.class, CANDELA);
        CLASS_TO_METRIC_UNIT.put(Temperature.class, KELVIN);
        CLASS_TO_METRIC_UNIT.put(Mass.class, KILOGRAM);
        CLASS_TO_METRIC_UNIT.put(Length.class, METRE);
        CLASS_TO_METRIC_UNIT.put(AmountOfSubstance.class, MOLE);
        CLASS_TO_METRIC_UNIT.put(Time.class, SECOND);
        CLASS_TO_METRIC_UNIT.put(MagnetomotiveForce.class, AMPERE_TURN);
        CLASS_TO_METRIC_UNIT.put(Angle.class, RADIAN);
        CLASS_TO_METRIC_UNIT.put(SolidAngle.class, STERADIAN);
        CLASS_TO_METRIC_UNIT.put(Information.class, BIT);
        CLASS_TO_METRIC_UNIT.put(Frequency.class, HERTZ);
        CLASS_TO_METRIC_UNIT.put(Force.class, NEWTON);
        CLASS_TO_METRIC_UNIT.put(Pressure.class, PASCAL);
        CLASS_TO_METRIC_UNIT.put(Energy.class, JOULE);
        CLASS_TO_METRIC_UNIT.put(Power.class, WATT);
        CLASS_TO_METRIC_UNIT.put(ElectricCharge.class, COULOMB);
        CLASS_TO_METRIC_UNIT.put(ElectricPotential.class, VOLT);
        CLASS_TO_METRIC_UNIT.put(ElectricCapacitance.class, FARAD);
        CLASS_TO_METRIC_UNIT.put(ElectricResistance.class, OHM);
        CLASS_TO_METRIC_UNIT.put(ElectricConductance.class, SIEMENS);
        CLASS_TO_METRIC_UNIT.put(MagneticFlux.class, WEBER);
        CLASS_TO_METRIC_UNIT.put(MagneticFluxDensity.class, TESLA);
        CLASS_TO_METRIC_UNIT.put(ElectricInductance.class, HENRY);
        CLASS_TO_METRIC_UNIT.put(LuminousFlux.class, LUMEN);
        CLASS_TO_METRIC_UNIT.put(Illuminance.class, LUX);
        CLASS_TO_METRIC_UNIT.put(Radioactivity.class, BECQUEREL);
        CLASS_TO_METRIC_UNIT.put(RadiationDoseAbsorbed.class, GRAY);
        CLASS_TO_METRIC_UNIT.put(RadiationDoseEffective.class, SIEVERT);
        CLASS_TO_METRIC_UNIT.put(CatalyticActivity.class, KATAL);
        CLASS_TO_METRIC_UNIT.put(Speed.class, METRES_PER_SECOND);
        CLASS_TO_METRIC_UNIT.put(Acceleration.class, METRES_PER_SQUARE_SECOND);
        CLASS_TO_METRIC_UNIT.put(Area.class, SQUARE_METRE);
        CLASS_TO_METRIC_UNIT.put(Volume.class, CUBIC_METRE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Q create(final Number value, final Unit<Q> unit) {
        return (Q) new BaseQuantity<>(value, unit);
    }

    @Override
    public Unit<Q> getMetricUnit() {
        return metricUnit;
    }
}
