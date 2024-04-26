package group1.mavenproject2;

import javafx.scene.control.Label;

/**
 *
 * @author noaca
 */
class LabelInfo {
    private Label label;
    private int row;
    private int col;

    public LabelInfo(Label label, int row, int col) {
        this.label = label;
        this.row = row;
        this.col = col;
    }

    public Label getLabel() {
        return label;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return (label.getText() + ":" + row + "-" + col);
    }
}
