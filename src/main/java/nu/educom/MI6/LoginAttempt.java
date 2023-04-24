package nu.educom.MI6;

import java.time.LocalDateTime;

public class LoginAttempt {

  private final int agentId;
  private final LocalDateTime loginTime;
  private final boolean successfulAttempt;

  public LoginAttempt(int attemptId, int agentId, LocalDateTime loginTime, boolean successfulAttempt ) {
    this.agentId = agentId;
    this.loginTime = loginTime;
    this.successfulAttempt = successfulAttempt;
  }
  public LoginAttempt(int agentId, LocalDateTime loginTime, boolean successfulAttempt) {
    this.agentId = agentId;
    this.loginTime = loginTime;
    this.successfulAttempt = successfulAttempt;
  }


  public int getAgentId() {
    return agentId;
  }

  public LocalDateTime getLoginTime() {
    return loginTime;
  }

  public boolean isSuccessfulAttempt() {
    return successfulAttempt;
  }


}
