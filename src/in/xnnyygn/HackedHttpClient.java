package in.xnnyygn;

import java.io.IOException;

import org.apache.commons.httpclient.HackedHttpMethodDirector;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HackedHttpClient extends HttpClient {

  private static Log logger = LogFactory.getLog(HackedHttpClient.class);

  public HackedHttpClient(HttpConnectionManager httpConnectionManager) {
    super(httpConnectionManager);
  }

  @Override
  public int executeMethod(HostConfiguration hostconfig, HttpMethod method,
      HttpState state) throws IOException, HttpException {
    logger
        .trace("enter HttpClient.executeMethod(HostConfiguration,HttpMethod,HttpState)");

    if (method == null) {
      throw new IllegalArgumentException("HttpMethod parameter may not be null");
    }
    HostConfiguration defaulthostconfig = getHostConfiguration();
    if (hostconfig == null) {
      hostconfig = defaulthostconfig;
    }
    URI uri = method.getURI();
    if (hostconfig == defaulthostconfig || uri.isAbsoluteURI()) {
      // make a deep copy of the host defaults
      hostconfig = new HostConfiguration(hostconfig);
      if (uri.isAbsoluteURI()) {
        hostconfig.setHost(uri);
      }
    }

    HackedHttpMethodDirector methodDirector =
        new HackedHttpMethodDirector(getHttpConnectionManager(), hostconfig,
            getParams(), (state == null ? getState() : state));
    methodDirector.executeMethod(method);
    return method.getStatusCode();
  }

}
