import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {
	
	/*Usaremos como referencia para los caracteres la tabla ASCII, donde a cada caracter le corresponde
	 * un numero entero distinto  2 a la 8 = 256 haciendo la conversion al sistema binario base 2.
	 */
	private static final int alfabeto = 256; // (ASCII)
	static int bytes = 0;
	/*esta tabla hash nos ayudara a guardar el codigo prefijo de cada caracter que se registre en el 
	 * texto ingresado
	 */
	static HashMap<Character, String> codigo = new HashMap<>();
	
	/* El siguiente metodo ubica cada caracter en su lugar correspondiente en un arreglo estatico que
	 * intenta simular el alfabeto de la tabla ASCII.
	 */
	static public int[] ASCII(final String datos) {
		int[] frecuencias = new int[alfabeto];
		for (char c : datos.toCharArray()) { // cuenta la frecuencia de cada caracter
			frecuencias[c]++;
			bytes++;
		}
		return frecuencias; // se retorna el arreglo est�tico de las frecuencias
	}
	//crea lo que denominamos como el �rbol de Huffman
	public static Node Tree(int[] frecuencias) {
		
		PriorityQueue<Node> tree = new PriorityQueue<>(); // aqui se usan los mont�culos binarios
		/*
		 * si un caracter de la tabla ASCII est� presente al menos una vez en la entrada, entonces se 
		 * crear� el nodo y se a�ade al mont�culo binario
		 */
		for (char i = 0; i < alfabeto; i++) {
			if (frecuencias[i] > 0) {
				tree.add(new Node(i, frecuencias[i], null, null));
			}
		}
		if (tree.size() == 1) {
			tree.add(new Node(1, null, null));
			
		}
		while(tree.size() > 1) {
			
			Node izquierda = tree.poll();
			Node derecha = tree.poll();
			Node padre = new Node(izquierda.frecuencia + derecha.frecuencia, izquierda, derecha);
			tree.add(padre);
		}
		return tree.poll();
	}
	

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String test = "";
		System.out.println("escriba el texto a codificar (Terminando con '-1'), puede incluir espacios," + "\n" +
		"cambios de linea y cualquier caracter especial presente en la tabla ASCII");
		System.out.println();
		while (true) {
			test += input.nextLine();
			test += '\n';
			if (test.endsWith("-1\n")) {
				test = test.substring(0, test.length() - 3);
				break;
			}
		}
		input.close();
		int[] ft = ASCII(test); 
		Node n = Tree(ft);
		n.building(n);
		System.out.println("la cantidad original de bits fue de: " + bytes * 8);
		
		System.out.println();
		System.out.println("Luego de la codificaci�n:");
		for (char letra : test.toCharArray()) {
			System.out.print(codigo.get(letra) );
		}
		
		System.out.println();
		
		System.out.println("la cantidad de bits utilizados fue de: " + Node.bits);
	}
}
