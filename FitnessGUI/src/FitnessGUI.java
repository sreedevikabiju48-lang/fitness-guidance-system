import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FitnessGUI extends JFrame {

    JTextField nameField, heightField, weightField, ageField;
    JTextArea outputArea;
    Connection conn;

    // -------- CONSTRUCTOR --------
    public FitnessGUI() {

        setTitle("Fitness Guidance System");
        setSize(500, 450);
        setLayout(new BorderLayout(10,10));

        JPanel formPanel = new JPanel(new GridLayout(4,2,10,10));

        formPanel.add(new JLabel("Enter Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Height (meters):"));
        heightField = new JTextField();
        formPanel.add(heightField);

        formPanel.add(new JLabel("Weight (kg):"));
        weightField = new JTextField();
        formPanel.add(weightField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Calculate & Save");
        JButton viewBtn = new JButton("View Users");

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);

        add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // 🔵 IMPORTANT: These must be INSIDE constructor
        connectDB();
        createTable();

        addBtn.addActionListener(e -> calculateAndSave());
        viewBtn.addActionListener(e -> viewUsers());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // -------- DATABASE CONNECTION --------
    private void connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:fitness.db");
            outputArea.append("Database Connected\n");
        } catch (SQLException e) {
            outputArea.append("Database Error\n");
        }
    }

    // -------- CREATE TABLE --------
    private void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "height REAL," +
                    "weight REAL," +
                    "age INTEGER," +
                    "bmi REAL," +
                    "guidance TEXT)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            outputArea.append("Table Error\n");
        }
    }

    // -------- CALCULATE & SAVE --------
    private void calculateAndSave() {
        try {
            String name = nameField.getText();
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int age = Integer.parseInt(ageField.getText());

            double bmi = weight / (height * height);
            String guidance;

            if (bmi < 18.5)
                guidance = "Underweight";
            else if (bmi < 25)
                guidance = "Normal";
            else if (bmi < 30)
                guidance = "Overweight";
            else
                guidance = "Obese";

            String sql = "INSERT INTO users(name,height,weight,age,bmi,guidance) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, height);
            pstmt.setDouble(3, weight);
            pstmt.setInt(4, age);
            pstmt.setDouble(5, bmi);
            pstmt.setString(6, guidance);
            pstmt.executeUpdate();

            outputArea.setText("BMI: " + String.format("%.2f", bmi) +
                    "\nGuidance: " + guidance +
                    "\nSaved Successfully!");

        } catch (Exception e) {
            outputArea.setText("Invalid Input!");
        }
    }

    // -------- VIEW USERS --------
    private void viewUsers() {
        try {
            String sql = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            outputArea.setText("---- Users ----\n");

            while (rs.next()) {
                outputArea.append(
                        "ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", BMI: " + rs.getDouble("bmi") +
                        ", Guidance: " + rs.getString("guidance") + "\n"
                );
            }

        } catch (SQLException e) {
            outputArea.setText("Fetch Error");
        }
    }

    // -------- MAIN --------
    public static void main(String[] args) {
    FitnessGUI frame = new FitnessGUI();
    frame.setVisible(true);          
}
}
