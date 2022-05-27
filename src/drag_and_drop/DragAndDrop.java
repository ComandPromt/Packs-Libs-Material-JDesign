package drag_and_drop;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class DragAndDrop extends JTextArea {

	TitledBorder dragBorder;

	public DragAndDrop(String title, String text) {

		setFont(new Font("Tahoma", Font.PLAIN, 16));

		setEditable(false);

		setText(text);

		setForeground(SystemColor.desktop);

		setBackground(SystemColor.menu);

		this.dragBorder = new javax.swing.border.TitledBorder(title);

	}

}
