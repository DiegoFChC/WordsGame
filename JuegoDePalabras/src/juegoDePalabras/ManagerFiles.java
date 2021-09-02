/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class ManagerFiles.
 * Clase encargada del flujo de datos
 */
public class ManagerFiles {
	//Atributos
	private FileReader fileReader;						//Para leer archivos
	private BufferedReader input;						//Bufer de lectura, guarda directamente en la memoria, input porque es de entrada
	private FileWriter fileWriter;						//Para escribir en archivos
	private BufferedWriter output;						//Bufer de escritura
	
	//Metodos
	/**
	 * Abrir archivo.
	 * Lee un archivo de texto
	 * @param nombreArchivo the nombre archivo
	 * @return the string // Retorna lo que hay dentro del archivo leido
	 */
	public String abrirArchivo(String nombreArchivo) {
		String salida = "";
		try {
			fileReader = new FileReader("src/resources/" + nombreArchivo);
			input = new BufferedReader(fileReader);
			
			String texto = input.readLine();
			while(texto != null) {
				salida += texto;
				salida += "\n";
				texto = input.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return salida;
	}
	
	/**
	 * Elegir palabras archivo.
	 * Escoge un archivo, y elige un nuemro de palabras de el
	 * @param nombreArchivo the nombre archivo
	 * @param numeroLargoArray the numero largo array //Numero de palabras que contendra el array
	 * @param palabrasExcluidas the palabras excluidas //Numero de palabras que no quiere que se lean al inicio
	 * @return the string[] //Array con las palabras elegidas
	 */
	public String[] elegirpalabrasArchivo(String nombreArchivo, int numeroLargoArray, int palabrasExcluidas) {
		int numero = numeroLargoArray;
		String[] arrayPalabras = new String[numero];
		String palabraNoUsada;
		int palabrasYaUsadas = palabrasExcluidas;
		try {
			fileReader = new FileReader("src/resources/" + nombreArchivo);
			input = new BufferedReader(fileReader);
			int indice = 0;
			for (int i = 0; i < numero + palabrasYaUsadas; i++) {
				if (i >= palabrasYaUsadas) {
					while(indice != numero) {			
						arrayPalabras[indice] = input.readLine();
						indice++;
					}
					break;
				}
				else {
					palabraNoUsada = input.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return arrayPalabras;
		
	}
	
	/**
	 * Definir largo array jugadores.
	 * Lee y determina el numero de jugadores que han jugado
	 * @return the int
	 */
	private int definirLargoArrayJugadores() {
		int numeroJugadores = 0;
		try {
			fileReader = new FileReader("src/resources/historialDeJugadores");
			input = new BufferedReader(fileReader);
			//Identificamos el tamaño del array
			int jugadores = 0;
			String texto = input.readLine();
			while(texto != null) {
				texto = input.readLine();
				jugadores++;
			}
			numeroJugadores = jugadores;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return numeroJugadores;
	}
	
	/**
	 * Leer historial jugadores.
	 * Lee y retorna el historial de los jugadores
	 * @return the string[] //Array de juagdores
	 */
	public String[] leerHistorialJugadores() {
		String[] arrayNombres;
		arrayNombres = new String[definirLargoArrayJugadores()];
		try {
			fileReader = new FileReader("src/resources/historialDeJugadores");
			input = new BufferedReader(fileReader);
			
			int indice = 0;
			String texto = input.readLine();
			while(texto != null) {
				arrayNombres[indice] = texto;
				texto = input.readLine();
				indice++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arrayNombres;
	}
	
	/**
	 * Escribir archivo.
	 * Escribe en un archivo los datos que se deseenS
	 * @param nombreArchivo the nombre archivo //Donde se va a escribir
	 * @param linea the linea //Lo que se va a escribir
	 */
	public void escribirArchivo(String nombreArchivo, String linea) {
		try {
			fileWriter = new FileWriter("src/resources/" + nombreArchivo, true);
			output = new BufferedWriter(fileWriter);
			
			output.write(linea);
			output.newLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Borrar archivo.
	 * Borra todo lo que halla dentro de un archivo
	 * @param nombreArchivo the nombre archivo
	 */
	public void borrarArchivo(String nombreArchivo) {
		try {
			fileWriter = new FileWriter("src/resources/" + nombreArchivo, false);
			output = new BufferedWriter(fileWriter);
			
			output.write("");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}