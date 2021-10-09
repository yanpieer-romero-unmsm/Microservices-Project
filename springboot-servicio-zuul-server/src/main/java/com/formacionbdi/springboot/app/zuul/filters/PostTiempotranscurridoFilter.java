package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempotranscurridoFilter extends ZuulFilter{
	
	private static final Logger Log = LoggerFactory.getLogger(PostTiempotranscurridoFilter.class);

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
		
		Log.info("Entrando a post filter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		Log.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTranscurrido.doubleValue()/1000.00));
		Log.info(String.format("Tiempo transcurrido en mileseg %s ms.", tiempoTranscurrido));
		
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
