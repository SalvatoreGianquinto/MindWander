package it.epicode.MindWander.exception;


// non lanciamo una Exception, ma una RunTimeException!!
//essendo una Exception un'eccezione CHECKED dovremmo metterla per forza nella firma del metodo "doFilterInternal", dato che useremo lì l'UnAuthrizedException. Questo perchè le checked devono obbligatoriamente mettere l'eccezione nel throws. Ma dato che questo metodo è un metodo Ovverride che esiste già e che deve avere la stessa firma della superClasse, se noi la andiamo a cambiare non è più una superclasse. Perciò se l'eccezione è Unchecked e quindi RunTimeException, non c'è bisogno di metterla nella firma del metodo.
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
