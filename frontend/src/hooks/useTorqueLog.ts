import { useState } from "react";

import { api }
  from "../services/api";

import { TorqueLogResponse }
  from "../models/TorqueLogResponse";

export function useTorqueLog() {

  const [loading, setLoading] =
    useState(false);

  const [log, setLog] =
    useState<TorqueLogResponse | null>(
      null
    );

  const [error, setError] =
    useState<string | null>(
      null
    );

  async function upload(
    file: File
  ) {

    try {

      setLoading(true);
      setError(null);

      const formData =
        new FormData();

      formData.append(
        "file",
        file
      );

      const response =
        await api.post<TorqueLogResponse>(
          "/log/upload",
          formData,
          {
            headers: {
              "Content-Type":
                "multipart/form-data"
            }
          }
        );

      setLog(
        response.data
      );

    }
    catch (err) {

      console.error(err);

      setError(
        "Error cargando CSV"
      );

    }
    finally {

      setLoading(false);

    }

  }

  return {

    loading,
    error,
    log,
    upload

  };

}