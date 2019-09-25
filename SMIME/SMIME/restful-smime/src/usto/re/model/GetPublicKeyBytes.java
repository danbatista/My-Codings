package usto.re.model;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import javax.net.ssl.*;
import sun.applet.Main;

// by daniel
public class GetPublicKeyBytes {

	public static PublicKey getBytes(String url, String email) throws IOException, GeneralSecurityException{
		System.out.println("Buscando: " + email + "URL:" + url);
		 trustServerGoHorse(url);
		PublicKey pub = null;
		String uri =url+"/SMIME_CHAVE_PUBLICA/rest_pub/service/buscarPorEmail";
         Client client = Client.create();
         WebResource wr = client.resource(uri);
	    //wr.type("text/plain").accept("text/plain").post(String.class, email);
       
	   byte[] bytes = wr.type("text/plain").accept("application/octet-stream").post(byte[].class, email);
	  
			pub = loadPublicKey(bytes);
			System.out.println(pub);

			return pub;
	}
	public static void trustServerGoHorse(String urlServer) throws IOException{


        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
  // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
  // Now you can access an https URL without having the certificate in the truststore
        try {


            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                            + session.getPeerHost());
                    return true;
                }
            };

            String datam = "param=myparam";
            URL url = new URL(urlServer+"/SMIME_CHAVE_PUBLICA");
            URLConnection conn = url.openConnection();
            HttpsURLConnection urlConn = (HttpsURLConnection) conn;
            urlConn.setHostnameVerifier(hv);
            //conn.setDoOutput(true);
            //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            //wr.write(datam);
            //wr.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            String res = sb.toString();
            System.out.println(res);
            urlConn.setHostnameVerifier(hv); 
        } catch (MalformedURLException e) {
            System.out.println("Error in SLL Connetion" + e);
        }

}
  private static PublicKey loadPublicKey(byte[] c) throws GeneralSecurityException {
	    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(c);
	   PublicKey pub = null;
	   KeyFactory key = KeyFactory.getInstance("RSA");
	    pub = key.generatePublic(x509KeySpec);
	    return pub;
} 
  /*
  public static void main(String[] args) throws IOException {
	getBytes("https://10.67.0.224:7777", "daniel@usto.re");
}*/
}
