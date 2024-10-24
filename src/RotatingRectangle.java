import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class RotatingRectangle extends JPanel implements ActionListener {

    private Timer timer;
    private double angle = 0; // Поточний кут обертання

    public RotatingRectangle() {
        timer = new Timer(20, this); // Таймер для оновлення кожні 20 мс
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Згладжування для кращого відображення
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Створення чотирикутника
        int width = 100;
        int height = 100;
        int x = getWidth() / 2 - width / 2;
        int y = getHeight() / 2 - height / 2;

        // Створення об'єкта чотирикутника
        Path2D.Double rect = new Path2D.Double();
        rect.moveTo(x, y);
        rect.lineTo(x + width, y);
        rect.lineTo(x + width, y + height);
        rect.lineTo(x, y + height);
        rect.closePath();

        // Збереження поточного стану трансформацій
        AffineTransform old = g2d.getTransform();

        // Обертання навколо центру ваги чотирикутника
        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(angle), x + width / 2.0, y + height / 2.0);
        g2d.setTransform(rotate);

        // Малювання чотирикутника
        g2d.setColor(Color.BLUE);
        g2d.fill(rect);

        // Відновлення початкового стану трансформацій
        g2d.setTransform(old);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Збільшення кута для обертання
        angle += 1;
        if (angle >= 360) {
            angle = 0;
        }
        // Перемальовування
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Rectangle");
        RotatingRectangle rotatingRectangle = new RotatingRectangle();

        frame.add(rotatingRectangle);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
