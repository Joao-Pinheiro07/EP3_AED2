package program;

import java.util.Scanner;

import entities.Grafo;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o caminho do arquivo a ser lido: ");
		String arquivo = sc.nextLine();		
		
		long start = System.currentTimeMillis();
		Grafo.gerar_lista_de_adjacencia(arquivo);		
		Grafo.calcular_componentes_conexos();
		long checkPoint1 = System.currentTimeMillis();
		
		System.out.println();
		System.out.print("Digite o caminho do arquivo de saída: ");		
		String saida = sc.nextLine();
		
		long checkPoint2 = System.currentTimeMillis();
		Grafo.gerar_tabela(saida);
		long end = System.currentTimeMillis();
		
		long tempo = (checkPoint1 - start) + (end - checkPoint2);
		System.out.println("Tempo de leitura: " + tempo + " milissegundos.");
		
		sc.close();
	}

}
