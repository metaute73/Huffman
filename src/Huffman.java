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
		return frecuencias; // se retorna el arreglo estático de las frecuencias
	}
	//crea lo que denominamos como el árbol de Huffman
	public static Node Tree(int[] frecuencias) {
		
		PriorityQueue<Node> tree = new PriorityQueue<>(); // aqui se usan los montículos binarios
		/*
		 * si un caracter de la tabla ASCII está presente al menos una vez en la entrada, entonces se 
		 * creará el nodo y se añade al montículo binario
		 */
		for (char i = 0; i < alfabeto; i++) {
			if (frecuencias[i] > 0) {
				tree.add(new Node(i, frecuencias[i], null, null));
			}
		}
		//Lo siguiente controla el caso en que la entrada que se de tenga tamaño uno (solo un caracter)
		if (tree.size() == 1) {
			tree.add(new Node(1, null, null));
			
		}
		/* 
		 * Este ciclo arma el árbol de Huffman, ya que el montículo binario implementado como la cola
		 * de prioridad, organiza los elementos de manera ascendente, y se arman las ramas del árbol de 
		 * acuerdo al algoritmo de la codificación Huffman (Se escogen las frecuencias más bajas entre las
		 * presentes y ses asigna como padre la suma de las frecuencias), esto se hará hasta que
		 * solo quede un elemento presente en la cola, el cual será la raíz del árbol.
		 */
		while(tree.size() > 1) {
			
			Node izquierda = tree.poll();
			Node derecha = tree.poll();
			Node padre = new Node(izquierda.frecuencia + derecha.frecuencia, izquierda, derecha);
			tree.add(padre); // se añade el padre a la cola de prioridad para compararse con las otras frecuencias
		}
		return tree.poll();// se retorna la raíz del árbol.
	}
	

	public static void main(String[] args) {
		/*
		 * Se pide que el usuario ingrese el texto a ser codificado, y se dan las instrucciones de cómo hacerlo
		 * en detalle:
		 */
		Scanner input = new Scanner(System.in);
		String test = "";
		System.out.println("Escriba el texto a codificar (Terminando con '-1' y Enter), puede incluir espacios," + "\n" +
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
		int[] ft = ASCII(test); // se crea la tabla de frecuencias ASCII
		Node n = Tree(ft); // raíz del árbol
		n.building(n); // se recorre el árbol en en-orden para asignar los strings de 0's y 1's
		/*
		 * Uno de los objetivos de este código es resaltar la compresión del texto, y se intenta mostrar
		 * cuántos bits hubiese tomado la codificación origninal.
		 */
		System.out.println("la cantidad original de bits fue de: " + bytes * 8);
		
		System.out.println();
		System.out.println("Luego de la codificación:");
		for (char letra : test.toCharArray()) {
			System.out.print(codigo.get(letra) );
		}
		
		System.out.println();
		
		System.out.println("la cantidad de bits utilizados fue de: " + Node.bits);
	}
}
