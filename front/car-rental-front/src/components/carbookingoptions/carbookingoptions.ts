import { Car } from "../../types";

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

export const getBookedIntervals = (car: Car) => {
  return car.bookings.map((booking) => ({
    start: getDateWithDayOffset(new Date(booking.fromDate), -1),
    end: new Date(booking.toDate),
  }));
};

export const filterToDate = (car: Car, date: Date, selStart: Date): boolean => {
  if (car.bookings.length == 0) {
    return true;
  }

  const futureDateList = car.bookings
    .map((booking) => new Date(booking.fromDate))
    .filter((fromDate) => fromDate > selStart);

  const nearestFutureDate = futureDateList
    .sort((a, b) => a.getTime() - b.getTime())[0];

  // Return true if the given date is smaller than the nearest future date
  return nearestFutureDate ? date < getDateWithDayOffset(nearestFutureDate, - 1) : true;
}
