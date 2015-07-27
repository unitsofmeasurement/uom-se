package tec.uom.se.internal.format;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Locale;

import javax.measure.spi.Bootstrap;
import javax.measure.spi.UnitFormatService;

import org.junit.Test;

/**
 * Tests for services provided via {@link Bootstrap}.
 */
public class UnitFormatServiceTest {

    @Test
    public void testGetServices() throws Exception {
    	Collection<UnitFormatService> services = Bootstrap.getServices(UnitFormatService.class);
        assertNotNull(services);
        assertFalse(services.isEmpty());
        assertEquals(1, services.size());
    }

    @Test
    public void testGetService() throws Exception {
        UnitFormatService ufs = Bootstrap.getService(UnitFormatService.class);
        assertNotNull(ufs);
        assertNotNull(ufs.getUnitFormat());
        assertEquals("DefaultFormat", ufs.getUnitFormat().getClass().getSimpleName());
    }
    
    @Test
    public void testGetFormatFound() throws Exception {
        UnitFormatService ufs = Bootstrap.getService(UnitFormatService.class);
        assertNotNull(ufs);
        assertNotNull(ufs.getUnitFormat("EBNF"));
    }
    
    @Test
    public void testGetFormatNotFound() throws Exception {
        UnitFormatService ufs = Bootstrap.getService(UnitFormatService.class);
        assertNotNull(ufs);
        assertNull(ufs.getUnitFormat("XYZ"));
    }

    @Test
    public void testGetService_BadCase() throws Exception {
        assertNull(Bootstrap.getService(Locale.class));
    }
}
