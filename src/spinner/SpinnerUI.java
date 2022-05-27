package spinner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

import textfield.TextField;

public class SpinnerUI extends BasicSpinnerUI {

	Editor editor;

	@Override

	public void installUI(JComponent jc) {

		super.installUI(jc);

		spinner.setEditor(new Editor(spinner, Spinner.valorMinimo, Spinner.valorMaximo, Spinner.valorNegativo,
				Spinner.valorIncremento));

		editor = (Editor) spinner.getEditor();

	}

	@Override

	protected Component createNextButton() {

		if (Spinner.mostrarUi) {

			ArrowButton cmd = new ArrowButton(true);

			cmd.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					try {

						Spinner.setNumeroValor(Integer.parseInt(editor.getText()));

						Spinner.numeroValor += editor.incremento;

						if (editor.max != 0 && Spinner.numeroValor > editor.max) {

							Spinner.numeroValor = editor.max;

						}

						Spinner.valores.add(Spinner.numeroValor);

						editor.setText("" + Spinner.numeroValor);

					}

					catch (Exception e1) {

					}

				}

			});

			return cmd;

		}

		else {

			return null;

		}

	}

	protected Component createPreviousButton() {

		if (Spinner.mostrarUi) {

			ArrowButton cmd = new ArrowButton(false);

			cmd.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					try {

						Spinner.setNumeroValor(Integer.parseInt(editor.getText()));

						Spinner.numeroValor -= editor.incremento;

						if (!editor.negativo && Spinner.numeroValor < editor.min) {

							Spinner.numeroValor = editor.min;

						}

						Spinner.valores.add(Spinner.numeroValor);

						editor.setText("" + Spinner.numeroValor);

					}

					catch (Exception e1) {

					}

				}

			});

			return cmd;

		}

		else {

			return null;

		}

	}

	public class Editor extends TextField implements ChangeListener, KeyListener {

		int min;

		int max;

		int incremento;

		boolean negativo;

		public Editor(JSpinner spinner, int min, int max, boolean negativo, int sumar) {

			spinner.addChangeListener(this);

			setEditable(false);

			setText("0");

			this.incremento = sumar;

			this.min = min;

			this.max = max;

			this.negativo = negativo;

			spinner.addKeyListener(this);

		}

		public void stateChanged(ChangeEvent ce) {

			JSpinner spinner = (JSpinner) ce.getSource();

			int comprobacion;

			try {

				comprobacion = Integer.parseInt(spinner.getValue().toString());

				setText("" + comprobacion);

			}

			catch (Exception e) {

				setText("0");

			}

		}

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {

		}

	}

	private class ArrowButton extends JButton {

		private final boolean next;

		public ArrowButton(boolean next) {

			this.next = next;

			setContentAreaFilled(false);

			setFocusable(false);

			setBorder(new EmptyBorder(5, 7, 5, 7));

			setBackground(new Color(231, 231, 231));

			setForeground(new Color(150, 150, 150));

			addMouseListener(new MouseAdapter() {

				@Override

				public void mousePressed(MouseEvent me) {

					setSelected(true);

				}

				@Override

				public void mouseReleased(MouseEvent me) {

					setSelected(false);

				}

			});

		}

		@Override

		public void paint(Graphics grphcs) {

			super.paint(grphcs);

			Graphics2D g2 = (Graphics2D) grphcs;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(getBackground());

			g2.fillRoundRect(0, 1, getWidth(), getHeight() - 2, 5, 5);

			if (next) {

				int width = getWidth();

				int height = getHeight();

				int size = 10;

				int x = (width - size) / 2;

				int y = (height - size) / 2;

				int px[] = { x + size / 2, x + size, x };

				int py[] = { y, y + size, y + size };

				if (isSelected()) {

					g2.setColor(new Color(181, 181, 181));

				}

				else {

					g2.setColor(getForeground());

				}

				g2.fillPolygon(px, py, px.length);
			}

			else {

				int width = getWidth();

				int height = getHeight();

				int size = 10;

				int x = (width - size) / 2;

				int y = (height - size) / 2;

				int px[] = { x, x + size, x + size / 2 };

				int py[] = { y, y, y + size };

				if (isSelected()) {

					g2.setColor(new Color(181, 181, 181));

				}

				else {

					g2.setColor(getForeground());

				}

				g2.fillPolygon(px, py, px.length);

			}

			g2.dispose();

		}

	}

}
