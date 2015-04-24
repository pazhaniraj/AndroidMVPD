package fileReaders;

import java.io.FileReader;
import java.util.Properties;

public class PropertiesReader {
	public static Properties getPropertiesFile(String propName) {
		try (FileReader reader = new FileReader("resources/" + propName
				+ ".properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
