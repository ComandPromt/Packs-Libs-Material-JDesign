package simple.chooser;

import java.awt.Toolkit;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")

public class DemoJavaFxStage extends JFrame {

	public String folder;

	public DemoJavaFxStage() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(DemoJavaFxStage.class.getResource("/simple/chooser/folder.png")));

		this.setVisible(false);

	}

	public LinkedList<File> showOpenFileDialog(String path, boolean carpeta, String filtro) {

		if (folder == null) {

			folder = "";

		}

		LinkedList<File> files = new LinkedList<File>();

		JFileChooser fileChooser = new JFileChooser();

		if (folder.isEmpty() || path.isEmpty()) {

			path = System.getProperty("user.home");

		}

		fileChooser.setCurrentDirectory(new File(path));

		fileChooser.setMultiSelectionEnabled(true);

		if (carpeta) {

			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		}

		else {

			switch (filtro) {

			case "":
			case "none":

			case "all":

				break;

			case "images":

				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif"));

				break;

			default:

				fileChooser.addChoosableFileFilter(
						new FileNameExtensionFilter(filtro + " Files (*." + filtro + ")", filtro));

				break;

			}

			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		}

		folder = fileChooser.getCurrentDirectory().toString();

		fileChooser.setAcceptAllFileFilterUsed(true);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {

			File[] selectedFile = fileChooser.getSelectedFiles();

			files = addImages(carpeta, filtro, selectedFile);

		}
		return files;
	}

	public String getFolder() {
		return folder;
	}

	public static void main(String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		}

		catch (Exception e) {

		}

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {

					new DemoJavaFxStage();

				}

				catch (Exception e) {
					//

				}

			}

		});

	}

	public static String extraerExtension(String nombreArchivo) {

		String extension = "";

		if (nombreArchivo.length() >= 3) {

			extension = nombreArchivo.substring(nombreArchivo.length() - 3, nombreArchivo.length());

			extension = extension.toLowerCase();

			if (extension.equals("peg")) {
				extension = "jpeg";
			}

			if (extension.equals("fif")) {
				extension = "jfif";
			}

			if (extension.equals("ebp")) {
				extension = "webp";
			}

			if (extension.equals("ebm")) {
				extension = "webm";
			}

			if (extension.equals("3u8")) {
				extension = "m3u8";
			}

			if (extension.equals(".ts")) {
				extension = "ts";
			}

		}

		return extension;
	}

	public static String saberSeparador() {

		if (System.getProperty("os.name").equals("Linux")) {

			return "/";

		}

		else {

			return "\\";

		}

	}

	public static LinkedList<File> addImages(boolean carpeta, String filtro, File[] fc) {

		LinkedList<File> files = new LinkedList<File>();

		files.clear();

		Arrays.asList(fc).forEach(x -> {

			if (!carpeta) {

				String extension;

				if (x.isFile()) {

					switch (filtro) {

					case "":

					case "none":

					case "all":

						files.add(new File(x.getAbsolutePath()));

						break;

					case "images":

						extension = extraerExtension(x.getAbsolutePath());

						if (extension.equals("jpeg") || extension.equals("bmp") || extension.equals("jpg")
								|| extension.equals("png") || extension.equals("gif")) {

							files.add(new File(x.getAbsolutePath()));

						}

						break;

					default:

						extension = extraerExtension(x.getAbsolutePath());

						if (extension.equals(filtro)) {

							files.add(new File(x.getAbsolutePath()));

						}

						break;

					}

				}

			}

			else {

				if (x.isDirectory()) {

					files.add(new File(x.getAbsolutePath()));

				}

			}

		});

		return files;

	}

}
