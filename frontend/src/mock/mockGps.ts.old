import {
  MapContainer,
  TileLayer,
  Polyline,
  Marker,
  Popup,
  useMap
} from "react-leaflet";

import { useEffect } from "react";

import {
  Box,
  Chip
} from "@mui/material";

import "leaflet/dist/leaflet.css";

type Props = {
  data: Record<string, any>[];

  latField: string;
  lonField: string;
  speedField: string;
};

function FitBounds({
  route
}: {
  route: [number, number][];
}) {

  const map = useMap();

  useEffect(() => {

    if (route.length > 1) {

      map.fitBounds(route, {
        padding: [50, 50]
      });

    }

  }, [map, route]);

  return null;

}

function getSpeedColor(
  speed: number
): string {

  if (speed < 30) {
    return "#2196f3"; // Azul
  }

  if (speed < 60) {
    return "#4caf50"; // Verde
  }

  if (speed < 90) {
    return "#ffeb3b"; // Amarillo
  }

  if (speed < 120) {
    return "#ff9800"; // Naranja
  }

  return "#f44336"; // Rojo

}

export default function GpsMap({
  data,
  latField,
  lonField,
  speedField
}: Props) {

  if (!data || data.length === 0) {
    return null;
  }

  const points = data
    .filter(
      row =>
        row[latField] !== undefined &&
        row[lonField] !== undefined
    )
    .map(row => ({
      lat: Number(row[latField]),
      lon: Number(row[lonField]),
      speed: Number(row[speedField] ?? 0)
    }))
    .filter(
      point =>
        !Number.isNaN(point.lat) &&
        !Number.isNaN(point.lon)
    );

  if (points.length === 0) {
    return null;
  }

  const route: [number, number][] =
    points.map(point => [
      point.lat,
      point.lon
    ]);

  const start = points[0];

  const end = points[
    points.length - 1
  ];

  return (
    <>
      <MapContainer
        center={[
          start.lat,
          start.lon
        ]}
        zoom={13}
        style={{
          width: "100%",
          height: "600px"
        }}
      >
        <TileLayer
          attribution="© OpenStreetMap contributors"
          url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        <FitBounds route={route} />

        {points
          .slice(0, -1)
          .map(
            (
              point,
              index
            ) => {

              const next =
                points[index + 1];

              return (
                <Polyline
                  key={index}
                  positions={[
                    [
                      point.lat,
                      point.lon
                    ],
                    [
                      next.lat,
                      next.lon
                    ]
                  ]}
                  pathOptions={{
                    color:
                      getSpeedColor(
                        point.speed
                      ),
                    weight: 5
                  }}
                />
              );

            }
          )}

        <Marker
          position={[
            start.lat,
            start.lon
          ]}
        >
          <Popup>
            Inicio
          </Popup>
        </Marker>

        <Marker
          position={[
            end.lat,
            end.lon
          ]}
        >
          <Popup>
            Fin
          </Popup>
        </Marker>

      </MapContainer>

      <Box
        sx={{
          display: "flex",
          gap: 1,
          mt: 2,
          flexWrap: "wrap"
        }}
      >
        <Chip
          label="< 30 km/h"
          sx={{
            backgroundColor: "#2196f3",
            color: "white"
          }}
        />

        <Chip
          label="30-60 km/h"
          sx={{
            backgroundColor: "#4caf50",
            color: "white"
          }}
        />

        <Chip
          label="60-90 km/h"
          sx={{
            backgroundColor: "#ffeb3b",
            color: "black"
          }}
        />

        <Chip
          label="90-120 km/h"
          sx={{
            backgroundColor: "#ff9800",
            color: "white"
          }}
        />

        <Chip
          label="120+ km/h"
          sx={{
            backgroundColor: "#f44336",
            color: "white"
          }}
        />
      </Box>
    </>
  );
}