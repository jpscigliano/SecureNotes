
# Secure file notes

This app allows users to take text notes and browse them in a simple list view. The notes are stored as separate files and are secured by a password. The user is prompted to enter a password upon launching the app and only the correct password will be accepted.

In order to adhere to the guidelines of this challenge, which did not allow for the use of third-party libraries, I made an effort to utilize as many built-in libraries as possible throughout the development of the app.

In case it was not the intended approach, I would be more than happy to incorporate certain libraries from Jetpack Compose to enhance the functionality and user experience of the app.

## App Usage
When a user logs in with a password, all files that were encrypted with that specific password will be retrieved and made available for the user to access. If the user chooses to use a new password, the files that were encrypted with that new password will then be retrieved

## Testing
In this project, unit tests were specifically created for the domain's UseCase, ensuring that its functionalities are working as intended. Additionally, instrumentation tests were implemented to test the encryptor classes ensuring their reliability .

Unit tests are documented with some comments following the Given-When-Then style.
- The given part describes the state of the world before beginning the behavior specified in the scenario under test.
- The when part is the behavior that we are specifying.
- The then section describes the changes expected due to the specified behavior.
