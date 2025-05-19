import time
import tracemalloc

def bubble_sort(arr):
    """ Implementação do Bubble Sort """
    n = len(arr)
    for i in range(n - 1):
        for j in range(n - 1 - i):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
    return arr

def quick_sort(arr):
    """ Implementação do Quick Sort utilizando recursão """
    if len(arr) <= 1:
        return arr
    pivot = arr[-1]
    left = [x for x in arr[:-1] if x <= pivot]
    right = [x for x in arr[:-1] if x > pivot]
    return quick_sort(left) + [pivot] + quick_sort(right)

def load_numbers(file_path):
    """ Lê os números do arquivo e os retorna em uma lista """
    numbers = []
    with open(file_path, "r") as file:
        for line in file:
            try:
                numbers.append(float(line.strip()))
            except ValueError:
                pass
    return numbers

def save_numbers(arr, file_path):
    """ Salva os números ordenados em um arquivo """
    with open(file_path, "w") as file:
        file.writelines(f"{num}\n" for num in arr)

def run_sorting_algorithm(algorithm, numbers):
    """ Executa o algoritmo de ordenação e mede tempo e uso de memória """
    tracemalloc.start()
    start_time = time.time()

    sorted_numbers = algorithm(numbers.copy())

    end_time = time.time()
    peak_memory = tracemalloc.get_traced_memory()
    tracemalloc.stop()

    execution_time = round((end_time - start_time), 5)  # Tempo em segundos
    memory_usage = round(peak_memory / 1024, 2)  # Memória em KB

    return sorted_numbers, execution_time, memory_usage

def main():
    input_file = "arq.txt"
    numbers = load_numbers(input_file)

    # Executando Bubble Sort
    sorted_bubble, time_bubble, memory_bubble = run_sorting_algorithm(bubble_sort, numbers)
    save_numbers(sorted_bubble, "arq-saida-bubble.txt")

    # Executando Quick Sort
    sorted_quick, time_quick, memory_quick = run_sorting_algorithm(quick_sort, numbers)
    save_numbers(sorted_quick, "arq-saida-quick.txt")

    print("\nConfiguração do sistema: Ryzen 7 4000 series, 16GB RAM, SSD 256GB")
    print(f"Bubble Sort -> Tempo: {time_bubble} s | Memória: {memory_bubble} KB | Saída: arq-saida-bubble.txt")
    print(f"Quick Sort -> Tempo: {time_quick} s | Memória: {memory_quick} KB | Saída: arq-saida-quick.txt")

if __name__ == "__main__":
    main()
#