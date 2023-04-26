package nu.educom.MI6;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MI6Model {

  private final DatabaseRepository repo;
  private List<LoginAttempt> failedAttempts = new ArrayList<>();
  MI6Model(DatabaseRepository repo) {
    this.repo = repo;
  }

  boolean validServiceNr(String input) {
    int serviceNumber;
    serviceNumber = stringToInt(input);
    return serviceNumber > 0 && serviceNumber <= 956;
  }

  private int stringToInt(String string) {

    try {
      return Integer.parseInt(string);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

 Agent validateServiceNr(String serviceNumber) {
    return repo.readAgentByServiceNumber(serviceNumber);
 }

  boolean isCorrectSecretCode(String serviceNumber, String secretCode) {
    return repo.readAgentByServiceNumAndSecretCode(serviceNumber, secretCode) != null;
  }


  public int timeOutLeft() {
    int attempts = this.failedAttempts.size();
    if (attempts > 0) {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime timeOfLastFailedAttempt = this.failedAttempts.get(attempts - 1).getLoginTime();
      long timeout = (long) (Math.pow(2, attempts - 1) * 60); // 60 is default timeout in seconds
      LocalDateTime endOfCoolDownPeriod = timeOfLastFailedAttempt.plusSeconds(timeout);
      double diff = Duration.between(now, endOfCoolDownPeriod).getSeconds();
      return (int) Math.max(diff, 0);
    }
    return 0;
  }
  void storeLoginAttempt(LoginAttempt newAttempt) {
   repo.createLoginAttempt(newAttempt);
  }
List<LoginAttempt> getLastFailedLoginAttempts(Agent agent) {
    failedAttempts = repo.readLastFailedLoginAttempts(agent.getId());
    return failedAttempts;
}

  public List<LoginAttempt> getFailedAttempts() {
    return failedAttempts;
  }
}

