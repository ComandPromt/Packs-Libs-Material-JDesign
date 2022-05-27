package com.languages;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

public class ComboBoxIcon extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	ImageIcon[] imageIcon = { new ImageIcon(getClass().getResource("/flags/1.png")),
			new ImageIcon(getClass().getResource("/flags/2.png")),
			new ImageIcon(getClass().getResource("/flags/3.png")),
			new ImageIcon(getClass().getResource("/flags/4.png")),
			new ImageIcon(getClass().getResource("/flags/5.png")),
			new ImageIcon(getClass().getResource("/flags/6.png")),
			new ImageIcon(getClass().getResource("/flags/7.png")),
			new ImageIcon(getClass().getResource("/flags/8.png")),
			new ImageIcon(getClass().getResource("/flags/9.png")),
			new ImageIcon(getClass().getResource("/flags/10.png")),
			new ImageIcon(getClass().getResource("/flags/11.png")),
			new ImageIcon(getClass().getResource("/flags/12.png")),
			new ImageIcon(getClass().getResource("/flags/13.png")),
			new ImageIcon(getClass().getResource("/flags/14.png")),
			new ImageIcon(getClass().getResource("/flags/15.png")),
			new ImageIcon(getClass().getResource("/flags/16.png")),
			new ImageIcon(getClass().getResource("/flags/17.png")) };

	public ComboBoxIcon(JFrame frame, Language language, Language[] list) {

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container con = frame.getContentPane();

		ComboBoxRenderar rendrar = new ComboBoxRenderar();

		if (list != null) {

		}

		Integer[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

		JComboBox<Object> box = new JComboBox<>(array);

		box.setRenderer(rendrar);

		switch (language) {

		case FRENCH:

			box.setSelectedIndex(0);

			break;

		}

		con.add(box);

		con.setLayout(new FlowLayout());

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				try {

					int notches = e.getWheelRotation();

					int indice = box.getSelectedIndex();

					if (notches < 0) {

						indice++;

						if (indice >= box.getItemCount()) {

							indice = box.getItemCount();

							indice--;

						}

					}

					else {

						indice--;

						if (indice < 0) {

							indice = 0;

						}

					}

					box.setSelectedIndex(indice);

				}

				catch (Exception e1) {

				}

			}

		});

		frame.setVisible(false);

		frame.pack();

		setVisible(false);

	}

	public class ComboBoxRenderar extends JLabel implements ListCellRenderer<Object> {

		private static final long serialVersionUID = 1L;

		@Override

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			int offset = ((Integer) value).intValue() - 1;

			ImageIcon icon = imageIcon[offset];

			setIcon(icon);

			return this;

		}

	}

}