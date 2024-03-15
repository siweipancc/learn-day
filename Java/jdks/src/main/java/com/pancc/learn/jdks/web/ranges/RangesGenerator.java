package com.pancc.learn.jdks.web.ranges;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;

import javax.annotation.Nonnull;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 范围产生器.
 *
 * @author Siweipancc
 */
public class RangesGenerator {


    /**
     * 从指定的 块大小与总数据大小生成区间流.
     * <p>
     * 产生的流大小是缓存的, 因此可以 多次调用该方法并输出 {@link Stream#count()},{@link Stream#findFirst()} 等快速方法.
     *
     * @param blockSize     块大小
     * @param contentLength 总数据大小
     * @return 区间流
     * @throws IllegalArgumentException 当任何参数小于 1
     * @see LongAdder
     * @see Collection#size()
     */
    @Nonnull
    public static Stream<Range> createOrderedRangeStream(final long blockSize, final long contentLength) throws IllegalArgumentException {
        long divided = blocksCount(blockSize, contentLength);
        long bound = contentLength - 1;
        return LongStream.range(0, divided).mapToObj((l) -> {
            long base = l * blockSize;
            long up = base + blockSize - 1;
            if (up >= bound) {
                up = bound;
            }
            return new Range(base, up);
        });
    }

    /**
     * 从指定的 块大小与总数据大小 计算块数量.
     *
     * @param blockSize     块大小
     * @param contentLength 总数据大小
     * @return 块数量
     * @throws IllegalArgumentException 当任何参数小于 1
     */
    public static long blocksCount(final long blockSize, final long contentLength) {
        Preconditions.checkArgument(blockSize > 0);
        Preconditions.checkArgument(contentLength > 0);
        return LongMath.divide(contentLength, blockSize, RoundingMode.CEILING);
    }

}
