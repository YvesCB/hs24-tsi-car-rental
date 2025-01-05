import { Car, CarType, Query } from "./types";

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

async function handleResponse(response: Response): Promise<any> {
  if (response.ok) {
    return response.json();
  } else if (response.status === 404) {
    throw new Error("Car not found");
  } else if (response.status === 400) {
    return response.text().then((errorText) => {
      throw new Error(errorText);
    });
  } else {
    throw new Error("Unknown Error");
  }
}

export async function findAllCars(): Promise<Car[]> {
  const response = await fetch(`${baseUrl}/cars`);

  return handleResponse(response);
}

export async function findCarsFiltered(query: Query): Promise<Car[]> {

  const response = await fetch(baseUrl + `/cars/filter${buildQueryString(query)}`);

  return handleResponse(response);
}

export async function findAllTypes(): Promise<CarType[]> {
  const response = await fetch(baseUrl + "/types");

  return handleResponse(response);
}
