package com.pancc.learn.jdks.web.ranges.demo;

import com.pancc.learn.jdks.web.ranges.Range;
import com.pancc.learn.jdks.web.ranges.RangesGenerator;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * 使用提前缓存大量对象方法.
 * <p>
 * JDK 21 8位对齐每个 Range 占用大小 12+4+8+8=32 字节
 * <p>
 * 100w 个对象缓存到 java.util.ImmutableCollections$ListN 12+1+3+8
 * <p>
 * 总计 32M.24KB 内存
 *
 * @author Siweipancc
 */
public class RangesGeneratorDemo1 {
    void main() throws InterruptedException {
        //
        Stream<Range> rangeStream = RangesGenerator.createOrderedRangeStream(1, 1_000_000);
        //
        //
        List<Range> ranges = rangeStream.toList();
        LongAdder adder = new LongAdder();
        try (StructuredTaskScope.ShutdownOnSuccess<Object> scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {
            for (Range _ : ranges) {
                scope.fork(() -> {
                    adder.increment();
                    return null;
                });
            }
            scope.join();
        }
        System.out.println(adder.sum());
        TimeUnit.HOURS.sleep(1);
    }
}
