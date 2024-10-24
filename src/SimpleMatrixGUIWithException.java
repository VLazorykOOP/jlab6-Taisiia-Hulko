import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Власне виключення, яке наслідується від ArithmeticException
class MatrixSumException extends ArithmeticException {
    public MatrixSumException(String message) {
        super(message);
    }
}

public class SimpleMatrixGUIWithException extends JFrame {

    private JTextField sizeInput;
    private JTable matrixATable, matrixBTable, resultTable;
    private JButton loadButton, compareButton;
    private DefaultTableModel matrixAModel, matrixBModel, resultModel;

    public SimpleMatrixGUIWithException() {
        setTitle("Matrix Comparison");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Поле для введення розміру і кнопки
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Розмір (до 15):"));
        sizeInput = new JTextField(5);
        topPanel.add(sizeInput);
        loadButton = new JButton("Завантажити");
        topPanel.add(loadButton);
        compareButton = new JButton("Порівняти");
        topPanel.add(compareButton);
        add(topPanel, BorderLayout.NORTH);

        // Таблиці для матриць і результатів
        matrixAModel = new DefaultTableModel(0, 0);
        matrixATable = new JTable(matrixAModel);
        matrixBModel = new DefaultTableModel(0, 0);
        matrixBTable = new JTable(matrixBModel);
        resultModel = new DefaultTableModel(0, 1);
        resultTable = new JTable(resultModel);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 3));
        tablesPanel.add(new JScrollPane(matrixATable));
        tablesPanel.add(new JScrollPane(matrixBTable));
        tablesPanel.add(new JScrollPane(resultTable));
        add(tablesPanel, BorderLayout.CENTER);

        // Обробка натискання кнопок
        loadButton.addActionListener(e -> loadFromFile());
        compareButton.addActionListener(e -> compareMatrices());
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (Scanner scanner = new Scanner(fileChooser.getSelectedFile())) {
                int n = Integer.parseInt(sizeInput.getText());
                if (n > 15) {
                    JOptionPane.showMessageDialog(this, "Розмір не більше 15");
                    return;
                }

                matrixAModel.setRowCount(n);
                matrixAModel.setColumnCount(n);
                matrixBModel.setRowCount(n);
                matrixBModel.setColumnCount(n);

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        matrixAModel.setValueAt(scanner.nextInt(), i, j);
                    }
                }
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        matrixBModel.setValueAt(scanner.nextInt(), i, j);
                    }
                }

            } catch (FileNotFoundException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Помилка завантаження файлу або невірний формат");
            }
        }
    }

    private void compareMatrices() {
        try {
            int n = Integer.parseInt(sizeInput.getText());
            if (n > 15) {
                JOptionPane.showMessageDialog(this, "Розмір не більше 15");
                return;
            }

            int[][] A = new int[n][n];
            int[][] B = new int[n][n];
            int[] X = new int[n];

            int sumA = 0, sumB = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = Integer.parseInt(matrixAModel.getValueAt(i, j).toString());
                    B[i][j] = Integer.parseInt(matrixBModel.getValueAt(i, j).toString());
                    sumA += A[i][j];
                    sumB += B[i][j];
                }
            }

            // Генерація власного виключення при перевищенні суми елементів
            if (sumA > 100 || sumB > 100) {
                throw new MatrixSumException("Сума елементів однієї з матриць перевищує 100!");
            }

            // Порівнюємо рядки
            for (int i = 0; i < n; i++) {
                boolean allNegative = true;
                for (int j = 0; j < n; j++) {
                    if (A[i][j] >= 0 || B[i][j] >= 0) {
                        allNegative = false;
                        break;
                    }
                }
                X[i] = allNegative ? 1 : 0;
            }

            resultModel.setRowCount(n);
            for (int i = 0; i < n; i++) {
                resultModel.setValueAt(X[i], i, 0);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Невірний формат числа");
        } catch (MatrixSumException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleMatrixGUIWithException().setVisible(true));
    }
}
