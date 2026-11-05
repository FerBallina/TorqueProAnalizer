import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from "@mui/material";

type Props = {

  data: Record<string, any>[];

};

export default function SensorDataTable({
  data
}: Props) {

  if (
    !data ||
    data.length === 0
  ) {
    return null;
  }

  const headers =
    Object.keys(
      data[0]
    );

  return (

    <TableContainer
      component={Paper}
      sx={{
        maxHeight: 600
      }}
    >

      <Table
        stickyHeader
        size="small"
      >

        <TableHead>

          <TableRow>

            {
              headers.map(
                header => (

                  <TableCell
                    key={header}
                  >
                    {header}
                  </TableCell>

                )
              )
            }

          </TableRow>

        </TableHead>

        <TableBody>

          {
            data.map(
              (
                row,
                rowIndex
              ) => (

                <TableRow
                  key={
                    rowIndex
                  }
                >

                  {
                    headers.map(
                      header => (

                        <TableCell
                          key={header}
                        >

                          {
                            row[
                              header
                            ]
                          }

                        </TableCell>

                      )
                    )
                  }

                </TableRow>

              )
            )
          }

        </TableBody>

      </Table>

    </TableContainer>

  );

}