#!/bin/sh
docker container rm -f prometheus
docker run -dp 9090:9090 -v /home/ec2-user/prometheus.yml:/etc/prometheus/prometheus.yml --name prometheus --network host --restart always prom/prometheus --web.enable-admin-api
