import { useState } from "react";

import {
  Container,
  Paper,
  Typography,
  CircularProgress,
  Alert,
  Divider
} from "@mui/material";

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
    upload
  } = useTorqueLog();

  const [
    selectedSensors,
    setSelectedSensors
  ] = useState<string[]>([]);

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
          Analizador de logs de Torque Pro
        </Typography>

        <UploadPanel
          onUpload={upload}
        />
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
            Procesando CSV...
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

      {/* CONTENIDO */}

      {log && (

        <>
          {/* GRAFICO PRINCIPAL */}

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
              RPM / Velocidad / Coolant
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
              Ruta GPS
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

          {/* EVENTOS */}

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
              Eventos Detectados
            </Typography>

            <EventTable
              events={log.events}
            />
          </Paper>

          {/* SENSORES DINAMICOS */}

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
              Sensores Dinámicos
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

          {/* DATOS CRUDOS */}

          <Paper
            sx={{
              p: 3
            }}
          >
            <Typography
              variant="h5"
              gutterBottom
            >
              Datos Crudos
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