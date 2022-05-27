package componente;

import java.awt.Font;
import java.awt.Window;
import java.lang.reflect.Method;

import alertas.AlertError;
import alertas.AlertInformation;
import alertas.AlertSuccess;
import alertas.AlertWarningSalir;

public class PopupAlerts {

	public static void mensaje(String mensaje, int type, int size) {

		if (size <= 0) {

			size = 18;

		}

		switch (type) {

		case 1:

			AlertError error;

			error = new AlertError(null, false);

			error.setTitulo(mensaje);

			error.titulo.setFont(new Font("Tahoma", Font.BOLD, size));

			error.setOpacity(0.5f);

			error.setVisible(true);

			break;

		case 2:

			AlertInformation informacion;

			informacion = new AlertInformation(null, false);

			informacion.setTitulo(mensaje);

			informacion.titulo.setFont(new Font("Tahoma", Font.BOLD, size));

			informacion.setOpacity(0.5f);

			informacion.setVisible(true);

			break;

		case 3:

			AlertWarningSalir salir;

			salir = new AlertWarningSalir(null, false);

			salir.setTitulo(mensaje);

			salir.titulo.setFont(new Font("Tahoma", Font.BOLD, size));

			salir.setOpacity(0.5f);

			salir.setVisible(true);

			break;

		case 4:

			AlertSuccess exito;

			exito = new AlertSuccess(null, false);

			exito.setTitulo(mensaje);

			exito.titulo.setFont(new Font("Tahoma", Font.BOLD, size));

			exito.setOpacity(0.5f);

			exito.setVisible(true);

			break;

		}

	}

	public static void setOpaque(Window window, boolean opaque) {

		try {

			Class<?> awtUtilsClass = Class.forName("com.sun.awt.AWTUtilities");

			if (awtUtilsClass != null) {

				Method method = awtUtilsClass.getMethod("setWindowOpaque", Window.class, boolean.class);

				method.invoke(null, window, opaque);

			}

		}

		catch (Exception exp) {

		}

	}

	public static void setOpacity(Window window, float opacidad) {

		try {

			Class<?> awtUtilsClass = Class.forName("com.sun.awt.AWTUtilities");

			if (awtUtilsClass != null) {

				Method method = awtUtilsClass.getMethod("setWindowOpacity", Window.class, float.class);

				method.invoke(null, window, opacidad);

			}

		}

		catch (Exception exp) {

		}

	}

}
