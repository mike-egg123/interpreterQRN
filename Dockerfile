FROM gcc:10
WORKDIR /app/
COPY ./* ./
RUN gcc -std=c99 word_analysis.c -o program
RUN chmod +x program