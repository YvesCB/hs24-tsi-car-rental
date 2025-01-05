import { deleteType } from "../../api";
import { CarType } from "../../types";
import "./style.css";

type ExistingTypesProps = {
  types: CarType[];
  setTypes: React.Dispatch<React.SetStateAction<CarType[] | undefined>>;
}

const ExistingTypes = ({ types, setTypes }: ExistingTypesProps) => {
  const handleDelete = (type: CarType) => {
    if (confirm(`Delete the type ${type.name}: ${type.description}`)) {
      deleteType(type.id)
        .then(() => {
          alert("Deleted type with id: " + type.id);
          setTypes([]);
        })
        .catch((err) => {
          alert(err.message);
        })
    }
  }

  return (
    <div className="existing-types">
      {types.map((type) => {
        return (
          <div className="input-box" key={type.id}>
            <div className="name-and-btn">
              <h3>{type.name}</h3>
              <button onClick={() => handleDelete(type)}>Delete</button>
            </div>
            <p>{type.description}</p>
          </div>
        );
      })}
    </div>
  );
}

export default ExistingTypes;
