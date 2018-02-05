#!/usr/bin/env bash

ICE_GRID="icegrid"
ICE_GRID_NODE="icegridnode"

echo "Stop IceGridNode and IceGridAdmin service..."
ID=`ps -ef | grep ${ICE_GRID} | awk '{print $2}'`
kill -9 ${ID}

echo "start slave-registry for IceGrid"
icegridregistry --Ice.Config=config.grid.registry.slave &
sleep 2

echo "start a node in IceGrid"
icegridnode --Ice.Config=config.grid.node2 &
sleep 1

echo "add advance application"
icegridadmin --Ice.Config=config.grid.node2 \
    -e "application add application_advance.xml"
echo "add application"
icegridadmin --Ice.Config=config.grid.node2 \
    -e "application add application_node_2.xml"
sleep 1

echo "enter IceGrid Admin"
icegridadmin -u foo -p bar \
    --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h 109.105.4.159 -p 4061: tcp -h 109.105.5.212 -p 4062"
