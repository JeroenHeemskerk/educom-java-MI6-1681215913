package nu.educom.MI6;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
  private static JFrame frame;
  private static JTextField serviceNumberField;
  private static JPasswordField secretCodeField;
  private static JLabel lblServiceNumber, lblSecretCode;
  private static JButton submit;
  private static JOptionPane pane;
  private static GridBagLayout gbl;
  private static GridBagConstraints gbc;

  private static ActionListener submitInfo = new SubmitAction();

  public static void main(String[] args) throws SQLException {

  DatabaseRepository.selectAll();
  System.out.println(DatabaseRepository.authenticateAgent("002", "test002"));
  DatabaseRepository.getLastLoginAttempts("002");
  DatabaseRepository.setLoginAttempt("002", true);


    serviceNumberField = new JTextField(20);
    secretCodeField = new JPasswordField(20);

    lblServiceNumber = new JLabel("Enter your service number");
    lblSecretCode = new JLabel("Enter your secret code");

    submit = new JButton("Log in");
    submit.addActionListener(submitInfo);

    pane = new JOptionPane("test");

    gbl = new GridBagLayout();
    gbc = new GridBagConstraints();

    frame = new JFrame("Secret Service Login");
    frame.setLayout(gbl);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    frame.add(lblServiceNumber, gbc);

    gbc.gridx = 1;
    gbc.gridwidth = 5;
    gbc.weightx = 1;
    frame.add(serviceNumberField, gbc);

    gbc.gridy = 1;
    gbc.gridx = 0;
    gbc.gridwidth = 1;
    frame.add(lblSecretCode, gbc);

    gbc.gridx = 1;
    gbc.gridwidth = 5;
    frame.add(secretCodeField, gbc);

    gbc.gridy = 2;
    gbc.gridwidth = 5;
    frame.add(submit, gbc);

    gbc.gridy = 3;
    gbc.gridwidth = 5;
    pane.setVisible(false);
    frame.add(pane, gbc);

    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  public static int checkServiceNumber() {
    String input;
    int serviceNumber;
    input = serviceNumberField.getText();
    serviceNumber = stringToInt(input);
    if (serviceNumber <= 0 || serviceNumber > 956) {
      serviceNumber = -1;
    }

    return serviceNumber;
  }

  private static int stringToInt(String string) {

    try {
      return Integer.parseInt(string);
    } catch (NumberFormatException e) {
      pane.setVisible(true);
      pane.showMessageDialog(frame, "Not a number! Please enter a number.");
      pane.setVisible(false);
      return -1;
    }
  }

  private static class SubmitAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int serviceNumber = checkServiceNumber();
      String secretCode = secretCodeField.getText();
      try {
        if (DatabaseRepository.authenticateAgent(String.format("%03d",serviceNumber), secretCode)) {
          pane.setVisible(true);
          pane.showMessageDialog(frame, String.format("You have been logged in with your service number %03d ", serviceNumber));
          pane.setVisible(false);
          serviceNumberField.setText("");
          secretCodeField.setText("");
        } else {
          pane.setVisible(true);
          pane.showMessageDialog(frame, "ACCESS DENIED!", "Error", pane.ERROR_MESSAGE);
          pane.setVisible(false);
        }
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
  }
}


