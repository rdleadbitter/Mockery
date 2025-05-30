package com.mockeryfx;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.model.MockDraft;
import com.model.MockDraftDatabase;
import com.model.MockeryFacade;
import com.model.Pick;
import com.model.PickDatabase;
import com.model.Player;
import com.model.PlayerDatabase;
import com.model.Team;
import com.model.TeamDatabase;
import com.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private VBox draftList;
    @FXML private VBox bigBoard;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> positionFilter;
    @FXML private VBox draftOrder;

    @FXML
    public void initialize() {
        User user = MockeryFacade.getInstance().getCurrentUser();
        if (user != null) {
            usernameLabel.setText(user.getUsername());
            loadMockDrafts(user);
            setupFilters();
            loadBigBoard();
            loadDraftOrder();
        }
    }

    private void loadMockDrafts(User user) {
        draftList.getChildren().clear();
        MockDraftDatabase draftDB = MockDraftDatabase.getInstance();

        for (UUID draftId : user.getMockDrafts()) {
            MockDraft draft = draftDB.getDraftById(draftId);
            if (draft != null) {
                HBox row = new HBox(10);
                Label label = new Label(draft.getDraftName() + " (" + draft.getYear() + ")");
                Button viewBtn = new Button("View");
                viewBtn.setOnAction(e -> openDraft(draft));
                row.getChildren().addAll(viewBtn, label);
                row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                draftList.getChildren().add(row);
            } else {
                System.out.println("Draft not found for ID: " + draftId);
            }
        }
    }

    private void loadBigBoard() {
        bigBoard.getChildren().clear();
        String search = searchField.getText().toLowerCase();
        String selectedPosition = positionFilter.getValue();

        PlayerDatabase playerDB = PlayerDatabase.getInstance();
        List<Player> players = playerDB.getPlayers();

        List<Player> filtered = players.stream()
            .filter(p ->
                p.getName().toLowerCase().contains(search) ||
                (p.getSchool() != null && p.getSchool().toLowerCase().contains(search))
            )
            .filter(p -> selectedPosition.equals("All") || p.getPosition().equals(selectedPosition))
            .collect(Collectors.toList());

        for (Player player : filtered) {
            HBox row = new HBox(10);
            Label label = new Label("#" + player.getConsensusRank() + " " + player.getName() + " (" + player.getPosition() + ")");
            row.getChildren().add(label);
            bigBoard.getChildren().add(row);
        }
    }

    private void setupFilters() {
        searchField.textProperty().addListener((obs, oldText, newText) -> loadBigBoard());
        positionFilter.getItems().clear();
        positionFilter.getItems().add("All");
        positionFilter.getItems().addAll(MockeryFacade.getInstance().getAllPositions().stream().sorted().collect(Collectors.toList()));        positionFilter.getSelectionModel().selectFirst();
        positionFilter.getSelectionModel().selectFirst();
        positionFilter.setOnAction(e -> loadBigBoard());
    }

    private void openDraft(MockDraft draft) {
        MockeryFacade.getInstance().setCurrentDraft(draft);
        try {
            App.setRoot("draftSummary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDraftOrder() {
        draftOrder.getChildren().clear();
        
        List<Pick> picks = PickDatabase.getInstance().getPicks();
        TeamDatabase teamDB = TeamDatabase.getInstance();

        for (Pick pick : picks) {
            Team team = MockeryFacade.getInstance().getTeamByAbbreviation(pick.getTeam());
            if (team == null) continue;

            HBox pickRow = new HBox(10);
            pickRow.setPadding(new Insets(5));
            pickRow.setStyle("-fx-background-color: " + team.getPrimaryColor() + "; -fx-background-radius: 5;");
            pickRow.setAlignment(Pos.CENTER_LEFT);

            // Pick #
            Label pickNumLabel = new Label("#" + pick.getRound()+"."+pick.getNumber());
            pickNumLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            // Logo
            ImageView logoView = new ImageView(new Image("/images/nfl_logos/" + team.getName() + ".png"));
            logoView.setFitHeight(30);
            logoView.setFitWidth(30);
            logoView.setPreserveRatio(true);

            // Needs
            List<String> topNeeds = team.getNeeds().stream().limit(7).collect(Collectors.toList());
            Label needsLabel = new Label(String.join(" ", topNeeds)); // removed commas
            needsLabel.setStyle("-fx-text-fill: white; -fx-font-style: italic;");

            pickRow.getChildren().addAll(pickNumLabel, logoView, needsLabel);
            draftOrder.getChildren().add(pickRow);
        }
    }

    @FXML
    private void handleCreateDraft() {
        try {
            App.setRoot("createDraft");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        MockeryFacade.getInstance().logout();
        try {
            App.setRoot("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
