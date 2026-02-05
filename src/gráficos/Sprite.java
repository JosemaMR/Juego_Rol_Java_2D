package gráficos;

/*
 * Clase Sprite.
 * 
 * En esta clase represento un sprite individual extraído
 * desde una hoja de sprites
 * 
 * Un sprite es una imagen cuadrada que se usa para dibujar
 * personajes, tiles, objetos o animaciones
 * 
 * @author JosemaMR
 */
public final class Sprite {

	/** Tamaño del lado del sprite (ancho y alto, en píxeles) */
	private final int lado;

	/** Posición horizontal del sprite dentro de la hoja */
	private int x;
	/** Posición vertical del sprite dentro de la hoja */
	private int y;

	/**
	 * Array donde guardo los píxeles del sprite recortado
	 * 
	 * El tamaño es lado * lado
	 */
	public int[] pixeles;
	/** Referencia a la hoja de sprites original */
	private final HojaSprites hoja;

	/**
	 * Constructor del sprite
	 * 
	 * Aquí calculo qué zona de la hoja corresponde al sprite usando la columna y la
	 * fila, y copio sus píxeles en un array propio para poder dibujarlo de forma
	 * independiente
	 * 
	 */
	public Sprite(final int lado, final int columna, final int fila, final HojaSprites hoja) {
		// Guardo el tamaño del sprite
		this.lado = lado;
		// Creo el array donde almacenaré los píxeles del sprite
		pixeles = new int[lado * lado];

		// Calculo la posición real en píxeles dentro de la hoja
		this.x = columna * lado;
		this.y = fila * lado;

		// Guardo la referencia a la hoja de sprites
		this.hoja = hoja;

		// Recorro cada píxel del sprite y lo copio desde la hoja
		for (int y = 0; y < lado; y++) {
			for (int x = 0; x < lado; x++) {
				// Calculo el índice del píxel en el sprite
				// y copio el color correspondiente desde la hoja
				pixeles[(x + y) * lado] = hoja.pixeles[(x + this.x) + (y + this.y) * hoja.getAncho()];

			}

		}

	}

}
