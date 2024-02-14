FROM amazoncorretto:17-alpine

WORKDIR /app

COPY target/clustered-data-warehouse-0.0.1-SNAPSHOT.jar /app

EXPOSE 8090

CMD ["java", "-jar", "/app/clustereddatawarehouse-0.0.1-SNAPSHOT.jar" ]