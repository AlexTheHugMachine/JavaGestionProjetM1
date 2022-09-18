import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Tableau extends Application {

  private TableView table;
  private ObservableList<ObservableList> data;

  public static void main(String[] args) {
    launch(args);
  }

  public void buildData() {
    data = FXCollections.observableArrayList();
    for (Prescription prescription : data) {

    }
  }

  @Override
  public void start(Stage stage) {
  }
}
