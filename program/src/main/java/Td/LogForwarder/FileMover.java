package Td.LogForwarder;

import java.io.File;

public class FileMover {

	File[] files;

	public FileMover() {

	}

	public FileMover(File[] files) {
		this.files = files;
	}

	public void moveFiles(String dir) {
		if (files == null) {
			System.out.println("移動対象のファイルがありません。");
		} else {
			for (File file : files) {
				File dest = new File(dir + "/" + file.getName());
				if (!file.renameTo(dest)) {
					System.out.println("ファイルの移動に失敗しました。" + file.getName());
				}
			}
		}
	}

	// private boolean moveFile(File source, File dest) {
	// return source.renameTo(dest);
	// }

}
