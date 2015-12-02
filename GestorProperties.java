package es.moralejo.org;

import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;

//usage: PropertiesReader.getParameter("clave");

public class PropertiesReader {

	private static Properties properties = null;
	
	
	public static String getParameter(String arg) {
		if ( properties == null ) {
			getPropertiesFile();
		}
		return pget(properties, arg.toUpperCase());
	}
	
	private static String pget(Properties p, String arg) {
		final String prp = p.getProperty(arg);
		if (prp == null) {
			return "";
		} else {
			return prp; 
		}
	}


	private static void getPropertiesFile() {
		PropertyResourceBundle objProperties = null;
		objProperties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(Constantes.NOMBRE_PROPERTIES);			
		final Enumeration<String> e = objProperties.getKeys();
		String key = null;
		String value = null;
		properties = new Properties();
		boolean blnHayMas = e.hasMoreElements();
		while (blnHayMas) {				
			key = (String) e.nextElement();				
			value = objProperties.getString(key);
			properties.setProperty(key.toUpperCase(), value);
			blnHayMas = e.hasMoreElements();
		}		
		
	}
}
