import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DigitalClock extends JFrame {
    private JLabel timeZoneLabel;
    private JLabel timeLabel;

    public DigitalClock() {
        setTitle("Digital Clock");
        setSize(300, 150); // Increased window size to accommodate additional text
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1)); // Using GridLayout for arranging components

        timeZoneLabel = new JLabel();
        timeZoneLabel.setHorizontalAlignment(JLabel.CENTER);
        timeZoneLabel.setForeground(Color.white); // Set font color to white
        add(timeZoneLabel);

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 24)); // Set font style to Helvetica and size
        timeLabel.setForeground(Color.white); // Set font color to white
        add(timeLabel);

        // Update time every second
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        // Set black background
        getContentPane().setBackground(Color.black);
    }

    private void updateTime() {
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        String ampm = cal.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        // Format time with AM/PM indication
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
        Date date = cal.getTime();
        String timeString = formatter.format(date);

        // Get current timezone name
        String timeZoneName = TimeZone.getDefault().getDisplayName();

        // Update labels
        timeZoneLabel.setText("Your Timezone: " + timeZoneName);
        timeLabel.setText(timeString);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DigitalClock clock = new DigitalClock();
                clock.setVisible(true);
            }
        });
    }
}
