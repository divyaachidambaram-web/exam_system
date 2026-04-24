import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ExamApp {

    JFrame f;
    JTextField nameField, subjectField, marksField, idField;
    JTable table;
    DefaultTableModel model;

    public ExamApp() {

        f = new JFrame("Exam Management System");

        f.setLayout(new FlowLayout());

        idField = new JTextField(5);
        

        nameField = new JTextField(10);
        subjectField = new JTextField(10);
        marksField = new JTextField(5);

        JButton addBtn = new JButton("Insert");
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");
        JButton loadBtn = new JButton("Load");

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Subject");
        model.addColumn("Marks");

        f.add(new JLabel("ID"));
        f.add(idField);
        f.add(new JLabel("Name"));
        f.add(nameField);
        f.add(new JLabel("Subject"));
        f.add(subjectField);
        f.add(new JLabel("Marks"));
        f.add(marksField);

        f.add(addBtn);
        f.add(viewBtn);
        f.add(updateBtn);
        f.add(deleteBtn);
        f.add(clearBtn);
        f.add(loadBtn);

        f.add(new JScrollPane(table));

        // BUTTON ACTIONS

        addBtn.addActionListener(e -> insertData());
        viewBtn.addActionListener(e -> viewData());
        updateBtn.addActionListener(e -> updateData());
        deleteBtn.addActionListener(e -> deleteData());
        clearBtn.addActionListener(e -> clearFields());
        loadBtn.addActionListener(e -> loadData());

        f.setSize(500, 500);
        f.setVisible(true);
    }

    // INSERT
    void insertData() {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO exam (student_name, subject, marks) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, nameField.getText());
            ps.setString(2, subjectField.getText());
            ps.setInt(3, Integer.parseInt(marksField.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(f, "Inserted Successfully");
            viewData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW
    void viewData() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM exam");

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("subject"),
                        rs.getInt("marks")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    /*void updateData() {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "UPDATE exam SET student_name=?, subject=?, marks=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, nameField.getText());
            ps.setString(2, subjectField.getText());
            ps.setInt(3, Integer.parseInt(marksField.getText()));
            ps.setInt(4, Integer.parseInt(idField.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(f, "Updated Successfully");
            viewData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/void updateData() {
    int row = table.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(f, "Please select a row and click Load first");
        return;
    }

    try {
        Connection conn = DBConnection.getConnection();

        String query = "UPDATE exam SET student_name=?, subject=?, marks=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, nameField.getText());
        ps.setString(2, subjectField.getText());
        ps.setInt(3, Integer.parseInt(marksField.getText()));
        ps.setInt(4, Integer.parseInt(idField.getText())); // ID stays same

        ps.executeUpdate();

        JOptionPane.showMessageDialog(f, "Updated Successfully");

        viewData();
        clearFields();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // DELETE
    void deleteData() {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "DELETE FROM exam WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(idField.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(f, "Deleted Successfully");
            viewData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CLEAR
    void clearFields() {
        idField.setText("");
        nameField.setText("");
        subjectField.setText("");
        marksField.setText("");
    }

    // LOAD (SELECT ROW → FILL TEXTBOX)
    void loadData() {
        int row = table.getSelectedRow();

        if (row != -1) {
            idField.setText(model.getValueAt(row, 0).toString());
            nameField.setText(model.getValueAt(row, 1).toString());
            subjectField.setText(model.getValueAt(row, 2).toString());
            marksField.setText(model.getValueAt(row, 3).toString());
        } else {
            JOptionPane.showMessageDialog(f, "Select a row first");
        }
    }

    public static void main(String[] args) {
        new ExamApp();
    }
}
