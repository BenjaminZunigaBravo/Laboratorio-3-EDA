import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class TiendaJuegos2 {
    public static class Game {
        private String nombre;
        private String categoria;
        private int precio;
        private int calidad;

        public Game(String nombre, String categoria, int precio, int calidad) {
            this.nombre = nombre;
            this.categoria = categoria;
            this.precio = precio;
            this.calidad = calidad;
        }

        public String getnombre() { return nombre; }
        public String getcategoria() { return categoria; }
        public int getprecio() { return precio; }
        public int getcalidad() { return calidad; }
    }

    public static class Dataset {
        public ArrayList<Game> data;
        public String sortedByAttribute;

        public Dataset(ArrayList<Game> data) {
            this.data = new ArrayList<>(data);
            this.sortedByAttribute = "";
        }

        public ArrayList<Game> getGamesByprecio(int precio) {
            return "precio".equals(sortedByAttribute) ? BusquedaBinariaPrecio(precio) : BusquedaLinealPrecio(precio);
        }

        public ArrayList<Game> getGamesByprecioRange(int inicio, int fin) {
            return "precio".equals(sortedByAttribute) ? BusquedaBinariaPorRango(inicio, fin) : BusquedaLinealRango(inicio, fin);
        }

        public ArrayList<Game> getGamesBycategoria(String categoria) {
            return "categoria".equals(sortedByAttribute) ? BusquedaBinariaCategoria(categoria) : BusquedaLinealCategoria(categoria);
        }

        public ArrayList<Game> getGamesBycalidad(int calidad) {
            return "calidad".equals(sortedByAttribute) ? BusquedaBinariaCalidad(calidad) : BusquedaLinealCalidad(calidad);
        }

        private ArrayList<Game> BusquedaLinealPrecio(int precio) {
            ArrayList<Game> result = new ArrayList<>();
            for(Game game : data) if(game.getprecio() == precio) result.add(game);
            return result;
        }

        private ArrayList<Game> BusquedaLinealRango(int inicio, int fin) {
            ArrayList<Game> result = new ArrayList<>();
            for(Game game : data) if(game.getprecio() >= inicio && game.getprecio() <= fin) result.add(game);
            return result;
        }

        private ArrayList<Game> BusquedaLinealCategoria(String categoria) {
            ArrayList<Game> result = new ArrayList<>();
            for(Game game : data) if(game.getcategoria().equals(categoria)) result.add(game);
            return result;
        }

        private ArrayList<Game> BusquedaLinealCalidad(int calidad) {
            ArrayList<Game> result = new ArrayList<>();
            for(Game game : data) if(game.getcalidad() == calidad) result.add(game);
            return result;
        }

        private ArrayList<Game> BusquedaBinariaPrecio(int precio) {
            ArrayList<Game> result = new ArrayList<>();
            int left = 0, right = data.size() - 1, mid, first = -1;
            
            while(left <= right) {
                mid = left + (right - left) / 2;
                if(data.get(mid).getprecio() >= precio) {
                    right = mid - 1;
                    if(data.get(mid).getprecio() == precio) first = mid;
                } else {
                    left = mid + 1;
                }
            }
            
            if(first == -1) return result;
            for(int i = first; i < data.size() && data.get(i).getprecio() == precio; i++) result.add(data.get(i));
            return result;
        }

        private ArrayList<Game> BusquedaBinariaPorRango(int inicio, int fin) {
            ArrayList<Game> result = new ArrayList<>();
            int left = 0, right = data.size() - 1, mid, start = -1, end = -1;
            
            while(left <= right) {
                mid = left + (right - left) / 2;
                if(data.get(mid).getprecio() >= inicio) {
                    right = mid - 1;
                    start = mid;
                } else {
                    left = mid + 1;
                }
            }
            
            left = 0;
            right = data.size() - 1;
            
            while(left <= right) {
                mid = left + (right - left) / 2;
                if(data.get(mid).getprecio() <= fin) {
                    left = mid + 1;
                    end = mid;
                } else {
                    right = mid - 1;
                }
            }
            
            if(start != -1 && end != -1 && start <= end) {
                for(int i = start; i <= end; i++) result.add(data.get(i));
            }
            return result;
        }

        private ArrayList<Game> BusquedaBinariaCategoria(String categoria) {
            ArrayList<Game> result = new ArrayList<>();
            int left = 0, right = data.size() - 1, mid, first = -1;
            
            while(left <= right) {
                mid = left + (right - left) / 2;
                int cmp = data.get(mid).getcategoria().compareTo(categoria);
                if(cmp >= 0) {
                    right = mid - 1;
                    if(cmp == 0) first = mid;
                } else {
                    left = mid + 1;
                }
            }
            
            if(first == -1) return result;
            for(int i = first; i < data.size() && data.get(i).getcategoria().equals(categoria); i++) result.add(data.get(i));
            return result;
        }

        private ArrayList<Game> BusquedaBinariaCalidad(int calidad) {
            ArrayList<Game> result = new ArrayList<>();
            int left = 0, right = data.size() - 1, mid, first = -1;
            
            while(left <= right) {
                mid = left + (right - left) / 2;
                if(data.get(mid).getcalidad() >= calidad) {
                    right = mid - 1;
                    if(data.get(mid).getcalidad() == calidad) first = mid;
                } else {
                    left = mid + 1;
                }
            }
            
            if(first == -1) return result;
            for(int i = first; i < data.size() && data.get(i).getcalidad() == calidad; i++) result.add(data.get(i));
            return result;
        }

        public void countingSortCalidad() {
            int maxCalidad = 100;
            int[] count = new int[maxCalidad + 1];
            ArrayList<Game> output = new ArrayList<>(Collections.nCopies(data.size(), null));

            for(Game game : data) count[game.getcalidad()]++;
            for(int i = 1; i <= maxCalidad; i++) count[i] += count[i-1];
            for(int i = data.size()-1; i >= 0; i--) output.set(--count[data.get(i).getcalidad()], data.get(i));
            
            data = output;
            sortedByAttribute = "calidad";
        }

        public void sortByAlgorithm(String algorithm, String attribute) {
            if("countingSort".equals(algorithm) && "calidad".equals(attribute)) {
                countingSortCalidad();
                return;
            }

            Comparator<Game> comparador = switch(attribute) {
                case "categoria" -> Comparator.comparing(Game::getcategoria);
                case "calidad" -> Comparator.comparingInt(Game::getcalidad);
                default -> Comparator.comparingInt(Game::getprecio);
            };

            switch(algorithm) {
                case "bubbleSort" -> bubbleSort(comparador);
                case "insertionSort" -> insertionSort(comparador);
                case "selectionSort" -> selectionSort(comparador);
                case "mergeSort" -> mergeSort(comparador);
                case "quickSort" -> quickSort(comparador);
                default -> Collections.sort(data, comparador);
            }
            sortedByAttribute = attribute;
        }

        private void bubbleSort(Comparator<Game> c) {
            for(int i = 0; i < data.size(); i++)
                for(int j = 0; j < data.size()-i-1; j++)
                    if(c.compare(data.get(j), data.get(j+1)) > 0)
                        Collections.swap(data, j, j+1);
        }

        private void insertionSort(Comparator<Game> c) {
            for(int i = 1; i < data.size(); i++) {
                Game key = data.get(i);
                int j = i-1;
                while(j >= 0 && c.compare(data.get(j), key) > 0) {
                    data.set(j+1, data.get(j));
                    j--;
                }
                data.set(j+1, key);
            }
        }

        private void selectionSort(Comparator<Game> c) {
            for(int i = 0; i < data.size(); i++) {
                int min = i;
                for(int j = i+1; j < data.size(); j++)
                    if(c.compare(data.get(j), data.get(min)) < 0) min = j;
                Collections.swap(data, i, min);
            }
        }

        private void mergeSort(Comparator<Game> c) {
            if(data.size() > 1) {
                ArrayList<Game> left = new ArrayList<>(data.subList(0, data.size()/2));
                ArrayList<Game> right = new ArrayList<>(data.subList(data.size()/2, data.size()));
                new Dataset(left).mergeSort(c);
                new Dataset(right).mergeSort(c);
                merge(left, right, c);
            }
        }

        private void merge(ArrayList<Game> left, ArrayList<Game> right, Comparator<Game> c) {
            int i = 0, j = 0, k = 0;
            while(i < left.size() && j < right.size())
                data.set(k++, c.compare(left.get(i), right.get(j)) <= 0 ? left.get(i++) : right.get(j++));
            while(i < left.size()) data.set(k++, left.get(i++));
            while(j < right.size()) data.set(k++, right.get(j++));
        }

        private void quickSort(Comparator<Game> c) {
            quickSort(0, data.size()-1, c);
        }

        private void quickSort(int low, int high, Comparator<Game> c) {
            if(low < high) {
                int pi = partition(low, high, c);
                if(pi > low) quickSort(low, pi-1, c);
                if(pi < high) quickSort(pi+1, high, c);
            }
        }

        private int partition(int low, int high, Comparator<Game> c) {
            Game pivot = data.get(high);
            int i = low-1;
            for(int j = low; j < high; j++)
                if(c.compare(data.get(j), pivot) <= 0)
                    Collections.swap(data, ++i, j);
            Collections.swap(data, i+1, high);
            return i+1;
        }
    }

    public static class GenerateData {
        private final String[] nombres = {"Dragon", "Empire", "Quest", "Legends", "Warrior"};
        private final String[] categorias = {"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación"};
        private final Random random = new Random();

        public ArrayList<Game> generarJuegos(int cantidad) {
            ArrayList<Game> juegos = new ArrayList<>(cantidad);
            for(int i = 0; i < cantidad; i++)
                juegos.add(new Game(
                    nombres[random.nextInt(nombres.length)] + nombres[random.nextInt(nombres.length)],
                    categorias[random.nextInt(categorias.length)],
                    random.nextInt(70001),
                    random.nextInt(101)
                ));
            return juegos;
        }
    }

    public static void main(String[] args) {
        GenerateData generador = new GenerateData();
        ArrayList<Game> juegos100 = generador.generarJuegos(100);
        ArrayList<Game> juegos10000 = generador.generarJuegos(10000);
        ArrayList<Game> juegos1M = generador.generarJuegos(1000000);

        System.out.println("=== Tabla precio ===");
        medicion("precio", juegos100, juegos10000);

        System.out.println("\n=== Tabla categoria ===");
        medicion("categoria", juegos100, juegos10000);

        System.out.println("\n=== Tabla calidad ===");
        medicion("calidad", juegos100, juegos10000);
        System.out.println("==Tabla del millon==");
        medicion("precio", juegos1M);
        medicion("categoria", juegos1M);
        medicion("calidad", juegos1M);
    }

    public static void medicion(String atributo, ArrayList<Game>... datasets) {
        String[] algoritmos = {"bubbleSort", "insertionSort", "selectionSort", "mergeSort", "quickSort", "collectionsSort"};
        
        for(String algoritmo : algoritmos) {
            for(ArrayList<Game> dataset : datasets) {
                long tiempo = 0;
                boolean timeout = false;
                
                for(int i = 0; i < 3 && !timeout; i++) {
                    Dataset ds = new Dataset(dataset);
                    long inicio = System.currentTimeMillis();
                    ds.sortByAlgorithm(algoritmo, atributo);
                    tiempo += System.currentTimeMillis() - inicio;
                    if(tiempo > 300000) timeout = true;
                }
                
                if(timeout) {
                    System.out.printf("Algoritmo: %-15s Tamaño: %-8d Atributo: %-10s Promedio: más de 300 segundos\n",
                            algoritmo, dataset.size(), atributo);
                } else {
                    System.out.printf("Algoritmo: %-15s Tamaño: %-8d Atributo: %-10s Promedio: %.2f ms\n",
                            algoritmo, dataset.size(), atributo, tiempo/3.0);
                }
            }
        }
    }
}