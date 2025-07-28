const admin = require('./src/firebase/admin');

async function clearGames() {
    try {
        console.log('Clearing all games from database...');
        
        const gamesSnapshot = await admin.firestore()
            .collection('games')
            .get();
        
        console.log(`Found ${gamesSnapshot.docs.length} games to delete`);
        
        const deletePromises = gamesSnapshot.docs.map(doc => doc.ref.delete());
        await Promise.all(deletePromises);
        
        console.log('All games cleared successfully!');
        process.exit(0);
    } catch (error) {
        console.error('Error clearing games:', error);
        process.exit(1);
    }
}

clearGames(); 