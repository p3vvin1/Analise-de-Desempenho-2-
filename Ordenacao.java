import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ordenacao {

    public static void bubble(List<Double> lista) {
        int tamanho = lista.size();
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - i - 1; j++) {
                if (lista.get(j) > lista.get(j + 1)) {
                    double temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }

    public static List<Double> quick(List<Double> lista) {
        if (lista.size() <= 1) {
            return lista;
        }
        double pivo = lista.get(lista.size() - 1);
        List<Double> menores = new ArrayList<>();
        List<Double> maiores = new ArrayList<>();

        for (int i = 0; i < lista.size() - 1; i++) {
            if (lista.get(i) <= pivo) {
                menores.add(lista.get(i));
            } else {
                maiores.add(lista.get(i));
            }
        }

        List<Double> listaOrdenada = new ArrayList<>(quick(menores));
        listaOrdenada.add(pivo);
        listaOrdenada.addAll(quick(maiores));
        return listaOrdenada;
    }

    public static List<Double> lerNumeros(String caminhoArquivo) throws IOException {
        List<Double> numeros = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(caminhoArquivo))) {
            while (scanner.hasNextLine()) {
                try {
                    numeros.add(Double.parseDouble(scanner.nextLine().trim()));
                } catch (NumberFormatException e) {
                    // Ignorar linhas inválidas
                }
            }
        }
        return numeros;
    }

    public static void salvarNumeros(List<Double> lista, String caminhoArquivo) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (double numero : lista) {
                escritor.write(numero + "\n");
            }
        }
    }

    public static void executarOrdenacao(String nomeMetodo, List<Double> numeros) {
        long tempoInicio = System.nanoTime();
        List<Double> numerosOrdenados = null;

        if ("bubble".equals(nomeMetodo)) {
            bubble(numeros);
            numerosOrdenados = numeros;
        } else if ("quick".equals(nomeMetodo)) {
            numerosOrdenados = quick(numeros);
        }

        long tempoFim = System.nanoTime();
        double tempoExecucao = (tempoFim - tempoInicio) / 1_000_000_000.0; // Tempo em segundos
        long memoriaUsada = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); // Memória em bytes

        try {
            salvarNumeros(numerosOrdenados, "saida-" + nomeMetodo.toLowerCase() + ".txt");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }

        System.out.println(nomeMetodo + " -> Tempo: " + tempoExecucao + " s | Memória: " + (memoriaUsada / 1024) + " KB");
    }

    public static void main(String[] args) {
        String arquivoEntrada = "arq.txt";

        try {
            List<Double> numeros = lerNumeros(arquivoEntrada);

            System.out.println("\nConfiguração do sistema: Ryzen 7 4000 series, 16GB RAM, SSD 256GB");

            // Executando Bubble Sort
            executarOrdenacao("bubble", new ArrayList<>(numeros));

            // Executando Quick Sort
            executarOrdenacao("quick", new ArrayList<>(numeros));

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
//