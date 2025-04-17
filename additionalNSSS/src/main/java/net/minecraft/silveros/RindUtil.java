package net.minecraft.silveros;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.imageio.ImageIO;

public class RindUtil {
    public RindUtil() {
    }

    public static int tryParseInt(String input, int value) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException var3) {
            return value;
        }
    }

    public static int tryParseInt_hexadecimal(String input, int value) {
        try {
            return Integer.parseInt(input, 16);
        } catch (NumberFormatException var3) {
            NumberFormatException e = var3;
            e.printStackTrace();
            return value;
        }
    }

    public static ByteBuffer[] loadDisplayIcons(URL... urls) throws IOException {
        int argLength = urls.length;
        ByteBuffer[] buffers = new ByteBuffer[argLength];

        for(int i = 0; i < argLength; ++i) {
            URL url = urls[i];
            if (url == null) {
                throw new IOException("url == null");
            }

            BufferedImage image = ImageIO.read(url);
            int width = image.getWidth();
            int height = image.getHeight();
            int capacity = width * height;
            ByteBuffer buffer = ByteBuffer.allocateDirect(capacity * 4);
            buffer.order(ByteOrder.nativeOrder());
            int[] pixels = new int[capacity];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            for(int y = 0; y < height; ++y) {
                for(int x = 0; x < width; ++x) {
                    int pixel = pixels[y * width + x];
                    buffer.put((byte)(pixel >> 16 & 255));
                    buffer.put((byte)(pixel >> 8 & 255));
                    buffer.put((byte)(pixel & 255));
                    buffer.put((byte)(pixel >> 24 & 255));
                }
            }

            buffer.flip();
            buffers[i] = buffer;
        }

        return buffers;
    }
}