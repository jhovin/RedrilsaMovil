package pe.bonifacio.redriwebservices.models;

import java.util.ArrayList;
import java.util.List;

public class Proyecto{

    private Long id,usuario_id;
    private String nombre;
    private String cliente,distrito,provincia,departamento,gerente,telefono,imagen;

    private List<MaquinaIm> maquinaim = new ArrayList<>();
    private List<MaquinaSup> maquinasup = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(Long id, Long usuario_id, String nombre, String cliente, String distrito, String provincia, String departamento, String gerente, String telefono, String imagen) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.cliente = cliente;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
        this.gerente = gerente;
        this.telefono = telefono;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
                ", usuario_id=" + usuario_id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", cliente='" + cliente + '\'' +
                ", distrito='" + distrito + '\'' +
                ", provincia='" + provincia + '\'' +
                ", departamento='" + departamento + '\'' +
                ", gerente='" + gerente + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
