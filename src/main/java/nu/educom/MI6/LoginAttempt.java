package nu.educom.MI6;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table( name = "login_attempts" )
public class LoginAttempt {
  private int attemptId = 0;
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

  public LoginAttempt() {

  }
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy = "increment")
  @Column(name = "attempt_id")
  public int getAttemptId() {return attemptId;}
  public void setAttemptId(int attemptId) {this.attemptId = attemptId; }
  @Column(name = "agent_id")
  public int getAgentId() {
    return agentId;
  }
  public void setAgentId(int agentId) {this.agentId = agentId;}

  @Column(name = "login_time")
  public LocalDateTime getLoginTime() {
    return loginTime;
  }
  public void setLoginTime(LocalDateTime loginTime) {this.loginTime = loginTime;}

  @Column(name = "successful_attempt")
  public boolean isSuccessfulAttempt() {
    return successfulAttempt;
  }
  public void setSuccessfulAttempt(boolean successfulAttempt) {this.successfulAttempt = successfulAttempt;}

}
