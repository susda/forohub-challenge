package ManejoAnomalias;

public class ManejoIntegridad extends RuntimeException {
    public ManejoIntegridad(String s){
        super(s);
    }
}
