package com.mockeryfx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.model.MockDraft;
import com.model.MockeryFacade;
import com.model.Pick;
import com.model.Team;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class TradeDialogController {

    @FXML private ComboBox<String> tradePartnerSelector;
    @FXML private FlowPane yourPicksPane;
    @FXML private FlowPane theirPicksPane;
    @FXML private Label statusLabel;

    private MockDraft draft;
    private String yourTeamAbbr;
    private List<Pick> yourPicks = new ArrayList<>();
    private List<Pick> theirPicks = new ArrayList<>();
    private Set<Pick> selectedYourPicks = new HashSet<>();
    private Set<Pick> selectedTheirPicks = new HashSet<>();

    public void setDraft(MockDraft draft, String yourTeamAbbr) {
        this.draft = draft;
        this.yourTeamAbbr = yourTeamAbbr;

        // Populate trade partner selector
        List<String> teams = MockeryFacade.getInstance().getAllTeams().stream()
            .map(Team::getAbbreviation)
            .filter(abbr -> !abbr.equals(yourTeamAbbr))
            .sorted()
            .collect(Collectors.toList());
        tradePartnerSelector.getItems().setAll(teams);

        // Load your picks into pick-button pane
        loadYourPicks();
    }

    @FXML
    private void initialize() {
        tradePartnerSelector.setOnAction(e -> loadTheirPicks());
    }

    private List<Pick> getPicksForTeam(String teamAbbr) {
        if (teamAbbr == null) {
            return new ArrayList<>();
        }

        // Show all picks the team has in the full draft, not limited by the mock draft's maxRounds
        return MockeryFacade.getInstance().getAllPicksForTeam(teamAbbr);
    }

    private void loadYourPicks() {
        yourPicks = getPicksForTeam(yourTeamAbbr);

        selectedYourPicks.clear();
        yourPicksPane.getChildren().clear();
        for (Pick p : yourPicks) {
            yourPicksPane.getChildren().add(createPickButton(p, true));
        }
    }

    private void loadTheirPicks() {
        String partnerAbbr = tradePartnerSelector.getValue();
        if (partnerAbbr == null) {
            statusLabel.setText("Please select a partner team.");
            theirPicksPane.getChildren().clear();
            return;
        }

        theirPicks = getPicksForTeam(partnerAbbr);

        selectedTheirPicks.clear();
        theirPicksPane.getChildren().clear();
        for (Pick p : theirPicks) {
            theirPicksPane.getChildren().add(createPickButton(p, false));
        }

        statusLabel.setText("Selected " + selectedYourPicks.size() + " your picks, "
            + selectedTheirPicks.size() + " their picks.");
    }

    private Button createPickButton(Pick pick, boolean yours) {
        Button button = new Button(pick.getRound() + "." + pick.getNumber());
        button.setMinSize(80, 50);
        button.setMaxSize(80, 50);
        button.getStyleClass().add("trade-pick-button");

        button.setOnAction(e -> {
            Set<Pick> selectionSet = yours ? selectedYourPicks : selectedTheirPicks;
            if (selectionSet.contains(pick)) {
                selectionSet.remove(pick);
                button.getStyleClass().remove("trade-pick-selected");
            } else {
                selectionSet.add(pick);
                button.getStyleClass().add("trade-pick-selected");
            }
            statusLabel.setText("Selected " + selectedYourPicks.size() + " your picks, "
                + selectedTheirPicks.size() + " their picks.");
        });

        return button;
    }

    public List<Pick> getSelectedYourPicks() {
        return new ArrayList<>(selectedYourPicks);
    }

    public List<Pick> getSelectedTheirPicks() {
        return new ArrayList<>(selectedTheirPicks);
    }

    public String getTradePartner() {
        return tradePartnerSelector.getValue();
    }

    public boolean validateTrade() {
        if (getTradePartner() == null || getTradePartner().isEmpty()) {
            statusLabel.setText("Select a trade partner first.");
            return false;
        }
        if (selectedYourPicks.isEmpty() || selectedTheirPicks.isEmpty()) {
            statusLabel.setText("Select at least one pick from each team.");
            return false;
        }
        return true;
    }
}