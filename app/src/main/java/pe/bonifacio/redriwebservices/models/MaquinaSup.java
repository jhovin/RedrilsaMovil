package pe.bonifacio.redriwebservices.models;

public class MaquinaSup {
    private Long id_sup;
    private String nombre_sup;
    private Long proyecto_id;

    public MaquinaSup() {
    }

    public MaquinaSup(Long id_sup, String nombre_sup, Long proyecto_id) {
        this.id_sup = id_sup;
        this.nombre_sup = nombre_sup;
        this.proyecto_id = proyecto_id;
    }

    public Long getId_sup() {
        return id_sup;
    }

    public void setId_sup(Long id_sup) {
        this.id_sup = id_sup;
    }

    public String getNombre_sup() {
        return nombre_sup;
    }

    public void setNombre_sup(String nombre_sup) {
        this.nombre_sup = nombre_sup;
    }

    public Long getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(Long proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    @Override
    public String toString() {
        return "SuperiorMina{" +
                "id_sup=" + id_sup +
                ", nombre_sup='" + nombre_sup + '\'' +
                ", proyecto_id=" + proyecto_id +
                '}';
    }
}
