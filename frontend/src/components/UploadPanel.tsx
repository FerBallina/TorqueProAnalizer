import {
  Box,
  Button
} from "@mui/material";

type Props = {

  onUpload:
    (file: File) => void;

};

export default function UploadPanel({
  onUpload
}: Props) {

  return (

    <Box>

      <Button
        variant="contained"
        component="label"
      >

        Upload CSV Torque

        <input
          hidden
          accept=".csv"
          type="file"
          onChange={(event) => {

            const file =
              event.target.files?.[0];

            if (file) {

              onUpload(file);

            }

          }}
        />

      </Button>

    </Box>

  );

}