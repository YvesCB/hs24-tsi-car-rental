import { Link } from "react-router-dom";
import { Booking } from "../../types";
import { getThisMonthBookings, getThisMonthProfit } from "./bookingoverviewinfo";
import "./style.css";
import { deleteBooking } from "../../api";

type BookingOverviewInfoProps = {
  bookings: Booking[],
  setBookings: React.Dispatch<React.SetStateAction<Booking[] | undefined>>;
}

const BookingOverviewInfo = ({ bookings, setBookings }: BookingOverviewInfoProps) => {
  const handleDelete = (booking: Booking) => {
    if (confirm(`Delete the booking of ${booking.car.name}`)) {
      deleteBooking(booking.id)
        .then(() => {
          alert("Deleted booking with id: " + booking.id);
          setBookings([]);
        })
        .catch((err) => {
          alert(err.message);
        })
    }
  }

  return (
    <div className="booking-overview-info">
      <div className="input-box">
        <div className="booking-info">
          <div>
            <h3>Profits this month</h3>
            <p>{getThisMonthProfit(bookings)}</p>
          </div>

          <div>
            <h3>Bookings this month</h3>
            <p>{getThisMonthBookings(bookings).length}</p>
          </div>
        </div>
      </div>
      {
        bookings.map((booking) => {
          return (
            <div className="input-box" key={booking.id}>
              <div className="name-and-btn">
                <div>
                  <Link to={`/admin/car/${booking.car.id}`}><h3>{booking.car.name}</h3></Link>
                  <p>From: {booking.fromDate.toString()}</p>
                  <p>To: {booking.toDate.toString()}</p>
                  <p>At: {booking.car.pricePerDay} per day</p>
                  <p>By: {booking.customerName}</p>
                </div>
                <button onClick={() => handleDelete(booking)}>Delete</button>
              </div>
            </div>
          );
        })}
    </div >
  );
}

export default BookingOverviewInfo;
