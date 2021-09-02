/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
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
import javax.swing.Timer;

/**
 * The Class GUIMenu.
 * Ventana (JFrame) que administra el menu de opciones del usuario
 */
public class GUIMenu extends JFrame {
	
	//Atributos
	private JPanel opciones,									//Panel de opciones del juego
				   fondoDePantalla;								//Panel que contiene el fondo de pantalla del juego
	private JButton nuevoJuego,									//Boton para iniciar un nuevo juego
					continuarJuego,								//Boton para continuar una partida del juego
					historialJugadores,							//Historial de los jugadores y el nivel en que quedaron
					salir;										//Salir del juego
	private ImageIcon fondo;									//Guardamos la imagen del juego
	private JLabel contenedorFondo;								//Contenedor de la imangen de fondo
	private Escucha escucha;									//Clase controladora de eventos
	private JFrame miMisma;										//Referencia a esta misma ventana
	private GUIHistorialJugadores vistaGUIHistorialJugadores;	//Ventana que grafica el historial de jugadores
	private GUIJuego vistaGUIJuego;								//Ventana que grafica el entorno principal del juego
	private GUIInicio vistaGUIInicio;							//Ventana que grafica el inicio del juego
	private Timer timer;										//Tiempo, en el cual se actualizan los componentes del JFrame
	private ManagerFiles managerFiles;							//Clase administradora del flujo de datos
	
	//Metodos
	/**
	 * Instantiates a new GUI menu.
	 * Constructor de la clase. Establece los parametros por defecto de la ventana e inicia los componentes grficos
	 */
	public GUIMenu() {
		initGUI();
		
		this.setTitle("Juego De Palabras");
		this.setSize(900,500);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
		this.setEnabled(false);
		
	}
	
	/**
	 * Inits the GUI.
	 * Inicia los componentes graficos del GUI, al igual que su layout
	 */
	private void initGUI() {
		//Inicializamos las variables necesarias
		JFrame vista = new JFrame();
		miMisma = this;
		vistaGUIHistorialJugadores = new GUIHistorialJugadores(this);
		vistaGUIJuego = new GUIJuego(this);
		escucha = new Escucha();
		timer = new Timer(1000, escucha);
		managerFiles = new ManagerFiles();
		vistaGUIInicio = new GUIInicio(miMisma);
		
		//Cambiamos el tipo de layout del JFrame
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Panel contenedor del menu de opciones
		opciones = new JPanel();
		//Cambiamos el layout del panel
		opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
		opciones.setOpaque(false);
		//Creamos los botones
		nuevoJuego = new JButton("Nuevo Juego");
		nuevoJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
		nuevoJuego.addActionListener(escucha);
		nuevoJuego.setCursor(new Cursor(HAND_CURSOR));
		nuevoJuego.setFocusable(false);
		nuevoJuego.setMaximumSize(new Dimension(150,60));
		continuarJuego = new JButton("Continuar Juego");
		continuarJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
		continuarJuego.addActionListener(escucha);
		continuarJuego.setMaximumSize(new Dimension(150,60));
		continuarJuego.setCursor(new Cursor(HAND_CURSOR));
		continuarJuego.setFocusable(false);
		String puedeContinuarPartida = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[1];
		//Si en la segunda piscion nos dicen que el jugador tiene una partida pendiente, activa el boton "continuarJuego"
		if (puedeContinuarPartida != null && puedeContinuarPartida.equals("conPartidaPendiente")) {
			continuarJuego.setEnabled(true);
		}
		//Si en la segunda piscion nos dicen que el jugador tiene una partida pendiente, desactiva el boton "continuarJuego"
		if (puedeContinuarPartida != null && puedeContinuarPartida.equals("sinPartidaPendiente")) {
			continuarJuego.setEnabled(false);
		}
		historialJugadores = new JButton("Historial Jugadores");
		historialJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
		historialJugadores.addActionListener(escucha);
		historialJugadores.setMaximumSize(new Dimension(150,60));
		historialJugadores.setCursor(new Cursor(HAND_CURSOR));
		historialJugadores.setFocusable(false);
		salir = new JButton("Salir");
		salir.setAlignmentX(Component.CENTER_ALIGNMENT);
		salir.addActionListener(escucha);
		salir.setMaximumSize(new Dimension(150,60));
		salir.setCursor(new Cursor(HAND_CURSOR));
		salir.setFocusable(false);
		//Añadimos los componentes
		opciones.add(nuevoJuego);
		opciones.add(Box.createRigidArea(new Dimension(0,5)));
		opciones.add(continuarJuego);
		opciones.add(Box.createRigidArea(new Dimension(0,5)));
		opciones.add(historialJugadores);
		opciones.add(Box.createRigidArea(new Dimension(0,5)));
		opciones.add(salir);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = vista.getWidth();
		constraints.gridheight = vista.getHeight();
		add(opciones, constraints);
		
		//Panel fondo de pantalla
		fondo = new ImageIcon("src/images/FondoInicio.png");
		contenedorFondo = new JLabel(fondo);
		fondoDePantalla = new JPanel();
		fondoDePantalla.add(contenedorFondo);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = this.getWidth();
		constraints.gridheight = this.getHeight();
		add(fondoDePantalla, constraints);
		timer.start();
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
			String puedeContinuarPartida = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[1];
			//Si el jugador puede continuar una partida
			if (puedeContinuarPartida != null && puedeContinuarPartida.equals("conPartidaPendiente")) {
				continuarJuego.setEnabled(true);
			}
			//Si el jugador no puede continuar una partida
			if (puedeContinuarPartida != null && puedeContinuarPartida.equals("sinPartidaPendiente")) {
				continuarJuego.setEnabled(false);
			}
			//Salir del Juego
			if(eventAction.getSource() == salir) {
				managerFiles.borrarArchivo("jugadorActual");
				System.exit(0);
			}
			//Continuar una partida guardada
			if(eventAction.getSource() == continuarJuego) {
				vistaGUIJuego.setVisible(true);
				vistaGUIJuego.setEnabled(true);
				miMisma.setVisible(false);
				miMisma.setEnabled(false);
				//true -> Serializamos
				managerFiles.escribirArchivo("jugadorActual", "true");
			}
			//Abrimos la ventana del hsitorial de jugadores
			if(eventAction.getSource() == historialJugadores) {
				vistaGUIHistorialJugadores.setVisible(true);
				vistaGUIHistorialJugadores.setEnabled(true);
				miMisma.setVisible(false);
				miMisma.setEnabled(false);
			}
			//Iniciar una partida nueva
			if(eventAction.getSource() == nuevoJuego) {
				vistaGUIJuego.setVisible(true);
				vistaGUIJuego.setEnabled(true);
				miMisma.setVisible(false);
				miMisma.setEnabled(false);
				//false -> No serializa
				managerFiles.escribirArchivo("jugadorActual", "false");
			}
		}	
	}
}
