import {
  MapContainer,
  TileLayer,
  Polyline,
  Marker,
  Popup,
  CircleMarker,
  useMap
} from "react-leaflet";

import { useEffect } from "react";

import {
  Box,
  Chip
} from "@mui/material";

type Props = {
  data: Record<string, any>[];

  latField: string;
  lonField: string;
  speedField: string;
  selectedPoint?: any;
};

function FocusSelectedPoint({
  selectedPoint
}: {
  selectedPoint: any;
}) {

  const map = useMap();

  useEffect(() => {

    if (!selectedPoint) {
      return;
    }

    map.flyTo(
      [
        selectedPoint.latitude,
        selectedPoint.longitude
      ],
      15,
      {
        duration: 1
      }
    );

  }, [map, selectedPoint]);

  return null;
}

function FitBounds({
  route,
  selectedPoint
}: {
  route: [number, number][];
  selectedPoint: any;
})
    
    {

  const map = useMap();

useEffect(() => {

  if (selectedPoint) {
    return;
  }

  if (route.length > 1) {

    map.fitBounds(
      route,
      {
        padding: [50, 50]
      }
    );

  }

}, [
  map,
  route,
  selectedPoint
]);

  return null;

}

function getSpeedColor(
  speed: number
): string {

  if (speed < 40)
    return "#2196f3";

  if (speed < 60)
    return "#4caf50";

  if (speed < 80)
    return "#ffeb3b";

  if (speed < 100)
    return "#ff9800";

  return "#f44336";

}

export default function GpsMap({

  data,
  latField,
  lonField,
  speedField,
  selectedPoint

}: Props) {

  if (
    !data ||
    data.length === 0
  ) {
    return null;
  }

  const points =
    data
      .filter(
        row =>
          row[latField] !== undefined &&
          row[lonField] !== undefined
      )
      .map(row => ({

        lat: Number(
          row[latField]
        ),

        lon: Number(
          row[lonField]
        ),

        speed: Number(
          row[speedField] ?? 0
        )

      }))
      .filter(
        point =>
          !Number.isNaN(
            point.lat
          ) &&
          !Number.isNaN(
            point.lon
          )
      );

  if (
    points.length === 0
  ) {
    return null;
  }

  const route =
    points.map(
      p =>
        [
          p.lat,
          p.lon
        ] as [
          number,
          number
        ]
    );

  const start =
    points[0];

  const end =
    points[
      points.length - 1
    ];
  return (
    <>

      <MapContainer
        center={[
          start.lat,
          start.lon
        ]}
        zoom={12}
        style={{
          width: "100%",
          height: "600px"
        }}
      >
          <FocusSelectedPoint
            selectedPoint={selectedPoint}
          />
          
        <TileLayer
          attribution="© OpenStreetMap contributors"
          url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        <FitBounds
          route={route}
          selectedPoint={selectedPoint}
        />

        {
          points
            .slice(
              0,
              points.length - 1
            )
            .map(
              (
                point,
                index
              ) => {

                const next =
                  points[
                    index + 1
                  ];

                return (
                  <Polyline
                    key={
                      index
                    }
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
                      weight: 6
                    }}
                  />
                );

              }
            )
        }

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
        {
            selectedPoint && (

              <CircleMarker
                center={[
                  selectedPoint.latitude,
                  selectedPoint.longitude
                ]}
                radius={12}
                pathOptions={{
                  color: "#ffffff",
                  fillColor: "#ff0000",
                  fillOpacity: 1,
                  weight: 3
                }}
              >

                <Popup>

                  <strong>
                    Punto seleccionado
                  </strong>

                  <br />

                  {selectedPoint.originalTime}

                  <br />

                  RPM:
                  {" "}
                  {selectedPoint.rpm}

                  <br />

                  Velocidad:
                  {" "}
                  {selectedPoint.speed}
                  {" "}
                  km/h

                  <br />

                  Coolant:
                  {" "}
                  {selectedPoint.coolant}
                  {" "}
                  °C

                </Popup>

              </CircleMarker>

            )
          }

      </MapContainer>

      <Box
        sx={{
          mt: 2,
          display: "flex",
          gap: 1,
          flexWrap:
            "wrap"
        }}
      >

        <Chip
          label="<40 km/h"
          sx={{ 
            bgcolor: getSpeedColor(30),
            color: '#ffffff'
          }}
        />

        <Chip
          label="40-60 km/h"
          sx={{ 
            bgcolor: getSpeedColor(40),
            color: '#ffffff'
          }}
        />

        <Chip
          label="60-80 km/h"
          sx={{ 
            bgcolor: getSpeedColor(60),
            color: '#ffffff'
          }}
        />

        <Chip
          label="80-100 km/h"
          sx={{ 
            bgcolor: getSpeedColor(80),
            color: '#ffffff'
          }}
        />

        <Chip
          label="100+ km/h"
          sx={{ 
            bgcolor: getSpeedColor(100),
            color: '#ffffff'
          }}
        />

      </Box>

    </>
  );

}