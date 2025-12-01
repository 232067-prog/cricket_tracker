# Firebase Deployment Guide for Cricket Tracker

## Prerequisites

1. **Install Firebase CLI** (if not already installed):
   ```powershell
   # Run PowerShell as Administrator and enable script execution
   Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
   
   # Install Firebase CLI
   npm install -g firebase-tools
   ```

2. **Verify Installation**:
   ```powershell
   firebase --version
   ```

## Deployment Steps

### 1. Login to Firebase

```powershell
firebase login
```

This will open a browser window for you to authenticate with your Google account.

### 2. Verify Project Configuration

The project is already configured to use `crickettracker-3bcb4`. You can verify this with:

```powershell
firebase use
```

### 3. Deploy to Firebase Hosting

From the cricket_tracker directory, run:

```powershell
firebase deploy --only hosting
```

This will:
- Upload all files from the `public` directory
- Configure URL rewrites for clean URLs
- Set up caching headers
- Provide you with a hosting URL

### 4. Access Your Deployed Site

After deployment completes, you'll see output like:

```
✔  Deploy complete!

Project Console: https://console.firebase.google.com/project/crickettracker-3bcb4/overview
Hosting URL: https://crickettracker-3bcb4.web.app
```

Your site will be live at: **https://crickettracker-3bcb4.web.app**

## Testing the Deployed Site

1. Open the hosting URL in your browser
2. You should be redirected to the login page
3. Create a new account or login
4. Test all functionality:
   - Add matches
   - View match history
   - Edit matches
   - Delete matches
   - View statistics

## URL Structure

The deployed site supports clean URLs:
- `/` → Dashboard (index.html)
- `/login` → Login page
- `/register` → Registration page
- `/matches/add` → Add match
- `/matches/history` → Match history
- `/matches/edit?id={matchId}` → Edit match
- `/matches/statistics` → Statistics

## Firestore Security Rules

Before going live, update your Firestore security rules in the Firebase Console:

1. Go to: https://console.firebase.google.com/project/crickettracker-3bcb4/firestore/rules
2. Update rules to:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can only access their own data
    match /users/{userId}/matches/{matchId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

3. Click "Publish"

## Updating the Site

To deploy updates:

1. Make changes to files in `src/main/resources/templates` and `src/main/resources/static`
2. Copy updated files to `public`:
   ```powershell
   copy src\main\resources\templates\*.html public\
   copy src\main\resources\static\css\*.css public\css\
   copy src\main\resources\static\js\*.js public\js\
   copy public\dashboard.html public\index.html
   ```
3. Deploy:
   ```powershell
   firebase deploy --only hosting
   ```

## Troubleshooting

### PowerShell Execution Policy Error
If you get an execution policy error when running npm:
```powershell
# Run PowerShell as Administrator
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Authentication Not Working
- Verify Firebase Authentication is enabled in the console
- Check that Email/Password provider is enabled
- Ensure the domain is authorized in Firebase Console > Authentication > Settings > Authorized domains

### Data Not Saving
- Check Firestore security rules
- Verify database is created in Firebase Console
- Check browser console for errors

## Custom Domain (Optional)

To use a custom domain:

1. Go to Firebase Console > Hosting
2. Click "Add custom domain"
3. Follow the instructions to verify ownership
4. Update DNS records as instructed

## Monitoring

View deployment history and analytics:
- Console: https://console.firebase.google.com/project/crickettracker-3bcb4/hosting
