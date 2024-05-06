package jcolonia.daw2024.quiniela;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Prueba
 * Modelo: Colección básica de resultados deportivos en formato de quiniela 1X2.
 * 
 * @version 1.1 (20240502000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class ConjuntoQuiniela1X2 {
	/**
	 * Lista donde se almacenan los elementos.
	 */
	List<ElementoPartido1X2> lista;

	/**
	 * Crea una lista inicialmente vacía.
	 */
	public ConjuntoQuiniela1X2() {
		lista = new Vector<ElementoPartido1X2>(6);
	}

	/**
	 * Localiza un elemento de la lista por su posición.
	 * 
	 * @param pos la posición a consultar
	 * @return el elemento correspondiente
	 */
	public ElementoPartido1X2 getElemento(int pos) {
		ElementoPartido1X2 resultado;
		resultado = lista.get(pos);
		return resultado;
	}

	/**
	 * Incorpora un elemento al final de la lista.
	 * 
	 * @param nuevo el elemento a incorporar
	 */
	public void agregarElemento(ElementoPartido1X2 nuevo) {
		lista.add(nuevo);
	}

	/**
	 * Elimina un elemento de la lista.
	 * 
	 * @param viejo el elemento retirado
	 */
	public void eliminarElemento(ElementoPartido1X2 viejo) {
		lista.remove(viejo);
	}

	/**
	 * Devuelve el número de elementos almacenados en la lista.
	 * 
	 * @return el número de elementos
	 */
	public int size() {
		return lista.size();
	}

	/**
	 * Elimina todos los elementos de la lista.
	 */
	public void vaciar() {
		lista.clear();
	}

	/**
	 * Facilita una lista con las descripciones de todos los resultados almacenados.
	 * 
	 * @see ElementoPartido1X2#toString()
	 * 
	 * @return la lista de textos correspondiente
	 */
	public List<String> generarListado() {
		List<String> listaTextos;
		listaTextos = new ArrayList<String>(lista.size());

		for (ElementoPartido1X2 resultado : lista) {
			listaTextos.add(resultado.toString());
		}
		return listaTextos;
	}

	/**
	 * Facilita una lista con las líneas de todos los resultados almacenados aptas
	 * para el archivo de exportación en formato pseudo CSV.
	 * 
	 * @see ElementoPartido1X2#toStringCSV()
	 * 
	 * @return la lista de textos correspondiente
	 * @throws Partido1X2Exception si los datos del partido aún están incompletos
	 */
	public List<String> generarListadoCSV() throws Partido1X2Exception {
		List<String> listaTextos;
		listaTextos = new ArrayList<String>(lista.size());

		for (ElementoPartido1X2 resultado : lista) {
			listaTextos.add(resultado.toStringCSV());
		}
		return listaTextos;
	}
}
