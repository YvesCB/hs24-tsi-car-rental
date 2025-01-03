# hs24-tsi-car-rental
Multi repo for frontend and backend of the car rental project

## Setup
- The backend relies on Sqlite3 being installed.
- The backend sets CORS for `localhost:5173` since that is the default port for Vite. When the port for the frontend is changed, the configuration for CORS in `/back/car-rental/src/main/java/ch/juventus/car_rental/CarRentalWebConfig.java` needs to be adjusted.

## Overview
The project contains the front and back end for a car rental web platform. To spice things up, the frontend is designed to feature Mars vehicles like rovers and drones instead. But the Back end allows for any generic "Car" details to be input.

The backend is a [Java Spring REST Server](https://spring.io/) and the frontend is a [React](https://react.dev/) app, written in [TypeScript](https://www.typescriptlang.org/) and built with [Vite](https://vite.dev/).

The platform allows for users to rent vehicles and for an admin to change the data of the vehicles and administer the bookings.
