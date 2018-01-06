package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class LoggingInterface extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	private class LogContent {
		
		
		public LogContent(ContentCachingRequestWrapper requestToCache, HttpServletResponse responseToCache) {

			
		}
		
	}
	
	private static final Logger log = LoggerFactory.getLogger(LoggingInterface.class);
	
	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.doService(request, response);
	}
	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}
		if (!(response instanceof ContentCachingResponseWrapper)) {
			response = new ContentCachingResponseWrapper(response);
		}
		HandlerExecutionChain handler = getHandler(request);

		ContentCachingRequestWrapper requestWrapper = wrapRequest(request);
		
		try {
			super.doDispatch(request, response);
		} finally {
			log(requestWrapper , response, handler);
			updateResponse(response);
		}
	}
	
	private void log(ContentCachingRequestWrapper requestToCache, HttpServletResponse responseToCache,
			HandlerExecutionChain handler) {
		LogContent log = new LogContent(requestToCache, responseToCache) ; 
		logger.info(log.toString());	
		
	}

	private String getResponsePayload(HttpServletResponse response) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		if (wrapper != null) {

			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, 5120);
				try {
					return new String(buf, 0, length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException ex) {
					// NOOP
				}
			}
		}
		return "[unknown]";
	}
	
	private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) throws IOException {
		ContentCachingRequestWrapper requestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class); 
		return requestWrapper; 
	}


	private void updateResponse(HttpServletResponse response) throws IOException {
		ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		responseWrapper.copyBodyToResponse();
	}

}
