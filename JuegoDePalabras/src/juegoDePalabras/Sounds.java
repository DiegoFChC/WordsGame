/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// TODO: Auto-generated Javadoc
/**
 * The Class Sounds.
 * Clase que maneja los sonidos del juego
 */
public class Sounds {
	//Atributos
	File fileSound;
	AudioInputStream audioStream;					//Secuencia de entrada de audio
	Clip clip;										//Clip de audio
	
	//Metodos
	/**
	 * Instantiates a new sounds.
	 * Constructor de la clase, inicia los valores predeterminados
	 */
	public Sounds() {
		try {
			//Buscamos el archivo de audio y lo ejecutamos (Esta es la pista que se reproducira todo el juego
			fileSound = new File("src/sounds/background.wav");
			audioStream = AudioSystem.getAudioInputStream(fileSound);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
			clip.start();
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Play sound.
	 * Reproduce un sonido dependiendo de la accion
	 * @param numberSound the number sound // 1: Sonido de error, 2: Sonido de acierto (a la hora de ingresar palabras)
	 */
	public void playSound(int numberSound) {
		//Nombre del archivod e audio
		String sound = "";
		//Segun la entrada, definimos el nombre del archivo de audio
		switch(numberSound){
		case 1: //Incorrect
			sound = "incorrect.wav";
			break;
		case 2: //Correct
			sound = "correct.wav";
			break;
		}
		
		//Ejecutamos el audio
		try {
			
			fileSound = new File("src/sounds/" + sound);
			audioStream = AudioSystem.getAudioInputStream(fileSound);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
