import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@mui/material";

import { EventDto }
  from "../models/EventDto";

type Props = {

  events: EventDto[];

};

export default function EventTable({
  events
}: Props) {

  return (

    <Paper>

      <Table>

        <TableHead>

          <TableRow>

            <TableCell>
              Fila
            </TableCell>

            <TableCell>
              Sensor
            </TableCell>

            <TableCell>
              Valor
            </TableCell>

            <TableCell>
              Umbral
            </TableCell>

            <TableCell>
              Severidad
            </TableCell>

          </TableRow>

        </TableHead>

        <TableBody>

          {
            events.map(
              (
                event,
                index
              ) => (

                <TableRow
                  key={index}
                >

                  <TableCell>
                    {event.row}
                  </TableCell>

                  <TableCell>
                    {event.sensor}
                  </TableCell>

                  <TableCell>
                    {event.value}
                  </TableCell>

                  <TableCell>
                    {event.threshold}
                  </TableCell>

                  <TableCell>
                    {event.severity}
                  </TableCell>

                </TableRow>

              )
            )
          }

        </TableBody>

      </Table>

    </Paper>

  );

}