import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField semilla1Field, semilla2Field, cantidadField;
    private JTable resultadoTabla;
    private DefaultTableModel tableModel;

    public Main() {
        setTitle("Generador de Números Pseudoaleatorios - Productos Medios");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelEntrada = new JPanel(new GridLayout(0, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Semilla 1 (Y0):"));
        semilla1Field = new JTextField(10);
        panelEntrada.add(semilla1Field);

        panelEntrada.add(new JLabel("Semilla 2 (X1):"));
        semilla2Field = new JTextField(10);
        panelEntrada.add(semilla2Field);

        panelEntrada.add(new JLabel("Cantidad de números a generar:"));
        cantidadField = new JTextField(10);
        panelEntrada.add(cantidadField);

        JButton generarBtn = new JButton("Generar");
        generarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarNumeros();
            }
        });
        panelEntrada.add(new JLabel()); // Espacio vacío para alineación
        panelEntrada.add(generarBtn);

        add(panelEntrada, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Y", "(Y) * X", "Resultado", "X", "r"}, 0);
        resultadoTabla = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultadoTabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generarNumeros() {
        try {
            int semilla1 = Integer.parseInt(semilla1Field.getText());
            int semilla2 = Integer.parseInt(semilla2Field.getText());
            int cantidad = Integer.parseInt(cantidadField.getText());

            if (String.valueOf(semilla1).length() <= 3 || String.valueOf(semilla2).length() <= 3) {
                JOptionPane.showMessageDialog(this, "Las semillas deben tener más de 3 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0);
            int D = String.valueOf(semilla1).length();
            int X0 = semilla1;
            int X1 = semilla2;

            for (int i = 0; i < cantidad; i++) {
                long Yi = (long) X0 * X1;
                String YiStr = String.format("%0" + (2 * D) + "d", Yi);
                String XiStr = YiStr.substring((YiStr.length() - D) / 2, (YiStr.length() - D) / 2 + D);
                int Xi = Integer.parseInt(XiStr);
                double ri = (double) Xi / Math.pow(10, D);

                tableModel.addRow(new Object[]{X0, "(" + X0 + ") * " + X1, YiStr, XiStr, ri});

                X0 = X1;
                X1 = Xi;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}