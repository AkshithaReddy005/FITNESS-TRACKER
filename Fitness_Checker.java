import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fitnesschecker {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Enhanced Fitness Checker");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with a border layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10)); // 8 rows, 2 columns

        // Add components
        JLabel titleLabel = new JLabel("Enhanced Fitness Checker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titleLabel);
        panel.add(new JLabel()); // Placeholder for alignment

        JLabel genderLabel = new JLabel("Select your gender:");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderBox = new JComboBox<>(genders);
        panel.add(genderLabel);
        panel.add(genderBox);

        JLabel ageLabel = new JLabel("Enter your age:");
        JTextField ageField = new JTextField();
        panel.add(ageLabel);
        panel.add(ageField);

        JLabel weightLabel = new JLabel("Enter your weight (kg):");
        JTextField weightField = new JTextField();
        panel.add(weightLabel);
        panel.add(weightField);

        JLabel heightLabel = new JLabel("Enter your height (m):");
        JTextField heightField = new JTextField();
        panel.add(heightLabel);
        panel.add(heightField);

        JButton calculateButton = new JButton("Calculate BMI");
        JButton resetButton = new JButton("Reset");
        panel.add(calculateButton);
        panel.add(resetButton);

        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(resultLabel);

        JTextArea tipsArea = new JTextArea(3, 20);
        tipsArea.setLineWrap(true);
        tipsArea.setWrapStyleWord(true);
        tipsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tipsArea);
        panel.add(scrollPane);

        // Add panel to frame
        frame.add(panel);

        // Add button action listeners
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String gender = (String) genderBox.getSelectedItem();
                    int age = Integer.parseInt(ageField.getText());
                    double weight = Double.parseDouble(weightField.getText());
                    double height = Double.parseDouble(heightField.getText());

                    double bmi = weight / (height * height);
                    String feedback;
                    String tips;

                    if (bmi < 18.5) {
                        feedback = "Underweight: Consult a nutritionist.";
                        tips = "Include more healthy calories in your diet. Focus on nutrient-rich foods.";
                    } else if (bmi >= 18.5 && bmi <= 24.9) {
                        feedback = "Healthy weight: Keep it up!";
                        tips = "Maintain a balanced diet and regular exercise.";
                    } else if (bmi >= 25 && bmi <= 29.9) {
                        feedback = "Overweight: Consider a fitness plan.";
                        tips = "Focus on portion control and increase physical activity.";
                    } else {
                        feedback = "Obese: Consult a healthcare professional.";
                        tips = "Seek guidance from a dietitian and engage in low-impact exercises.";
                    }

                    resultLabel.setText(String.format("Your BMI: %.2f | %s", bmi, feedback));
                    tipsArea.setText("Fitness Tips:\n" + tips);

                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter valid numerical values!");
                    tipsArea.setText("");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genderBox.setSelectedIndex(0);
                ageField.setText("");
                weightField.setText("");
                heightField.setText("");
                resultLabel.setText("");
                tipsArea.setText("");
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
