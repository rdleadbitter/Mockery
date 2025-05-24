package com.mockeryfx;

import com.model.MockDraft;
import com.model.MockeryFacade;
import com.model.Pick;
import com.model.Player;
import com.model.Team;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DraftSummaryController {

    @FXML private Label draftTitleLabel;
    @FXML private VBox summaryList;

    private MockDraft draft;

    @FXML
    public void initialize() {
        draft = MockeryFacade.getInstance().getCurrentDraft();
        if (draft == null) {
            draftTitleLabel.setText("No draft available");
            return;
        }
        draftTitleLabel.setText(draft.getDraftName() + " Summary");
        loadSummary();
    }

    private void loadSummary() {
        summaryList.getChildren().clear();

        for (Pick pick : draft.getPicks()) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPrefWidth(800);
            row.getStyleClass().add("team-row");

            String teamAbbr = pick.getTeam();
            Team team = MockeryFacade.getInstance().getTeamByAbbreviation(teamAbbr);
            String teamName = team.getName().toLowerCase();
            String logoPath = "/images/nfl_logos/" + teamName + ".png";

            ImageView logoView = new ImageView();
            try {
                logoView.setImage(new Image(getClass().getResourceAsStream(logoPath)));
                logoView.setFitHeight(30);
            } catch (Exception e) {
                // logoView stays blank if image missing
            }
            logoView.setFitHeight(30);
            logoView.setFitWidth(30);
            logoView.setPreserveRatio(true);

            String text = "Round " + pick.getRound() + ", Pick " + pick.getNumber() +
                          " - " + team.getName();

            if (pick.getPlayer() != null) {
                Player player = MockeryFacade.getInstance().getAllPlayers().stream()
                        .filter(p -> p.getConsensusRank() == pick.getPlayer().getConsensusRank())
                        .findFirst()
                        .orElse(null);
                text += ": " + player.getName() + " (" + player.getPosition() + ")";
            } else {
                text += ": [No Selection]";
            }

            Label pickLabel = new Label(text);
            pickLabel.setWrapText(true);

            row.getChildren().addAll(logoView, pickLabel);

            // Set background to team's primary color
            row.setStyle("-fx-background-color: " + team.getPrimaryColor() + "; -fx-padding: 10; -fx-background-radius: 8;");

            summaryList.getChildren().add(row);
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            App.setRoot("dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
