package it.polimi.ingsw.server.Messages;

import it.polimi.ingsw.utils.Matrix;

public class MatrixMessage extends Message{

    private Matrix matrix;

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
