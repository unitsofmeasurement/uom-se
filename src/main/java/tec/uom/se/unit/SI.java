/*
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.uom.se.unit;

import tec.uom.se.*;
import tec.uom.se.function.*;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.*;

/**
 * <p> This class defines all SI (Système International d'Unités) base units and
 *     derived units as well as units that are accepted for use with the
 *     SI units.</p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/International_System_of_Units">Wikipedia: International System of CommonUnits</a>
 * @see <a href="http://physics.nist.gov/cuu/CommonUnits/outside.html>CommonUnits outside the SI that are accepted for use with the SI</a>
 * @see <a href="http://www.bipm.org/utils/common/pdf/si_brochure_8.pdf>SI 2006 - Official Specification</a>
 * @see SIPrefixOld
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.7, June 27, 2015
 * @deprecated see https://java.net/jira/browse/UNITSOFMEASUREMENT-100 relevant units moved to @link Units
*/
public final class SI extends Units {

    /**
     * The singleton instance.
     */
    private static final SI INSTANCE = new SI();

     /**
     * Private constructor (prevents this class from being instantiated).
     */
    private SI() {
    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return the metric system instance.
     */
    public static SI getInstance() {
        return INSTANCE;
    }

    /**
     * The SI unit for electric permittivity quantities (standard name <code>F/m</code>).
     */
    public static final ProductUnit<ElectricPermittivity> FARADS_PER_METRE
            = addUnit(new ProductUnit<ElectricPermittivity>(
            FARAD.divide(METRE)), ElectricPermittivity.class);

    /**
     * The SI unit for luminance quantities (standard name <code>cd/m2</code>).
     */
    public static final ProductUnit<Luminance> CANDELAS_PER_SQUARE_METRE
            = addUnit(new ProductUnit<Luminance>(
            CANDELA.divide(SQUARE_METRE)), Luminance.class);

    /**
     * The SI unit for magnetic field strength quantities (standard name <code>A/m"</code>).
     */
    public static final ProductUnit<MagneticFieldStrength> AMPERES_PER_METRE
            = addUnit(new ProductUnit<MagneticFieldStrength>(
            AMPERE.divide(METRE)), MagneticFieldStrength.class);

    /**
     * A dimensionless unit accepted for use with SI units (standard name <code>Np</code>).
     * Although the neper is coherent with SI units and is accepted by the CIPM,
     * it has not been adopted by the General Conference on Weights and Measures
     * (CGPM, Conférence Générale des Poids et Mesures) and is thus not an SI unit.
     */
    public static final TransformedUnit<Dimensionless> NEPER
        = new TransformedUnit<Dimensionless>(Units.ONE, new LogConverter(E).inverse());

    /**
     * A dimensionless unit accepted for use with SI units (standard name <code>B</code>).
     * The bel is most commonly used with the SI prefix deci: 1 dB = 0.1 B
     */
    public static final TransformedUnit<Dimensionless> BEL
        = new TransformedUnit<Dimensionless>(Units.ONE, new LogConverter(10).inverse());

    /**
     * An energy unit accepted for use with SI units (standard name <code>eV</code>).
     * The electronvolt is the kinetic energy acquired by an electron passing
     * through a potential difference of 1 V in vacuum.
     * The value must be obtained by experiment, and is therefore not known exactly.
     */
    public static final TransformedUnit<Energy> ELECTRON_VOLT
        = new TransformedUnit<Energy>(JOULE, new MultiplyConverter(1.602176487E-19));
        // CODATA 2006 - http://physics.nist.gov/cuu/Constants/codata.pdf

    /**
     * A mass unit accepted for use with SI units (standard name <code>u</code>).
     *  The unified atomic mass unit is equal to 1/12 of the mass of an unbound
     * atom of the nuclide 12C, at rest and in its ground state. The value must
     * be obtained by experiment, and is therefore not known exactly.
     */
    public static final TransformedUnit<Mass> UNIFIED_ATOMIC_MASS
        = new TransformedUnit<Mass>(KILOGRAM, new MultiplyConverter(1.660538782E-27));
        // CODATA 2006 - http://physics.nist.gov/cuu/Constants/codata.pdf

    /**
     * A length unit accepted for use with SI units (standard name <code>UA</code>).
     * The astronomical unit is a unit of length. Its value is such that,
     * when used to describe the motion of bodies in the solar system,
     * the heliocentric gravitation constant is (0.017 202 098 95)2 ua3·d-2.
     * The value must be obtained by experiment, and is therefore not known exactly.
     */
    public static final TransformedUnit<Length> ASTRONOMICAL_UNIT
        = new TransformedUnit<Length>(METRE, new MultiplyConverter(149597871000.0));
        // Best estimate source: http://maia.usno.navy.mil/NSFA/CBE.html

    /**
     *  An angle unit accepted for use with SI units (standard name <code>rev</code>).
     */
    public static final TransformedUnit<Angle> REVOLUTION
            = new TransformedUnit<Angle>(RADIAN, new PiMultiplierConverter().concatenate(new RationalConverter(2, 1)));

    /**
     *  An angle unit accepted for use with SI units (standard name <code>ha</code>).
     */
//    public static final TransformedUnit<Area> HECTAR
//            = new TransformedUnit<Area>(SQUARE_METRE, new RationalConverter(10000, 1));

    /////////////////////
    // Collection View //
    /////////////////////

    @Override
    public String getName() {
        return "SI";
    }

    /**
     * Adds a new unit not mapped to any specified quantity type.
     *
     * @param  unit the unit being added.
     * @return <code>unit</code>.
     */
    private static <U extends Unit<?>>  U addUnit(U unit) {
        INSTANCE.units.add(unit);
        return unit;
    }

    /**
     * Adds a new unit and maps it to the specified quantity type.
     *
     * @param  unit the unit being added.
     * @param type the quantity type.
     * @return <code>unit</code>.
     */
    private static <U extends AbstractUnit<?>>  U addUnit(U unit, Class<? extends Quantity<?>> type) {
        INSTANCE.units.add(unit);
        INSTANCE.quantityToUnit.put(type, unit);
        return unit;
    }
}
