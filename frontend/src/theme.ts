import { createTheme } from "@mui/material";

const theme = createTheme({
    palette: {
        mode: "dark",

        primary: {
            main: "#1976d2"
        },

        secondary: {
            main: "#ff9800"
        },

        background: {
            default: "#0f172a",
            paper: "#111827"
        }
    }
});

export default theme;