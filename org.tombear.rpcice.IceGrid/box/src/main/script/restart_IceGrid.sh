#!/usr/bin/env bash

cd /home/zhangji/projects_repo/ice-work/org.tombear.rpcice.IceGrid/box/target/org.tombear.rpcice.IceGrid-box-1.0.0-SNAPSHOT
ICE_GRID="icegrid"
echo "Stop IceGridNode and IceGridAdmin service..."
ID=`ps -ef | grep ${ICE_GRID} | awk '{print $2}'`
kill -9 ${ID}

ICE_GRID_NODE="icegridnode"
echo "start icegrid with node"
icegridnode --Ice.Config=config.grid &
sleep 2
echo "add application"
icegridadmin --Ice.Config=config.grid -e "application add application.xml"
sleep 2
echo "enter admin"
icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061"
