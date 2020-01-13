package pe.bonifacio.redriwebservices.models;

public class MaquinaIm {
    private Long id_im;
    private String nombre_im;
    private Long proyecto_id;

    public MaquinaIm() {
    }

    public MaquinaIm(Long id_im, String nombre_im, Long proyecto_id) {
        this.id_im = id_im;
        this.nombre_im = nombre_im;
        this.proyecto_id = proyecto_id;
    }

    public Long getId_im() {
        return id_im;
    }

    public void setId_im(Long id_im) {
        this.id_im = id_im;
    }

    public String getNombre_im() {
        return nombre_im;
    }

    public void setNombre_im(String nombre_im) {
        this.nombre_im = nombre_im;
    }

    public Long getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(Long proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    @Override
    public String toString() {
        return "InteriorMina{" +
                "id_im=" + id_im +
                ", nombre_im='" + nombre_im + '\'' +
                ", proyecto_id=" + proyecto_id +
                '}';
    }
}
