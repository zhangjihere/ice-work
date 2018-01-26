#!/usr/bin/env bash

ICE_GRID="icegrid"
echo "Stop IceGridNode and IceGridAdmin service..."
ID=`ps -ef | grep ${ICE_GRID} | awk '{print $2}'`
kill -9 ${ID}

ICE_GRID_NODE="icegridnode"
echo "start another new node in IceGrid"
icegridnode --Ice.Config=config.grid.node &
sleep 2
echo "add advance application"
icegridadmin --Ice.Config=config.grid.node -e "application add application_advance.xml"
echo "add application"
icegridadmin --Ice.Config=config.grid.node -e "application add application_node_2.xml"
sleep 2
echo "enter IceGrid Admin"
icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h 109.105.4.159 -p 4061"
