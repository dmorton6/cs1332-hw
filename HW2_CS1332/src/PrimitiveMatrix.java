/**
 * Implementation of Homework 2
 * 
 * @author First Last (GT Username)
 */
public class PrimitiveMatrix implements Matrix {
    
    private final String INDEX_ERROR = "You can't index that element!";
    private final String NONMATCHING_DIMENSIONS =
            "These matrices do not have matching dimensions";
    private int rows, cols, maxElements, numElements;
    private double[] matrix; //of type double because of the put() method signature
    
    
	/**
	 * @param rows
	 *            number of rows in matrix
	 * @param cols
	 *            number of cols in matrix
	 */
	public PrimitiveMatrix(final int rows, final int cols) {
		this.rows = rows;
                this.cols = cols;
                this.maxElements = rows*cols;
                this.matrix = new double[maxElements];
                this.numElements = 0;
	}

	@Override
	public void put(final double value, final int row, final int col)
			throws IndexOutOfBoundsException {
            
            int index = row*cols + col; //index for putting the value in the Matrix
            
            if (index > maxElements || index < 0) {
                throw new IndexOutOfBoundsException(INDEX_ERROR);
            }
            else {
                matrix[index] = value;
                ++numElements;
            }
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
            int index = row*cols + col;
            
            if (index > maxElements || index < 0) {
                throw new IndexOutOfBoundsException(INDEX_ERROR);
            }
            else {
                return matrix[index];
            }
	}

	@Override
	public Matrix add(final Matrix rhs) throws IllegalArgumentException {
            int rhsRows = rhs.getRowDimension();
            int rhsCols = rhs.getColumnDimension();
            
            if (rhsRows != this.getRowDimension() || 
                rhsCols != this.getColumnDimension() ) {
                throw new IllegalArgumentException(NONMATCHING_DIMENSIONS);
            }
            else {
                Matrix newMat = new PrimitiveMatrix(rhsRows, rhsCols);
                for (int r = 0; r < rhsRows; ++r) {
                    for (int c = 0; c < rhsCols; ++c) {
                        double nextValue = rhs.get(r,c) + this.get(r,c);
                        newMat.put(nextValue, r, c);
                    }
                }
                return newMat;
            }
	}

	@Override
	public Matrix sub(final Matrix rhs) throws IllegalArgumentException {
            int rhsRows = rhs.getRowDimension();
            int rhsCols = rhs.getColumnDimension();
            
            if (rhsRows != this.getRowDimension() || 
                rhsCols != this.getColumnDimension() ) {
                throw new IllegalArgumentException(NONMATCHING_DIMENSIONS);
            }
            else {
                Matrix newMat = new PrimitiveMatrix(rhsRows, rhsCols);
                for (int r = 0; r < rhsRows; ++r) {
                    for (int c = 0; c < rhsCols; ++c) {
                        double nextValue = this.get(r,c) - rhs.get(r,c);
                        newMat.put(nextValue, r, c);
                    }
                }
                return newMat;
            }
	}

	@Override
	public Matrix mult(final Matrix rhs) throws IllegalArgumentException {
            int rhsRows = rhs.getRowDimension();
            int rhsCols = rhs.getColumnDimension();
            
            if (rhsRows != this.getColumnDimension() ) {
                throw new IllegalArgumentException(NONMATCHING_DIMENSIONS);
            }
            else {
                // manual matrix multiplication
                Matrix newMat = new PrimitiveMatrix(rows, rhsCols);
                double nextVal = 0;
                
                for (int i = 0; i < rows; ++i) { 
                    for (int j = 0; j < rhsCols; ++j) {
                        nextVal = 0;
                        for (int k = 0; k < cols; ++k) {
                            nextVal += this.get(i,k) * rhs.get(k,j);
                        }
                        newMat.put(nextVal, i, j);
                    }
                }

                return newMat;
            }
	}

	@Override
	public int getRowDimension() {
            return rows;
	}

	@Override
	public int getColumnDimension() {
            return cols;
	}
}
