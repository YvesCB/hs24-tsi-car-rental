import { Car, CarType, Query, UpsertCar } from "./types";

export const baseUrl = "http://localhost:8080/api";

export function buildQueryString(params: Query): string {
  const query = Object.entries(params)
    .filter(([_, value]) => value !== null) // Remove entries with null values
    .map(([key, value]) => {
      if (value instanceof Date) {
        // Convert Date objects to ISO strings
        return `${encodeURIComponent(key)}=${encodeURIComponent(value.toISOString().split("T")[0])}`;
      }
      return `${encodeURIComponent(key)}=${encodeURIComponent(value as string | number)}`;
    })
    .join('&');
  return query ? `?${query}` : '';
}

async function handleJsonResponse(response: Response): Promise<any> {
  if (response.ok) {
    return response.json();
  } else if (response.status === 404) {
    const errorText = await response.text();
    throw new Error(errorText);
  } else if (response.status === 400) {
    const errorText = await response.text();
    throw new Error(errorText);
  } else {
    throw new Error("Unknown Error");
  }
}

async function handleResponse(response: Response): Promise<void> {
  if (response.status === 202) {
    return;
  } else if (response.status === 200) {
    return;
  } else if (response.status === 404) {
    const errorText = await response.text();
    throw new Error(errorText);
  } else if (response.status === 400) {
    const errorText = await response.text();
    throw new Error(errorText);
  } else {
    throw new Error("Unknown Error");
  }
}

export async function findAllCars(): Promise<Car[]> {
  const response = await fetch(`${baseUrl}/cars`);

  return handleJsonResponse(response);
}

export async function findCarsFiltered(query: Query): Promise<Car[]> {

  const response = await fetch(baseUrl + `/cars/filter${buildQueryString(query)}`);

  return handleJsonResponse(response);
}

export async function findCarById(id: number): Promise<Car> {
  const response = await fetch(baseUrl + `/cars/${id}`);

  return handleJsonResponse(response);
}

export async function createCar(car: UpsertCar): Promise<Car> {
  const response = await fetch(baseUrl + "/cars", {
    headers: { "Content-Type": "application/json" },
    method: "POST", body: JSON.stringify(car)
  });

  return handleJsonResponse(response);
}

export async function deleteCar(id: number): Promise<void> {
  const response = await fetch(baseUrl + `/cars/${id}`, { method: "DELETE" });

  return handleResponse(response);
}

export async function findAllTypes(): Promise<CarType[]> {
  const response = await fetch(baseUrl + "/types");

  return handleJsonResponse(response);
}

export async function createType(carType: Omit<CarType, "id">): Promise<CarType> {
  const response = await fetch(baseUrl + "/types", {
    headers: { "Content-Type": "application/json" },
    method: "POST", body: JSON.stringify(carType)
  });

  return handleJsonResponse(response);
}

export async function deleteType(id: number): Promise<void> {
  const response = await fetch(baseUrl + `/types/${id}`, { method: "DELETE" });

  return handleResponse(response);
}
