# TorqueProAnalizer
backend
│
├── pom.xml
│
├── src/main/java
│   └── com/example/torqueanalyzer
│
│       ├── TorqueAnalyzerApplication.java
│       │
│       ├── config
│       │   ├── TorqueProperties.java
│       │   └── ThresholdConfig.java
│       │
│       ├── controller
│       │   └── LogController.java
│       │
│       ├── dto
│       │   ├── TorqueLogResponse.java
│       │   ├── ChartResponse.java
│       │   └── ColumnDefinition.java
│       │
│       ├── model
│       │   ├── TorqueLog.java
│       │   ├── TorqueRecord.java
│       │   └── Threshold.java
│       │
│       ├── parser
│       │   └── TorqueCsvParser.java
│       │
│       ├── service
│       │   ├── TorqueLogService.java
│       │   └── EventDetectionService.java
│       │
│       └── plugin
│           └── AnalyzerPlugin.java
│
└── src/main/resources
    └── application.yml



    POST /api/log/upload


    curl -F file=@trackLog.csv \
    http://localhost:8080/api/log/upload


    sudo lsof -i :8080

    mvn clean install
    mvn spring-boot:run

    http://localhost:8080/swagger-ui.html

JSON de respuesta

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