package nu.educom.MI6;

import java.time.LocalDateTime;

public class LoginAttempt {

  private int attemptId;
  private int agentId;
  private LocalDateTime loginTime;
  private boolean successfulAttempt;

  public LoginAttempt(int attemptId, int agentId, LocalDateTime loginTime, boolean successfulAttempt ) {
    this.attemptId = attemptId;
    this.agentId = agentId;
    this.loginTime = loginTime;
    this.successfulAttempt = successfulAttempt;
  }
  public LoginAttempt(int agentId, LocalDateTime loginTime, boolean successfulAttempt) {
    this.agentId = agentId;
    this.loginTime = loginTime;
    this.successfulAttempt = successfulAttempt;
  }



  public int getAttemptId() {
    return attemptId;
  }
  public void setAttemptId(int attemptId) {
    this.attemptId = attemptId;
  }
  public int getAgentId() {
    return agentId;
  }
  public void setAgentId(int agentId) {
    this.agentId = agentId;
  }
  public LocalDateTime getLoginTime() {
    return loginTime;
  }
  public void setLoginTime(LocalDateTime loginTime) {
    this.loginTime = loginTime;
  }
  public boolean isSuccessfulAttempt() {
    return successfulAttempt;
  }
  public void setSuccessfulAttempt(boolean successfulAttempt) {
    this.successfulAttempt = successfulAttempt;
  }


}
