import java.util.Scanner;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Main {

	static double[][] inn = {{1, 0},
		{0, 1},
		{1, 2},
		{2, 1}};
	static double[][] out = {{1, 0},
		{0, 1},
		{0, 1},
		{1, 0}};

	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		// NeuralNetwork net = new NeuralNetwork(784, 15, 10);
		// DataInputStream iIn1 = new DataInputStream(new FileInputStream("data/ti"));
		// DataInputStream lIn1 = new DataInputStream(new FileInputStream("data/tl"));				
		// ProblemSet ps = ProblemSet.parseFile(iIn1, lIn1);
		
		NeuralNetwork net = new NeuralNetwork(2, 4, 2);
		ProblemSet ps = new ProblemSet(inn, out);
		while(true){
			System.out.print("Enter instruction: ");
			String line = in.nextLine();
			String[] bits = line.split(" ");
			switch (bits[0]){
				case "quit":
					System.exit(0);
				break;
				case "net":
					int firstSize = Integer.parseInt(bits[1]);
					int[] sizes = new int[bits.length - 2];
					for(int i = 0; i < sizes.length; i ++){
						sizes[i] = Integer.parseInt(bits[i+2]);
					}
					net = new NeuralNetwork(firstSize, sizes);
					System.out.println("Created new neural network");
				break;
				case "set":
					try{
						DataInputStream iIn = new DataInputStream(new FileInputStream(bits[1]));
						DataInputStream lIn = new DataInputStream(new FileInputStream(bits[2]));
						ps = ProblemSet.parseFile(iIn, lIn);
						iIn.close();
						lIn.close();
					}catch(IOException e){
						System.out.println("Unrecognized file!");
					}
				break;
				case "train":
					int count = Integer.parseInt(bits[1]);
					for(int i = 0; i < count; i ++){
						BackPropagationTrainer.train(net, ps);
					}
				break;
				case "test":
					System.out.println(ps.cost(net));
				break;
				case "print":
					System.out.println(net);
				break;
				case "last":
					System.out.println(net.activation[net.activation.length-1]);
				break;
				default:
					System.out.println("UNRECOGNIZED");
			}
		}
		// ProblemSet p = new ProblemSet(inputs, outputs);
		// NeuralNetwork net = new NeuralNetwork(2, 5, 5, 2);
		// Trainer t = new BackPropagationTrainer();
		// for(int i = 0; i < 1000; i ++){
		// 	System.out.println(p.cost(net));
		// 	t.train(net, p);
		// }
	}

}