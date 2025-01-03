export type Car = {
  id: number;
  name: string;
  yearOfConstruction: string;
  pricePerDay: number;
  type: CarType;
  bookings: Booking[];
};

export type CarType = {
  id: number;
  name: string;
  description: string;
};

export type Booking = {
  fromDate: Date;
  toDate: Date;
  customerName: string;
  carId: number;
}
