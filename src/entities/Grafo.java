package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class Grafo {
	private static Map<String, Set<String>> listaDeAdjacencia = new HashMap<String, Set<String>>();
	private static Map<String, Boolean> marcados = new HashMap<>();
	private static List<Set<String>> componentesConexos = new LinkedList<>();
	private static Map<Integer, Integer> tabela = new HashMap<>();
	
	public static Map<String, Set<String>> getListaDeAdjacencia() {
		return listaDeAdjacencia;
	}	

	public static void gerar_lista_de_adjacencia(String arquivo) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(arquivo));
			bf.readLine(); // vertices
			bf.readLine(); // arestas
			String linha = bf.readLine();
			int i = 1;
			while (linha != null) {
				String[] dados = linha.split(" ");

				adicionar_aresta(dados[0], dados[1]);
				adicionar_aresta(dados[1], dados[0]);

				System.out.println("Linha " + i + " lida...");
				i = i + 1;
				linha = bf.readLine();
			}
			bf.close();
		}catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
    }
	

	private static void adicionar_aresta(String id1, String id2) {
		Set<String> set = listaDeAdjacencia.get(id1);
		if (set == null) set = new HashSet<String>();
		set.add(id2);
		listaDeAdjacencia.put(id1, set);
	}
	
	public static void calcular_componentes_conexos() {
		for(Map.Entry<String, Set<String>> entry : listaDeAdjacencia.entrySet()) {
			if(marcados.get(entry.getKey()) == null) {
				Set<String> componente = new HashSet<String>();
				componentesConexos.add(componente);
				busca_em_profundidade(entry.getKey(), componente);
			}
		}
	}
	
	private static void busca_em_profundidade(String vertice, Set<String> componente) {
		Stack <String> pilha = new Stack<String>();
		marcados.put(vertice, true);
		pilha.push(vertice);
		componente.add(vertice);
		while(!pilha.empty()) {
			String atual = pilha.pop();
			for(String id: listaDeAdjacencia.get(atual)) {
				if(marcados.get(id) == null) {
					componente.add(id);
					marcados.put(id, true);
					pilha.push(id);
				}
			}
		}		
	}	
	
	private static void adicionarNaTabela(Set<String> componente) {
		int tamanho  = componente.size();
		int frequencia;
		if(tabela.get(tamanho) == null) frequencia = 0;
		else frequencia = tabela.get(tamanho);
		frequencia++;
		tabela.put(tamanho, frequencia);
	}
	
	public static void gerar_tabela(String saida) {
		for(Set<String> componente: componentesConexos) adicionarNaTabela(componente);		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saida));
			bw.write("Tamanho, Frequência");
			for(Map.Entry<Integer, Integer> entry : tabela.entrySet()){
				bw.newLine();
				bw.write(entry.getKey() + ", " + entry.getValue());
			}
			bw.close();
		}catch(IOException e) {
			e.getMessage();
			e.printStackTrace();
		}		
	}	
	
}
