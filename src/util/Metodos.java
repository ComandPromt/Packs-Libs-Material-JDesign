package util;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import componente.PopupAlerts;
import io.github.biezhi.webp.WebpIO;

public abstract class Metodos {

	static Clipboard clipboard;

	public static PopupAlerts alertas;

	public static void help() {

		System.out.println("Las rutas de las carpetas deben terminar con el separador");

		System.out.println("Ejemplo: /home/user/  C:\\Users\\user\\Desktop\\");

		System.out.println(
				"eliminarEspacios(texto,control) --> Elimina los espacios en blanco innecesarios de una cadena");

		System.out.println("si control es true elimina todos los espacios en blanco");

		System.out.println("borrarArchivosDuplicados(carpeta) --> Borra los archivos duplicados de una carpeta");

		System.out.println("eliminarFichero(archivo) --> Elimina un archivo");

		System.out.println("esBisiesto(year) --> Comprueba si un año es bisiesto");

		System.out.println("aumentarDia(Date,numeroDias) --> Aumenta los días indicados a la fecha deseada");

		System.out.println("rutaActual() --> Devuelve la ruta actual");

		System.out.println("truncateDouble(numero,digitos) --> Trunca un número decimal a los dígitos introducidos");

		System.out.println(
				"convertirImagen(extensionEntrada,extensionSalida,carpeta) --> Conversor de imagenes. Ej convertirImagen(\"png\",\"jpg\",\"/home/user\")");

		System.out.println(
				"conversion(extensionEntrada,extensionSalida,carpeta) --> Cambia las extensiónes de los archivos de la carpeta introducida con la extension deseada");

		System.out.println(
				"renombrarArchivos(carpeta, \".\" , filtro) --> Renombra los archivos de una carpeta con el filtro indicado.");

		System.out.println("Si el filtro es true renombra los archivos segun la fecha actual");

		System.out.println("Si el filtro es false solo renombra el archivo quitandole espacios en blanco innecesarios");

		System.out.println("copy(texto) --> copia el texto deseado al portapapeles");

		System.out.println(
				"renombrarConCeros(ruta,extension) --> renombra los archivos que contienen el nombre indicado con el nombre del archivo seguido del 1 hasta el número de archivos con el nombre introducido respetando la extension de los archivos");

		System.out.println("Ejemplo renombrarConCeros(\"C:\\Users\\user\\Desktop\\\",\".jpg\")");

		System.out.println("Ejemplo renombrarConCeros(\"C:\\Users\\user\\Desktop\\\",\"all\")");

		System.out.println("Ejemplo renombrarConCeros(\"C:\\Users\\user\\Desktop\\\",\".\")");

		System.out.println("Ejemplo renombrarConCeros(\"C:\\Users\\user\\Desktop\\\",\"*\")");

		System.out.println("Ejemplo renombrarConCeros(\"C:\\Users\\user\\Desktop\\\",\"*.*\")");

		System.out.println("pingURL(String url) --> retorna si la url introducida está disponible o no");

		System.out.println(
				"convertirASegundos(duracion) --> muestra el número de segundos que hay en la duración introducida");

		System.out.println("Ejemplo: convertirASegundos(\"01:14:06\")");

		System.out.println("saberSeparador() --> obtiene el separador del sistema operativo actual");

		System.out.println("saberNombreArchivo(String ruta) --> obtiene el nombre del archivo de la ruta indicada");

		System.out.println("Ejemplo: saberNombreArchivo(\"C:\\Users\\user\\Desktop\\test.png\") --> test");

		System.out.println(
				"obtenerParametrosApi(ruta) --> muestra el nuevo nombre del archivo con la fecha y hora actual");

		System.out.println("Ejemplo obtenerParametrosApi(\"test.png\") ");

	}

	public static void crearCarpeta(String path) {

		new File(path).mkdir();

	}

	public static ArrayList<String> obtenerParametrosApi(String parametros) {

		JSONObject json = null;

		JSONArray array;

		ArrayList<String> resultado = new ArrayList<String>();

		try {

			json = apiImagenes(parametros);

			array = json.getJSONArray("imagenes_bd");

			for (int i = 0; i < array.length(); i++) {

				resultado.add(array.get(i).toString());

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;

	}

	public static void renombrarConCeros(String ruta, String filtro) {

		LinkedList<String> lista = new LinkedList<String>();

		lista = directorio(ruta, filtro, true, true);

		int y = 1;

		for (int i = 0; i < lista.size(); i++) {

			renombrar(lista.get(i), lista.get(i).substring(0, lista.get(i).lastIndexOf(saberSeparador()) + 1)
					+ calcularCeros(y, lista.size()) + "." + extraerExtension(lista.get(i)));

			y++;

		}

	}

	public static String calcularCeros(int frame, int numFrames) {

		String numeroDigitos = "";

		int totalFrames = ("" + numFrames).length();

		int digitos = ("" + frame).length();

		numeroDigitos = pintarCeros(totalFrames);

		if (totalFrames > 0) {

			numeroDigitos = pintarCeros(totalFrames -= (--digitos));

		}

		return numeroDigitos + frame;

	}

	public static String pintarCeros(int frames) {

		String ceros = "";

		for (int i = 0; i < frames; i++) {

			ceros += "0";

		}

		return ceros;

	}

	public static byte[] createChecksum(String filename) throws NoSuchAlgorithmException, IOException {

		InputStream fis = null;

		MessageDigest complete = MessageDigest.getInstance("SHA-256");

		try {

			fis = new FileInputStream(filename);

			byte[] buffer = new byte[1024];

			int numRead;

			do {

				numRead = fis.read(buffer);

				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}

			}

			while (numRead != -1);

			fis.close();

		}

		catch (IOException e) {

			if (fis != null) {
				fis.close();
			}

		}

		return complete.digest();

	}

	public static String getSHA256Checksum(String filename) {

		String result = "";

		try {

			byte[] b;

			b = createChecksum(filename);

			StringBuilder bld = new StringBuilder();

			for (int i = 0; i < b.length; i++) {

				bld.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));

			}

			result = bld.toString();

		}

		catch (Exception e) {
			//
		}

		return result;
	}

	public static List<String> borrarArchivosDuplicados(String carpeta) throws IOException {

		LinkedList<String> lista = new LinkedList<String>();

		lista = directorio(carpeta, ".", true, true);

		if (!lista.isEmpty()) {

			LinkedList<String> listaImagenesSha = new LinkedList<String>();

			for (int i = 0; i < lista.size(); i++) {

				listaImagenesSha.add(getSHA256Checksum(lista.get(i)));

			}

			List<String> duplicateList = listaImagenesSha.stream()

					.collect(Collectors.groupingBy(s -> s)).entrySet().stream()

					.filter(e -> e.getValue().size() > 1).map(e -> e.getKey()).collect(Collectors.toList());

			int indice = 0;

			for (String archivoRepetido : duplicateList) {

				for (int i = 0; i < Collections.frequency(listaImagenesSha, archivoRepetido) - 1; i++) {

					indice = listaImagenesSha.indexOf(archivoRepetido);

					if (indice < lista.size()) {

						eliminarFichero(lista.get(indice));

						lista.remove(indice);

					}

				}

			}

		}

		return lista;

	}

	public static void eliminarFichero(String archivo) throws IOException {

		File fichero = new File(archivo);

		if (fichero.exists()) {

			if (!fichero.isDirectory()) {

				fichero.delete();

			} else {

				FileUtils.deleteDirectory(new File(archivo));

			}
		}

	}

	public static boolean esBisiesto(int year) {

		if (year >= 3344) {
			return true;
		}

		else {

			if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
				return true;
			}

			else {
				return false;
			}
		}

	}

	public static String aumentarDia(Date fecha, int valor) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha);

		int calendarTime = Calendar.DAY_OF_MONTH;

		int temp = calendar.get(calendarTime);

		calendar.set(calendarTime, temp + valor);

		Date newDate = calendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String date = sdf.format(newDate);

		return date;

	}

	public static String saberSeparador() {

		if (System.getProperty("os.name").contains("indows")) {

			return "\\";

		}

		else {

			return "/";

		}

	}

	public static String rutaActual() throws IOException {

		return new File(".").getCanonicalPath() + saberSeparador();

	}

	public static double truncateDouble(double number, int numDigits) {

		double result = number;

		String arg = "" + number;

		int idx = arg.indexOf('.');

		if (idx != -1) {

			if (arg.length() > idx + numDigits) {

				arg = arg.substring(0, idx + numDigits + 1);

				result = Double.parseDouble(arg);

			}
		}

		return result;

	}

	public static void convertirImagen(String extensionEntrada, String extensionSalida, String folder)
			throws IOException {

		LinkedList<String> imagenesPng = new LinkedList<String>();

		imagenesPng = directorio(folder, extensionEntrada, true, true);

		File beforeFile;

		File afterFile;

		for (int i = 0; i < imagenesPng.size(); i++) {

			beforeFile = new File(imagenesPng.get(i));

			afterFile = new File(
					imagenesPng.get(i).substring(0, imagenesPng.get(i).lastIndexOf(".") + 1) + extensionSalida);

			BufferedImage beforeImg = ImageIO.read(beforeFile);

			BufferedImage afterImg = new BufferedImage(beforeImg.getWidth(), beforeImg.getHeight(),
					BufferedImage.TYPE_INT_RGB);

			afterImg.createGraphics().drawImage(beforeImg, 0, 0, Color.white, null);

			ImageIO.write(afterImg, extensionSalida, afterFile);

			eliminarFichero(imagenesPng.get(i));

		}

	}

	public static void conversion(String extension, String salida, String carpeta) {

		LinkedList<String> listaImagenes = directorio(carpeta, extension, true, false);

		int resto = 3;

		if (extension.length() == 4) {
			resto = 5;
		}

		for (int i = 0; i < listaImagenes.size(); i++) {

			File f1 = new File(carpeta + listaImagenes.get(i));

			File f2 = new File(
					carpeta + listaImagenes.get(i).substring(0, listaImagenes.get(i).length() - resto) + "." + salida);

			f1.renameTo(f2);

		}

	}

	public static String eliminarPuntos(String cadena) {

		String cadena2 = cadena;

		try {

			cadena2 = eliminarEspacios(cadena2, false);

			cadena2 = cadena.substring(0, cadena.length() - 4);

			cadena = cadena2.replace(".", "_") + "." + extraerExtension(cadena);

		}

		catch (Exception e) {

		}

		return cadena;

	}

	public static void renombrarArchivos(String ruta, String filtro, boolean api) throws IOException {

		List<String> list = directorio(ruta, filtro, true, false);

		if (list.size() > 0) {

			File f1;

			File f2;

			File f3;

			JSONArray imagenesBD = null;

			if (api) {

				JSONObject json;

				String parametros = obtenerParametros(list);

				json = apiImagenes(parametros);

				imagenesBD = json.getJSONArray("imagenes_bd");
			}

			for (int x = 0; x < list.size(); x++) {

				f1 = new File(ruta + list.get(x));

				f2 = new File(ruta + eliminarPuntos(list.get(x)));

				if (!f1.isDirectory() && f1.renameTo(f2)) {

					if (api) {

						f3 = new File(ruta + imagenesBD.get(x));

						if (!f2.renameTo(f3)) {

							x = list.size();

						}

					}

				}

				else {

					x = list.size();

				}

			}
		}
	}

	public static void renombrar(String ruta1, String ruta2) {

		File f1 = new File(ruta1);

		File f2 = new File(ruta2);

		f1.renameTo(f2);

	}

	public static boolean pingURL(String url) {

		int timeout = 400000;

		url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

		try {

			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");

			int responseCode = connection.getResponseCode();

			if (responseCode == 404) {
				return false;
			}

			else {
				return (200 <= responseCode && responseCode <= 399);
			}

		} catch (IOException exception) {
			return false;
		}

	}

	private static String readAll(Reader rd) throws IOException {

		StringBuilder sb = new StringBuilder();

		int cp;

		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}

		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException {

		InputStream is = new URL(url).openStream();

		BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

		String jsonText = readAll(rd);

		is.close();

		return new JSONObject(jsonText);

	}

	public static JSONObject apiImagenes(String parametros) throws IOException {

		JSONObject json = readJsonFromUrl("https://apiperiquito.herokuapp.com/recibo-json.php?imagenes=" + parametros);

		return json;

	}

	public static String obtenerParametros(List<String> list) {

		StringBuilder bld = new StringBuilder();

		String extension;

		for (int i = 0; i < list.size(); i++) {

			extension = list.get(i).substring(list.get(i).length() - 3, list.get(i).length());

			if (list.size() == 1 || i + 1 == list.size()) {

				bld.append(i + "." + extension);

			}

			else {

				bld.append(i + "." + extension + ",");

			}

		}

		return bld.toString();
	}

	public static double convertirASegundos(String duracionVideo) {

		double horas, minutos, segundos;

		try {

			horas = Double.parseDouble(duracionVideo.substring(0, duracionVideo.indexOf(":")));

			if (horas > 0) {

				horas *= 3600f;

			}

			minutos = Double.parseDouble(
					duracionVideo.substring(duracionVideo.indexOf(":") + 1, duracionVideo.lastIndexOf(":")));

			if (minutos > 0) {

				minutos *= 60f;

			}

			segundos = Double
					.parseDouble(duracionVideo.substring(duracionVideo.lastIndexOf(":") + 1, duracionVideo.length()));

		}

		catch (Exception e) {

			horas = 0;

			minutos = 0;

			segundos = 0;

		}

		return horas + minutos + segundos;

	}

	public static String calcularTiempo(long segundos) {

		int minutos = 0;

		int horas = 0;

		if (segundos == 60) {

			minutos = 1;

			segundos = 0;

		}

		minutos = (int) (segundos / 60);

		int calculoSegundos = 0;

		calculoSegundos = 60 * minutos;

		segundos -= calculoSegundos;

		if (minutos == 60) {

			horas = 1;

			minutos = 0;

			segundos = 0;

		}

		if (minutos > 60) {

			if (minutos % 60 == 0) {

				horas = minutos / 60;

				minutos = 0;

				segundos = 0;

			}

			else {

				int contador = 0;

				int horaProxima = 120;

				int siguienteHora = 0;

				while (contador == 0) {

					if (minutos < horaProxima) {

						contador = horaProxima;

					}

					else {

						siguienteHora = horaProxima + 60;

						if (minutos > horaProxima && minutos < siguienteHora) {

							contador = siguienteHora;

						}

						horaProxima = siguienteHora;

					}
				}

				horas = minutos / 60;

				minutos = 60 - (horaProxima - minutos);

			}

		}

		String ceroHoras = "";

		String ceroMinutos = "";

		String ceroSegundos = "";

		if (horas <= 9) {

			ceroHoras = "0";

		}

		if (minutos <= 9) {

			ceroMinutos = "0";

		}

		if (segundos <= 9) {

			ceroSegundos = "0";

		}

		return ceroHoras + horas + " : " + ceroMinutos + minutos + " : " + ceroSegundos + segundos;

	}

	public static String saberNombreArchivo(String ruta) {

		String separador = Metodos.saberSeparador();

		String resultado = "";

		if (ruta.contains(separador)) {

			resultado = ruta.substring(ruta.lastIndexOf(separador), ruta.lastIndexOf("."));

		}

		return resultado;

	}

	public static String extraerExtension(String nombreArchivo) {

		String extension = "";

		if (nombreArchivo.length() >= 3) {

			extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1, nombreArchivo.length());

			extension = extension.toLowerCase();

			if (extension.endsWith(".ts")) {

				extension = "ts";

			}

		}

		return extension;

	}

	private static Clipboard getSystemClipboard() {

		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();

		Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

		return systemClipboard;
	}

	public static void copy(String text) {

		Clipboard clipboard = getSystemClipboard();

		clipboard.setContents(new StringSelection(text), null);
	}

	public static void moverArchivo(String origen, String destino) {

		try {

			Files.move(FileSystems.getDefault().getPath(origen), FileSystems.getDefault().getPath(destino),
					StandardCopyOption.REPLACE_EXISTING);

		}

		catch (Exception e) {

		}

	}

	public static void webp_png(boolean png, String src, String dest, boolean eliminarArchivo) throws IOException {

		try {

			if (png) {

				WebpIO.create().toNormalImage(src, dest);

			}

			else {

				WebpIO.create().toWEBP(src, dest);

			}

			if (eliminarArchivo) {

				eliminarFichero(src);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<String> directorio(String ruta, String extension, boolean filtro, boolean carpeta) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			File f = new File(ruta);

			if (f.exists()) {

				File[] ficheros = f.listFiles();

				String fichero = "";

				String extensionArchivo;

				File folder;

				for (int x = 0; x < ficheros.length; x++) {

					fichero = ficheros[x].getName();

					folder = new File(ruta + fichero);

					extensionArchivo = extraerExtension(fichero);

					if (filtro && extensionArchivo.equals("webp")) {

						webp_png(true, ruta + fichero, ruta + fichero.substring(0, fichero.lastIndexOf(".")) + ".png",
								true);

					}

					if (filtro) {

						if (folder.isFile() && (extension.equals(".") || extension.equals(extensionArchivo)

								||

								(extension.equals("images")
										&& (extensionArchivo.equals("jpg") || extensionArchivo.equals("png")))

								|| (extension.equals("allimages")
										&& (extensionArchivo.equals("jpg") || extensionArchivo.equals("jpeg")
												|| extensionArchivo.equals("png") || extensionArchivo.equals("gif")))

						)) {

							if (carpeta) {

								lista.add(ruta + fichero);

							}

							else {

								lista.add(fichero);

							}

						}

					}

					else {

						if (folder.isDirectory() && carpeta) {

							lista.add(ruta + fichero);
						}

						else {

							if (folder.isDirectory() && !fichero.isEmpty()) {

								lista.add(fichero);

							}

						}

					}

				}

			}

		}

		catch (Exception e) {

		}

		Collections.sort(lista);

		return lista;

	}

	@SuppressWarnings("static-access")

	public static void copiar(String cadena, String texto, int size) {

		if (!texto.isEmpty()) {

			alertas.mensaje(texto, 2, size);

			alertas.mensaje(texto, 2, size);

		}

		StringSelection stringSelection = new StringSelection(cadena);

		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		clipboard.setContents(stringSelection, null);

	}

	public static void abrirCarpeta(String ruta) throws IOException {

		if (ruta != null && !ruta.equals("") && !ruta.isEmpty()) {

			try {

				if (System.getProperty("os.name").contentEquals("Linux")) {

					Runtime.getRuntime().exec("xdg-open " + ruta);

				}

				else {

					Runtime.getRuntime().exec("cmd /c C:\\Windows\\explorer.exe " + "\"" + ruta + "\"");

				}

			}

			catch (IOException e) {

				alertas.mensaje("Ruta inválida", 1, 20);

			}

		}

	}

	public static String eliminarEspacios(String cadena, boolean filtro) {

		cadena = cadena.trim();

		cadena = cadena.replace("  ", " ");

		cadena = cadena.trim();

		if (filtro) {

			cadena = cadena.replace(" ", "");

		}

		return cadena;

	}

}
