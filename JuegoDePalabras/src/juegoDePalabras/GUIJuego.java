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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIJuego.
 * Ventana (JFrame) que administra los componentes graficos del juego
 */
public class GUIJuego extends JFrame {
	
	//Atributos
	private JLabel puntaje,						//Para el nombre "Puntaje"
				   nivel,						//Para el nombre "Nivel"
				   palabrasAcertadas,			//Para el nombre "Palabras Correctas"
				   palabrasErradas,				//Para el nombre "Palabras Incorrectas"
				   ingresar,					//Para el nombre "Ingresar"
				   tiempo,						//Tiempo para responder
				   serie;						//Para el nombre "Serie"
	private JTextField puntos,					//Puntos del jugador
					   nivelActual,				//Nivel del jugador
					   ingresarPalabra,			//Para ingresar la palabra que recuerda
					   serieActual;				//Serie en que va el jugador
	private JTextArea palabrasCorrectas,		//Palabras correctas que ha ingresado el jugador
					  palabrasIncorrectas;		//Palabras incorrectas que ha ingresado el jugador
	private JPanel areaDeRespuesta,				//Panel donde estan las opciones para responder
				   datosDelJuego,				//Panel que contiene los datos del juego
				   palabrasIntroducidas,		//Panel donde se muetran las palabras correctas e incorrestas
				   iniciar;						//Panel donde se inicia el nivel
	private JButton correcto,					//Brilla si la respuesta fue correcta
					incorrecto,					//Brilla si la respuesta fue incorrecta
					comprobar,					//Boton para comprobar si la palabra ingresada es correcta o no
					terminarSerie,				//Boton para terminar de ingresar palabras
					terminar,					//Boton para ir al inicio
					iniciarAhora;				//Boton para iniciar el nivel
	private Palabras palabras;					//Clase que controla las reglas del juego
	private String[] palabrasMostradas,			//Palabras que se mostraron en la serie actual
					 palabrasQueAcierto;		//Palabras que el jugador acerto en la serie
	private Escucha escucha;					//Clase que monitorea las acciones
	private JFrame vistaGUIMenu,
				   miMisma;
	private DibujarPalabras dibujarPalabras;
	private ManagerFiles managerFiles;
	private ManagerObjects managerObjects;
	private int centecimas = 100,				//Componentes del cronometro
				segundos = 59,
				minutos = 1,
				cuentaPalabras = 0;				//Cuenta las palabras que se muestran
	private Timer timerCronometro,
				  timerPalabras,
				  timer;
	private Sounds sounds;
	
	//Metodos
	/**
	 * Instantiates a new GUI juego.
	 * Constructor de la clase. Establece los parametros por defecto de la ventana e inicia los componentes grficos
	 * @param GUIMenu the GUI menu
	 */
	public GUIJuego(JFrame GUIMenu) {
		this.vistaGUIMenu = GUIMenu;
		initGUI();
		
		this.setTitle("Juego De Palabras");
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
		//Valores esenciales
		sounds = new Sounds();
		managerFiles = new ManagerFiles();
		managerObjects = new ManagerObjects();
		//MIramos si se hace serializacion o no
		String serializaONo = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[2];
		String nombreJugador = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[0];
		//La tercera posicion nos dice si se serializa o no
		if (serializaONo != null && serializaONo.equals("true")) {
			palabras = managerObjects.deserializarObjectoString(nombreJugador);
		}
		else {
			palabras = new Palabras();
		}
		palabras.setNombreJugador(nombreJugador);
		int largoArray = palabras.getNumeroPalabrasDeLaSerie() * 2;
		palabrasQueAcierto = new String[largoArray];
		escucha = new Escucha();
		timerCronometro = new Timer(10, escucha);
		timerPalabras = new Timer(3000, escucha);
		timer = new Timer(1000, escucha);
		miMisma = this;
		
		//Cambiamos el layout de la ventana
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Paneles de ditribucion de la ventana
		//Panel de datos del juego (Nivel,  Puntaje)
		datosDelJuego = new JPanel();
		datosDelJuego.setOpaque(false);
		puntaje = new JLabel("Puntaje: ");
		puntos = new JTextField(3);
		puntos.setText(Integer.toString(palabras.getPuntaje()));
		puntos.setEditable(false);
		nivel = new JLabel("Nivel: ");
		nivelActual = new JTextField(3);
		nivelActual.setText(Integer.toString(palabras.getNivel()));
		nivelActual.setEditable(false);
		serie = new JLabel("Serie: ");
		serieActual = new JTextField(3);
		serieActual.setText(Integer.toString(palabras.getSerie()));
		serieActual.setEditable(false);
		datosDelJuego.add(puntaje);
		datosDelJuego.add(puntos);
		datosDelJuego.add(nivel);
		datosDelJuego.add(nivelActual);
		datosDelJuego.add(serie);
		datosDelJuego.add(serieActual);
		datosDelJuego.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 24;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(datosDelJuego, constraints);
		
		//Panel contenedor de la informacion que el jugador escriba
		palabrasIntroducidas = new JPanel();
		palabrasIntroducidas.setLayout(new BoxLayout(palabrasIntroducidas, BoxLayout.Y_AXIS));
		palabrasIntroducidas.setBackground(new Color(30, 195, 240));
		palabrasAcertadas = new JLabel("Palabras acertadas");
		palabrasAcertadas.setAlignmentX(CENTER_ALIGNMENT);
		palabrasCorrectas = new JTextArea(10, 20);
		palabrasCorrectas.setEditable(false);
		JScrollPane scroll = new JScrollPane(palabrasCorrectas);
		palabrasErradas = new JLabel("Palabras erradas");
		palabrasErradas.setAlignmentX(CENTER_ALIGNMENT);
		palabrasIncorrectas = new JTextArea(10, 20);
		palabrasIncorrectas.setEditable(false);
		JScrollPane scroll2 = new JScrollPane(palabrasIncorrectas);
		palabrasIntroducidas.add(palabrasAcertadas);
		palabrasIntroducidas.add(scroll);
		palabrasIntroducidas.add(Box.createRigidArea(new Dimension(0,10)));
		palabrasIntroducidas.add(palabrasErradas);
		palabrasIntroducidas.add(scroll2);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 11;
		constraints.gridheight = 14;
		constraints.fill = GridBagConstraints.NONE;
		add(palabrasIntroducidas, constraints);
		
		//Panel donde se muestran las palabras
		dibujarPalabras = new DibujarPalabras();
		constraints.gridx = 11;
		constraints.gridy = 1;
		constraints.gridwidth = 14;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.NONE;
		add(dibujarPalabras, constraints);
		
		//Panel donde se responde
		areaDeRespuesta = new JPanel();
		areaDeRespuesta.setLayout(new BoxLayout(areaDeRespuesta, BoxLayout.Y_AXIS));
		areaDeRespuesta.setBackground(new Color(30, 195, 240));
		tiempo = new JLabel("02:00");
		tiempo.setAlignmentX(Component.CENTER_ALIGNMENT);
		ingresar = new JLabel("Ingrese una palabra");
		ingresar.setAlignmentX(Component.CENTER_ALIGNMENT);
		ingresarPalabra = new JTextField();
		ingresarPalabra.setPreferredSize(new Dimension(150,30));
		ingresarPalabra.setEnabled(false);
		ingresarPalabra.addKeyListener(escucha);
		ImageIcon iconUno = new ImageIcon("src/images/correcto.png");
		correcto = new JButton(iconUno);
		correcto.setEnabled(false);
		correcto.addActionListener(escucha);
		ImageIcon iconDos = new ImageIcon("src/images/incorrecto.png");
		incorrecto = new JButton(iconDos);
		incorrecto.setEnabled(false);
		incorrecto.addActionListener(escucha);
		//Area para saber si la palabra introcida es correcta o no
		JPanel correccion = new JPanel();
		correccion.setOpaque(false);
		correccion.add(ingresarPalabra);
		correccion.add(correcto);
		correccion.add(incorrecto);
		correccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		comprobar = new JButton("Comprobar");
		comprobar.setEnabled(false);
		comprobar.addActionListener(escucha);
		comprobar.setAlignmentX(Component.CENTER_ALIGNMENT);
		terminarSerie = new JButton("Terminar serie");
		terminarSerie.setEnabled(false);
		terminarSerie.addActionListener(escucha);
		terminar = new JButton("Terminar");
		terminar.addActionListener(escucha);
		//Area para terminar una serie o volver al inicio
		JPanel opciones = new JPanel();
		opciones.setOpaque(false);
		opciones.add(terminarSerie);
		opciones.add(terminar);
		opciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		areaDeRespuesta.add(tiempo);
		areaDeRespuesta.add(ingresar);
		areaDeRespuesta.add(correccion);
		areaDeRespuesta.add(comprobar);
		areaDeRespuesta.add(opciones);
		constraints.gridx = 11;
		constraints.gridy = 8;
		constraints.gridwidth = 14;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.NONE;
		add(areaDeRespuesta, constraints);
		areaDeRespuesta.setVisible(false);
		areaDeRespuesta.setEnabled(false);
		
		//Panel
		iniciar = new JPanel();
		iniciar.setLayout(new BoxLayout(iniciar, BoxLayout.Y_AXIS));
		iniciar.setBackground(new Color(30, 195, 240));
		iniciarAhora = new JButton("Iniciar ahora");
		iniciarAhora.addActionListener(escucha);
		iniciarAhora.setAlignmentX(Component.CENTER_ALIGNMENT);
		iniciar.add(iniciarAhora);
		constraints.gridx = 11;
		constraints.gridy = 8;
		constraints.gridwidth = 14;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.NONE;
		add(iniciar, constraints);
		
		//Inicimos el tiempo
		//timer.start();
		
	}
	
	/**
	 * Cronometro.
	 * Cronometro de tiempo
	 */
	private void cronometro() {
		centecimas--;
		if(centecimas == 0) {
			centecimas = 100;
			--segundos;
		}
		if(segundos == 0) {
			segundos = 60;
			--minutos;
		}
		String texto;
		if(minutos <= 9) {
			texto = "0" + minutos;
			}
		else {
			texto = Integer.toString(minutos);
			}
		if (segundos <= 9) {
			texto += ":0" + segundos;
			} 
		else {
			texto += ":" + Integer.toString(segundos);
			};
		tiempo.setText(texto);
	}
	
	/**
	 * Mostrar palabras.
	 * Elige y muestra las palabras correspondientes a cada serie
	 */
	private void mostrarPalabras() {
		int largoArray = palabras.getNumeroPalabrasDeLaSerie();
		palabrasMostradas = managerFiles.elegirpalabrasArchivo("palabrasParaRecordar", largoArray, palabras.getNumeroPalabrasMostradas());

		dibujarPalabras.setPalabra(palabrasMostradas[cuentaPalabras]);
		cuentaPalabras++;
	}
	
	/**
	 * Comprobar palabra.
	 * Comprueba que la palabra que el jugador ingreso sea correcta
	 */
	private void comprobarPalabra() {
		//boolean respuesta;
		String palabraAProbar = ingresarPalabra.getText();
		
		for (int i = 0; i < palabras.getNumeroPalabrasDeLaSerie(); i++) {
			//Si la palabra que se digito esta entre las mostradas
			if (palabraAProbar.equals(palabrasMostradas[i])) {
				//Si la palabra es repetida no hace nada
				if (comprobarPalabrasAcertadasRepetidas(palabraAProbar) == true) {
					break;
				}
				//Si no es repetida, suma la palabra acertada
				if (comprobarPalabrasAcertadasRepetidas(palabraAProbar) == false) {
					palabrasQueAcierto[palabras.getNumeroAciertos()] = palabraAProbar;
					palabras.sumarAcierto();
					puntos.setText(Integer.toString(palabras.getPuntaje()));
					managerFiles.escribirArchivo("palabrasCorrectas", palabraAProbar);
					palabrasCorrectas.setText(managerFiles.abrirArchivo("palabrasCorrectas"));
					ingresarPalabra.setText("");
					correcto.setEnabled(true);
					sounds.playSound(2);
					incorrecto.setEnabled(false);
					break;
				}
			}
			//Si la palabra no esta entre las mostradas
			else {
				//Si estamos en la ultima posicion del bucle
				if (i == palabras.getNumeroPalabrasDeLaSerie() - 1) {
					palabras.sumarFallo();
					puntos.setText(Integer.toString(palabras.getPuntaje()));
					managerFiles.escribirArchivo("palabrasIncorrectas", palabraAProbar);
					palabrasIncorrectas.setText(managerFiles.abrirArchivo("palabrasIncorrectas"));
					ingresarPalabra.setText("");
					incorrecto.setEnabled(true);
					sounds.playSound(1);
					correcto.setEnabled(false);
					break;
				}
			}
		}
	}
	
	/**
	 * Avanzar.
	 * Pasa la serie o el nivel dependiendo del caso
	 */
	private void avanzar() {
		int palabrasSerieActual = palabras.getNumeroPalabrasDeLaSerie();
		//Si estamos en la segunda serie
		if (palabras.getSerie() == 2) {
			//Si ya acerto todas las palabras
			if ((palabras.getNumeroAciertos() == palabrasSerieActual * 2) || (minutos == 0 && segundos == 1)) {
				tiempo.setText("02:00");
				palabras.pasarSerie(false);
				
				palabras.sumarPalabrasMostradas(palabrasSerieActual);
				bloquearPanelDelJugador();
				serieActual.setText(Integer.toString(palabras.getSerie()));
				nivelActual.setText(Integer.toString(palabras.getNivel()));
				minutos = 1;
				segundos = 59;
				centecimas = 100;
				cuentaPalabras = 0;
				int largoArray = palabras.getNumeroPalabrasDeLaSerie() * 2;
				palabrasQueAcierto = new String[largoArray];
				managerFiles.borrarArchivo("palabrasCorrectas");
				palabrasCorrectas.setText(managerFiles.abrirArchivo("palabrasCorrectas"));
				managerFiles.borrarArchivo("palabrasIncorrectas");
				palabrasIncorrectas.setText(managerFiles.abrirArchivo("palabrasIncorrectas"));
				comprobarSiPuedePasarDeNivel();
			}
		}
		//Si estamos en la primera serie
		if (palabras.getSerie() == 1) {
			//Si acerto todas las palabras de la primera serie
			if ((palabras.getNumeroAciertos() == palabrasSerieActual) || (minutos == 0 && segundos == 1)) {
				tiempo.setText("02:00");
				palabras.pasarSerie(false);
				palabras.sumarPalabrasMostradas(palabrasSerieActual);
				timerCronometro.stop();
				timerPalabras.start();
				bloquearPanelDelJugador();
				serieActual.setText(Integer.toString(palabras.getSerie()));
				minutos = 1;
				segundos = 59;
				centecimas = 100;
				cuentaPalabras = 0;
			}
		}	
	}
	
	/**
	 * Comprobar si puede pasar de nivel.
	 * Comprobamos si se han cumplido los requisitos para pasar de nivel
	 */
	private void comprobarSiPuedePasarDeNivel() {
		//Ya ganó
		if(palabras.getNivel() == 5 && palabras.getNumeroAciertos() >= palabras.getNumeroPalabrasParaSuperarNivel()) {
			tiempo.setText("02:00");
			JOptionPane.showMessageDialog(null,
					  					  "FELICIDADES, HAZ GANADO :)",
					  					  null,
					  					  JOptionPane.DEFAULT_OPTION);
			reiniciarValores();
			puntos.setText(Integer.toString(palabras.getPuntaje()));
			nivelActual.setText(Integer.toString(palabras.getNivel()));
			serieActual.setText(Integer.toString(palabras.getSerie()));
			String nombre = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[0];
			managerFiles.borrarArchivo("jugadorActual");
			managerFiles.escribirArchivo("jugadorActual", nombre);
			managerFiles.escribirArchivo("jugadorActual", "sinPartidaPendiente");
			timerCronometro.stop();
			timerPalabras.stop();
			//timer.start();
			iniciar.setVisible(true);
			iniciar.setEnabled(true);
			areaDeRespuesta.setVisible(false);
			areaDeRespuesta.setEnabled(false);
			this.setEnabled(false);
			this.setVisible(false);
			vistaGUIMenu.setEnabled(true);
			vistaGUIMenu.setVisible(true);
		}
		//Si cumple el numero de aciertos minimos
		else if (palabras.getNumeroAciertos() >= palabras.getNumeroPalabrasParaSuperarNivel()) {
			palabras.pasarNivel();
			palabras.reglasParaAvanzar();
			tiempo.setText("02:00");
			JOptionPane.showMessageDialog(null,
										  "Felicidades, pasas de nivel\n" +
										  "En este nivel debes acertar minimo " +
										  palabras.getNumeroPalabrasParaSuperarNivel() + 
										  " palabras para pasar seguir avanzando.",
										  null,
										  JOptionPane.DEFAULT_OPTION);
			timerCronometro.stop();
			timerPalabras.start();
		}
		//Si no alcanzo el minimo
		else {
			tiempo.setText("02:00");
			JOptionPane.showMessageDialog(null,
					  					  "Oh no, has perdido\n" +
					  					  "No haz alcanzado el minimo de palabras acertadas para avanzar.",
					  					  null,
					  					  JOptionPane.DEFAULT_OPTION);
			reiniciarValores();
			puntos.setText(Integer.toString(palabras.getPuntaje()));
			nivelActual.setText(Integer.toString(palabras.getNivel()));
			serieActual.setText(Integer.toString(palabras.getSerie()));
			String nombre = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[0];
			managerFiles.borrarArchivo("jugadorActual");
			managerFiles.escribirArchivo("jugadorActual", nombre);
			managerFiles.escribirArchivo("jugadorActual", "sinPartidaPendiente");
			timerCronometro.stop();
			timerPalabras.stop();
			//timer.start();
			iniciar.setVisible(true);
			iniciar.setEnabled(true);
			areaDeRespuesta.setVisible(false);
			areaDeRespuesta.setEnabled(false);
			this.setEnabled(false);
			this.setVisible(false);
			vistaGUIMenu.setEnabled(true);
			vistaGUIMenu.setVisible(true);
		}
	}
	
	/**
	 * Bloquear panel del jugador.
	 * Bloque el panel donde el jugador escribe, para que con pueda interactuar conel
	 */
	private void bloquearPanelDelJugador() {
		ingresarPalabra.setEnabled(false);
		correcto.setEnabled(false);
		incorrecto.setEnabled(false);
		comprobar.setEnabled(false);
		terminarSerie.setEnabled(false);
		
	}
	
	/**
	 * Comprobar palabras acertadas repetidas.
	 * Comprueba si la palabra introducida ya la a acertado o no
	 * @param palabraAComprobar the palabra A comprobar
	 * @return true, if successful //true -> repetida, false -> no repetida
	 */
	private boolean comprobarPalabrasAcertadasRepetidas(String palabraAComprobar) {
		boolean respuesta = false;
		for (int i = 0; i < palabrasQueAcierto.length; i++) {
			//Si el jugador ya a acertado palabras
			if (palabrasQueAcierto[i] != null) {
				//Si ya acerto la palabbra actual
				if (palabrasQueAcierto[i].equals(palabraAComprobar)) {
					JOptionPane.showMessageDialog(null,
		  					  "Esta palabra ya fue acertada, por favor digita una distinta",
		  					  null,
		  					  JOptionPane.DEFAULT_OPTION);
					ingresarPalabra.setText("");
					respuesta =  true;
				}
			}
		}
		return respuesta;
		
	}
	
	/**
	 * Reiniciar valores.
	 * Reinicia los valores por defecto del juego
	 */
	public void reiniciarValores() {
		palabras.reiniciar();
		centecimas = 100;
		segundos = 59;
		minutos = 1;
		cuentaPalabras = 0;
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
			if (!miMisma.isVisible()) {
				timerPalabras.stop();
			}
			puntos.setText(Integer.toString(palabras.getPuntaje()));
			nivelActual.setText(Integer.toString(palabras.getNivel()));
			serieActual.setText(Integer.toString(palabras.getSerie()));
			ingresarPalabra.requestFocus();
			//Si el tiempo de mostrar las palabras esta corriendo
			if(timerPalabras.isRunning()) {
				if(cuentaPalabras >= palabras.getNumeroPalabrasDeLaSerie()) {
					timerPalabras.stop();
					cuentaPalabras = 0;
					dibujarPalabras.setPalabra("");
					timerCronometro.start();
					ingresarPalabra.setEnabled(true);
					ingresarPalabra.requestFocus();
					terminarSerie.setEnabled(true);
				}
				else {
					mostrarPalabras();
				}
			}
			//Si el timepo del cronometro esta corriendo
			if(timerCronometro.isRunning()) {
				cronometro();
				avanzar();
			}
			//SI el tiempo de las palabras no esta corriendo
			if(timerPalabras.isRunning() == false && timer.isRunning() == false) {
				//Activamos botones
				if(ingresarPalabra.getText().length() != 0) {
					comprobar.setEnabled(true);
				}
				else {
					comprobar.setEnabled(false);
				}
			}
			//Si hay mas de una letra en el campo de respuesta, desactivamos las opciones de correcto o incorrecto
			if(ingresarPalabra.getText().length() == 1) {
				correcto.setEnabled(false);
				incorrecto.setEnabled(false);
			}
			//Si terminamos el juego
			if(eventAction.getSource() == terminar) {
				//Serializamos
				String nombre = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[0];
				palabras.pasarSerie(true);
				managerObjects.serializarObjeto(palabras.getNombreJugador(), palabras);
				//Guardamos el nombre, nivel y puntaje
				String lineaAAñadir = String.format("Jugador: \t%s,\tNivel: \t%d,\tPuntaje: \t%d", nombre, palabras.getNivel(), palabras.getPuntaje());
				managerFiles.escribirArchivo("mostrarNombreNivel", lineaAAñadir);
				reiniciarValores();
				ingresarPalabra.setText("");
				palabras.yaHaJugado();
				tiempo.setText("02:00");
				puntos.setText(Integer.toString(palabras.getPuntaje()));
				nivelActual.setText(Integer.toString(palabras.getNivel()));
				serieActual.setText(Integer.toString(palabras.getSerie()));
				vistaGUIMenu.setVisible(true);
				vistaGUIMenu.setEnabled(true);
				miMisma.setVisible(false);
				miMisma.setEnabled(false);
				iniciar.setVisible(true);
				iniciar.setEnabled(true);
				areaDeRespuesta.setVisible(false);
				areaDeRespuesta.setEnabled(false);
				managerFiles.borrarArchivo("palabrasCorrectas");
				palabrasCorrectas.setText(managerFiles.abrirArchivo("palabrasCorrectas"));
				managerFiles.borrarArchivo("palabrasIncorrectas");
				palabrasIncorrectas.setText(managerFiles.abrirArchivo("palabrasIncorrectas"));
				timerCronometro.stop();
				timerPalabras.stop();
				//timer.start();
				managerFiles.borrarArchivo("jugadorActual");
				managerFiles.escribirArchivo("jugadorActual", nombre);
				managerFiles.escribirArchivo("jugadorActual", "conPartidaPendiente");
			}
			//SI comprobamos una palabra
			if(eventAction.getSource() == comprobar) {
				if (ingresarPalabra.getText().length() != 0) {
					comprobarPalabra();
				}
				else {
					JOptionPane.showMessageDialog(null,
												 "Campo vacio, por favor digite una palabra",
												 null,
												 JOptionPane.DEFAULT_OPTION);
				}
				
			}
			//Si apenas vamos a iniciar el juego
			if(eventAction.getSource() == iniciarAhora) {
				timer.start();
				//Si el tiempo normal esta corriendo, comprobamos de nuevo la serializacion
				if(timer.isRunning()) {
					managerFiles = new ManagerFiles();
					String serializaONo = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[2];
					String nombreJugador = managerFiles.elegirpalabrasArchivo("jugadorActual", 3, 0)[0];
					if (serializaONo != null && serializaONo.equals("true")) {
						palabras = managerObjects.deserializarObjectoString(nombreJugador);
					}
					else {
						palabras = new Palabras();
					}
					palabras.setNombreJugador(nombreJugador);
					timer.stop();
				}
				puntos.setText(Integer.toString(palabras.getPuntaje()));
				nivelActual.setText(Integer.toString(palabras.getNivel()));
				serieActual.setText(Integer.toString(palabras.getSerie()));
				iniciar.setVisible(false);
				iniciar.setEnabled(false);
				areaDeRespuesta.setVisible(true);
				areaDeRespuesta.setEnabled(true);
				palabras.reglasParaAvanzar();
				JOptionPane.showMessageDialog(null,
											  "En este nivel debes acertar minimo " +
											  palabras.getNumeroPalabrasParaSuperarNivel() + 
											  " palabras para pasar seguir avanzando.",
											  null,
											  JOptionPane.DEFAULT_OPTION);
				
				
				timerPalabras.start();
			}
			//Si quiero terinar una serie
			if(eventAction.getSource() == terminarSerie) {
				int palabrasSerieActual = palabras.getNumeroPalabrasDeLaSerie();
				//Si estamos en la segunda serie
				if (palabras.getSerie() == 2) {
					tiempo.setText("02:00");
					palabras.pasarSerie(false);
					comprobarSiPuedePasarDeNivel();
					palabras.sumarPalabrasMostradas(palabrasSerieActual);
					bloquearPanelDelJugador();
					serieActual.setText(Integer.toString(palabras.getSerie()));
					nivelActual.setText(Integer.toString(palabras.getNivel()));
					minutos = 1;
					segundos = 59;
					centecimas = 100;
					cuentaPalabras = 0;
					int largoArray = palabras.getNumeroPalabrasDeLaSerie() * 2;
					palabrasQueAcierto = new String[largoArray];
					managerFiles.borrarArchivo("palabrasCorrectas");
					palabrasCorrectas.setText(managerFiles.abrirArchivo("palabrasCorrectas"));
					managerFiles.borrarArchivo("palabrasIncorrectas");
					palabrasIncorrectas.setText(managerFiles.abrirArchivo("palabrasIncorrectas"));
					ingresarPalabra.setText("");
				}
				//Si estamos en la primera serie
				if (palabras.getSerie() == 1) {
					tiempo.setText("02:00");
					palabras.pasarSerie(false);
					palabras.sumarPalabrasMostradas(palabrasSerieActual);
					timerCronometro.stop();
					timerPalabras.start();
					bloquearPanelDelJugador();
					serieActual.setText(Integer.toString(palabras.getSerie()));
					minutos = 1;
					segundos = 59;
					centecimas = 100;
					cuentaPalabras = 0;
					ingresarPalabra.setText("");
				}
			}
		}

		/**
		 * Key typed.
		 * @param eventKey the event key
		 */
		@Override
		public void keyTyped(KeyEvent eventKey) {
			//Convertimos cada letra introducida por el jugador en mayuscula
			if (eventKey.getSource() == ingresarPalabra) {
				char c = eventKey.getKeyChar();
				if (Character.isLowerCase(c)) {
					String cad = (""+c).toUpperCase();
					c = cad.charAt(0);
					eventKey.setKeyChar(c);
				}
			}
		}

		/**
		 * Key pressed.
		 * @param eventKey the event key
		 */
		@Override
		public void keyPressed(KeyEvent eventKey) {
			//Si comprueba con enter
			if(eventKey.getKeyCode() == KeyEvent.VK_ENTER) {
				if (ingresarPalabra.getText().length() != 0) {
					comprobarPalabra();
				}
				else {
					JOptionPane.showMessageDialog(null, "Campo vacio, por favor digite una palabra", null, JOptionPane.DEFAULT_OPTION);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
}
