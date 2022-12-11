FROM maven:3.6.1-jdk-11
VOLUME [ "/tmp/.X11-unix:/tmp/.X11-unix", "$HOME/.Xauthority:/root/.Xauthority"]

WORKDIR /app
COPY . /app

RUN apt update \
  && apt install -y openjfx \
  && CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/ext/jfxrt.jar \
  && CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/jfxswt.jar \
  && export CLASSPATH

RUN mvn clean compile

CMD mvn exec:java
