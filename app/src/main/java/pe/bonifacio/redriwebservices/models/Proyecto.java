package pe.bonifacio.redriwebservices.models;

import java.util.ArrayList;
import java.util.List;

public class Proyecto{

    private Long id;
    private String nombre;
    private String imagen;
    private List<MaquinaIm> maquinaims = new ArrayList<>();
    private List<MaquinaSup> maquinasups = new ArrayList<>();

    public Proyecto(Long id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<MaquinaIm> getMaquinaims() {
        return maquinaims;
    }

    public void setMaquinaims(List<MaquinaIm> maquinaims) {
        this.maquinaims = maquinaims;
    }

    public List<MaquinaSup> getMaquinasups() {
        return maquinasups;
    }

    public void setMaquinasups(List<MaquinaSup> maquinasups) {
        this.maquinasups = maquinasups;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", maquinaims=" + maquinaims +
                ", maquinasups=" + maquinasups +
                '}';
    }
}
