package nu.educom.MI6;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Presenter implements IPresenter {
  private enum STATES {
    READY,
    WAIT_FOR_LOGIN,
    LOGIN_RECEIVED,
    WAIT_FOR_SECRET_CODE,
    SECRET_CODE_RECEIVED
  }

  private STATES currentState = STATES.READY;
  IView theView;
  MI6Model model;
  boolean running = true;

  private String serviceNumber;
  private String secretCode;

  Presenter(IView view, MI6Model model) {
    theView = view;
    theView.addPresenterListener(this);
    this.model = model;
  }

  public void run() {
    while (running) {
      theView.triggerAskLogIn();
      switch (currentState) {
        case READY -> {
          theView.triggerAskLogIn();
          currentState = STATES.WAIT_FOR_LOGIN;
        }
        case LOGIN_RECEIVED -> {
          if (!model.validServiceNr(serviceNumber)) {
            theView.showMessage("Enter a positive number in the range from 0 to 956");
            currentState = STATES.READY;
          } else {
            if (model.validateServiceNr(serviceNumber) == null) { // agent does not exist in database
              theView.showMessage("Wrong secret code!");
              currentState = STATES.READY;
            } else {
              currentState = STATES.WAIT_FOR_SECRET_CODE;
              theView.triggerAskSecretCode();
            }
          }
        }
        case SECRET_CODE_RECEIVED -> {
          Agent agent = model.validateServiceNr(serviceNumber);
          if(!agent.active()) {
            theView.showMessage("You are in cool down (forever)");
            currentState = STATES.READY;
            break;
          }
          int timeLeft;
          if (model.getLastFailedLoginAttempts(agent).size() > 0 && (timeLeft = model.timeOutLeft()) > 0) {
            theView.showMessage(String.format("You are in cool down. You can login after %d seconds", timeLeft));
          } else {
            if (model.validateAgent(serviceNumber, secretCode) == null) {
              theView.showMessage("Wrong secret code!");
              model.storeLoginAttempt(new LoginAttempt(agent.id(), LocalDateTime.now(), false));
            } else {
              StringBuilder failedAttempts = new StringBuilder("\nFailed login attempts: \n");
              for (LoginAttempt attempt : model.getFailedAttempts()) {
                failedAttempts.append(attempt.getLoginTime());
                failedAttempts.append("\n");
              }
              if (model.getFailedAttempts().size() == 0) {
                failedAttempts.append("0");
              }
              theView.showMessage(
                String.format("You are logged in, agent %s. ", serviceNumber) +
                  (agent.licenseToKill() && LocalDate.now().isBefore(agent.licenseValidUntil()) ?
                    "You have a license to kill and it expires on " + agent.licenseValidUntil() : "You have no license to kill or it has expired"
                    + String.format(failedAttempts.toString())
                  ));
            model.storeLoginAttempt(new LoginAttempt(agent.id(), LocalDateTime.now(), true));
            }
          }

          currentState = STATES.READY;
        }
      }

    }

    theView.close();
  }

  @Override
  public void handleLogin() {
    serviceNumber = theView.getServiceNumber();
    currentState = STATES.LOGIN_RECEIVED;


  }

  @Override
  public void handleSecretCode() {
    secretCode = theView.getSecretCode();
    currentState = STATES.SECRET_CODE_RECEIVED;

  }
}
