FROM openjdk:9

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/L01.1-maven/L01.1-maven.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/L01.1-maven/L01.1-maven.jar