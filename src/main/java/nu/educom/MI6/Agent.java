package nu.educom.MI6;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agents")
public class Agent {
  private int id;
  private String serviceNumber;
  private String secretCode;
  private boolean active;


  private boolean licenseToKill;
  private LocalDate licenseValidUntil;

  public Agent(int id, String serviceNumber, String secretCode, boolean active, boolean licenseToKill, LocalDate licenseValidUntil) {
    this.id = id;
    this.serviceNumber = serviceNumber;
    this.secretCode = secretCode;
    this.active = active;
    this.licenseToKill = licenseToKill;
    this.licenseValidUntil = licenseValidUntil;
  }

  public Agent() {}



  // Getters and setters
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy = "increment")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  @Column(name = "service_number")
  public String getServiceNumber() {
    return serviceNumber;
  }

  public void setServiceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
  }
  @Column(name = "secret_code")
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


  @Column(name = "license_to_kill")
  public boolean isLicenseToKill() {
    return licenseToKill;
  }

  public void setLicenseToKill(boolean licenseToKill) {
    this.licenseToKill = licenseToKill;
  }


  @Column(name = "license_valid_until")
  public LocalDate getLicenseValidUntil() {
    return licenseValidUntil;
  }

  public void setLicenseValidUntil(LocalDate licenseValidUntil) {
    this.licenseValidUntil = licenseValidUntil;
  }
}
