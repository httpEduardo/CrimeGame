package pl.zycienakodach.kttimetraveler.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import pl.zycienakodach.kttimetraveler.core.ClockTimeProvider;
import pl.zycienakodach.kttimetraveler.core.TimeProvider;

import java.time.Clock;

public class CurrentTimeInitializer implements ApplicationContextInitializer<GenericApplicationContext> {
  private final CurrentTimeProperties properties;

  public CurrentTimeInitializer(CurrentTimeProperties properties) {
    this.properties = properties;
  }

  @Override
  public void initialize(GenericApplicationContext context) {
    final Clock clock = properties.getClock();
    context.registerBean(Clock.class, () -> clock);
    context.registerBean(TimeProvider.class, () -> new ClockTimeProvider(clock));
  }
}
