package models.cliente;

public class Cliente {
	private String nombre;
	private int dni;
	private String direccion;
	
	public Cliente(String nombre, int dni, String direccion) {
		validarNombre(nombre);
		validarDireccion(direccion);
		this.nombre = nombre;
		this.dni = dni;
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente: [nombre: ")
		.append(nombre)
		.append(", DNI")
		.append(dni)
		.append(", Dirección: ")
		.append(direccion)
		.append("]");
		return sb.toString();
	}
	
	private void validarNombre(String nombre) {
		if (nombre == null || nombre.length() < 4 || nombre.length() > 100) 
			throw new IllegalArgumentException("El nombre no puede ser vacío o tener menos de 4 caracteres, ni superar los 100 caracteres.");
	}
	
	private void validarDireccion(String dir) {
		if (dir == null || dir.length() < 4 || dir.length() > 200)
			throw new IllegalArgumentException("La dirección no puede ser vacía, ni tener menos de 4 caracteres o más de 200.");
	}
}
