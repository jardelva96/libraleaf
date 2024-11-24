package view;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TelaLeitura extends JFrame {

    private PDDocument document;
    private PDFRenderer renderer;
    private JLabel imagemLabel;
    private int paginaAtual;
    private float zoomLevel = 1.0f; // Nível de zoom padrão

    public TelaLeitura(String titulo, String caminhoPdf) {
        // Configurações da janela
        setTitle("Leitura do Livro - " + titulo);
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51));
        setLayout(new BorderLayout());

        // Barra superior
        JPanel barraSuperior = new JPanel();
        barraSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        barraSuperior.setBackground(new Color(33, 47, 61));

        JButton btnVoltar = criarBotao("← Voltar");
        JButton btnAnterior = criarBotao("Anterior");
        JButton btnProxima = criarBotao("Próxima");
        JButton btnAumentar = criarBotao("Aumentar Zoom");
        JButton btnReduzir = criarBotao("Reduzir Zoom");

        JLabel lblPagina = new JLabel();
        lblPagina.setForeground(Color.WHITE);

        barraSuperior.add(btnVoltar);
        barraSuperior.add(btnAnterior);
        barraSuperior.add(lblPagina);
        barraSuperior.add(btnProxima);
        barraSuperior.add(btnAumentar);
        barraSuperior.add(btnReduzir);
        add(barraSuperior, BorderLayout.NORTH);

        // Área central para exibir a página do PDF
        imagemLabel = new JLabel("", JLabel.CENTER);
        JScrollPane scrollPane = new JScrollPane(imagemLabel);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar o PDF
        abrirPdf(caminhoPdf);

        // Atualiza a página exibida
        renderizarPagina(0, lblPagina);

        // Ações dos botões
        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());
        btnAnterior.addActionListener(e -> {
            if (paginaAtual > 0) {
                renderizarPagina(--paginaAtual, lblPagina);
            }
        });
        btnProxima.addActionListener(e -> {
            if (paginaAtual < document.getNumberOfPages() - 1) {
                renderizarPagina(++paginaAtual, lblPagina);
            }
        });

        // Aumentar Zoom
        btnAumentar.addActionListener(e -> {
            zoomLevel += 0.2f; // Aumenta o nível de zoom
            renderizarPagina(paginaAtual, lblPagina);
        });

        // Reduzir Zoom
        btnReduzir.addActionListener(e -> {
            if (zoomLevel > 0.4f) { // Evita zoom negativo ou muito pequeno
                zoomLevel -= 0.2f;
                renderizarPagina(paginaAtual, lblPagina);
            }
        });
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(41, 128, 185));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        return botao;
    }

    private void abrirPdf(String caminho) {
        try {
            File arquivo = new File(caminho);
            if (!arquivo.exists()) {
                JOptionPane.showMessageDialog(this, "Arquivo PDF não encontrado: " + caminho, "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            document = Loader.loadPDF(arquivo);
            renderer = new PDFRenderer(document);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void renderizarPagina(int pagina, JLabel lblPagina) {
        try {
            if (document == null || renderer == null) {
                JOptionPane.showMessageDialog(this, "Erro: Documento PDF não carregado corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BufferedImage imagem = renderer.renderImageWithDPI(pagina, 150 * zoomLevel); // Ajusta o DPI com base no zoom

            if (imagem == null || imagem.getWidth() == 0 || imagem.getHeight() == 0) {
                JOptionPane.showMessageDialog(this, "Erro ao renderizar a página: Imagem inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Image imagemEscalada = imagem.getScaledInstance(-1, -1, Image.SCALE_SMOOTH); // Escala proporcionalmente
            imagemLabel.setIcon(new ImageIcon(imagemEscalada));
            lblPagina.setText("Página: " + (pagina + 1) + " de " + document.getNumberOfPages());
            paginaAtual = pagina;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao renderizar a página: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaTelaPrincipal() {
        dispose(); // Fecha a tela atual
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true)); // Cria uma nova instância da tela principal
    }

    @Override
    public void dispose() {
        try {
            if (document != null) {
                document.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao fechar documento: " + e.getMessage());
        }
        super.dispose();
    }
}
