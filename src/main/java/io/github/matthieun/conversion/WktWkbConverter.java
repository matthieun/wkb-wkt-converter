package io.github.matthieun.conversion;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * Use JTS to provide a simple tool to convert WKT (Well Known Text) from/to WKB (Well Known Binary)
 * geometries
 *
 * @author matthieun
 */
public final class WktWkbConverter
{
    private static final int HEXADECIMAL = 16;
    private static final int HEXADECIMAL_SHIFT = 4;

    private final WKBReader wkbReader;
    private final WKBWriter wkbWriter;
    private final WKTReader wktReader;
    private final WKTWriter wktWriter;

    public WktWkbConverter()
    {
        this.wkbReader = new WKBReader();
        this.wkbWriter = new WKBWriter();
        this.wktReader = new WKTReader();
        this.wktWriter = new WKTWriter();
    }

    /**
     * @param wkbHexadecimal
     *            The hexadecimal {@link String} version of the Well Known Binary format
     * @return The corresponding Well Known Text
     */
    public String wkbHexadecimalToWkt(final String wkbHexadecimal)
    {
        // Remove spaces if any
        final String local = wkbHexadecimal.replace(" ", "");
        final int length = local.length();
        if (length % 2 != 0)
        {
            throw new IllegalArgumentException("String length " + length
                    + " is not an even number, cannot be hexadecimal wkb.");
        }
        // Each byte is 2 characters
        final byte[] wkb = new byte[length / 2];
        for (int index = 0; index < length; index += 2)
        {
            wkb[index / 2] = (byte) ((Character.digit(local.charAt(index),
                    HEXADECIMAL) << HEXADECIMAL_SHIFT)
                    + Character.digit(local.charAt(index + 1), HEXADECIMAL));
        }
        return wkbToWkt(wkb);
    }

    /**
     * @param wkb
     *            The Well Known Binary byte array
     * @return The corresponding Well Known Text
     */
    public String wkbToWkt(final byte[] wkb)
    {
        final Geometry geometry;
        try
        {
            geometry = this.wkbReader.read(wkb);
        }
        catch (final ParseException e)
        {
            throw new RuntimeException("Cannot parse wkb : " + wkb, e);
        }
        final String wkt = this.wktWriter.write(geometry);
        return wkt;
    }

    /**
     * @param wkt
     *            The Well Known Text
     * @return The corresponding Well Known Binary
     */
    public byte[] wktToWkb(final String wkt)
    {
        final Geometry geometry;
        try
        {
            geometry = this.wktReader.read(wkt);
        }
        catch (final ParseException e)
        {
            throw new RuntimeException("Cannot parse wkt : " + wkt, e);
        }
        final byte[] wkb = this.wkbWriter.write(geometry);
        return wkb;
    }

    /**
     * @param wkt
     *            The Well Known Text
     * @return The corresponding hexadecimal {@link String} version of the Well Known Binary format
     */
    public String wktToWkbHexadecimal(final String wkt)
    {
        final byte[] wkb = wktToWkb(wkt);
        final StringBuilder result = new StringBuilder();
        for (final byte number : wkb)
        {
            result.append(String.format("%02X", number));
        }
        return result.toString();
    }
}
