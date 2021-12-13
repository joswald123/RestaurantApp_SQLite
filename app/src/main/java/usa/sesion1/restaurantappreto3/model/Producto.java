package usa.sesion1.restaurantappreto3.model;

public class Producto {
    private int id;
    private String nombre;
    private String precio;
    private int imagen;
    private boolean favorito;

    public Producto(int id, String nombre, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.favorito = false;
    }

    public Producto(int id, String nombre, String precio, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = false;
    }

    public Producto(int id, String nombre, String precio, int imagen, boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = favorito;
    }

    public Producto(String nombre, String precio, int imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = false;
    }

    public Producto(String nombre, String precio, int imagen, boolean favorito) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.favorito = favorito;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}


