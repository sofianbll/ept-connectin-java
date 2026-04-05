# STAGE 01
FROM maven:4.0.0-rc-5-eclipse-temurin-25-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q dependency:go-offline

COPY src ./src

# Build + remove -plain.jar to avoid conflict
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q clean package -DskipTests && \
    rm -f target/*-plain.jar

# Stage 02
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
RUN mkdir -p storage/posts storage/avatars
COPY --from=build /app/target/*.jar app.jar

# Add User & UserGroup for security
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]