import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class TiendaJuegos{
    // Programa Laboratorio Estructura de Datos 3
public static class Game{
    private String name;
    private String category;
    private int price;
    private int quality;

    public Game(String name, String category, int price, int quality){
        this.name = name;
        this.category = category;
        this.price = price;
        this.quality = quality;
    }

    // Getters //
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public int getPrice(){
        return price;
    }
    public int getQuality(){
        return quality;
    }

    // Setters //
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setQuality(int quality){
        this.quality = quality;
    }
}

public static class Dataset{
    public ArrayList<Game> data;
    public String sortedByAttribute;

    public Dataset(ArrayList<Game> data){
        this.data = data;
        this.sortedByAttribute = "";
    }

    // ---------------------Métodos de búsqueda--------------------- //

    public ArrayList<Game> getGamesByPrice(int price){
        if("price".equals(sortedByAttribute)){
            return BusquedaBinariaPrecio(price);
        } else {
            return BusquedaLinealPrecio(price);
        }
    }
    public ArrayList<Game> getGamesByPriceRange(int lower, int higher){
        if("price".equals(sortedByAttribute)){
            return BusquedaBinariaPorRango(lower, higher);
        } else {
            return BusquedaLinealRango(lower, higher);
        }
    }
    public ArrayList<Game> getData(){
        return data;
    }
    public ArrayList<Game> getGamesByCategory(String category){
        if("category".equals(sortedByAttribute)){
            return BusquedaBinariaCategoria(category);
        } else {
            return BusquedaLinealCategoria(category);
        }
    }
    public ArrayList<Game> getGamesByQuality(int quality){
        if("quality".equals(sortedByAttribute)){
            return BusquedaBinariaCalidad(quality);
        } else {
            return BusquedaLinealCalidad(quality);
        }
    }

    // ---------------------Métodos de búsqueda lineal--------------------- //

    public ArrayList<Game> BusquedaLinealPrecio(int price){
        ArrayList<Game> JuegosPorPrecio = new ArrayList<>();
        for(Game game : data){
            if(game.getPrice() == price){
                JuegosPorPrecio.add(game);
            }
        }
        return JuegosPorPrecio;
    }
    public ArrayList<Game> BusquedaLinealRango(int lowerPrice, int higherPrice){
        ArrayList<Game> JuegosDentroDelRango = new ArrayList<>();
        for(Game game : data){
            if(game.getPrice() >= lowerPrice && game.getPrice() <= higherPrice){
                JuegosDentroDelRango.add(game);
            }
        }
        return JuegosDentroDelRango;
    }
    public ArrayList<Game> BusquedaLinealCategoria(String category){
        ArrayList<Game> JuegosPorCategoria = new ArrayList<>();
        for(Game game : data){
            if(game.getCategory().equals(category)){
                JuegosPorCategoria.add(game);
            }
        }
        return JuegosPorCategoria;
    }
    public ArrayList<Game> BusquedaLinealCalidad(int quality){
        ArrayList<Game> JuegosPorCalidad = new ArrayList<>();
        for(Game game : data){
            if(game.getQuality() == quality){
                JuegosPorCalidad.add(game);
            }
        }
        return JuegosPorCalidad;
    }

    // ---------------------Métodos de búsqueda binaria--------------------- //

    public ArrayList<Game> BusquedaBinariaPrecio(int price){
        ArrayList<Game> JuegosPorPrecio = new ArrayList<>();

        int inicio, mitad, fin;
        inicio = 0;
        fin = data.size() - 1;

        while(inicio <= fin){
            mitad = inicio + (fin - inicio) / 2;
            if(data.get(mitad).getPrice() == price){
                fin = mitad ;
            } else if(data.get(mitad).getPrice() < price){
                inicio = mitad + 1;
            } else {
                fin = mitad - 1;
            }
        }

        if(inicio >= data.size() || data.get(inicio).getPrice() != price) return JuegosPorPrecio;

        for(int i = inicio; i < data.size(); i++){
            if(data.get(i).getPrice() == price){
                JuegosPorPrecio.add(data.get(i));
            } else {
                break;
            }
        }
        return JuegosPorPrecio;
    }
    public ArrayList<Game> BusquedaBinariaPorRango(int lowerPrice, int higherPrice){
        ArrayList<Game> JuegosPorRango = new ArrayList<>();

        int inicio, mitad, fin, precioInicial, precioFinal;
        inicio = 0;
        fin = data.size() - 1;
        precioInicial = -1;

        while(inicio <= fin){
            mitad = inicio + (fin - inicio) / 2;
            if(data.get(mitad).getPrice() >= lowerPrice){
                precioInicial = mitad;
                fin = mitad;
            } else {
                inicio = mitad + 1;
            }
        }

        inicio = 0;
        fin = data.size() - 1;
        precioFinal = -1;

        while(inicio <= fin){
            mitad = inicio + (fin - inicio) / 2;
            if(data.get(mitad).getPrice() <= higherPrice){
                precioFinal = mitad;
                inicio = mitad + 1;
            } else {
                fin = mitad - 1;
            }
        }

        if(precioInicial <= precioFinal){
            for(int i = precioInicial; i <= precioFinal; i++){
                JuegosPorRango.add(data.get(i));
            }
        }
        return JuegosPorRango;
    }
    public ArrayList<Game> BusquedaBinariaCategoria(String category){
        ArrayList<Game> JuegosPorCategoria = new ArrayList<>();

        int inicio, mitad, fin;
        inicio = 0;
        fin = data.size() - 1;

        while(inicio <= fin){
            mitad = inicio + (fin - inicio) / 2;
            if(data.get(mitad).getCategory().equals(category)){
                fin = mitad - 1;
            } else if(data.get(mitad).getCategory().compareTo(category) < 0){
                inicio = mitad + 1;
            } else {
                fin = mitad - 1;
            }
        }

        if(inicio >= data.size() || !data.get(inicio).getCategory().equals(category)) return JuegosPorCategoria;

        for(int i = inicio; i < data.size(); i++){
            if(data.get(i).getCategory().equals(category)){
                JuegosPorCategoria.add(data.get(i));
            } else {
                break;
            }
        }
        return JuegosPorCategoria;
    }
    public ArrayList<Game> BusquedaBinariaCalidad(int quality){
        ArrayList<Game> JuegosPorCalidad = new ArrayList<>();

        int inicio, mitad, fin;
        inicio = 0;
        fin = data.size() - 1;

        while(inicio <= fin){
            mitad = inicio + (fin - inicio) / 2;
            if(data.get(mitad).getQuality() == quality){
                fin = mitad - 1;
            } else if(data.get(mitad).getQuality() < quality){
                inicio = mitad + 1;
            } else {
                fin = mitad - 1;
            }
        }

        if(inicio >= data.size() || data.get(inicio).getQuality() != quality) return JuegosPorCalidad;

        for(int i = inicio; i < data.size(); i++){
            if(data.get(i).getQuality() == quality){
                JuegosPorCalidad.add(data.get(i));
            } else {
                break;
            }
        }
        return JuegosPorCalidad;
    }

    // ---------------------Algoritmos de ordenamiento--------------------- //

    public void bubbleSort(Comparator<Game> comparador){
            for(int i = 0; i < data.size(); i++){
                for(int j = 0; j < data.size() - i-1; j++){
                    if(comparador.compare(data.get(j), data.get(j+1)) > 0){
                        Collections.swap(data, j, j+1);
                    }
                }
            }
    }
    public void insertionSort(Comparator<Game> comparador){
        for(int i = 1; i < data.size(); i++){
            Game key = data.get(i);
            int j = i - 1;

            while(j >= 0 && comparador.compare(data.get(j), key) > 0){
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }
    public void selectionSort(Comparator<Game> comparador){
        for(int i = 0; i < data.size(); i++){
            int numMin = i;
            for(int j = i + 1; j < data.size(); j++){
                if(comparador.compare(data.get(j), data.get(numMin)) < 0){
                    numMin = j;
                }
            }
            Collections.swap(data, i, numMin);
        }
    }
    public void mergeSort(Comparator<Game> comparador){
        if (data.size()> 1){
            ArrayList <Game> left = new ArrayList<>(data.subList(0, data.size()/2));
            ArrayList <Game> right = new ArrayList<>(data.subList(data.size()/2, data.size()));
            Dataset leftDataset =new Dataset(left);
            Dataset rightDataset = new Dataset(right);

            leftDataset.mergeSort(comparador);
            rightDataset.mergeSort(comparador);

            merge(left, right, comparador);
        }
    
    }
    public void merge(ArrayList<Game> left, ArrayList<Game> right, Comparator<Game> comparador){
        ArrayList<Game> merged = new ArrayList<>();
        int i=0, j=0;
        while (i < left.size() && j < right.size()){
            if (comparador.compare(left.get(i) , right.get(j)) <= 0) {
                merged.add(left.get(i++));
                }else {
                merged.add(right.get(j++));
                }
            }
            while (i < left.size()){
                merged.add(left.get(i++));
            }
            while (j < right.size()){
                merged.add(right.get(j++));
            }
            for(int k=0; k< merged.size(); k++){
                data.set(k, merged.get(k));
            }
        }
    public void quickSort(Comparator<Game> comparador){
        quickSortHelper(0, data.size() - 1, comparador);
    }
    private void quickSortHelper (int low, int high, Comparator<Game> comparador){
        if (low < high){
            int pi= partition(low, high, comparador);
            quickSortHelper(low, pi - 1, comparador);
            quickSortHelper(pi + 1, high, comparador);
        }
    }
    private int partition(int low, int high, Comparator<Game> comparador){
        Game pivot = data.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++){
            if (comparador.compare(data.get(j), pivot) <= 0){
                i++;
                Collections.swap(data, i, j);
            }
        }
        Collections.swap(data, i + 1, high);
        return i + 1;
    }

    // ---------------------Este metodo ordena por calidad ---------------------//

    public void countingSortCalidad(){
        int maxCalidad = 100;
        int[] cont = new int[maxCalidad + 1];
        ArrayList<Game> listaOrdenada = new ArrayList<>();

        for(int i = 0; i < data.size(); i++){
            listaOrdenada.add(null);
        }

        for(Game game : data){
            cont[game.getQuality()]++;
        }


        for(int i = 1; i <= maxCalidad; i++){
            cont[i] += cont[i - 1];
        }

        for(int i = data.size() - 1; i >= 0; i--){
            Game game = data.get(i);
            int calidad = game.getQuality();
            int indice = cont[calidad] - 1;
            listaOrdenada.set(indice, game);
            cont[calidad]--;
        }

        data = listaOrdenada;
        sortedByAttribute = "quality";
    }

    // ---------------------Métodos de ordenamiento--------------------- //

    public void sortByAlgorithm(String algorithm, String attribute){
        if(algorithm.equals("countingSort") && attribute.equals("quality")){
            countingSortCalidad();
            return;
        }

        Comparator<Game> comparador;

        switch (attribute){
            case "category":
                comparador = Comparator.comparing(Game::getCategory);
                break;
            case "quality":
                comparador = Comparator.comparingInt(Game::getQuality);
                break;
            case "price":
            default:
                comparador = Comparator.comparingInt(Game::getPrice);
                break;
        }
        switch (algorithm){
            case "bubbleSort":
                bubbleSort(comparador);
                break;
            case "insertionSort":
                insertionSort(comparador);
                break;
            case "selectionSort":
                selectionSort(comparador);
                break;
            case "mergeSort":
                mergeSort(comparador);
                break;
            case "quickSort":
                quickSort(comparador);
                break;
            default:
                Collections.sort(data, comparador);
        }
        this.sortedByAttribute = attribute;
    }
}
public static class GenerateData{
    private String[] nombres = {"Dragon", "Empire", "Quest", "Legends","Warrior"};
    private String[] categorias = {"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación"};

    private Random random = new Random();

    public ArrayList<Game> generarJuegos(int cantidad){
        ArrayList<Game> juegosGenerados = new ArrayList<>();

        for(int i = 0; i < cantidad; i++){
            String nombre = generarNombreRandom();
            String categoria = categorias[random.nextInt(categorias.length)];
            int precio = random.nextInt(70001);
            int calidad = random.nextInt(101);

            juegosGenerados.add(new Game(nombre, categoria, precio, calidad));
        }

        return juegosGenerados;
    }
    public String generarNombreRandom(){
        String nombre1 = nombres[random.nextInt(nombres.length)];
        String nombre2 = nombres[random.nextInt(nombres.length)];

        return nombre1 + nombre2;
    }
}
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        GenerateData generador = new GenerateData();
        ArrayList<Game> juegos = generador.generarJuegos(20);
        Dataset dataset = new Dataset(juegos);

        System.out.println("=== JUEGOS SIN ORDENAR ===");
        imprimirJuegos(dataset.getData());

        System.out.println("\n=== ORDENADO POR PRECIO (QUICKSORT) ===");
        dataset.sortByAlgorithm("quickSort", "price");
        imprimirJuegos(dataset.getData());

        System.out.println("\n=== ORDENADO POR CATEGORÍA (MERGESORT) ===");
        dataset.sortByAlgorithm("mergeSort", "category");
        imprimirJuegos(dataset.getData());

        System.out.println("\n=== ORDENADO POR CALIDAD (COUNTING SORT) ===");
        dataset.sortByAlgorithm("countingSort", "quality");
        imprimirJuegos(dataset.getData());

        // busquedas
        System.out.println("\n=== BUSQUEDA POR PRECIO (10000) ===");
        imprimirJuegos(dataset.getGamesByPrice(10000));

        System.out.println("\n=== BUSQUEDA POR RANGO DE PRECIO (5000-15000) ===");
        imprimirJuegos(dataset.getGamesByPriceRange(5000, 15000));

        System.out.println("\n=== BUSQUEDA POR CATEGORÍA (Aventura) ===");
        imprimirJuegos(dataset.getGamesByCategory("Estrategia"));

        System.out.println("\n=== BUSQUEDA POR CALIDAD (80) ===");
        imprimirJuegos(dataset.getGamesByQuality(80));
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " milisegundos");
    }

    // Método auxiliar para imprimir la lista de juegos
    private static void imprimirJuegos(ArrayList<Game> juegos) {
        if (juegos.isEmpty()) {
            System.out.println("No se encontraron juegos");
            return;
        }

        System.out.printf("%-20s %-15s %-10s %-10s%n", 
                         "Nombre", "Categoría", "Precio", "Calidad");
        System.out.println("--------------------------------------------------");
        
        for (Game juego : juegos) {
            System.out.printf("%-20s %-15s %-10d %-10d%n", 
                            juego.getName(), 
                            juego.getCategory(), 
                            juego.getPrice(), 
                            juego.getQuality());
        }
    }

}