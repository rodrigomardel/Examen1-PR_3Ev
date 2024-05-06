package jcolonia.daw2024.quiniela;

/**
 * Modelo: Representación de un partido entre dos equipos aplicable a un formato
 * de quiniela 1X2.
 * 
 * @version 1.1 (20240503000)
 * @author Rodrigo Martínez Delgado &lt;rodrigo.mardel.daw@gmail.com&gt;
 */
public class ElementoPartido1X2 {
	/**
	 * Separador empleado en la exportación pseudo-CSV: «#» (almohadilla, número…).
	 * 
	 * @see #toStringCSV()
	 * @see #of(String)
	 */
	private static final String SEPARADOR = "#";

	/**
	 * Número de propiedades de cada partido: dos equipos y un resultado.
	 */
	private static final int MÁX_CAMPOS = 3;

	/**
	 * Ancho de la columna de nombres para la representación textual.
	 */
	private static final int TXT_ANCHO_NOMBRES = 43;

	/**
	 * Nombre del primer equipo, el equipo local.
	 */
	private String equipoLocal;

	/**
	 * Nombre del segundo equipo, el equipo visitante.
	 */
	private String equipoVisitante;

	/**
	 * Resultado 1-X-2 del partido.
	 */
	private Resultado1X2 resultado;

	/**
	 * Número de valores almacenados en la combinación. Evidencia si la combinación
	 * está completa o no.
	 */
	private int númDatos;

	/**
	 * Inicializa una lista de valores vacía y pone el contador de valores a cero.
	 */
	public ElementoPartido1X2() {
		númDatos = 0;
	}

	/**
	 * Añade el siguiente dato del partido. Los nombres de equipos se almacenan en
	 * mayúsculas. Verifica que no se introduzcan textos vacíos o nombres de equipo
	 * repetidos.
	 * 
	 * @param texto entrada a almacenar
	 * @throws DatoPartido1X2Exception si el dato no es válido para la posición a
	 *                                 rellenar
	 * @throws Partido1X2Exception     si todos los datos ya están completos.
	 */
	public void setDato(String texto) throws DatoPartido1X2Exception {
		verificarTextoNoNulo(texto);

		// Comprobamos de forma implícita númValores
		verificarAbierta();

		switch (númDatos) {
		case 0:
			verificarNombreVálido(texto);
			equipoLocal = texto.toUpperCase();
			númDatos++;
			break;
		case 1:
			verificarNombreVálido(texto);
			if (texto.compareToIgnoreCase(equipoLocal) == 0) {
				throw new DatoPartido1X2Exception("Equipo repetido");
			}
			equipoVisitante = texto.toUpperCase();
			númDatos++;
			break;
		case 2:
			setResultado(texto);
			númDatos++;
			break;
		default:
			// Sería redundante, comprobado antes por «verificarAbierta()»
			// throw new Partido1X2Exception("Datos partido ya están completos");
		}
	}

	/**
	 * Almacena el resultado 1-X-2 del partido.
	 * 
	 * @param texto entrada a almacenar
	 * @throws DatoPartido1X2Exception Si no es uno de los tres valores permitidos
	 *                                 1-X-2
	 */
	private void setResultado(String texto) throws DatoPartido1X2Exception {
		if (texto.length() != 1) {
			throw new DatoPartido1X2Exception("Resultado no válido");
		}

		switch (texto.toUpperCase()) {
		case "1":
			resultado = Resultado1X2.Local1;
			break;
		case "X":
			resultado = Resultado1X2.EmpateX;
			break;
		case "2":
			resultado = Resultado1X2.Visitante2;
			break;
		default:
			throw new DatoPartido1X2Exception("Resultado no válido");
		}
	}

	/**
	 * Comprueba que el nombre del equipo sea válido. La longitud del nombre debe
	 * estar en el rango [5, 20].
	 * 
	 * @param texto el nombre a verificar
	 * @throws DatoPartido1X2Exception si la longitud del nombre no es adecuada.
	 */
	protected static void verificarNombreVálido(String texto) throws DatoPartido1X2Exception {
		if (texto.length() < 5) {
			throw new DatoPartido1X2Exception("Nombre demasiado corto");
		} else if (texto.length() > 20) {
			throw new DatoPartido1X2Exception("Nombre demasiado largo");
		}

	}

	/**
	 * Verifica que se haya proporcionado un texto.
	 * 
	 * @param texto el dato a comprobar
	 * @throws DatoPartido1X2Exception si el texto es nulo o está vacío
	 */
	public void verificarTextoNoNulo(String texto) throws DatoPartido1X2Exception {
		if (texto == null || texto.length() == 0) {
			throw new DatoPartido1X2Exception("Dato vacío");
		}
	}

	/**
	 * Informa si la carga de todos los datos del partido y su resultado se han
	 * completado y así el objeto está listo para su consulta y uso.
	 * 
	 * @return el valor correspondiente
	 */
	public boolean estáCerrada() {
		boolean resultado = númDatos == MÁX_CAMPOS;
		return resultado;
	}

	/**
	 * Verifica que todos los datos del partido estén ya cerrados y lanza una
	 * {@link Partido1X2Exception} en caso contrario.
	 * @throws Partido1X2Exception si los datos del partido aún están incompletos
	 */
	public void verificarCerrada() throws Partido1X2Exception {
		if (!estáCerrada()) {
			throw new Partido1X2Exception("Datos de partido todavía incompletos");
		}
	}

	/**
	 * Verifica que el partido admita todavía valores para su compleción y lanza una
	 * {@link Partido1X2Exception} en caso contrario.
	 * @throws Partido1X2Exception si los datos del partido ya están completos
	 */
	public void verificarAbierta() throws Partido1X2Exception {
		if (estáCerrada()) {
			throw new Partido1X2Exception("Datos partido ya están completos");
		}
	}

	/**
	 * Proporciona el nombre del primer equipo, el equipo local.
	 * 
	 * @throws Partido1X2Exception si los datos están todavía incompletos
	 * @return el valor correspondiente
	 *
	 */
	public String getEquipoLocal() throws Partido1X2Exception {
		verificarCerrada();
		return equipoLocal;
	}

	/**
	 * Proporciona el nombre del segundo equipo, el equipo visitante.
	 * 
	 * @throws Partido1X2Exception si los datos están todavía incompletos
	 * @return el valor correspondiente
	 *
	 */
	public String getEquipoVisitante() throws Partido1X2Exception {
		verificarCerrada();
		return equipoVisitante;
	}

	/**
	 * Proporciona el resultado.
	 * 
	 * @throws Partido1X2Exception si los datos están todavía incompletos
	 * @return el valor correspondiente
	 *
	 */
	public Resultado1X2 getResultado() throws Partido1X2Exception {
		verificarCerrada();
		return resultado;
	}

	/**
	 * Construye un partido a partir de una línea de texto. La línea de texto debe
	 * contener, al menos, tres textos válidos en un formato pseudo-CSV empleando el
	 * carácter definido como {@link #SEPARADOR separador}.
	 * 
	 * <div>Se facilita este método estático como alternativa a la posibilidad de
	 * disponer un método setDatos(String), evitando así tener que gestionar las
	 * recargas sobre objetos parcialmente rellenados en el inicio. También se evita
	 * así el caso de que el objeto quedara incompleto si se produce una incidencia
	 * fatal a mitad de la carga.</div>
	 * 
	 * @param líneaCSV la línea de texto
	 * @throws DatoPartido1X2Exception si alguno de los datos no encaja en la
	 *                                 posición correspondiente
	 * @return el nuevo partido creado
	 */
	public static ElementoPartido1X2 of(String líneaCSV) throws DatoPartido1X2Exception {
		ElementoPartido1X2 nuevoPartido;
		nuevoPartido = new ElementoPartido1X2();

		String[] partes = líneaCSV.split(SEPARADOR);

		if (partes.length < MÁX_CAMPOS) { // Excepción temprana si faltan campos
			throw new Partido1X2Exception("Línea CSV mal formada");
		}

		for (String pieza : partes) {
			// Admitimos campos extra, pero ignoramos lo que sobre.
			if (nuevoPartido.estáCerrada()) {
				break;
			}

			nuevoPartido.setDato(pieza); // Atención: excepción si el dato no encaja
		}

		return nuevoPartido;
	}

	/**
	 * Construye un partido a partir de los datos del mismo.
	 * 
	 * @param nombreLocal      el nombre del primer equipo, el equipo local
	 * @param nombreVisitante  el nombre del segundo equipo, el equipo visitante
	 * @param resultadoPartido el resultado 1-X-2 del partido
	 * @throws DatoPartido1X2Exception si alguno de los datos no encaja en la
	 *                                 posición correspondiente
	 * @return el nuevo partido creado
	 */
	public static ElementoPartido1X2 of(String nombreLocal, String nombreVisitante, String resultadoPartido)
			throws DatoPartido1X2Exception {
		ElementoPartido1X2 nuevoPartido;
		nuevoPartido = new ElementoPartido1X2();

		nuevoPartido.setDato(nombreLocal);
		nuevoPartido.setDato(nombreVisitante);
		nuevoPartido.setDato(resultadoPartido);

		return nuevoPartido;
	}

	/**
	 * Proporciona una representación en texto formateada del partido. En la primera
	 * columna van los dos equipos -con puntos de relleno- y en la segunda los
	 * resultados. Ambas columnas son de ancho fijo para obtener composiciones
	 * alineadas que formen una tabla.
	 * 
	 * @return el texto correspondiente
	 */
	@Override
	public String toString() {
		String mensaje;
		String textoNombres, textoLocal, textoVisitante;
		String textoResultado;

		textoLocal = (equipoLocal == null) ? "¿?" : equipoLocal;
		textoVisitante = (equipoVisitante == null) ? "¿?" : equipoVisitante;
		textoNombres = String.format("%s - %s", textoLocal, textoVisitante);

		textoNombres = normalizarAncho(TXT_ANCHO_NOMBRES, textoNombres, '.');

		textoResultado = (resultado == null) ? Resultado1X2.toNullString() : resultado.to1X2String();
		mensaje = String.format("%s %s", textoNombres, textoResultado);
		return mensaje;
	}

	/**
	 * Complemento a {#link #toString} para acomodar el relleno entre la columna de
	 * nombres y la de resultado con el ancho adecuado.
	 * 
	 * @param ancho   el ancho total deseado
	 * @param texto   el texto original o recortar o rellenar
	 * @param relleno el carácter de relleno
	 * @return el texto formateado
	 */
	private String normalizarAncho(int ancho, String texto, char relleno) {
		StringBuffer resultado;
		int anchoTexto;

		resultado = new StringBuffer();

		anchoTexto = texto.length();
		if (anchoTexto > ancho) { // Texto grande, hay que recortar
			resultado.append(texto.substring(0, ancho));
		} else {
			resultado.append(texto);
			if (anchoTexto < ancho) { // Al menos cabe uno más…
				resultado.append(" ");
			}
			anchoTexto = resultado.length();
			if (anchoTexto < ancho) { // Rellenamos lo que queda
				resultado.append(String.valueOf(relleno).repeat(ancho - anchoTexto));
			}
		}
		return resultado.toString();
	}

	/**
	 * Coloca en una línea todos los datos del partido separados por el carácter
	 * definido como {@link #SEPARADOR separador}. Se utiliza para montar el archivo
	 * en formato pseudo CSV.
	 * 
	 * @return la línea completa
	 * @throws Partido1X2Exception si los datos del partido aún están incompletos
	 */
	public String toStringCSV() throws Partido1X2Exception {
		verificarCerrada();
		StringBuffer texto = new StringBuffer();

		texto.append(equipoLocal);
		texto.append(SEPARADOR);
		texto.append(equipoVisitante);
		texto.append(SEPARADOR);
		texto.append(resultado);

		return texto.toString();
	}

	/**
	 * Proporciona una representación en texto de los puntos conseguidos por cada
	 * equipo. El ganador recibe tres puntos y en caso de empate se reparte un punto
	 * a cada uno.
	 * 
	 * @return el texto correspondiente
	 */
	public String toStringPuntos() {
		String mensaje;

		switch (resultado) {
		case Local1:
			mensaje = String.format("↑ %s (%d)", equipoLocal, 3);
			break;
		case Visitante2:
			mensaje = String.format("↓ %s (%d)", equipoVisitante, 3);
			break;
		case EmpateX:
			mensaje = String.format("= %s (%3$d) - %s (%3$d)", equipoLocal, equipoVisitante, 1);
			break;
		default: // No debe ocurrir, datos incompletos
			mensaje = "- - - -";
			break;
		}
		return mensaje;
	}
}
