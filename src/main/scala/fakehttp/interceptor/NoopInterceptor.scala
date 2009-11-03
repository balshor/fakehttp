package fakehttp.interceptor

import org.jboss.netty.handler.codec.http._

class NoopInterceptor extends Interceptor {
  def intercept(req: HttpRequest): InterceptResult = {
    // Watch for foo.com:123
    val parts = req.getHeader(HttpHeaders.Names.HOST).split(":")
    val host = parts(0)
    val port = if (parts.size > 1) {
      parts(1).toInt
    } else if (req.getMethod == HttpMethod.CONNECT) {
      req.getUri.split(":").last.toInt
    } else {
      80
    }
    return ProxyResult(host, port)
  }
}