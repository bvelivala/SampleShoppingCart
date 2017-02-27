#! /bin/sh

echo $1
export PGDATABASE=sonardb
export PGUSER=sonar
export PGPASSWORD=sonar
sudo $1/psql -f "/tmp/postgres_scripts.sql"
