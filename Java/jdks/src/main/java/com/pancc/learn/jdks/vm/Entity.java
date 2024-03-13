package com.pancc.learn.jdks.vm;

import java.time.Instant;

/**
 * @author Siweipancc
 */
public record Entity(long id, String name, Instant createAt) {
}
