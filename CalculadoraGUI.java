import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CalculadoraGUI extends JFrame {

    private JLabel labelTitulo;
    private JLabel labelExpresion;
    private JLabel labelPrefijo;
    private JLabel labelPosfijo;
    private JLabel labelResultado;
    private JTextField txtPosfijo;
    private JTextField txtExpresion;
    private JTextField txtPrefijo;
    private JTextField txtResultado;
    private JButton btnLimpiar;
    private JButton btnAceptar;
    public Calculadora calculadora;
    private List<String> elementos;

    public CalculadoraGUI(String titulo) {
        super(titulo);
        init();
    }

    public void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBounds(550, 200, 500, 250);
        this.setResizable(false);
        
        calculadora = new Calculadora();
        labelTitulo = new JLabel("Calculadora");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(labelTitulo, BorderLayout.NORTH);

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.add(new JLabel(" "));

        labelExpresion = new JLabel("Expresi\u00f3n Infija");
        labelExpresion.setFont(new Font("Arial", Font.BOLD, 16));
        panelIzquierdo.add(labelExpresion);
        panelIzquierdo.add(new JLabel(" "));

        labelPrefijo = new JLabel("Prefijo");
        labelPrefijo.setFont(new Font("Arial", Font.BOLD, 16));
        panelIzquierdo.add(labelPrefijo);
        panelIzquierdo.add(new JLabel(" "));

        labelPosfijo = new JLabel("Posfijo");
        labelPosfijo.setFont(new Font("Arial", Font.BOLD, 16));
        panelIzquierdo.add(labelPosfijo);
        panelIzquierdo.add(new JLabel(" "));

        labelResultado = new JLabel("Resultado");
        labelResultado.setFont(new Font("Arial", Font.BOLD, 16));
        panelIzquierdo.add(labelResultado);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        txtExpresion = new JTextField(10);
        txtExpresion.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentral.add(txtExpresion);

        txtPrefijo = new JTextField(10);
        txtPrefijo.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPrefijo.setEditable(false);
        panelCentral.add(txtPrefijo);

        txtPosfijo = new JTextField(10);
        txtPosfijo.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPosfijo.setEditable(false);
        panelCentral.add(txtPosfijo);

        txtResultado = new JTextField(10);
        txtResultado.setFont(new Font("Arial", Font.PLAIN, 16));
        txtResultado.setEditable(false);
        panelCentral.add(txtResultado);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 16));
        panelInferior.add(btnAceptar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 16));
        panelInferior.add(btnLimpiar);

        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(panelCentral, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elementos = calculadora.dividirExpresion(txtExpresion.getText());
                txtPrefijo.setText(calculadora.infijoPrefija(txtExpresion.getText()));
                txtPosfijo.setText(calculadora.infijoPosfija(elementos));
                txtResultado.setText(calculadora.resultado(elementos));
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtExpresion.setText("");
                txtPrefijo.setText("");
                txtPosfijo.setText("");
                txtResultado.setText("");
            }
        });

        txtExpresion.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    btnAceptar.doClick();
                }
            }
        });
    }
}
