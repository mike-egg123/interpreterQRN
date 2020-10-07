FROM openjdk:8
WORKDIR /app/
COPY ./* ./
RUN javac program.java