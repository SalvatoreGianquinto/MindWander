# Usa Java 17 (Eclipse Temurin)
FROM eclipse-temurin:17-jdk

# Imposta la working directory
WORKDIR /app

# Copia tutti i file del progetto
COPY . .

# Rendi eseguibile mvnw
RUN chmod +x mvnw

# Build del progetto
RUN ./mvnw clean package -DskipTests

# Espone la porta di Spring Boot
EXPOSE 8080

# Comando di avvio
CMD ["java", "-jar", "target/Mindwander-0.0.1-SNAPSHOT.jar"]
