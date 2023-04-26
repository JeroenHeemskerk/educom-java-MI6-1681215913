package nu.educom.MI6;

public class Main {
  public static void main(String[] args) {
    IView view = new JOptionPaneView();
    DatabaseRepository repo = new DatabaseRepository();
    MI6Model model = new MI6Model(repo);
    Presenter presenter = new Presenter(view, model);
    presenter.run();
    HibernateUtil.shutdown();
  }
}


