

public class Warstwa {
	Neuron [] neurony;
	int liczba_neuronow;
	double[] previousInput;
	public Warstwa(){
		neurony=null;
		liczba_neuronow=0;
	}
	public Warstwa(int liczba_wejsc,int liczba_neuronow){
		this.liczba_neuronow=liczba_neuronow;
		neurony=new Neuron[liczba_neuronow];
		for(int i=0;i<liczba_neuronow;i++)
			neurony[i]=new Neuron(liczba_wejsc);
	}
	
	double [] CalculateOutput(double [] input){
		previousInput = input;
		double [] wyjscie=new double[liczba_neuronow];
		for(int i=0;i<liczba_neuronow;i++)
			wyjscie[i]=neurony[i].CalculateOutput(input);
		return wyjscie;
	}
	
	public double[] CalculateLowerLayerDelta() {
		int lowerLayerNeuronCount = neurony[0].GetInputCount();
		double[] delta = new double[lowerLayerNeuronCount];
		
		for (int i = 0; i < lowerLayerNeuronCount; i++) {
			for (int j = 0; j < neurony.length; j++) {
				delta[i] += neurony[j].GetDeltaMultipliedByNthWeight(i);
			}
		}
		return delta;
	}
	
	
	
	public void SetDeltaInNeurons(double[] delta) {
		
		for(int i = 0; i<delta.length;i++ ) {
			neurony[i].SetDelta(delta[i]);		}
	}
	
	
	
	public void ChangeWeights() {
		for(int i=0; i<neurony.length; i++)
			neurony[i].ChangeWeights(previousInput);
	}
	
	public double[] oblicz_wyjscie(double[] wyjscie) {
		return null;
	}
	
	
	
	
	
	
	
}

