#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Paf Sports Betting Simulator - Local Development ===${NC}\n"

# Check prerequisites
check_prerequisites() {
    local missing=0
    
    if ! command -v java &> /dev/null; then
        echo -e "${RED}✗ Java is not installed${NC}"
        missing=1
    else
        echo -e "${GREEN}✓ Java found$(NC)"
    fi
    
    if ! command -v node &> /dev/null; then
        echo -e "${RED}✗ Node.js is not installed${NC}"
        missing=1
    else
        echo -e "${GREEN}✓ Node.js found${NC}"
    fi
    
    if ! command -v mongosh &> /dev/null && ! command -v mongo &> /dev/null; then
        echo -e "${RED}✗ MongoDB is not running or mongosh not installed${NC}"
        missing=1
    else
        echo -e "${GREEN}✓ MongoDB found${NC}"
    fi
    
    if [ $missing -eq 1 ]; then
        echo -e "\n${RED}Please install missing prerequisites${NC}"
        exit 1
    fi
}

echo -e "${YELLOW}Checking prerequisites...${NC}\n"
check_prerequisites

echo -e "\n${YELLOW}Starting services in separate terminals...${NC}\n"

# Get the directory of this script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Function to run in background and handle cleanup
cleanup() {
    echo -e "\n${YELLOW}Stopping all services...${NC}"
    pkill -P $$ || true
    exit 0
}

trap cleanup SIGINT SIGTERM

# Start backend in a new terminal
echo -e "${BLUE}Starting Backend (Spring Boot)...${NC}"
open -a Terminal "$SCRIPT_DIR/backend" --args "cd '$SCRIPT_DIR/backend' && ./mvnw spring-boot:run"

# Wait a bit for backend to start
sleep 3

# Start frontend in a new terminal
echo -e "${BLUE}Starting Frontend (React + Vite)...${NC}"
open -a Terminal "$SCRIPT_DIR/frontend" --args "cd '$SCRIPT_DIR/frontend' && npm install && npm run dev"

echo -e "\n${GREEN}Services are starting...${NC}"
echo -e "${BLUE}Backend: ${NC}http://localhost:8080"
echo -e "${BLUE}Frontend: ${NC}http://localhost:5173"
echo -e "\n${YELLOW}Press Ctrl+C to stop all services${NC}"

# Keep script running
wait
