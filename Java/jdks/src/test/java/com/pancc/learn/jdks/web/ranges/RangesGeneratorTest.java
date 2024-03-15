package com.pancc.learn.jdks.web.ranges;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

import static com.pancc.learn.jdks.web.ranges.RangesGenerator.createOrderedRangeStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Siweipancc
 */
class RangesGeneratorTest {

    @Test
    @DisplayName("参数检查")
    void t0() {
        assertThrows(IllegalArgumentException.class, () -> createOrderedRangeStream(0, 0));
        assertThrows(IllegalArgumentException.class, () -> createOrderedRangeStream(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> createOrderedRangeStream(0, -1));
        assertThrows(IllegalArgumentException.class, () -> createOrderedRangeStream(0, -1));
        assertThrows(IllegalArgumentException.class, () -> createOrderedRangeStream(0, 1));
        assertDoesNotThrow(() -> createOrderedRangeStream(1, 1));
    }

    @Test
    @DisplayName("int 长度数组")
    void t1() {
        Assertions.assertThat(createOrderedRangeStream(1, 1)).singleElement().isEqualTo(new Range(0, 0));

        Assertions.assertThat(createOrderedRangeStream(2, 1)).singleElement().isEqualTo(new Range(0, 0));
        Assertions.assertThat(createOrderedRangeStream(3, 1)).singleElement().isEqualTo(new Range(0, 0));


        Assertions.assertThat(createOrderedRangeStream(1, 1)).singleElement().isEqualTo(new Range(0, 0));
        Assertions.assertThat(createOrderedRangeStream(2, 2)).singleElement().isEqualTo(new Range(0, 1));
        Assertions.assertThat(createOrderedRangeStream(3, 3)).singleElement().isEqualTo(new Range(0, 2));

        Assertions.assertThat(createOrderedRangeStream(4, 4)).singleElement().isEqualTo(new Range(0, 3));
        Assertions.assertThat(createOrderedRangeStream(4, 5)).containsExactlyInAnyOrder(new Range(0, 3), new Range(4, 4));
        Assertions.assertThat(createOrderedRangeStream(4, 6)).containsExactlyInAnyOrder(new Range(0, 3), new Range(4, 5));
        Assertions.assertThat(createOrderedRangeStream(4, 7)).containsExactlyInAnyOrder(new Range(0, 3), new Range(4, 6));
        Assertions.assertThat(createOrderedRangeStream(4, 8)).containsExactlyInAnyOrder(new Range(0, 3), new Range(4, 7));
        Assertions.assertThat(createOrderedRangeStream(4, 9)).containsExactlyInAnyOrder(new Range(0, 3), new Range(4, 7), new Range(8, 8));
    }

    /**
     * @see OutOfMemoryError
     */
    @Test
    @DisplayName("long array 溢出")
    void t2() {
        Stream<Range> stream = createOrderedRangeStream(1, Integer.MAX_VALUE + 4L);
        // 存储到列表将导致 OutOfMemoryError
        // List<Range> collected = stream.toList();

        long count = stream.count();
        assertThrows(ArithmeticException.class, () -> Math.toIntExact(count));

        // 不可重复读
        stream = createOrderedRangeStream(1, Integer.MAX_VALUE + 4L).parallel();


        // 大量数据并发下避免使用一般原子类
        LongAdder adder = new LongAdder();
        stream.forEach(_ -> adder.add(1));

    }

}