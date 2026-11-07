import { useMemo, useState } from "react";

import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ReferenceLine,
  CartesianGrid,
  Brush
} from "recharts";

import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Box
} from "@mui/material";

type ScaleMode =
  | "AUTO"
  | "SMART"
  | "FIXED";

type AxisConfig = {
  min?: number;
  max?: number;
};

type Props = {
  data: Record<string, any>[];

  timeField: string;

  rpmField: string;
  speedField: string;
  coolantField: string;

  latitudeField: string;
  longitudeField: string;

  onPointSelected?: (
    point: any
  ) => void;

  rpmScale?: AxisConfig;
  speedScale?: AxisConfig;
  coolantScale?: AxisConfig;
};

function percentile(
  values: number[],
  p: number
): number {
  if (values.length === 0) {
    return 0;
  }

  const sorted =
    [...values].sort((a, b) => a - b);

  const index =
    Math.floor(sorted.length * p);

  return sorted[
    Math.min(
      index,
      sorted.length - 1
    )
  ];
}

function calculateDomain(
  values: number[],
  mode: ScaleMode,
  fixed?: AxisConfig
): [number, number] {

  const numeric =
    values.filter(
      v =>
        !Number.isNaN(v) &&
        Number.isFinite(v)
    );

  if (numeric.length === 0) {
    return [0, 100];
  }

  if (
    mode === "FIXED" &&
    fixed?.min !== undefined &&
    fixed?.max !== undefined
  ) {
    return [
      fixed.min,
      fixed.max
    ];
  }

  const max =
    Math.max(...numeric);

  if (mode === "SMART") {

    const min =
      percentile(numeric, 0.05);

    const padding =
      Math.max(
        (max - min) * 0.1,
        1
      );

    return [
      min - padding,
      max + padding
    ];
  }

  const min =
    Math.min(...numeric);

  const padding =
    Math.max(
      (max - min) * 0.1,
      1
    );

  return [
    min - padding,
    max + padding
  ];
}

function parseTimeToSeconds(
  value: string
): number {

  if (!value) {
    return 0;
  }

  const parts =
    value.split(":");

  if (parts.length < 3) {
    return 0;
  }

  const hours =
    Number(parts[0]);

  const minutes =
    Number(parts[1]);

  const seconds =
    Number(parts[2]);

  return (
    hours * 3600 +
    minutes * 60 +
    seconds
  );
}

function formatElapsedTime(
  value: number
): string {

  const totalSeconds =
    Math.floor(value);

  const hours =
    Math.floor(
      totalSeconds / 3600
    );

  const minutes =
    Math.floor(
      (totalSeconds % 3600) / 60
    );

  const seconds =
    totalSeconds % 60;

  if (hours > 0) {

    return `${hours}:${String(
      minutes
    ).padStart(2, "0")}:${String(
      seconds
    ).padStart(2, "0")}`;

  }

  return `${minutes}:${String(
    seconds
  ).padStart(2, "0")}`;

}

export default function RpmSpeedCoolantChart({

  data,

  timeField,

  rpmField,

  speedField,

  coolantField,

  latitudeField,

  longitudeField,

  onPointSelected,

  rpmScale,

  speedScale,

  coolantScale

}: Props) {

  const [
    scaleMode,
    setScaleMode
  ] = useState<ScaleMode>(
    "SMART"
  );

  const chartData =
    useMemo(
        () =>
            data.map(row => ({

                elapsedSeconds:
                    Number(
                        row.elapsedSeconds ?? 0
                    ),

              originalTime:
                  row[timeField],

              rpm:
                  Number(
                      row[rpmField] ?? 0
                  ),

              speed:
                  Math.trunc(
                    Number(
                      row[speedField] ?? 0
                    )
                  ),

              coolant:
                  Number(
                      row[coolantField] ?? 0
                  ),

              latitude:
                  Number(
                      row[latitudeField]
                  ),

              longitude:
                  Number(
                      row[longitudeField]
                  )

            })),
        [
            data,
            timeField,
            rpmField,
            speedField,
            coolantField
        ]
    );

  const rpmDomain =
    calculateDomain(
      chartData.map(
        x => x.rpm
      ),
      scaleMode,
      rpmScale
    );

  const speedDomain =
    calculateDomain(
      chartData.map(
        x => x.speed
      ),
      scaleMode,
      speedScale
    );

  const coolantDomain =
    calculateDomain(
      chartData.map(
        x => x.coolant
      ),
      scaleMode,
      coolantScale
    );

    console.log("TIME FIELD", timeField);
    console.log("RPM FIELD", rpmField);
    console.log("SPEED FIELD", speedField);
    console.log("COOLANT FIELD", coolantField);

    console.log("PRIMER REGISTRO", data[0]);
    console.log("PRIMER CHART DATA", chartData[0]);

  return (
    <>
      <Box sx={{ mb: 2 }}>

        <FormControl
          size="small"
          sx={{
            minWidth: 220
          }}
        >

          <InputLabel>
            Escala
          </InputLabel>

          <Select
            value={scaleMode}
            label="Escala"
            onChange={(event) =>
              setScaleMode(
                event.target.value as ScaleMode
              )
            }
          >

            <MenuItem value="SMART">
              Smart
            </MenuItem>

            <MenuItem value="AUTO">
              Auto
            </MenuItem>

            <MenuItem value="FIXED">
              Backend
            </MenuItem>

          </Select>

        </FormControl>

      </Box>

      <ResponsiveContainer
        width="100%"
        height={650}
      >

        <LineChart
              data={chartData}
              onClick={(event: any) => {

                    const index =
                      Number(
                        event?.activeTooltipIndex
                      );

                    if (
                      Number.isNaN(index)
                    ) {
                      return;
                    }

                    const point =
                      chartData[index];

                    console.log(
                      "POINT",
                      point
                    );

                    onPointSelected?.(
                      point
                    );

                  }}
            >

          <CartesianGrid
            strokeDasharray="3 3"
          />

          <XAxis
            dataKey="elapsedSeconds"
            minTickGap={120}
            tickFormatter={
              formatElapsedTime
            }
          />

          <YAxis
            yAxisId="rpm"
            orientation="left"
            domain={rpmDomain}
          />

          <YAxis
            yAxisId="speed"
            orientation="right"
            domain={speedDomain}
          />

          <YAxis
            yAxisId="coolant"
            orientation="right"
            offset={65}
            domain={coolantDomain}
          />

          <ReferenceLine
            yAxisId="coolant"
            y={100}
            stroke="#ffeb3b"
            strokeDasharray="5 5"
          />

          <ReferenceLine
            yAxisId="coolant"
            y={105}
            stroke="#ff9800"
            strokeDasharray="5 5"
          />

          <ReferenceLine
            yAxisId="coolant"
            y={110}
            stroke="#f44336"
            strokeDasharray="5 5"
          />

          <Tooltip
              contentStyle={{
                backgroundColor: "#111827",
                border: "1px solid #374151",
                color: "#ffffff"
              }}
              labelStyle={{
                color: "#ffffff",
                fontWeight: "bold"
              }}
              itemStyle={{
                color: "#ffffff"
              }}
              labelFormatter={(_, payload) => {

                if (
                  payload &&
                  payload.length > 0
                ) {

                  return `Timestamp: ${
                    payload[0].payload.originalTime
                  }`;

                }

                return "";

              }}
              formatter={(value, name) => {

                if (name === "RPM") {

                  return [
                    value,
                    "RPM"
                  ];

                }

                if (name === "Velocidad") {

                  return [
                    `${Number(value).toFixed(1)} km/h`,
                    "Velocidad"
                  ];

                }

                if (name === "Coolant") {

                  return [
                    `${value} °C`,
                    "Coolant"
                  ];

                }

                return [
                  value,
                  name
                ];

              }}
            />

          <Legend />

          <Line
            yAxisId="rpm"
            type="monotone"
            dataKey="rpm"
            name="RPM"
            stroke="#42a5f5"
            strokeWidth={2}
            dot={false}
          />

          <Line
            yAxisId="speed"
            type="monotone"
            dataKey="speed"
            name="Velocidad"
            stroke="#4caf50"
            strokeWidth={2}
            dot={false}
          />

          <Line
            yAxisId="coolant"
            type="monotone"
            dataKey="coolant"
            name="Coolant"
            stroke="#ef5350"
            strokeWidth={2}
            dot={false}
          />

          <Brush
              dataKey="elapsedSeconds"
              height={35}
              stroke="#42a5f5"
              tickFormatter={
                formatElapsedTime
              }
            />

        </LineChart>

      </ResponsiveContainer>
    </>
  );
}