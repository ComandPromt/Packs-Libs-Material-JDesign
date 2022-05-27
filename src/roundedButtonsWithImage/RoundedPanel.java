package roundedButtonsWithImage;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

	protected int strokeSize;

	protected boolean highQuality = true;

	protected Dimension arcs = new Dimension(20, 20);

	public ImageIcon icon;

	BufferedImage image;

	BufferedImage roundedImage;

	int width;

	int height;

	Graphics2D graphics;

	private static Image scaleImage(Image image, int w, int h) {

		Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

		return scaled;
	}

	public RoundedPanel(int ancho, int alto, int strokeWidth, String imagen) {

		super();

		setOpaque(false);

		try {

			image = ImageIO.read(new File(imagen));

			roundedImage = makeRoundedCorner(scaleImage(image, ancho, alto), 20, ancho, alto);

			this.width = ancho;

			this.height = alto;

			this.strokeSize = strokeWidth;

		}

		catch (Exception ex) {

		}

	}

	public static BufferedImage makeRoundedCorner(Image image2, int cornerRadius, int w, int h) {

		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = output.createGraphics();

		g2.setComposite(AlphaComposite.Src);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.WHITE);

		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

		g2.setComposite(AlphaComposite.SrcAtop);

		g2.drawImage(image2, 0, 0, null);

		g2.dispose();

		return output;

	}

	protected Icon pintar(Graphics2D g) {

		super.paintComponent(g);

		this.graphics = g;

		this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		this.graphics.setColor(getBackground());

		this.graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);

		this.graphics.setColor(getForeground());

		this.graphics.setStroke(new BasicStroke(strokeSize));

		this.graphics.drawRoundRect(0, 0, width, height, arcs.width, arcs.height);
		if (roundedImage != null) {
			this.graphics.drawImage(roundedImage, 0, 0, this);

			icon = new ImageIcon(roundedImage);
		}
		return icon;

	}

	@Override

	public Dimension getPreferredSize() {

		return new Dimension(width, height);

	}

}
