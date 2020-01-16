package pe.bonifacio.redriwebservices.models;

public class MaquinaIm {
    private Long id_im;
    private String nombre_im;
    private String placa;
    private Integer serie_motor;
    private Integer lectura_horometro;
    private String observacion;

    private Long proyecto_id;

    public MaquinaIm() {
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getSerie_motor() {
        return serie_motor;
    }

    public void setSerie_motor(Integer serie_motor) {
        this.serie_motor = serie_motor;
    }

    public Integer getLectura_horometro() {
        return lectura_horometro;
    }

    public void setLectura_horometro(Integer lectura_horometro) {
        this.lectura_horometro = lectura_horometro;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(Long proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    @Override
    public String toString() {
        return "MaquinaIm{" +
                "id_im=" + id_im +
                ", nombre_im='" + nombre_im + '\'' +
                ", placa='" + placa + '\'' +
                ", serie_motor=" + serie_motor +
                ", lectura_horometro=" + lectura_horometro +
                ", observacion='" + observacion + '\'' +
                ", proyecto_id=" + proyecto_id +
                '}';
    }
}
