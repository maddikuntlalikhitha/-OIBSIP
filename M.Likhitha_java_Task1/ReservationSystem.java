import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class ReservationSystem extends JFrame implements ActionListener {

    Connection con;

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    JTextField passengerField;
    JTextField trainNumberField;
    JTextField trainNameField;
    JComboBox<String> classBox;
    JTextField dateField;
    JTextField sourceField;
    JTextField destinationField;

    JButton bookButton;
    JButton cancelPageButton;

    JTextField pnrField;
    JTextArea bookingDetailsArea;

    JButton fetchButton;
    JButton confirmCancelButton;
    JButton backButton;

   
    String fetchedPNR = "";

  
    public ReservationSystem() {

        connectDatabase();
        createTable();

        showLoginPage();
    }


   

    public void connectDatabase() {

        try {

            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection(
                    "jdbc:sqlite:reservation.db");

            System.out.println("Database Connected Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "Database Connection Failed: " + e.getMessage());
        }
    }




    public void createTable() {

        try {

            String sql = "CREATE TABLE IF NOT EXISTS reservations ("
                    + "pnr TEXT PRIMARY KEY,"
                    + "passengerName TEXT,"
                    + "trainNumber TEXT,"
                    + "trainName TEXT,"
                    + "classType TEXT,"
                    + "journeyDate TEXT,"
                    + "source TEXT,"
                    + "destination TEXT"
                    + ")";

            Statement st = con.createStatement();

            st.execute(sql);

            System.out.println("Table Ready");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }




    public void showLoginPage() {

        getContentPane().removeAll();

        setTitle("Online Reservation System - Login");

        setSize(500, 400);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("ONLINE RESERVATION SYSTEM");

        title.setFont(new Font("Arial", Font.BOLD, 20));

        title.setBounds(100, 40, 350, 30);

        add(title);


        JLabel userLabel = new JLabel("Username:");

        userLabel.setBounds(80, 120, 100, 30);

        add(userLabel);


        usernameField = new JTextField();

        usernameField.setBounds(190, 120, 180, 30);

        add(usernameField);


        JLabel passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(80, 180, 100, 30);

        add(passwordLabel);


        passwordField = new JPasswordField();

        passwordField.setBounds(190, 180, 180, 30);

        add(passwordField);


        loginButton = new JButton("Login");

        loginButton.setBounds(190, 250, 100, 35);

        loginButton.addActionListener(this);

        add(loginButton);


        setLocationRelativeTo(null);

        revalidate();

        repaint();

        setVisible(true);
    }




    public void showReservationPage() {

        getContentPane().removeAll();

        setTitle("Online Reservation System - Book Ticket");

        setSize(600, 650);

        setLayout(null);


        JLabel title = new JLabel("TRAIN RESERVATION FORM");

        title.setFont(new Font("Arial", Font.BOLD, 20));

        title.setBounds(170, 20, 300, 30);

        add(title);




        JLabel passengerLabel = new JLabel("Passenger Name:");

        passengerLabel.setBounds(70, 80, 120, 30);

        add(passengerLabel);


        passengerField = new JTextField();

        passengerField.setBounds(220, 80, 250, 30);

        add(passengerField);


     

        JLabel trainNumberLabel = new JLabel("Train Number:");

        trainNumberLabel.setBounds(70, 130, 120, 30);

        add(trainNumberLabel);


        trainNumberField = new JTextField();

        trainNumberField.setBounds(220, 130, 250, 30);

        add(trainNumberField);



        JLabel trainNameLabel = new JLabel("Train Name:");

        trainNameLabel.setBounds(70, 180, 120, 30);

        add(trainNameLabel);


        trainNameField = new JTextField();

        trainNameField.setBounds(220, 180, 250, 30);

        trainNameField.setEditable(false);

        add(trainNameField);


    

        JLabel classLabel = new JLabel("Class Type:");

        classLabel.setBounds(70, 230, 120, 30);

        add(classLabel);


        String classes[] = {
                "Select Class",
                "Sleeper",
                "AC 3 Tier",
                "AC 2 Tier",
                "First Class"
        };


        classBox = new JComboBox<String>(classes);

        classBox.setBounds(220, 230, 250, 30);

        add(classBox);


    

        JLabel dateLabel = new JLabel("Journey Date:");

        dateLabel.setBounds(70, 280, 120, 30);

        add(dateLabel);


        dateField = new JTextField();

        dateField.setBounds(220, 280, 250, 30);

        dateField.setToolTipText("DD-MM-YYYY");

        add(dateField);




        JLabel sourceLabel = new JLabel("Source:");

        sourceLabel.setBounds(70, 330, 120, 30);

        add(sourceLabel);


        sourceField = new JTextField();

        sourceField.setBounds(220, 330, 250, 30);

        add(sourceField);


   

        JLabel destinationLabel = new JLabel("Destination:");

        destinationLabel.setBounds(70, 380, 120, 30);

        add(destinationLabel);


        destinationField = new JTextField();

        destinationField.setBounds(220, 380, 250, 30);

        add(destinationField);




        bookButton = new JButton("Book Ticket");

        bookButton.setBounds(150, 470, 140, 40);

        bookButton.addActionListener(this);

        add(bookButton);


        

        cancelPageButton = new JButton("Cancel Booking");

        cancelPageButton.setBounds(310, 470, 150, 40);

        cancelPageButton.addActionListener(this);

        add(cancelPageButton);


        setLocationRelativeTo(null);

        revalidate();

        repaint();
    }




    public void setTrainName() {

        String trainNumber = trainNumberField.getText();

        if (trainNumber.equals("12760")) {

            trainNameField.setText("Charminar Express");

        }

        else if (trainNumber.equals("12727")) {

            trainNameField.setText("Godavari Express");

        }

        else if (trainNumber.equals("12723")) {

            trainNameField.setText("Telangana Express");

        }

        else if (trainNumber.equals("12706")) {

            trainNameField.setText("Falaknuma Express");

        }

        else {

            trainNameField.setText("Unknown Train");
        }
    }




    public String generatePNR() {

        Random random = new Random();

        int number = 100000 + random.nextInt(900000);

        return "PNR" + number;
    }


  

    public void bookTicket() {

        String passenger = passengerField.getText();

        String trainNumber = trainNumberField.getText();

        String trainName = trainNameField.getText();

        String classType = classBox.getSelectedItem().toString();

        String date = dateField.getText();

        String source = sourceField.getText();

        String destination = destinationField.getText();




        if (passenger.isEmpty()
                || trainNumber.isEmpty()
                || date.isEmpty()
                || source.isEmpty()
                || destination.isEmpty()
                || classType.equals("Select Class")) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields");

            return;
        }




        try {

            Integer.parseInt(trainNumber);

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Train Number must be numeric");

            return;
        }


   

        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {

            JOptionPane.showMessageDialog(this,
                    "Enter date in DD-MM-YYYY format");

            return;
        }


        setTrainName();

        trainName = trainNameField.getText();


        String pnr = generatePNR();


        try {

            String sql = "INSERT INTO reservations VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            PreparedStatement ps = con.prepareStatement(sql);


            ps.setString(1, pnr);

            ps.setString(2, passenger);

            ps.setString(3, trainNumber);

            ps.setString(4, trainName);

            ps.setString(5, classType);

            ps.setString(6, date);

            ps.setString(7, source);

            ps.setString(8, destination);


            ps.executeUpdate();


            String message =
                    "BOOKING SUCCESSFUL!\n\n"
                            + "PNR Number: " + pnr + "\n"
                            + "Passenger: " + passenger + "\n"
                            + "Train Number: " + trainNumber + "\n"
                            + "Train Name: " + trainName + "\n"
                            + "Class: " + classType + "\n"
                            + "Journey Date: " + date + "\n"
                            + "From: " + source + "\n"
                            + "To: " + destination;


            JOptionPane.showMessageDialog(this,
                    message,
                    "Booking Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);


            clearReservationFields();


        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Booking Failed: " + e.getMessage());
        }
    }


  

    public void clearReservationFields() {

        passengerField.setText("");

        trainNumberField.setText("");

        trainNameField.setText("");

        classBox.setSelectedIndex(0);

        dateField.setText("");

        sourceField.setText("");

        destinationField.setText("");
    }


  

    public void showCancellationPage() {

        getContentPane().removeAll();

        setTitle("Online Reservation System - Cancellation");

        setSize(600, 550);

        setLayout(null);


        JLabel title = new JLabel("CANCEL RESERVATION");

        title.setFont(new Font("Arial", Font.BOLD, 20));

        title.setBounds(180, 30, 250, 30);

        add(title);


        JLabel pnrLabel = new JLabel("Enter PNR Number:");

        pnrLabel.setBounds(70, 100, 150, 30);

        add(pnrLabel);


        pnrField = new JTextField();

        pnrField.setBounds(220, 100, 220, 30);

        add(pnrField);


        fetchButton = new JButton("Fetch");

        fetchButton.setBounds(450, 100, 80, 30);

        fetchButton.addActionListener(this);

        add(fetchButton);


        bookingDetailsArea = new JTextArea();

        bookingDetailsArea.setEditable(false);

        bookingDetailsArea.setFont(new Font("Arial", Font.PLAIN, 15));


        JScrollPane scroll = new JScrollPane(bookingDetailsArea);

        scroll.setBounds(80, 170, 430, 200);

        add(scroll);


        confirmCancelButton = new JButton("Confirm Cancellation");

        confirmCancelButton.setBounds(190, 410, 180, 40);

        confirmCancelButton.addActionListener(this);

        add(confirmCancelButton);


        backButton = new JButton("Back");

        backButton.setBounds(250, 460, 100, 30);

        backButton.addActionListener(this);

        add(backButton);


        setLocationRelativeTo(null);

        revalidate();

        repaint();
    }


   

    public void fetchBooking() {

        String pnr = pnrField.getText();


        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter PNR Number");

            return;
        }


        try {

            String sql =
                    "SELECT * FROM reservations WHERE pnr = ?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ps.setString(1, pnr);


            ResultSet rs = ps.executeQuery();


            if (rs.next()) {

                fetchedPNR = pnr;


                String details =
                        "BOOKING DETAILS\n\n"
                                + "PNR: " + rs.getString("pnr") + "\n"
                                + "Passenger: "
                                + rs.getString("passengerName") + "\n"
                                + "Train Number: "
                                + rs.getString("trainNumber") + "\n"
                                + "Train Name: "
                                + rs.getString("trainName") + "\n"
                                + "Class: "
                                + rs.getString("classType") + "\n"
                                + "Journey Date: "
                                + rs.getString("journeyDate") + "\n"
                                + "Source: "
                                + rs.getString("source") + "\n"
                                + "Destination: "
                                + rs.getString("destination");


                bookingDetailsArea.setText(details);

            }

            else {

                bookingDetailsArea.setText("");

                fetchedPNR = "";

                JOptionPane.showMessageDialog(this,
                        "Booking not found!");
            }

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }


   

    public void cancelBooking() {

        if (fetchedPNR.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "First fetch a valid booking");

            return;
        }


        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this booking?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);


        if (choice == JOptionPane.YES_OPTION) {

            try {

                String sql =
                        "DELETE FROM reservations WHERE pnr = ?";


                PreparedStatement ps =
                        con.prepareStatement(sql);


                ps.setString(1, fetchedPNR);


                ps.executeUpdate();


                JOptionPane.showMessageDialog(this,
                        "Booking Cancelled Successfully");


                pnrField.setText("");

                bookingDetailsArea.setText("");

                fetchedPNR = "";


            }

            catch (Exception e) {

                JOptionPane.showMessageDialog(this,
                        e.getMessage());
            }
        }
    }


  

    public void actionPerformed(ActionEvent e) {




        if (e.getSource() == loginButton) {

            String username = usernameField.getText();

            String password =
                    new String(passwordField.getPassword());


            if (username.equals("admin")
                    && password.equals("1234")) {

                JOptionPane.showMessageDialog(this,
                        "Login Successful");

                showReservationPage();

            }

            else {

                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password");
            }
        }


        

        if (e.getSource() == bookButton) {

            bookTicket();
        }


     

        if (e.getSource() == cancelPageButton) {

            showCancellationPage();
        }


      

        if (e.getSource() == fetchButton) {

            fetchBooking();
        }


      

        if (e.getSource() == confirmCancelButton) {

            cancelBooking();
        }


     

        if (e.getSource() == backButton) {

            showReservationPage();
        }
    }


  

    public static void main(String[] args) {

        new ReservationSystem();
    }
}