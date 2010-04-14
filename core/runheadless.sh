#!/bin/bash

nohup startx -- `which Xvfb` :20 -screen 0 1024x768x24 & sleep 7
DISPLAY=:20 firefox  &     DISPLAY=:20 mvn test
pkill Xvfb