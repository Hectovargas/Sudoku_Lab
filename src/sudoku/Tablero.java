package sudoku;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Tablero extends JFrame {
    JLabel label[] = new JLabel[10];
    int arreglo[] = new int[10];
    private int vidas = 3;
    private String numero = "";
    boolean desactivar=true;
    ImageIcon imagen2 = new ImageIcon("blanco.png");
    ImageIcon imagen3 = new ImageIcon("blanco.png");
    JLabel etiqueta0 = new JLabel("Vidas");
    JLabel etiqueta1 = new JLabel(new ImageIcon("corazon.jpg"));
    JLabel etiqueta2 = new JLabel(new ImageIcon("corazon.jpg"));
    JLabel etiqueta3 = new JLabel(new ImageIcon("corazon.jpg"));
    
    public Tablero() {
        crear_tablero();
    }
    
    private void crear_tablero() {
        setSize(680, 590);
        setLocationRelativeTo(null);
        componentes_de_panel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SUDOKU DE HECTOR VARGAS, KENNY MENJIVAR, GUILLERMO");
        setResizable(false);
    }
    
    private void componentes_de_panel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.GRAY);
                //cuadrado de afuera
                g.fillRect(0, 0, 2, 510);
                g.fillRect(0, 0, 510, 2);
                g.fillRect(509, 0, 4, 513);
                g.fillRect(0, 509, 513, 4);
                //lineas horizontales
                g.fillRect(0, 166, 510, 7);
                g.fillRect(0, 338, 510, 7);
                g.fillRect(166, 0, 7, 510);
                g.fillRect(338, 0, 7, 510);
          }
        };
        panel.setBounds(2, 2, 680, 590);
        panel.setBackground(Color.white);
        panel.setLayout(null);
        add(panel);
        botones(panel);
        numeros(panel);
        etiqueta0.setBounds(4, 527, 50, 10);
        etiqueta0.setFont(new Font("Arial", 0, 12));
    
        etiqueta1.setBounds(45, 517, 30, 30);
        etiqueta2.setBounds(80, 517, 30, 30);
        etiqueta3.setBounds(115, 517, 30, 30);
            panel.add(etiqueta0);
            panel.add(etiqueta1);
            panel.add(etiqueta2);
            panel.add(etiqueta3);
            
           
 
    }

    private void botones(JPanel panel) {
        
        JButton boton[][] = new JButton[9][9];
        Border borde = new LineBorder(Color.BLACK, 2);
        int x = 2;
        int y = 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boton[i][j] = new JButton();
                boton[i][j].setBounds(x, y, 51, 51);
                boton[i][j].setBackground(Color.white);
                boton[i][j].setFont(new Font("Arial", 0, 15));
                boton[i][j].setBorder(borde);
                panel.add(boton[i][j]);
                final int finalI = i;
                final int finalJ = j;
                boton[i][j].addActionListener(e -> {
                    actualizarLabels(boton);
                    JButton acciones_boton = (JButton) e.getSource();
                    String texto = acciones_boton.getText();
                    if(get_numero().equals("")&&desactivar==true){
                     JOptionPane.showMessageDialog(null, "Primero debe seleccionar un numero");
                     desactivar=false;
                    }
                    else if (texto.equals(get_numero())) {
                        boton[finalI][finalJ].setForeground(Color.BLUE);
                        boton[finalI][finalJ].setEnabled(false);
                        if(ganar(boton)){
                            JOptionPane.showMessageDialog(null, "¡FELICIDADES A GANADO!");
                            dispose();
                        }
                    } else {
                        set_vida(get_vida() - 1);
                        JOptionPane.showMessageDialog(null, "Ha fallado, solo tiene "+vidas+" Vidas");
                    }
                    int k = get_vida();
                    for (int l = 0; l < 9; l++) {
                        for (int m = 0; m < 9; m++) {
                          
                        }
                    }
                    
    switch (k) {
        case 2:    
            etiqueta3.setIcon(imagen2);
            break;           
        case 1:
            etiqueta2.setIcon(imagen3);
            break;
    }
                    panel.repaint();
                    if (k == 0) {
                        JOptionPane.showMessageDialog(null, "Perdiste");
                        System.exit(0);
                    }

                    set_numero("");
                    actualizarLabels(boton);
                });
                x += 57;
            }
            y += 57;
            x = 2;
        }
        numero_aleatorio(boton);
        contador_numeros(panel, boton);
        instrucciones(panel);
    }

    private void numeros(JPanel panel) {
        JLabel label_1 = new JLabel("Numeros");
        Border borde = new LineBorder(Color.BLACK, 2);
        label_1.setFont(new Font("Arial", 0, 12));
        label_1.setForeground(Color.black);
        label_1.setBounds(520, 2, 70, 11);
        panel.add(label_1);
        JButton[] boton = new JButton[10];
        int y = 15;

        for (int i = 1; i < 10; i++) {
            boton[i] = new JButton();
            boton[i].setBounds(520, y, 51, 51);
            boton[i].setBackground(Color.white);
            boton[i].setForeground(Color.BLUE);
            boton[i].setText(i + "");
            boton[i].setBorder(borde);
            boton[i].setFont(new Font("Arial", 0, 15));
            panel.add(boton[i]);
            boton[i].addActionListener(e -> {
                JButton acciones_boton = (JButton) e.getSource();
                String texto = acciones_boton.getText();
                set_numero(texto);
            });

            y += 55;
        }
    }

    public void numero_aleatorio(JButton[][] boton) {
        Random rand = new Random();
        String tablero[][] = new String[9][9];
        while (true) {
            int contador = 0;
            boolean error = false;
            int contador_error = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    boton[i][j].setText("");
                    tablero[i][j] = "";
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    while (true) {
                        int random = rand.nextInt(9) + 1;
                        contador_error++;
                        if (contador_error == 500) {
                            error = true;
                            break;
                        }
                        String random_real = random + "";
                        boolean valido = true;

                        // Verificación horizontal
                        for (int k = 0; k < 9; k++) {
                            if (boton[i][k].getText().equals(random_real)) {
                                valido = false;
                                break;
                            }
                        }

                        // Verificación vertical
                        for (int k = 0; k < 9; k++) {
                            if (boton[k][j].getText().equals(random_real)) {
                                valido = false;
                                break;
                            }
                        }

                        // Verificación en submatriz 3x3
                        int submatriz_fila_inicio = i - i % 3;
                        int submatriz_col_inicio = j - j % 3;
                        for (int k = submatriz_fila_inicio; k < submatriz_fila_inicio + 3; k++) {
                            for (int l = submatriz_col_inicio; l < submatriz_col_inicio + 3; l++) {
                                if (boton[k][l].getText().equals(random_real)) {
                                    valido = false;
                                    break;
                                }
                            }
                        }

                        if (valido) {
                            boton[i][j].setText(random_real);
                            tablero[i][j] = random_real;
                            int probabilidad = rand.nextInt(100) + 1;
                            if (probabilidad <= 35) {
                                boton[i][j].setForeground(Color.BLUE);
                                boton[i][j].setEnabled(false);

                            } else {
                                boton[i][j].setForeground(Color.WHITE);
                                boton[i][j].setEnabled(true);
                            }
                            contador++;
                            break;
                        }
                    }
                    if (error) {
                        break;
                    }
                }
                if (error) {
                    break;
                }
            }

            if (contador == 81) {
                break;
            }
        }
    }
    
    public boolean ganar(JButton boton[][]){
        Boolean verificador=true;
        for(int i=1;i<9;i++){
            for(int j=1;j<9;j++){
            if(boton[i][j].getForeground().equals(Color.BLUE)){
                verificador=true;
            }else{
                verificador=false;
            }
            if(verificador==false){
                break;
            }}
            if(verificador==false){
                break;
            }
            if(verificador==true){
                return true;
            }
        }
       return false;
    }
    
    public void contador_numeros(JPanel panel, JButton boton[][]) {

        int y = 15;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boton[i][j].getForeground().equals(Color.WHITE)) {
                    switch (boton[i][j].getText()) {
                        case 1 + "":
                            arreglo[1]++;
                            break;
                        case 2 + "":
                            arreglo[2]++;
                            break;
                        case 3 + "":
                            arreglo[3]++;
                            break;
                        case 4 + "":
                            arreglo[4]++;
                            break;
                        case 5 + "":
                            arreglo[5]++;
                            break;
                        case 6 + "":
                            arreglo[6]++;
                            break;
                        case 7 + "":
                            arreglo[7]++;
                            break;
                        case 8 + "":
                            arreglo[8]++;
                            break;
                        case 9 + "":
                            arreglo[9]++;
                            break;
                    }
                }
            }
        }
        for (int i = 1; i < 10; i++) {
            label[i] = new JLabel();
            label[i].setBounds(580, y, 100, 51);
            label[i].setForeground(Color.black);
            label[i].setText("Numero " + i + "-  " + arreglo[i] + "/9");
            label[i].setFont(new Font("Arial", 0, 12));

            panel.add(label[i]);

            y += 55;
        }
    }

    public void actualizarLabels(JButton boton[][]) {
        for (int i = 1; i < 10; i++) {
            arreglo[i] = 0;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boton[i][j].getForeground().equals(Color.white)) {
                    switch (boton[i][j].getText()) {
                        case 1 + "":
                            arreglo[1]++;
                            break;
                        case 2 + "":
                            arreglo[2]++;
                            break;
                        case 3 + "":
                            arreglo[3]++;
                            break;
                        case 4 + "":
                            arreglo[4]++;
                            break;
                        case 5 + "":
                            arreglo[5]++;
                            break;
                        case 6 + "":
                            arreglo[6]++;
                            break;
                        case 7 + "":
                            arreglo[7]++;
                            break;
                        case 8 + "":
                            arreglo[8]++;
                            break;
                        case 9 + "":
                            arreglo[9]++;
                            break;
                    }
                }
            }
        }
        for (int i = 1; i < 10; i++) {
            label[i].setText("Numero " + i + "-  " + arreglo[i] + "/9");
            
        }
    }

    public void disable_botones(JButton boton[]) {
        for (int i = 1; i < 10; i++) {
            if (arreglo[i] == 0) {
                boton[i].setEnabled(false);
            }
        }
    }

    public void set_numero(String num) {
        numero = num;
    }

    public String get_numero() {
        return numero;
    }

    public void set_vida(int vida) {
        vidas = vida;
    }

    public int get_vida() {
        return vidas;
    }
    
    public void instrucciones(JPanel panel){
        JLabel label_1 = new JLabel("para jugar toque el numero y coloquelo en el espacio del tablero,tiene 3 vidas");
        label_1.setFont(new Font("Arial", 0, 12));
        label_1.setForeground(Color.black);
        label_1.setBounds(200, 520, 400, 15);
        panel.add(label_1);
    }
}
