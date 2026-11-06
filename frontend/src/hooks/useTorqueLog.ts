import { useState } from "react";
import { api } from "../services/api";
import { TorqueLogResponse } from "../models/TorqueLogResponse";

// CORREGIDO: Quitamos el 'public' antes de la interfaz
export interface SessionHistoryItem {
  sessionId: string;
  deviceId: string;
  startTime: string;
  endTime: string;
  recordCount: number;
}

export function useTorqueLog() {
  const [loading, setLoading] = useState(false);
  const [log, setLog] = useState<TorqueLogResponse | null>(null);
  const [error, setError] = useState<string | null>(null);
  
  const [history, setHistory] = useState<SessionHistoryItem[]>([]);

  // Subir archivos CSV
  async function upload(file: File) {
    try {
      setLoading(true);
      setError(null);

      const formData = new FormData();
      formData.append("file", file);

      const response = await api.post<TorqueLogResponse>(
        "/log/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      );

      setLog(response.data);
    } catch (err) {
      console.error(err);
      setError("Error cargando CSV");
    } finally {
      setLoading(false);
    }
  }

  // Obtener datos de la DB por ID de Sesión
  async function loadById(id: string) {
    try {
      setLoading(true);
      setError(null);

      const response = await api.get<TorqueLogResponse>(
        `/log/liveData?id=${id}`
      );

      setLog(response.data);
    } catch (err) {
      console.error(err);
      setError("Error al buscar el ID en la base de datos");
    } finally {
      setLoading(false);
    }
  }

  // Consultar lista de sesiones por email y rango de fechas
  async function loadHistory(email: string, fromDate: string, toDate: string) {
    try {
      setLoading(true);
      setError(null);
      setHistory([]); 

      const response = await api.get<SessionHistoryItem[]>(
        `/log/history?email=${email}&fromDate=${fromDate}&toDate=${toDate}`
      );

      setHistory(response.data);
      
      if (response.data.length === 0) {
        setError("No se encontraron sesiones para los criterios ingresados.");
      }
    } catch (err) {
      console.error(err);
      setError("Error al consultar el historial de sesiones");
    } finally {
      setLoading(false);
    }
  }

  return {
    loading,
    error,
    log,
    history,
    upload,
    loadById,
    loadHistory
  };
}
