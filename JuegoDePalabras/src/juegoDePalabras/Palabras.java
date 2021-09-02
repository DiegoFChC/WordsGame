/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 3. Juego de Palabras
 */

package juegoDePalabras;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Palabras.
 * Clase encargada de las reglas del juego
 */
public class Palabras implements Serializable {
	//Atributos
	private int nivel,								//Nivel del juego
				serie,								//Serie en la que se encuentra el nivel
				puntaje,							//Puntaje del jugador
				numeroFallos,						//Fallos al recordar palabras
				numeroAciertos,						//Aciertos al recordar palabras
				numeroPalabrasDeLaSerie,			//Numero de palabras que se mostraran en cada serie
				numeroPalabrasParaSuperarNivel,		//Numero minimo de palabras correctas para pasar de nivel
				numeroPalabrasMostradas;			//Numero de palabras que ha visto el usuario
	private boolean primeraVez;						//True -> Primera vez que juega. False -> Lo contrario
	private String nombreJugador;					//Nombre del jugador actual
	
	//Metodos
	/**
	 * Instantiates a new palabras.
	 * Contructor de la clase, define los valores iniciales
	 */
	public Palabras() {
		nivel = 1;
		serie = 1;
		reglasParaAvanzar();
		puntaje = 0;
		primeraVez = true;
	}
	
	/**
	 * Reglas para avanzar.
	 * Define las reglas para avanzar de nivel
	 */
	public void reglasParaAvanzar() {
		switch(nivel) {
		case 1:
			numeroPalabrasDeLaSerie = 4;
			numeroPalabrasParaSuperarNivel = 7;
			break;
		case 2:
			numeroPalabrasDeLaSerie = 6;
			numeroPalabrasParaSuperarNivel = 9;
			break;
		case 3:
			numeroPalabrasDeLaSerie = 8;
			numeroPalabrasParaSuperarNivel = 12;
			break;
		case 4:
			numeroPalabrasDeLaSerie = 10;
			numeroPalabrasParaSuperarNivel = 15;
			break;
		case 5:
			numeroPalabrasDeLaSerie = 12;
			numeroPalabrasParaSuperarNivel = 18;
			break;
		}
	}
	
	/**
	 * Sumar fallo.
	 * Suma los fallos del jugador
	 */
	public void sumarFallo() {
		numeroFallos += 1;
		puntaje -= 5;
	}
	
	/**
	 * Sumar acierto.
	 * Suma los aciertos del jugador
	 */
	public void sumarAcierto() {
		numeroAciertos += 1;
		puntaje += 5;
	}
	
	/**
	 * Pasar nivel.
	 * Pasa el nivel si el jugaor cumple los requisitos
	 */
	public void pasarNivel() {
		if (nivel == 5) {
			nivel = 1;
			serie = 1;
			numeroFallos = 0;
			numeroAciertos = 0;
		}
		else {
			nivel += 1;
			serie = 1;
			numeroFallos = 0;
			numeroAciertos = 0;
		}
	}
	
	/**
	 * Pasar serie.
	 * Pasa la serie del juego si el jugador cumple los requisitos
	 * @param pasaPorTerminar the pasa por terminar //True -> Pasa serie por terminar, false -> pasa serie por ganar
	 */
	public void pasarSerie(boolean pasaPorTerminar) {
		if (pasaPorTerminar == true) {
			serie = 1;
		}
		else {
			if (serie == 1) {
				serie++;
			}
			else {
				serie = 1;
			}
		}
	}
	
	/**
	 * Reiniciar.
	 * Devuelve los valores del juego a los por defecto
	 */
	public void reiniciar() {
		nivel = 1;
		serie = 1;
		puntaje = 0;
		numeroFallos = 0;
		numeroAciertos = 0;
	}
	
	/**
	 * Sumar palabras mostradas.
	 * Calcula las palabras que ya han sido mostradas
	 * @param cuantoSuma the cuanto suma //Cuantas palabras suma
	 */
	public void sumarPalabrasMostradas(int cuantoSuma) {
		numeroPalabrasMostradas += cuantoSuma;
		if (numeroPalabrasMostradas >= 80) {
			numeroPalabrasMostradas = 0;
		}
	}
	
	/**
	 * Ya ha jugado.
	 * Determina si el jugador ya ha jugado una vez como minimo
	 */
	public void yaHaJugado() {
		primeraVez = false;
	}
	
	/**
	 * Sets the nombre jugador.
	 * Cambia el nombre del jugador
	 * @param jugador the new nombre jugador
	 */
	public void setNombreJugador(String jugador) {
		this.nombreJugador = jugador;
	}
	
	/**
	 * Gets the nivel.
	 * Retorna el nivel actual
	 * @return the nivel
	 */
	public int getNivel() {
		return nivel;
	}
	
	/**
	 * Gets the puntaje.
	 * Retorna el puntaje actual
	 * @return the puntaje
	 */
	public int getPuntaje() {
		if (puntaje < 0) {
			puntaje = 0;
		}
		return puntaje;
	}
	
	/**
	 * Gets the serie.
	 * Retorna el numero de la serie actual
	 * @return the serie
	 */
	public int getSerie() {
		return serie;
	}

	/**
	 * Gets the numero palabras de la serie.
	 * Retorna el numero de palabras que se muestran en cada serie
	 * @return the numero palabras de la serie
	 */
	public int getNumeroPalabrasDeLaSerie() {
		return numeroPalabrasDeLaSerie;
	}

	/**
	 * Gets the numero palabras para superar nivel.
	 * Retorna el minimo de palabras que debe acertar para pasar de nivel
	 * @return the numero palabras para superar nivel
	 */
	public int getNumeroPalabrasParaSuperarNivel() {
		return numeroPalabrasParaSuperarNivel;
	}

	/**
	 * Gets the numero palabras mostradas.
	 * Retorna el numero de palabras que ya se han mostrado
	 * @return the numero palabras mostradas
	 */
	public int getNumeroPalabrasMostradas() {
		return numeroPalabrasMostradas;
	}

	/**
	 * Gets the numero fallos.
	 * Retorna el numero de fallos que se han tenido
	 * @return the numero fallos
	 */
	public int getNumeroFallos() {
		return numeroFallos;
	}

	/**
	 * Gets the numero aciertos.
	 * Retorna el numero de aciertos que se han tenido
	 * @return the numero aciertos
	 */
	public int getNumeroAciertos() {
		return numeroAciertos;
	}

	/**
	 * Checks if is primera vez.
	 * Retorna si es o no la primera vez que el jugador juega
	 * @return true, if is primera vez
	 */
	public boolean isPrimeraVez() {
		return primeraVez;
	}

	/**
	 * Gets the nombre jugador.
	 * Retorna el nombre del Jugador
	 * @return the nombre jugador
	 */
	public String getNombreJugador() {
		return nombreJugador;
	}
	
}
