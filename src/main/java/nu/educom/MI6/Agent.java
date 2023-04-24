package nu.educom.MI6;

import java.time.LocalDate;

public record Agent(int id, boolean active, boolean licenseToKill, LocalDate licenseValidUntil) {

}
