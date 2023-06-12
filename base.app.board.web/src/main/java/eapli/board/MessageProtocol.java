package eapli.board;

import java.io.DataOutputStream;
import java.io.IOException;

public interface MessageProtocol {

    public boolean send(DataOutputStream out)  throws IOException;
    public String getContentAsString();
    public void setContentFromString(String cnt);

}
