
package alertas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import Animacion.Fade;
import componente.PopupAlerts;

public class AlertSuccess extends javax.swing.JFrame {

	Timer timer = null;

	TimerTask task;

	int i = 32;

	public AlertSuccess(java.awt.Frame parent, boolean modal) {

		setAlwaysOnTop(true);

		initComponents();

		this.setLocationRelativeTo(null);

		PopupAlerts.setOpaque(this, false);

		Ubicar(0);

		Fade.JFrameFadeOut(1f, 0f, 0.1f, 480, this, Fade.DISPOSE);

	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();

		jPanel1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				dispose();

			}

		});

		jLabel1 = new javax.swing.JLabel();

		jLabel1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				dispose();

			}

		});

		titulo = new javax.swing.JLabel();

		titulo.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				dispose();

			}

		});

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		setUndecorated(true);

		addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent evt) {

				formWindowClosing(evt);

			}

			public void windowOpened(java.awt.event.WindowEvent evt) {

				formWindowOpened(evt);

			}

		});

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/success.png")));

		titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		titulo.setText("Webcamer introducida correctamente");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);

		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
								.addComponent(titulo, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
						.addContainerGap()));

		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(49)
						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE).addGap(39)
						.addComponent(titulo, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(63, Short.MAX_VALUE)));

		jPanel1.setLayout(jPanel1Layout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}

	public void setTitulo(String mensaje) {

		titulo.setText(mensaje);

	}

	private void formWindowOpened(java.awt.event.WindowEvent evt) {

		task = new TimerTask() {

			@Override

			public void run() {

				if (i == 352) {

					timer.cancel();

				}

				else {

					Ubicar(i);

					i += 32;

					Trasparencia((float) i / 352);

				}

			}

		};

		timer = new java.util.Timer();

		timer.schedule(task, 0, 2);

	}

	private void formWindowClosing(java.awt.event.WindowEvent evt) {

		setVisible(false);

		dispose();

	}

	private javax.swing.JLabel jLabel1;

	public javax.swing.JLabel titulo;

	private javax.swing.JPanel jPanel1;

	private void Trasparencia(float trasp) {

		PopupAlerts.setOpacity(this, trasp);

	}

	private void Ubicar(int y) {

		this.setLocation(550, y - 120);

	}

}
