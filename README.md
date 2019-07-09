## aNote


![Initial Password Setup](/previews/initial_password_setup.gif)
![Main Screen](/previews/main_screen.gif)
![Adding Pin](/previews/adding_pin.gif)
![Adding Note](/previews/adding_note.gif)


### Intro

Android app (my first!) for securely storing pins and notes. After initial setup - where a password is used to encrypt the database - 
all auth is carried out using the users' fingerprint. This app was built for me. My requirements were: 

- Be able to access a pin (e.g. bank pin) quickly and securely, without the need for a password. 
- For pins, I only need a short key (e.g. the first letter of the bank name) to be able to identify its purpose.
- Be able to store other sensitive notes (e.g. passport details).
- Lock some pins/notes, so that fingerprint auth is required again to unlock them. This means I don't need to close the app every time it's sent to the background.
- Keep all notes in an encrypted database.
- No ads.


### Requirements
- JDK 8
- Android SDK 28
- Phone with fingerprint scanner

### External Dependencies
- [SQLCipher](https://github.com/sqlcipher/sqlcipher)
- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org)
- [AssertJ](https://joel-costigliola.github.io/assertj/index.html)

### Further Info

#### Database

An [SQLCipher](https://github.com/sqlcipher/sqlcipher) database is used. Encryption is performed with a password 
on first-use of the app. The password itself is encrypted then stored in the app's shared preferences, and the 
encryption key is stored in the [Android KeyStore](https://developer.android.com/training/articles/keystore). 
This is a one-off process: the encryption password is never asked for again, and cannot be changed. 

Future access to the app requires a fingerprint. On successful auth, the password is decrypted and used to unlock
the database.

#### Tests

[JUnit5](https://junit.org/junit5/) tests are written for non-activity classes. Given the nature of Android code, writing tests without a large amount of mocks was difficult. If I take this any further, I'll probably use [Roboelectric](http://robolectric.org) to write tests for activity classes.

#### Known Issues
- The action bar currently has no functionality.
- Two separate grids are used for pins and "other notes". If one of the grids is empty, the other doesn't resize to full-screen.
- App has only been tested on a Pixel 2. I have no current plans to adapt the UI for different screen sizes.
- Would be ideal to have auto-resizable TextViews, so that text fits nicely on the main page.

### Play Store

This app is not available on the Play Store right now. I simply don't have enough time to maintain it at a standard I'd be happy with.
