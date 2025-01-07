import { Booking } from "../../types";
import { daysDifference } from "../../utils";

export function getThisMonthBookings(bookings: Booking[]): Booking[] {
  const now = new Date(Date.now());
  const thisYear = now.getFullYear();
  const thisMonth = now.getMonth();

  // we go by start date
  const bookingsThisMonth = bookings.filter((booking) => {
    const isThisYear = new Date(booking.fromDate).getFullYear() === thisYear;
    const isThisMonth = new Date(booking.fromDate).getMonth() === thisMonth;

    return isThisYear && isThisMonth;
  })

  return bookingsThisMonth;
}

export function getThisMonthProfit(bookings: Booking[]): number {
  const thisMonthBookings = getThisMonthBookings(bookings);

  const thisMonthBookingsDaysAndCost = thisMonthBookings.map(booking => {
    return {
      numDays: daysDifference(new Date(booking.fromDate), new Date(booking.toDate)) + 1,
      carPrice: booking.car.pricePerDay,
    }
  });

  let profit = 0;

  thisMonthBookingsDaysAndCost.forEach(daysAndCost => {
    profit += daysAndCost.numDays * daysAndCost.carPrice;
  });

  return profit;
}
