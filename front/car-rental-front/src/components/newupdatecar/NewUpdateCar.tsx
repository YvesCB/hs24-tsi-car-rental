import { useEffect, useState } from "react";
import { Car, CarType, UpsertCar } from "../../types";
import { createCar, findAllTypes } from "../../api";
import { useNavigate } from "react-router-dom";

type NewUpdateCarProps = {
  car: UpsertCar; // null when we're making a new one
  setCar: React.Dispatch<React.SetStateAction<UpsertCar>>;
  carId: number | null;
}

const NewUpdateCar = ({ car, setCar, carId }: NewUpdateCarProps) => {
  const [types, setTypes] = useState<CarType[]>();

  const navigate = useNavigate()

  useEffect(() => {
    findAllTypes()
      .then((carTypes: CarType[]) => {
        setTypes(carTypes);
      })
      .catch((err) => {
        alert(err.mesage);
      });
  }, [types]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (confirm("Submit new vehicle?")) {
      createCar(car)
        .then((createdCar: Car) => {
          alert(`Created car with id: ${createdCar.id}`);
          setCar(
            {
              name: "",
              brand: "",
              yearOfConstruction: 0,
              automatic: true,
              pricePerDay: 0,
              active: true,
              type: { id: 0 },
            }
          );
        })
        .catch((err) => alert(err.message));

      if (carId) {
        navigate("/admin/car/" + carId);
      }
    }
  }

  return (
    <form className="upsert-car" onSubmit={handleSubmit}>
      <div className="input-box">

        <div className="input-with-label">
          <label htmlFor="name">Name</label>
          <input type="text"
            id="name"
            placeholder="Some name here"
            required
            value={car.name}
            onChange={(e) => setCar((prev) => ({ ...prev, "name": e.target.value }))}
          />
        </div>

        <div className="input-with-label">
          <label htmlFor="brand">Brand</label>
          <input type="text"
            id="brand"
            placeholder="Some description here"
            required
            value={car.brand}
            onChange={(e) => setCar((prev) => ({ ...prev, "brand": e.target.value }))}
          />
        </div>

        <div className="input-with-label">
          <label htmlFor="yearOfConstruction">Year of construction</label>
          <input type="number"
            id="yearOfConstruction"
            placeholder="Some year here"
            max="2030"
            min="1800"
            required
            value={car.yearOfConstruction}
            onChange={(e) => setCar((prev) => ({ ...prev, "yearOfConstruction": parseInt(e.target.value) }))}
          />
        </div>

        <div className="input-with-label">
          <label htmlFor="automatic">Gearbox</label>
          <select
            id="automatic"
            required
            value={car.automatic ? "automatic" : "manual"}
            onChange={(e) => setCar((prev) => ({ ...prev, "automatic": e.target.value === "automatic" }))}
          >
            <option value="">Select gearbox</option>
            <option value="automatic">
              automatic
            </option>
            <option value="manual">
              manual
            </option>
          </select>
        </div>
        <div className="input-with-label">
          <label htmlFor="type">Type</label>
          {types ? (
            <select
              id="type"
              required
              value={car.type.id}
              onChange={(e) => setCar((prev) => ({ ...prev, type: { id: parseInt(e.target.value) } }))}
            >
              <option value="">Select Type</option>
              {types.map((type) => (
                <option key={type.id} value={type.id}>
                  {type.name}
                </option>
              ))}
            </select>
          ) : (
            <p>Loading ...</p>
          )}
        </div>

        <div className="input-with-label">
          <label htmlFor="pricePerDay">Price per day</label>
          <input type="number"
            id="pricePerDay"
            placeholder="Some price here"
            required
            value={car.pricePerDay}
            onChange={(e) => setCar((prev) => ({ ...prev, "pricePerDay": parseInt(e.target.value) }))}
          />
        </div>

      </div>
      <input className="submit-btn" type="submit" />
    </form>
  );
}

export default NewUpdateCar;
