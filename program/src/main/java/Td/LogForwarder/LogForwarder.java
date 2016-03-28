package Td.LogForwarder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.komamitsu.fluency.Fluency;
import org.xml.sax.SAXException;

/**
 *
 */
public class LogForwarder {
	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			ConfigurationParser conParser = new ConfigurationParser();
			Map<String, String> config = new HashMap<String, String>();
			System.out.println("parserConfigurationFile = "
					+ props.getProperty("parserConfigurationFile"));
			config = conParser.parseTdConfig(props
					.getProperty("parserConfigurationFile"));

			Fluency fluency = Fluency.defaultFluency(
					Arrays.asList(
							new InetSocketAddress(Integer.parseInt(config
									.get("pPort"))), new InetSocketAddress(
									Integer.parseInt(config.get("sPort")))),
					new Fluency.Config()
							.setMaxBufferSize(
									Integer.parseInt(config
											.get("maxBufferSize")))
							.setFlushIntervalMillis(
									Integer.parseInt(config
											.get("flushIntervalMillis")))
							.setSenderMaxRetryCount(
									Integer.parseInt(config
											.get("maxRetryCount"))));

			TdLogParser tdLogParser = new TdLogParser();
			tdLogParser.doApp(fluency, config);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}
