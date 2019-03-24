package com.ahmed.anote.auth;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.ahmed.anote.R;

import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthenticateButtonTest {

    @Test
    public void userAuthenticatedOnClick() {
        Activity activityMock = mock(Activity.class);
        BiometricAuth biometricAuthMock = mock(BiometricAuth.class);
        ImageView buttonMock = mock(ImageView.class);

        doReturn(buttonMock).when(activityMock).findViewById(eq(R.id.fingerprint_logo));

        new AuthenticateButton(activityMock, biometricAuthMock).onClick(mock(View.class));
        verify(biometricAuthMock, times(1)).authenticateUser();
    }
}
