package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Slf4j
@Component
public class PreTiempotranscurridoFilter extends ZuulFilter{
	@Override
	public boolean shouldFilter() {
		// Para validar si vamos a ejecutar o no el filtro
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// Acá se resuelve la lógica de nuestro filtro
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
		
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	@Override
	public String filterType() {
		// Antes de que se resuelva la ruta y la comunicación con el microservicio
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
