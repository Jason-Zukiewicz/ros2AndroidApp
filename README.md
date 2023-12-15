Installation Steps:
1. Download APK or build project in Android Studio
2. Install ROS2 onto the robot: https://docs.ros.org/en/humble/index.html
3. Install ROS2 MQTT Client package: https://github.com/ika-rwth-aachen/mqtt_client/tree/main
4. Install ROS2 translateNode package and build
```colcon build --packages-select translateNode```
5. Source the new setup
```source install/setup.bash```

To install onto a pc and run turtlesim:
```
source /opt/ros/$DISTRO/setup.bash
cd tranlateNode
colcon build
source install/setup.bash
sudo su
ros2 run translateNode translater
```


To install onto robot:
```
source /opt/ros/$DISTRO/setup.bash
cd tNode
colcon build
source install/setup.bash
sudo su
ros2 run tNode translater
```