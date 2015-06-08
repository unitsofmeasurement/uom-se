package tec.uom.se.format.intarnal.l10n;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.Test;

import tec.uom.se.format.internal.l10n.Resources;

public class ResourcesTest {

	@Test
	public void testResources() {
		ResourceBundle bundle = new Resources("de");
		assertNotNull(bundle.getKeys());
		assertTrue(bundle.getKeys().hasMoreElements());
//		assertEquals(10, bundle.keySet().size());
	}

}
