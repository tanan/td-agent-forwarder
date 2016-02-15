package jp.co.kccs.td.log.forwarder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.komamitsu.fluency.Fluency;

public class TdLogForwarder {

	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			props.load(new FileInputStream(props.getProperty("TdProperties")));

			ConfigurationParser conParser = new ConfigurationParser();
			Map<String, String> config = new HashMap<String, String>();
			config = conParser.parseTdConfig(props
					.getProperty("parserConfigurationFile"));

			Fluency fluency = Fluency.defaultFluency(
					Arrays.asList(new InetSocketAddress(24224),
							new InetSocketAddress(24225)), new Fluency.Config()
							.setMaxBufferSize(32 * 1024 * 1024)
							.setFlushIntervalMillis(200)
							.setSenderMaxRetryCount(12));
			Map<String, Object> event = new HashMap<String, Object>();

		} catch (IOException e) {

		}

	}

}
