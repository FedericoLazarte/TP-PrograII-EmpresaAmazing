package models.pedido;

import java.util.ArrayList;
import java.util.List;

import models.cliente.Cliente;
import models.paquete.Paquete;

public class Pedido {
	private static int proxCod = 1;
	private int codPedido;
	private Cliente cliente;
	private List<Paquete> carrito;
	private boolean estaCerrado;
	private int total;
	
	public Pedido(Cliente cliente) {
		validarCliente(cliente);
		this.codPedido = proxCod++;
		this.cliente = cliente;
		this.carrito = new ArrayList<Paquete>();
		this.estaCerrado = false;
		this.total = 0;
	}
	
	public void agregarPaquete(Paquete paq) {
		if (this.estaCerrado) throw new RuntimeException("No se pueden agregar paquetes a un pedido cerrado");
		this.total += paq.totalPrecio();
		this.carrito.add(paq);
	}
	
	public boolean quitarPaquete(int idPaq) {
		Paquete paq = buscarPaquete(idPaq);
		if (paq == null) throw new RuntimeException("El paquete no se encuentra en el carrito.");
		this.total -= paq.totalPrecio();
		return this.carrito.remove(paq);
	}
	
	public void cerrarPedido() {
		if (!this.estaCerrado) this.estaCerrado = true;
		else throw new RuntimeException("El pedido ya está cerrado.");
	}
	
	public int totalAPagar() {
		return this.total;
	}
	
	public int getCodPedido() {
		return this.codPedido;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public boolean estaCerrado() {
		return this.estaCerrado;
	}
	
	public List<Paquete> getCarrito() {
		return new ArrayList<>(this.carrito);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Pedido [Código de Pedido: ")
		.append(this.codPedido)
		.append(", Cliente: ")
		.append(this.cliente)
		.append(", Carrito de Compras: ")
		.append(this.carrito)
		.append(", El pedido se encuentra cerrado?: ")
		.append(this.estaCerrado);
		return sb.toString();
	}
	
	private void validarCliente(Cliente cli) {
		if (cli == null) throw new IllegalArgumentException("El cliente no puede ser null.");
	}
	
	private Paquete buscarPaquete(int id) {
		for (Paquete paq : this.carrito)
			if (paq.getIdPaquete() == id)
				return paq;
		return null;
	}
}
