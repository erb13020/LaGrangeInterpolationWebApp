import Jama.util.Maths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

public class Matrix
  implements Cloneable, Serializable
{
  private double[][] A;
  private int m;
  private int n;
  private static final long serialVersionUID = 1L;
  
  public Matrix(int paramInt1, int paramInt2)
  {
    this.m = paramInt1;
    this.n = paramInt2;
    this.A = new double[paramInt1][paramInt2];
  }
  
  public Matrix(int paramInt1, int paramInt2, double paramDouble)
  {
    this.m = paramInt1;
    this.n = paramInt2;
    this.A = new double[paramInt1][paramInt2];
    for (int i = 0; i < paramInt1; i++) {
      for (int j = 0; j < paramInt2; j++) {
        this.A[i][j] = paramDouble;
      }
    }
  }
  
  public Matrix(double[][] paramArrayOfDouble)
  {
    this.m = paramArrayOfDouble.length;
    this.n = paramArrayOfDouble[0].length;
    for (int i = 0; i < this.m; i++) {
      if (paramArrayOfDouble[i].length != this.n) {
        throw new IllegalArgumentException("All rows must have the same length.");
      }
    }
    this.A = paramArrayOfDouble;
  }
  
  public Matrix(double[][] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    this.A = paramArrayOfDouble;
    this.m = paramInt1;
    this.n = paramInt2;
  }
  
  public Matrix(double[] paramArrayOfDouble, int paramInt)
  {
    this.m = paramInt;
    this.n = (paramInt != 0 ? paramArrayOfDouble.length / paramInt : 0);
    if (paramInt * this.n != paramArrayOfDouble.length) {
      throw new IllegalArgumentException("Array length must be a multiple of m.");
    }
    this.A = new double[paramInt][this.n];
    for (int i = 0; i < paramInt; i++) {
      for (int j = 0; j < this.n; j++) {
        this.A[i][j] = paramArrayOfDouble[(i + j * paramInt)];
      }
    }
  }
  
  public static Matrix constructWithCopy(double[][] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length;
    int j = paramArrayOfDouble[0].length;
    Matrix localMatrix = new Matrix(i, j);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int k = 0; k < i; k++)
    {
      if (paramArrayOfDouble[k].length != j) {
        throw new IllegalArgumentException("All rows must have the same length.");
      }
      for (int i1 = 0; i1 < j; i1++) {
        arrayOfDouble[k][i1] = paramArrayOfDouble[k][i1];
      }
    }
    return localMatrix;
  }
  
  public Matrix copy()
  {
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = this.A[i][j];
      }
    }
    return localMatrix;
  }
  
  public Object clone()
  {
    return copy();
  }
  
  public double[][] getArray()
  {
    return this.A;
  }
  
  public double[][] getArrayCopy()
  {
    double[][] arrayOfDouble = new double[this.m][this.n];
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = this.A[i][j];
      }
    }
    return arrayOfDouble;
  }
  
  public double[] getColumnPackedCopy()
  {
    double[] arrayOfDouble = new double[this.m * this.n];
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[(i + j * this.m)] = this.A[i][j];
      }
    }
    return arrayOfDouble;
  }
  
  public double[] getRowPackedCopy()
  {
    double[] arrayOfDouble = new double[this.m * this.n];
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[(i * this.n + j)] = this.A[i][j];
      }
    }
    return arrayOfDouble;
  }
  
  public int getRowDimension()
  {
    return this.m;
  }
  
  public int getColumnDimension()
  {
    return this.n;
  }
  
  public double get(int paramInt1, int paramInt2)
  {
    return this.A[paramInt1][paramInt2];
  }
  
  public Matrix getMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Matrix localMatrix = new Matrix(paramInt2 - paramInt1 + 1, paramInt4 - paramInt3 + 1);
    double[][] arrayOfDouble = localMatrix.getArray();
    try
    {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = paramInt3; j <= paramInt4; j++) {
          arrayOfDouble[(i - paramInt1)][(j - paramInt3)] = this.A[i][j];
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  
  public Matrix getMatrix(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    Matrix localMatrix = new Matrix(paramArrayOfInt1.length, paramArrayOfInt2.length);
    double[][] arrayOfDouble = localMatrix.getArray();
    try
    {
      for (int i = 0; i < paramArrayOfInt1.length; i++) {
        for (int j = 0; j < paramArrayOfInt2.length; j++) {
          arrayOfDouble[i][j] = this.A[paramArrayOfInt1[i]][paramArrayOfInt2[j]];
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  
  public Matrix getMatrix(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    Matrix localMatrix = new Matrix(paramInt2 - paramInt1 + 1, paramArrayOfInt.length);
    double[][] arrayOfDouble = localMatrix.getArray();
    try
    {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = 0; j < paramArrayOfInt.length; j++) {
          arrayOfDouble[(i - paramInt1)][j] = this.A[i][paramArrayOfInt[j]];
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  
  public Matrix getMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    Matrix localMatrix = new Matrix(paramArrayOfInt.length, paramInt2 - paramInt1 + 1);
    double[][] arrayOfDouble = localMatrix.getArray();
    try
    {
      for (int i = 0; i < paramArrayOfInt.length; i++) {
        for (int j = paramInt1; j <= paramInt2; j++) {
          arrayOfDouble[i][(j - paramInt1)] = this.A[paramArrayOfInt[i]][j];
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  
  public void set(int paramInt1, int paramInt2, double paramDouble)
  {
    this.A[paramInt1][paramInt2] = paramDouble;
  }
  
  public void setMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Matrix paramMatrix)
  {
    try
    {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = paramInt3; j <= paramInt4; j++) {
          this.A[i][j] = paramMatrix.get(i - paramInt1, j - paramInt3);
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  
  public void setMatrix(int[] paramArrayOfInt1, int[] paramArrayOfInt2, Matrix paramMatrix)
  {
    try
    {
      for (int i = 0; i < paramArrayOfInt1.length; i++) {
        for (int j = 0; j < paramArrayOfInt2.length; j++) {
          this.A[paramArrayOfInt1[i]][paramArrayOfInt2[j]] = paramMatrix.get(i, j);
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  
  public void setMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2, Matrix paramMatrix)
  {
    try
    {
      for (int i = 0; i < paramArrayOfInt.length; i++) {
        for (int j = paramInt1; j <= paramInt2; j++) {
          this.A[paramArrayOfInt[i]][j] = paramMatrix.get(i, j - paramInt1);
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  
  public void setMatrix(int paramInt1, int paramInt2, int[] paramArrayOfInt, Matrix paramMatrix)
  {
    try
    {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = 0; j < paramArrayOfInt.length; j++) {
          this.A[i][paramArrayOfInt[j]] = paramMatrix.get(i - paramInt1, j);
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  
  public Matrix transpose()
  {
    Matrix localMatrix = new Matrix(this.n, this.m);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[j][i] = this.A[i][j];
      }
    }
    return localMatrix;
  }
  
  public double norm1()
  {
    double d1 = 0.0D;
    for (int i = 0; i < this.n; i++)
    {
      double d2 = 0.0D;
      for (int j = 0; j < this.m; j++) {
        d2 += Math.abs(this.A[j][i]);
      }
      d1 = Math.max(d1, d2);
    }
    return d1;
  }
  
  public double normInf()
  {
    double d1 = 0.0D;
    for (int i = 0; i < this.m; i++)
    {
      double d2 = 0.0D;
      for (int j = 0; j < this.n; j++) {
        d2 += Math.abs(this.A[i][j]);
      }
      d1 = Math.max(d1, d2);
    }
    return d1;
  }
  
  public double normF()
  {
    double d = 0.0D;
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        d = Maths.hypot(d, this.A[i][j]);
      }
    }
    return d;
  }
  
  public Matrix uminus()
  {
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (-this.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix plus(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (this.A[i][j] + paramMatrix.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix plusEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        this.A[i][j] += paramMatrix.A[i][j];
      }
    }
    return this;
  }
  
  public Matrix minus(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (this.A[i][j] - paramMatrix.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix minusEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        this.A[i][j] -= paramMatrix.A[i][j];
      }
    }
    return this;
  }
  
  public Matrix arrayTimes(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (this.A[i][j] * paramMatrix.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix arrayTimesEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        this.A[i][j] *= paramMatrix.A[i][j];
      }
    }
    return this;
  }
  
  public Matrix arrayRightDivide(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (this.A[i][j] / paramMatrix.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix arrayRightDivideEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        this.A[i][j] /= paramMatrix.A[i][j];
      }
    }
    return this;
  }
  
  public Matrix arrayLeftDivide(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (paramMatrix.A[i][j] / this.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix arrayLeftDivideEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        paramMatrix.A[i][j] /= this.A[i][j];
      }
    }
    return this;
  }
  
  public Matrix times(double paramDouble)
  {
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = (paramDouble * this.A[i][j]);
      }
    }
    return localMatrix;
  }
  
  public Matrix timesEquals(double paramDouble)
  {
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        this.A[i][j] = (paramDouble * this.A[i][j]);
      }
    }
    return this;
  }
  
  public Matrix times(Matrix paramMatrix)
  {
    if (paramMatrix.m != this.n) {
      throw new IllegalArgumentException("Matrix inner dimensions must agree.");
    }
    Matrix localMatrix = new Matrix(this.m, paramMatrix.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    double[] arrayOfDouble1 = new double[this.n];
    for (int i = 0; i < paramMatrix.n; i++)
    {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble1[j] = paramMatrix.A[j][i];
      }
      for (int j = 0; j < this.m; j++)
      {
        double[] arrayOfDouble2 = this.A[j];
        double d = 0.0D;
        for (int k = 0; k < this.n; k++) {
          d += arrayOfDouble2[k] * arrayOfDouble1[k];
        }
        arrayOfDouble[j][i] = d;
      }
    }
    return localMatrix;
  }
  
  public LUDecomposition lu()
  {
    return new LUDecomposition(this);
  }
  
  public QRDecomposition qr()
  {
    return new QRDecomposition(this);
  }

  public Matrix solve(Matrix paramMatrix)
  {
    return this.m == this.n ? new LUDecomposition(this).solve(paramMatrix) : new QRDecomposition(this).solve(paramMatrix);
  }
  
  public Matrix solveTranspose(Matrix paramMatrix)
  {
    return transpose().solve(paramMatrix.transpose());
  }
  
  public Matrix inverse()
  {
    return solve(identity(this.m, this.m));
  }
  
  public double det()
  {
    return new LUDecomposition(this).det();
  }
  
  public double trace()
  {
    double d = 0.0D;
    for (int i = 0; i < Math.min(this.m, this.n); i++) {
      d += this.A[i][i];
    }
    return d;
  }
  
  public static Matrix random(int paramInt1, int paramInt2)
  {
    Matrix localMatrix = new Matrix(paramInt1, paramInt2);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < paramInt1; i++) {
      for (int j = 0; j < paramInt2; j++) {
        arrayOfDouble[i][j] = Math.random();
      }
    }
    return localMatrix;
  }
  
  public static Matrix identity(int paramInt1, int paramInt2)
  {
    Matrix localMatrix = new Matrix(paramInt1, paramInt2);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < paramInt1; i++) {
      for (int j = 0; j < paramInt2; j++) {
        arrayOfDouble[i][j] = (i == j ? 1.0D : 0.0D);
      }
    }
    return localMatrix;
  }
  
  public void print(int paramInt1, int paramInt2)
  {
    print(new PrintWriter(System.out, true), paramInt1, paramInt2);
  }
  
  public void print(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
    localDecimalFormat.setMinimumIntegerDigits(1);
    localDecimalFormat.setMaximumFractionDigits(paramInt2);
    localDecimalFormat.setMinimumFractionDigits(paramInt2);
    localDecimalFormat.setGroupingUsed(false);
    print(paramPrintWriter, localDecimalFormat, paramInt1 + 2);
  }
  
  public void print(NumberFormat paramNumberFormat, int paramInt)
  {
    print(new PrintWriter(System.out, true), paramNumberFormat, paramInt);
  }
  
  public void print(PrintWriter paramPrintWriter, NumberFormat paramNumberFormat, int paramInt)
  {
    paramPrintWriter.println();
    for (int i = 0; i < this.m; i++)
    {
      for (int j = 0; j < this.n; j++)
      {
        String str = paramNumberFormat.format(this.A[i][j]);
        int k = Math.max(1, paramInt - str.length());
        for (int i1 = 0; i1 < k; i1++) {
          paramPrintWriter.print(' ');
        }
        paramPrintWriter.print(str);
      }
      paramPrintWriter.println();
    }
    paramPrintWriter.println();
  }
  
  public static Matrix read(BufferedReader paramBufferedReader)
    throws IOException
  {
    StreamTokenizer localStreamTokenizer = new StreamTokenizer(paramBufferedReader);
    
    localStreamTokenizer.resetSyntax();
    localStreamTokenizer.wordChars(0, 255);
    localStreamTokenizer.whitespaceChars(0, 32);
    localStreamTokenizer.eolIsSignificant(true);
    Vector localVector1 = new Vector();
    while (localStreamTokenizer.nextToken() == 10) {}
    if (localStreamTokenizer.ttype == -1) {
      throw new IOException("Unexpected EOF on matrix read.");
    }
    do
    {
      localVector1.addElement(Double.valueOf(localStreamTokenizer.sval));
    } while (localStreamTokenizer.nextToken() == -3);
    int i = localVector1.size();
    double[] arrayOfDouble = new double[i];
    for (int j = 0; j < i; j++) {
      arrayOfDouble[j] = ((Double)localVector1.elementAt(j)).doubleValue();
    }
    Vector localVector2 = new Vector();
    localVector2.addElement(arrayOfDouble);
    while (localStreamTokenizer.nextToken() == -3)
    {
      localVector2.addElement(arrayOfDouble = new double[i]);
      int k = 0;
      do
      {
        if (k >= i) {
          throw new IOException("Row " + localVector2.size() + " is too long.");
        }
        arrayOfDouble[(k++)] = Double.valueOf(localStreamTokenizer.sval).doubleValue();
      } while (localStreamTokenizer.nextToken() == -3);
      if (k < i) {
        throw new IOException("Row " + localVector2.size() + " is too short.");
      }
    }
    int k = localVector2.size();
    double[][] arrayOfDouble1 = new double[k][];
    localVector2.copyInto(arrayOfDouble1);
    return new Matrix(arrayOfDouble1);
  }
  
  private void checkMatrixDimensions(Matrix paramMatrix)
  {
    if ((paramMatrix.m != this.m) || (paramMatrix.n != this.n)) {
      throw new IllegalArgumentException("Matrix dimensions must agree.");
    }
  }

}
