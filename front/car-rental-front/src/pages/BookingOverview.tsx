import { useEffect, useState } from "react";
import BookingOverviewInfo from "../components/bookingoverviewinfo/BookingOverviewInfo";
import { Booking, Car } from "../types";
import { findAllBookings, findAllCars } from "../api";

const BookingOverview = () => {
  const [bookings, setBookings] = useState<Booking[]>();
  const [cars, setCars] = useState<Car[]>();

  useEffect(() => {
    findAllBookings()
      .then((bookings: Booking[]) => {
        setBookings(bookings);
      })
      .catch((err) => {
        alert(err.message);
      });
    findAllCars()
      .then((cars: Car[]) => {
        setCars(cars);
      })
      .catch((err) => {
        alert(err.message);
      });
  }, [bookings, cars]);

  if (bookings && cars) {
    return (
      <div className="booking-overview">
        <h1>Booking Overview</h1>
        <BookingOverviewInfo bookings={bookings} setBookings={setBookings} />
      </div>
    );
  } else {
    return (
      <>
        <h1>Booking Overview</h1>
        <p>Loading...</p>
      </>
    );
  }

}

export default BookingOverview;
