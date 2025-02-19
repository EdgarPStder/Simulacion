import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    private static DefaultTableModel modelRangos;
    private static DefaultTableModel modelPromedios;
    private static DefaultTableModel modelNumeros;
    private static Random random = new Random();
    private static int secuenciaCount = 1;  // Contador para secuencias

    public static void main(String[] args) {
        JFrame frame = new JFrame("Generador de Números Aleatorios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Campos de texto para los rangos
        JTextField min1Field = new JTextField();
        JTextField max1Field = new JTextField();
        JTextField cantidadField = new JTextField();

        panel.add(new JLabel("Límite inferior:"));
        panel.add(min1Field);
        panel.add(new JLabel("Límite superior:"));
        panel.add(max1Field);
        panel.add(new JLabel("Cantidad de números:"));
        panel.add(cantidadField);

        frame.add(panel, BorderLayout.NORTH);

        // Tablas para mostrar rangos, promedios y números aleatorios
        modelRangos = new DefaultTableModel(new Object[]{"Secuencia", "Límite Inferior", "Límite Superior"}, 0);
        JTable rangoTable = new JTable(modelRangos);
        rangoTable.setDefaultEditor(Object.class, null);  // Establecer como solo lectura
        JScrollPane rangoScrollPane = new JScrollPane(rangoTable);

        modelPromedios = new DefaultTableModel(new Object[]{"Secuencia", "Promedio"}, 0);
        JTable promedioTable = new JTable(modelPromedios);
        promedioTable.setDefaultEditor(Object.class, null);  // Establecer como solo lectura
        JScrollPane promedioScrollPane = new JScrollPane(promedioTable);

        modelNumeros = new DefaultTableModel(new Object[]{"Secuencia", "Números Aleatorios"}, 0);
        JTable numerosTable = new JTable(modelNumeros);
        numerosTable.setDefaultEditor(Object.class, null);  // Establecer como solo lectura
        JScrollPane numerosScrollPane = new JScrollPane(numerosTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rangoScrollPane, promedioScrollPane);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, numerosScrollPane);
        frame.add(splitPane2, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel();
        JButton agregarButton = new JButton("Agregar");
        JButton borrarButton = new JButton("Borrar");
        buttonPanel.add(agregarButton);
        buttonPanel.add(borrarButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Acción para agregar un rango
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int min1 = Integer.parseInt(min1Field.getText());
                    int max1 = Integer.parseInt(max1Field.getText());
                    int cantidad = Integer.parseInt(cantidadField.getText());

                    // Generar los números aleatorios
                    ArrayList<Integer> numerosGenerados = new ArrayList<>();
                    for (int i = 0; i < cantidad; i++) {
                        int num = random.nextInt(max1 - min1 + 1) + min1;
                        numerosGenerados.add(num);
                    }

                    // Calcular el promedio
                    double promedio = calcularPromedio(numerosGenerados);

                    // Agregar a la tabla de Secuencia
                    modelRangos.addRow(new Object[]{"Secuencia " + secuenciaCount, min1, max1});

                    // Agregar el promedio a la tabla de promedios
                    modelPromedios.addRow(new Object[]{"Secuencia " + secuenciaCount, promedio});

                    // Agregar los números generados a la tabla de números aleatorios
                    StringBuilder numerosString = new StringBuilder();
                    for (int i = 0; i < numerosGenerados.size(); i++) {
                        if (i > 0) numerosString.append(", ");
                        numerosString.append(numerosGenerados.get(i));
                    }
                    modelNumeros.addRow(new Object[]{"Secuencia " + secuenciaCount, numerosString.toString()});

                    // Incrementar el contador de secuencias
                    secuenciaCount++;

                    // Limpiar las cajas de texto después de agregar
                    min1Field.setText("");
                    max1Field.setText("");
                    cantidadField.setText("");

                    // Desplazar la tabla hacia abajo automáticamente para ver la última fila
                    JScrollBar vertical = rangoScrollPane.getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());

                    JScrollBar verticalPromedio = promedioScrollPane.getVerticalScrollBar();
                    verticalPromedio.setValue(verticalPromedio.getMaximum());

                    JScrollBar verticalNumeros = numerosScrollPane.getVerticalScrollBar();
                    verticalNumeros.setValue(verticalNumeros.getMaximum());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor ingresa números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para borrar un rango seleccionado
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = rangoTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Borrar la fila seleccionada en todas las tablas
                    modelRangos.removeRow(selectedRow);
                    modelPromedios.removeRow(selectedRow);
                    modelNumeros.removeRow(selectedRow);
                } else {
                    // Si no hay ninguna fila seleccionada, borrar todas las filas
                    int confirm = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que deseas borrar todas las secuencias?",
                            "Borrar Todo", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Eliminar todas las filas en las tres tablas
                        modelRangos.setRowCount(0);
                        modelPromedios.setRowCount(0);
                        modelNumeros.setRowCount(0);
                    }
                }

                // Limpiar las cajas de texto después de borrar
                min1Field.setText("");
                max1Field.setText("");
                cantidadField.setText("");
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }

    // Método para calcular el promedio de los números generados
    public static double calcularPromedio(ArrayList<Integer> numeros) {
        int suma = 0;
        for (int num : numeros) {
            suma += num;
        }
        return (double) suma / numeros.size();
    }
}