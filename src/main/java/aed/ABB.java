package aed;
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz;
    private int cardinal;
    private int altura;

    private class Nodo {
        private T valor;
        private Nodo hijoDerecha;
        private Nodo hijoIzquierda;
        private Nodo padre;

        Nodo (T v){
            valor = v;
            hijoDerecha = null;
            hijoIzquierda = null;
            padre = null;
        }
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
        altura = 0;
    }

    public int cardinal() {
        return this.cardinal;
    }

    public T minimo(){
        Nodo apuntado = this.raiz;
        while(apuntado.hijoIzquierda != null ){
            apuntado = apuntado.hijoIzquierda;
        }
        return apuntado.valor;
    }

    public T maximo(){
        Nodo apuntado = this.raiz;
        while(apuntado.hijoDerecha != null ){
            apuntado = apuntado.hijoDerecha;
        }
        return apuntado.valor;
    }

    public void insertar(T elem){
        if(this.pertenece(elem)){//caso pertenece al arbol => no hago nada
        }else{
            Nodo arbol = this.raiz;
            Nodo nuevo = new Nodo (elem);
            if (this.raiz == null){//caso arbol esta vacio
                this.raiz = nuevo;
                this.cardinal += 1;
                this.altura += 1;
            }else{//se inserta en algun lugar del arbol
                this.insertarRecursivo(arbol, nuevo);
            } 
        }
    }
    
    public boolean pertenece(T elem){
        Nodo apuntado = this.raiz;
        Boolean res = pertenceRecursivo(elem, apuntado);
        return res;
    }

    public void eliminar(T elem) {
        if (!this.pertenece(elem)) {  // Caso A: no pertenece
            return;
        } 
        Nodo nodo = encontrarNodo(this.raiz, elem);
        if (nodo == null) {  
            return;  
        }
        Nodo padre = nodo.padre;  
        // Caso B: es raíz sin hijos
        if (nodo == this.raiz && nodo.hijoDerecha == null && nodo.hijoIzquierda == null) {
            this.raiz = null;
            this.cardinal = 0;
            return;
        } 
        switch (cantidadHijos(nodo)) {  // Caso C: 0/1/2 hijos, no raíz
            case 0:
                this.cardinal -= 1;
                if (padre != null) {  
                    if (padre.hijoIzquierda == nodo) {
                        padre.hijoIzquierda = null;
                    } else {
                        padre.hijoDerecha = null;
                    }
                }
                nodo.padre = null; 
                break;   
            case 1:
                Nodo huerfano = (nodo.hijoDerecha == null) ? nodo.hijoIzquierda : nodo.hijoDerecha;
                if (padre != null) {  
                    if (padre.hijoIzquierda == nodo) {
                        padre.hijoIzquierda = huerfano;
                    } else {
                        padre.hijoDerecha = huerfano;
                    }
                } else {  
                    this.raiz = huerfano;
                }
                if (huerfano != null) { 
                    huerfano.padre = padre; 
                }
                this.cardinal -= 1;
                break;
            case 2:
                Nodo sucesor = sucesorInmediato(nodo.hijoDerecha);
                if (sucesor != null) {  
                    sucesor.padre = nodo.padre; 
                    if (nodo.padre == null) {  // Si es raíz
                        this.raiz = sucesor;
                    } else if (nodo.padre.hijoIzquierda == nodo) {
                        nodo.padre.hijoIzquierda = sucesor;
                    } else {
                        nodo.padre.hijoDerecha = sucesor;
                    }
                    sucesor.hijoIzquierda = nodo.hijoIzquierda; 
                    if (nodo.hijoDerecha != sucesor) { 
                        sucesor.hijoDerecha = nodo.hijoDerecha; 
                    }
                }
                this.cardinal -= 1;
                break;
        }
    }    

    public String toString(){
        Iterador<T> puntero = new ABB_Iterador();
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        while(puntero.haySiguiente()){
            buffer.append(puntero.siguiente());
            if(puntero.haySiguiente()){
                buffer.append(",");
            }
        }
        buffer.append("}");
        return buffer.toString();
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;
        private Nodo _prev; 

        public boolean haySiguiente() {            
            return this._actual != null; 
        }
    
        public T siguiente() {
            Nodo actualViejo = this._actual;
            this._prev = actualViejo;
            Nodo siguiente = hallarNodoSiguiente(this._actual);
            this._actual = siguiente;
            return actualViejo.valor;    
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }



    //auxiliares
    private Nodo anteriorTarget(Nodo target){
        Nodo actual = target;
        if (actual == null){
            return null;
        }
        return null;
    }

    //paso recursivo de la diapo para pertenece
    private Boolean pertenceRecursivo (T target, Nodo nodo){
        if (nodo == null){
            return false;
        }else if (nodo.valor.equals(target)){
            return true;
        }else if (nodo.valor.compareTo(target)>0){//nodo>target
            return pertenceRecursivo(target, nodo.hijoIzquierda);
        }else{
            return pertenceRecursivo(target, nodo.hijoDerecha);
        }
    }

    //modificacion de perteneceRecursivo, devuele nodo anterior de la busqueda
    private Nodo perteneceRecursivoAnterior (T target, Nodo nodo){
        if (nodo == null){
            return null;
        }else if (nodo.valor.equals(target)){
            return nodo.padre;
        }else if (nodo.valor.compareTo(target)>0){//nodo>target
            return perteneceRecursivoAnterior(target, nodo.hijoIzquierda);
        }else{
            return perteneceRecursivoAnterior(target, nodo.hijoDerecha);
        }
    }

    //insertar un nuevo nodo
    private void insertarRecursivo(Nodo arbol, Nodo nuevo){
        if(arbol == null){
            return;
        }if (nuevo.valor.compareTo(arbol.valor)>0){ //nuevo>arbol o sea vamos por la derecha
            if(arbol.hijoDerecha == null){//append del nodo nuevo a la derecha
                arbol.hijoDerecha = nuevo;
                nuevo.padre = arbol;
                this.cardinal += 1;
            }else{
                insertarRecursivo(arbol.hijoDerecha, nuevo);
            }
        }else{//aca vamos por la izquierda
            if(arbol.hijoIzquierda == null){//append del nodo nuevo a la izquierda
                arbol.hijoIzquierda = nuevo;
                nuevo.padre = arbol;
                this.cardinal += 1;
            }else{
                insertarRecursivo(arbol.hijoIzquierda, nuevo);
            }
        }
    }

    //contamos hijos para ver como borramos
    private int cantidadHijos(Nodo nodo){
        int hijos = 0;
        if (nodo.hijoDerecha != null){
            hijos += 1;
        }
        if( nodo.hijoIzquierda != null){
            hijos += 1;
        }
        return hijos;
    } 

    // Encuentra un nodo y lo devuelve
    private Nodo encontrarNodo(Nodo arbol, T target) {
        // Verifica si el árbol es nulo
        if (arbol == null) {
        return null;  // Devuelve null si no se encuentra el nodo
        }else if (arbol.valor.equals(target)) {
            return arbol;  
        } else if (arbol.valor.compareTo(target) > 0) {  // arbol > target
            return encontrarNodo(arbol.hijoIzquierda, target);
        } else {
            return encontrarNodo(arbol.hijoDerecha, target);
        }
    }


    //encuentra el nodo menor del lado derecho del arbol
    private Nodo sucesorInmediato(Nodo arbol) {
        if (arbol == null) {
            return null;  // Manejar el caso donde el nodo es null
        }
    
        // Si no tiene hijo izquierdo, este es el sucesor
        if (arbol.hijoIzquierda == null) {
            Nodo padre = arbol.padre;
            
            // Verifica si el padre no es null antes de acceder a sus hijos
            if (padre != null && padre.hijoIzquierda == arbol) {
                padre.hijoIzquierda = null;  // Desconectar el sucesor de su padre
            }
            
            return arbol;  // Devuelve el sucesor
        } else {
            // Llamada recursiva para encontrar el sucesor más a la izquierda
            return sucesorInmediato(arbol.hijoIzquierda);
        }
    }
    //
    private Nodo hallarNodoSiguiente(Nodo arbol){
        if (arbol == null){ 
            return null;
        }
        if (arbol.hijoDerecha != null) {
            return hallarNodoConMinimo(arbol.hijoDerecha);
        }
        return hallarSiguienteNodoPadre(arbol);
    }

    private Nodo hallarSiguienteNodoPadre(Nodo arbol) {
        Nodo nodoPadre = arbol.padre;
        if (arbol == null || nodoPadre == null || arbol == nodoPadre.hijoIzquierda) return nodoPadre;
        return hallarSiguienteNodoPadre(nodoPadre);
    }

    public Nodo hallarNodoConMinimo(Nodo arbol){
        if(arbol.hijoIzquierda == null){ 
            return arbol; 
        }else{
            return hallarNodoConMinimo(arbol.hijoIzquierda);
        }
    } 
}