package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTiempotranscurridoFilter extends ZuulFilter{
	
	private static final Logger Log = LoggerFactory.getLogger(PreTiempotranscurridoFilter.class);

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
		
		Log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
		
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
