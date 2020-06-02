# charging-station-service
FROM anapsix/alpine-java:latest

USER nobody

# Environmet variables
ENV SERVICE_NAME=charging-service \
    MANAGEMENT_PORT=8081 \
    SERVICE_PORT=8080 \
    JAVA_OPTS="-XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=20 -Xms64m -Xmx150m"

EXPOSE ${SERVICE_PORT} ${MANAGEMENT_PORT}

WORKDIR "app"

# Healthcheck while running docker container
HEALTHCHECK --interval=1m --timeout=5s --start-period=5m \
  CMD curl -f http://localhost:${MANAGEMENT_PORT}/system/health || exit 1

# Parameter
ARG JAR_FILE
ARG JAR_VERSION
ARG BUILD_DATETIME

ADD ${JAR_FILE} app.jar
ENTRYPOINT exec java  $JAVA_OPTS -jar app.jar

LABEL vendor=emphasize-it \
      build_DateTime=${BUILD_DATETIME} \
      service=${SERVICE_NAME} \
      version=${JAR_VERSION} \
      maintainer=${TEAM} \
      startingFile=${JAR_FILE} \
