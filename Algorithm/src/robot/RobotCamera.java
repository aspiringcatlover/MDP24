package robot;
import static main.Constants.*;

public class RobotCamera {
	
	private float angle; //from ground
	
	//constructor
	public RobotCamera() {
		angle = ANGLE;
	}
	
	// getters and setters
	public void setAngle(float angle) {
		this.angle = angle;
	}
		
	public float getAngle() {
		return angle;
	}
}
