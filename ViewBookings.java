import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ViewBookings extends JFrame {
    private JTable bookingsTable;

    public ViewBookings(int userId) {
        setTitle("View Bookings");
        setSize(800, 300); // Adjust size for better visibility
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set layout
        setLayout(new BorderLayout());

        // Add a header label
        JLabel headerLabel = new JLabel("My Bookings", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
        add(headerLabel, BorderLayout.NORTH);

        // Initialize JTable and set it inside a JScrollPane
        bookingsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(154, 156, 143)); // Light background

        // Create a back button
        JButton backButton = createButton("Back");
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Load bookings for the given user
        loadBookings(userId);

        // Action listener for the back button
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    // Load bookings based on the logged-in user's ID
    private void loadBookings(int userId) {
        String url = "jdbc:mysql://localhost:3306/RailwaySystem";
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT b.booking_id, u.name, t.name AS train_name, b.date, b.class, b.seat_number, b.status " +
                    "FROM Bookings b " +
                    "JOIN Users u ON b.user_id = u.user_id " +
                    "JOIN Trains t ON b.train_id = t.train_id " +
                    "WHERE b.user_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            // Create a model for the JTable
            DefaultTableModel tableModel = new DefaultTableModel(
                    new Object[]{"Booking ID", "User Name", "Train Name", "Date", "Class", "Seat Number", "Status"}, 0);

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("booking_id"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("train_name"));
                row.add(resultSet.getDate("date"));
                row.add(resultSet.getString("class"));
                row.add(resultSet.getString("seat_number"));
                row.add(resultSet.getString("status"));
                tableModel.addRow(row);
            }

            bookingsTable.setModel(tableModel);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bookings from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to create a styled button with fixed size
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(50, 30)); // Fixed size
        return button;
    }

    // Main method to test with a specific user ID
    public static void main(String[] args) {
        int loggedInUserId = 6; // Replace this with actual user ID after login
        SwingUtilities.invokeLater(() -> new ViewBookings(loggedInUserId));
    }
}
