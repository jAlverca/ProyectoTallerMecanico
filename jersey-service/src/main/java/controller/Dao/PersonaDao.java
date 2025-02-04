package controller.Dao;

import models.Persona;
import models.TipoIdentificacion;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class PersonaDao extends AdapterDao<Persona> {
    private Persona persona;
    private LinkedList<Persona> listAll;

    public PersonaDao() {
        super(Persona.class);
        this.listAll = new LinkedList<>();
    }

    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LinkedList<Persona> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        persona.setId(id);
        this.persist(this.persona);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getPersona(), getPersona().getId() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Persona> list = getlistAll();
        Persona persona = get(id);
        if (persona != null) {
            list.delete(id);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Persona con id " + id + " no encontrada.");
            return false;
        }
    }


    public TipoIdentificacion getTipoIdentificacion(String tipo) {
        return TipoIdentificacion.valueOf(tipo);
    }

    public TipoIdentificacion[] getTipos() {
        return TipoIdentificacion.values();
    }

    
    // Funcion para comparar los atribuitos
    private boolean compare(Persona a, Persona b, Integer type, String field) {
        if (type == 1) {
            if (field.equalsIgnoreCase("apellido")) {
                return a.getApellidos().compareTo(b.getApellidos()) < 0;
            } else if (field.equalsIgnoreCase("nombre")) {
                return a.getNombres().compareTo(b.getNombres()) < 0;
            } else if (field.equalsIgnoreCase("telefono")) {
                return a.getNombres().compareTo(b.getNombres()) < 0;
            } else if (field.equalsIgnoreCase("id")) {
                return a.getId() < b.getId();
            }
        } else {
            if (field.equalsIgnoreCase("apellido")) {
                return a.getApellidos().compareTo(b.getApellidos()) > 0;
            } else if (field.equalsIgnoreCase("nombre")) {
                return a.getNombres().compareTo(b.getNombres()) > 0;
            } else if (field.equalsIgnoreCase("telefono")) {
                return a.getNombres().compareTo(b.getNombres()) > 0;
            } else if (field.equalsIgnoreCase("id")) {
                return a.getId() > b.getId();
            }
        }
        return false;
    }

    // Metodo de ordenacion Quicksort

    public LinkedList<Persona> quickSort(LinkedList<Persona> lista, Integer type, String field) throws Exception {

        Persona[] m = lista.toArray();
        quicksort(m, 0, m.length - 1, type, field);
        lista.toList(m);
        return lista;
    }

    private void quicksort(Persona[] m, int low, int high, Integer type, String field) {
        if (low < high) {
            int pivotIndex = partition(m, low, high, type, field);
            quicksort(m, low, pivotIndex - 1, type, field);
            quicksort(m, pivotIndex + 1, high, type, field);
        }
    }

    private int partition(Persona[] array, int low, int high, Integer type, String field) {
        Persona pivote = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(array[j], pivote, type, field)) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(Persona[] array, int i, int j) {
        Persona temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    

    // Metodo de ordenacion MergeSort

    public LinkedList<Persona> mergeSort(Integer type, String field) throws Exception {
        LinkedList<Persona> lista = listAll();
        Persona[] array = (Persona[]) lista.toArray();
        mergeSort(array, 0, array.length - 1, type, field);
        lista.toList(array);
        return lista;
    }

    private void mergeSort(Persona[] array, int low, int high, Integer type, String field) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(array, low, mid, type, field);
            mergeSort(array, mid + 1, high, type, field);
            merge(array, low, mid, high, type, field);
        }
    }

    private void merge(Persona[] array, int low, int mid, int high, Integer type, String field) {
        Persona[] left = new Persona[mid - low + 1];
        Persona[] right = new Persona[high - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = array[low + i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = array[mid + 1 + i];
        }

        int i = 0, j = 0, k = low;
        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j], type, field)) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    // Metodo de ordenacion ShellSort
    public LinkedList<Persona> shellSort(Integer type, String field) throws Exception {
        LinkedList<Persona> lista = listAll();
        Persona[] array = (Persona[]) lista.toArray();
        shellSort(array, type, field);
        lista.toList(array);
        return lista;
    }

    private void shellSort(Persona[] array, Integer type, String field) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                Persona temp = array[i];
                int j;
                for (j = i; j >= gap && compare(array[j - gap], temp, type, field); j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    public Persona buscarIdentificacion(String texto){
        Persona persona = null;
        LinkedList<Persona> listita = listAll();
        if(!listita.isEmpty()){
            Persona[] aux = listita.toArray();
            for(int i = 0; i < aux.length; i++){
                if(aux[i].getIdentificacion().equalsIgnoreCase(texto)){
                    persona = aux[i];
                    break;
                }
            }
        }
        return persona;
    }
    // Metodo de busqeuda binaria para cedula
    public Persona busquedaBinaria(String identificacion) throws Exception {
        LinkedList<Persona> listita = listAll();
    
        // Ordenar la lista por cedula usando el algoritmo de inserción
        Persona[] aux = listita.toArray();
        for (int i = 1; i < aux.length; i++) {
            Persona key = aux[i];
            int j = i - 1;
            while (j >= 0 && aux[j].getIdentificacion().compareTo(key.getIdentificacion()) > 0) {
                aux[j + 1] = aux[j];
                j = j - 1;
            }
            aux[j + 1] = key;
        }
    
        int low = 0;
        int high = aux.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (aux[mid].getIdentificacion().equals(identificacion)) {
                return aux[mid];
            } else if (aux[mid].getIdentificacion().compareTo(identificacion) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public Persona busquedaBinaria1(String attribute, String value) throws Exception {
        LinkedList<Persona> listita = listAll();

        // Ordenar la lista por cedula usando el algoritmo de inserción
        Persona[] aux = listita.toArray();
        for (int i = 1; i < aux.length; i++) {
            Persona key = aux[i];
            int j = i - 1;
            while (j >= 0 && aux[j].getIdentificacion().compareTo(key.getIdentificacion()) > 0) {
                aux[j + 1] = aux[j];
                j = j - 1;
            }
            aux[j + 1] = key;
        }

        int low = 0;
        int high = aux.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (attribute.equalsIgnoreCase("cedula")) {
                if (aux[mid].getIdentificacion().equals(value)) {
                    return aux[mid];
                } else if (aux[mid].getIdentificacion().compareTo(value) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return null;
    }

    // Metodo de busqueda lineal binaria
    public LinkedList<Persona> busquedaLinealBinaria(String attribute, String value) throws Exception {

        LinkedList<Persona> lista = new LinkedList<>();
        LinkedList<Persona> listita = listAll();
        if (!listita.isEmpty()) {
            Persona[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (attribute.equalsIgnoreCase("id")) {
                    if (aux[i].getId() == Integer.parseInt(value)) {
                        lista.add(aux[i]);
                    }
                } else if (attribute.equalsIgnoreCase("nombre")) {
                    if (aux[i].getNombres().equalsIgnoreCase(value)) {
                        lista.add(aux[i]);
                    }
                } else if (attribute.equalsIgnoreCase("apellido")) {
                    if (aux[i].getApellidos().equalsIgnoreCase(value)) {
                        lista.add(aux[i]);
                    }
                } else if (attribute.equalsIgnoreCase("telefono")) {
                    if (aux[i].getTelefono().equalsIgnoreCase(value)) {
                        lista.add(aux[i]);
                    }
                }
            }
        }
        return lista;
    }




}