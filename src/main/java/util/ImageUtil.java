package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImageUtil {
	
	public static ImageIcon redimensionarImagem (ImageIcon imagem, int largura, int altura) {
		Image imagemOriginal = imagem.getImage();
		System.out.println("Tamanho original: " + imagemOriginal.getWidth(null) + "x" + imagemOriginal.getHeight(null));
		BufferedImage imagemPronta = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D gd2d = imagemPronta.createGraphics();
		System.out.println("Redimensionando imagem: " + largura + "x" + altura);
		
		gd2d.drawImage(imagemOriginal,0,0, largura, altura, null);
		
		gd2d.dispose();
		
		return new ImageIcon(imagemPronta);
	}

}
