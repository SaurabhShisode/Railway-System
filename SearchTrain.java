import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import java.sql.Date;

public class SearchTrain extends JFrame {
    private int loggedInUserId;
    private Connection conn;
    private ResultSet rs;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTable table;
    private JPanel bookingFormPanel;
    private JTextField bookingIdField, trainIdField, passengerNameField, seatNumberField;
    private JComboBox<String> classComboBox, typeComboBox;

    public SearchTrain(int loggedInUserId) {
        setTitle("Search Train");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.loggedInUserId = loggedInUserId;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(154, 156, 143));

        JLabel sourceLabel = new JLabel("Source:");
        sourceField = new JTextField(15);
        JLabel destinationLabel = new JLabel("Destination:");
        destinationField = new JTextField(15);

        inputPanel.add(sourceLabel);
        inputPanel.add(sourceField);
        inputPanel.add(destinationLabel);
        inputPanel.add(destinationField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(154, 156, 143));

        JButton searchButton = createButton("Search");

        JButton backButton = createButton("Back");
        backButton.setBackground(Color.WHITE);

        backButton.addActionListener(e -> {
            dispose();
        });

        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        bookingFormPanel = createBookingFormPanel();
        add(bookingFormPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchTrains());

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(60, 25));
        return button;
    }

    private void searchTrains() {
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        if (source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both source and destination.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String username = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (username == null || password == null) {
                JOptionPane.showMessageDialog(this, "Database credentials not set.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RailwaySystem", username, password);

            String query = "SELECT * FROM trains WHERE source = ? AND destination = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, source);
            pstmt.setString(2, destination);

            rs = pstmt.executeQuery();

            table.setModel(buildTableModel(rs));

            bookingFormPanel.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }

    private JPanel createBookingFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bookingIdLabel = new JLabel("Booking ID:");
        bookingIdField = new JTextField(15);
        bookingIdField.setEditable(false);

        JLabel trainIdLabel = new JLabel("Train ID:");
        trainIdField = new JTextField(15);

        JLabel passengerNameLabel = new JLabel("Passenger Name:");
        passengerNameField = new JTextField(15);

        JLabel seatNumberLabel = new JLabel("Seat Number:");
        seatNumberField = new JTextField(15);

        JLabel classLabel = new JLabel("Class:");
        classComboBox = new JComboBox<>(new String[]{"Sleeper", "AC1", "AC2", "AC3", "General"});

        JLabel typeLabel = new JLabel("Type:");
        typeComboBox = new JComboBox<>(new String[]{"Online", "Offline", "Tatkal"});

        JButton bookButton = new JButton("Book Now");
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setBackground(new Color(154, 156, 143));
        bookButton.setForeground(Color.WHITE);

        formPanel.add(trainIdLabel);
        formPanel.add(trainIdField);
        formPanel.add(passengerNameLabel);
        formPanel.add(passengerNameField);
        formPanel.add(seatNumberLabel);
        formPanel.add(seatNumberField);
        formPanel.add(classLabel);
        formPanel.add(classComboBox);
        formPanel.add(typeLabel);
        formPanel.add(typeComboBox);
        formPanel.add(new JLabel());
        formPanel.add(bookButton);

        formPanel.setVisible(false);

        bookButton.addActionListener(e -> submitBooking());

        return formPanel;
    }

    private void submitBooking() {
        try {
            int trainId = Integer.parseInt(trainIdField.getText());
            String passengerName = passengerNameField.getText();
            String seatNumber = seatNumberField.getText();
            String selectedClass = (String) classComboBox.getSelectedItem();
            String selectedType = (String) typeComboBox.getSelectedItem();
            Date date = new Date(System.currentTimeMillis());
            String status = "Booked";

            String bookingQuery = "INSERT INTO bookings (user_id, train_id, date, class, type, seat_number, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(bookingQuery);
            pstmt.setInt(1, loggedInUserId);
            pstmt.setInt(2, trainId);
            pstmt.setDate(3, date);
            pstmt.setString(4, selectedClass);
            pstmt.setString(5, selectedType);
            pstmt.setString(6, seatNumber);
            pstmt.setString(7, status);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Booking successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during booking.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        int loggedInUserId = 6;
        SwingUtilities.invokeLater(() -> new SearchTrain(loggedInUserId));
    }
}
