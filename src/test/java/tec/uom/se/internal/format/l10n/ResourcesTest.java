package tec.uom.se.internal.format.l10n;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.Test;

import tec.uom.se.internal.format.l10n.MultiPropertyResourceBundle;

public class ResourcesTest {

	@Test
	public void testResources() {
	       ResourceBundle bundle = ResourceBundle.getBundle("format/messages");

	        assertNotNull(bundle.getString("res1"));

	        MultiPropertyResourceBundle multiBundle = new MultiPropertyResourceBundle(bundle, "format");
	        assertNotNull(multiBundle.getString("res1"));

	        ResourceBundle bundle2 = ResourceBundle.getBundle("other_format/more_messages");
	        assertNotNull(bundle2.getString("more1"));

	        multiBundle.merge(bundle2, "other_format");

	        assertNotNull(multiBundle.getString("res1"));
	        assertNotNull(multiBundle.getString("res2"));
	        assertNotNull(multiBundle.getString("res3"));

	        assertEquals("res1", multiBundle.getString("res1"));
	        assertEquals("res2", multiBundle.getString("res2"));
	        assertEquals("res3", multiBundle.getString("res3"));
	        assertEquals("more message1", multiBundle.getString("more1"));
	}

}
