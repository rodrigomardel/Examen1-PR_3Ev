package jcolonia.daw2024.quiniela;

import static java.lang.System.out;

import java.util.List;

/**
 * Prueba
 * Controlador: Aplicación de gestión de resultados deportivos de tipo 1X2.
 * Gestiona las distintas funciones del menú principal.
 * 
 * @see ElementoPartido1X2
 * 
 * @version 1.1 (20240502000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class ControladorQuiniela1X2 {
	/** Nombre del archivo de datos para impotación/exportación. */
	private static final String NOMBRE_ARCHIVO = "Almacén Quiniela1X2.txt";
	/** Opciones del menú principal. */
	private static final String[] TXT_MENÚ_PRINCIPAL = { "Alta", "Baja", "Listado", "Exportación", "Importación",
			"Borrado" };
	/** Colección principal de resultados. */
	ConjuntoQuiniela1X2 listaResultados;

	/**
	 * Inicializa la lista/colección donde se irán guardando los partidos.
	 */
	public ControladorQuiniela1X2() {
		listaResultados = new ConjuntoQuiniela1X2();
	}

	/**
	 * Bucle principal ligado al menú de entrada.
	 */
	public void buclePrincipal() {
		VistaMenú menú;
		int opción;
		int n = 0;
		boolean salir = false;

		menú = new VistaMenú("Quiniela 1-X-2", TXT_MENÚ_PRINCIPAL);

		do {
			menú.mostrarTítuloPrincipal();
			menú.mostrarMenú();
			opción = menú.pedirOpción();
			n++;

			switch (opción) {
			case 1: // Alta
				alta();
				break;
			case 2: // Baja
				baja();
				break;
			case 3: // Listado
				listado();
				break;
			case 4: // Exportación
				exportación(NOMBRE_ARCHIVO);
				break;
			case 5: // Importación
				importación(NOMBRE_ARCHIVO);
				break;
			case 6:
				reset();
				break;
			case 0:
				finalizar(); // Finalizar programa
				salir = true;
				break;
			default:
				stub(opción, n);
				break;
			}
		} while (!salir);
	}

	/**
	 * Borra todos los datos piediendo confirmación previa al usuario.
	 */
	private void reset() {
		VistaGeneral.pedirConfirmación("¡Se borraran todas las bandas!", "¿Desea continuar?");
		VistaGeneral.pedirConfirmación("S/N");
		listaResultados.vaciar();
	}

	/**
	 * Crea un partido vacío para rellenarlo y eventualmente almacenarlo. El partido
	 * se envía al diálogo de altas, y si este responde positivamente se almacena.
	 */
	private void alta() {
		ElementoPartido1X2 nuevo;

		VistaDiálogoAltaPartido1X2 dlg;
		dlg = new VistaDiálogoAltaPartido1X2("Altas 1-X-2");

		nuevo = dlg.entradaQuiniela1X2();
		if (dlg.confirmarAlta(nuevo.toString())) {
			listaResultados.agregarElemento(nuevo);
		}
		listado();
	}

	/**
	 * Abre el diálogo de bajas y recibe de él un partido de la lista para su
	 * eliminación.
	 */
	private void baja() {
		if(listaResultados.size() <= 0) {
			VistaGeneral.mostrarAviso("*** ¡No hay ningún elemento que mostrar!");
		}
		ElementoPartido1X2 partidoBorrable;
		int posiciónBorrable;

		VistaDiálogoBajaPartido1X2 dlg;
		dlg = new VistaDiálogoBajaPartido1X2("Bajas 1-X-2");

		posiciónBorrable = dlg.bajaQuiniela1X2(listaResultados.generarListado());

		if (posiciónBorrable > -1) { // Pulsación de «Intro» sin seleccionar nada
			partidoBorrable = listaResultados.getElemento(posiciónBorrable);
			if (dlg.confirmarBaja(partidoBorrable.toString())) {
				listaResultados.eliminarElemento(partidoBorrable);
			}
			listado();
		}
	}

	/**
	 * Genera una pantalla con el listado completo de partidos almacenados.
	 */
	private void listado() {
		if(listaResultados.size() <= 0) {
			VistaGeneral.mostrarAviso("*** ¡No hay ningún elemento que mostrar!");
		}
		VistaListado dlg;
		dlg = new VistaListado("Lista de Resultados");

		dlg.mostrar(listaResultados.generarListado());
		VistaGeneral.preguntaSeguir();
	}

	/**
	 * Realiza el volcado de todas las partidos almacenados a un archivo de texto.
	 * Emplea un formato propio –de estilo CSV con separador «#»– que puede ser
	 * recuperado posteriormente (ver {@link #importación(String)}). En caso de
	 * producirse algún error de acceso se envía el mensaje a la salida de error
	 * estándar y el programa continua.
	 * 
	 * @param rutaArchivo el nombre o ruta al archivo
	 */
	private void exportación(String rutaArchivo) {
		AccesoArchivo archivo;
		List<String> listaTextosCSV;
		int númElementos;
		String mensaje;

		boolean bienGrabado = false;

		listaTextosCSV = listaResultados.generarListadoCSV();
		númElementos = listaTextosCSV.size();

		if (númElementos == 0) {
			VistaGeneral.mostrarAviso("No hay ningún resultado que exportar");
		} else {
			archivo = new AccesoArchivo(rutaArchivo);
			bienGrabado = archivo.escribir(listaTextosCSV);

			if (bienGrabado) {
				mensaje = String.format("%d resultados exportados", númElementos);
				VistaGeneral.mostrarTexto(mensaje);
			}
		}
	}

	/**
	 * Importa partidos almacenados en un archivo de texto reemplazando el contenido
	 * actual del programa. Emplea un formato propio –de estilo CSV con separador
	 * «#»– producido por una exportación previa (ver {@link #exportación(String)}).
	 * En caso de producirse algún error de acceso o por el propio formato del
	 * archivo, se envía el mensaje a la salida de error estándar y el programa
	 * continúa sin perder el contenido anterior.
	 * 
	 * @param rutaArchivo el nombre o ruta al archivo
	 */
	private void importación(String rutaArchivo) {
		AccesoArchivo archivo;
		ConjuntoQuiniela1X2 nuevaLista;
		ElementoPartido1X2 nuevoElemento;
		List<String> contenido;
		int númElementos;
		String mensaje;

		try {
			archivo = new AccesoArchivo(rutaArchivo);
			contenido = archivo.leer();
			númElementos = contenido.size();

			if (númElementos == 0) {
				VistaGeneral.mostrarAviso("No hay ningún elemento que importar");
			} else {
				nuevaLista = new ConjuntoQuiniela1X2();
				for (String línea : contenido) {
					nuevoElemento = ElementoPartido1X2.of(línea);
					nuevaLista.agregarElemento(nuevoElemento);
				}
				listaResultados = nuevaLista;

				mensaje = String.format("%d resultados importados", númElementos);
				VistaGeneral.mostrarTexto(mensaje);
				listado();
			}
		} catch (DatoPartido1X2Exception ex) {
			System.err.printf("Error de importación: %s%n", ex.getLocalizedMessage());
		}
	}

	/**
	 * Muestra un mensaje temporal, de relleno, para opciones pendientes de
	 * implementar.
	 * 
	 * @param entrada la opción elegida
	 * @param n       el número de secuencia en el historial de opciones cursadas
	 */
	private void stub(int entrada, int n) {
		out.printf("(%02d) → %d [Opción sin desarrollar]%n", n, entrada);
	}

	/**
	 * Finaliza el programa. Muestra un mensaje final y cierra la conexión con la
	 * entrada estándar.
	 */
	private void finalizar() {
		out.println("*** FIN ***");
		VistaGeneral.close();
	}
}
