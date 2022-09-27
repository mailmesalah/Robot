import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Robot {	
	private final static int minimumDistance = 8;	
	private final static int requiredDistance = 13;
	private final static int maximumDistance = 18;
	
	private final static int obstacleFound = 25;
	

	public static void main(String[] aArg) throws Exception {
		UltrasonicSensor wallWatcher = new UltrasonicSensor(SensorPort.S1);
		UltrasonicSensor obstacleWatcher = new UltrasonicSensor(SensorPort.S2);

		int wallDistance = 0;
		int obstacleDistance=255;
	
		Motor.A.setSpeed(250);
		Motor.B.setSpeed(250);
		
		while (!Button.ESCAPE.isDown()) {

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
			

			if(wallDistance<maximumDistance && obstacleDistance<=obstacleFound){
				/*
				 * Greater than Track but less than Maximum Distance
				 * Should Turn Left to Track
				 */
				while(obstacleDistance<=obstacleFound){
					Motor.A.stop();
					Motor.B.forward();
					obstacleDistance = obstacleWatcher.getDistance();
				}
				wallDistance=wallWatcher.getDistance();
				while(wallDistance<=requiredDistance){
					Motor.A.stop();
					Motor.B.forward();
					wallDistance = wallWatcher.getDistance();
				}
			}
			else if(wallDistance<minimumDistance){
				/*
				 * Near To Wall
				 * Should Turn Right and Go Forward
				 */
				Motor.A.stop();
				Motor.B.forward();
				//Thread.sleep(50);
				Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(100);
			}else if(wallDistance<requiredDistance){
				/*
				 * Less than Required Distance(Track) but Near to Track
				 * Should Turn Right to Track
				 */
				Motor.A.stop();
				Motor.B.forward();
			}else if(wallDistance<maximumDistance){
				/*
				 * Greater than Track but less than Maximum Distance
				 * Should Turn Left to Track
				 */
				Motor.A.forward();
				Motor.B.stop();
			}else if(wallDistance>maximumDistance){
				/*
				 * Greater than Maximum Distance
				 * Should Turn Left and Go Forward
				 */
				Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(1000);
				wallDistance = wallWatcher.getDistance();
				while(wallDistance>requiredDistance){
					Motor.A.forward();
					Motor.B.stop();
					wallDistance = wallWatcher.getDistance();
				}
				
				//Thread.sleep(50);				
			}
		}
	}

}