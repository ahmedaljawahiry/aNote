## aNote

Android app (my first!) for securely storing pins and notes. After initial setup - where a password is used to encrypt the database - 
all auth is carried out using the users' fingerprint. This app was built with my own use-case in mind: 

I'd like to be able to: 
- Access a pin (e.g. bank pin) quickly, without the need for a password. 
- For pins, I only need a short key (e.g. the first letter of the bank name) to be able to identify its purpose. 
- Store other sensitive notes (e.g. passport details).
- Lock some pins/notes, so that fingerprint auth is required again to unlock them.
- Keep all notes in an encrypted database.


### Database

An [SQLCipher](https://github.com/sqlcipher/sqlcipher) database is used. Encryption is performed with a password 
on first-use of the app. The password itself is encrypted then stored in the app's shared preferences, and the 
encryption key is stored in the [Android KeyStore](https://developer.android.com/training/articles/keystore). 
This is a one-off process: the encryption password is never asked for again, and cannot be changed. 

Future access to the app requires a fingerprint. On successful auth, the password is decrypted and used to unlock
the database.


### Tests

JUnit tests are written for non-activity classes. Given the nature of Android code, writing tests without a large amount of mocks was difficult.   

The plan is to use [Roboelectric](http://robolectric.org) to write tests for activity classes. As mentioned, this is my first Android project - so I'll need time to learn how to use the library. Unfortunately, I cant guarantee I'll find the time :(


### Known Issues
- The action bar currently has no functionality.
- Two separate grids are used for pins and "other notes". If one of the grids is empty, the other doesn't resize to full-screen.
- App has only been tested on a Pixel 2. I have no current plans to adapt the UI for different screen sizes.
