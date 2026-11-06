import { useState } from "react";

import {
  Container,
  Paper,
  Typography,
  CircularProgress,
  Alert,
  Divider,
  Box,
  Button,
  TextField
} from "@mui/material";

import DownloadIcon from "@mui/icons-material/Download";
import SendIcon from "@mui/icons-material/Send";

import UploadPanel from "./components/UploadPanel";
import RpmSpeedCoolantChart from "./components/RpmSpeedCoolantChart";
import GpsMap from "./components/GpsMap";
import EventTable from "./components/EventTable";
import SensorSelector from "./components/SensorSelector";
import DynamicSensorChart from "./components/DynamicSensorChart";
import SensorDataTable from "./components/SensorDataTable";

import { useTorqueLog } from "./hooks/useTorqueLog";

export default function App() {

  const {
    loading,
    error,
    log,
    upload,
    loadById
  } = useTorqueLog();

  const [
    selectedSensors,
    setSelectedSensors
  ] = useState<string[]>([]);

  const [
    idInput,
    setIdInput
  ] = useState<string>("");

  // Estado para el mensaje de carga dinámico
  const [
    loadingMessage,
    setLoadingMessage
  ] = useState<string>("Processing CSV...");

  // Manejador para el botón de enviar ID
  const handleSendId = () => {
    if (!idInput.trim()) return;
    setLoadingMessage("Loading data...");
    loadById(idInput.trim());
  };

  // Manejador para cuando se sube un archivo
  const handleFileUpload = (file: File) => {
    setLoadingMessage("Processing CSV...");
    upload(file);
  };

  return (
    <Container
      maxWidth="xl"
      sx={{
        mt: 3,
        mb: 3
      }}
    >
      {/* HEADER */}

      <Paper
        sx={{
          p: 3,
          mb: 3
        }}
      >
        <Typography
          variant="h3"
          gutterBottom
        >
          Torque Analyzer
        </Typography>

        <Typography
          variant="body1"
          sx={{
            mb: 3
          }}
        >
          Torque Pro CVS analyzer. Upload your Torque Pro CSV file and visualize the data in charts, tables, and maps.
        </Typography>

        {/* SPLITTER */}
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            flexDirection: { xs: "column", sm: "row" },
            gap: 2
          }}
        >
          {/* UPLOAD & TEXTBOX */}
          <Box 
            sx={{ 
              flexGrow: 1, 
              width: { xs: "100%", sm: "auto" },
              display: "flex",
              alignItems: "center",
              flexDirection: { xs: "column", md: "row" },
              gap: 2 
            }}
          >
            <Box sx={{ width: { xs: "100%", md: "auto" } }}>
              <UploadPanel
                onUpload={handleFileUpload}
              />
            </Box>

            <Box 
              sx={{ 
                display: "flex", 
                alignItems: "center", 
                gap: 1,
                width: { xs: "100%", md: "auto" }
              }}
            >
              <TextField
                label="Log ID"
                variant="outlined"
                size="small"
                value={idInput}
                onChange={(e) => setIdInput(e.target.value)}
                disabled={loading}
                sx={{ minWidth: "150px", flexGrow: { xs: 1, md: 0 } }}
              />
              <Button
                variant="contained"
                color="secondary"
                endIcon={<SendIcon />}
                onClick={handleSendId}
                disabled={!idInput.trim() || loading}
                sx={{ height: "40px", textTransform: "none" }}
              >
                Enviar
              </Button>
            </Box>
          </Box>

          {/* EXAMPLE */}
          <Box sx={{ textAlign: "right", minWidth: "200px", width: { xs: "100%", sm: "auto" } }}>
            <Button
              variant="outlined"
              color="primary"
              startIcon={<DownloadIcon />}
              href="/example.csv"
              download="example.csv"
              sx={{ 
                p: 0.3,
                fontSize: "0.7rem",
                textTransform: "none",
                width: { xs: "100%", sm: "auto" }
              }}
            >
              Example
            </Button>
            
            <Typography 
              variant="caption" 
              display="block" 
              color="text.secondary"
              sx={{ 
                mt: 0.5, 
                mr: { xs: 0, sm: 1 }, 
                textAlign: { xs: "center", sm: "right" },
                fontSize: "9px",
                fontStyle: "italic"
              }}
            >
              Don't have a log? Use our example.
            </Typography>
          </Box>
        </Box>
      </Paper>

      {/* LOADING */}

      {loading && (
        <Paper
          sx={{
            p: 3,
            mb: 3,
            textAlign: "center"
          }}
        >
          <CircularProgress />

          <Typography
            sx={{
              mt: 2
            }}
          >
            {loadingMessage}
          </Typography>
        </Paper>
      )}

      {/* ERROR */}

      {error && (
        <Alert
          severity="error"
          sx={{
            mb: 3
          }}
        >
          {error}
        </Alert>
      )}

      {/* CONTENT */}

      {log && (

        <>
          {/* CHART */}

          <Paper
            sx={{
              p: 3,
              mb: 3
            }}
          >
            <Typography
              variant="h5"
              gutterBottom
            >
              RPM / Speed / Coolant
            </Typography>

            <RpmSpeedCoolantChart
              data={log.records}
              timeField={log.config.timeField}
              rpmField={log.config.rpmField}
              speedField={log.config.speedField}
              coolantField={log.config.coolantField}
            />
          </Paper>

          {/* GPS */}

          <Paper
            sx={{
              p: 3,
              mb: 3
            }}
          >
            <Typography
              variant="h5"
              gutterBottom
            >
              GPS
            </Typography>

            <GpsMap
              data={log.records}
              latField={
                log.config.latitudeField
              }
              lonField={
                log.config.longitudeField
              }
              speedField={
                log.config.speedField
              }
            />
          </Paper>

          {/* EVENTS */}

          <Paper
            sx={{
              p: 3,
              mb: 3
            }}
          >
            <Typography
              variant="h5"
              gutterBottom
            >
              Events detected
            </Typography>

            <EventTable
              events={log.events}
            />
          </Paper>

          {/* SENSORS */}

          <Paper
            sx={{
              p: 3,
              mb: 3
            }}
          >
            <Typography
              variant="h5"
              gutterBottom
            >
              Dinamic Sensors
            </Typography>

            <SensorSelector
              columns={
                log.columns.map(
                  c => c.name
                )
              }
              selected={
                selectedSensors
              }
              onChange={
                setSelectedSensors
              }
            />

            <Divider
              sx={{
                my: 2
              }}
            />

            <DynamicSensorChart
              data={log.records}
              selectedColumns={
                selectedSensors
              }
            />
          </Paper>

          {/* RAW */}

          <Paper
            sx={{
              p: 3
            }}
          >
            <Typography
              variant="h5"
              gutterBottom
            >
              RAW Data
            </Typography>

            <SensorDataTable
              data={log.records}
            />
          </Paper>
        </>
      )}
    </Container>
  );
}
