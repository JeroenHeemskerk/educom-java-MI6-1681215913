package nu.educom.MI6;

public class Agent {
  private String name;

  private String  serviceNumber;
  private boolean canLogIn;
  public Agent(String name, String serviceNumber, boolean canLogin) {
    this.name = name;
    this.serviceNumber = serviceNumber;
    this.canLogIn = canLogin;
  }


  // Getters and setters
  public boolean isCanLogIn() {
    return canLogIn;
  }

  public void setCanLogIn(boolean canLogIn) {
    this.canLogIn = canLogIn;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getServiceNumber() {
    return serviceNumber;
  }

  public void setServiceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
  }

}
