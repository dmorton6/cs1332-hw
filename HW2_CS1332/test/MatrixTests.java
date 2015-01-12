import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class MatrixTests {

	private static final int TIMEOUT = 200;
	private static final double DELTA = 1e-6;
	
	@Test(timeout = TIMEOUT)
	public void constructor1() {
		Matrix m = empty(3, 3);
		assertEquals(m.getRowDimension(), 3);
		assertEquals(m.getColumnDimension(), 3);
	}
	
	@Test(timeout = TIMEOUT)
	public void constructor2() {
		Matrix m = empty(3, 5);
		assertEquals(3, m.getRowDimension());
		assertEquals(5, m.getColumnDimension());
	}
	
	@Test(timeout = TIMEOUT)
	public void put1() {
		Matrix m = empty(3, 3);
		m.put(2.5, 1, 2);
		assertEquals(2.5, m.get(1, 2), DELTA);
	}
	
        
	@Test(timeout = TIMEOUT)
	public void put2() {
		Matrix m = empty(3, 3);
		m.put(2.5, 1, 2);
		m.put(5, 1, 2);
		assertEquals(5, m.get(1, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void put3() {
		Matrix m = empty(3, 3);
		m.put(2.5, 1, 2);
		assertEquals(2.5, m.get(1, 2), DELTA);
		m.put(3.2, 2, 1);
		assertEquals(3.2, m.get(2, 1), DELTA);
	}
	
	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void put4() {
		Matrix m = empty(3, 4);
		m.put(2.5, 3, 4);
	}
	
	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void get1() {
		Matrix m = empty(3, 4);
		m.get(3, 4);
	}
	
	@Test(timeout = TIMEOUT)
	public void put5() {
		Matrix m = matrix1();
		assertEquals(3, m.get(0, 0), DELTA);
		assertEquals(2, m.get(0, 1), DELTA);
		assertEquals(6, m.get(0, 2), DELTA);
		assertEquals(6, m.get(1, 0), DELTA);
		assertEquals(0, m.get(1, 1), DELTA);
		assertEquals(0, m.get(1, 2), DELTA);
		assertEquals(9, m.get(2, 0), DELTA);
		assertEquals(4, m.get(2, 1), DELTA);
		assertEquals(36, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void add1() {
		Matrix m = empty(3, 3).add(matrix1());
		assertEquals(3, m.get(0, 0), DELTA);
		assertEquals(2, m.get(0, 1), DELTA);
		assertEquals(6, m.get(0, 2), DELTA);
		assertEquals(6, m.get(1, 0), DELTA);
		assertEquals(0, m.get(1, 1), DELTA);
		assertEquals(0, m.get(1, 2), DELTA);
		assertEquals(9, m.get(2, 0), DELTA);
		assertEquals(4, m.get(2, 1), DELTA);
		assertEquals(36, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void add2() {
		Matrix m = matrix1().add(matrix1());
		assertEquals(6, m.get(0, 0), DELTA);
		assertEquals(4, m.get(0, 1), DELTA);
		assertEquals(12, m.get(0, 2), DELTA);
		assertEquals(12, m.get(1, 0), DELTA);
		assertEquals(0, m.get(1, 1), DELTA);
		assertEquals(0, m.get(1, 2), DELTA);
		assertEquals(18, m.get(2, 0), DELTA);
		assertEquals(8, m.get(2, 1), DELTA);
		assertEquals(72, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void add3() {
		Matrix m = matrix1().add(matrix2());
		assertEquals(5, m.get(0, 0), DELTA);
		assertEquals(6, m.get(0, 1), DELTA);
		assertEquals(12, m.get(0, 2), DELTA);
		assertEquals(14, m.get(1, 0), DELTA);
		assertEquals(10, m.get(1, 1), DELTA);
		assertEquals(12, m.get(1, 2), DELTA);
		assertEquals(23, m.get(2, 0), DELTA);
		assertEquals(20, m.get(2, 1), DELTA);
		assertEquals(54, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void add4() {
		empty(2, 8).add(matrix1());
	}
	
	@Test(timeout = TIMEOUT)
	public void sub1() {
		Matrix m = empty(3, 3).sub(matrix1());
		assertEquals(-3, m.get(0, 0), DELTA);
		assertEquals(-2, m.get(0, 1), DELTA);
		assertEquals(-6, m.get(0, 2), DELTA);
		assertEquals(-6, m.get(1, 0), DELTA);
		assertEquals(0, m.get(1, 1), DELTA);
		assertEquals(0, m.get(1, 2), DELTA);
		assertEquals(-9, m.get(2, 0), DELTA);
		assertEquals(-4, m.get(2, 1), DELTA);
		assertEquals(-36, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void sub2() {
		Matrix m = matrix1().sub(matrix1());
		assertEquals(0, m.get(0, 0), DELTA);
		assertEquals(0, m.get(0, 1), DELTA);
		assertEquals(0, m.get(0, 2), DELTA);
		assertEquals(0, m.get(1, 0), DELTA);
		assertEquals(0, m.get(1, 1), DELTA);
		assertEquals(0, m.get(1, 2), DELTA);
		assertEquals(0, m.get(2, 0), DELTA);
		assertEquals(0, m.get(2, 1), DELTA);
		assertEquals(0, m.get(2, 2), DELTA);
	}
	
        
	@Test(timeout = TIMEOUT)
	public void sub3() {
		Matrix m = matrix1().sub(matrix2());
		assertEquals(1, m.get(0, 0), DELTA);
		assertEquals(-2, m.get(0, 1), DELTA);
		assertEquals(0, m.get(0, 2), DELTA);
		assertEquals(-2, m.get(1, 0), DELTA);
		assertEquals(-10, m.get(1, 1), DELTA);
		assertEquals(-12, m.get(1, 2), DELTA);
		assertEquals(-5, m.get(2, 0), DELTA);
		assertEquals(-12, m.get(2, 1), DELTA);
		assertEquals(18, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void sub4() {
		empty(2, 8).sub(matrix1());
	}
	
	@Test(timeout = TIMEOUT)
	public void mult1() {
		Matrix m = empty(3, 3).mult(matrix1());
		assertEquals(0, m.get(0, 0), DELTA);
		assertEquals(0, m.get(0, 1), DELTA);
		assertEquals(0, m.get(0, 2), DELTA);
		assertEquals(0, m.get(1, 0), DELTA);
		assertEquals(0, m.get(1, 1), DELTA);
		assertEquals(0, m.get(1, 2), DELTA);
		assertEquals(0, m.get(2, 0), DELTA);
		assertEquals(0, m.get(2, 1), DELTA);
		assertEquals(0, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void mult2() {
		Matrix m = matrix1().mult(matrix1());
		assertEquals(75, m.get(0, 0), DELTA);
		assertEquals(30, m.get(0, 1), DELTA);
		assertEquals(234, m.get(0, 2), DELTA);
		assertEquals(18, m.get(1, 0), DELTA);
		assertEquals(12, m.get(1, 1), DELTA);
		assertEquals(36, m.get(1, 2), DELTA);
		assertEquals(375, m.get(2, 0), DELTA);
		assertEquals(162, m.get(2, 1), DELTA);
		assertEquals(1350, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void mult3() {
		Matrix m = matrix1().mult(matrix2());
		assertEquals(106, m.get(0, 0), DELTA);
		assertEquals(128, m.get(0, 1), DELTA);
		assertEquals(150, m.get(0, 2), DELTA);
		assertEquals(12, m.get(1, 0), DELTA);
		assertEquals(24, m.get(1, 1), DELTA);
		assertEquals(36, m.get(1, 2), DELTA);
		assertEquals(554, m.get(2, 0), DELTA);
		assertEquals(652, m.get(2, 1), DELTA);
		assertEquals(750, m.get(2, 2), DELTA);
	}
	
	@Test(timeout = TIMEOUT)
	public void mult4() {
		Matrix m = matrix1().mult(matrix3());
		assertEquals(17, m.get(0, 0), DELTA);
		assertEquals(16, m.get(0, 1), DELTA);
		assertEquals(39, m.get(0, 2), DELTA);
		assertEquals(38, m.get(0, 3), DELTA);
		assertEquals(6, m.get(1, 0), DELTA);
		assertEquals(12, m.get(1, 1), DELTA);
		assertEquals(18, m.get(1, 2), DELTA);
		assertEquals(24, m.get(1, 3), DELTA);
		assertEquals(61, m.get(2, 0), DELTA);
		assertEquals(62, m.get(2, 1), DELTA);
		assertEquals(183, m.get(2, 2), DELTA);
		assertEquals(184, m.get(2, 3), DELTA);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void mult5() {
		empty(2, 8).mult(matrix3());
	}
        
	
	private Matrix empty(int rows, int cols) {
		return new PrimitiveMatrix(rows, cols);
	}
	
	private Matrix matrix1() {
		Matrix m = new PrimitiveMatrix(3, 3);
		m.put(3, 0, 0);
		m.put(2, 0, 1);
		m.put(6, 0, 2);
		m.put(6, 1, 0);
		m.put(0, 1, 1);
		m.put(0, 1, 2);
		m.put(9, 2, 0);
		m.put(4, 2, 1);
		m.put(36, 2, 2);
		return m;
	}
	
	private Matrix matrix2() {
		Matrix m = new PrimitiveMatrix(3, 3);
		m.put(2, 0, 0);
		m.put(4, 0, 1);
		m.put(6, 0, 2);
		m.put(8, 1, 0);
		m.put(10, 1, 1);
		m.put(12, 1, 2);
		m.put(14, 2, 0);
		m.put(16, 2, 1);
		m.put(18, 2, 2);
		return m;
	}
	
	private Matrix matrix3() {
		Matrix m = new PrimitiveMatrix(3, 4);
		m.put(1, 0, 0);
		m.put(2, 0, 1);
		m.put(3, 0, 2);
		m.put(4, 0, 3);
		m.put(4, 1, 0);
		m.put(2, 1, 1);
		m.put(3, 1, 2);
		m.put(1, 1, 3);
		m.put(1, 2, 0);
		m.put(1, 2, 1);
		m.put(4, 2, 2);
		m.put(4, 2, 3);
		return m;
	}
	
}
