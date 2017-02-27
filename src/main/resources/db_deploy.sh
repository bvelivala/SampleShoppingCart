#! /bin/sh

echo $1
export PGDATABASE=sonardb
export PGUSER=sonar
export PGPASSWORD=sonar
$1/psql -f "/var/lib/jenkins/workspace/demo_insdevops/postgres_scripts.sql"
