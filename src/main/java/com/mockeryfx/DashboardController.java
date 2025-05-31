package com.mockeryfx;

import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private VBox draftList;
    @FXML private VBox bigBoard;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> positionFilter, teamFilter;
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
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(5));
                row.setMaxWidth(Double.MAX_VALUE);

                // Make the row stretch the full width of the VBox
                HBox.setHgrow(row, Priority.ALWAYS);

                Label label = new Label(draft.getDraftName() + " (" + draft.getYear() + ")");
                label.setStyle("-fx-font-size: 14px;");

                Button viewBtn = new Button("View");
                viewBtn.setOnAction(e -> openDraft(draft));

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                Image trashImage = new Image(getClass().getResource("/images/trash.png").toExternalForm());
                ImageView trashIcon = new ImageView(trashImage);
                trashIcon.setFitWidth(25);
                trashIcon.setFitHeight(25);
                trashIcon.setPreserveRatio(true);
                trashIcon.setSmooth(true);
                // Wrap icon in a StackPane to center it and limit overflow
                StackPane iconWrapper = new StackPane(trashIcon);
                iconWrapper.setPrefSize(30, 30);
                iconWrapper.setMaxSize(30, 30);
                iconWrapper.setMinSize(30, 30);
                // Now the button
                Button deleteBtn = new Button();
                deleteBtn.setGraphic(iconWrapper);
                // Hard limit the button size
                deleteBtn.setPrefSize(30, 30);
                deleteBtn.setMinSize(30, 30);
                deleteBtn.setMaxSize(30, 30);
                deleteBtn.setStyle("-fx-background-color: #ff4d4d; -fx-background-radius: 5; ");
                deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle("-fx-background-color: #cc0000;"));
                deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle("-fx-background-color: #ff4d4d;"));
                deleteBtn.setOnAction(e -> {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Confirm Delete");
                    confirm.setHeaderText("Are you sure you want to delete this mock draft?");
                    confirm.setContentText(draft.getDraftName() + " (" + draft.getYear() + ")");
                    Optional<ButtonType> result = confirm.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        deleteDraft(draft, user);
                    }
                });

                row.getChildren().addAll(viewBtn, label, spacer, deleteBtn);
                draftList.getChildren().add(row);
            } else {
                System.out.println("Draft not found for ID: " + draftId);
            }
        }
    }


    private void deleteDraft(MockDraft draft, User user) {
        MockeryFacade.getInstance().deleteDraft(draft);
        loadMockDrafts(user);
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
            .filter(p -> selectedPosition.equals("ALL") || p.getPosition().equals(selectedPosition))
            .collect(Collectors.toList());

        for (Player player : filtered) {
            HBox row = new HBox(10);
            Label label = new Label("#" + player.getConsensusRank() + " " + player.getName() + " (" + player.getPosition() + ")");
            row.getChildren().add(label);
            bigBoard.getChildren().add(row);
        }
    }

    private void setupFilters() {
        // Set up Big Board filters
        searchField.textProperty().addListener((obs, oldText, newText) -> loadBigBoard());

        positionFilter.getItems().clear();
        positionFilter.getItems().add("ALL");
        positionFilter.getItems().addAll(
            MockeryFacade.getInstance()
                .getAllPositions()
                .stream()
                .sorted()
                .collect(Collectors.toList())
        );
        positionFilter.getSelectionModel().selectFirst();
        positionFilter.setOnAction(e -> loadBigBoard());

        // Set up Draft Order filters
        teamFilter.getItems().clear();
        teamFilter.getItems().add("ALL");
        teamFilter.getItems().addAll(
            MockeryFacade.getInstance()
                .getAllTeamsAbbreviations()
                .stream()
                .sorted()
                .collect(Collectors.toList())
        );
        teamFilter.getSelectionModel().selectFirst();
        teamFilter.setOnAction(e -> loadDraftOrder());

        searchField.textProperty().addListener((obs, oldVal, newVal) -> loadDraftOrder());
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

        String selectedTeam = teamFilter.getValue();
        String searchTerm = searchField.getText().toLowerCase();

        List<Pick> picks = PickDatabase.getInstance().getPicks();
        TeamDatabase teamDB = TeamDatabase.getInstance();

        for (Pick pick : picks) {
            Team team = MockeryFacade.getInstance().getTeamByAbbreviation(pick.getTeam());
            if (team == null) continue;

            // Filter by team abbreviation
            if (!"ALL".equals(selectedTeam) && !team.getAbbreviation().equalsIgnoreCase(selectedTeam)) {
                continue;
            }

            // Filter by search (city or name)
            String fullName = (team.getCity() + " " + team.getName()).toLowerCase();
            if (!searchTerm.isEmpty() && !fullName.contains(searchTerm)) {
                continue;
            }

            HBox pickRow = new HBox(10);
            pickRow.setPadding(new Insets(5));
            pickRow.setStyle("-fx-background-color: " + team.getPrimaryColor() + "; -fx-background-radius: 5;");
            pickRow.setAlignment(Pos.CENTER_LEFT);

            // Pick #
            Label pickNumLabel = new Label("#" + pick.getRound() + "." + pick.getNumber() + " - " + team.getAbbreviation() + " - ");
            pickNumLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            // Needs
            List<String> topNeeds = team.getNeeds().stream().limit(7).collect(Collectors.toList());
            Label needsLabel = new Label(String.join(" ", topNeeds)); // no commas
            needsLabel.setStyle("-fx-text-fill: white; -fx-font-style: italic;");

            pickRow.getChildren().addAll(pickNumLabel, needsLabel);
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
