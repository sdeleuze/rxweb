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

package rxweb.rx.rxjava;

import java.nio.ByteBuffer;

import org.reactivestreams.Subscriber;
import rx.Observable;
import rx.RxReactiveStreams;
import rxweb.converter.ConverterResolver;
import rxweb.http.Protocol;
import rxweb.server.ServerRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sebastien Deleuze
 */
public class RxJavaServerRequestAdapter implements RxJavaServerRequest {

	private ServerRequest serverRequest;

	public RxJavaServerRequestAdapter(ServerRequest serverRequest) {
		this.serverRequest = serverRequest;
	}

	@Override
	public HttpHeaders getHeaders() {
		return this.serverRequest.getHeaders();
	}

	@Override
	public Observable<ByteBuffer> getContent() {
		return RxReactiveStreams.toObservable(this.serverRequest.getContent());
	}

	@Override
	public <T> Observable<T> getContent(Class<T> clazz) {
		return RxReactiveStreams.toObservable(this.serverRequest.getContent(clazz));
	}

	@Override
	public Observable<ByteBuffer> getContentStream() {
		return RxReactiveStreams.toObservable(this.serverRequest.getContentStream());
	}

	@Override
	public <T> Observable<T> getContentStream(Class<T> clazz) {
		return RxReactiveStreams.toObservable(this.serverRequest.getContentStream(clazz));
	}

	@Override
	public void setConverterResolver(ConverterResolver converterResolver) {
		this.serverRequest.setConverterResolver(converterResolver);
	}

	@Override
	public void subscribe(Subscriber<? super ByteBuffer> s) {
		this.serverRequest.subscribe(s);
	}

	@Override
	public Protocol getProtocol() {
		return this.serverRequest.getProtocol();
	}

	@Override
	public String getUri() {
		return this.serverRequest.getUri();
	}

	@Override
	public RequestMethod getMethod() {
		return this.serverRequest.getMethod();
	}
}