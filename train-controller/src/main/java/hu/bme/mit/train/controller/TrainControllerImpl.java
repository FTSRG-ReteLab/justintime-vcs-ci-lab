package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hu.bme.mit.train.interfaces.TrainController;

import javax.annotation.Nullable;
import java.util.*;

public class TrainControllerImpl implements TrainController {

	private Table<Date, Integer, Integer> tachy = HashBasedTable.create();
	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Timer timer = new Timer();

	public TrainControllerImpl()
	{
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(step != 0) {
					followSpeed();
				}
			}
		}, 0, 1000);
	}

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
