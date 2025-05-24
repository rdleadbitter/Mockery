package com.mockeryfx;

import com.model.MockDraft;
import com.model.MockeryFacade;
import com.model.Team;

import javafx.fxml.FXML; 
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateDraftController {

    @FXML private TextField draftNameField;
    @FXML private ComboBox<Integer> roundSelector;
    @FXML private Label statusLabel;
    @FXML private ComboBox<String> teamSelector;

    @FXML
    public void initialize() {
        for (int i = 1; i <= 7; i++) {
            roundSelector.getItems().add(i);
        }
        roundSelector.setValue(1);

        teamSelector.getItems().add("ALL");
        teamSelector.getItems().add("NONE");
        // Populate team abbreviations
        for (Team team : MockeryFacade.getInstance().getAllTeams()) {
            teamSelector.getItems().add(team.getAbbreviation());
        }
        teamSelector.setValue("ALL"); // default
    }

    @FXML
    private void handleCreateDraft() {
        String name = draftNameField.getText().trim();
        Integer rounds = roundSelector.getValue();
        String teamAbbr = teamSelector.getValue();

        if (name.isEmpty()) {
            statusLabel.setText("Enter a draft name.");
            return;
        }

        if (teamAbbr == null || teamAbbr.isEmpty()) {
            statusLabel.setText("Select a team to draft for.");
            return;
        }

        MockDraft draft = MockeryFacade.getInstance().createMockDraft(name, 2025, rounds);
        if (draft == null) {
            statusLabel.setText("Failed to create draft.");
            return;
        }

        MockeryFacade.getInstance().setCurrentDraft(draft);
        MockeryFacade.getInstance().setUserDraftTeam(teamAbbr);

        try {
            App.setRoot("draftBoard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBack() {
        try {
            App.setRoot("dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
