package expo.modules.haptics.parameters;

public interface VibrationType {
  long[] getTimings();

  int[] getAmplitudes();

  long[] getOldSDKPattern();
}
