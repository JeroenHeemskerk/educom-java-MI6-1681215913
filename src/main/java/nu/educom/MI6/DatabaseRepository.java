package nu.educom.MI6;

import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {

  private Session session = HibernateUtil.openSession();

  public Agent readAgentByServiceNumber(String serviceNr) {
    Agent agent = this.session.createQuery("from Agent WHERE service_number = :serviceNr", Agent.class)
      .setParameter("serviceNr", serviceNr).uniqueResultOptional().orElse(null);
    return agent;
  }
  public Agent readAgentByServiceNumAndSecretCode(String serviceNr, String secret) {
    Agent agent = this.session.createQuery("from Agent WHERE service_number = :serviceNr AND secret_code = :secret", Agent.class)
      .setParameter("serviceNr", serviceNr)
      .setParameter("secret", secret)
      .uniqueResultOptional().orElse(null);
    return agent;
  }

  public List<LoginAttempt> readLastFailedLoginAttempts(int agentId) {
    List<LoginAttempt> failedLoginAttempts = new ArrayList<>();

    // Find the latest successful login attempt
    String subquery = "SELECT MAX(la.loginTime) FROM LoginAttempt la WHERE agent_id = :agentId AND successful_attempt = true";
    // Combine with main query of finding the id of said login-attempt
    String query = String.format("SELECT attemptId FROM LoginAttempt WHERE agent_id = :agentId AND login_time = (%s)", subquery);
    Integer lastSuccessId = (Integer) session.createQuery(query).setParameter("agentId", agentId).uniqueResultOptional().orElse(null);
    if (lastSuccessId == null) {
      lastSuccessId = 0;
    }

    String loginAttempts = "FROM LoginAttempt WHERE agent_id = :agentId";
    if (lastSuccessId > 0) // if there has been a successful login before
    {
      loginAttempts = String.format("FROM LoginAttempt WHERE agent_id = :agentId AND attempt_id > %s", lastSuccessId);
    }
    Query attempts = session.createQuery(loginAttempts).setParameter("agentId", agentId);
    failedLoginAttempts = attempts.getResultList();

    return failedLoginAttempts;
  }

  public void createLoginAttempt(LoginAttempt attempt) {
    session.beginTransaction();
    session.save(attempt);
    session.getTransaction().commit();

  }

}
