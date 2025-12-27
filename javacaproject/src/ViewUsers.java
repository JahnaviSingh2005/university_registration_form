import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewUsers extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewUsers() {

        setTitle("Registered Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Full Name");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Email");

        loadData();

        add(new JScrollPane(table));
        setVisible(true);
    }

    private void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/registration_db",
                    "root",
                    "Devansh@001"
            );

            String query = "SELECT * FROM users";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                });
            }

            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}

