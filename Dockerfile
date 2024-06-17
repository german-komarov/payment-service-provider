FROM eclipse-temurin:21
ARG JAR_VERSION
WORKDIR /app
COPY /build/libs/psp-${JAR_VERSION}.jar /app/psp.jar
EXPOSE 8080
#All additional JVM arguments may be passed via JAVA_TOOL_OPTIONS env variable
CMD ["java", "-jar", "/app/psp.jar"]