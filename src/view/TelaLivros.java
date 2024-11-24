package view;

import util.LivroDAO;
import util.PDFUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class TelaLivros extends JFrame {

    public TelaLivros() {
        // Configurações da janela
        setTitle("Biblioteca - Libraleaf");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51));

        // Painel principal para os livros
        JPanel painelLivros = new JPanel();
        painelLivros.setBackground(new Color(28, 40, 51));
        painelLivros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JScrollPane scrollPane = new JScrollPane(painelLivros);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        // Buscar livros do banco de dados
        List<String[]> livros = LivroDAO.listarLivrosComCaminho(null);

        // Diretório para salvar as imagens
        File diretorioImagens = new File("imagens_livros");
        if (!diretorioImagens.exists()) {
            diretorioImagens.mkdir();
        }

        // Gerar e exibir imagens
        for (String[] livro : livros) {
            String titulo = livro[0];
            String caminhoPdf = livro[1];
            String caminhoImagem = "imagens_livros/" + titulo.replaceAll("\\s+", "_") + ".png";

            // Gerar a imagem da primeira página do PDF
            String imagemGerada = PDFUtils.gerarImagemPrimeiraPagina(caminhoPdf, caminhoImagem);

            if (imagemGerada != null) {
                // Adicionar ao painel
                JLabel labelImagem = criarLabelImagem(titulo, imagemGerada);
                painelLivros.add(labelImagem);
            } else {
                System.err.println("Erro ao carregar imagem para o livro: " + titulo);
            }
        }
    }

    /**
     * Cria um JLabel com a imagem e título do livro.
     *
     * @param titulo        Título do livro.
     * @param caminhoImagem Caminho da imagem.
     * @return JLabel configurado.
     */
    private JLabel criarLabelImagem(String titulo, String caminhoImagem) {
        ImageIcon icon = new ImageIcon(new ImageIcon(caminhoImagem).getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(icon);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setText(titulo);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ação ao clicar na imagem
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Abrindo o livro: " + titulo);
                // Aqui você pode abrir a tela de leitura do livro
            }
        });

        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLivros().setVisible(true));
    }
}
