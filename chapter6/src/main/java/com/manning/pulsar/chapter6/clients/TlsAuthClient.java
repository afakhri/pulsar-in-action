package com.manning.pulsar.chapter6.clients;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class TlsAuthClient {
	public static void main(String[] args) throws PulsarClientException {
		final String AUTH = "org.apache.pulsar.client.impl.auth.AuthenticationTls";
		final String HOME = "/Users/david/exchange";
		final String TOPIC = "persistent://public/default/test-topic";
		
		PulsarClient client = PulsarClient.builder()
			    .serviceUrl("pulsar+ssl://localhost:6651/")
			    .tlsTrustCertsFilePath(HOME + "/ca.cert.pem")
			    .authentication(AUTH,
	               "tlsCertFile:" + HOME + "/admin.cert.pem," + 
			       "tlsKeyFile:" + HOME + "/admin-pk8.pem")
			    .build();

		Producer<String> producer = 
				client.newProducer(Schema.STRING).topic(TOPIC).create();
		
		for (int idx = 0; idx < 100; idx++) {
			producer.send("Hello TLS Auth");
		}	
		System.exit(0);
	}
}
