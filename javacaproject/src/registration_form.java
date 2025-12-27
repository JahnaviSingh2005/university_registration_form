import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class registration_form extends JFrame {

    JTextField tfFullname, tfUsername, tfEmail;
    JPasswordField tfPassword;
    JButton btnRegister, btnDelete, btnUpdate;

    public registration_form() {

        setTitle("University Registration System");
        setSize(450, 500);
        setLocationRelativeTo(null);  // Center screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------- MAIN PANEL ----------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ---------- HEADING ----------
        JLabel title = new JLabel("User Registration");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(title);

        // ---------- FORM PANEL ----------
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));

        formPanel.add(new JLabel("Full Name:"));
        tfFullname = new JTextField();
        formPanel.add(tfFullname);

        formPanel.add(new JLabel("Username:"));
        tfUsername = new JTextField();
        formPanel.add(tfUsername);

        formPanel.add(new JLabel("Password:"));
        tfPassword = new JPasswordField();
        formPanel.add(tfPassword);

        formPanel.add(new JLabel("Email:"));
        tfEmail = new JTextField();
        formPanel.add(tfEmail);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ---------- BUTTON PANEL ----------
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 153, 76));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 102, 204));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnUpdate);

        mainPanel.add(buttonPanel);

        add(mainPanel);

        // ---------- EVENT HANDLERS ----------
        btnRegister.addActionListener(e -> registerUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnUpdate.addActionListener(e -> updateUser());

        setVisible(true);
    }

    // ------------------- REGISTER USER -------------------
    private void registerUser() {
        String fullname = tfFullname.getText();
        String username = tfUsername.getText();
        String password = String.valueOf(tfPassword.getPassword());
        String email = tfEmail.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/registration_db",
                    "root",
                    "Devansh@001"
            );

            String query = "INSERT INTO users (fullname, username, password, email) VALUES (?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, fullname);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, email);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // ------------------- DELETE USER -------------------
    private void deleteUser() {
        String username = tfUsername.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a username to delete.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/registration_db",
                    "root",
                    "Devansh@001"
            );

            String query = "DELETE FROM users WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "User Not Found!");
            }

            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // ------------------- UPDATE USER -------------------
    private void updateUser() {
        String fullname = tfFullname.getText();
        String username = tfUsername.getText();
        String password = String.valueOf(tfPassword.getPassword());
        String email = tfEmail.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username to update details.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/registration_db",
                    "root",
                    "Devansh@001"
            );

            String query = "UPDATE users SET fullname = ?, password = ?, email = ? WHERE username = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, fullname);
            pst.setString(2, password);
            pst.setString(3, email);
            pst.setString(4, username);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "User Not Found!");
            }

            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new registration_form();
    }
}
