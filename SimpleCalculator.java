 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends JFrame implements ActionListener {

    JTextField display;

    double num1, num2, result;
    char operator;
    boolean newNumber = true;

    SimpleCalculator() {

        setTitle("Simple Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 25));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));

        String[] keys = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", ".", "+"
        };

        for (String key : keys) {
            JButton button = new JButton(key);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            panel.add(button);
        }

        JButton equal = new JButton("=");
        equal.setFont(new Font("Arial", Font.BOLD, 22));
        equal.addActionListener(this);

        add(panel, BorderLayout.CENTER);
        add(equal, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        // Number buttons
        if (command.matches("[0-9]")) {

            if (newNumber || display.getText().equals("0")) {
                display.setText(command);
                newNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        }

        // Decimal button
        else if (command.equals(".")) {

            if (newNumber) {
                display.setText("0.");
                newNumber = false;
            }
            else if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        }

        // Clear button
        else if (command.equals("C")) {

            display.setText("0");
            num1 = num2 = result = 0;
            operator = '\0';
            newNumber = true;
        }

        // Operator buttons
        else if (command.equals("+") || command.equals("-")
                || command.equals("*") || command.equals("/")) {

            if (!newNumber) {
                num1 = Double.parseDouble(display.getText());
            }

            operator = command.charAt(0);
            newNumber = true;
        }

        // Equal button
        else if (command.equals("=")) {

            if (operator == '\0' || newNumber) {
                return;
            }

            num2 = Double.parseDouble(display.getText());

            switch (operator) {

                case '+':
                    result = num1 + num2;
                    break;

                case '-':
                    result = num1 - num2;
                    break;

                case '*':
                    result = num1 * num2;
                    break;

                case '/':
                    if (num2 == 0) {
                        display.setText("Error");
                        operator = '\0';
                        newNumber = true;
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            display.setText(
                result == (long) result
                ? String.valueOf((long) result)
                : String.valueOf(result)
            );

            num1 = result;
            operator = '\0';
            newNumber = true;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
            new SimpleCalculator()
        );
    }
}
