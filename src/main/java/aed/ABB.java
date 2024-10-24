package aed;
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2

public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz;
    private int cardinal;
    private int altura; //sirve?

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
        return pertenceRecursivo(elem, apuntado);
    }

    public void eliminar(T elem) {
        // Caso A: Si no pertenece, no hacemos nada
        if (this.pertenece(elem) == false) {  
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
    
        // Caso C.0: Nodo sin hijos
        if (cantidadHijos(nodo) == 0) {
            if (padre != null) {  
                if (padre.hijoIzquierda == nodo) {
                    padre.hijoIzquierda = null;
                } else {
                    padre.hijoDerecha = null;
                }
            } else {// Si el nodo fuese la raíz
                this.raiz = null;
            }
            nodo.padre = null; 
            this.cardinal -= 1;
            return;
        }
    
        // Caso C.1: Nodo con un hijo
        if (cantidadHijos(nodo) == 1) {
            Nodo huerfano = null;
            if(nodo.hijoDerecha != null){
                huerfano=nodo.hijoDerecha; 
            }else{
                huerfano=nodo.hijoIzquierda;
            }
            if (padre != null) {  
                if (padre.hijoIzquierda == nodo) {
                    padre.hijoIzquierda = huerfano;
                } else {
                    padre.hijoDerecha = huerfano;
                }
            } else {// Si el nodo es la raíz
                this.raiz = huerfano;
            }
            if (huerfano != null) {
                huerfano.padre = padre;
            }
            nodo.padre = null; 
            this.cardinal -= 1;
            return;
        }
    
        // Caso C.2:Nodo con dos hijos
        if (cantidadHijos(nodo) == 2) {
            Nodo sucesor = hallarMinimo(nodo.hijoDerecha);
            if (sucesor != nodo.hijoDerecha) {//si no es el hijo directo del nodo a borrar
                Nodo padreSucesor = sucesor.padre;
                if (sucesor.hijoDerecha != null) {
                    padreSucesor.hijoIzquierda = sucesor.hijoDerecha;
                    sucesor.hijoDerecha.padre = padreSucesor;
                } else {
                    padreSucesor.hijoIzquierda = null;
                }
                sucesor.hijoDerecha = nodo.hijoDerecha;
                if (nodo.hijoDerecha != null) {
                    nodo.hijoDerecha.padre = sucesor;
                }
            }
            sucesor.hijoIzquierda = nodo.hijoIzquierda;
            if (nodo.hijoIzquierda != null) {
                nodo.hijoIzquierda.padre = sucesor;
            }
            sucesor.padre = nodo.padre;
            if (nodo.padre == null) {// Si el nodo es la raíz
                this.raiz = sucesor;
            } else if (nodo.padre.hijoIzquierda == nodo) {
                nodo.padre.hijoIzquierda = sucesor;
            } else {
                nodo.padre.hijoDerecha = sucesor;
            }
            nodo.padre = null;
            this.cardinal -= 1;
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
        private Nodo _actual = hallarMinimo(raiz);
        
        public boolean haySiguiente() {            
            return this._actual != null; 
        }
    
        public T siguiente() {
            if (this._actual == null) {
                return  null;
            }
            T valorActual = this._actual.valor; 
            _actual = hallarNodoSiguiente(_actual); 
            return valorActual;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }



    //auxiliares
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

    // encuentra un nodo y lo devuelve
    private Nodo encontrarNodo(Nodo arbol, T target) {
        if (arbol == null) {
            return null;// devuelve null si no se encontrase el nodo
        }else if (arbol.valor.equals(target)) {
            return arbol;  
        } else if (arbol.valor.compareTo(target) > 0) {// arbol>target => voy x la izquierda
            return encontrarNodo(arbol.hijoIzquierda, target);
        } else {
            return encontrarNodo(arbol.hijoDerecha, target);
        }
    }

    //busca nodo siguiente
    private Nodo hallarNodoSiguiente(Nodo arbol){
        if (arbol == null){ 
            return null;
        }
        if (arbol.hijoDerecha != null) {
            return hallarMinimo(arbol.hijoDerecha);
        }
        return hallarSiguienteNodoPadre(arbol);
    }
    
    //se mueve para arriba para encontrar el siguiente
    private Nodo hallarSiguienteNodoPadre(Nodo arbol) {
        if (arbol == null) {
            return null; 
        }
        Nodo nodoPadre = arbol.padre; 
        if (nodoPadre == null) {
            return null; 
        }
        if (arbol == nodoPadre.hijoIzquierda) {
            return nodoPadre;
        }
        return hallarSiguienteNodoPadre(nodoPadre);
    }
    
    private Nodo hallarMinimo(Nodo arbol) {
        if (arbol == null) { 
            return null; 
        }
        if (arbol.hijoIzquierda == null) { 
            return arbol; 
        }
        return hallarMinimo(arbol.hijoIzquierda);
    }
}