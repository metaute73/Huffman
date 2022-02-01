public class Node implements Comparable<Node> {
	
	private char caracter;
	static int bits = 0;
	public int frecuencia;
	public Node hijoIzquierdo;
	public Node hijoDerecho;
	public String s = "";
	
        //crear un constructor para hojas, pues se permite los caracteres
	public Node(char caracter, int frecuencia, Node hijoIzquierdo, Node hijoDerecho) {
		this.caracter = caracter;
		this.frecuencia = frecuencia;
		this.hijoIzquierdo = hijoIzquierdo;
		this.hijoDerecho = hijoDerecho;
	}
        
        //constructor para crear un nodo sin necesidad de poner un caracter(para hojas)
	public Node(int frecuencia, Node hijoIzquierdo, Node hijoDerecho) {
		this.frecuencia = frecuencia;
		this.hijoIzquierdo = hijoIzquierdo;
		this.hijoDerecho = hijoDerecho;
	}
	boolean hoja() {
		return this.hijoIzquierdo == null && this.hijoDerecho == null;
	}

	
        //compara dos numeors y si son diferentes retorna 1 o -1 sino, retorna la comparacion entre caracteres
        @Override
	public int compareTo(Node o) {
		final int comparacionFrecuencia = Integer.compare(this.frecuencia, o.frecuencia);
		if (comparacionFrecuencia != 0) {
			return comparacionFrecuencia;
		}
		return Integer.compare(this.caracter, o.caracter);
	}
	public void building(Node root) {
		buildingRecursive(root, "");
		
	}
        
        //metodo recursivo para construir el arbol con base en si sus hojas tienen hijos
	public void buildingRecursive(Node root, String str) {
		if (!root.hoja()) {
			buildingRecursive(root.hijoIzquierdo, str + '0');
			buildingRecursive(root.hijoDerecho, str + '1');
		}else {
			root.s = str;
			if(root.caracter > 0) {
				Huffman.codigo.put(root.caracter, str);
				bits += str.length()*root.frecuencia;
				//System.out.println(root.caracter);
				//System.out.println(root.s);
			}
			
		}
		
	}
}