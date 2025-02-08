import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 450;  // Increased height to accommodate all buttons

    Color customLightGray = new Color(212, 212, 210);  // Gray color for number buttons
    Color customDarkGray = new Color(80, 80, 80);      // Dark gray for general background
    Color customBlack = new Color(28, 28, 28);         // Black color for the label
    Color customOrange = new Color(255, 149, 0);       // Orange for operator buttons
    Color customWhite = Color.WHITE;                    // White for special function buttons

    JFrame frame = new JFrame("Calculator");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel buttonsJPanel = new JPanel();

    String[] buttonValues = {
        "AC", "+/-", "%", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "=",
    };

    double result = 0;
    String currentOperation = "";
    boolean isNewOperation = true;

    Calculator() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label.setBackground(customBlack);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.PLAIN, 80));
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setText("0");
        label.setOpaque(true);

        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);

        buttonsJPanel.setLayout(new GridLayout(5, 4, 10, 10));  // Added horizontal and vertical gaps between buttons
        buttonsJPanel.setBackground(customDarkGray);
        frame.add(buttonsJPanel, BorderLayout.CENTER);

        // Create buttons and add to the grid
        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton(buttonValues[i]);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            // Apply button color based on the button value
            if (Arrays.asList( "+", "-", "*", "/", "=" ).contains(buttonValues[i])) {
                button.setBackground(customOrange);  // Orange for operators and equals
                button.setForeground(customBlack);   // Black text on orange
            } else if (Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "%", "+/-", "√").contains(buttonValues[i])) {
                button.setBackground(customLightGray);  // Gray for numbers and some utilities
                button.setForeground(customBlack);      // Black text on gray
            } else {
                button.setBackground(customWhite);      // White for special function buttons like sqrt
                button.setForeground(customBlack);      // Black text on white
            }

            buttonsJPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (buttonValue.equals("AC")) {
                        // Clear all values
                        label.setText("0");
                        result = 0;
                        currentOperation = "";
                        isNewOperation = true;
                    } else if (buttonValue.equals("+/-")) {
                        // Toggle sign
                        double numDisplay = Double.parseDouble(label.getText());
                        numDisplay *= -1;
                        label.setText(Double.toString(numDisplay));
                    } else if (buttonValue.equals("%")) {
                        // Percentage calculation
                        double numDisplay = Double.parseDouble(label.getText());
                        numDisplay /= 100;
                        label.setText(Double.toString(numDisplay));
                    } else if (buttonValue.equals("√")) {
                        // Square root calculation
                        double numDisplay = Double.parseDouble(label.getText());
                        if (numDisplay >= 0) {
                            numDisplay = Math.sqrt(numDisplay);
                            label.setText(Double.toString(numDisplay));
                        } else {
                            label.setText("Error");
                        }
                    } else if (buttonValue.equals("=")) {
                        // Perform the calculation based on the current operation
                        double currentValue = Double.parseDouble(label.getText());

                        switch (currentOperation) {
                            case "+":
                                result += currentValue;
                                break;
                            case "-":
                                result -= currentValue;
                                break;
                            case "*":
                                result *= currentValue;
                                break;
                            case "/":
                                if (currentValue != 0) {
                                    result /= currentValue;
                                } else {
                                    label.setText("Error");
                                    return;
                                }
                                break;
                        }
                        label.setText(Double.toString(result));
                        currentOperation = "";
                        isNewOperation = true;
                    } else if ("0123456789".contains(buttonValue)) {
                        // Handle digit entry
                        if (isNewOperation) {
                            label.setText(buttonValue);
                            isNewOperation = false;
                        } else {
                            label.setText(label.getText() + buttonValue);
                        }
                    } else {
                        // Handle operation entry (+, -, *, /)
                        if (!currentOperation.isEmpty()) {
                            return;
                        }
                        result = Double.parseDouble(label.getText());
                        currentOperation = buttonValue;
                        isNewOperation = true;
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
