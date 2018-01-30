#!/usr/bin/env bash

cd ${project.build.directory}/${project.build.finalName}
ICE_GRID="icegrid"
echo "Stop IceGridNode and IceGridAdmin service..."
ID=`ps -ef | grep ${ICE_GRID} | awk '{print $2}'`
kill -9 ${ID}

ICE_GRID_NODE="icegridnode"
echo "start IceGrid with a new node"
icegridnode --Ice.Config=config.grid &
sleep 2
echo "add advance application"
icegridadmin --Ice.Config=config.grid -e "application add application_advance.xml"
echo "add application"
icegridadmin --Ice.Config=config.grid -e "application add application_node_1.xml"
sleep 2
echo "enter IceGrid Admin"
icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061"
