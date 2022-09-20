# Mandi Mobile App

Apple sellers can sell their products in nearby mandis (marketplaces) using this mobile application.
Their product's price is determined by factors such as the village price per
unit, weight, and loyalty index.

### Features

- This mobile app allows apple sellers to register.
- This mobile app allows apple sellers to login.
- Sellers can sell their products without login/registering.
- After successful register, a unique loyalty card is generated and its auto-fills in the sell product screen.
- Autofill the loyalty card info, based on the registered seller name in the sell product screen.
- Autofill the registered seller name if the loyalty card number is entered in the sell product screen. 
- The registered user will get more prices for their product based on loyalty index.
- It allows sellers to sell their products through a mobile app.

### Supported Android OS Version

Android 5.0(Lollipop) to the latest android os version

### Library reference resources:

| **S No.** | **Name**  | **Refrence Link**  |
| :-----: | :- | :- |
| 1 | **Dagger2** | https://github.com/google/dagger |
| 2 | **RxJava2** | https://github.com/ReactiveX/RxJava |
| 3 | **Androidx.navigation** | https://developer.android.com/guide/navigation/navigation-getting-started |
| 4 | **AppCompat** | https://developer.android.com/jetpack/androidx/releases/appcompat |
| 5 | **Material** | https://material.io/develop/android/docs/getting-started |
| 6 | **AndroidX Lifecycle** | https://developer.android.com/jetpack/androidx/releases/lifecycle |
| 7 | **Androidx Databinding** | https://developer.android.com/topic/libraries/data-binding |
| 8 | **Androidx Room** | https://developer.android.com/jetpack/androidx/releases/room |
| 9 | **Gson** | https://github.com/google/gson |

### Project structure:

| **S No.** | **Name**  | **Description**  |
| :-----: | :- | :- |
| 1 | **data** | It contains all the database,model,preference related class. |
| 2 | **di** | Dependency providing classes using Dagger2. |
| 3 | **ui** | View classes along with their corresponding ViewModel adapter. |
| 4 | **util** | Utility classes. |
| 5 | **MandiApp** | It is application class. |

# Start the application in a local environment

   ### Android studio setup
   1. Download the android studio from this link: https://developer.android.com/studio
   2. To download the required API level follow the below steps.
      - Open Tools → SDK Manager → Appearance & Behavior → System Settings → Android SDK 
        → SDK Platforms Tab select the API level and apply to download
      - SDK Tools Tab select the below items and apply to download.
        1. Android SDK build-tool 32 or the latest
        2. Android emulator
        3. Android SDK platform tools
        4. Google play services
    
   ### Project setup and run into emulator/device
   1. Check out the source code from Github.
   2. Click the open button in android studio and choose your project folder.
   3. To run the application, you must first create an emulator.
   4. Please follow the below steps to create emulators
      - Open Tools → AVD Manager → Create Virtual Device → Select Any Device → Press Next → Download Any OS → Press Finish.
   5. The emulator name now appears before the play icon in the header toolbar.
   6. When you select an emulator and click the play button, the emulator will open and the application will install and open successfully.
   7. When you want to run the application directly into your mobile phone, connect the mobile phone with your laptop, so the name of your phone will appear before the play button.
      Once you press the play button, the application will run directly onto your phone.

### Generate debug APK

1. Open up the project with android studio.
2. Select **Build>Build Bundle(s)/APK(s)>Build APK(s)** from the toolbar menu.
3. Android studio will take a few moments to generate an APK file.
4. Once the APK build is complete, you will receive a notification on the bottom right corner of android studio.
   The notification will lead you to the APK file location if you select Locate.
5. You can still locate the APK file in the following location with your project folder if you missed the notification:
   **app/build/outputs/apk/debug/app-debug.apk**
   
### Generate signed APK

1. Open up the project with android studio.
2. Select **Build>Generate Signed Bundle/APK** from the toolbar menu.
3. Create/import key store details to generate signed APKs.
4. As a final step, you must select the destination folder, select V2 (Full APK Signature), and click finish.
3. Android studio will take a few moments to generate an APK file.
5. Once the APK build is complete, you will receive a notification on the bottom right corner of android studio.
   The notification will lead you to the APK file location if you select Locate.
6. You can still locate the APK file in the following location with your project folder if you missed the notification:
   **app/release**

