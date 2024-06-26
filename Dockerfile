FROM gradle:7.6.3-jdk17-alpine

WORKDIR /app

COPY . /app
USER root
RUN chown -R gradle /app
USER gradle

RUN ./gradlew clean build

COPY src ./src

EXPOSE 8080

CMD ["java", "-jar", "build/libs/fastfood-0.0.1-SNAPSHOT.jar"]