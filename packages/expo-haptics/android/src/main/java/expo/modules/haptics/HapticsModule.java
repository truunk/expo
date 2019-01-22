package expo.modules.haptics;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import expo.core.ExportedModule;
import expo.core.ModuleRegistry;
import expo.core.Promise;
import expo.core.interfaces.ExpoMethod;
import expo.core.interfaces.ModuleRegistryConsumer;
import expo.modules.haptics.parameters.ImpactType;
import expo.modules.haptics.parameters.NotificationType;
import expo.modules.haptics.parameters.SelectionType;
import expo.modules.haptics.parameters.VibrationType;

public class HapticsModule extends ExportedModule implements ModuleRegistryConsumer {
  private static final String TAG = "ExpoHaptics";
  private static final String ERROR_TAG = "E_HAPTICS";
  private final Vibrator mVibrator;

  HapticsModule(Context context) {
    super(context);
    mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
  }

  @Override
  public String getName() {
    return TAG;
  }

  @Override
  public void setModuleRegistry(ModuleRegistry moduleRegistry) {}

  @ExpoMethod
  public void notificationAsync(String type, Promise promise) {
    try {
      vibrate(NotificationType.fromString(type));
      promise.resolve(null);
    } catch (IllegalArgumentException e) {
      promise.reject(ERROR_TAG + "_INVALID_ARG", e);
    }
  }

  @ExpoMethod
  public void selectionAsync(Promise promise) {
    vibrate(new SelectionType());
    promise.resolve(null);
  }

  @ExpoMethod
  public void impactAsync(String style, Promise promise) {
    try {
      vibrate(ImpactType.fromString(style));
      promise.resolve(null);
    } catch (IllegalArgumentException e) {
      promise.reject(ERROR_TAG + "_INVALID_ARG", e);
    }
  }

  private void vibrate(VibrationType type) {
    vibrate(type.getTimings(), type.getAmplitudes(), type.getOldSDKPattern());
  }

  private void vibrate(long[] timings, int[] amplitudes, long[] oldSDKPattern) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      mVibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1));
    } else {
      mVibrator.vibrate(oldSDKPattern, -1);
    }
  }
}
