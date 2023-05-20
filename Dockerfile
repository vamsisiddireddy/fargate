FROM ubuntu
MAINTAINER vamsi
RUN apt-get update
RUN apt install -y openjdk-8-jdk
WORKDIR /usr/local/
COPY ./Max-Doctor-PatientApp-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
CMD ["java", "-jar", "Max-Doctor-PatientApp-0.0.1-SNAPSHOT.jar"]


