package nu.educom.MI6;

public interface IView {
  void showMessage(String msg);
  void triggerAskLogIn();
  String getServiceNumber();
  void triggerAskSecretCode();
  String getSecretCode();
  void addPresenterListener(IPresenter p);
  void close();
}
