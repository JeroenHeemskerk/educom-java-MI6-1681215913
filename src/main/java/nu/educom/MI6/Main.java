package nu.educom.MI6;

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
    System.out.println("Enter your name or enter 'stop' to exit");
    String name = scanner.nextLine();
    while (blacklist.contains(name)) {
      System.out.println("Access denied");
      System.out.println("Enter another name");
      name = scanner.nextLine();
    }
    if (name.equalsIgnoreCase("stop")) {
      System.exit(0);
    }
    return name;
  }

  public static int checkServiceNumber() {
    Scanner serviceNumScan = new Scanner(System.in);
    int serviceNumber;
    do {
      System.out.println("Enter your (positive) service number between 1 and 956");
      while (!serviceNumScan.hasNextInt()) {
        System.out.println("Please enter a number");
        serviceNumScan.next();
      }
      serviceNumber = serviceNumScan.nextInt();
    } while (serviceNumber <= 0 || serviceNumber > 956);
    return serviceNumber;
  }

  public static void checkSecretCode(String name, int serviceNumber) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the secret code");
    String userCode = scanner.nextLine();
    if (userCode.equals("For ThE Royal QUEEN")) {
      agents.add(new Agent(name, String.format("%03d", serviceNumber), true));
      System.out.printf("You have been logged in with your service number %s, agent %s", String.format("%03d", serviceNumber), name);
      System.out.println();
    } else {
      System.out.println("Wrong!");
      blacklist.add(name);
    }
  }
}


