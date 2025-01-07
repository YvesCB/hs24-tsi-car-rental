import { CarType } from "./types";

export function daysDifference(start: Date, end: Date): number {
  const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds

  const diffDays = Math.round(Math.abs((end.getTime() - start.getTime()) / oneDay));

  return diffDays;
}

export function getTodayDate(): Date {
  const now = Date.now();
  const nowDate = new Date(now);
  const onlyDateNow = new Date(nowDate.toISOString().split("T")[0]);

  return onlyDateNow;
}

export function getDateWithDayOffset(date: Date, offsetDays: number): Date {
  const offsetMilllis = offsetDays * 1000 * 60 * 60 * 24;

  const newDate = new Date(date.getTime() + offsetMilllis);

  return newDate;
}

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
