// Firestore Service for Cricket Tracker
// Handles all database operations for match data

import { initializeApp } from 'https://www.gstatic.com/firebasejs/10.7.1/firebase-app.js';
import { getAuth } from 'https://www.gstatic.com/firebasejs/10.7.1/firebase-auth.js';
import { 
    getFirestore, 
    collection, 
    doc, 
    addDoc, 
    getDoc, 
    getDocs, 
    updateDoc, 
    deleteDoc, 
    query, 
    orderBy,
    serverTimestamp 
} from 'https://www.gstatic.com/firebasejs/10.7.1/firebase-firestore.js';

// Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyClv7N2rctOpWElweKmJCgODANUVvl5onw",
    authDomain: "crickettracker-3bcb4.firebaseapp.com",
    projectId: "crickettracker-3bcb4",
    storageBucket: "crickettracker-3bcb4.firebasestorage.app",
    messagingSenderId: "1082299029552",
    appId: "1:1082299029552:web:d38c59752b9788ee7eb5f5",
    measurementId: "G-489D3PJ092"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);

// === AUTHENTICATION HELPERS ===

/**
 * Get current user ID
 */
export function getCurrentUserId() {
    const user = auth.currentUser;
    return user ? user.uid : null;
}

/**
 * Get current user
 */
export function getCurrentUser() {
    return auth.currentUser;
}

/**
 * Check authentication and redirect if not logged in
 */
export function requireAuth() {
    return new Promise((resolve, reject) => {
        const unsubscribe = auth.onAuthStateChanged((user) => {
            unsubscribe();
            if (user) {
                resolve(user);
            } else {
                window.location.href = '/login';
                reject(new Error('Not authenticated'));
            }
        });
    });
}

/**
 * Sign out current user
 */
export async function signOut() {
    try {
        await auth.signOut();
        window.location.href = '/login';
    } catch (error) {
        console.error('Sign out error:', error);
        throw error;
    }
}

// === FIRESTORE HELPERS ===

/**
 * Calculate derived statistics for a match
 */
function calculateMatchStats(match) {
    const stats = { ...match };
    
    // Calculate batting average (runs per dismissal)
    stats.battingAverage = match.ballsFaced > 0 ? 
        (match.runsScored / (match.ballsFaced / 6)).toFixed(2) : 0;
    
    // Calculate strike rate
    stats.strikeRate = match.ballsFaced > 0 ? 
        ((match.runsScored / match.ballsFaced) * 100).toFixed(2) : 0;
    
    // Calculate bowling economy
    stats.bowlingEconomy = match.oversBowled > 0 ? 
        (match.runsConceded / match.oversBowled).toFixed(2) : 0;
    
    // Calculate bowling average
    stats.bowlingAverage = match.wicketsTaken > 0 ? 
        (match.runsConceded / match.wicketsTaken).toFixed(2) : 0;
    
    return stats;
}

// === MATCH CRUD OPERATIONS ===

/**
 * Add a new match for the current user
 */
export async function addMatch(matchData) {
    const userId = getCurrentUserId();
    if (!userId) {
        throw new Error('User not authenticated');
    }

    try {
        // Calculate stats
        const matchWithStats = calculateMatchStats(matchData);
        
        // Add timestamp
        matchWithStats.createdAt = serverTimestamp();
        matchWithStats.updatedAt = serverTimestamp();
        
        // Add to Firestore
        const matchesRef = collection(db, 'users', userId, 'matches');
        const docRef = await addDoc(matchesRef, matchWithStats);
        
        return {
            id: docRef.id,
            ...matchWithStats
        };
    } catch (error) {
        console.error('Error adding match:', error);
        throw error;
    }
}

/**
 * Get all matches for the current user
 */
export async function getAllMatches() {
    const userId = getCurrentUserId();
    if (!userId) {
        throw new Error('User not authenticated');
    }

    try {
        const matchesRef = collection(db, 'users', userId, 'matches');
        const q = query(matchesRef, orderBy('matchDate', 'desc'));
        const querySnapshot = await getDocs(q);
        
        const matches = [];
        querySnapshot.forEach((doc) => {
            matches.push({
                id: doc.id,
                ...doc.data()
            });
        });
        
        return matches;
    } catch (error) {
        console.error('Error getting matches:', error);
        throw error;
    }
}

/**
 * Get a specific match by ID
 */
export async function getMatchById(matchId) {
    const userId = getCurrentUserId();
    if (!userId) {
        throw new Error('User not authenticated');
    }

    try {
        const matchRef = doc(db, 'users', userId, 'matches', matchId);
        const matchDoc = await getDoc(matchRef);
        
        if (matchDoc.exists()) {
            return {
                id: matchDoc.id,
                ...matchDoc.data()
            };
        } else {
            throw new Error('Match not found');
        }
    } catch (error) {
        console.error('Error getting match:', error);
        throw error;
    }
}

/**
 * Update an existing match
 */
export async function updateMatch(matchId, matchData) {
    const userId = getCurrentUserId();
    if (!userId) {
        throw new Error('User not authenticated');
    }

    try {
        // Calculate stats
        const matchWithStats = calculateMatchStats(matchData);
        
        // Add updated timestamp
        matchWithStats.updatedAt = serverTimestamp();
        
        // Update in Firestore
        const matchRef = doc(db, 'users', userId, 'matches', matchId);
        await updateDoc(matchRef, matchWithStats);
        
        return {
            id: matchId,
            ...matchWithStats
        };
    } catch (error) {
        console.error('Error updating match:', error);
        throw error;
    }
}

/**
 * Delete a match
 */
export async function deleteMatch(matchId) {
    const userId = getCurrentUserId();
    if (!userId) {
        throw new Error('User not authenticated');
    }

    try {
        const matchRef = doc(db, 'users', userId, 'matches', matchId);
        await deleteDoc(matchRef);
        return true;
    } catch (error) {
        console.error('Error deleting match:', error);
        throw error;
    }
}

// === STATISTICS CALCULATIONS ===

/**
 * Calculate player statistics from all matches
 */
export async function calculatePlayerStatistics() {
    const matches = await getAllMatches();
    
    const stats = {
        totalMatches: 0,
        totalRuns: 0,
        totalBallsFaced: 0,
        totalWickets: 0,
        totalOversBowled: 0,
        totalRunsConceded: 0,
        totalCatches: 0,
        highestScore: 0,
        bestBowlingWickets: 0,
        mostCatches: 0,
        overallBattingAverage: 0,
        overallStrikeRate: 0,
        overallBowlingEconomy: 0,
        overallBowlingAverage: 0
    };

    if (matches.length === 0) {
        return stats;
    }

    // Aggregate data
    matches.forEach(match => {
        stats.totalRuns += match.runsScored || 0;
        stats.totalBallsFaced += match.ballsFaced || 0;
        stats.totalWickets += match.wicketsTaken || 0;
        stats.totalOversBowled += match.oversBowled || 0;
        stats.totalRunsConceded += match.runsConceded || 0;
        stats.totalCatches += match.catches || 0;

        if (match.runsScored > stats.highestScore) {
            stats.highestScore = match.runsScored;
        }
        if (match.wicketsTaken > stats.bestBowlingWickets) {
            stats.bestBowlingWickets = match.wicketsTaken;
        }
        if (match.catches > stats.mostCatches) {
            stats.mostCatches = match.catches;
        }
    });

    stats.totalMatches = matches.length;

    // Calculate overall statistics
    stats.overallBattingAverage = stats.totalBallsFaced > 0 ? 
        (stats.totalRuns / stats.totalMatches).toFixed(2) : 0;
    
    stats.overallStrikeRate = stats.totalBallsFaced > 0 ? 
        ((stats.totalRuns / stats.totalBallsFaced) * 100).toFixed(2) : 0;
    
    stats.overallBowlingEconomy = stats.totalOversBowled > 0 ? 
        (stats.totalRunsConceded / stats.totalOversBowled).toFixed(2) : 0;
    
    stats.overallBowlingAverage = stats.totalWickets > 0 ? 
        (stats.totalRunsConceded / stats.totalWickets).toFixed(2) : 0;

    return stats;
}

/**
 * Get recent matches (limited)
 */
export async function getRecentMatches(limit = 5) {
    const matches = await getAllMatches();
    return matches.slice(0, limit);
}

// Export auth for use in other modules
export { auth, db };
