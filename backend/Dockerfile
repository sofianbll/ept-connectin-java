# On utilise une image allégée avec le JDK Java 25
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# On copie les fichiers de configuration Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn

# On donne les droits d'exécution au script Maven
RUN chmod +x mvnw

# On copie le code source
COPY src ./src

# Run app -> spring-boot:run
CMD ["./mvnw", "spring-boot:run"]
