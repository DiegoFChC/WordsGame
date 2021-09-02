/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class ManagerObjects.
 * Clase manejadora de la serializacion
 */
public class ManagerObjects {
	//Atributos
	private FileInputStream fileInput;				//Clase para acceder a la ruta (lectura)
	private ObjectInputStream input;				//Bufer que guarda la lectura
	private FileOutputStream fileOutput;			//Clase para escritura
	private ObjectOutputStream output;				//Bufer para la escritura
	
	//Metodos
	/**
	 * Serializar objeto.
	 * Guarda un objeto en un archivo
	 * @param nombreJugador the nombre jugador //nombre del acrivho
	 * @param palabras the palabras //Objeto a guardar
	 */
	public void serializarObjeto(String nombreJugador, Palabras palabras) {

		//Direccion de ubicacion donde esta el archivo de escritura
		try {
            String ruta = "src/partidas/" + nombreJugador;
            fileOutput = new FileOutputStream(ruta);
			output = new ObjectOutputStream(fileOutput);
			
			//Escribimos en el archivo
			output.writeObject(palabras);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Deserializar objecto string.
	 * Saca un objeto guardado en un archivo
	 * @param nombreJugador the nombre jugador //nombre del archivo
	 * @return the palabras //Objecto a deserializar
	 */
	//Lee o saca el objeto que esta en un archivo (deserializacion)
	public Palabras deserializarObjectoString (String nombreJugador) {

		Palabras palabras = null;
		//Ubicamos el archivo
		try {
			fileInput = new FileInputStream("src/partidas/" + nombreJugador);
			//Bufer de lectura, fujo de datos de lectura
			input = new ObjectInputStream(fileInput);
			//Lo devolvemos al objeto palabra
			palabras = (Palabras)input.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return palabras;
	}
	
}
