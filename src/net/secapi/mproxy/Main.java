package net.secapi.mproxy;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class Main {

	public static void main(String [] args) throws Exception
	{
		
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		
		IoConnector connector = new NioSocketConnector();
		
		connector.setConnectTimeoutMillis(30*1000L);
		
		InetSocketAddress isas [] = new InetSocketAddress [] {
				new InetSocketAddress("172.28.11.24",Integer.parseInt("8888")),
				new InetSocketAddress("172.28.11.73",Integer.parseInt("8080")),
				new InetSocketAddress("172.28.11.99",Integer.parseInt("5000"))
		};
		
		ClientToProxyIoHandler handler = new ClientToProxyIoHandler(connector,isas);
		
		
		acceptor.setHandler(handler);
		acceptor.bind(new InetSocketAddress(Integer.parseInt("7789")));
		
		System.out.println("Listening on port"+Integer.parseInt("7789"));
		
	}
}
