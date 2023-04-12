package nu.educom.MI6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    List<String> blacklist = new ArrayList<>();
    List<Agent> agents = new ArrayList<>();
    blacklist.add("FakeAgent");
    while(true) {
      for (Agent agent : agents
      ) {
        System.out.println(agent.getName() + agent.isCanLogIn());

      }
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter your name or enter 'stop' to exit");
      String name = scanner.nextLine();
      while (blacklist.contains(name)) {
        System.out.println("Access denied");
        System.out.println("Enter another name");
        name = scanner.nextLine();
      }
      if (name.equals("stop")) {
        System.exit(0);
      }
      Scanner serviceNumScan = new Scanner(System.in);
      int serviceNumber;
      do {
        System.out.println("Enter your (positive) service number");
      while (!serviceNumScan.hasNextInt()) {
        System.out.println("Please enter a number");
        serviceNumScan.next();
      }
        serviceNumber = serviceNumScan.nextInt();
      } while (serviceNumber <= 0);
        int length = (int) Math.log10(serviceNumber) + 1;
        while (length > 3) {
          System.out.println("Too long, enter a new one");
          serviceNumber = scanner.nextInt();
          length = (int) Math.log10(serviceNumber) + 1;
        }

      System.out.println("Enter the secret code");
      String userCode = scanner.nextLine();
      if (userCode.equals("test")) {
        agents.add(new Agent(name, serviceNumber, true));
        System.out.printf("You have been logged in with your service number %d, agent %s", serviceNumber, name);
        System.out.println();
      } else {
        System.out.println("Wrong!");
        blacklist.add(name);
      }

    }



    }
  }


