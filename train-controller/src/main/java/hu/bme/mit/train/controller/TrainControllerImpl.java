package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hu.bme.mit.train.interfaces.TrainController;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class TrainControllerImpl implements TrainController {

	private Table<Date, Integer, Integer> tachy = HashBasedTable.create();
	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.tachy.put(new Date(), joystickPosition, this.referenceSpeed);

		this.step = joystickPosition;		
	}

	@Override
	public Table<Date, Integer, Integer> getTachy() {
		return tachy;
	}

}
