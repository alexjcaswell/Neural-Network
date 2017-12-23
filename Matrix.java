import java.io.Serializable;

class Matrix implements Serializable{
	
	int width, height;
	double[][] mat;

	public Matrix(int height, int width){
		this.width = width;
		this.height = height;
		mat = new double[width][height];
	}

	public void set(double d){
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				mat[x][y] = d;
			}
		}
	}

	public void zero(){
		set(0);
	}

	public Matrix plus(Matrix m){
		assert(m.width == width && m.height == height);
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = mat[x][y] + m.mat[x][y];
			}
		}
		return r;
	}

	public Matrix minus(Matrix m){
		assert(m.width == width && m.height == height);
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = mat[x][y] - m.mat[x][y];
			}
		}
		return r;
	}

	public Matrix scale(double d){
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = mat[x][y] * d;
			}
		}
		return r;
	}

	public Matrix elementExp(){
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = Math.exp(mat[x][y]);
			}
		}
		return r;
	}

	public Matrix elementMult(Matrix m){
		assert(m.width == width && m.height == height);
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = mat[x][y] * m.mat[x][y];
			}
		}
		return r;
	}

	public Matrix elementDiv(Matrix m){
		assert(m.width == width && m.height == height);
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = mat[x][y] / m.mat[x][y];
			}
		}
		return r;
	}

	public Matrix transpose(){
		Matrix r = new Matrix(width, height);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[y][x] = mat[x][y];
			}
		}
		return r;
	}

	public Matrix mult(Matrix m){
		assert(width == m.height);
		Matrix r = new Matrix(height, m.width);
		// System.out.println(r);
		// System.out.println(this);
		// System.out.println(m);
		for(int x = 0; x < r.width; x ++){
			for(int y = 0; y < r.height; y ++){
				r.mat[x][y] = 0;
				for(int i = 0; i < width; i ++){
					r.mat[x][y] += mat[i][y] * m.mat[x][i];
				}
			}
		}
		return r;
	}



	public Matrix copy(){
		Matrix r = new Matrix(height, width);
		for(int x = 0; x < width; x ++){
			for(int y = 0; y < height; y ++){
				r.mat[x][y] = mat[x][y];
			}
		}
		return r;
	}

	public int numRows(){
		return height;
	}

	public int numCols(){
		return width;
	}

	public double get(int i){
		return mat[0][i];
	}

	public double get(int x, int y){
		return mat[x][y];
	}

	public void set(int i, double d){
		mat[0][i] = d;
	}

	public Matrix negative(){
		return scale(-1);
	}

	public String toString(){
		return width + ", " + height;
	}
}