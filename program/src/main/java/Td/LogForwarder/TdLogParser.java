package Td.LogForwarder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.komamitsu.fluency.BufferFullException;
import org.komamitsu.fluency.Fluency;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class TdLogParser {

	private static final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public TdLogParser() {

	}

	public void doApp(Fluency fluency, Map<String, String> config) {
		CsvParserSettings settings = new CsvParserSettings();
		settings.getFormat().setLineSeparator("\r\n");
		settings.getFormat().setDelimiter('\t');
		settings.setHeaderExtractionEnabled(false);
		settings.setMaxCharsPerColumn(100000);

		CsvParser parser = new CsvParser(settings);

		// データベース名取得
		String database = config.get("database");

		// テーブル名取得
		String tablename = config.get("tablename");

		// カラム名取得
		String[] columns = config.get("columns").split(",");

		// カラム正規表現取得
		Map<String, String> typeMap = new HashMap<String, String>();
		String[] columntype = config.get("columntype").split(",");
		for (String kv : columntype) {
			String[] arg = kv.split(":");
			typeMap.put(arg[0], arg[1]);
			System.out.println(arg[0] + ":" + typeMap.get(arg[0]));
		}

		// 対象ファイル取得

		InputFileFilter inputFileFilter = new InputFileFilter(
				config.get("sourcedir"), config.get("filename"));
		File[] files = inputFileFilter.getFilterFile();

		BufferedReader br = null;
		try {
			for (File file : files) {
				System.out.println("START:" + file);
				br = Files.newBufferedReader(Paths.get(file.getPath()),
						Charset.forName("UTF-8"));

				Map<String, Object> event = new HashMap<String, Object>();
				String tag = database + "." + tablename;

				String line;
				while ((line = br.readLine()) != null) {
					long timestamp = Instant.now().getEpochSecond();
					String[] values = parser.parseLine(line);
					try {
						for (int i = 0; i < columns.length; i++) {
							if (typeMap.containsKey(columns[i])) {
								String type = typeMap.get(columns[i]);
								if (type == null) {

								} else
									switch (type) {
									case "integer":
										event.put(columns[i],
												Integer.parseInt(values[i]));
										break;
									case "float":
										event.put(columns[i],
												Float.parseFloat(values[i]));
										break;
									case "time":
										LocalDateTime date = LocalDateTime
												.parse(values[i], formatter);
										Instant instant = date
												.toInstant(ZoneOffset
														.ofHours(9));
										timestamp = instant.getEpochSecond();
										break;
									default:
										event.put(columns[i], values[i]);
									}
							} else {
								event.put(columns[i], values[i]);
							}
						}
					} catch (NumberFormatException e) {

					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("カラム数が一致していません。");
						continue;
					}
					fluency.emit(tag, timestamp, event);
				}
				System.out.println("FINISH:" + file);
				br.close();
			}
		} catch (BufferFullException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fluency.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileMover fileMover = new FileMover(files);
		fileMover.moveFiles(config.get("destdir"));
	}
}
