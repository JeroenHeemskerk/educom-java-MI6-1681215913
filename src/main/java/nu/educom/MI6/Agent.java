package nu.educom.MI6;
import java.util.Date;

public class Agent {
  private int id;
  private String serviceNumber;
  private String secretCode;
  private boolean active;


  private boolean licenseToKill;
  private Date licenseValidUntil;

  public Agent(int id, String serviceNumber, String secretCode, boolean active, boolean licenseToKill, Date licenseValidUntil) {
    this.id = id;
    this.serviceNumber = serviceNumber;
    this.secretCode = secretCode;
    this.active = active;
    this.licenseToKill = licenseToKill;
    this.licenseValidUntil = licenseValidUntil;
  }


  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  public String getServiceNumber() {
    return serviceNumber;
  }

  public void setServiceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
  }
  public String getSecretCode() {
    return secretCode;
  }
  public void setSecretCode(String secretCode) {
    this.secretCode = secretCode;
  }
  public boolean isActive() {
    return active;
  }
  public void setActive(boolean active) {
    this.active = active;
  }


  public boolean isLicenseToKill() {
    return licenseToKill;
  }

  public void setLicenseToKill(boolean licenseToKill) {
    this.licenseToKill = licenseToKill;
  }

  public Date getLicenseValidUntil() {
    return licenseValidUntil;
  }

  public void setLicenseValidUntil(Date licenseValidUntil) {
    this.licenseValidUntil = licenseValidUntil;
  }
}
