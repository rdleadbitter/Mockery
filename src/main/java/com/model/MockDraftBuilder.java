package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockDraftBuilder {

    public static MockDraft createMockDraft(String draftName, int year, User user) {
        List<Pick> basePicks = PickDatabase.getInstance().getPicks();
        System.out.println("MockDraftBuilder sees " + basePicks.size() + " base picks");

        List<Pick> mockPicks = new ArrayList<>();
        for (Pick basePick : basePicks) {
            mockPicks.add(new Pick(
                basePick.getTeam(),
                basePick.getNumber(),
                basePick.getRound(),
                basePick.isTraded(),
                null  // No player selected yet
            ));
        }

        MockDraft draft = new MockDraft(draftName, year, mockPicks, user.getUUID());
        draft.setId(UUID.randomUUID());
        return draft;
    }

    public static MockDraft createMockDraft(String draftName, int year, int maxRounds, User user) {
        List<Pick> allPicks = PickDatabase.getInstance().getPicks();
        List<Pick> filtered = new ArrayList<>();

        for (Pick p : allPicks) {
            if (p.getRound() <= maxRounds) {
                filtered.add(new Pick(
                    p.getTeam(),
                    p.getNumber(),
                    p.getRound(),
                    false,
                    null
                ));
            }
        }

        MockDraft draft = new MockDraft(draftName, year, filtered, user.getUUID());
        draft.setId(UUID.randomUUID());
        return draft;
    }

    public static boolean assignPlayerToPick(MockDraft draft, int pickNumber, int playerId) {
        Player player = PlayerDatabase.getInstance().getPlayerById(playerId);
        if (player == null) return false;

        for (Pick pick : draft.getPicks()) {
            if (pick.getNumber() == pickNumber) {
                pick.setPlayer(player);
                draft.markPlayerTaken(playerId);
                return true;
            }
        }
        return false;
    }

    public static boolean tradePick(MockDraft draft, int pickNumber, Team newTeam) {
        if (newTeam == null) return false;

        Pick pick = draft.getPickByNumber(pickNumber);
        if (pick == null) return false;

        pick.setTeam(newTeam.getAbbreviation());
        pick.setTraded(true);
        return true;
    }
}
