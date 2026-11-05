import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend
} from "recharts";

type Props = {

  data: Record<string, any>[];

  selectedColumns: string[];

};

const colors = [
  "#42a5f5",
  "#66bb6a",
  "#ef5350",
  "#ab47bc",
  "#ffa726",
  "#26c6da",
  "#d4e157",
  "#8d6e63"
];

export default function DynamicSensorChart({
  data,
  selectedColumns
}: Props) {

  if (
    !data ||
    selectedColumns.length === 0
  ) {
    return null;
  }

  const chartData =
    data.map(
      (
        row,
        index
      ) => {

        const item: Record<
          string,
          any
        > = {
          index
        };

        selectedColumns.forEach(
          (
            column
          ) => {

            item[column] =
              Number(
                row[column]
              );

          }
        );

        return item;

      }
    );

  return (

    <ResponsiveContainer
      width="100%"
      height={500}
    >

      <LineChart
        data={chartData}
      >

        <XAxis
          dataKey="index"
        />

        <YAxis />

        <Tooltip />

        <Legend />

        {
          selectedColumns.map(
            (
              column,
              index
            ) => (

              <Line
                key={column}
                type="monotone"
                dataKey={column}
                stroke={
                  colors[
                    index %
                    colors.length
                  ]
                }
                dot={false}
              />

            )
          )
        }

      </LineChart>

    </ResponsiveContainer>

  );

}