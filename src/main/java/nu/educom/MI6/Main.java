package nu.educom.MI6;

import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException {
    IView view = new JOptionPaneView();
    DatabaseRepository repo = new DatabaseRepository();
    MI6Model model = new MI6Model(repo);
    Presenter presenter = new Presenter(view, model);
    presenter.run();
  }
}


