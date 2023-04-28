package nu.educom.MI6;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {

  private final Session session = HibernateUtil.openSession();

  public Agent readAgentByServiceNumber(String serviceNr) {
    return this.session.createQuery("from Agent WHERE service_number = :serviceNr", Agent.class)
      .setParameter("serviceNr", serviceNr).uniqueResultOptional().orElse(null);
  }
  public Agent readAgentByServiceNumAndSecretCode(String serviceNr, String secret) {
    return this.session.createQuery("from Agent WHERE service_number = :serviceNr AND secret_code = :secret", Agent.class)
      .setParameter("serviceNr", serviceNr)
      .setParameter("secret", secret)
      .uniqueResultOptional().orElse(null);
  }

  public List<LoginAttempt> readLastFailedLoginAttempts(Agent agent) {
    List<LoginAttempt> failedLoginAttempts = new ArrayList<>();

    // Find the latest successful login attempt
    LoginAttempt lastSuccessAttempt = session.createQuery("FROM LoginAttempt la WHERE agent_id = :agentId AND successful_attempt = true ORDER BY attempt_id DESC", LoginAttempt.class)
      .setParameter("agentId", agent.getId()).setMaxResults(1).uniqueResultOptional().orElse(null);

    if (lastSuccessAttempt != null) {
      failedLoginAttempts = session.createQuery("FROM LoginAttempt WHERE agent_id = :agentId AND attempt_id > :successId", LoginAttempt.class)
        .setParameter("agentId", agent.getId())
        .setParameter("successId", lastSuccessAttempt.getAttemptId())
        .getResultList();

    }
    return failedLoginAttempts;
  }

  public void createLoginAttempt(LoginAttempt attempt) {
    session.beginTransaction();
    session.save(attempt);
    session.getTransaction().commit();

  }

}
