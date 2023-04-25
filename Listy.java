import java.util.Scanner;


public class Listy {
	static class Elem{
		String nazwa;
		int populacja;
		Elem nast;
		static Elem dodaj(String naz,int pop,Elem lista){
			Elem q=new Elem();
			q.nazwa=naz;
			q.populacja = pop;
			q.nast=lista;
			return q;
		}
		static Elem wstaw(String naz, int pop, Elem lista){
			if(lista==null)
				return dodaj(naz,pop,lista);
			if(naz.compareTo(lista.nazwa)<0)
				return dodaj(naz,pop,lista);
			if(naz.compareTo(lista.nazwa)==0) {
				System.out.println("Blad " + lista.nazwa + " jest juz w liscie ");
				return lista;
			}
			lista.nast=wstaw(naz,pop,lista.nast);
			return lista;
		}
		
		static void wypisz(Elem lista){
			while(lista!=null){
				System.out.println(lista.nazwa + " : " + lista.populacja);
				lista=lista.nast;
				}
			}
		}
	

	
	public static void main(String[] args) {
		Elem lista=null;
		Scanner input_m=new Scanner(System.in);
		Scanner input_n=new Scanner(System.in);
		int n;
		System.out.print("Ile miast wprowadzamy? : ");
		n = input_n.nextInt();
		System.out.println();
		for(int i=0;i<n;i++)
		{
		System.out.print("Nazwa miasta: ");
		String naz = input_m.nextLine();
		System.out.print("Populacja miasta: ");
		int pop = input_n.nextInt();
			lista=Elem.wstaw(naz, pop, lista);
			System.out.println();
		}
		System.out.println();
		/*lista=Elem.wstaw("Warszawa", 345, lista);
		lista=Elem.wstaw("Poznan", 234, lista);
		lista=Elem.wstaw("Radom", 689, lista);
		lista=Elem.wstaw("Radom", 987, lista);
		lista=Elem.wstaw("Wroclaw", 456, lista);*/
		Elem.wypisz(lista);
	}

}
