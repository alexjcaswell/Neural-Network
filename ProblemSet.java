import java.io.DataInputStream;
import java.io.IOException;

public class ProblemSet {
	
	public int inputSize, outputSize;
	private double[][] problems, solutions;	//index, data
	
	public ProblemSet(double[][] problems, double[][] solutions){
		this.problems = problems;
		this.solutions = solutions;
		inputSize = problems[0].length;
		outputSize = solutions[0].length;
		assert(problems.length == solutions.length);
		for(int i = 0; i < problems.length; i ++){
			assert(problems[i].length == inputSize);
			assert(solutions[i].length == outputSize);
		}
	}
	
	public double[] getProblem(int i){
		return problems[i];
	}
	
	public double[] getSolution(int i){
		return solutions[i];
	}
	
	public Matrix getMProblem(int i){
		return toMat(getProblem(i));
	}
	
	public Matrix getMSolution(int i){
		return toMat(getSolution(i));
	}
	
	private Matrix toMat(double[] a){
		Matrix r = new Matrix(a.length, 1);
		for(int i = 0; i < a.length; i ++){
			r.set(i, a[i]);
		}
		return r;
	}
	
	public int getCount(){
		return problems.length;
	}
	
	public double cost(NeuralNetwork net){
		double cost = 0;
		int correct = 0;
		for(int i = 0; i < problems.length; i ++){
			double[] answer = net.calculate(getProblem(i));
			double[] solution = getSolution(i);
			double costSum = 0;
			double max = answer[0];
			int mI = 0;
			int aI = 0;
			for(int n = 0; n < outputSize; n ++){
				costSum += (solution[n] - answer[n]) * (solution[n] - answer[n]);
				// System.out.println(answer[n]);
				if(answer[n] > max){
					max = answer[n];
					mI = n;
				}
				if(solution[n] == 1){
					aI = n;
				}
			}
			// System.out.println(mI + ", " + aI);
			if(mI == aI){
				correct ++;
			}
			cost += costSum / (2 * problems.length);
		}
		System.out.println("Correct percent: " + (((double)correct) / solutions.length) * 100);
		return cost;
	}

	public static ProblemSet parseFile(DataInputStream pIn, DataInputStream aIn)throws IOException{
		System.out.println("Image magic number: " + pIn.readInt());
		int pItemCount = pIn.readInt();
		System.out.println("Image item count: " + pItemCount);

		System.out.println("Label magic number: " + aIn.readInt());
		int aItemCount = aIn.readInt();
		System.out.println("Label item count: " + aItemCount);

		if(pItemCount != aItemCount){
			System.out.println("Item counts don't match!");
			return null;
		}
		int rows = pIn.readInt();
		int cols = pIn.readInt();

		double[][] problems = new double[pItemCount/100][rows * cols];
		double[][] solutions = new double[aItemCount/100][10];

		for(int i = 0; i < problems.length; i ++){
			if(i%1000 == 0){
				System.out.println((double)i / pItemCount);
			}
			for(int c = 0; c < cols; c ++){
				for(int r = 0; r < rows; r ++){
					problems[i][r + c * rows] = ((double)pIn.readByte()) / 256;
				}
			}
			int label = (int)aIn.readByte();
			for(int n = 0; n < 10; n ++){
				solutions[i][n] = (n == label)? 1d : 0d;
			}
		}

		return new ProblemSet(problems, solutions);
	}
	
}
