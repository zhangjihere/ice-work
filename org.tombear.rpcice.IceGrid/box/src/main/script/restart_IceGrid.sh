#!/usr/bin/env bash

ICE_GRID="icegrid"
ICE_GRID_REGISTRY="icegridregistry"

cd ${project.build.directory}/${project.build.finalName}
echo "Stop IceGridNode and IceGridAdmin service..."
#ID=`ps -ef | grep ${ICE_GRID} | awk '{print $2}'`
ID=$(ps -ef | awk '/icegridregistry|icegridnode|IceBox/ {print $2}')
kill -9 ${ID}


echo "start registry for IceGrid"
icegridregistry --Ice.Config=config.grid.registry &
sleep 1

echo "start a node in IceGrid"
nohup  icegridnode --Ice.Config=config.grid.node1 > /dev/null &
sleep 1

echo "add advance application"
icegridadmin -u foo -p bar \
    --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061: tcp -h 109.105.5.212 -p 4062" \
    -e "application add application_advance.xml"
echo "add application"
icegridadmin -u foo -p bar \
    --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061: tcp -h 109.105.5.212 -p 4062" \
    -e "application add application_node_1.xml"
sleep 1

echo "enter IceGrid Admin"
icegridadmin -u foo -p bar \
    --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061: tcp -h 109.105.5.212 -p 4062"
