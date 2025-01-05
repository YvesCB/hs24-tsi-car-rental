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

export type Query = {
  minPrice: number | null;
  maxPrice: number | null;
  brand: string | null;
  typeName: string | null;
  start: Date | null;
  end: Date | null;
}
