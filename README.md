# Paf Sports Betting Simulator

A full-stack sports betting simulator built with Spring Boot (backend) and React (frontend).

## ⚡ Quickstart (2 minutes)

**Prerequisites:** Java 17+, Node.js 18+, MongoDB running (see MongoDB startup below)

### Option 1: Run with Simple Script
```bash
./run-docker.sh
```
✅ Everything runs in Docker (MongoDB, Backend, Frontend)
Open http://localhost:5173

### Option 2: Run Locally with Script
```bash
./run-local-dev.sh
```
✅ Backend runs on http://localhost:8080
✅ Frontend runs on http://localhost:5173

### Option 3: Manual Setup
1. **Terminal 1 - Backend:**
```bash
cd backend && ./mvnw spring-boot:run
```
2. **Terminal 2 - Frontend:**
```bash
cd frontend && npm install && npm run dev
```

**Done!** Open http://localhost:5173 in your browser and start betting.

---

## Docker Setup (Recommended)

### Prerequisites
- Docker and Docker Compose installed

### Run with Docker Compose

```bash
docker-compose up --build
```

This will:
- Start MongoDB on port 27017
- Build and start the backend on http://localhost:8080
- Build and start the frontend on http://localhost:5173

**Done!** Open http://localhost:5173 in your browser.

To stop the services:
```bash
docker-compose down
```

To remove volumes (reset database):
```bash
docker-compose down -v
```

---

## Full Setup Guide

### Prerequisites
- Java 17+
- Node.js 18+
- MongoDB (running locally on default port 27017)

### MongoDB Startup (local)
- macOS (Homebrew): `brew services start mongodb-community@7.0`
- Docker fallback: `docker run -d -p 27017:27017 --name paf-mongo mongo:7.0`
- Verify connection: `mongosh --eval "db.runCommand({ ping: 1 })"`

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Run the Spring Boot application:
```bash
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080` and automatically:
- Load 20 pre-seeded football matches from `backend/src/main/resources/football_matches.json`
- Reset the MongoDB database on startup
- Enable caching and scheduling for match status updates

### Frontend Setup

1. In a new terminal, navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`

## Features

- **Create Players**: Set up players with initial balance
- **View Matches**: See upcoming, in-progress, and finished football matches with live odds
- **Place Bets**: 
  - Single bets on individual matches
  - Combination bets across multiple matches
- **Track History**: View bet history with status (PLACED, WON, LOST) and transaction records
- **Real-time Updates**: Matches automatically progress through UPCOMING → IN_PROGRESS → FINISHED states (every second)

## API Documentation

Full OpenAPI/Swagger specification available in `frontend/openapi.yaml`

Key endpoints:
- `GET /matches` - List open matches (cached)
- `GET /matches/finished` - List finished matches
- `POST /players` - Create player
- `GET /players/{playerName}` - Fetch player details
- `POST /bets/single` - Place single bet
- `POST /bets/combination` - Place combination bet
- `GET /players/{playerName}/bets` - Get player bets
- `GET /players/{playerName}/transactions` - Get player transactions

## Project Structure

```
.
├── backend/          # Spring Boot REST API
│   ├── src/
│   │   ├── main/java/com/gritlab/paf_hackathon/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── scheduler/
│   │   └── resources/
│   │       └── football_matches.json
│   └── pom.xml
└── frontend/         # React + Vite
    ├── src/
    │   ├── App.jsx
    │   ├── api.js
    │   └── styles.css
    └── package.json
```

## Technology Stack

**Backend:**
- Spring Boot 4.0
- MongoDB
- Spring Cache (in-memory caching)
- Spring Scheduling (background tasks)

**Frontend:**
- React 18
- Vite
- Axios

## Notes

- Matches automatically transition through their lifecycle every second
- Match results are randomly determined when matches finish
- Bets are settled based on match outcomes
- All data is stored in MongoDB and reset on each application startup

