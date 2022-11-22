/*
 * 
 * Más info es:
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html
 * https://www.baeldung.com/java-fork-join
 * https://www.adictosaltrabajo.com/2015/08/21/el-paralelismo-en-java-y-el-framework-forkjoin/
 * https://www.geeksforgeeks.org/forkjoinpool-class-in-java-with-examples/?ref=gcse
 * 
 */

package com.fernandopaniagua.ejemplo;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RenderizadorParalelo extends RecursiveAction {

	private static boolean renderizando = true;
	
	private List<Poligono> lista;
	public RenderizadorParalelo(List<Poligono> lista) {
		this.lista=lista;
	}
	
	@Override
	protected void compute() {
		if (lista.size()>2) {
			int medio = lista.size() / 2;
			RenderizadorParalelo rp1 = new RenderizadorParalelo(lista.subList(0, medio));
			RenderizadorParalelo rp2 = new RenderizadorParalelo(lista.subList(medio, lista.size()));
			invokeAll(rp1, rp2);
		} else {
			for (Poligono poligono : lista) {
				poligono.render();
			}
		}
	}

	public static void main(String[] args) {
		long start = new java.util.Date().getTime();
		List<Poligono> poligonos = Poligono.getPoligonos();
		
		mostrarAvance(poligonos);
		
		int npr = 0;//Número de polígonos renderizados
		
		for (Poligono poligono : poligonos) {
			if(poligono.renderizado) npr++;
		}
		System.out.println("Número de polígonos renderizados:" + npr);
		ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new RenderizadorParalelo (poligonos));
        for (Poligono poligono : poligonos) {
			if(poligono.renderizado) npr++;
		}
        renderizando=false;
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
