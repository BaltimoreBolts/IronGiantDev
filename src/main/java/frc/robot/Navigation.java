/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 * Add your docs here.
 */
public class Navigation {
    DifferentialDrive driveTrain;

	public Navigation(DifferentialDrive initDriveTrain, Encoder leftEncoder, Encoder rightEncoder) {
        this.driveTrain = initDriveTrain;
        
       
    }
    public boolean turn(double degrees, double heading, Direction direction, boolean debug) {
		double absHeading = Math.abs(heading);
		double remaining = degrees - absHeading;

		/**
		 * We want our top end turn speed to be about 0.8 and the motors stall out at
		 * about 0.4, so we calculate the remaining turn percentage and multiply it by
		 * 0.4, then add 0.4 to it so that our motor power range goes from 0.8 (when
		 * turn started) to 0.4 (when turn complete)`
		 */
		double power = ((remaining / degrees) * 0.5) + 0.4;
		
		if (absHeading < degrees) {
			
			if (direction == Direction.COUNTERCLOCKWISE) {
				this.driveTrain.tankDrive(-power, power);
			} else if (direction == Direction.CLOCKWISE) {
				this.driveTrain.tankDrive(power, -power);
			}
			
			return true; // still turning
		} else {
			return false; // no longer turning
		}
		
		
	}
	
	public boolean straight(double goal, double traveled, Direction direction) {
		double remaining = goal - traveled;
		double power = ((remaining / goal) * 0.3) + 0.4;
		
		if (traveled < goal && remaining > 2) {
			if (direction == Direction.FORWARD) {
				this.driveTrain.tankDrive(-power, -power);
			} else if (direction == Direction.REVERSE) {
				this.driveTrain.tankDrive(power, power);
			}
			return false;
		} else {
			return true;
		}
	}
	
	public void arcade(double x, double y) {
		this.driveTrain.arcadeDrive(y, x);
	}
}

