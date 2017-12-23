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
		for(int i = 0; i < problems.length; i ++){
			double[] answer = net.calculate(getProblem(i));
			double[] solution = getSolution(i);
			double costSum = 0;
			for(int n = 0; n < outputSize; n ++){
				costSum += (solution[n] - answer[n]) * (solution[n] - answer[n]);
			}
			cost += costSum / (2 * problems.length);
		}
		return cost;
	}
	
}
