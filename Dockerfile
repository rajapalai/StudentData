FROM openjdk:8
EXPOSE 8989
ADD /target/devops-integration.jar devops-integration.jar
CMD [ "java", "-jar", "/devops-integration.jar" ]