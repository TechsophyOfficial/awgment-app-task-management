FROM adoptopenjdk/openjdk11
RUN groupadd --gid 1000 spring  && useradd --uid 1000 --gid spring --shell /bin/bash --create-home spring
USER 10001
WORKDIR /home/spring/app
ARG JAR_FILE=build/libs/awgment-task-mangement-0.0.1.jar
COPY build/libs/awgment-task-mangement-0.0.1.jar /home/spring/app/bin/awgment-task-mangement-0.0.1.jar	
ENTRYPOINT ["java", "-jar","/home/spring/app/bin/awgment-task-mangement-0.0.1.jar"]
EXPOSE 8080
