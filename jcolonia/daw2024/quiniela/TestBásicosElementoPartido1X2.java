package jcolonia.daw2024.quiniela;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Modelo: Pruebas básicas sobre {@link ElementoPartido1X2}. Los datos se
 * almacenan correctamente, los nombres se convierten a mayúsculas, se realiza
 * control de las entradas…
 * 
 * @version 2023.6.1 (1.0)
 * @author <a href="dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
class TestBásicosElementoPartido1X2 {
	/**
	 * Método de prueba sobre {@link ElementoPartido1X2}: crea partidos de prueba,
	 * los carga con datos correctos y comprueba que los equipos y el resultado
	 * devueltos ̣—métodos getXXX()— son correctos.
	 * <ul>
	 * <li>Los datos se cargan como textos, ¡en MAYÚSCULAS!</li>
	 * <li>El equipo local se carga al ejecutar
	 * {@link ElementoPartido1X2#setDato(String) setDato(String)} la primera vez y
	 * se recupera con {@link ElementoPartido1X2#getEquipoLocal()
	 * getEquipoLocal()}.</li>
	 * <li>El equipo visitante se carga al ejecutar
	 * {@link ElementoPartido1X2#setDato(String) setDato(String)} la segunda vez y
	 * se recupera con {@link ElementoPartido1X2#getEquipoVisitante()
	 * getEquipoVisitante()}.</li>
	 * <li>El equipo visitante se carga al ejecutar
	 * {@link ElementoPartido1X2#setDato(String) setDato(String)} la tercera vez y
	 * se recupera con {@link ElementoPartido1X2#getResultado()
	 * getResultado()}.</li>
	 * </ul>
	 * 
	 * @throws DatoPartido1X2Exception no esperada
	 */
	@Test
	@DisplayName("Carga y consulta de datos")
	public void testCargaConsultaCorrectas() throws DatoPartido1X2Exception {
		ElementoPartido1X2 partido;

		String equipoA = "EQUIPO A";
		String equipoB = "EQUIPO B";
		String resultado = "1";

		partido = new ElementoPartido1X2();
		partido.setDato(equipoA);
		partido.setDato(equipoB);
		partido.setDato(resultado);

		assertEquals(equipoA, partido.getEquipoLocal(), "Equipo local");
		assertEquals(equipoB, partido.getEquipoVisitante(), "Equipo visitante");
		assertEquals(Resultado1X2.Local1, partido.getResultado(), "Resultado 1");
	}

	/**
	 * Método de prueba sobre
	 * {@link ElementoPartido1X2#of(String, String, String) }: crea partidos de
	 * prueba, los carga con datos correctos y comprueba que los equipos y el
	 * resultado devueltos ̣—métodos getXXX()— son correctos.
	 * 
	 * @throws DatoPartido1X2Exception no esperada
	 * 
	 * @see #testCargaConsultaCorrectas()
	 */
	@Test
	@DisplayName("Factoría y consulta de datos")
	public void testCargaFactoríaConsultaCorrectas() throws DatoPartido1X2Exception {
		ElementoPartido1X2 partido;

		String equipoA = "EQUIPO A";
		String equipoB = "EQUIPO B";
		String resultado = "2";

		partido = ElementoPartido1X2.of(equipoA, equipoB, resultado);

		assertEquals(equipoA, partido.getEquipoLocal(), "Equipo local");
		assertEquals(equipoB, partido.getEquipoVisitante(), "Equipo visitante");
		assertEquals(Resultado1X2.Visitante2, partido.getResultado(), "Resultado 2");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2}: crea partidos de prueba,
	 * los carga con datos correctos y comprueba que los nombres de los equipos
	 * devueltos con {@link ElementoPartido1X2#getEquipoLocal() getEquipoLocal()} y
	 * {@link ElementoPartido1X2#getEquipoVisitante() getEquipoVisitante()} se
	 * entregan convertidos a mayúsculas.
	 * 
	 * <pre>
	 * Ej: RCD Quintanilla → RCD QUINTANILLA
	 * </pre>
	 * 
	 * @throws DatoPartido1X2Exception no esperada
	 */
	@Test
	@DisplayName("Conversión nombres a mayúsculas")
	public void testCargaConsultaMinúsculas() throws DatoPartido1X2Exception {
		ElementoPartido1X2 partido;

		String equipoA = "equipo a";
		String equipoB = "Equipo B";
		String resultado = "X";

		partido = new ElementoPartido1X2();
		partido.setDato(equipoA);
		partido.setDato(equipoB);
		partido.setDato(resultado);

		assertEquals(equipoA.toUpperCase(), partido.getEquipoLocal(), "Equipo local");
		assertEquals(equipoB.toUpperCase(), partido.getEquipoVisitante(), "Equipo visitante");
		assertEquals(Resultado1X2.EmpateX, partido.getResultado(), "Resultado X");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo local un texto vacío se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 */
	@Test
	@DisplayName("Excepción por local vacío")
	public void testCargaLocalVacía() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "";

		partido = new ElementoPartido1X2();
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoA), "Equipo local vacío");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo local un texto nulo se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 */
	@Test
	@DisplayName("Excepción por local nulo")
	public void testCargaLocalNulo() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = null;

		partido = new ElementoPartido1X2();
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoA), "Equipo local nulo");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo local un texto corto se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 * 
	 * @see ElementoPartido1X2#verificarNombreVálido(String)
	 */
	@Test
	@DisplayName("Excepción por local corto")
	public void testCargaLocalNombreCorto() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "A";

		partido = new ElementoPartido1X2();
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoA), "Equipo local corto");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo local un texto largo se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 * 
	 * @see ElementoPartido1X2#verificarNombreVálido(String)
	 */
	@Test
	@DisplayName("Excepción por local largo")
	public void testCargaLocalNombreLargo() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "A012 3456012 sfd 343413123123 23 13";

		partido = new ElementoPartido1X2();
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoA), "Equipo local largo");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo visitante un texto vacío se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 */
	@Test
	@DisplayName("Excepción por visitante vacío")
	public void testCargaVisitanteVacía() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "EQUIPO A";
		String equipoB = null;

		partido = new ElementoPartido1X2();
		assertDoesNotThrow(() -> partido.setDato(equipoA), "Equipo local correcto");
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoB), "Equipo visitante vacío");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo visitante un texto nulo se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 */
	@Test
	@DisplayName("Excepción por visitante nulo")
	public void testCargaVisitanteNulo() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "EQUIPO A";
		String equipoB = "";

		partido = new ElementoPartido1X2();
		assertDoesNotThrow(() -> partido.setDato(equipoA), "Equipo local correcto");
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoB), "Equipo visitante nulo");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo visitante un texto corto se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 * 
	 * @see ElementoPartido1X2#verificarNombreVálido(String)
	 */
	@Test
	@DisplayName("Excepción por visitante corto")
	public void testCargaVisitanteNombreCorto() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "EQUIPO A";
		String equipoB = "B";

		partido = new ElementoPartido1X2();
		assertDoesNotThrow(() -> partido.setDato(equipoA), "Equipo local correcto");
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoB), "Equipo visitante corto");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como nombre de equipo visitante un texto largo se recibe una
	 * excepción {@link DatoPartido1X2Exception} con descripción no vacía.
	 * 
	 * @see ElementoPartido1X2#verificarNombreVálido(String)
	 */
	@Test
	@DisplayName("Excepción por visitante largo")
	public void testCargaVisitanteNombreLargo() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "EQUIPO A";
		String equipoB = "B012 3456012 sfd 343413123123 23 13";

		partido = new ElementoPartido1X2();
		assertDoesNotThrow(() -> partido.setDato(equipoA), "Equipo local correcto");
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(equipoB), "Equipo visitante largo");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como resultado un texto vacío se recibe una excepción
	 * {@link DatoPartido1X2Exception} con descripción no vacía.
	 */
	@Test
	@DisplayName("Excepción por resultado vacío")
	public void testCargaResultadoVacía() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "EQUIPO A";
		String equipoB = "EQUIPO B";
		String resultado = "";

		partido = new ElementoPartido1X2();
		assertDoesNotThrow(() -> partido.setDato(equipoA), "Equipo local correcto");
		assertDoesNotThrow(() -> partido.setDato(equipoB), "Equipo visitante correcto");
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(resultado), "Resultado vacío");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}

	/**
	 * Método de prueba sobre {@link ElementoPartido1X2#setDato(String)}: al
	 * intentar cargar como resultado un texto nulo se recibe una excepción
	 * {@link DatoPartido1X2Exception} con descripción no vacía.
	 */
	@Test
	@DisplayName("Excepción por resultado nulo")
	public void testCargaResultadoNulo() {
		ElementoPartido1X2 partido;
		DatoPartido1X2Exception ex;
		String descripciónExcepción;

		String equipoA = "EQUIPO A";
		String equipoB = "EQUIPO B";
		String resultado = null;

		partido = new ElementoPartido1X2();
		assertDoesNotThrow(() -> partido.setDato(equipoA), "Equipo local correcto");
		assertDoesNotThrow(() -> partido.setDato(equipoB), "Equipo visitante correcto");
		ex = assertThrows(DatoPartido1X2Exception.class, () -> partido.setDato(resultado), "Resultado nulo");
		descripciónExcepción = ex.getLocalizedMessage();
		assertNotNull(descripciónExcepción, "Texto excepción nulo");
		assertNotEquals(0, descripciónExcepción.length(), "Texto excepción vacío");
	}
}
