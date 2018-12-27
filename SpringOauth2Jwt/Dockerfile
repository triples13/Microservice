FROM fabric8/java-jboss-openjdk8-jdk:1.4.0

ADD target/OPCUAclient-0.0.1-SNAPSHOT.jar opcuaclient.jar

EXPOSE 8080

ENTRYPOINT exec java -jar opcuaclient.jar