package pe.bonifacio.redriwebservices.models;

import java.util.ArrayList;
import java.util.List;

public class Proyecto{

    private Long id;
    private String nombre;
    private String imagen;
    private List<MaquinaIm> maquinaim = new ArrayList<>();
    private List<MaquinaSup> maquinasup = new ArrayList<>();

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

    public List<MaquinaIm> getMaquinaim() {
        return maquinaim;
    }

    public void setMaquinaim(List<MaquinaIm> maquinaim) {
        this.maquinaim = maquinaim;
    }

    public List<MaquinaSup> getMaquinasup() {
        return maquinasup;
    }

    public void setMaquinasup(List<MaquinaSup> maquinasup) {
        this.maquinasup = maquinasup;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", maquinaim=" + maquinaim +
                ", maquinasup=" + maquinasup +
                '}';
    }
}
