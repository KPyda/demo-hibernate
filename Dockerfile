FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/demo-hibernate-dirty-checking.jar demo-hibernate-dirty-checking.jar
RUN sh -c 'touch /demo-hibernate-dirty-checking.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /demo-hibernate-dirty-checking.jar" ]