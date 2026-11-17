package io.material4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.core.graphics.ColorUtils;

import org.junit.Test;

public class Material4AccessibilityTest {

    @Test
    public void blackOnWhitePassesAaa() {
        assertEquals(21.0, Material4Accessibility.contrastRatio(0xFF000000, 0xFFFFFFFF), 0.05);
        assertTrue(Material4Accessibility.meetsWcagAaa(0xFF000000, 0xFFFFFFFF, false));
    }

    @Test
    public void lowContrastGreyFailsAa() {
        int grey = ColorUtils.setAlphaComponent(0xFF777777, 255);
        assertEquals(false, Material4Accessibility.meetsWcagAa(grey, 0xFFFFFFFF, false));
    }

    @Test
    public void largeTextThresholdIsThree() {
        int fourAndAHalf = 0xFF767676;
        assertTrue(Material4Accessibility.meetsWcagAa(fourAndAHalf, 0xFFFFFFFF, true));
    }

    @Test
    public void sizeClassesMatchMaterialBreakpoints() {
        assertEquals(Material4Adaptive.SizeClass.COMPACT,
                Material4Adaptive.sizeClassForWidthDp(599));
        assertEquals(Material4Adaptive.SizeClass.MEDIUM,
                Material4Adaptive.sizeClassForWidthDp(600));
        assertEquals(Material4Adaptive.SizeClass.EXPANDED,
                Material4Adaptive.sizeClassForWidthDp(840));
    }
}
