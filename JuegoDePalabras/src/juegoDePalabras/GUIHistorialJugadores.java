/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIHistorialJugadores.
 * Ventana (JFrame) que grafica el historial de jugadores
 */
public class GUIHistorialJugadores extends JFrame {
	
	//Atributos
	private JTextArea areaDeInformacion;						//Area donde se muestra la inforcacion (Nombre, nivel)
	private ImageIcon fondo;									//Donde se guarda la imagen de fondo
	private JLabel contenedorFondo;								//Contenedor de la imagen de fondo
	private JPanel fondoDePantalla,								//Panel del fondo de pantalla
		   		   contenedorInformacion;						//Panel donde se muetra la informacion
	private JFrame vistaGUIMenu;								//JFrame que adminsitra el menu d eopciones
	private JButton regresar;									//Boton para devolvernos al menu
	private Escucha escucha;									//Clase controladora de eventos
	private ManagerFiles managerFiles;							////Clase administradora del flujo de datos
	private Timer timer;										//Tiempo, en el cual se actualizan los componentes del JFrame
	
	//Metodos
	/**
	 * Instantiates a new GUI historial jugadores.
	 * Constructor de la clase. Establece los parametros por defecto de la ventana e inicia los componentes grficos
	 * @param GUIMenu the GUI menu //Referencia al JFrame menu
	 */
	public GUIHistorialJugadores(JFrame GUIMenu) {
		
		this.vistaGUIMenu = GUIMenu;
		initGUI();
		
		this.setTitle("Historial de Jugadores");
		this.setSize(900, 500);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(0, 153, 222));
		this.setVisible(false);
		this.setEnabled(false);
	}
	
	/**
	 * Inits the GUI.
	 * Inicia los componentes graficos del GUI, al igual que su layout
	 */
	private void initGUI() {
		//Inicializamos las variables necesarias
		managerFiles = new ManagerFiles();
		escucha = new Escucha();
		timer = new Timer(1000, escucha);
		
		//Cambiamos el layout
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Panel con el historial de jugadores
		contenedorInformacion = new JPanel();
		contenedorInformacion.setLayout(new BoxLayout(contenedorInformacion, BoxLayout.Y_AXIS));
		contenedorInformacion.setOpaque(false);
		//contenedorInformacion.setLayout(new BorderLayout());
		contenedorInformacion.setSize(new Dimension(200,200));
		areaDeInformacion = new JTextArea(10, 55);
		areaDeInformacion.setBackground(new Color(213, 219, 218));
		areaDeInformacion.setEditable(false);
		areaDeInformacion.setText(managerFiles.abrirArchivo("mostrarNombreNivel"));
		JScrollPane scroll = new JScrollPane(areaDeInformacion);
		regresar = new JButton("Regresar");
		regresar.setAlignmentX(Component.CENTER_ALIGNMENT);
		regresar.addActionListener(escucha);
		contenedorInformacion.add(scroll);
		contenedorInformacion.add(Box.createRigidArea(new Dimension(0,5)));
		contenedorInformacion.add(regresar);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 200;
		constraints.gridheight = 200;
		constraints.fill = GridBagConstraints.CENTER;
		add(contenedorInformacion, constraints);

		// Panel fondo de pantalla
		fondo = new ImageIcon("src/images/Historial.png");
		contenedorFondo = new JLabel(fondo);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = this.getWidth();
		constraints.gridheight = this.getHeight();
		constraints.fill = GridBagConstraints.NONE;
		add(contenedorFondo, constraints);
		
		timer.start();
	}
	
	/**
	 * Cambiar de ventana.
	 * Cambia la ventana hacia la del menu
	 */
	private void cambiarDeVentana() {
		setVisible(false);
		setEnabled(false);
		vistaGUIMenu.setVisible(true);
		vistaGUIMenu.setEnabled(true);
	}
	
	/**
	 * The Class Escucha.
	 * Clase privada de escucha, monitorea los eventos en la ventana.
	 */
	private class Escucha implements ActionListener {
		
		/**
		 * Action performed.
		 * Monitorea los eventos sobre los componentes la ventana
		 * @param eventAction the event action
		 */
		public void actionPerformed(ActionEvent eventAction) {
			areaDeInformacion.setText(managerFiles.abrirArchivo("mostrarNombreNivel"));
			//Regresamos a la ventana menu
			if (eventAction.getSource() == regresar) {
				cambiarDeVentana();
			}
		}
	}
}
