package com.fernandopaniagua.ejemplo;

import java.util.List;

public class RenderizadorSecuencial {
	private static boolean renderizando = true;
	
	public void renderList(List<Poligono> lista) {
		if (lista.size() > 2) {
			int medio = lista.size() / 2;
			renderList(lista.subList(0, medio));
			renderList(lista.subList(medio, lista.size()));
		} else {
			for (Poligono poligono : lista) {
				poligono.render();
			}
		}
	}
	
	public static void main(String[] args) {
		long start = new java.util.Date().getTime();//Número de ms
		List<Poligono> poligonos = Poligono.getPoligonos();
		
		mostrarAvance(poligonos);
		
		new RenderizadorSecuencial().renderList(poligonos);
		int npr = 0;//Número de polígonos renderizados
		for (Poligono poligono : poligonos) {
			if(poligono.renderizado) npr++;
		}
		long end = new java.util.Date().getTime();
		System.out.println("Número de polígonos renderizados:" + npr);
		System.out.println("Tiempo empleado (ms):" + (end-start));
		
	}
	
	private static void mostrarAvance(List<Poligono> poligonos) {
		new Thread(()->{
			try {
				while(renderizando) {
					Thread.sleep(100);
					for (Poligono poligono : poligonos) {
						System.out.print(poligono.renderizado ? '0' : '.');
					}
					System.out.println();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
