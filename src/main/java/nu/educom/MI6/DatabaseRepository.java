package nu.educom.MI6;

import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {
    Session session = HibernateUtil.openSession();


  public void closeSession() {
    session.close();
  }

  public Agent readAgentByServiceNumber(String serviceNr) {

    Agent agent = session.createQuery("from Agent WHERE service_number = :serviceNr", Agent.class)
      .setParameter("serviceNr", serviceNr).uniqueResultOptional().orElse(null);
    return agent;
  }
  public Agent readAgentByServiceNumAndSecretCode(String serviceNr, String secret) {

      Agent agent = session.createQuery("from Agent WHERE service_number = :serviceNr AND secret_code = :secret", Agent.class)
      .setParameter("serviceNr", serviceNr)
      .setParameter("secret", secret)
      .uniqueResultOptional().orElse(null);
      return agent;
  }

  public List<LoginAttempt> readLastFailedLoginAttempts(Agent agent) {

    List<LoginAttempt> failedLoginAttempts = new ArrayList<>();
    LocalDateTime since;
    // Find the latest successful login attempt
    LoginAttempt lastSuccessAttempt = session.createQuery("FROM LoginAttempt la WHERE agent_id = :agentId AND successful_attempt = true ORDER BY attempt_id DESC", LoginAttempt.class)
      .setParameter("agentId", agent.getId()).setMaxResults(1).uniqueResultOptional().orElse(null);

    if (lastSuccessAttempt != null) {
      since = lastSuccessAttempt.getLoginTime();
    } else {
      since = LocalDateTime.MIN;
    }


      failedLoginAttempts = session.createQuery("FROM LoginAttempt WHERE agent_id = :agentId AND login_time > :since", LoginAttempt.class)
        .setParameter("agentId", agent.getId())
        .setParameter("since", since)
        .getResultList();


    return failedLoginAttempts;
  }

  public void createLoginAttempt(LoginAttempt attempt) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(attempt);
    session.getTransaction().commit();
    session.close();
  }

}
