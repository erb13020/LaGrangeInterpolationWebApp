import java.io.Serializable;

public class LUDecomposition
  implements Serializable
{
  private double[][] LU;
  private int m;
  private int n;
  private int pivsign;
  private int[] piv;
  private static final long serialVersionUID = 1L;
  
  public LUDecomposition(Matrix paramMatrix)
  {
    this.LU = paramMatrix.getArrayCopy();
    this.m = paramMatrix.getRowDimension();
    this.n = paramMatrix.getColumnDimension();
    this.piv = new int[this.m];
    for (int i = 0; i < this.m; i++) {
      this.piv[i] = i;
    }
    this.pivsign = 1;
    
    double[] arrayOfDouble2 = new double[this.m];
    for (int j = 0; j < this.n; j++)
    {
      for (int k = 0; k < this.m; k++) {
        arrayOfDouble2[k] = this.LU[k][j];
      }
      double d;
      for (int k = 0; k < this.m; k++)
      {
        double[] arrayOfDouble1 = this.LU[k];
        
        int i1 = Math.min(k, j);
        d = 0.0D;
        for (int i2 = 0; i2 < i1; i2++) {
          d += arrayOfDouble1[i2] * arrayOfDouble2[i2];
        }
        arrayOfDouble1[j] = (arrayOfDouble2[k] -= d);
      }
      int k = j;
      for (int i1 = j + 1; i1 < this.m; i1++) {
        if (Math.abs(arrayOfDouble2[i1]) > Math.abs(arrayOfDouble2[k])) {
          k = i1;
        }
      }
      if (k != j)
      {
        for (int i1 = 0; i1 < this.n; i1++)
        {
          d = this.LU[k][i1];this.LU[k][i1] = this.LU[j][i1];this.LU[j][i1] = d;
        }
        int i1 = this.piv[k];this.piv[k] = this.piv[j];this.piv[j] = i1;
        this.pivsign = (-this.pivsign);
      }
      if (((j < this.m ? 1 : 0) & (this.LU[j][j] != 0.0D ? 1 : 0)) != 0) {
        for (int i1 = j + 1; i1 < this.m; i1++) {
          this.LU[i1][j] /= this.LU[j][j];
        }
      }
    }
  }
  
  public boolean isNonsingular()
  {
    for (int i = 0; i < this.n; i++) {
      if (this.LU[i][i] == 0.0D) {
        return false;
      }
    }
    return true;
  }
  
  public Matrix getL()
  {
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        if (i > j) {
          arrayOfDouble[i][j] = this.LU[i][j];
        } else if (i == j) {
          arrayOfDouble[i][j] = 1.0D;
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  
  public Matrix getU()
  {
    Matrix localMatrix = new Matrix(this.n, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.n; i++) {
      for (int j = 0; j < this.n; j++) {
        if (i <= j) {
          arrayOfDouble[i][j] = this.LU[i][j];
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  
  public int[] getPivot()
  {
    int[] arrayOfInt = new int[this.m];
    for (int i = 0; i < this.m; i++) {
      arrayOfInt[i] = this.piv[i];
    }
    return arrayOfInt;
  }
  
  public double[] getDoublePivot()
  {
    double[] arrayOfDouble = new double[this.m];
    for (int i = 0; i < this.m; i++) {
      arrayOfDouble[i] = this.piv[i];
    }
    return arrayOfDouble;
  }
  
  public double det()
  {
    if (this.m != this.n) {
      throw new IllegalArgumentException("Matrix must be square.");
    }
    double d = this.pivsign;
    for (int i = 0; i < this.n; i++) {
      d *= this.LU[i][i];
    }
    return d;
  }
  
  public Matrix solve(Matrix paramMatrix)
  {
    if (paramMatrix.getRowDimension() != this.m) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isNonsingular()) {
      throw new RuntimeException("Matrix is singular.");
    }
    int i = paramMatrix.getColumnDimension();
    Matrix localMatrix = paramMatrix.getMatrix(this.piv, 0, i - 1);
    double[][] arrayOfDouble = localMatrix.getArray();
    int k;
    int i1;
    for (int j = 0; j < this.n; j++) {
      for (k = j + 1; k < this.n; k++) {
        for (i1 = 0; i1 < i; i1++) {
          arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * this.LU[k][j];
        }
      }
    }
    for (int j = this.n - 1; j >= 0; j--)
    {
      for (k = 0; k < i; k++) {
        arrayOfDouble[j][k] /= this.LU[j][j];
      }
      for (k = 0; k < j; k++) {
        for (i1 = 0; i1 < i; i1++) {
          arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * this.LU[k][j];
        }
      }
    }
    return localMatrix;
  }
}