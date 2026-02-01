package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/*
 * Clase principal del juego
 * 
 * En esta clase creo la ventana principal, configuro el Canvas
 * y arranco la aplicación
 * 
 * @author JosemaMR
 * 
 */

public class Juego extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	/** Aquí guardaré la referencia a la ventana principal */
	private static JFrame ventana;

	/**
	 * En este Thread ejecutaré el bucle principal del juego (actualización y
	 * renderizado).
	 */
	private static Thread thread;

	/** Este será el título que aparecerá en la ventana */
	private static final String NOMBRE = "Juego";

	private static int aps = 0;

	private static int fps = 0;

	/** Ancho fijo que quiero para la ventana */
	/** Alto fijo que quiero para la ventana */
	public static final int ANCHO = 800;
	public static final int ALTO = 600;

	/**
	 * Indico si el juego está en ejecución.
	 * 
	 * Uso 'volatile' para asegurarme de que todos los hilos vean siempre el valor
	 * actualizado de esta variable.
	 */
	private static volatile boolean enFuncionamiento = false;

	/**
	 * Constructor del juego.
	 * 
	 * Aquí creo y configuro toda la ventana: tamaño, layout, Canvas y visibilidad.
	 */

	private Juego() {

		// Establezco el tamaño que quiero para el Canvas
		setPreferredSize(new Dimension(ANCHO, ALTO));

		// Creo la ventana principal con el nombre del juego
		ventana = new JFrame(NOMBRE);

		// Hago que la aplicación se cierre cuando se cierre la ventana
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// No permito que el usuario cambie el tamaño de la ventana
		ventana.setResizable(false);

		// Uso BorderLayout para organizar los componentes
		ventana.setLayout(new BorderLayout());

		// Añado este Canvas en el centro de la ventana
		ventana.add(this, BorderLayout.CENTER);

		// Ajusto la ventana automáticamente al tamaño del Canvas
		ventana.pack();

		// Centro la ventana en la pantalla
		ventana.setLocationRelativeTo(null);

		// Finalmente hago visible la ventana
		ventana.setVisible(true);

	}

	/**
	 * Método principal.
	 * 
	 * Desde aquí arranco el juego creando una instancia de esta clase.
	 */

	public static void main(String[] args) {

		// Creo el objeto principal del juego
		Juego juego = new Juego();

		// Inicio el hilo donde se ejecutará el bucle del juego
		juego.iniciar();

	}

	/**
	 * Inicio el juego creando el Thread y marcando el estado como activo.
	 * 
	 * El método es synchronized para evitar que dos hilos intenten iniciarlo a la
	 * vez.
	 */
	private synchronized void iniciar() {
		enFuncionamiento = true;

		// Creo el hilo indicando que esta clase implementa Runnable
		thread = new Thread(this, "Graficos");

		// Arranco el hilo y se llamará automáticamente al método run()
		thread.start();

	}

	/**
	 * Detengo la ejecución del juego
	 * 
	 * Marco el estado como falso y espero a que el hilo termine correctamente
	 * usando join()
	 */
	private synchronized void detener() {
		enFuncionamiento = false;

		try {
			// Espero a que el hilo termine antes de continuar
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void actualizar() {
		// Incremento el contador de Actualizaciones Por Segundo (APS)
		aps++;
	}

	private void mostrar() {
		// Incremento el contador de Frames Por Segundo (FPS)
		fps++;
	}

	/**
	 * Método que ejecuta el hilo principal del juego.
	 * 
	 * Aquí iré implementando el game loop: actualizar la lógica renderizar en
	 * pantalla controlar los FPS
	 * 
	 * Uso un bucle while que se ejecuta mientras 'enFuncionamiento' sea true
	 * Controlo las actualizaciones por segundo usando un delta temporal, y calculo
	 * los FPS y APS para mostrarlo en el título de la ventana
	 * 
	 */

	@Override
	public void run() {
		// Cantidad de nanosegundos que hay en un segundo
		final int NS_POR_SEGUNDO = 1000000000;
		// Número de actualizaciones por segundo que quiero
		final byte APS_OBJETIVO = 60;
		// Tiempo en nanosegundos que debe pasar entre cada actualizacion
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		// Momento de la última actualización
		long referenciaActualizacion = System.nanoTime();
		// Momento de referencia para contar APS y FPS
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;
		// Mientras el juego esté activo, ejecuto el bucle principal
		while (enFuncionamiento) {
			// Momento actual al inicio de este ciclo
			final long inicioBucle = System.nanoTime();

			// Calculo el tiempo transcurrido desde la última actualización
			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			// Incremento delta con la fracción de actualización correspondiente
			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			// Mientras delta sea >= 1, realizo actualizaciones
			while (delta >= 1) {
				actualizar(); // Actualizo la lógica del juego
				delta--; // Disminuyo delta tras cada actualización
			}
			// Renderizo el juego en pantalla
			mostrar();

			// Cada segundo, actualizo el título de la ventana con APS y FPS
			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				ventana.setTitle(NOMBRE + " APS: " + aps + " FPS: " + fps);
				aps = 0; // Reinicio contador de actualizaciones
				fps = 0; // Reinicio contador de frames
				referenciaContador = System.nanoTime(); // Reinicio referencia temporal
			}

		}

	}

}