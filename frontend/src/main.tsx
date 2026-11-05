import React from "react";
import ReactDOM from "react-dom/client";

import { ThemeProvider } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";

import App from "./App";
import theme from "./theme";

import "leaflet/dist/leaflet.css";
import "leaflet-defaulticon-compatibility";
import "leaflet-defaulticon-compatibility/dist/leaflet-defaulticon-compatibility.css";

import "./styles.css";

ReactDOM.createRoot(
    document.getElementById("root")!
).render(
    <React.StrictMode>

        <ThemeProvider theme={theme}>
            <CssBaseline />

            <App />
        </ThemeProvider>

    </React.StrictMode>
);