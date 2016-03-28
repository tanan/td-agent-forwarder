package Td.LogForwarder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ConfigurationParser {
	// データベース名Path
	private String database = "/configuration/database";
	// テーブル名Path
	private String tablename = "/configuration/tablename";
	// フォルダ名Path
	private String sourcedir = "/configuration/sourcedir";
	// フォルダ名Path
	private String destdir = "/configuration/destdir";
	// ファイル名Path
	private String filename = "/configuration/filename";
	// カラム名Path
	private String columns = "/configuration/columns";
	// カラム定義Path
	private String columntype = "/configuration/columntype";
	// プライマリポート番号Path
	private String pPort = "/configuration/pPort";
	// セカンダリポート番号Path
	private String sPort = "/configuration/sPort";
	// 最大バッファサイズPath
	private String maxBufferSize = "/configuration/maxBufferSize";
	// Flush間隔Path
	private String flushIntervalMillis = "/configuration/flushIntervalMillis";
	// リトライ回数
	private String maxRetryCount = "/configuration/maxRetryCount";


	public ConfigurationParser() {

	}

	public Map<String, String> parseTdConfig(String configFile)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document parseConfig = builder.parse(new File(configFile));

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		// 各種設定格納用マップ
		Map<String, String> configurationMap = new HashMap<String, String>();

		// CONFIG読み込み後変数格納
		configurationMap.put("database", xpath.evaluate(database, parseConfig));
		configurationMap.put("tablename",
				xpath.evaluate(tablename, parseConfig));
		configurationMap.put("sourcedir", xpath.evaluate(sourcedir, parseConfig));
		configurationMap.put("destdir", xpath.evaluate(destdir, parseConfig));
		configurationMap.put("filename", xpath.evaluate(filename, parseConfig));
		configurationMap.put("columns", xpath.evaluate(columns, parseConfig));
		configurationMap.put("columntype",
				xpath.evaluate(columntype, parseConfig));
		configurationMap.put("pPort", xpath.evaluate(pPort, parseConfig));
		configurationMap.put("sPort", xpath.evaluate(sPort, parseConfig));
		configurationMap.put("maxBufferSize", xpath.evaluate(maxBufferSize, parseConfig));
		configurationMap.put("flushIntervalMillis", xpath.evaluate(flushIntervalMillis, parseConfig));
		configurationMap.put("maxRetryCount", xpath.evaluate(maxRetryCount, parseConfig));

		System.out.println("database:" + configurationMap.get("database"));
		System.out.println("tablename:" + configurationMap.get("tablename"));
		System.out.println("sourcedir:" + configurationMap.get("sourcedir"));
		System.out.println("destdir:" + configurationMap.get("destdir"));
		System.out.println("filename:" + configurationMap.get("filename"));
		System.out.println("columns:" + configurationMap.get("columns"));
		System.out.println("columntype:" + configurationMap.get("columns"));
		System.out.println("pPort:" + configurationMap.get("pPort"));
		System.out.println("sPort:" + configurationMap.get("sPort"));
		System.out.println("maxBufferSize:" + configurationMap.get("maxBufferSize"));
		System.out.println("flushIntervalMillis:" + configurationMap.get("flushIntervalMillis"));
		System.out.println("maxRetryCount:" + configurationMap.get("maxRetryCount"));
		return configurationMap;

	}

}
