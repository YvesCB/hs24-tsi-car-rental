import { Car } from "../../types";
import { getDateWithDayOffset } from "../../utils";

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
