package com.exercise.listingproject.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ImplFunction {
  // Get the current time in microseconds since epoch
  public static Long nowInEpochMicroSecond() {
    return ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now());
  }
}
