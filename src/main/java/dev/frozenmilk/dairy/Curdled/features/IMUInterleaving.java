package dev.frozenmilk.dairy.Curdled.features;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import dev.frozenmilk.dairy.core.Feature;
import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;

public class IMUInterleaving implements Feature {
	private Dependency<?> dependency = new SingleAnnotation<>(Attach.class);
	
	@NonNull
	@Override
	public Dependency<?> getDependency() { return dependency; }
	
	@Override
	public void setDependency(@NonNull Dependency<?> dependency) {
		this.dependency = dependency;
	}
	
	private IMUInterleaving() {}
	public static final IMUInterleaving INSTANCE = new IMUInterleaving();
	private static List<LynxModule> allHubs;
	public YawPitchRollAngles imuRead;

	private void getBulkRead(){ allHubs.get(0).getBulkData(); }

	private void imuShit1(){
		//do the first part of the imu shit
	}
	
	private void imuShit2(){
		//do the second part of the imu shit
	}

	private void getIMUBulkInterleaved(){
		imuShit1();
		getBulkRead();
		imuShit2();
	}

	@Override
	public void preUserInitHook(@NotNull Wrapper opMode) {}

	@Override
	public void postUserInitHook(@NotNull Wrapper opMode) {
		allHubs = opMode.getOpMode().hardwareMap.getAll(LynxModule.class);
		for (LynxModule hub : allHubs) { hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL); }
	}

	@Override
	public void preUserInitLoopHook(@NotNull Wrapper opMode) { getIMUBulkInterleaved(); }

	@Override
	public void preUserStartHook(@NotNull Wrapper opMode) { getIMUBulkInterleaved(); }

	@Override
	public void preUserLoopHook(@NotNull Wrapper opMode) { getIMUBulkInterleaved(); }
	
	@Override
	public void preUserStopHook(@NotNull Wrapper opMode) { getIMUBulkInterleaved(); }
	
	@Override
	public void cleanup(@NonNull Wrapper opMode) { allHubs.clear(); }
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public @interface Attach {}
}