package io.material4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 35, qualifiers = "w411dp-h891dp-xhdpi")
public class Material4TokensTest {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        context.setTheme(R.style.Theme_Material4);
    }

    @Test
    public void defaultThemeResolvesAuroraAndDepthTokens() {
        Material4Tokens tokens = Material4Tokens.from(context);
        assertEquals(0xFF7C4DFF, tokens.auroraStart);
        assertEquals(0xFF00D9FF, tokens.auroraMid);
        assertEquals(0xFFFF4D9D, tokens.auroraEnd);
        assertTrue(tokens.radiusMedium > 0f);
        assertEquals(24f * context.getResources().getDisplayMetrics().density,
                tokens.radiusLarge, 0.5f);
        assertEquals(48f * context.getResources().getDisplayMetrics().density,
                tokens.touchTarget, 0.5f);
        assertTrue(tokens.superellipse);
        assertFalse(tokens.reducedMotion);
        assertFalse(tokens.highContrast);
        assertTrue(tokens.motionMedium > 0);
    }

    @Test
    public void highContrastOverlayChangesTokens() {
        context.getTheme().applyStyle(R.style.ThemeOverlay_Material4_HighContrast, true);
        Material4Tokens tokens = Material4Tokens.from(context);
        assertTrue(tokens.highContrast);
        assertFalse(tokens.superellipse);
        assertFalse(tokens.morphOnPress);
        assertEquals(context.getColor(R.color.m4_hc_primary), tokens.primary);
    }

    @Test
    public void reducedMotionOverlayZeroesDurations() {
        context.getTheme().applyStyle(R.style.ThemeOverlay_Material4_ReducedMotion, true);
        Material4Tokens tokens = Material4Tokens.from(context);
        assertTrue(tokens.reducedMotion);
        assertEquals(0, tokens.motionShort);
        assertEquals(0, tokens.motionMedium);
        assertEquals(0, tokens.motionLong);
    }

    @Test
    public void comparisonOverlayFlagsBaselineMode() {
        context.getTheme().applyStyle(R.style.ThemeOverlay_Material4_Material3Baseline, true);
        Material4Tokens tokens = Material4Tokens.from(context);
        assertTrue(tokens.comparisonMode);
        assertFalse(tokens.morphOnPress);
    }

    @Test
    public void themeConfigRoundTrips() {
        Material4ThemeConfig config = new Material4ThemeConfig();
        config.nightMode = Material4ThemeConfig.MODE_DARK;
        config.dynamicColour = false;
        config.seedColour = 0xFF00AA55;
        config.highContrast = true;
        config.reducedMotion = true;
        config.comparisonMode = true;
        config.save(context);

        Material4ThemeConfig loaded = Material4ThemeConfig.load(context);
        assertEquals(Material4ThemeConfig.MODE_DARK, loaded.nightMode);
        assertFalse(loaded.dynamicColour);
        assertNotNull(loaded.seedColour);
        assertEquals(0xFF00AA55, loaded.seedColour.intValue());
        assertTrue(loaded.highContrast);
        assertTrue(loaded.reducedMotion);
        assertTrue(loaded.comparisonMode);
    }
}
