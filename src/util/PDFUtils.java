package util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFUtils {

    /**
     * Gera uma imagem da primeira página de um PDF.
     *
     * @param caminhoPdf    Caminho do arquivo PDF.
     * @param caminhoImagem Caminho para salvar a imagem gerada.
     * @return Caminho da imagem gerada ou null em caso de erro.
     */
    public static String gerarImagemPrimeiraPagina(String caminhoPdf, String caminhoImagem) {
        File arquivoPdf = new File(caminhoPdf);

        // Verifica se o arquivo PDF existe
        if (!arquivoPdf.exists()) {
            System.err.println("Arquivo PDF não encontrado: " + caminhoPdf);
            return null;
        }

        try (PDDocument document = Loader.loadPDF(arquivoPdf)) { // Método correto para PDFBox 3.x
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 150); // Renderiza a primeira página com 150 DPI
            ImageIO.write(image, "PNG", new File(caminhoImagem));
            System.out.println("Imagem gerada com sucesso: " + caminhoImagem);
            return caminhoImagem;
        } catch (IOException e) {
            System.err.println("Erro ao gerar imagem do PDF: " + e.getMessage());
            return null;
        }
    }
}
