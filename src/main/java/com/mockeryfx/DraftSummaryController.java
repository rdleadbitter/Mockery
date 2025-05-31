package com.mockeryfx;

import java.util.ArrayList;

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
        String userTeam = MockeryFacade.getInstance().getCurrentDraft().getUserTeam();
        System.out.println("User team: " + userTeam);
        if (userTeam.equals("ALL") || userTeam.equals("NONE")) {
            draft = MockeryFacade.getInstance().getCurrentDraft();
        } else {
            draft = loadUserPicksOnly(MockeryFacade.getInstance().getCurrentDraft());
        }
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
            ImageView tradeIndicator = new ImageView();
            try {
                logoView.setImage(new Image(getClass().getResourceAsStream(logoPath)));
                tradeIndicator.setImage(new Image(getClass().getResourceAsStream("/images/trade.png")));
            } catch (Exception e) {
                // logoView stays blank if image missing
            }
            logoView.setFitHeight(40);
            logoView.setFitWidth(40);
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
            if (!pick.getTradedFrom().equals("") || pick.isTraded()) {
                System.out.println(pick.getTradedFrom());
                tradeIndicator.setFitHeight(20);
                tradeIndicator.setFitWidth(20);
                tradeIndicator.setPreserveRatio(true);
                String tradedTeamAbbr = pick.getTradedFrom();
                Team tradedTeam = MockeryFacade.getInstance().getTeamByAbbreviation(tradedTeamAbbr);
                String tradedTeamName = tradedTeam.getName().toLowerCase();
                String tradedLogoPath = "/images/nfl_logos/" + tradedTeamName + ".png";
                ImageView tradedImageView = new ImageView();
                try {
                    tradedImageView.setImage(new Image(getClass().getResourceAsStream(tradedLogoPath)));
                } catch (Exception e) {
                    // logoView stays blank if image missing
                }
                tradedImageView.setFitHeight(30);
                tradedImageView.setFitWidth(30);
                tradedImageView.setPreserveRatio(true);
                row.getChildren().addAll(tradeIndicator, tradedImageView);
            }

            // Set background to team's primary color
            row.setStyle("-fx-background-color: " + team.getPrimaryColor() + "; -fx-padding: 10; -fx-background-radius: 8;");

            summaryList.getChildren().add(row);
        }
    }

    private MockDraft loadUserPicksOnly(MockDraft draft) {
        MockDraft userDraft = new MockDraft(draft.getDraftName(), draft.getYear(), draft.getPicks(), draft.getOwnerId(), draft.getUserTeam());
        userDraft.setId(draft.getId());
        userDraft.setPicks(new ArrayList<>());
        for (Pick pick : draft.getPicks()) {
            if (pick.getTeam().equals(draft.getUserTeam())) {
                userDraft.addPick(pick);
            }
        }

        return userDraft;
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
