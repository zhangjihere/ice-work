#!/usr/bin/env bash

ICE_GRID="icegrid"
ICE_GRID_REGISTRY="icegridregistry"
ICE_GRID_NODE="icegridnode"

cd ${project.build.directory}/${project.build.finalName}
echo "Stop IceGridNode and IceGridAdmin service..."
ID=`ps -ef | grep ${ICE_GRID} | awk '{print $2}'`
kill -9 ${ID}

echo "start registry for IceGrid"
icegridregistry --Ice.Config=config.grid.registry &
sleep 2

echo "start a node in IceGrid"
icegridnode --Ice.Config=config.grid.node1 &
sleep 2

echo "add advance application"
icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061" \
        -e "application add application_advance.xml"
echo "add application"
icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061" \
        -e "application add application_node_1.xml"
sleep 1

echo "enter IceGrid Admin"
icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061"
