import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calc1 extends JFrame implements ActionListener {

    String[] KEYS = { "1", "2", "3", "+", "4", "5", "6", "-",
            "7", "8", "9", "*", "0", ".", "/", "=" };
    JButton keys[] = new JButton[KEYS.length];
    JButton jbClean = new JButton("clean");
    JTextField resultText = new JTextField("0", 10);

    private boolean firstDigit = true;
    private double resultNum = 0.0;
    private String operator = "=";
    private boolean operateValidFlag = true;

    public Calc1() {
        init();
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("计算器");
        this.setLocation(800, 200);
        this.setResizable(true);
        this.setVisible(true);
        this.pack();
    }

    private void init() {
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setEditable(false);
        resultText.setBackground(Color.WHITE);

        JPanel toppanel=new JPanel();
        toppanel.add(resultText);
        jbClean.setForeground(Color.blue);
        toppanel.add(jbClean);

        JPanel calckeysPanel = new JPanel();
        calckeysPanel.setLayout(new GridLayout(4, 4));
        for(int i=0;i<KEYS.length;i++) {
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setForeground(Color.blue);
        }
        for(int i=0;i<KEYS.length;i++) {
            keys[i].addActionListener(this);
        }
        jbClean.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(toppanel,BorderLayout.NORTH);
        this.add(calckeysPanel, BorderLayout.CENTER);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if ("0123456789.".indexOf(label) >= 0) {
            handleNumber(label);

        } else if(label=="clean") {
            resultText.setText("");
        }else {
            handleOperator(label);
        }

    }



    private void handleNumber(String key) {
        if (firstDigit) {

            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            resultText.setText(resultText.getText() + key);
        }
        firstDigit = false;
    }


    private void handleOperator(String key) {
        if (operator.equals("/")) {
            if (getNumberFromText() == 0.0) {
                operateValidFlag = false;
                resultText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        }  else if (operator.equals("+")) {
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            resultNum *= getNumberFromText();
        } else if(operator.equalsIgnoreCase("/")) {
            resultNum=getNumberFromText();
        }
        else if (operator.equals("=")) {
            resultNum = getNumberFromText();
        }
        if (operateValidFlag) {
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }


    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }

    public static void main(String[] args) {
        new Calc1();
    }
}