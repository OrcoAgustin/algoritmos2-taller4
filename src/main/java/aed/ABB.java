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
    //auxiliar
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
        if(pertenece(elem)){
            Nodo nodoEliminar = conseguirNodo(elem);
            Nodo hijoBorrado = null;
            switch (tieneHijos(nodoEliminar)) {
                case 0:
                    {//caso sin hijos
                        Nodo padreBorrado = nodoEliminar.padre;
                        if (padreBorrado.valor.compareTo(nodoEliminar.valor)>0){
                            padreBorrado.hijoIzquierda = null;
                        }else{
                            padreBorrado.hijoDerecha = null;
                        }       break;
                    }
                case 1:
                    {//caso 1 hijo
                        if (nodoEliminar.hijoDerecha == null){ //determino el hijo
                            hijoBorrado = nodoEliminar.hijoIzquierda;
                        }else{
                            hijoBorrado = nodoEliminar.hijoDerecha;
                        }       Nodo padreBorrado = nodoEliminar.padre;
                        if (padreBorrado.valor.compareTo(nodoEliminar.valor)>0){ //determino de que lado venia el hijo
                            padreBorrado.hijoIzquierda = hijoBorrado;
                        }else{
                            padreBorrado.hijoDerecha = hijoBorrado;
                        }       hijoBorrado.padre = padreBorrado;
                        nodoEliminar.padre = null;                
                        nodoEliminar.hijoDerecha = null;
                        nodoEliminar.hijoIzquierda = null;
                        break;
                    }
                case 2:
                    {//redundante pero caso 2 hijos
                    }
            }
        }
    }

    //auxiliar
    private int tieneHijos(Nodo nodo){
        int hijos = 0;
        if(nodo.hijoDerecha != null){
            hijos += 1;
        }
        if(nodo.hijoIzquierda != null){
            hijos += 1;
        }
        return hijos;
    }

    //auxiliar
    //se supone que siempre va a pertenecer por lo cual nunca va a loopear perma
    private Nodo conseguirNodo (T elem){
        Nodo apuntado = this.raiz;
        while (true) { 
            if (apuntado.valor.equals(elem)) {
                return apuntado;
            }else if (apuntado.valor.compareTo(elem)>0){ //apuntado>elem
                apuntado = apuntado.hijoIzquierda;
            }else{ //caso derecha
                apuntado = apuntado.hijoDerecha;
            }
        }
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
