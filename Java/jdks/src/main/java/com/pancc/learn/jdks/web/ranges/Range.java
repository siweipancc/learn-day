package com.pancc.learn.jdks.web.ranges;

import java.io.Serializable;

/**
 * @author Siweipancc
 */
public record Range(long startInclude, long endInclude) implements Serializable {
}
