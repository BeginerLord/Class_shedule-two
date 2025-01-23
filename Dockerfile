# Etapa 1: Compilacion

# Usa la imagen base de Eclipse Temurin con JDK 17
FROM eclipse-temurin:17.0.11_9-jdk AS build

WORKDIR /root

# Copiar archivos de config de Maven y el archivo pom.xml
COPY ./pom.xml ./
COPY .mvn/ .mvn
COPY mvnw ./

# Otorgar permisos de ejecucion al script mvnw
RUN chmod +x mvnw

# Descargar dependencias necesarias
RUN ./mvnw dependency:go-offline

# Copiar el codigo fuente y construir la aplicacion
COPY ./src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:17.0.11_9-jdk
WORKDIR /root

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /root/target/Class_shedule-0.0.1-SNAPSHOT.jar /root/target/Class_shedule-0.0.1-SNAPSHOT.jar


# Define el puerto que expondra la aplicacion
EXPOSE 8080


# Define el comando que se ejecutará cuando inicie el contenedor
ENTRYPOINT ["java","-jar","/root/target/Class_shedule-0.0.1-SNAPSHOT.jar"]