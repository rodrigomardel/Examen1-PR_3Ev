package jcolonia.daw2024.quiniela;

import static java.lang.System.out;

import java.util.List;

/**
 * Vista para listar los elementos de los datos introducidos.
 * 
 * @version 1.0 (20240503000)
 * @author Rodrigo Martínez Delgado &lt;rodrigo.mardel.daw@gmail.com&gt;
 */
public class VistaListado extends VistaGeneral {

	/**
	 * Almacena el nombre o título.
	 * 
	 * @param nombre el nombre o título del menú
	 */
	public VistaListado(String nombre) {
		super(nombre);
	}

	/**
	 * Muestra el listado de la colección principal de resultados.
	 * 
	 * @param generarListado listado correspondiente
	 */
	public void mostrar(List<String> generarListado) {
		for (int i = 0; i < generarListado.size(); i++) {
			out.printf("%d. %s %n", i + 1, generarListado.get(i));
		}		
	}
}
