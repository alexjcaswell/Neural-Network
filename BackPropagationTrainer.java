public class BackPropagationTrainer extends Trainer {

	private static final double SPEED = .1;

	@Override
	public void train(NeuralNetwork net, ProblemSet pSet) {

		Matrix[] wGradient = new Matrix[net.getLayerCount() - 1];
		Matrix[] bGradient = new Matrix[net.getLayerCount() - 1];
		for (int l = 1; l <= wGradient.length; l++) {
			wGradient[l - 1] = new Matrix(net.getLayerSize(l), net.getLayerSize(l - 1));
			bGradient[l - 1] = new Matrix(net.getLayerSize(l), 1);

			wGradient[l - 1].zero();
			bGradient[l - 1].zero();
		}

		for (int problemId = 0; problemId < pSet.getCount(); problemId++) {
			net.calculate(pSet.getMProblem(problemId));
			Matrix[] error = new Matrix[net.getLayerCount()];
			for (int l = 0; l < error.length; l++) {
				error[l] = new Matrix(net.getLayerSize(l), 1);
			}

			// calculate output layer error
			Matrix answer = pSet.getMSolution(problemId);

			int lastLayer = net.getLayerCount() - 1;
			error[lastLayer] = net.activation[lastLayer].minus(answer).elementMult(net.dSigmoid(lastLayer));

			// propagate error backwards
			for (int l = lastLayer - 1; l > 0; l--) {
				error[l] = net.weight[l].transpose().mult(error[l + 1]).elementMult(net.dSigmoid(l));
			}

			for (int l = 1; l < wGradient.length; l++) {
				bGradient[l - 1] = bGradient[l - 1].plus(error[l]);
			}

			for (int l = 1; l < net.getLayerCount(); l++) {
				wGradient[l - 1] = wGradient[l-1].plus(net.activation[l - 1].mult(error[l].transpose()).transpose());
			}
		}
		
		for (int l = 1; l < net.getLayerCount(); l++) {
			net.weight[l-1] = net.weight[l-1].minus(wGradient[l-1].scale(SPEED));
			net.bias[l-1] = net.bias[l-1].minus(bGradient[l-1].scale(SPEED));
		}
	}

}
