import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private int loggedInUserId;

    public Dashboard(int userId) {
        this.loggedInUserId = userId;

        setTitle("Railway System Management - Dashboard");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(154, 156, 143));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(154, 156, 143));

        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JButton searchTrainButton = createButton("Search Train & Book", buttonFont);
        JButton viewBookingsButton = createButton("View Bookings", buttonFont);
        JButton cancelBookingButton = createButton("Cancel Booking", buttonFont);
        JButton logoutButton = createButton("Logout", buttonFont);

        buttonPanel.add(searchTrainButton);
        buttonPanel.add(viewBookingsButton);
        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(logoutButton);

        add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.SOUTH);
        add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);

        searchTrainButton.addActionListener(e -> new SearchTrain(userId));
        viewBookingsButton.addActionListener(e -> new ViewBookings(loggedInUserId));
        cancelBookingButton.addActionListener(e -> new CancelBooking(loggedInUserId));
        logoutButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        setVisible(true);
    }

    private JButton createButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard(6));
    }
}
