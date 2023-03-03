FROM ubuntu:latest
#EXPOSE 8080
LABEL authors="pavel.larin"

ENTRYPOINT ["top", "-b"]
