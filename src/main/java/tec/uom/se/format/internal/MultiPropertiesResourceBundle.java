package tec.uom.se.format.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Yves Deschamps
 * 
 */
public class MultiPropertiesResourceBundle extends ResourceBundle {

	/**
	 * A logger.
	 */
	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Path for bundles.
	 */
	private static final String RESOURCEPATH = "tec/uom/se/format/";

	/**
	 * Path for bundles order.
	 */
	private static final String SPRINGRESOURCEPATH = "tec/uom/se/";

	/**
	 * The language in use.
	 */
	private String language;

	/**
	 * A Map containing the combined resources of all parts building this
	 * MultiplePropertiesResourceBundle.
	 */
	private Map<String, Object> combined;

	/**
	 * Constructor.
	 * 
	 * @param language
	 */
	public MultiPropertiesResourceBundle(String language) {
		this.language = language;
	}

	@Override
	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}
		loadBundlesOnce();
		return combined.get(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		loadBundlesOnce();
		return new ResourceBundleEnumeration(combined.keySet(),
				(parent != null) ? parent.getKeys() : null);
	}

	/**
	 * Load the resources once.
	 */
	private void loadBundlesOnce() {
		if (combined == null) {
			combined = new HashMap<String, Object>(128);

			List<String> bundleBasenames = new ArrayList<String>();

			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			String beanFileName = "i18n.xml";
			String resourcePath = SPRINGRESOURCEPATH;
			String resourceName = resourcePath + beanFileName;
			Enumeration<URL> names;
			try {
				names = cl.getResources(resourceName);
				while (names.hasMoreElements()) {
					URL fileUrl = names.nextElement();
					File file = new File(fileUrl.getFile());
					InputStream ips = new FileInputStream(file);
					InputStreamReader ipsr = new InputStreamReader(ips);
					BufferedReader br = new BufferedReader(ipsr);
					String ligne;
					while ((ligne = br.readLine()) != null) {
						if (ligne.indexOf("<value>") > -1) {
							ligne = ligne.replace("<value>", "")
									.replace("</value>", "").trim();
							if (!bundleBasenames.contains(ligne)) {
								bundleBasenames.add(ligne);
							}
						}
					}
					br.close();
				}
				List<String> bundleNames = new ArrayList<String>();

				for (String bundleName : bundleBasenames) {
					bundleNames.addAll(findBaseNames(bundleName
							.substring(bundleName.lastIndexOf("/") + 1)
							+ "_"
							+ language));
				}

				for (String bundleName : bundleNames) {
					ResourceBundle bundle = ResourceBundle.getBundle(
							bundleName, getLocale());
					Enumeration<String> keys = bundle.getKeys();
					String key = null;
					while (keys.hasMoreElements()) {
						key = keys.nextElement();
						combined.put(key, bundle.getObject(key));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Return a Set with the real base-names of the multiple properties based
	 * resource bundles that contribute to the full set of resources.
	 * 
	 * @param baseName
	 *            the base-name that must be part of the properties file names.
	 * @return a List with the real base-names.
	 */
	private List<String> findBaseNames(final String baseName) {
		boolean isLoggable = logger.isLoggable(Level.FINE);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		List<String> bundleNames = new ArrayList<String>();
		try {
			String baseFileName = baseName + ".properties";
			String resourcePath = RESOURCEPATH;
			String resourceName = resourcePath + baseFileName;
			if (isLoggable) {
				logger.fine("Looking for files named '" + resourceName + "'");
			}
			Enumeration<URL> names = cl.getResources(resourceName);
			int nbNames = 0;
			while (names.hasMoreElements()) {
				nbNames++;
				URL jarUrl = names.nextElement();
				if (isLoggable) {
					logger.fine("inspecting: " + jarUrl);
				}
				if ("jar".equals(jarUrl.getProtocol())) {
					String path = jarUrl.getFile();
					String filename = path.substring(0, path.length()
							- resourceName.length() - 2);
					if (filename.startsWith("file:")) {
						filename = filename.substring(5);
					}
					filename = filename.replaceAll("%20", " ");
					JarFile jar = new JarFile(filename);
					for (Enumeration<JarEntry> entries = jar.entries(); entries
							.hasMoreElements();) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						addMatchingNameOnce("", baseName, bundleNames,
								baseFileName, name);
					}
					jar.close();
				} else {
					File dir = new File(jarUrl.getFile());
					dir = dir.getParentFile();
					if (dir.isDirectory()) {
						for (String name : dir.list()) {
							addMatchingNameOnce(resourcePath, baseName,
									bundleNames, baseFileName, name);
						}
					}
				}
			}
			logger.fine("Number of bundles: " + nbNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isLoggable) {
			logger.fine("Combine ResourceBundles named: " + bundleNames);
		}
		return bundleNames;
	}

	/**
	 * @param resourcePath
	 * @param baseName
	 * @param bundleNames
	 * @param baseFileName
	 * @param name
	 */
	private void addMatchingNameOnce(String resourcePath, String baseName,
			List<String> bundleNames, String baseFileName, String name) {
		int prefixed = name.indexOf(baseName);
		if (prefixed > -1 && name.endsWith(baseFileName)) {
			String toAdd = resourcePath + name.substring(0, prefixed)
					+ baseName;
			if (!bundleNames.contains(toAdd)) {
				bundleNames.add(toAdd);
			}
		}
	}

}
