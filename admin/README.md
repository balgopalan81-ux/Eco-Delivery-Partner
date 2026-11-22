# ECO Delivery Admin

A mobile-friendly manual order entry website for ECO Grocery.

## What it does
- Admin email/password sign-in with Firebase Authentication
- Add customer orders manually
- Add delivery partners
- View orders as they are added and assign a saved partner
- Track order status fields and filter the list

## Firebase setup (free tier)
1. Open https://console.firebase.google.com/ and create a project named `eco-delivery-admin`.
2. In Project settings, add a Web app. Copy its Firebase config.
3. Open `admin/index.html` in this repository and replace the six `YOUR_...` values in `firebaseConfig`.
4. In Authentication > Sign-in method, enable Email/Password.
5. In Authentication > Users, add your own admin email and a strong password.
6. In Firestore Database, create a database (start in production mode).
7. Open Firestore Rules, paste the contents of `firestore.rules` from this repository, and Publish.
8. Do not create delivery-boy Firebase accounts yet. The first release is admin-only; rider-specific permissions and app sync are a separate next step.

## Publish the website with GitHub Pages
1. In the GitHub repository, open Settings > Pages.
2. Under Build and deployment, choose GitHub Actions.
3. The workflow `.github/workflows/deploy-admin.yml` publishes the `admin` folder.
4. Open Actions and wait for **Deploy ECO Delivery Admin** to finish.
5. GitHub Pages will show the website address in Settings > Pages.

## Important
- This panel is not connected to the SuperSquare system.
- Orders must be typed in manually.
- The current Android APK still contains local demo orders. It will not receive these website orders until the Firebase Android integration is completed and rebuilt.
- Firestore rules here are for the initial single-admin stage. Do not add delivery partner accounts until role-based rules are installed.
- Keep Firebase project credentials private; never share the admin password in chat.
