
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

public class RobotVersion2 {
	private final static int minimumDistance = 6;	
	private final static int requiredDistanceLowerLimit = 10;
	private final static int requiredDistanceUpperLimit = 12;
	private final static int maximumDistance = 35;
	
	private final static int aboutToCollide = 8;	
	private final static int obstacleFound = 12;
	
	private final static int SlowSpeed = 50;	
	private final static int SlowerSpeed = 120;
	private final static int NormalSpeed = 200;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		UltrasonicSensor wallWatcher = new UltrasonicSensor(SensorPort.S1);
		UltrasonicSensor obstacleWatcher = new UltrasonicSensor(SensorPort.S2);

		int wallDistance = 0;
		int obstacleDistance=255;
	
		Motor.A.setSpeed(NormalSpeed);
		Motor.B.setSpeed(NormalSpeed);
		while (!Button.ESCAPE.isDown()) {
			/*
			 * Stop to read a proper distance
			 */
			//Motor.B.stop();
			//Motor.A.stop();
			
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
			
			if(obstacleDistance<aboutToCollide){
				/*
				 *  Robot find an obstacle that is so near, so it should stop and make sound 
				 */
				Motor.B.stop();
				Motor.A.stop();
				int count=0;
				while(count<5){
					Sound.beep();
					Thread.sleep(500);
					++count;
				}
				System.exit(0);
								
			}
			
			if(wallDistance<=maximumDistance && obstacleDistance<=obstacleFound){
				/*
				 * Wall is present and an obstacle is found, then turn Right.
				 */
				while(obstacleDistance<=obstacleFound && !Button.ESCAPE.isDown()){
					Motor.B.setSpeed(NormalSpeed);
					Motor.A.setSpeed(NormalSpeed);
					Motor.B.forward();
					Motor.A.backward();
					obstacleDistance = obstacleWatcher.getDistance();
					
					if(obstacleDistance<aboutToCollide){
						/*
						 *  Robot find an obstacle that is so near, so it should stop and make sound 
						 */
						Motor.B.stop();
						Motor.A.stop();
						int count=0;
						while(count<5){
							Sound.beep();
							Thread.sleep(500);
							++count;
						}
						System.exit(0);
										
					}
				}
				
				/*
				 * Exit on Escape button is clicked
				 */
				if(Button.ESCAPE.isDown())System.exit(0);
				
				wallDistance = wallWatcher.getDistance();
				while(wallDistance>requiredDistanceUpperLimit && !Button.ESCAPE.isDown()){
					Motor.B.forward();
					Motor.A.backward();
					wallDistance = wallWatcher.getDistance();
					
					obstacleDistance = obstacleWatcher.getDistance();					
					if(obstacleDistance<aboutToCollide){
						/*
						 *  Robot find an obstacle that is so near, so it should stop and make sound 
						 */
						Motor.B.stop();
						Motor.A.stop();
						int count=0;
						while(count<5){
							Sound.beep();
							Thread.sleep(500);
							++count;
						}
						System.exit(0);
										
					}
				}
				
				/*
				 * Exit on Escape button is clicked
				 */
				if(Button.ESCAPE.isDown())System.exit(0);
			}
			
			else if(wallDistance>maximumDistance){
				/*
				 *  Robot is far from Wall means no wall on Left, should go forward a little and turn left till it find a Wall
				 */
				Motor.B.setSpeed(NormalSpeed);
				Motor.A.setSpeed(NormalSpeed);
				Motor.B.forward();
				Motor.A.forward();
				Thread.sleep(500);
				Motor.B.setSpeed(SlowSpeed);
				Motor.A.setSpeed(NormalSpeed);
				Motor.B.forward();
				Motor.A.forward();
				Thread.sleep(2000);
				Motor.B.setSpeed(NormalSpeed);
				Motor.B.forward();
				Motor.A.forward();
				Thread.sleep(500);
				/*wallDistance = wallWatcher.getDistance();
				/while(wallDistance>requiredDistanceUpperLimit && !Button.ESCAPE.isDown()){
					Motor.B.setSpeed(SlowSpeed);
					Motor.A.setSpeed(NormalSpeed);
					Motor.B.forward();
					Motor.A.forward();
					wallDistance = wallWatcher.getDistance();
				}*/
			}
			
			else if(wallDistance>requiredDistanceUpperLimit){
				/*
				 *  Robot is between upper required limit and maximum distance, should turn to upper limit fast.
				 */
				Motor.B.setSpeed(SlowerSpeed);
				Motor.A.setSpeed(NormalSpeed);
				Motor.B.forward();
				Motor.A.forward();				
			}
			
			else if(wallDistance>requiredDistanceLowerLimit){
				/*
				 *  Robot is between upper required limit and Lower limit, should go forward.
				 */
				Motor.B.setSpeed(NormalSpeed);
				Motor.A.setSpeed(NormalSpeed);
				Motor.B.forward();
				Motor.A.forward();				
			}
			
			else if(wallDistance>minimumDistance){
				/*
				 *  Robot is between lower required limit and minimum distance, should turn to lower limit.
				 */
				Motor.B.setSpeed(NormalSpeed);
				Motor.A.setSpeed(SlowerSpeed);
				Motor.B.forward();
				Motor.A.forward();				
			}
			
			else if(wallDistance<minimumDistance){
				/*
				 *  Robot is less than minimum distance, should turn to minimum distance fast.
				 */
				Motor.B.setSpeed(NormalSpeed);
				Motor.A.setSpeed(SlowSpeed);
				Motor.B.forward();
				Motor.A.forward();				
			}
						
			
			/*
			 * Exit on Escape button is clicked
			 */
			if(Button.ESCAPE.isDown())System.exit(0);

		}
	}

}