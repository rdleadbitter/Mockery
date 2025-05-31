package com.mockeryfx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.model.MockDraft;
import com.model.MockeryFacade;
import com.model.Pick;
import com.model.Player;
import com.model.Team;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DraftBoardController {
    @FXML private Label draftNameLabel, roundLabel, pickLabel;
    @FXML private Label selectedNameLabel, selectedPositionLabel, selectedSchoolLabel;
    @FXML private VBox playerListBox;
    @FXML private Label statusLabel;
    @FXML private Button confirmDraftButton;
    @FXML private TextField tradeAbbrField;
    @FXML private ImageView teamLogoView;
    @FXML private TextFlow teamNeedsFlow;
    @FXML private VBox pickHistoryBox;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> positionFilter;
    @FXML private Label teamCityLabel;
    @FXML private Label teamNameLabel;


    private MockDraft draft;
    private Pick currentPick;
    private Player selectedPlayer;

    @FXML
    public void initialize() {
        draft = MockeryFacade.getInstance().getCurrentDraft();
        draftNameLabel.setText(draft.getDraftName());

        // Setup position filter options
        positionFilter.getItems().add("All");
        positionFilter.getItems().addAll(MockeryFacade.getInstance().getAllPositions());
        positionFilter.getSelectionModel().selectFirst();

        // Add listeners to trigger filtering
        searchField.textProperty().addListener((obs, oldVal, newVal) -> loadPlayerList());
        positionFilter.setOnAction(e -> loadPlayerList());

        loadNextPick();
        loadPlayerList();
    }

    private void loadPlayerList() {
        playerListBox.getChildren().clear();

        String search = searchField.getText().toLowerCase();
        String selectedPosition = positionFilter.getValue();

        for (Player p : MockeryFacade.getInstance().getAllPlayers()) {
            if (draft.isPlayerTaken(p.getConsensusRank())) continue;

            boolean matchesSearch = p.getName().toLowerCase().contains(search)
                                || p.getSchool().toLowerCase().contains(search);

            boolean matchesPosition = selectedPosition.equals("All") || p.getPosition().equals(selectedPosition);

            if (matchesSearch && matchesPosition) {
                Button playerBtn = new Button(
                    "#" + p.getConsensusRank() + " - " + p.getName() +
                    " (" + p.getPosition() + ", " + p.getSchool() + ")"
                );
                playerBtn.setPrefWidth(450);
                playerBtn.setOnAction(e -> selectPlayer(p));
                playerListBox.getChildren().add(playerBtn);
            }
        }
    }

    private void selectPlayer(Player player) {
        this.selectedPlayer = player;
        selectedNameLabel.setText("Name: " + player.getName());
        selectedPositionLabel.setText("Position: " + player.getPosition());
        selectedSchoolLabel.setText("School: " + player.getSchool());
        confirmDraftButton.setDisable(false);
    }

    @FXML
    private void handleDraftPlayer() {
        if (selectedPlayer == null || currentPick == null) return;

        boolean success = MockeryFacade.getInstance().assignPlayerToPick(draft, currentPick.getNumber(), selectedPlayer.getConsensusRank());
        if (success) {
            draft.markPlayerTaken(selectedPlayer.getConsensusRank());
            selectedPlayer = null;
            clearMetadata();
            confirmDraftButton.setDisable(true);
            loadNextPick();
            loadPlayerList();
        } else {
            statusLabel.setText("Failed to draft player.");
        }
    }

    private void clearMetadata() {
        selectedNameLabel.setText("");
        selectedPositionLabel.setText("");
        selectedSchoolLabel.setText("");
    }

    private void loadNextPick() {
        currentPick = draft.getNextUnfilledPick();

        if (currentPick == null) {
            try {
                App.setRoot("draftSummary");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        String teamAbbr = currentPick.getTeam();
        Team team = MockeryFacade.getInstance().getTeamByAbbreviation(teamAbbr);
        String userTeam = MockeryFacade.getInstance().getCurrentDraft().getUserTeam();

        roundLabel.setText("Round: " + currentPick.getRound());
        pickLabel.setText("Pick: " + currentPick.getNumber());
        statusLabel.setText("");

        // Logo
        String teamName = team.getName().toLowerCase();
        String path = "/images/nfl_logos/" + teamName + ".png";
        try {
            teamCityLabel.setText(team.getCity().toUpperCase());
            teamNameLabel.setText(team.getName().toUpperCase());
            teamCityLabel.setStyle("-fx-text-fill: " + team.getSecondaryColor() + "; -fx-background-color: " + team.getPrimaryColor() + ";");
            teamNameLabel.setStyle("-fx-text-fill: " + team.getSecondaryColor() + "; -fx-background-color: " + team.getPrimaryColor() + ";");
            teamLogoView.setImage(new Image(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            teamLogoView.setImage(new Image(getClass().getResourceAsStream("/images/nfl_logos/nfl.png")));
        }
        teamLogoView.setFitWidth(120);
        teamLogoView.setFitHeight(120);

        // Background
        pickLabel.getParent().setStyle("-fx-text-fill: " + team.getSecondaryColor() + "; -fx-background-color: " + team.getPrimaryColor() + ";");

        // Top needs rendering
        teamNeedsFlow.getChildren().clear();
        List<String> needs = team.getNeeds();
        List<String> topNeeds = needs.size() > 7 ? needs.subList(0, 7) : needs;
        Set<String> filledNeeds = new HashSet<>();

        for (Pick p : draft.getPicks()) {
            if (p.getTeam().equals(teamAbbr) && p.getPlayer() != null) {
                String pos = p.getPlayer().getPosition();
                if (pos.contains("/")) {
                    for (String part : pos.split("/")) filledNeeds.add(part.trim());
                } else {
                    filledNeeds.add(pos);
                }
            }
        }

        for (int i = 0; i < topNeeds.size(); i++) {
            String need = topNeeds.get(i);
            Text t = new Text(need + (i < topNeeds.size() - 1 ? "   " : ""));
            t.getStyleClass().add(filledNeeds.contains(need) ? "needs-filled" : "needs-available");
            teamNeedsFlow.getChildren().add(t);
        }

        loadPickHistory();

        if ("ALL".equals(userTeam) || teamAbbr.equals(userTeam)) {
            loadPlayerList();
            clearMetadata();
            confirmDraftButton.setDisable(true);
        } else {
            new Thread(() -> {
                try {
                    Thread.sleep(500); // delay for realism
                } catch (InterruptedException ignored) {}

                Platform.runLater(() -> autoPick(currentPick)); // no double-next
            }).start();
        }
    }


    @FXML 
    private void handleTradePick() {
        String abbr = tradeAbbrField.getText().trim().toUpperCase();
        if (abbr.isEmpty()) {
            statusLabel.setText("Enter a team abbreviation.");
            return;
        }
        boolean success = MockeryFacade.getInstance().tradePick(draft, currentPick.getNumber(), abbr);
        if (success) {
            tradeAbbrField.clear();
            loadNextPick();
        } else {
            statusLabel.setText("Invalid team abbreviation or trade failed.");
        }
    }

    private Player determineAutoPick(Pick pick) {
        String teamAbbr = pick.getTeam();
        Team team = MockeryFacade.getInstance().getTeamByAbbreviation(teamAbbr);
        List<Player> allPlayers = MockeryFacade.getInstance().getAllPlayers();

        Set<Integer> takenIds = new HashSet<>();
        for (Pick p : draft.getPicks()) {
            if (p.getPlayer() != null) {
                takenIds.add(p.getPlayer().getConsensusRank());
            }
        }

        List<String> needs = team.getNeeds();
        int round = pick.getRound();

        // Determine how many needs to weigh based on round
        int maxConsider = round <= 2 ? 4 : (round <= 4 ? 7 : needs.size());
        List<String> priorityNeeds = needs.subList(0, Math.min(maxConsider, needs.size()));

        List<Player> filtered = new ArrayList<>();
        for (Player p : allPlayers) {
            if (takenIds.contains(p.getConsensusRank())) continue;

            String pos = p.getPosition();
            boolean matchesNeed = priorityNeeds.stream().anyMatch(need ->
                pos.equals(need) || (pos.contains("/") && Arrays.asList(pos.split("/")).contains(need))
            );

            if (matchesNeed) {
                // Add QB weighting bonus
                int bonus = pos.contains("QB") ? 6 : 0;
                for (int i = 0; i < (3 + bonus); i++) {
                    filtered.add(p);
                }
            } else {
                // Add non-matching players with low probability
                if (round > 4) {
                    filtered.add(p);
                }
            }
        }

        // If nothing matched, just pick the best available
        if (filtered.isEmpty()) {
            for (Player p : allPlayers) {
                if (!takenIds.contains(p.getConsensusRank())) {
                    filtered.add(p);
                }
            }
        }

        // Sort and pick a random top 3 to add variety
        filtered.sort(Comparator.comparingInt(Player::getConsensusRank));
        int limit = Math.min(3, filtered.size());
        return filtered.get(new Random().nextInt(limit));
    }

    private void autoPick(Pick pick) {
        Player selected = determineAutoPick(pick);
        if (selected == null) return;

        selectedPlayer = selected;
        selectedNameLabel.setText("Name: " + selected.getName());
        selectedPositionLabel.setText("Position: " + selected.getPosition());
        selectedSchoolLabel.setText("School: " + selected.getSchool());

        new Thread(() -> {
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}

            javafx.application.Platform.runLater(() -> {
                boolean success = MockeryFacade.getInstance().assignPlayerToPick(draft, pick.getNumber(), selected.getConsensusRank());
                if (success) {
                    draft.markPlayerTaken(selected.getConsensusRank());
                    selectedPlayer = null;
                    clearMetadata();
                    confirmDraftButton.setDisable(true);
                    loadPickHistory(); // Update after AI pick
                    loadNextPick();
                    loadPlayerList();
                }
            });
        }).start();
    }

    private void loadPickHistory() {
        pickHistoryBox.getChildren().clear();
        List<Pick> picks = draft.getPicks();

        for (Pick pick : picks) {
            Label entry = new Label();

            String team = pick.getTeam();
            String text = "Pick " + pick.getNumber() + " - " + team;

            if (pick.getPlayer() != null) {
                text += " → " + pick.getPlayer().getName() + " (" + pick.getPlayer().getPosition() + ")";
            }

            entry.setText(text);
            entry.setStyle("-fx-padding: 5px; -fx-background-color: " + (MockeryFacade.getInstance().getTeamByAbbreviation(pick.getTeam()).getPrimaryColor()) + ";");
            pickHistoryBox.getChildren().add(entry);
        }
    }

    private void setupFilters() {
        searchField.textProperty().addListener((obs, oldText, newText) -> loadPlayerList());
        positionFilter.getItems().clear();
        positionFilter.getItems().add("All");
        positionFilter.getItems().addAll(MockeryFacade.getInstance().getAllPositions().stream().sorted().collect(Collectors.toList()));        positionFilter.getSelectionModel().selectFirst();
        positionFilter.getSelectionModel().selectFirst();
        positionFilter.setOnAction(e -> loadPlayerList());
    }

}
