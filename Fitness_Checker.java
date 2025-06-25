import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class fitnesschecker {
    // Session history storage
    private static java.util.List<String> history = new java.util.ArrayList<>();
    private static boolean useMetric = true; // true=metric, false=imperial
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SECONDARY_COLOR = new Color(100, 149, 237);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        // Helper for unit toggle
        String[] units = {"Metric (kg, cm)", "Imperial (lb, in)"};
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the frame
        JFrame frame = new JFrame("Fitness Tracker Pro");
        frame.setSize(500, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        // Main container with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Dark mode state
        final boolean[] darkMode = {false};
        Color DARK_BG = new Color(40, 44, 52);
        Color DARK_FG = new Color(220, 220, 220);
        Color DARK_PANEL = new Color(60, 63, 65);
        Color DARK_ACCENT = new Color(100, 149, 237);

        // Motivational quotes
        String[] quotes = {
            "Push yourself, because no one else is going to do it for you!",
            "Fitness is not about being better than someone else. It's about being better than you used to be.",
            "Success starts with self-discipline.",
            "The pain you feel today will be the strength you feel tomorrow.",
            "Don’t limit your challenges. Challenge your limits!",
            "You don’t have to be extreme, just consistent.",
            "Small progress is still progress.",
            "It never gets easier, you just get stronger."
        };
        JLabel quoteLabel = new JLabel(quotes[(int)(Math.random() * quotes.length)], SwingConstants.CENTER);
        quoteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        quoteLabel.setForeground(new Color(80, 80, 80));
        quoteLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 8, 0));
        JLabel titleLabel = new JLabel("FITNESS TRACKER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        JButton darkModeBtn = new JButton("🌙 Dark Mode");
        darkModeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        darkModeBtn.setFocusPainted(false);
        darkModeBtn.setBackground(SECONDARY_COLOR);
        darkModeBtn.setForeground(Color.WHITE);
        darkModeBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(darkModeBtn, BorderLayout.EAST);

        // Input Panel with GridBagLayout for better alignment
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Enter Your Details"),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Make input fields expand horizontally

        // Styling for labels and inputs
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(200, 35);
        Color fieldBg = new Color(255, 255, 255);

        // Unit Toggle
        JLabel unitLabel = createStyledLabel("Units:", labelFont);
        JComboBox<String> unitBox = new JComboBox<>(units);
        styleComboBox(unitBox, fieldSize, inputFont);
        
        // Gender Input
        JLabel genderLabel = createStyledLabel("Gender:", labelFont);
        String[] genders = {"Select Gender", "Male", "Female", "Other"};
        JComboBox<String> genderBox = new JComboBox<>(genders);
        styleComboBox(genderBox, fieldSize, inputFont);
        
        // Age Input
        JLabel ageLabel = createStyledLabel("Age:", labelFont);
        JTextField ageField = new JTextField();
        styleTextField(ageField, fieldSize, inputFont);
        
        // Weight Input
        JLabel weightLabel = createStyledLabel("Weight:", labelFont);
        JTextField weightField = new JTextField();
        styleTextField(weightField, fieldSize, inputFont);
        
        // Height Input
        JLabel heightLabel = createStyledLabel("Height:", labelFont);
        JTextField heightField = new JTextField();
        styleTextField(heightField, fieldSize, inputFont);
        
        // Add components to input panel (GridBagLayout)
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(unitLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(unitBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(genderLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(genderBox, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(ageLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; inputPanel.add(ageField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(weightLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; inputPanel.add(weightField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(heightLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; inputPanel.add(heightField, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JButton calculateButton = createStyledButton("CALCULATE BMI", PRIMARY_COLOR, Color.WHITE);
        JButton resetButton = createStyledButton("RESET", new Color(100, 100, 100), Color.WHITE);
        
        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);

        // Result Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(BACKGROUND_COLOR);
        resultPanel.setBorder(BorderFactory.createTitledBorder("Your Results"));
        
        JLabel resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setForeground(PRIMARY_COLOR);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel bmiScalePanel = createBmiScalePanel();
        bmiScalePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea tipsArea = new JTextArea(5, 30);
        tipsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tipsArea.setLineWrap(true);
        tipsArea.setWrapStyleWord(true);
        tipsArea.setEditable(false);
        tipsArea.setBackground(new Color(240, 240, 245));
        tipsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane scrollPane = new JScrollPane(tipsArea);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton copyBtn = new JButton("Copy Results");
        copyBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        copyBtn.setBackground(new Color(120, 180, 120));
        copyBtn.setForeground(Color.WHITE);
        copyBtn.setFocusPainted(false);
        copyBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        copyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // History Panel (vertical, below results)
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(BACKGROUND_COLOR);
        historyPanel.setBorder(BorderFactory.createTitledBorder("Session History"));
        JTextArea historyArea = new JTextArea(6, 30);
        historyArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyPanel.add(historyScroll, BorderLayout.CENTER);
        
        // Add components to result panel (vertical)
        resultPanel.add(resultLabel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        resultPanel.add(bmiScalePanel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        resultPanel.add(scrollPane);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        resultPanel.add(copyBtn);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        resultPanel.add(historyPanel);
        
        // Add all panels to main panel (vertical order)
        mainPanel.add(headerPanel);
        mainPanel.add(quoteLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Center result panel horizontally in a scrollable area
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add main panel to a scroll pane for scrollability
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.setContentPane(mainScrollPane);

        // Add action listeners
        unitBox.addActionListener(e -> {
            useMetric = unitBox.getSelectedIndex() == 0;
            if (useMetric) {
                weightLabel.setText("Weight (kg):");
                heightLabel.setText("Height (cm):");
            } else {
                weightLabel.setText("Weight (lb):");
                heightLabel.setText("Height (in):");
            }
        });

        calculateButton.addActionListener(e -> {
            // Show a new motivational quote each time
            quoteLabel.setText(quotes[(int)(Math.random() * quotes.length)]);
            try {
                // Input validation
                if (genderBox.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(frame, "Please select your gender", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String gender = (String) genderBox.getSelectedItem();
                int age = Integer.parseInt(ageField.getText().trim());
                if (age < 2 || age > 120) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age (2-120)", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double weight = Double.parseDouble(weightField.getText().trim());
                double height = Double.parseDouble(heightField.getText().trim());
                if (useMetric) {
                    if (weight <= 0 || weight > 500) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid weight (0-500 kg)", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (height <= 0 || height > 300) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid height (1-300 cm)", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    if (weight <= 0 || weight > 1100) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid weight (0-1100 lb)", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (height <= 0 || height > 120) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid height (1-120 in)", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // Convert to metric for calculation
                double weightKg = useMetric ? weight : weight * 0.453592;
                double heightM = useMetric ? height / 100.0 : height * 0.0254;

                // BMI Calculation
                double bmi = weightKg / (heightM * heightM);
                String bmiCategory;
                String feedback;
                String tips;
                Color categoryColor;
                String healthRisk;

                if (bmi < 18.5) {
                    bmiCategory = "Underweight";
                    feedback = "Consider consulting a nutritionist";
                    healthRisk = "Malnutrition risk";
                    tips = "• Increase calorie intake with nutrient-dense foods\n" +
                           "• Include healthy fats (avocados, nuts, olive oil)\n" +
                           "• Strength training to build muscle mass\n" +
                           "• Eat smaller, more frequent meals";
                    categoryColor = new Color(70, 130, 200); // Blue
                } else if (bmi < 25) {
                    bmiCategory = "Normal weight";
                    feedback = "Great job! Maintain your healthy lifestyle";
                    healthRisk = "Low risk";
                    tips = "• Continue balanced diet and regular exercise\n" +
                           "• Include variety in your meals\n" +
                           "• Stay hydrated\n" +
                           "• Get enough sleep and manage stress";
                    categoryColor = new Color(60, 179, 113); // Green
                } else if (bmi < 30) {
                    bmiCategory = "Overweight";
                    feedback = "Consider a balanced fitness plan";
                    healthRisk = "Enhanced risk";
                    tips = "• Focus on portion control\n" +
                           "• Increase physical activity (150+ min/week)\n" +
                           "• Reduce processed foods and sugary drinks\n" +
                           "• Get adequate sleep (7-9 hours)";
                    categoryColor = new Color(255, 165, 0); // Orange
                } else {
                    bmiCategory = "Obese";
                    feedback = "Please consult a healthcare professional";
                    healthRisk = "High risk";
                    tips = "• Seek guidance from a dietitian\n" +
                           "• Start with low-impact exercises\n" +
                           "• Focus on sustainable lifestyle changes\n" +
                           "• Consider professional support if needed";
                    categoryColor = new Color(220, 20, 60); // Red
                }

                // BMR Calculation (Mifflin-St Jeor)
                double bmr;
                if (gender.equals("Male")) {
                    bmr = 10 * weightKg + 6.25 * (useMetric ? height : height * 2.54) - 5 * age + 5;
                } else {
                    bmr = 10 * weightKg + 6.25 * (useMetric ? height : height * 2.54) - 5 * age - 161;
                }
                // Daily calorie needs (assume sedentary)
                double calorieNeeds = bmr * 1.2;

                // Workout suggestion
                String workout;
                if (bmi < 18.5) {
                    workout = "Focus: Strength & Nutrition\nTry: Resistance training, yoga, healthy calorie surplus meals.";
                } else if (bmi < 25) {
                    workout = "Focus: Maintenance & Cardio\nTry: Brisk walking, cycling, HIIT 2x/week, strength 2x/week.";
                } else if (bmi < 30) {
                    workout = "Focus: Fat Loss & Cardio\nTry: Daily brisk walking, cycling, light jogging, full-body strength.";
                } else {
                    workout = "Focus: Low Impact & Mobility\nTry: Swimming, water aerobics, chair exercises, walking.";
                }

                // Update UI with results
                resultLabel.setText(String.format("<html><div style='text-align: center;'><b>BMI: %s</b> - <span style='color:rgb(%d,%d,%d)'>%s</span><br>%s<br><i>Health Risk:</i> %s<br><br><b>BMR:</b> %s kcal/day<br><b>Daily Calorie Needs:</b> %s kcal</div></html>",
                    df.format(bmi),
                    categoryColor.getRed(), categoryColor.getGreen(), categoryColor.getBlue(),
                    bmiCategory.toUpperCase(),
                    feedback,
                    healthRisk,
                    df.format(bmr),
                    df.format(calorieNeeds)));
                tipsArea.setText(tips + "\n\nWorkout Suggestion:\n" + workout);
                updateBmiScale(bmiScalePanel, bmi);

                // Save to session history
                String entry = String.format("BMI: %s (%s), BMR: %s kcal, Cal: %s kcal, Age: %d, Gender: %s, Wt: %s, Ht: %s", df.format(bmi), bmiCategory, df.format(bmr), df.format(calorieNeeds), age, gender, weightField.getText(), heightField.getText());
                history.add(entry);
                historyArea.setText(String.join("\n", history));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter valid numerical values for all fields", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        resetButton.addActionListener(e -> {
            genderBox.setSelectedIndex(0);
            ageField.setText("");
            weightField.setText("");
            heightField.setText("");
            resultLabel.setText(" ");
            tipsArea.setText("");
            resetBmiScale(bmiScalePanel);
        });

        // Copy results to clipboard
        copyBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append(resultLabel.getText().replaceAll("<[^>]*>", "").replaceAll("\\s+", " ").trim()).append("\n\n");
            sb.append(tipsArea.getText());
            java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(sb.toString());
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(frame, "Results copied to clipboard!", "Copied", JOptionPane.INFORMATION_MESSAGE);
        });

        // Dark mode toggle
        darkModeBtn.addActionListener(e -> {
            darkMode[0] = !darkMode[0];
            if (darkMode[0]) {
                mainPanel.setBackground(DARK_BG);
                headerPanel.setBackground(DARK_ACCENT);
                inputPanel.setBackground(DARK_PANEL);
                buttonPanel.setBackground(DARK_PANEL);
                resultPanel.setBackground(DARK_PANEL);
                historyPanel.setBackground(DARK_PANEL);
                bmiScalePanel.setBackground(DARK_PANEL);
                tipsArea.setBackground(new Color(60, 63, 65));
                tipsArea.setForeground(DARK_FG);
                historyArea.setBackground(new Color(60, 63, 65));
                historyArea.setForeground(DARK_FG);
                resultLabel.setForeground(DARK_ACCENT);
                quoteLabel.setForeground(DARK_FG);
                darkModeBtn.setText("☀ Light Mode");
            } else {
                mainPanel.setBackground(BACKGROUND_COLOR);
                headerPanel.setBackground(PRIMARY_COLOR);
                inputPanel.setBackground(BACKGROUND_COLOR);
                buttonPanel.setBackground(BACKGROUND_COLOR);
                resultPanel.setBackground(BACKGROUND_COLOR);
                historyPanel.setBackground(BACKGROUND_COLOR);
                bmiScalePanel.setBackground(BACKGROUND_COLOR);
                tipsArea.setBackground(new Color(240, 240, 245));
                tipsArea.setForeground(Color.BLACK);
                historyArea.setBackground(Color.WHITE);
                historyArea.setForeground(Color.BLACK);
                resultLabel.setForeground(PRIMARY_COLOR);
                quoteLabel.setForeground(new Color(80, 80, 80));
                darkModeBtn.setText("🌙 Dark Mode");
            }
        });

        // Add window close listener
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to exit?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Center the frame and make it visible
        frame.pack();
        frame.setMinimumSize(new Dimension(700, 700));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "Fitness Tracker App launched!", "Startup", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Helper methods for UI components
    private static JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(50, 50, 50));
        return label;
    }
    
    private static void styleTextField(JTextField field, Dimension size, Font font) {
        field.setPreferredSize(size);
        field.setFont(font);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(Color.WHITE);
    }
    
    private static void styleComboBox(JComboBox<String> comboBox, Dimension size, Font font) {
        comboBox.setPreferredSize(size);
        comboBox.setFont(font);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210)));
    }
    
    private static JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private static JPanel createBmiScalePanel() {
        JPanel scalePanel = new JPanel(new GridLayout(1, 5, 0, 0));
        scalePanel.setBackground(BACKGROUND_COLOR);
        scalePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));
        
        String[] categories = {"Underweight\n<18.5", "Normal\n18.5-24.9", "Overweight\n25-29.9", "Obese\n30-34.9", "Extremely Obese\n35+"};
        Color[] colors = {
            new Color(70, 130, 200),    // Blue
            new Color(60, 179, 113),    // Green
            new Color(255, 165, 0),     // Orange
            new Color(220, 20, 60),      // Red
            new Color(139, 0, 0)         // Dark Red
        };
        
        for (int i = 0; i < categories.length; i++) {
            JPanel section = new JPanel(new BorderLayout());
            section.setBackground(colors[i]);
            section.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            
            JLabel label = new JLabel("<html><div style='text-align:center;color:white;font-size:10px;'>" + 
                                   categories[i].replace("\n", "<br>") + "</div></html>");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            section.add(label, BorderLayout.SOUTH);
            
            // Add a hidden marker that will be shown when BMI is calculated
            JLabel marker = new JLabel("▲");
            marker.setForeground(colors[i].darker());
            marker.setFont(new Font("Arial", Font.BOLD, 16));
            marker.setHorizontalAlignment(SwingConstants.CENTER);
            marker.setVisible(false);
            marker.setName("marker_" + i);
            section.add(marker, BorderLayout.CENTER);
            
            scalePanel.add(section);
        }
        
        return scalePanel;
    }
    
    private static void updateBmiScale(JPanel scalePanel, double bmi) {
        // Reset all markers
        resetBmiScale(scalePanel);
        
        // Determine which section to highlight
        int sectionIndex;
        if (bmi < 18.5) sectionIndex = 0;
        else if (bmi < 25) sectionIndex = 1;
        else if (bmi < 30) sectionIndex = 2;
        else if (bmi < 35) sectionIndex = 3;
        else sectionIndex = 4;
        
        // Show the marker for the current BMI category
        Component[] sections = scalePanel.getComponents();
        if (sectionIndex < sections.length) {
            JPanel section = (JPanel) sections[sectionIndex];
            for (Component comp : section.getComponents()) {
                if (comp.getName() != null && comp.getName().equals("marker_" + sectionIndex)) {
                    comp.setVisible(true);
                    break;
                }
            }
        }
    }
    
    private static void resetBmiScale(JPanel scalePanel) {
        for (Component section : scalePanel.getComponents()) {
            if (section instanceof JPanel) {
                for (Component comp : ((JPanel) section).getComponents()) {
                    if (comp.getName() != null && comp.getName().startsWith("marker_")) {
                        comp.setVisible(false);
                    }
                }
            }
        }
    }
}
