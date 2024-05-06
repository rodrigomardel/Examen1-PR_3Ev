package jcolonia.daw2024.quiniela;

import java.util.Scanner;

/**
 * Vista del menú general para la interacción con el usuario.
 * 
 * @version 1.0 (20240503000)
 * @author Rodrigo Martínez Delgado &lt;rodrigo.mardel.daw@gmail.com&gt;
 */
public class VistaMenú extends VistaGeneral {

	/** Opciones almacenadas. */
	private String[] listaOpciones;
	/** Opciones numéricas del menú. */
	private int opción;


	/**
	 * Inicializa y asigna el nombre del menú, la lista de opciones y la entrada
	 * recibida.
	 * 
	 * @param nombre        nombre deseado
	 * @param listaOpciones lista de opciones recibida
	 */
	public VistaMenú(String nombre, String[] listaOpciones) {
		super(nombre);
		this.listaOpciones = listaOpciones;
	}

	/**
	 * Muestra las opciones con su enumeración correspondiente.
	 */
	public void mostrarMenú() {
		for (int i = 0; i < listaOpciones.length; i++) {
			System.out.printf("%d.- %s %n", i + 1, listaOpciones[i]);
		}
	}

	/**
	 * Pide al usuario la opción que desea para continuar, verificando que debe
	 * estar comprendida entre las opcines listadas en el menú. Admite como valor
	 * solo número enteros.
	 * 
	 * @return la opción seleccionada
	 */
	public int pedirOpción() {
		boolean salir = false;
		String textoEntrada;
		Scanner in = getScEntrada();

		do {
			try {
				System.out.println("Introduzca la opción elegida:");
				textoEntrada = in.nextLine();
				opción = Integer.parseInt(textoEntrada);

				if (opción <= 0 || opción > listaOpciones.length) {
					System.out.println("*** Opción inválida ***");
				} else {
					salir = true;
				}
			} catch (NumberFormatException e) {
				System.err.println("*** Error de formato, el sistema solo admite números enteros ***");
			}
		} while (!salir);
		return opción;
	}
}
