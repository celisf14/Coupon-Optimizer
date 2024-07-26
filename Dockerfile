# Usa una imagen base que tenga OpenJDK 17
FROM openjdk:17-jdk-alpine AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y el directorio src para construir la aplicación
COPY pom.xml .
COPY src ./src

# Descarga las dependencias y construye la aplicación
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw package -DskipTests

# Usa una imagen base más ligera para el entorno de ejecución
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR construido en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Define el comando por defecto para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]