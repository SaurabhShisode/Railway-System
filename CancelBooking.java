import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CancelBooking extends JFrame {
    private int loggedInUserId;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;

    public CancelBooking(int userId) {
        this.loggedInUserId = userId;

        setTitle("Cancel Booking");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(154, 156, 143));

        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("YOUR BOOKINGS");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(new Color(154, 156, 143));
        headingPanel.add(headingLabel);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Booking ID", "Train ID", "Date", "Class", "Type", "Seat Number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelBookingButton.setBackground(new Color(154, 156, 143));
        cancelBookingButton.setForeground(Color.BLACK);

        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(154, 156, 143));
        backButton.setForeground(Color.BLACK);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));

        cancelBookingButton.setPreferredSize(new Dimension(80, 30));
        backButton.setPreferredSize(new Dimension(80, 30));

        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(backButton);

        add(headingPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        fetchUserBookings();

        setVisible(true);
    }

    private void fetchUserBookings() {
        String dbUrl = "jdbc:mysql://localhost:3306/railwaysystem";
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT booking_id, train_id, date, class, type, seat_number FROM Bookings WHERE user_id = ? AND status = 'Booked'")) {
            ps.setInt(1, loggedInUserId);
            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0);

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int trainId = rs.getInt("train_id");
                java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
                String classType = rs.getString("class");
                String type = rs.getString("type");
                String seatNumber = rs.getString("seat_number");

                tableModel.addRow(new Object[]{bookingId, trainId, sqlDate, classType, type, seatNumber});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching bookings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBooking() {
        int selectedRow = bookingsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookingId = (int) tableModel.getValueAt(selectedRow, 0);

        String dbUrl = "jdbc:mysql://localhost:3306/railwaysystem";
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE Bookings SET status = 'Canceled' WHERE booking_id = ? AND user_id = ? AND status = 'Booked'")) {
            ps.setInt(1, bookingId);
            ps.setInt(2, loggedInUserId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Booking canceled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                fetchUserBookings();
            } else {
                JOptionPane.showMessageDialog(this, "No matching active booking found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error canceling booking: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancelBooking(6));
    }
}
