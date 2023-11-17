import rclpy
from rclpy.node import Node

from std_msgs.msg import String
from std_msgs.msg import Int32
from geometry_msgs.msg import Twist


class MinimalSubscriber(Node):

	def __init__(self):
		super().__init__('minimal_subscriber')
		self.subscription = self.create_subscription(
			Int32,
			'topic',
			self.listener_callback,
			10)
		self.subscription  # prevent unused variable warning
		self.turtleMove = self.create_publisher(Twist, 'turtle1/cmd_vel', 10)

	def listener_callback(self, msg):
		self.get_logger().info('I heard: "%s"' % msg.data)
		twistMsg = Twist()

		if msg.data == 12:
			twistMsg.angular.z = 0.0;
			twistMsg.linear.x = 2.0;
		elif msg.data == 3:
			twistMsg.angular.z = -2.0;
			twistMsg.linear.x = 0.0;
		elif msg.data == 6:
			twistMsg.angular.z = 0.0;
			twistMsg.linear.x = -2.0;
		elif msg.data == 9:
			twistMsg.angular.z = 2.0;
			twistMsg.linear.x = 0.0;
		else:
			twistMsg.angular.z = 0.0;
			twistMsg.linear.x = 0.0;

		self.turtleMove.publish(twistMsg)



def main(args=None):
	rclpy.init(args=args)

	minimal_subscriber = MinimalSubscriber()

	rclpy.spin(minimal_subscriber)

	# Destroy the node explicitly
	# (optional - otherwise it will be done automatically
	# when the garbage collector destroys the node object)
	minimal_subscriber.destroy_node()
	rclpy.shutdown()


if __name__ == '__main__':
	main()
