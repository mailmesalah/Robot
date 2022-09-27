import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
public class Slate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UltrasonicSensor wallWatcher = new UltrasonicSensor(SensorPort.S1);
		UltrasonicSensor obstacleWatcher = new UltrasonicSensor(SensorPort.S2);

		int wallDistance = 0;
		int obstacleDistance=255;
	
		Motor.A.setSpeed(250);
		Motor.B.setSpeed(250);
		
		

			/*
			 * Retrieving Distance of Ultrasonic sensor from Wall(objects)  
			 */
			wallDistance = wallWatcher.getDistance();
			
			/*
			 * Retrieving Distance of Ultrasonic sensor from objects in front of the Robot
			 */
			obstacleDistance = obstacleWatcher.getDistance();
			
			/*
			 * Drawing the Distance from Wall(objects) and the Distance from Obstacle(objects) in front of the robot to the sensor value on LCD
			 */
			LCD.drawString("Distance From Wall: ", 0, 1);
			LCD.drawInt(wallDistance, 4, 5, 2);
			LCD.drawString("Distance From Obstacle: ", 0, 3);
			LCD.drawInt(obstacleDistance, 4, 5, 4);
			LCD.refresh();
			int i=10;
			while(i<=250){
				LCD.drawInt(i, 4, 5, 5);
				LCD.refresh();
				
				Motor.A.setSpeed(i);
				Motor.B.setSpeed(250);
				Motor.A.forward();
				Motor.B.forward();
				
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				i=i+10;
			}

		
	
	}

}
