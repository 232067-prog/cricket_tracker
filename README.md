# 🏏 Cricket Tracker

A comprehensive web application for local cricket players to track their match performance, view statistics, and monitor progress over time.

## ✨ Features

- **📊 Dashboard**: View overall statistics and recent matches at a glance
- **➕ Add Match**: Record match performance with batting, bowling, and fielding stats
- **📜 Match History**: View, edit, and delete all recorded matches
- **📈 Statistics**: Detailed performance analysis with batting, bowling, and fielding metrics
- **🎨 Modern UI**: Beautiful glassmorphism design with smooth animations
- **📱 Responsive**: Fully responsive design works on desktop, tablet, and mobile
- **✅ Validation**: Client-side and server-side form validation
- **🔒 Duplicate Prevention**: Prevents duplicate match entries

## 🚀 Technologies Used

- **Backend**: Spring Boot 3.2.0, Java 17
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Storage**: In-memory (ArrayList-based repository)
- **Build Tool**: Maven
- **Additional**: Lombok, Spring Validation

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## 🛠️ Installation & Setup

1. **Clone or navigate to the project directory**
   ```bash
   cd c:\Users\232067\Documents\cricket_tracker
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Open your browser and navigate to: `http://localhost:8080`

## 📖 Usage Guide

### Adding a Match
1. Click "Add Match" in the navigation menu
2. Fill in the match details:
   - Match date and opponent name (required)
   - Batting stats: runs scored, balls faced
   - Bowling stats: wickets, overs bowled, runs conceded
   - Fielding stats: catches
3. Click "Save Match"

### Viewing Statistics
- **Dashboard**: Shows overall statistics and recent 5 matches
- **Statistics Page**: Detailed breakdown of batting, bowling, and fielding performance
- **Match History**: Complete list of all matches with calculated statistics

### Editing/Deleting Matches
1. Go to "Match History"
2. Click "Edit" to modify a match or "Delete" to remove it
3. Confirm deletion when prompted

## 📊 Calculated Statistics

### Batting
- **Batting Average**: Total runs / Total matches
- **Strike Rate**: (Total runs / Total balls faced) × 100
- **Highest Score**: Best individual score

### Bowling
- **Bowling Average**: Total runs conceded / Total wickets
- **Economy Rate**: Total runs conceded / Total overs bowled
- **Best Bowling**: Most wickets in a match

### Fielding
- **Total Catches**: Sum of all catches
- **Catches per Match**: Average catches per match

## 🎨 Design Features

- **Glassmorphism Effects**: Modern frosted glass aesthetic
- **Gradient Backgrounds**: Vibrant color schemes
- **Smooth Animations**: Fade-in, slide, and hover effects
- **Mobile-First**: Responsive design for all screen sizes
- **Dark Theme**: Eye-friendly dark color palette

## ⚠️ Important Notes

- **Data Persistence**: This application uses in-memory storage. All data will be lost when the application restarts.
- **Single User**: Designed for individual player tracking (no authentication)
- **Local Use**: Intended for local development and personal use

## 🔮 Future Enhancements

- Database integration (MySQL/PostgreSQL)
- User authentication and multi-user support
- Data export (PDF/Excel)
- Charts and graphs for trend analysis
- Mobile app version
- Team management features

## 📝 Project Structure

```
cricket_tracker/
├── src/
│   ├── main/
│   │   ├── java/com/cricket/tracker/
│   │   │   ├── controller/          # MVC Controllers
│   │   │   ├── model/               # Domain models
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── service/             # Business logic
│   │   │   └── CricketTrackerApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/             # Stylesheets
│   │       │   └── js/              # JavaScript files
│   │       ├── templates/           # Thymeleaf templates
│   │       └── application.properties
│   └── test/                        # Test files
└── pom.xml                          # Maven configuration
```

## 🤝 Contributing

This is a personal project for local cricket players. Feel free to fork and customize for your needs!

## 📄 License

This project is open source and available for personal use.

## 👨‍💻 Developer

Built with ❤️ for local cricket players

---

**Happy Cricket Tracking! 🏏**
