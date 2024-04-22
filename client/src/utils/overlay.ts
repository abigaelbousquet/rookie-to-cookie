import { FeatureCollection } from "geojson";
import { FillLayer } from "react-map-gl";

const propertyName = "holc_grade";
export const geoLayer: FillLayer = {
  id: "geo_data",
  type: "fill",
  paint: {
    "fill-color": [
      "match",
      ["get", propertyName],
      "A",
      "#5bcc04",
      "B",
      "#04b8cc",
      "C",
      "#e9ed0e",
      "D",
      "#d11d1d",
      "#ccc",
    ],
    "fill-opacity": 0.2,
  },
};

function isFeatureCollection(json: any): json is FeatureCollection {
  return json.type === "FeatureCollection";
}

/**
 * This function fetches the overlay data from the backend and returns the Feature Collection to the front end
 * @param params bounding box query
 * @returns GeoJSON
 */
export async function overlayData(
  params: number[]
): Promise<GeoJSON.FeatureCollection | undefined> {
  let rl_response;
  if (params.length == 0) {
    rl_response = await fetch("http://localhost:3233/geodata");
  } else if (params.length == 4) {
    console.log("fetching" + params);
    rl_response = await fetch(
      "http://localhost:3233/geodata?" +
        "min-lat=" +
        params[0] +
        "&min-lon=" +
        params[1] +
        "&max-lat=" +
        params[2] +
        "&max-lon=" +
        params[3]
    );
  }
  const rl_json = await rl_response?.json();
  console.log(rl_json);
  return isFeatureCollection(rl_json) ? rl_json : undefined;
}

/**
 * This function queries the backend with searching for a keyword in the area description of each Feature
 * @param params 
 * @returns 
 */
export async function overlaySearchData(
  params: String
): Promise<GeoJSON.FeatureCollection | undefined> {
  let rl_response;
  console.log("fetching" + params);
  rl_response = await fetch(
    "http://localhost:3233/search?" + "keyword=" + params
  );
  const rl_json = await rl_response?.json();
  console.log(rl_json);
  return isFeatureCollection(rl_json) ? rl_json : undefined;
}
