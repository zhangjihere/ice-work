# README

## run the IceBox single please use follow command
> unzip org.tombear.rpcice.IceGrid-box-1.0.0-SNAPSHOT-bin.zip  
> cd org.tombear.rpcice.IceGrid-box-1.0.0-SNAPSHOT  
> java -cp 'lib/*' 'com.zeroc.IceBox.Server' --Ice.Config=config.iceboxG

## run the IceGrid with IceBox please use follow command
> unzip org.tombear.rpcice.IceGrid-box-1.0.0-SNAPSHOT-bin.zip  
> cd org.tombear.rpcice.IceGrid-box-1.0.0-SNAPSHOT  
> icegridnode --Ice.Config=config.grid
> icegridadmin --Ice.Config=config.grid -e "application add application.xml"
> icegridadmin -u foo -p bar --Ice.Default.Locator="DemoIceGrid/Locator:tcp -h localhost -p 4061"
### Maven package and unzip, then test to run IceGrid with IceBox automatically
> mvn clean package  
run 
> target/org.tombear.rpcice.IceGrid-box-1.0.0-SNAPSHOT/restart_IceGrid.sh
