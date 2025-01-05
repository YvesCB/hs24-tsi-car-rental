import { useState } from "react";
import { createType } from "../../api";
import { CarType } from "../../types";

const CreateCarType = () => {
  const [carType, setCarType] = useState<Omit<CarType, "id">>(
    {
      name: "",
      description: "",
    }
  )

  const handleSubmit = () => {
    if (carType.name === "" || carType.description === "") {
      alert("Name and description both need to contain something.")
    } else if (confirm(`Create Vehicle type ${carType.name}: ${carType.description}?`)) {
      createType(carType)
        .then((createdCarType: CarType) => {
          alert(`Created new type with id: ${createdCarType.id}`);
        })
        .catch((err) => {
          alert(err.message);
        })
    }
  }

  return (
    <div className="new-car-type">
      <div className="input-box">
        <div className="input-with-label">
          <label htmlFor="name">Name of type</label>
          <input type="text"
            id="name"
            placeholder="Some name here"
            value={carType.name}
            onChange={(e) => setCarType((prev) => ({ ...prev, "name": e.target.value }))}
          />
        </div>
        <div className="input-with-label">
          <label htmlFor="description">Description of type</label>
          <input type="text"
            id="description"
            placeholder="Some description here"
            value={carType.description}
            onChange={(e) => setCarType((prev) => ({ ...prev, "description": e.target.value }))}
          />
        </div>
      </div>
      <button onClick={handleSubmit}>Submit</button>
    </div>

  );
}

export default CreateCarType;
