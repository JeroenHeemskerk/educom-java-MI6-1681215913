package nu.educom.MI6;

public class MI6Model {

  private final DatabaseRepository repo;

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
    return repo.readAgentByServiceNr(serviceNumber);
 }

  Agent validateAgent(String serviceNumber, String secretCode) {
    return repo.authenticateAgent(serviceNumber, secretCode);
  }

  boolean isInCoolDown() {
    return true;
  }

  void storeLoginAttempt(LoginAttempt newAttempt) {

   repo.insertLoginAttempt(newAttempt);
  }
}
