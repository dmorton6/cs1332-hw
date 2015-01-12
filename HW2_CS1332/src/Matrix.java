/**
 * This class provides an interface for a 2D matrix that supports some basic
 * operations.
 * 
 * The matrix is 0-Indexed. Row 0 is the first row, Col 0 is the first column.
 */
public interface Matrix {

	/**
	 * Put a value into the matrix at the given row and column
	 * 
	 * @param value
	 *            the value to place into the location
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @throws IndexOutOfBoundsException
	 *             if any of the given indices are out of bounds
	 */
	void put(final double value, final int row, final int col)
			throws IndexOutOfBoundsException;

	/**
	 * Get a value from the matrix at the given row and column
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @return the value stored at the location
	 * @throws IndexOutOfBoundsException
	 *             if any of the given indices are out of bounds
	 */
	double get(final int row, final int col) throws IndexOutOfBoundsException;

	/**
	 * Add the matrix rhs to this matrix (result = this + rhs)
	 * 
	 * You should not modify this matrix, return the result!
	 * 
	 * @param rhs
	 *            the matrix to add
	 * @return a new matrix that is the sum of rhs and this
	 * @throws IllegalArgumentException
	 *             if dimensions invalid for operation
	 */
	Matrix add(final Matrix rhs) throws IllegalArgumentException;

	/**
	 * Subtract the matrix rhs from this matrix (result = this - rhs)
	 * 
	 * You should not modify this matrix, return the result!
	 * 
	 * @param rhs
	 *            the matrix to subtract
	 * @return a new matrix that is the result, this
	 * @throws IllegalArgumentException
	 *             if dimensions invalid for operation
	 */
	Matrix sub(final Matrix rhs) throws IllegalArgumentException;

	/**
	 * Multiply this matrix by rhs using matrix multiplication. (result = this *
	 * rhs)
	 * 
	 * You should not modify this matrix, return the result!
	 * 
	 * @param rhs
	 *            the matrix to multiply by
	 * @return a new matrix that is the result of the operation
	 * @throws IllegalArgumentException
	 *             if dimensions invalid for operation
	 */
	Matrix mult(final Matrix rhs) throws IllegalArgumentException;

	/**
	 * @return the number of rows in the matrix
	 */
	int getRowDimension();

	/**
	 * @return the number of columns in the matrix
	 */
	int getColumnDimension();
}
