package fr.univ_lyon1.info.m1.mes.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessional;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * On pourrait avoir une class SearchPatientInput, seuelement si on veut créer
 * un autre bouton de recherche plus tard on devra en recréer un autre. Dans ce
 * contexte on a donc choisi de généraliser le plus possible le processus même
 * si on aurait pu faire en sort que l'on puisse modifier la manière dont les
 * élements sont placés en configurant HBOX ou VBOX ...
 */
public class SearchInput {
  public SearchInput() {
    // Container de l'input recherche et bouton search
    final HBox searchContainer = new HBox();
    // | Input pour le numéro de sécu | Bouton Search
    final TextField searchInput = new TextField();
    final Button searchButton = new Button("Search");
    searchContainer.getChildren().addAll(searchInput, searchButton);

    final EventHandler<ActionEvent> ssHandler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        final String searchText = searchInput.getText().trim();
        if (searchText.equals("")) {
          return; // Do nothing
        }
        String searchContentToHandle = searchText;
        // TODO: Envoyer au contoleur ou à une interface searcgContent.
        // ....
        searchInput.setText("");
        searchInput.requestFocus();
      }
    };
    searchButton.setOnAction(ssHandler);
    searchInput.setOnAction(ssHandler);
  }
}
