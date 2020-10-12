package com.desafio.logistiqal.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//paginacion, metodo devuelve el inicio y fin de cantidad de paginas a mostrar en el paginador.

public abstract class Util {
	public static List<Integer> getArregloPaginas(Integer paginaSolicitada, Integer totalPaginas) {
		Integer cantidadBotonesPaginador = 2;
		if(totalPaginas==0) totalPaginas=1;

		Integer inicioLista = (paginaSolicitada - cantidadBotonesPaginador) < 1 ? 1
				: paginaSolicitada - cantidadBotonesPaginador;

		System.out.println("----------------------------------------------------------------inside the Util, value of inicioLista: "+inicioLista);
		Integer finLista = (paginaSolicitada + cantidadBotonesPaginador) > totalPaginas ? totalPaginas
				: paginaSolicitada + cantidadBotonesPaginador;
		System.out.println("----------------------------------------------------------------inside the Util, value of finLista: "+finLista);
		
		
		System.out.println("----------------------------------------------------------------inside the Util, value of totalPaginas: "+totalPaginas);
		
		List<Integer> paginas = IntStream.rangeClosed(inicioLista, finLista).boxed().collect(Collectors.toList());
		return paginas;
	}
}
