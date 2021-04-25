package xueqiao.trade.hosting.push.server.tasks;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import io.netty.handler.codec.http.HttpResponseStatus;
import xueqiao.trade.hosting.push.server.core.URLConstants;

public class ResourceLoaderTask extends HttpTask {
	private String mResource;
	
	public ResourceLoaderTask() {
	}

	public String getContentType() {
		if (mResource.endsWith(".js")) {
			return "application/x-javascript;charset=utf-8";
		} 
		if (mResource.endsWith(".css")) {
			return "text/css;charset=utf-8";
		}
		if (mResource.endsWith(".html")) {
			return "text/html;charset=utf-8";
		}
		return "application/octet-stream";
	}
	
	public byte[] getContent() throws IOException {
		URL url = ResourceLoaderTask.class.getResource(mResource);
		if (url == null) {
			return null;
		}
		return IOUtils.toByteArray(url);
	}

	@Override
	protected void onHttpExecute(HttpTaskResponse response) throws IOException  {
		mResource = httpReq().uri().substring(URLConstants.RESOURCE_START.length());
		URL url = ResourceLoaderTask.class.getResource(mResource);
		if (url == null) {
			response.status = HttpResponseStatus.NOT_FOUND;
			return ;
		}
		response.contentType = getContentType();
		response.body = IOUtils.toByteArray(url);
		response.status = HttpResponseStatus.OK;
	}
	
}
