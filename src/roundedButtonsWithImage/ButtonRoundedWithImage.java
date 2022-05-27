package roundedButtonsWithImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class ButtonRoundedWithImage extends JButton {

	BufferedImage output;

	Graphics2D g2;

	private String limpiarCadena(String cadena) {

		cadena = cadena.replace("file:/", "");

		return cadena;

	}

	public ButtonRoundedWithImage() {

		output = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_ARGB);

		this.setBorder(new RoundedBorder(15));

	}

	public void setIcon(String icon, int width, int height, int stroke) {

		icon = limpiarCadena(icon);

		g2 = output.createGraphics();

		this.setIcon(new RoundedPanel(width, height, stroke, icon).pintar(g2));

		this.setBorder(new RoundedBorder(stroke));

	}

}
