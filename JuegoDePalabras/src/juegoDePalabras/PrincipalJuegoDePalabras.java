/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.awt.EventQueue;

import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class principalJuegoDePalabras.
 * Clase principal, contiene el metodo main que inicia todo
 */
public class PrincipalJuegoDePalabras {
	
	/**
	 * The main method.
	 * Inicia el juego o la GUI del mismo.
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			//Esto se hace para que el programa tenga la misma apariencia en todos los sistemas operativos.
			String javaLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(javaLookAndFeel);
		} catch (Exception e) {
			
		}
		EventQueue.invokeLater(new Runnable() {
			//Iniciamos la interfaz del juego
			public void run() {	
				GUIMenu ventana = new GUIMenu();
			}
		});	
	}
}
