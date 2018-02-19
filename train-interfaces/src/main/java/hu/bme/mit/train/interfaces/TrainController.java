package hu.bme.mit.train.interfaces;

import com.google.common.collect.Table;

import java.util.Date;

public interface TrainController {

	void followSpeed();

	int getReferenceSpeed();

	void setSpeedLimit(int speedLimit);

	void setJoystickPosition(int joystickPosition);

	Table<Date, Integer, Integer> getTachy();

}
