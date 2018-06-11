package com.cjburkey.cjscomputation;

public class HexUtil {
    
    public static int getHex(byte[][][] fragmentColors, int x, int y) {
        return (0xFF << 24) | getHexNoAlpha(fragmentColors, x, y);
    }
    
    public static int getHexNoAlpha(byte[][][] fragmentColors, int x, int y) {
        if (fragmentColors.length <= 0 || fragmentColors[0].length <= 0 || fragmentColors[0][0].length != 3) {
            return 0x000000;  // Out of bounds
        }
        return getHexColorNoAlpha(fragmentColors[x][y][0], fragmentColors[x][y][1], fragmentColors[x][y][2]);
    }
    
    public static int getHexColorNoAlpha(byte r, byte g, byte b) {
        if (r == 0 && g == 0 && b == 0) {
            return 0x000000;
        }
        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }
    
    public static byte[] getPureColor(int hex) {
        byte[] color = new byte[3];
        color[0] = (byte) ((hex & 0xFF0000) >> 16); // R
        color[1] = (byte) ((hex & 0x00FF00) >> 8);  // G
        color[2] = (byte) ((hex & 0x0000FF));       // B
        return color;
    }
    
}