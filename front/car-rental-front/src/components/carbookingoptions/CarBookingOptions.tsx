import { useEffect, useState } from "react";
import { Booking, Car } from "../../types";
import DatePicker from "react-datepicker";
import { createBooking } from "../../api";
import { useNavigate } from "react-router-dom";
import "react-datepicker/dist/react-datepicker.css"
import "./style.css";
import { daysDifference, filterToDate, getBookedIntervals, getDateWithDayOffset, getTodayDate } from "./carbookingoptions";

type CarBookingOptionsProps = {
  car: Car;
}

const CarBookingOptions = ({ car }: CarBookingOptionsProps) => {
  const [calcCost, setCalcCost] = useState(0);
  const [calcDays, setCalcDays] = useState(0);
  const [selStart, setSelStart] = useState<Date>();
  const [selEnd, setSelEnd] = useState<Date>();
  const [name, setName] = useState<string>("");

  const navigate = useNavigate();

  useEffect(() => {
    if (selStart && selEnd) {
      const diff = daysDifference(selStart, selEnd) + 1;
      setCalcDays(diff);
      setCalcCost(car.pricePerDay * diff);
    }
  }, [selStart, selEnd])


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (selStart && selEnd) {
      if (confirm("Submit this booking?")) {
        const booking: Omit<Booking, "id"> = {
          fromDate: selStart,
          toDate: selEnd,
          customerName: name,
          carId: car.id,
        }

        createBooking(booking)
          .then((createdBooking: Booking) => {
            alert(`Created booking with id: ${createdBooking.id}`);
          })
          .catch((err) => {
            alert(err.message);
          });

        navigate("/car/" + car.id);
      }
    }
  }

  return (
    <div className="car-booking-options">
      <h1>Booking <span className="vehicle-name">{car.name}</span></h1>
      <div className="overview">
        <h3>Booking for: {calcDays} days</h3>
        <h3>Cost: {calcCost}</h3>
      </div>
      <form className="booking-form" onSubmit={handleSubmit} >
        <div className="input-box">
          <div className="date-selection">
            <h3>Booking dates</h3>
            <div className="input-with-label">
              <label htmlFor="start">From</label>
              <DatePicker
                selected={selStart}
                required
                excludeDateIntervals={getBookedIntervals(car)}
                filterDate={(date) => date >= getDateWithDayOffset(getTodayDate(), -1)}
                onChange={(date) => {
                  setSelStart(date ? date : new Date());
                  setSelEnd(undefined);
                }} />
            </div>

            {selStart ?
              <div className="input-with-label">
                <label htmlFor="to">To</label>
                <DatePicker
                  selected={selEnd}
                  required
                  filterDate={(date) => date >= selStart && filterToDate(car, date, selStart)}
                  //filterDate={(date) => filterToDate(car, date, selStart) && date >= selStart}
                  onChange={(date) => setSelEnd(date ? date : new Date())} />
              </div> :
              null
            }

          </div>
          <div className="input-with-label">
            <label htmlFor="name">Full name</label>
            <input type="text"
              id="name"
              placeholder="Full name here"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
        </div>

        <input className="submit-btn" type="submit" />
      </form>
    </div>
  );
}

export default CarBookingOptions;
