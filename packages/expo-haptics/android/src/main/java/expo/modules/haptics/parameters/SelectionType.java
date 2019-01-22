package expo.modules.haptics.parameters;

public class SelectionType implements VibrationType {
  private static final long[] timings = { 0, 100 };
  private static final int[] amplitudes = { 0, 100 };
  private static final long[] oldSDKPattern = { 0, 70 };

  @Override
  public long[] getTimings() {
    return timings;
  }

  @Override
  public int[] getAmplitudes() {
    return amplitudes;
  }

  @Override
  public long[] getOldSDKPattern() {
    return oldSDKPattern;
  }
}
