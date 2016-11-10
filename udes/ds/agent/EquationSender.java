package udes.ds.agent;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jade.content.ContentManager;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import udes.ds.agent.behaviors.GeneticSenderBehaviour;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Created by root on 16-10-26.
 */
public class EquationSender extends Agent {

    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    private ContentManager manager = (ContentManager) getContentManager();

    @Override
    protected void setup() {
        // Registration with the DF
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Sender");
        sd.setName(getName());
        sd.setOwnership("TILAB");
        dfd.setName(getAID());
        dfd.addServices(sd);
        HttpServer server = null;
        Behaviour b = new GeneticSenderBehaviour(this);
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/requests", new InputServer(queue));
            server.setExecutor(null); // creates a default executor
            server.start();
            b.getDataStore().put("queue", queue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DFService.register(this, dfd);
            addBehaviour(b);
        } catch (FIPAException e) {
            myLogger.log(Logger.SEVERE, "Agent " + getLocalName() + " - Cannot register with DF", e);
            doDelete();
        }
        System.out.println(getAID());

    }

    private class InputServer implements HttpHandler {
        ConcurrentLinkedQueue<String> queue;

        InputServer(ConcurrentLinkedQueue<String> q) {
            queue = q;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            queue.add(body);
            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
