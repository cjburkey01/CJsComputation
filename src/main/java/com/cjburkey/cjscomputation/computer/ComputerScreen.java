package com.cjburkey.cjscomputation.computer;

import java.util.Arrays;

public final class ComputerScreen {
    
    public static final int CHARACTER_WIDTH = 5;
    public static final int CHARACTER_HEIGHT = 7;
    public static final int CHARACTER_PADDING = 1;
    
    public static final int SCREEN_CHARACTER_WIDTH = 40;
    public static final int SCREEN_CHARACTER_HEIGHT = 25;
    
    public static final int SCREEN_PIXEL_WIDTH = (SCREEN_CHARACTER_WIDTH * (CHARACTER_WIDTH + CHARACTER_PADDING)) + CHARACTER_PADDING;
    public static final int SCREEN_PIXEL_HEIGHT = (SCREEN_CHARACTER_HEIGHT * (CHARACTER_HEIGHT + CHARACTER_PADDING)) + CHARACTER_PADDING;
    
    private byte[][][] pixels = new byte[SCREEN_PIXEL_WIDTH][SCREEN_PIXEL_HEIGHT][3];
    private char[][] characters = new char[SCREEN_CHARACTER_WIDTH][SCREEN_CHARACTER_HEIGHT];
    private byte[][][] characterColors = new byte[SCREEN_CHARACTER_WIDTH][SCREEN_CHARACTER_HEIGHT][3];
    private boolean pixelDrawMode;
    
    public ComputerScreen() {
        clearScreen();
    }
    
    public void setPixel(short x, short y, byte r, byte g, byte b) {
        if (!getIsValidPixel(x, y)) {
            return;
        }
        pixels[x][y][0] = r;
        pixels[x][y][1] = g;
        pixels[x][y][2] = b;
    }
    
    public void setPixel(short x, short y, byte[] rgb) {
        if (rgb.length != 3) {
            return;
        }
        setPixel(x, y, rgb[0], rgb[1], rgb[2]);
    }
    
    // Clears the pixel buffer
    public void clearPixels() {
        for (short x = 0; x < pixels.length; x ++) {
            for (short y = 0; y < pixels[0].length; y ++) {
                setPixel(x, y, (byte) 0, (byte) 0, (byte) 0);
            }
        }
    }
    
    public byte[] getPixel(short x, short y) {
        if (!getIsValidPixel(x, y)) {
            return new byte[3];
        }
        byte[] out = new byte[3];
        out[0] = pixels[x][y][0];
        out[1] = pixels[x][y][1];
        out[2] = pixels[x][y][2];
        return out;
    }
    
    private boolean getIsValidPixel(short x, short y) {
        return x >= 0 && y >= 0 && x < pixels.length && y < pixels[0].length;
    }
    
    public void setCharacter(short x, short y, char character) {
        if (!getIsValidCharacter(x, y)) {
            return;
        }
        characters[x][y] = character;
    }
    
    // Clears the character buffers
    public void clearCharacters() {
        for (short x = 0; x < characters.length; x ++) {
            for (short y = 0; y < characters[0].length; y ++) {
                setCharacter(x, y, (char) 0);
            }
        }
        for (short x = 0; x < characterColors.length; x ++) {
            for (short y = 0; y < characterColors[0].length; y ++) {
                setCharacterColor(x, y, (byte) 0, (byte) 0, (byte) 0);
            }
        }
    }
    
    public char getCharacter(short x, short y) {
        if (!getIsValidCharacter(x, y)) {
            return (char) 0;
        }
        return characters[x][y];
    }
    
    public void setCharacterColor(short x, short y, byte[] c) {
        if (c.length != 3) {
            return;
        }
        setCharacterColor(x, y, c[0], c[1], c[2]);
    }
    
    public void setCharacterColor(short x, short y, byte r, byte g, byte b) {
        if (!getIsValidCharacter(x, y)) {
            return;
        }
        characterColors[x][y][0] = r;
        characterColors[x][y][1] = g;
        characterColors[x][y][2] = b;
    }
    
    public byte[] getCharacterColor(short x, short y) {
        if (!getIsValidCharacter(x, y)) {
            return new byte[3];
        }
        return characterColors[x][y];
    }
    
    private boolean getIsValidCharacter(short x, short y) {
        return x >= 0 && y >= 0 && x < characters.length && y < characters[0].length;
    }
    
    // Clears the current buffer (will clear pixel buffer if it's the current one and the character buffers if they're the current one)
    public void clearCurrent() {
        if (pixelDrawMode) {
            clearPixels();
            return;
        }
        clearCharacters();
    }
    
    // Clears both buffers
    public void clearScreen() {
        clearPixels();
        clearCharacters();
    }
    
    public void setPixelDrawMode() {
        pixelDrawMode = true;
    }
    
    public void setCharacterDrawMode() {
        pixelDrawMode = false;
    }
    
    public boolean getIsPixelDrawMode() {
        return pixelDrawMode;
    }
    
    public byte[][][] getPixelData() {
        return pixels;
    }
    
    public char[][] getCharacterData() {
        return characters;
    }
    
    public byte[][][] getCharacterColors() {
        return characterColors;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(characterColors);
        result = prime * result + Arrays.deepHashCode(characters);
        result = prime * result + (pixelDrawMode ? 1231 : 1237);
        result = prime * result + Arrays.deepHashCode(pixels);
        return result;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ComputerScreen other = (ComputerScreen) obj;
        if (!Arrays.deepEquals(characterColors, other.characterColors)) {
            return false;
        }
        if (!Arrays.deepEquals(characters, other.characters)) {
            return false;
        }
        if (pixelDrawMode != other.pixelDrawMode) {
            return false;
        }
        if (!Arrays.deepEquals(pixels, other.pixels)) {
            return false;
        }
        return true;
    }
    
}