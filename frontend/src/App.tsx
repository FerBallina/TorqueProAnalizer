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
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from "@mui/material";

import DownloadIcon from "@mui/icons-material/Download";
import SearchIcon from "@mui/icons-material/Search";
import CloudQueueIcon from "@mui/icons-material/CloudQueue";

import UploadPanel from "./components/UploadPanel";
import RpmSpeedCoolantChart from "./components/RpmSpeedCoolantChart";
import GpsMap from "./components/GpsMap";
import EventTable from "./components/EventTable";
import SensorSelector from "./components/SensorSelector";
import DynamicSensorChart from "./components/DynamicSensorChart";
import SensorDataTable from "./components/SensorDataTable";

import { useTorqueLog, SessionHistoryItem } from "./hooks/useTorqueLog";

export default function App() {

  const {
    loading,
    error,
    log,
    history,
    upload,
    loadById,
    loadHistory
  } = useTorqueLog();

  const [
    selectedSensors,
    setSelectedSensors
  ] = useState<string[]>([]);

  const [
    selectedTelemetryPoint,
    setSelectedTelemetryPoint
  ] = useState<any>(null);
  console.log(
    "APP SELECTED",
    selectedTelemetryPoint
  );
  // Estados para el formulario de búsqueda histórica
  const [emailInput, setEmailInput] = useState<string>("");
  const [fromDate, setFromDate] = useState<string>("");
  const [toDate, setToDate] = useState<string>("");

  // Estado para controlar qué fila de la tabla está seleccionada
  const [selectedSessionId, setSelectedSessionId] = useState<string | null>(null);

  const [
    loadingMessage,
    setLoadingMessage
  ] = useState<string>("Processing CSV...");

  // Manejador para buscar el historial de sesiones
  const handleSearchHistory = (e: React.FormEvent) => {
    e.preventDefault();
    if (!emailInput.trim() || !fromDate || !toDate) return;
    setLoadingMessage("Searching sessions...");
    loadHistory(emailInput.trim(), fromDate, toDate);
  };

  // Manejador al hacer click en una sesión de la lista
  const handleSelectSession = (session: SessionHistoryItem) => {
    setSelectedSessionId(session.sessionId);
    setLoadingMessage("Loading session data...");
    loadById(session.sessionId);
  };

  // Manejador para cuando se sube un archivo CSV manual
  const handleFileUpload = (file: File) => {
    setSelectedSessionId(null); // Reseteamos la selección de DB si sube un CSV
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
      {/* HEADER & CONTROLES */}
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
          Torque Pro CSV analyzer. Upload your Torque Pro CSV file or search live database entries by your account info.
        </Typography>

        <Divider sx={{ mb: 3 }} />

        {/* CONTENEDOR DE ENTRADAS (HISTORIAL + ARCHIVO) */}
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 3
          }}
        >
          {/* SECCIÓN 1: FORMULARIO HISTORIAL DE SESIONES */}
          <Box component="form" onSubmit={handleSearchHistory}>
            <Typography variant="subtitle2" color="text.secondary" sx={{ mb: 1, fontWeight: "bold" }}>
              SEARCH REMOTE DATABASE SESSIONS
            </Typography>
            <Box
              sx={{
                display: "flex",
                flexDirection: { xs: "column", md: "row" },
                gap: 2,
                alignItems: "flex-end"
              }}
            >
              <TextField
                label="Account Email"
                variant="outlined"
                size="small"
                type="email"
                required
                value={emailInput}
                onChange={(e) => setEmailInput(e.target.value)}
                disabled={loading}
                sx={{ flexGrow: 1, width: "40%" }}
              />
              <TextField
                label="From Date"
                variant="outlined"
                size="small"
                type="date"
                required
                InputLabelProps={{ shrink: true }}
                value={fromDate}
                onChange={(e) => setFromDate(e.target.value)}
                disabled={loading}
                sx={{ width: { xs: "100%", md: "200px" } }}
              />
              <TextField
                label="To Date"
                variant="outlined"
                size="small"
                type="date"
                required
                InputLabelProps={{ shrink: true }}
                value={toDate}
                onChange={(e) => setToDate(e.target.value)}
                disabled={loading}
                sx={{ width: { xs: "100%", md: "200px" } }}
              />
              <Button
                variant="contained"
                color="primary"
                type="submit"
                startIcon={<SearchIcon />}
                disabled={loading || !emailInput.trim() || !fromDate || !toDate}
                sx={{ height: "40px", textTransform: "none", width: { xs: "100%", md: "auto" } }}
              >
                Search Sessions
              </Button>
            </Box>
          </Box>

          <Divider />

          {/* SECCIÓN 2: SUBIDA MANUAL & EJEMPLO */}
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              flexDirection: { xs: "column", sm: "row" },
              gap: 2
            }}
          >
            <Box sx={{ flexGrow: 1, width: { xs: "100%", sm: "auto" } }}>
              <Typography variant="subtitle2" color="text.secondary" sx={{ mb: 1, fontWeight: "bold" }}>
                OR UPLOAD LOCAL CSV LOG
              </Typography>
              <UploadPanel onUpload={handleFileUpload} />
            </Box>

            <Box sx={{ textAlign: "right", minWidth: "200px", width: { xs: "100%", sm: "auto" }, mt: { xs: 1, sm: 3 } }}>
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
        </Box>
      </Paper>

      {/* HISTORIAL: TABLA DE VIAJES ENCONTRADOS */}
      {history.length > 0 && (
        <TableContainer component={Paper} sx={{ mb: 3, maxHeight: "250px" }}>
          <Table stickyHeader size="small">
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold" }}>Start Time</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>End Time</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>Records Amount</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>Session ID</TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {history.map((session) => (
                <TableRow 
                  key={session.sessionId}
                  hover
                  selected={selectedSessionId === session.sessionId}
                >
                  <TableCell>{session.startTime}</TableCell>
                  <TableCell>{session.endTime}</TableCell>
                  <TableCell>{session.recordCount} pts</TableCell>
                  <TableCell sx={{ fontStyle: "italic", fontSize: "0.8rem" }}>{session.sessionId}</TableCell>
                  <TableCell align="right">
                    <Button
                      size="small"
                      variant={selectedSessionId === session.sessionId ? "contained" : "outlined"}
                      color="secondary"
                      startIcon={<CloudQueueIcon />}
                      onClick={() => handleSelectSession(session)}
                      disabled={loading}
                      sx={{ textTransform: "none", fontSize: "0.75rem" }}
                    >
                      {selectedSessionId === session.sessionId ? "Cargado" : "Visualizar"}
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

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
              latitudeField={log.config.latitudeField}
              longitudeField={log.config.longitudeField}
              onPointSelected={setSelectedTelemetryPoint}
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
              selectedPoint={
                selectedTelemetryPoint
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
