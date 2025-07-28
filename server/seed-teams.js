const admin = require('./src/firebase/admin');

const sampleTeams = [
  {
    name: "Basketball Warriors",
    description: "A competitive basketball team looking for dedicated players",
    memberCount: 8
  },
  {
    name: "Soccer Legends",
    description: "Casual soccer team for weekend games",
    memberCount: 12
  },
  {
    name: "Tennis Pros",
    description: "Advanced tennis players only",
    memberCount: 6
  },
  {
    name: "Volleyball Stars",
    description: "Recreational volleyball team",
    memberCount: 10
  },
  {
    name: "Swimming Dolphins",
    description: "Swimming club for all levels",
    memberCount: 15
  },
  {
    name: "Running Club",
    description: "Morning running group",
    memberCount: 20
  },
  {
    name: "Gym Buddies",
    description: "Fitness and workout partners",
    memberCount: 25
  },
  {
    name: "Yoga Masters",
    description: "Yoga and meditation group",
    memberCount: 18
  },
  {
    name: "Cycling Team",
    description: "Road cycling enthusiasts",
    memberCount: 14
  },
  {
    name: "Hiking Adventures",
    description: "Weekend hiking and outdoor activities",
    memberCount: 22
  }
];

async function seedTeams() {
  try {
    console.log('Starting to seed teams...');
    
    for (const team of sampleTeams) {
      const teamRef = admin.firestore().collection('teams').doc();
      
      await teamRef.set({
        name: team.name,
        name_lower: team.name.toLowerCase(),
        description: team.description,
        memberCount: team.memberCount,
        creatorId: 'system', // System-created teams
        createdAt: Date.now(),
        updatedAt: Date.now()
      });
      
      console.log(`Created team: ${team.name}`);
    }
    
    console.log('Successfully seeded all teams!');
  } catch (error) {
    console.error('Error seeding teams:', error);
  } finally {
    process.exit(0);
  }
}

seedTeams(); 