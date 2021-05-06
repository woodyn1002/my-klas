#!/bin/sh
docker container rm -f mysqld-exporter
docker run -dp 9104:9104 --name mysqld-exporter --network host --restart always -e DATA_SOURCE_NAME="exporter:myPassw0rd@(localhost:3306)/" prom/mysqld-exporter --collect.info_schema.innodb_metrics
