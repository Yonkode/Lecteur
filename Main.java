import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        try (PDDocument document = PDDocument.load(
                new File("C:\\Programmation\\Java\\Lecteur\\C.pdf"))) {

            PDFRenderer renderer = new PDFRenderer(document);

            JFrame frame = new JFrame("Lecteur PDF");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Panel qui contiendra toutes les pages
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Boucle sur toutes les pages
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 150);
                image = invertColors(image);
                JLabel label = new JLabel(new ImageIcon(image));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);

                panel.add(label);
                panel.add(Box.createVerticalStrut(10)); // espace entre pages
            }

            JScrollPane scrollPane = new JScrollPane(panel);

            frame.add(scrollPane);
            frame.setSize(900, 700);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage invertColors(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);

                Color inverted = new Color(
                        255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue()
                );

                image.setRGB(x, y, inverted.getRGB());
            }
        }
        return image;
    }

}
