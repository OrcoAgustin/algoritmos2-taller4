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
        if(pertenece(elem)){}
        else{ 
            Nodo ultimoBuscado = perteneceUltimo(elem);
            Nodo nuevo = new Nodo (elem);
            nuevo.padre = ultimoBuscado; 
            if (ultimoBuscado.valor.compareTo(nuevo.valor)>0){
                ultimoBuscado.hijoIzquierda = nuevo;
            }else{
                ultimoBuscado.hijoDerecha = nuevo;
            } //no se que tan bien esta
        }
    }

    private Nodo perteneceUltimo(T elem){
        Nodo apuntado=this.raiz;
        while (true) {
            if(apuntado.valor.compareTo(elem)>0){ //apuntado>elem
                if(apuntado.hijoIzquierda == null){
                    return apuntado;
                }else{
                    apuntado = apuntado.hijoIzquierda;
                }
            }else if(elem.compareTo(apuntado.valor)>0){ //apuntado<elem
                if(apuntado.hijoDerecha == null){
                    return apuntado;
                }else{
                    apuntado = apuntado.hijoDerecha;
                }
            }
        } 
    }
    
    public boolean pertenece(T elem){
        Nodo apuntado=this.raiz;
        while (true) {
            if (apuntado.valor.equals(elem)){
                return true;
            }else if(apuntado.valor.compareTo(elem)>0){ //apuntado>elem
                if(apuntado.hijoIzquierda != null){
                    apuntado = apuntado.hijoIzquierda;
                }else{
                    return false;
                }
            }else if(elem.compareTo(apuntado.valor)>0){ //apuntado<elem
                if(apuntado.hijoDerecha != null){
                    apuntado = apuntado.hijoDerecha;
                }else{
                    return false;
                }
            }
        } 
    }

    public void eliminar(T elem){
        
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
