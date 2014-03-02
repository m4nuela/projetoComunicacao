package protocolo.UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializador {

	public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        Object obj = o.readObject();
        System.out.println(obj.toString());
        return obj;
    }
    
    public static int byteToInt(byte[] b){
    	return ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16)
				| ((b[1] & 0xff) << 8) | (b[0] & 0xff);
    }
    
    public static byte[] intToByte(int valor, int numeroBytes){
    	byte[] bytes = new byte[numeroBytes];
		for (int i = 0; i < numeroBytes; i++) {
			bytes[i] = (byte) (valor >>> (i * 8));
		}
		return bytes;
    }
}
