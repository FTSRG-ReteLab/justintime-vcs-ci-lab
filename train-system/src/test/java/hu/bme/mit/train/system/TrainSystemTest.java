package hu.bme.mit.train.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingSpeedLimit_SetsSpeedToSpeedLimit() {
		controller.setSpeedLimit(10);
		user.overrideJoystickPosition(10);
		controller.followSpeed();

		Assert.assertEquals(10, controller.getReferenceSpeed());

		controller.setSpeedLimit(5);
		Assert.assertEquals(5, controller.getReferenceSpeed());
	}

	@Test
	public void DuringNormalUsage_TachyWorks() {
		sensor.overrideSpeedLimit(50);
		user.overrideJoystickPosition(10);
		controller.followSpeed();
		controller.followSpeed();
		controller.followSpeed();
		user.overrideJoystickPosition(-3);
		controller.followSpeed();
		controller.followSpeed();
		user.overrideJoystickPosition(0);

		Assert.assertEquals(false, controller.getTachy().isEmpty());
	}
}
