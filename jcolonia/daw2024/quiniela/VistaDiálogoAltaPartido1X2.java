package jcolonia.daw2024.quiniela;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Vista de altas para agregar elementos.
 * 
 * @version 1.0 (20240503000)
 * @author Rodrigo Martínez Delgado &lt;rodrigo.mardel.daw@gmail.com&gt;
 */
public class VistaDiálogoAltaPartido1X2 extends VistaGeneral {

	/**
	 * Nuevo partido.
	 */
	ElementoPartido1X2 nuevaAlta;

	/**
	 * Almacena el nombre o título.
	 * 
	 * @param nombre el nombre o título del menú
	 */
	public VistaDiálogoAltaPartido1X2(String nombre) {
		super(nombre);
	}

	/**
	 * Registra las entradas del usuario, comprobando que son válidas.
	 * 
	 * @return nuevaAlta el registro correspondiente
	 */
	public ElementoPartido1X2 entradaQuiniela1X2() {
		Scanner in = getScEntrada();
		String textoEntrada;
		boolean salir;

		nuevaAlta = new ElementoPartido1X2();
		salir = false;

		do {
			try {
				mostrarTexto(nuevaAlta.toString());
				mostrarTexto("Introduce el equipo local:");
				textoEntrada = in.nextLine();
				nuevaAlta.setDato(textoEntrada);
				mostrarTexto("Introduce el equipo visitante:");
				textoEntrada = in.nextLine();
				nuevaAlta.setDato(textoEntrada);
				mostrarTexto("Introduce resultado:");
				textoEntrada = in.nextLine();
				nuevaAlta.setDato(textoEntrada);

				if (nuevaAlta.estáCerrada()) {
					salir = true;
				}

			} catch (DatoPartido1X2Exception ex) {

				String mensaje = String.format(("Dato no válido: %s"), ex.getLocalizedMessage());

				System.err.println(mensaje);
			}

		} while (!salir);
		mostrarTexto("¡Alta registrada!");
		return nuevaAlta;
	}

	/**
	 * Comprueba que la representación de texto introducida corresponda con la del
	 * elemento.
	 * 
	 * @param string texto de alta
	 * @return valor correspodiente
	 */
	public boolean confirmarAlta(String string) {
		boolean confirmación;
		confirmación = nuevaAlta.toString() == string;
		return confirmación;
	}

	/**
	 * Control del número de entradas.
	 * 
	 * @param conjunto conjunto correspondiente
	 * @return listado correspondiente
	 */
	public List<String> controlRegistros(ConjuntoQuiniela1X2 conjunto) {
		List<String> listado = new ArrayList<String>();
		String númeroEntradas;

		númeroEntradas = String.format("%d", conjunto.size());

		listado.add(númeroEntradas);

		return listado;

	}

}
