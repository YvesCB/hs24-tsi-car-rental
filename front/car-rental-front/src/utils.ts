import { CarType } from "./types";

export function invariant(value: unknown): asserts value {
  if (value) return;

  throw new Error("Invariant violation");
};

export function imageFromType(carType: CarType): string {
  if (carType.name == "Rover") {
    return "/rover.jpg";
  } else if (carType.name === "Lander") {
    return "/lander.jpg";
  } else if (carType.name === "Drone") {
    return "/drone.png";
  } else {
    return "/placeholder-img.png";
  }
}
