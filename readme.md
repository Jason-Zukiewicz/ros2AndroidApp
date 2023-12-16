How to start robot 

Terminal 1: Start MQTT_TO_ROS
source /opt/ros/humble/setup.bash
ros2 launch mqtt_client standalone.launch.ros2.xml params_file:=/home/kettic/ros2AndroidApp/tnode/params.ros2.primitive.yaml

Terminal 2: Start Turtle
source /opt/ros/humble/setup.bash
ros2 run turtlesim turtlesim_node

Terminal 3: Start Translater
from dir ros2AndroidApp/tnode
source /opt/ros/humble/setup.bash
source install/setup.bash
ros2 run tnode translater 


ON ROBOT:
run hotspot.sh

