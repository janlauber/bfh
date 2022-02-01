package ch.bfh.bti3001.model;

import javafx.scene.paint.Color;

/**
 * Cell class
 */
public class Cell {

    private int value;

    /**
     * Constructor
     */
    public Cell() {
        value = 0;
    }

    /**
     * Constructor
     * @param value the value of the cell
     */
    public Cell(int value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @param other cell to compare
     * @return true if the cells are equal
     */
    public boolean equals(Cell other) {
        if (other == null) {
            return false;
        }
        return this.value == other.value;
    }

    /**
     * @return the color of the cell based on the value
     */
    public Color getBackground() {
        return switch (value) {
            case 2 -> Color.rgb(238, 228, 218, 1.0); //238 228 218 1.0      0xeee4da
            case 4 -> Color.rgb(237, 224, 200, 1.0); //237, 224, 200, 1.0   0xede0c8
            case 8 -> Color.rgb(242, 177, 121, 1.0); //242, 177, 121, 1.0   0xf2b179
            case 16 -> Color.rgb(245, 149, 99, 1.0); //245, 149, 99, 1.0     0xf59563
            case 32 -> Color.rgb(246, 124, 95, 1.0); //246, 124, 95, 1.0     0xf67c5f
            case 64 -> Color.rgb(246, 94, 59, 1.0); //246, 94, 59, 1.0      0xf65e3b
            case 128 -> Color.rgb(237, 207, 114, 1.0); //237, 207, 114, 1.0   0xedcf72
            case 256 -> Color.rgb(237, 204, 97, 1.0); //237, 204, 97, 1.0     0xedcc61
            case 512 -> Color.rgb(237, 200, 80, 1.0); //237, 200, 80, 1.0     0xedc850
            case 1024 -> Color.rgb(237, 197, 63, 1.0); //237, 197, 63, 1.0     0xedc53f
            case 2048 -> Color.rgb(237, 194, 46, 1.0);
            default -> Color.rgb(205, 193, 180, 1.0);
        };
    }

    /**
     * @return the color of the text based on the value
     */
    public Color getForeground() {
        Color foreground;
        if(value < 16) {
            foreground = Color.rgb(119, 110, 101, 1.0); //0x776e65
        } else {
            foreground = Color.rgb(249, 246, 242, 1.0);    //0xf9f6f2
        }
        return foreground;
    }

    /**
     * @param other cell to merge
     */
    public void merge(Cell other) {
        if (other == null) {
            return;
        }
        this.value += other.value;
    }

    /**
     * reset the cell
     */
    public void reset() {
        value = 0;
    }

    /**
     * @return the string value of the cell
     */
    public String toString() {
        return String.valueOf(value);
    }
}
