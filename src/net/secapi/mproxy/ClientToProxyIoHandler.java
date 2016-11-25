package net.secapi.mproxy;

import java.net.SocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

public class ClientToProxyIoHandler extends AbstractProxyIoHandler {
    private final ServerToProxyIoHandler connectorHandler = new ServerToProxyIoHandler();
 
    private final IoConnector connector;
 
    private final SocketAddress[] remoteAddress;
 
    public ClientToProxyIoHandler(IoConnector connector,
            SocketAddress[] remoteAddress) {
        this.connector = connector;
        this.remoteAddress = remoteAddress;
        connector.setHandler(connectorHandler);
    }
    
    int k=0;
    
    @Override
    public void sessionOpened(final IoSession session) throws Exception {
 
    	SocketAddress saddress = remoteAddress[(k++)%remoteAddress.length];
    	System.out.println("¥˙¿ÌIP£∫"+saddress);
    	
        connector.connect(saddress).addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                try {
                    future.getSession().setAttribute(OTHER_IO_SESSION, session);
                    session.setAttribute(OTHER_IO_SESSION, future.getSession());
                    IoSession session2 = future.getSession();
                    session2.resumeRead();
                    session2.resumeWrite();
                } catch (RuntimeIoException e) {
                    // Connect failed
                    session.close(true);
                } finally {
                    session.resumeRead();
                    session.resumeWrite();
                }
            }
        });
    }
}
