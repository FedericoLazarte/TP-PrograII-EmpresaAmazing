package controller.empresa;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.cliente.Cliente;
import models.pedido.Pedido;
import models.transporte.*;
import models.paquete.*;

public class EmpresaAmazing implements IEmpresa {
	private String cuit;
	private Map<String, Transporte> transportes;
	private Map<Integer, Pedido> pedidos;
	private double facturacionTotalPedidosCerrados;
	
	public EmpresaAmazing(String cuit) {
		validarCuit(cuit);
		this.cuit = cuit;
		this.transportes = new HashMap<>();
		this.pedidos = new HashMap<>();
		this.facturacionTotalPedidosCerrados = 0;
	}

	@Override
	public void registrarAutomovil(String patente, int volMax, int valorViaje, int maxPaq) {
		if (estaSinRegistrarTransporte(patente)) {
			Automovil aut = new Automovil(patente, volMax, valorViaje, maxPaq);
			this.transportes.put(patente, aut);
		}
		
	}

	@Override
	public void registrarUtilitario(String patente, int volMax, int valorViaje, int valorExtra) {
		if (estaSinRegistrarTransporte(patente)) {
			Utilitario ut = new Utilitario(patente, volMax, valorViaje, valorExtra);
			this.transportes.put(patente, ut);
		}
	}

	@Override
	public void registrarCamion(String patente, int volMax, int valorViaje, int adicXPaq) {
		if (estaSinRegistrarTransporte(patente)) {
			Camion cam = new Camion(patente, volMax, valorViaje, adicXPaq);
			this.transportes.put(patente, cam);
		}
	}

	@Override
	public int registrarPedido(String cliente, String direccion, int dni) {
		Cliente cli = new Cliente(cliente, dni, direccion);
		Pedido ped = new Pedido(cli);
		this.pedidos.put(ped.getCodPedido(), ped);
		return ped.getCodPedido();
	}

	@Override
	public int agregarPaquete(int codPedido, int volumen, int precio, int costoEnvio) {
		Paquete paqOr = new PaqueteOrdinario(volumen, precio, costoEnvio);
		return this.agregarPaquete(codPedido, paqOr);
	}

	@Override
	public int agregarPaquete(int codPedido, int volumen, int precio, int porcentaje, int adicional) {
		Paquete paqEs = new PaqueteEspecial(volumen, precio, porcentaje, adicional);
		return this.agregarPaquete(codPedido, paqEs);
	}

	@Override
	public boolean quitarPaquete(int codPaquete) {
		for (Integer numPed : this.pedidos.keySet()) {
			Pedido ped = this.pedidos.get(numPed);
			if (ped.estaCerrado()) {
				throw new RuntimeException("El pedido ya se encuentra cerrado.");
			} else {
				return ped.quitarPaquete(codPaquete);
			}
		}
		return false;
	}

	@Override
	public double cerrarPedido(int codPedido) {
		Pedido ped = this.pedidos.get(codPedido);
		if (ped == null) throw new RuntimeException("El pedido no se encuentra registrado");
		if (ped.estaCerrado()) throw new RuntimeException("El pedido ya está cerrado.");
		double totalAPagar = ped.totalAPagar();
		ped.cerrarPedido();
		this.facturacionTotalPedidosCerrados += totalAPagar;
		return totalAPagar;
	}

	@Override
	public String cargarTransporte(String patente) {
		Transporte t = buscarTransporte(patente);
		if (t == null) throw new RuntimeException("La patente del transporte no está registrada.");
		StringBuilder sb = new StringBuilder();
		for (Integer codPed : this.pedidos.keySet()) {
			Pedido ped = this.pedidos.get(codPed);
			if (ped.estaCerrado()) {
				for (Paquete paq : ped.getCarrito()) {
					if (t.cargarPaquete(paq)) {
						sb.append(" + [ ").append(ped.getCodPedido()).append(" - ")
						.append(ped.getCodPedido()).append(" ] ")
						.append(ped.getCliente().getDireccion()).append("\n");
					}
				}
			}
		}
		return sb.toString();
	}

	@Override
	public double costoEntrega(String patente) {
		Transporte t = buscarTransporte(patente);
		if (t == null) throw new RuntimeException("La patente del transporte no está registrada.");
		if (!t.estaCargado()) throw new RuntimeException("El transporte no está cargado.");
		return t.costoTotalXViaje();
	}

	@Override
	public Map<Integer, String> pedidosNoEntregados() {
		Map<Integer, String> pedidosSinEntregar = new HashMap<>();
		for (Integer cod : this.pedidos.keySet()) {
			Pedido pedido = this.pedidos.get(cod);
			for (Paquete paq : pedido.getCarrito()) {
				if (!paq.estaEntregado() && pedido.estaCerrado()) {
					pedidosSinEntregar.put(pedido.getCodPedido(), pedido.getCliente().getNombre());
				}
			} 
		}
		return pedidosSinEntregar;
	}

	@Override
	public double facturacionTotalPedidosCerrados() {
		return this.facturacionTotalPedidosCerrados;
	}

	@Override
	public boolean hayTransportesIdenticos() {
		return hayTransportesIdenticos(this.transportes);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmpresaAmazing [\n");
		builder.append("\tCUIT: ").append(cuit).append(",\n");
		builder.append("\tTransportes Registrados: [\n");
		for (String patente : transportes.keySet()) {
			Transporte transporte = this.transportes.get(patente);
			builder.append("\t\t").append(transporte.toString()).append(",\n");
		}
		builder.append("\t],\n");
		builder.append("\tPedidos Registrados: [\n");
		for (Integer codPedido :  this.pedidos.keySet()) {
			Pedido pedido = this.pedidos.get(codPedido);
			builder.append("\t\t").append(pedido.toString()).append(",\n");
		}
		builder.append("\t],\n");
		builder.append("\tFacturación Total de Pedidos Cerrados: ").append(facturacionTotalPedidosCerrados)
				.append("\n");
		builder.append("]");
		return builder.toString();
	}
	
	private void validarCuit(String cuit) {
		if (cuit == null || cuit.length() == 0) throw new RuntimeException("El cuit no uede ser vacío ni nulo");
	}
	
	private boolean estaSinRegistrarTransporte(String patente) {
		if (this.transportes.containsKey(patente)) throw new RuntimeException("El transporte ya se encuentra registrado.");
		return true;
	}
	
	private int agregarPaquete(int codPedido, Paquete paq) {
		if (estaRegistradoPedido(codPedido)) {
			Pedido p = this.pedidos.get(codPedido);
			if (!p.estaCerrado()) {
				p.agregarPaquete(paq);
				return p.getCodPedido();
			} else {
				throw new RuntimeException("El pedido ya se encuentra cerrado.");
			}
		} else {
			throw new RuntimeException("El pedido no se encuentra registrado.");
		}
	}
	
	private boolean estaRegistradoPedido(int codPed) {
		return this.pedidos.containsKey(codPed);
	}
	
	private Transporte buscarTransporte(String patente) {
		return this.transportes.get(patente);
	}
	
	private <K, V> boolean hayTransportesIdenticos(Map<K, V> hashMap) {
		Collection<V> valores = hashMap.values();
		Set<V> conjuntoValores = new HashSet<>(valores);
		return valores.size() != conjuntoValores.size();
	}
}
