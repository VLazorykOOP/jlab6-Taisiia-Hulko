import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class RotatingRectangle extends JPanel implements ActionListener {

    private Timer timer;
    private double angle = 0; 

    public RotatingRectangle() {
        timer = new Timer(20, this); 
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = 100;
        int height = 100;
        int x = getWidth() / 2 - width / 2;
        int y = getHeight() / 2 - height / 2;

        Path2D.Double rect = new Path2D.Double();
        rect.moveTo(x, y);
        rect.lineTo(x + width, y);
        rect.lineTo(x + width, y + height);
        rect.lineTo(x, y + height);
        rect.closePath();

        AffineTransform old = g2d.getTransform();

        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(angle), x + width / 2.0, y + height / 2.0);
        g2d.setTransform(rotate);

        g2d.setColor(Color.BLUE);
        g2d.fill(rect);

        g2d.setTransform(old);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += 1;
        if (angle >= 360) {
            angle = 0;
        }
        
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
