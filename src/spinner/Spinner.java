package spinner;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;

import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class Spinner extends JSpinner {

	private boolean negativo;

	public static int numeroValor;

	private int minValor;

	private int maxValor;

	protected static int valorMinimo;

	protected static int valorMaximo;

	protected static int valorIncremento;

	protected static boolean valorNegativo;

	private static boolean error;

	public static boolean mostrarUi;

	public static int incremento;

	public static LinkedList<Integer> valores;

	static SpinnerUI.Editor editor;

	public int getValor() {

		SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();

		return Integer.parseInt(editor.getText());

	}

	public void setValor(int numeroValor) {

		SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();

		editor.setText("" + numeroValor);

	}

	public void setIncremento(int incremento) {

		this.incremento = incremento;

	}

	public int getMinValor() {

		return minValor;

	}

	public int getMaxValor() {

		return this.maxValor;

	}

	public static void setMaxValor(int valorMaximo) {

		Spinner.valorMaximo = valorMaximo;

	}

	public boolean isNegativo() {

		return this.negativo;

	}

	public void setNegativo(boolean negativo) {

		this.negativo = negativo;

	}

	public void setLabelText(String text) {

		SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();

		editor.setLabelText(text);

	}

	public String getLabelText() {

		SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();

		return editor.getLabelText();

	}

	public Spinner() {

		this.valores = new LinkedList<Integer>();

		this.mostrarUi = true;

		this.valorNegativo = false;

		this.valorMinimo = 0;

		this.valorMaximo = 0;

		this.negativo = false;

		this.incremento = 1;

		this.valorIncremento = 1;

		this.minValor = 0;

		this.maxValor = 0;

		setOpaque(false);

		setUI(new SpinnerUI());

		this.editor = (SpinnerUI.Editor) getEditor();

		this.editor.setHorizontalAlignment(SwingConstants.CENTER);

		this.editor.setEditable(true);

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				try {

					int notches = e.getWheelRotation();

					if (notches < 0) {

						setNumeroValor(Integer.parseInt(editor.getText()));

						numeroValor -= editor.incremento;

						if (!editor.negativo && numeroValor < editor.min) {

							numeroValor = editor.min;

						}

						valores.add(numeroValor);

						editor.setText("" + numeroValor);

					}

					else {

						setNumeroValor(Integer.parseInt(editor.getText()));

						numeroValor += editor.incremento;

						if (editor.max != 0 && numeroValor > editor.max) {

							numeroValor = editor.max;

						}

						valores.add(numeroValor);

						editor.setText("" + numeroValor);

					}

				}

				catch (Exception e1) {

				}

			}

		});

	}

	public Spinner(boolean showUI, boolean editable, boolean negativo, int min, int max, int incremento) {

		this.valores = new LinkedList<Integer>();

		this.mostrarUi = showUI;

		this.valorNegativo = negativo;

		this.valorMinimo = min;

		this.valorMaximo = max;

		this.negativo = negativo;

		this.incremento = incremento;

		this.valorIncremento = incremento;

		this.minValor = min;

		this.maxValor = max;

		setOpaque(false);

		setUI(new SpinnerUI());

		this.editor = (SpinnerUI.Editor) getEditor();

		this.editor.setHorizontalAlignment(SwingConstants.CENTER);

		if (editable) {

			this.editor.setEditable(editable);

		}

		this.editor.setText("" + min);

	}

	public void ponerFiltro() {

		SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();

		try {

			String valor = editor.getText();

			int numero;

			numero = Integer.parseInt(valor);

			if (valor.isEmpty() || (!this.negativo && !valor.isEmpty() && numero <= 0 || valor.matches("0[0-9]*"))) {

				reiniciarValor(editor, numero, this.minValor);

			}

			else {

				if (this.maxValor != 0 && numero > this.maxValor) {

					numero = this.maxValor;

				}

				if (numero < this.minValor) {

					numero = minValor;

				}

				this.numeroValor = numero;

				valores.add(numero);

				editor.setText("" + numero);

			}

			this.error = false;

		}

		catch (Exception e) {

			this.error = true;

			int valorError;

			if (this.valores.isEmpty()) {

				valorError = this.minValor;

			}

			else {

				valorError = this.valores.getLast();

				if (valorError < this.minValor) {

					valorError = this.minValor;

				}

			}

			editor.setText("" + valorError);

			this.valores.clear();

		}

	}

	public void setMinValor(int minValor) {

		this.minValor = minValor;

		this.valorMinimo = minValor;

		this.editor.min = minValor;

		this.editor.setText("" + minValor);

	}

	protected static void setNumeroValor(int numeroValor) {

		Spinner.numeroValor = numeroValor;

	}

	public static void reiniciarValor(SpinnerUI.Editor editor, int valor, int minimo) {

		try {

			if (valor < editor.min) {

				valor = editor.min;

			}

			if (error) {

				error = false;

				numeroValor = valor;

			}

			else {

				error = true;

				if (valor <= minimo) {

					numeroValor = minimo;

				}

				else {

					numeroValor = 0;

				}

			}

			editor.setText("" + numeroValor);

		}

		catch (Exception e) {

			numeroValor = editor.min;

		}

	}

}
