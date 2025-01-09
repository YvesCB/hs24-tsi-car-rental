#!/bin/bash

# Define API endpoints
TYPES_ENDPOINT="http://localhost:8080/api/types"
CARS_ENDPOINT="http://localhost:8080/api/cars"

# Create car types
echo "Creating car types..."
curl -X POST -H "Content-Type: application/json" -d '{"name": "Rover", "descript": "Exploration vehicles for rough terrains."}' $TYPES_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Drone", "descript": "Flying machines for aerial exploration."}' $TYPES_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Lander", "descript": "Stationary vehicles for scientific experiments."}' $TYPES_ENDPOINT

# Create cars (Mars missions)
echo "Creating cars..."
# Rovers
curl -X POST -H "Content-Type: application/json" -d '{"name": "Sojourner", "brand": "NASA", "yearOfConstruction": 1997, "automatic": true, "pricePerDay": 1000, "active": true, "type": {"id": 1}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Spirit", "brand": "NASA", "yearOfConstruction": 2004, "automatic": true, "pricePerDay": 1200, "active": true, "type": {"id": 1}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Curiosity", "brand": "NASA", "yearOfConstruction": 2012, "automatic": true, "pricePerDay": 1500, "active": true, "type": {"id": 1}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Perseverance", "brand": "NASA", "yearOfConstruction": 2021, "automatic": true, "pricePerDay": 2000, "active": true, "type": {"id": 1}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "ExoMars Rover (Rosalind Franklin)", "brand": "ESA", "yearOfConstruction": 2023, "automatic": true, "pricePerDay": 1800, "active": true, "type": {"id": 1}}' $CARS_ENDPOINT

# Drones
curl -X POST -H "Content-Type: application/json" -d '{"name": "Ingenuity", "brand": "NASA", "yearOfConstruction": 2021, "automatic": true, "pricePerDay": 800, "active": true, "type": {"id": 2}}' $CARS_ENDPOINT

# Landers
curl -X POST -H "Content-Type: application/json" -d '{"name": "Viking 1", "brand": "NASA", "yearOfConstruction": 1976, "automatic": true, "pricePerDay": 1000, "active": true, "type": {"id": 3}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Viking 2", "brand": "NASA", "yearOfConstruction": 1976, "automatic": true, "pricePerDay": 1100, "active": true, "type": {"id": 3}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Phoenix", "brand": "NASA", "yearOfConstruction": 2008, "automatic": true, "pricePerDay": 1300, "active": true, "type": {"id": 3}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "InSight", "brand": "NASA", "yearOfConstruction": 2018, "automatic": true, "pricePerDay": 1400, "active": true, "type": {"id": 3}}' $CARS_ENDPOINT
curl -X POST -H "Content-Type: application/json" -d '{"name": "Schiaparelli EDM", "brand": "ESA", "yearOfConstruction": 2016, "automatic": true, "pricePerDay": 1250, "active": true, "type": {"id": 3}}' $CARS_ENDPOINT

echo "Data population complete!"
