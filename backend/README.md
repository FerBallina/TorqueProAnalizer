# TorqueProAnalizer

    POST /api/log/upload


    curl -F file=@trackLog.csv \
    http://localhost:8080/api/log/upload


    sudo lsof -i :8080

    mvn clean install
    mvn spring-boot:run -Dspring-boot.run.profiles=dev

    http://localhost:8080/swagger-ui.html



JSON de respuesta:
{
  "config": {
    "rpmField": "Engine RPM(rpm)",
    "speedField": "GPS Speed (Meters/second)",
    "coolantField": "Engine Coolant Temperature(°C)",
    "latitudeField": "Latitude",
    "longitudeField": "Longitude"
  },
  "columns": [
    {
      "position": 0,
      "name": "GPS Time"
    },
    {
      "position": 1,
      "name": "Engine RPM(rpm)"
    }
  ],
  "records": [
    {
      "Engine RPM(rpm)": "950",
      "GPS Speed (Meters/second)": "0.0",
      "Latitude": "-34.6037",
      "Longitude": "-58.3816"
    }
  ],
  "events": [
    {
      "row": 152,
      "sensor": "Engine Coolant Temperature(°C)",
      "value": 106.3,
      "threshold": 105.0,
      "severity": "HIGH"
    }
  ]
}


formato torque live:
{"eml":"torquefer@yopmail.com", "v":"9", "session":"1793936866390", "id":"4ed0e83821ad6243950e3577e0e38dbf", "time":"1793937303846", "kff1005":"-58.43234390780593", "kff1006":"-34.61312503672552", "kff1001":"0.8132131361110169", "kff1007":"17.73611831665039"}


insert en postgres para la primera vez (para cuando tengo ddl-auto: none en lugar de auto):
CREATE TABLE IF NOT EXISTS torque_telemetry (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    session_id VARCHAR(255),
    device_time BIGINT,
    all_parameters JSONB
);
-- Un índice opcional para que tus búsquedas por email vuelen de rápido:
CREATE INDEX IF NOT EXISTS idx_torque_telemetry_email ON torque_telemetry(email);
