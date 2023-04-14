package nu.educom.MI6;

import java.sql.Timestamp;

public class LoginAttempt {

  private int attemptId;
  private int agentId;
  private Timestamp loginTime;
  private boolean succesfulAttempt;

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
  public Timestamp getLoginTime() {
    return loginTime;
  }
  public void setLoginTime(Timestamp loginTime) {
    this.loginTime = loginTime;
  }
  public boolean isSuccesfulAttempt() {
    return succesfulAttempt;
  }
  public void setSuccesfulAttempt(boolean succesfulAttempt) {
    this.succesfulAttempt = succesfulAttempt;
  }


}
