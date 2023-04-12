package nu.educom.MI6;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  static List<String> blacklist = new ArrayList<>();
  static List<Agent> agents = new ArrayList<>();

  public static void main(String[] args) {
    while (true) {
      String name = checkName();
      int serviceNumber = checkServiceNumber();
      checkSecretCode(name, serviceNumber);
    }
  }

  public static String checkName() {
    Scanner scanner = new Scanner(System.in);
    JFrame frame = new JFrame("InputDialog");

    // prompt the user to enter their name

    String name = JOptionPane.showInputDialog(frame, "Enter your name or enter 'stop' to exit");
    while (name == null) {
      name = JOptionPane.showInputDialog(frame, "Please enter a name");
    };
    while (blacklist.contains(name) || name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(null, "ACCESS DENIED!",
        "Error", JOptionPane.ERROR_MESSAGE);
      System.out.println("Enter another name");
      name = JOptionPane.showInputDialog(frame, "Enter another name");
    }
    if (name.equalsIgnoreCase("stop")) {
      System.exit(0);
    }
    return name;
  }

  public static int checkServiceNumber() {
    String input = "";
    int serviceNumber = -1;
      while (serviceNumber == -1) {
        input = JOptionPane.showInputDialog("Enter your (positive) service number between 1 and 956");
        serviceNumber = stringToInt(input);
        if (serviceNumber <= 0 || serviceNumber > 956) {
          serviceNumber = -1;
        }
      }
    return serviceNumber;
  }

  public static void checkSecretCode(String name, int serviceNumber) {
    String userCode = JOptionPane.showInputDialog("Enter the secret code");
    if (userCode.equals("test")) {
      agents.add(new Agent(name, String.format("%03d", serviceNumber), true));
      JOptionPane.showMessageDialog(null, String.format("You have been logged in with your service number" + String.format("%03d, agent %s", serviceNumber, name)));
    } else {
      JOptionPane.showMessageDialog(null, "Wrong!", "Error", JOptionPane.ERROR_MESSAGE);
        blacklist.add(name);
    }
  }

  private static int stringToInt(String string) {

    try {
      return Integer.parseInt(string);
    }
    catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Not a number! Please enter a number.");
      return -1;
    }
  }
}


