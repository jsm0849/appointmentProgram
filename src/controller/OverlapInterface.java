package controller;

import java.time.ZonedDateTime;

public interface OverlapInterface {
    boolean checkForOverlap(ZonedDateTime start, ZonedDateTime end);
}
