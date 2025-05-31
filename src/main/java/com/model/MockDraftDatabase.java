package com.model;

import java.util.List;
import java.util.UUID;

public class MockDraftDatabase {
    private static MockDraftDatabase instance;
    private List<MockDraft> drafts;

    private MockDraftDatabase() {
        drafts = DataLoader.getMockDrafts(); // new method
    }

    public static MockDraftDatabase getInstance() {
        if (instance == null) instance = new MockDraftDatabase();
        return instance;
    }

    public List<MockDraft> getAllDrafts() {
        return drafts;
    }

    public MockDraft getDraftById(UUID id) {
        return drafts.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    public void addDraft(MockDraft draft) {
        drafts.add(draft);
        save();
    }

    public void save() {
        DataWriter.saveMockDrafts(drafts);
    }

    public void removeDraft(MockDraft draft) {
        drafts.remove(draft);
        save();
    }
}
