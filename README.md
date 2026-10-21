# ZYRO PAY — Android Starter

This is a native Android **prototype** using Kotlin and Jetpack Compose. It includes a branded dashboard and placeholder screens for Send Money, Scan & Pay, Receive, History, Bank, and Profile.

## Important
- This project does **not** send real money, connect to banks, scan live QR codes, or create a real UPI ID.
- The balance and QR are demo placeholders. Never present them as real financial data.
- Real UPI payments require an authorized bank/UPI/payment partner, their approved SDK/API and onboarding, secure server-side verification, compliance review, and security testing. Do not collect or store UPI PINs yourself.

## Build
1. Open this folder as a project in a compatible Android Studio installation.
2. Let Gradle sync and install the required Android SDK (compile SDK 35).
3. Run the `app` configuration on an Android device or emulator.
4. To build an APK, use **Build > Build APK(s)** in Android Studio.

This starter has no payment-provider credentials or backend. Those must come from the authorized provider after onboarding.


## Build an APK using GitHub Actions from a phone
1. Create a new **public or private** GitHub repository.
2. Extract this ZIP on your phone, then upload the *contents* of the extracted folder to the repository (including the `.github/workflows/android-apk.yml` file).
3. Open the repository's **Actions** tab. If prompted, enable Actions.
4. Choose **Build ZYRO PAY APK**, tap **Run workflow**, then run it.
5. When the run finishes successfully, open that run and download the `zyro-pay-debug-apk` artifact. Extract the artifact ZIP to get `app-debug.apk`.
6. Open the APK on your Android phone and follow Android's install prompts. Only install APKs you built or trust.

The workflow builds a **debug APK**. This is still a demo/prototype and does not support real UPI payments.
