package gráficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Clase HojaSprites.
 * 
 * En esta clase cargo una imagen desde los recursos del proyecto
 * y extraigo todos sus píxeles en un array para poder trabajar
 * con ellos directamente durante el renderizado
 * 
 * Una hoja de sprites suele contener muchas imágenes pequeñas
 * (tiles, animaciones, personajes, etc.) dentro de un solo archivo
 * 
 * @author JosemaMR
 */

public class HojaSprites {

	// Guardo el ancho y alto de la imagen
	private final int ancho;
	/** Alto total de la imagen en píxeles */
	private final int alto;

	/**
	 * Array donde guardo todos los píxeles de la imagen
	 * 
	 * Cada posición representa un color en formato ARGB. El tamaño es ancho * alto
	 */
	public final int[] pixeles;

	/**
	 * Constructor de la hoja de sprites.
	 * 
	 * Aquí cargo la imagen desde la ruta indicada, guardo su tamaño y copio todos
	 * los píxeles en el array para poder acceder a ellos rápidamente
	 * 
	 */
	public HojaSprites(final String ruta, final int ancho, final int alto) {
		// Guardo el ancho y alto de la imagen
		this.ancho = ancho;
		this.alto = alto;

		// Creo el array donde almacenaré los píxeles
		pixeles = new int[ancho * alto];

		BufferedImage imagen;
		try {
			// Cargo la imagen desde los recursos usando la ruta proporcionada
			imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
			// Copio todos los píxeles de la imagen al array
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
		} catch (IOException e) {
			// Si ocurre un error al cargar la imagen, muestro la traza por consola
			e.printStackTrace();
		}

	}

	// Devuelvo el ancho total de la hoja de sprites

	public int getAncho() {
		return ancho;
	}

}
