package tec.uom.se.spi;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Locale;

import javax.measure.spi.Bootstrap;
import javax.measure.spi.UnitFormatService;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for services provided via {@link Bootstrap}.
 */
@SuppressWarnings("unchecked")
public class ServiceTest {

    @Test
    @Ignore
    public void testGetServices() throws Exception {
        Collection<Object> services = Collection.class.cast(Bootstrap.getServices(String.class));
        assertNotNull(services);
        assertFalse(services.isEmpty());
        assertTrue(services.contains("service1"));
        assertTrue(services.contains("service2"));
        services = Collection.class.cast(Bootstrap.getServices(Runtime.class));
        assertNotNull(services);
        assertTrue(services.isEmpty());
    }

    @Test
    public void testGetService() throws Exception {
        UnitFormatService ufs = Bootstrap.getService(UnitFormatService.class);
        assertNotNull(ufs);
        assertNotNull(ufs.getUnitFormat());
        assertEquals("DefaultFormat", ufs.getUnitFormat().getClass().getSimpleName());
    }

    @Test
    public void testGetService_BadCase() throws Exception {
        assertNull(Bootstrap.getService(Locale.class));
    }
}
