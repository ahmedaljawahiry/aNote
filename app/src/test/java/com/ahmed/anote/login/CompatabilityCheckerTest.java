package com.ahmed.anote.login;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;

import org.junit.BeforeClass;
import org.junit.Test;

import static android.content.Context.KEYGUARD_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CompatabilityCheckerTest {

    private static Activity activityMock = mock(LoginActivity.class);
    private static KeyguardManager keyguardManagerMock = mock(KeyguardManager.class);
    private static PackageManager packageManagerMock = mock(PackageManager.class);
    private static CompatibilityChecker compatibilityChecker;

    @BeforeClass
    public static void setUp() {
        doReturn(keyguardManagerMock).when(activityMock).getSystemService(eq(KEYGUARD_SERVICE));
        doReturn(packageManagerMock).when(activityMock).getPackageManager();

        compatibilityChecker = new CompatibilityChecker(activityMock);
    }

    @Test
    public void falseIfNotKeyGuardSecure() {
        doReturn(false).when(keyguardManagerMock).isKeyguardSecure();
        doReturn(true).when(packageManagerMock)
                .hasSystemFeature(eq(PackageManager.FEATURE_FINGERPRINT));
        assertThat(compatibilityChecker.isCompatible()).isFalse();
    }

    @Test
    public void falseIfNoSystemFeature() {
        doReturn(true).when(keyguardManagerMock).isKeyguardSecure();
        doReturn(false).when(packageManagerMock)
                .hasSystemFeature(eq(PackageManager.FEATURE_FINGERPRINT));
        assertThat(compatibilityChecker.isCompatible()).isFalse();
    }
}
