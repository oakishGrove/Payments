FROM bellsoft/liberica-runtime-container:jdk-21-stream-musl as builder
#FROM bellsoft/liberica-runtime-container as builder

WORKDIR /app
#COPY . /app/backend

# Set working directory
#WORKDIR /app

# Copy gradle wrapper files
COPY gradlew .
COPY gradlew.bat .
COPY gradle/wrapper /app/gradle/wrapper

# Copy project files
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Give executable permission to gradlew
RUN chmod +x gradlew

# Run build (optional)
RUN ./gradlew bootJar
#RUN ./gradlew build


#RUN cd backend && ./gradlew clean build

#FROM bellsoft/liberica-runtime-container as optimizer
FROM bellsoft/liberica-runtime-container:jdk-21-stream-musl as optimizer

WORKDIR /app
#COPY --from=builder /home/app/backend/target/*.jar app.jar
#RUN ls -a
#RUN cd app && ls
#COPY --from=builder /build/libs/*.jar app.jar
COPY --from=builder /app/target/*.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --launcher


#FROM bellsoft/liberica-runtime-container:jre-17-stream-musl as final
FROM bellsoft/liberica-runtime-container

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
COPY --from=optimizer /app/dependencies/ ./
COPY --from=optimizer /app/spring-boot-loader/ ./
COPY --from=optimizer /app/snapshot-dependencies/ ./
COPY --from=optimizer /app/application/ ./