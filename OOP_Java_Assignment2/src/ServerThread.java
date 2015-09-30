import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ServerThread extends Thread {
private Socket socket = null;
    
    public ServerThread(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run(){
        try{
            InputStream s1ls = socket.getInputStream();
            OutputStream s1os = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(s1ls);
            DataOutputStream dos = new DataOutputStream(s1os);
            
            dos.writeUTF("Echo Message: " + dis.readUTF());
            
            dis.close();dos.close();
            s1ls.close();s1os.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
