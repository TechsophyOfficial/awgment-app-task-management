FROM adoptopenjdk/openjdk11
RUN groupadd --gid 1000 spring  && useradd --uid 1000 --gid spring --shell /bin/bash --create-home spring
USER 10001
WORKDIR /home/spring/app
ARG JAR_FILE=build/libs/task-management-0.0.1-SNAPSHOT.jar
COPY build/libs/task-management-0.0.1-SNAPSHOT.jar /home/spring/app/bin/task-management-0.0.1-SNAPSHOT.jar	
ENTRYPOINT ["java", "-jar","/home/spring/app/bin/task-management-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
