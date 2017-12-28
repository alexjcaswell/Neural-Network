import java.io.Serializable;

public class NeuralNetwork implements Serializable{
	
	public Matrix[] weight;
	// Vectors
	public Matrix[] bias;
	public Matrix[] activation;

	public NeuralNetwork(int inputSize, int... layerSize) {
		weight = new Matrix[layerSize.length];
		bias = new Matrix[layerSize.length];
		activation = new Matrix[layerSize.length + 1];

		activation[0] = new Matrix(inputSize, 1);
		for (int i = 0; i < layerSize.length; i++) {
			if (i == 0) {
				weight[i] = new Matrix(layerSize[i], inputSize);
				weight[i].rand(-.5,.5);
			} else {
				weight[i] = new Matrix(layerSize[i], layerSize[i - 1]);
				weight[i].rand(-.5,.5);
			}
			bias[i] = new Matrix(layerSize[i], 1);
			activation[i+1] = new Matrix(layerSize[i], 1);
		}
	}
	
	public Matrix calculate(Matrix input){
		assert(input.numRows() == activation[0].numRows());
		activation[0] = input.copy();
		calculate();
		return activation[activation.length - 1];
	}
	
	public double[] calculate(double... input) {
		Matrix matrix = new Matrix(input.length, 1);
		for(int i = 0; i < input.length; i ++){
			matrix.set(i, input[i]);
		}
		Matrix out = calculate(matrix);
		double[] output = new double[out.numRows()];
		for(int i = 0; i < output.length; i ++){
			output[i] = out.get(i);
		}
		return output;
	}
	
	public void calculate(){
		for(int i = 1; i < activation.length; i ++){
			activation[i] = sigmoid(weight[i-1].mult(activation[i-1]).plus(bias[i-1]));
			// for(int n = 0; n < activation[i].numRows(); n ++){
			// 	System.out.println(activation[i].get(n));
			// }
		}
	}
	
	public Matrix sigmoid(Matrix z){
		Matrix oneMatrix = new Matrix(z.numRows(),1);
		oneMatrix.set(1);
		return oneMatrix.elementDiv((oneMatrix.plus(z.negative().elementExp())));
	}
	
	public Matrix dSigmoid(int l){
		Matrix oneMatrix = new Matrix(activation[l].numRows(),1);
		Matrix sig = sigmoid(activation[l]);
		return sig.elementMult(oneMatrix.minus(sig));
	}

	public int getLayerCount(){
		return activation.length;
	}
	
	public double getActivation(int l, int n){
		return activation[l].get(n);
	}
	
	public double getWeight(int l, int n, int j){
		return weight[l].get(n, j);
	}
	
	public double getBias(int l, int n){
		return bias[l].get(n);
	}

	public int getLayerSize(int l) {
		return activation[l].numRows();
	}


	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("NeuralNetwork: ");
		for(int l = 0; l < activation.length; l ++){
			b.append(activation[l].numRows() + " ");
		}
		b.append("\n");
		for(int l = 1; l < activation.length; l ++){
			b.append("LAYER: " + l + "\n");
			b.append(weight[l-1]);
		}
		return b.toString();
	}
}
