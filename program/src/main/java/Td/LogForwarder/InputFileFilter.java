package Td.LogForwarder;

import java.io.File;
import java.io.FilenameFilter;

public class InputFileFilter {

	private String targetFolder;
	private String targetfilename;

	public InputFileFilter() {

	}

	public InputFileFilter(String folder, String filename) {
		this.targetFolder = folder;
		this.targetfilename = filename;
	}

	public File[] getFilterFile() {

		File folder = new File(targetFolder);
		File[] files = folder.listFiles(new TargetFileFilter());

		return files;


	}

	class TargetFileFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			if (name.matches(targetfilename)) {
				return true;
			}
			return false;
		}

	}
}