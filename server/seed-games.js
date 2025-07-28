const admin = require('./src/firebase/admin');

const sampleGames = [
    {
        teamId: 'team1',
        teamName: 'Demo FC',
        opponent: 'Test United',
        date: '2025-07-25',
        time: '7:30 PM',
        place: 'Paramount Fine Foods Centre'
    },
    {
        teamId: 'team2',
        teamName: 'Red Wolves',
        opponent: 'Blue Sharks',
        date: '2025-07-29',
        time: '6:00 PM',
        place: 'Iceland Arena'
    },
    {
        teamId: 'team1',
        teamName: 'Lions',
        opponent: 'Bears',
        date: '2025-07-31',
        time: '8:45 PM',
        place: 'Clarkson Arena'
    },
    {
        teamId: 'team3',
        teamName: 'Thunder',
        opponent: 'Lightning',
        date: '2025-08-02',
        time: '5:30 PM',
        place: 'Sports Complex'
    },
    {
        teamId: 'team1',
        teamName: 'Eagles',
        opponent: 'Falcons',
        date: '2025-08-05',
        time: '7:00 PM',
        place: 'Community Center'
    }
];

async function seedGames() {
    try {
        console.log('Starting to seed games...');
        
        for (const game of sampleGames) {
            const gameRef = await admin.firestore()
                .collection('games')
                .add({
                    teamId: game.teamId,
                    teamName: game.teamName,
                    opponent: game.opponent,
                    date: game.date,
                    time: game.time,
                    place: game.place,
                    createdAt: Date.now(),
                    updatedAt: Date.now()
                });
            
            console.log(`Created game: ${game.teamName} vs ${game.opponent} (ID: ${gameRef.id})`);
        }
        
        console.log('Games seeded successfully!');
        process.exit(0);
    } catch (error) {
        console.error('Error seeding games:', error);
        process.exit(1);
    }
}

seedGames(); 