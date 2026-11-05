import {
  Autocomplete,
  TextField
} from "@mui/material";

type Props = {
  columns: string[];

  selected: string[];

  onChange: (
    values: string[]
  ) => void;
};

export default function SensorSelector({
  columns,
  selected,
  onChange
}: Props) {

  return (
    <Autocomplete
      multiple
      options={columns}
      value={selected}
      onChange={(_, value) =>
        onChange(value)
      }
      renderInput={(params) => (
        <TextField
          {...params}
          label="Seleccionar sensores"
        />
      )}
    />
  );

}