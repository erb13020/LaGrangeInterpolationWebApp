import Jama.util.Maths;
import java.io.Serializable;

public class QRDecomposition
  implements Serializable
{
  private double[][] QR;
  private int m;
  private int n;
  private double[] Rdiag;
  private static final long serialVersionUID = 1L;
  
  public QRDecomposition(Matrix paramMatrix)
  {
    this.QR = paramMatrix.getArrayCopy();
    this.m = paramMatrix.getRowDimension();
    this.n = paramMatrix.getColumnDimension();
    this.Rdiag = new double[this.n];
    for (int i = 0; i < this.n; i++)
    {
      double d1 = 0.0D;
      for (int j = i; j < this.m; j++) {
        d1 = Maths.hypot(d1, this.QR[j][i]);
      }
      if (d1 != 0.0D)
      {
        if (this.QR[i][i] < 0.0D) {
          d1 = -d1;
        }
        for (int j = i; j < this.m; j++) {
          this.QR[j][i] /= d1;
        }
        this.QR[i][i] += 1.0D;
        for (int j = i + 1; j < this.n; j++)
        {
          double d2 = 0.0D;
          for (int k = i; k < this.m; k++) {
            d2 += this.QR[k][i] * this.QR[k][j];
          }
          d2 = -d2 / this.QR[i][i];
          for (int k = i; k < this.m; k++) {
            this.QR[k][j] += d2 * this.QR[k][i];
          }
        }
      }
      this.Rdiag[i] = (-d1);
    }
  }
  
  public boolean isFullRank()
  {
    for (int i = 0; i < this.n; i++) {
      if (this.Rdiag[i] == 0.0D) {
        return false;
      }
    }
    return true;
  }
  
  public Matrix getH()
  {
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.m; i++) {
      for (int j = 0; j < this.n; j++) {
        if (i >= j) {
          arrayOfDouble[i][j] = this.QR[i][j];
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  
  public Matrix getR()
  {
    Matrix localMatrix = new Matrix(this.n, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < this.n; i++) {
      for (int j = 0; j < this.n; j++) {
        if (i < j) {
          arrayOfDouble[i][j] = this.QR[i][j];
        } else if (i == j) {
          arrayOfDouble[i][j] = this.Rdiag[i];
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  
  public Matrix getQ()
  {
    Matrix localMatrix = new Matrix(this.m, this.n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = this.n - 1; i >= 0; i--)
    {
      for (int j = 0; j < this.m; j++) {
        arrayOfDouble[j][i] = 0.0D;
      }
      arrayOfDouble[i][i] = 1.0D;
      for (int j = i; j < this.n; j++) {
        if (this.QR[i][i] != 0.0D)
        {
          double d = 0.0D;
          for (int k = i; k < this.m; k++) {
            d += this.QR[k][i] * arrayOfDouble[k][j];
          }
          d = -d / this.QR[i][i];
          for (int k = i; k < this.m; k++) {
            arrayOfDouble[k][j] += d * this.QR[k][i];
          }
        }
      }
    }
    return localMatrix;
  }
  
  public Matrix solve(Matrix paramMatrix)
  {
    if (paramMatrix.getRowDimension() != this.m) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isFullRank()) {
      throw new RuntimeException("Matrix is rank deficient.");
    }
    int i = paramMatrix.getColumnDimension();
    double[][] arrayOfDouble = paramMatrix.getArrayCopy();
    int k;
    for (int j = 0; j < this.n; j++) {
      for (k = 0; k < i; k++)
      {
        double d = 0.0D;
        for (int i2 = j; i2 < this.m; i2++) {
          d += this.QR[i2][j] * arrayOfDouble[i2][k];
        }
        d = -d / this.QR[j][j];
        for (int i2 = j; i2 < this.m; i2++) {
          arrayOfDouble[i2][k] += d * this.QR[i2][j];
        }
      }
    }
    for (int j = this.n - 1; j >= 0; j--)
    {
      for (k = 0; k < i; k++) {
        arrayOfDouble[j][k] /= this.Rdiag[j];
      }
      for (k = 0; k < j; k++) {
        for (int i1 = 0; i1 < i; i1++) {
          arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * this.QR[k][j];
        }
      }
    }
    return new Matrix(arrayOfDouble, this.n, i).getMatrix(0, this.n - 1, 0, i - 1);
  }
}
