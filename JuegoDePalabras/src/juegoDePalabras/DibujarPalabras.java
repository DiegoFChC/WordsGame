/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class DibujarPalabras.
 * JPanel donde se dibujan graficos 2D
 */
public class DibujarPalabras extends JPanel implements ActionListener {
	
	//Atributos
	public final Font FUENTE = new Font(Font.DIALOG, Font.BOLD + Font.ITALIC, 30);
	private String palabra;					//Palabra que se va a pintar
	private int x,							//Posicion x de la palabra
				y;							//Posicion y de la palabra
	private Timer timer;
		
	//Metodos
	
	/**
	 * Instantiates a new dibujar palabras.
	 * Constructor de la clase. Establece los parametros por defecto
	 */
	public DibujarPalabras() {
		//Inicializamos la palabra
		palabra = "";
		this.setPreferredSize(new Dimension(300, 200));
		x = 300;
		y = 110;
		timer = new Timer(10, this);
	}
	
	/**
	 * Sets the palabra.
	 * Establece la palabra que se pinta
	 * @param palabra the new palabra
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
		
		//Llamamos el mertodo de pintado
		repaint();
		timer.start();
	}
	
	/**
	 * Paint component.
	 * Pinta o dibuja
	 * @param g the g
	 */
	public void paintComponent(Graphics g) {
		//Metodo padre
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		//Fondo
		GradientPaint paint = new GradientPaint(100, 300, new Color(0, 185, 206), this.getWidth(), this.getHeight(), new Color(160, 206, 222));
		
		//g2D.setColor(Color.GRAY);
		g2D.setPaint(paint);
		g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2D.setFont(FUENTE);
		g2D.setColor(Color.BLACK);
		
		//Pintamos el texto
		//g2D.drawString(palabra, (this.getWidth()/2) - ((palabra.length()/2) * 27), (this.getHeight()/2) + (this.getHeight()/15));
		g2D.drawString(palabra, x, y);
	}
	
	/**
	 * Action performed.
	 * Monitorea los eventos sobre los componentes la ventana
	 * @param eventAction the event action
	 */
	public void actionPerformed(ActionEvent e) {
		x -= 2;
		repaint();
		
		if (x == 0) {
			timer.stop();
			x = 300;
		}
	}
}