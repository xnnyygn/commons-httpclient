package in.xnnyygn;

import static org.apache.commons.httpclient.params.HttpConnectionParams.CONNECTION_TIMEOUT;
import static org.apache.commons.httpclient.params.HttpConnectionParams.SO_TIMEOUT;

import java.io.IOException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUsage {

  public static void main(String[] args) throws NullPointerException,
      HttpException, IOException {
    HackedHttpClient client =
        new HackedHttpClient(createHttpConnectionManager());
    GetMethod get = new GetMethod();
    int statusCode =
        client.executeMethod(createHostConfig("http://xnnyygn.in", 2002, 1502),
            get, new HttpState());
    System.out.println(statusCode);
    System.out.println(get.getResponseBodyAsString());
  }

  private static HttpConnectionManager createHttpConnectionManager()
      throws URIException, NullPointerException {
    MultiThreadedHttpConnectionManager manager =
        new MultiThreadedHttpConnectionManager();
    // default max connections per host
    manager.getParams().setDefaultMaxConnectionsPerHost(10);
    // total connections
    manager.getParams().setMaxTotalConnections(1000);
    // default connect timeout
    manager.getParams().setConnectionTimeout(3000);
    // default read timeout
    manager.getParams().setSoTimeout(2000);
    manager.getParams().setMaxConnectionsPerHost(
        createHostConfig("http://xnnyygn.in"), 17);
    manager.getParams().setMaxConnectionsPerHost(
        createHostConfig("http://www.google.com/search"), 20);
    manager.getParams().setMaxConnectionsPerHost(
        createHostConfig("http://www.yahoo.com"), 15);
    return manager;
  }

  private static HostConfiguration createHostConfig(String uri)
      throws URIException, NullPointerException {
    HostConfiguration config = new HostConfiguration();
    config.setHost(new URI(uri, true));
    return config;
  }

  private static HostConfiguration createHostConfig(String uri,
      int connectionTimeout, int readTimeout) throws URIException,
      NullPointerException {
    HostConfiguration config = new HostConfiguration();
    config.setHost(new URI(uri, true));
    config.getParams().setIntParameter(CONNECTION_TIMEOUT, connectionTimeout);
    config.getParams().setIntParameter(SO_TIMEOUT, readTimeout);
    return config;
  }

}
