/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rxweb.engine.server.netty;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import rx.Observable;
import rxweb.http.Method;
import rxweb.http.Protocol;
import rxweb.server.ServerRequest;
import rxweb.server.ServerRequestHeaders;

import java.nio.ByteBuffer;

;

/**
 * @author Sebastien Deleuze
 */
public class NettyServerRequestAdapter implements ServerRequest {

	private final HttpRequest nettyRequest;
	private final ServerRequestHeaders headers;
	private UnicastContentSubject<ByteBuffer> content;

	public NettyServerRequestAdapter(HttpRequest request, UnicastContentSubject<ByteBuffer> content) {
		this.nettyRequest = request;
		this.headers = new NettyRequestHeadersAdapter(request);
		this.content = content;
	}

	@Override
	public Observable<ByteBuffer> getContent() {
		return this.content;
	}

	@Override
	public Protocol getProtocol() {
		HttpVersion version = this.nettyRequest.getProtocolVersion();
		if (version.equals(HttpVersion.HTTP_1_0)) {
			return Protocol.HTTP_1_0;
		} else if (version.equals(HttpVersion.HTTP_1_1)) {
			return Protocol.HTTP_2_0;
		}
		throw new IllegalStateException(version.protocolName() + " not supported");
	}

	@Override
	public String getUri() {
		return this.nettyRequest.getUri();
	}

	@Override
	public Method getMethod() {
		return new Method(this.nettyRequest.getMethod().name());
	}

	@Override
	public ServerRequestHeaders getHeaders() {
		return this.headers;
	}

}
