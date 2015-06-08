package tec.uom.se.format.internal.l10n;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.Ignore;
import org.junit.Test;

import tec.uom.se.format.internal.l10n.Resources;

public class ResourcesTest {

	@Test
	@Ignore
	public void testResources() {
		ResourceBundle bundle = new Resources("format", "resources");
		assertNotNull(bundle.getKeys());
		assertTrue(bundle.getKeys().hasMoreElements());
//		assertEquals(10, bundle.keySet().size());
	}

}
