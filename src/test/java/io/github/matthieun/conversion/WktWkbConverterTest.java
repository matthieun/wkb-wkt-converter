package io.github.matthieun.conversion;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author matthieun
 */
public class WktWkbConverterTest
{
    private static final Logger logger = LoggerFactory.getLogger(WktWkbConverterTest.class);
    private static final String WKT = "MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)), ((20 35, 10 30, 10 10, 30 5, 45 20, 20 35), (30 20, 20 15, 20 25, 30 20)))";
    private static final String WKB_HEXADECIMAL = "0000000006000000020000000003000000010000000440440000000000004044000000000000403400000000000040468000000000004046800000000000403E0000000000004044000000000000404400000000000000000000030000000200000006403400000000000040418000000000004024000000000000403E00000000000040240000000000004024000000000000403E0000000000004014000000000000404680000000000040340000000000004034000000000000404180000000000000000004403E00000000000040340000000000004034000000000000402E00000000000040340000000000004039000000000000403E0000000000004034000000000000";
    private final WktWkbConverter converter = new WktWkbConverter();

    @Test
    public void testWkbToWkt()
    {
        final String result = this.converter.wkbHexadecimalToWkt(WKB_HEXADECIMAL);
        logger.info(result);
        Assert.assertEquals(WKT, result);
    }

    @Test
    public void testWktToWkb()
    {
        final String result = this.converter.wktToWkbHexadecimal(WKT);
        logger.info(result);
        Assert.assertEquals(WKB_HEXADECIMAL, result);
    }
}
