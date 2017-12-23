public class Main {
	
	static double[][] inputs = {
			{0,0},
			{1,0},
			{0,1},
			{1,1},
	};
	
	static double[][] outputs = {
			{1,0},
			{-1,0},
			{1,-1},
			{-1,-1},
	};

	public static void main(String[] args) {
		ProblemSet p = new ProblemSet(inputs, outputs);
		NeuralNetwork net = new NeuralNetwork(2, 5, 5, 2);
		Trainer t = new BackPropagationTrainer();
		for(int i = 0; i < 1000; i ++){
			System.out.println(p.cost(net));
			t.train(net, p);
		}
	}

}