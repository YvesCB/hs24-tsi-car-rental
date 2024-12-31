#!/bin/bash

# Base API URLs
TYPE_API="http://localhost:8080/api/types"
VEHICLE_API="http://localhost:8080/api/cars" # Adjust this if the "Car" entity has been renamed

# Create types
echo "Creating types..."
TYPES=("Rover" "Lander" "Orbiter" "Drone" "Transfer Vehicle")
for TYPE in "${TYPES[@]}"; do
    curl -s -X POST -H "Content-Type: application/json" -d "{\"name\": \"$TYPE\", \"description\": \"$TYPE type vehicle\"}" "$TYPE_API" > /dev/null
done

echo "Types created."

# Type IDs (order matters)
ROVER_ID=1
LANDER_ID=2
ORBITER_ID=3
DRONE_ID=4
TRANSFER_VEHICLE_ID=5

# List of Mars exploration vehicles with actual years
VEHICLES=(
    '{"name": "Perseverance", "brand": "NASA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2020}'
    '{"name": "Curiosity", "brand": "NASA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2011}'
    '{"name": "Spirit", "brand": "NASA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2003}'
    '{"name": "Opportunity", "brand": "NASA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2003}'
    '{"name": "Sojourner", "brand": "NASA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 1996}'
    '{"name": "Ingenuity", "brand": "NASA", "type": {"id": '$DRONE_ID'}, "yearOfConstruction": 2020}'
    '{"name": "Phoenix", "brand": "NASA", "type": {"id": '$LANDER_ID'}, "yearOfConstruction": 2007}'
    '{"name": "Viking 1", "brand": "NASA", "type": {"id": '$LANDER_ID'}, "yearOfConstruction": 1975}'
    '{"name": "Viking 2", "brand": "NASA", "type": {"id": '$LANDER_ID'}, "yearOfConstruction": 1975}'
    '{"name": "Mars Express", "brand": "ESA", "type": {"id": '$ORBITER_ID'}, "yearOfConstruction": 2003}'
    '{"name": "Beagle 2", "brand": "ESA", "type": {"id": '$LANDER_ID'}, "yearOfConstruction": 2003}'
    '{"name": "Rosalind Franklin", "brand": "ESA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2022}'
    '{"name": "Zhurong", "brand": "CNSA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2021}'
    '{"name": "Tianwen-1", "brand": "CNSA", "type": {"id": '$ORBITER_ID'}, "yearOfConstruction": 2020}'
    '{"name": "Mars Polar Lander", "brand": "NASA", "type": {"id": '$LANDER_ID'}, "yearOfConstruction": 1999}'
    '{"name": "Pathfinder", "brand": "NASA", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 1996}'
    '{"name": "Red Pioneer", "brand": "Fictional Agency", "type": {"id": '$TRANSFER_VEHICLE_ID'}, "yearOfConstruction": 2030}'
    '{"name": "Stellar Scout", "brand": "Fictional Agency", "type": {"id": '$ORBITER_ID'}, "yearOfConstruction": 2035}'
    '{"name": "Ares Pathfinder", "brand": "Fictional Agency", "type": {"id": '$ROVER_ID'}, "yearOfConstruction": 2028}'
    '{"name": "Cosmos Voyager", "brand": "Fictional Agency", "type": {"id": '$TRANSFER_VEHICLE_ID'}, "yearOfConstruction": 2032}'
)

# Create vehicles with correct years and types
echo "Creating vehicles..."
for VEHICLE_DATA in "${VEHICLES[@]}"; do
    curl -s -X POST -H "Content-Type: application/json" -d "$VEHICLE_DATA" "$VEHICLE_API" > /dev/null
done

echo "Vehicles created successfully!"
