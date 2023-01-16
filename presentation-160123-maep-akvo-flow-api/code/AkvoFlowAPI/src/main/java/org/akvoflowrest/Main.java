package org.akvoflowrest;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main() throws IOException, URISyntaxException {
        Token token = new Token();
        System.out.println(token.getToken("deden@akvo.org", "4LRdywjhCNNhUBWFwqe^vugV"));
    }
}