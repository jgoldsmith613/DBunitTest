#!/bin/bash

pwd;

sudo puppet apply mysql-server.pp;
cd ..
mvn test;