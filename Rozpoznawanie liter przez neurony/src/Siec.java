

import java.util.ArrayList;

public class Siec {
	
	static Warstwa [] warstwy;
	static int liczba_warstw;
	private static final int LICZBA_CYKLI = 1200;
	private static final double EPS = 0.2;
	
	public Siec(){
		warstwy=null;
	}
	public Siec(int liczba_wejsc,int liczba_warstw,int [] lnww){
		this.liczba_warstw=liczba_warstw;
		warstwy=new Warstwa[liczba_warstw];
		for(int i=0;i<liczba_warstw;i++)
			warstwy[i]=new Warstwa((i==0)?liczba_wejsc:lnww[i-1],lnww[i]);
	}
	double [] CalculateOutput(double [] input){
		double [] wyjscie=null;
		for(int i=0;i<liczba_warstw;i++)
			input=wyjscie=warstwy[i].CalculateOutput(input);
		return wyjscie;
	}
	
	void LearnFromSequence(ArrayList<double[]> inputSequence, ArrayList<double[]> outputSequence)
	{		
			
		for(int cykle = 0; cykle < LICZBA_CYKLI; cykle++){
			int correctAnswerCount = 0;
			for(int nrLitery = 0; nrLitery < inputSequence.size(); nrLitery++) {
				
				double[] output = inputSequence.get(nrLitery);
				double[] correctOutput = outputSequence.get(nrLitery);
				double delta[] = new double[correctOutput.length];
				
				for(int i=0;i<liczba_warstw;i++)
					output=warstwy[i].CalculateOutput(output);
				
				boolean closeEnough = true;
				for(int i=0; i<output.length; i++)
					if(Math.abs(delta[i] = (correctOutput[i]-output[i]))> EPS)
						closeEnough = false;
				
				if(closeEnough)
					correctAnswerCount++;
				
				for(int i = warstwy.length - 1; i>0; i--) {
					warstwy[i].SetDeltaInNeurons(delta);
					delta = warstwy[i].CalculateLowerLayerDelta();
				}
				warstwy[0].SetDeltaInNeurons(delta);
				
				for(int i = 0; i < warstwy.length; i++)
					warstwy[i].ChangeWeights();
			}
			
			if(correctAnswerCount == inputSequence.size())
				{System.out.println(cykle);
				break;}
		}
	}
	public void Siec1(int liczba_wejsc,int liczba_warstw,int [] lnww){
		this.liczba_warstw=liczba_warstw;
		warstwy=new Warstwa[liczba_warstw];
		for(int i=0;i<liczba_warstw;i++)
			warstwy[i]=new Warstwa((i==0)?liczba_wejsc:lnww[i-1],lnww[i]);
	}
	
	public static double[] oblicz_wyjscia(double[] data) {
		double [] wyjscie=null;
		for(int i=0;i<liczba_warstw;i++)
			 wyjscie = warstwy[i].oblicz_wyjscie(wyjscie);
		return wyjscie;
		
	}
	
	public static int ucz(double[] wartosci_zadane, double[] data, int[] neuronsPerLayer) {
		return 0;
	}
}
