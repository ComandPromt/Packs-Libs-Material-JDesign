package combo_suggestion;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComboBox;

public class ComboBoxSuggestion<E> extends JComboBox<E> {

	public ComboBoxSuggestion() {

		setUI(new ComboSuggestionUI());

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				int notches = e.getWheelRotation();

				int valorIndice = getSelectedIndex();

				if (notches < 0) {

					valorIndice--;

				}

				else {

					valorIndice++;

				}

				if (valorIndice < 0) {

					valorIndice = 0;

				}

				if (valorIndice >= getItemCount()) {

					valorIndice = getItemCount();

					valorIndice--;

				}

				setSelectedIndex(valorIndice);

			}

		});

	}

}
