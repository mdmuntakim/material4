package io.material4.gallery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CatalogDataTest {

    @Test
    public void catalogueContainsEveryRequiredSection() {
        Set<String> titles = new HashSet<>();
        for (Sections section : Sections.values()) {
            titles.add(section.title);
        }
        String[] required = {
                "Buttons", "FAB", "Switches", "Checkboxes", "Radio buttons", "Chips", "Cards",
                "Text fields", "Sliders", "Progress indicators", "Snackbars", "Dialogs",
                "Bottom sheets", "Navigation bar / rail", "Tabs", "Toolbar", "Search", "Badges",
                "Dividers", "Segmented buttons", "Date / time pickers"
        };
        for (String title : required) {
            assertTrue("missing section " + title, titles.contains(title));
        }
        assertEquals(23, Sections.values().length);
    }

    @Test
    public void everyGroupHasAtLeastOneSectionAndValidFirstPosition() {
        for (Sections.Group group : Sections.Group.values()) {
            assertFalse(group.sections().isEmpty());
            assertTrue(group.firstPosition() >= 0);
            assertTrue(group.firstPosition() < Sections.values().length);
        }
    }

    @Test
    public void snippetsDocumentTheWidgetSwap() {
        for (Sections section : Sections.values()) {
            assertFalse(section.snippet.isEmpty());
            assertFalse(section.description.isEmpty());
            assertFalse(section.snippet.toLowerCase().contains("*patch"));
        }
    }
}
