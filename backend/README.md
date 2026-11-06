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
    mvn spring-boot:run -Dspring-boot.run.profiles=dev

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


formato torque:
{"eml":"torquefer@yopmail.com", "v":"9", "session":"1793936866390", "id":"4ed0e83821ad6243950e3577e0e38dbf", "time":"1793937303846", "kff1005":"-58.43234390780593", "kff1006":"-34.61312503672552", "kff1001":"0.8132131361110169", "kff1007":"17.73611831665039"}