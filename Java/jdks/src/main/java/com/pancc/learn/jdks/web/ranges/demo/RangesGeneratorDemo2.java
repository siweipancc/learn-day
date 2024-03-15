package com.pancc.learn.jdks.web.ranges.demo;

import com.pancc.learn.jdks.web.ranges.Range;
import com.pancc.learn.jdks.web.ranges.RangesGenerator;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * 使用流将 Range 传入线程闭包.
 *
 * @author Siweipancc
 */
public class RangesGeneratorDemo2 {
    void main() throws InterruptedException {
        Stream<Range> rangeStream = RangesGenerator.createOrderedRangeStream(1, 1_000_000);
        LongAdder adder = new LongAdder();
        try (StructuredTaskScope.ShutdownOnSuccess<Object> scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {
            rangeStream.forEach(_ -> scope.fork(() -> {
                adder.increment();
                return null;
            }));
            scope.join();
        }
        System.out.println(adder.sum());
        TimeUnit.HOURS.sleep(1);
    }
}
