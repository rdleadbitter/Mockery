package com.mockeryfx;

import java.util.UUID;

import com.model.MockDraft;
import com.model.MockDraftDatabase;
import com.model.MockeryFacade;
import com.model.Player;
import com.model.PlayerDatabase;
import com.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private VBox draftList;
    @FXML private VBox bigBoard;

    @FXML
    public void initialize() {
        User user = MockeryFacade.getInstance().getCurrentUser();
        if (user != null) {
            usernameLabel.setText(user.getUsername());
            loadMockDrafts(user);
            loadBigBoard();
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
                row.getChildren().addAll(label, viewBtn);
                row.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
                draftList.getChildren().add(row);
            } else {
                System.out.println("⚠️ Draft not found for ID: " + draftId);
            }
        }
    }

    private void loadBigBoard() {
    bigBoard.getChildren().clear();

    PlayerDatabase playerDB = PlayerDatabase.getInstance();
    for (Player player : playerDB.getPlayers()) {
        HBox row = new HBox(10);
        Label label = new Label(
            "#" + player.getConsensusRank() + " - " + player.getName() +
            " (" + player.getPosition() + ", " + player.getSchool() + ")"
        );        
        label.setPrefWidth(400);
        row.getChildren().add(label);
        bigBoard.getChildren().add(row);
    }
}

    private void openDraft(MockDraft draft) {
        MockeryFacade.getInstance().setCurrentDraft(draft);
        try {
            App.setRoot("draftSummary");
        } catch (Exception e) {
            e.printStackTrace();
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
