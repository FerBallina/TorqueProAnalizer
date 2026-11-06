import { useState } from "react";
import { api } from "../services/api";
import { TorqueLogResponse } from "../models/TorqueLogResponse";

export function useTorqueLog() {
  const [loading, setLoading] = useState(false);
  const [log, setLog] = useState<TorqueLogResponse | null>(null);
  const [error, setError] = useState<string | null>(null);

  // Función existente para subir archivos CSV
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

  // NUEVA FUNCIÓN: Obtener datos de la DB por ID
  async function loadById(id: string) {
    try {
      setLoading(true);
      setError(null);

      // Recordá cambiar a `/log/liveData/${id}` si tu API no usa query params (?id=...)
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

  return {
    loading,
    error,
    log,
    upload,
    loadById
  };
}
