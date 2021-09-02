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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Class GUIInicio.
 * Ventana (JFrame) que administra la primera ventana que ve el usuario
 */
public class GUIInicio extends JFrame {
	
	//Atributos
	private JPanel datosUsuario,									//Panel para pedir datos al usuario
				   fondoPantalla;									//Panel que contiene la imagen de fondo
	private JLabel pedirNombre,										//Pregunta para el usuario (nombre)
				   contenedorDeFondo;								//Donde se contendra la imagen de fondo para mostrar
	private JTextField campoNombre;									//Campo para ingresar el dato solicitado
	private JButton okInicio;										//Boton de OK en el inicio
	private ImageIcon fondo;										//Donde se guarda la imagen
	private Escucha escucha;										//Clase privada administradora de eventos
	private JFrame vistaGUIMenu;									//Referencia a la ventana que grafica el menu de opciones
	private ManagerFiles managerFiles;								//Clase administradora del flujo de datos
	private String[] historialJugadores;							//Array que contiene todos los usuarios que han juagado
	private String nombreJugador;									//Nombre del jugador actual
	
	//Metodos
	/**
	 * Instantiates a new GUI inicio.
	 * Constructor de la clase. Establece los parametros por defecto de la ventana e inicia los componentes grficos
	 * @param GUIMenu the GUI menu // Referencia a la siguiente ventana (Menu)
	 */
	public GUIInicio(JFrame GUIMenu) {
		
		this.vistaGUIMenu = GUIMenu;
		initGUI();
		
		this.setTitle("Juego De Palabras");
		this.setSize(900,500);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setEnabled(true);
		
	}
	
	/**
	 * Inits the GUI.
	 * Inicia los componentes graficos del GUI, al igual que su layout
	 */
	private void initGUI() {
		//Inicializamos las variables necesarias
		JFrame vista = new JFrame();
		managerFiles = new ManagerFiles();
		escucha = new Escucha();
		//Primero buscamos la lista de jugadores que han ejecutado el juego
		historialJugadores = managerFiles.leerHistorialJugadores();
		
		//Cambiamos el tipo de layout del JFrame
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Panel para pedir datos al usuario
		datosUsuario = new JPanel();
		datosUsuario.setBackground(new Color (150, 152, 154));
		//Cambiamos el layout del panel
		datosUsuario.setLayout(new BoxLayout(datosUsuario, BoxLayout.Y_AXIS));
		//Componentes del JPanel
		//Titulo del panel
		pedirNombre = new JLabel(" NOMBRE DE USUARIO ");
		pedirNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font = new Font(Font.SERIF, Font.BOLD, 15);
		pedirNombre.setFont(font);
		pedirNombre.setBackground(null);
		pedirNombre.setForeground(Color.WHITE);
		pedirNombre.setOpaque(true);
		//Area donde se escribe
		campoNombre = new JTextField(7);
		campoNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
		campoNombre.addKeyListener(escucha);
		//Boton para identificar que ya estan los datos
		okInicio = new JButton("OK!");
		okInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
		okInicio.addActionListener(escucha);
		//Añadimos los componentes al panel y a la ventana
		datosUsuario.add(pedirNombre);
		datosUsuario.add(Box.createRigidArea(new Dimension(0,10)));
		datosUsuario.add(campoNombre);
		datosUsuario.add(Box.createRigidArea(new Dimension(0,10)));
		datosUsuario.add(okInicio);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = vista.getWidth();
		constraints.gridheight = vista.getHeight();
		add(datosUsuario, constraints);
		
		// Panel Fondo de pantalla
		fondo = new ImageIcon("src/images/FondoInicio.png");
		contenedorDeFondo = new JLabel(fondo);
		fondoPantalla = new JPanel();
		fondoPantalla.add(contenedorDeFondo);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = vista.getWidth();
		constraints.gridheight = vista.getHeight();
		add(fondoPantalla, constraints);
		
	}
	
	/**
	 * Cambiar de ventana.
	 * Desactiva esta ventana y activa la siguiente (Menu de opciones del juego)
	 */
	private void cambiarDeVentana() {
		//Desactivamos la ventana actual
		setVisible(false);
		setEnabled(false);
		//Activamos la ventana siguiente
		vistaGUIMenu.setVisible(true);
		vistaGUIMenu.setEnabled(true);
	}
	
	/**
	 * Crear file.
	 * Crea los archivos donde se almacenará la informacion de cada jugador
	 * @param nombreJugador the nombre jugador //El nombre del jugador se establece como nombre del archivo
	 */
	public void crearFile(String nombreJugador) {
		try {
			File file = new File("src/partidas/" + nombreJugador);
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The Class Escucha.
	 * Clase privada de escucha, monitorea los eventos en la ventana.
	 */
	private class Escucha extends KeyAdapter implements ActionListener {
		
		/**
		 * Action performed.
		 * Monitorea los eventos sobre los componentes la ventana
		 * @param eventAction the event action
		 */
		public void actionPerformed(ActionEvent eventAction) {
			//Si el usuario confirma su nombre
			if (eventAction.getSource() == okInicio) {
				//Si ya digito el nombre de usuario
				if (campoNombre.getText().length() != 0) {
					//Guardamos el nombre del usuario
					nombreJugador = campoNombre.getText();
					//Guardamos el nombre en un archivo distinto para usarlo mas adelante (Queda en la primera posicion del archivo)
					managerFiles.borrarArchivo("jugadorActual");
					managerFiles.escribirArchivo("jugadorActual", nombreJugador);
					//Validamos si el juegador entra por primera vez
					boolean guardarDatos = true;
					//Si el ciclo encuentra el nombre introducido en el historial de usuarios, no registra el nombre como nuevo
					for(int i = 0; i < historialJugadores.length; i++) {
						if (campoNombre.getText().equals(historialJugadores[i])) {
							guardarDatos = false;
							break;
						};
					}
					//En caso de que el nombre introducido sea nuevo
					if (guardarDatos == true) {
						//Guardamos el nombre en el historial de jugadores
						managerFiles.escribirArchivo("historialDeJugadores", campoNombre.getText());
						//Definimos si el jugador tiene partidas pendientes
						managerFiles.escribirArchivo("jugadorActual", "sinPartidaPendiente");
						crearFile(campoNombre.getText());
						/*
						 * Nota:
						 * En el archivo "jugadorActual" se guardaran tres datos
						 * 1. Nombre del jugador: (String) Nombre de Uuario
						 * 2. El jugador tiene pasado en el juego: (String) Si puede continuar una partida "conPartidaPendiente"
						 * 										de lo contrario, "sinPartidaPendiente"
						 * 3. El jugador quiere continuar una partida: (String) Si continua "true" (Serializa), de lo contrario "false" (No serialza)
						 */
					}
					//En caso de que el nombre ingresado no sea nuevo
					else {
						//Buscamos el archivo del jugador
						File file2 = new File("src/partidas/" + nombreJugador);
						//Si el archivo existe
						if (file2.exists()) {
							//Si el archivo esta vacio, se continua sin tener partidas pendientes
							if (file2.length() == 0) {
								managerFiles.escribirArchivo("jugadorActual", "sinPartidaPendiente");
							}
							//De lo contrario, se continua con la opcion de reanudar una partida guardada
							else {
								managerFiles.escribirArchivo("jugadorActual", "conPartidaPendiente");
							}
						}
					}
					//Cambiamos de ventana
					cambiarDeVentana();
				}
				//En caso de qeue el usuario de ok sin haber dado su nombre de usuario
				else {
					JOptionPane.showMessageDialog(null, "Por favor, digite un nombre de usuario", null, JOptionPane.DEFAULT_OPTION);
					okInicio.setFocusable(false);
					campoNombre.setFocusable(true);
				}
			}
			
		}
		
		
		/**
		 * Key pressed.
		 * Monitorea los eventos del teclado en la ventana
		 * @param eventKey the event key
		 */
		public void keyPressed(KeyEvent eventKey) {
			
			if(eventKey.getKeyCode() == KeyEvent.VK_ENTER) {
				//Si ya digito el nombre de usuario
				if (campoNombre.getText().length() != 0) {
					//Guardamos el nombre del usuario
					nombreJugador = campoNombre.getText();
					//Guardamos el nombre en un archivo distinto para usarlo mas adelante (Queda en la primera posicion del archivo)
					managerFiles.borrarArchivo("jugadorActual");
					managerFiles.escribirArchivo("jugadorActual", nombreJugador);
					//Validamos si el juegador entra por primera vez
					boolean guardarDatos = true;
					//Si el ciclo encuentra el nombre introducido en el historial de usuarios, no registra el nombre como nuevo
					for(int i = 0; i < historialJugadores.length; i++) {
						if (campoNombre.getText().equals(historialJugadores[i])) {
							guardarDatos = false;
							break;
						};
					}
					//En caso de que el nombre introducido sea nuevo
					if (guardarDatos == true) {
						//Guardamos el nombre en el historial de jugadores
						managerFiles.escribirArchivo("historialDeJugadores", campoNombre.getText());
						//Definimos si el jugador tiene partidas pendientes
						managerFiles.escribirArchivo("jugadorActual", "sinPartidaPendiente");
						crearFile(campoNombre.getText());
						/*
						 * Nota:
						 * En el archivo "jugadorActual" se guardaran tres datos
						 * 1. Nombre del jugador: (String) Nombre de Uuario
						 * 2. El jugador tiene pasado en el juego: (String) Si puede continuar una partida "conPartidaPendiente"
						 * 										de lo contrario, "sinPartidaPendiente"
						 * 3. El jugador quiere continuar una partida: (String) Si continua "true", de lo contrario "false"
						 */
					}
					//En caso de que el nombre ingresado no sea nuevo
					else {
						//Buscamos el archivo del jugador
						File file2 = new File("src/partidas/" + nombreJugador);
						//Si el archivo existe
						if (file2.exists()) {
							//Si el archivo esta vacio, se continua sin tener partidas pendientes
							if (file2.length() == 0) {
								managerFiles.escribirArchivo("jugadorActual", "sinPartidaPendiente");
							}
							//De lo contrario, se continua con la opcion de reanudar una partida guardada
							else {
								managerFiles.escribirArchivo("jugadorActual", "conPartidaPendiente");
							}
						}
					}
					//Cambiamos de ventana
					cambiarDeVentana();
				}
				//En caso de qeue el usuario de ok sin haber dado su nombre de usuario
				else {
					JOptionPane.showMessageDialog(null, "Por favor, digite un nombre de usuario", null, JOptionPane.DEFAULT_OPTION);
					okInicio.setFocusable(false);
					campoNombre.setFocusable(true);
				}
			}
			
		}
		
	}
	
}