package com.biosis.server.rest1.controller;

import com.biosis.server.rest1.entity.Mensaje;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Francis on 17/12/2015.
 */
@RestController
public class MensajeController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final Gson gson = new Gson();

    @RequestMapping("/mensaje")
    public Mensaje greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Mensaje mensaje = new Mensaje(counter.incrementAndGet(), name);
        mensaje.setMensaje("Hola mundo");
        String json = gson.toJson(mensaje);
        Socket socket;
        try {
            socket = new Socket("127.0.0.1", 5678);
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            DataOutputStream dataSalida = new DataOutputStream(socket.getOutputStream());
            byte[] byteSalida = json.getBytes(StandardCharsets.UTF_8);
            dataSalida.write(byteSalida, 0, byteSalida.length);
            System.out.println("DATA SALIDA: "+json);
            StringBuilder sb = new StringBuilder("Retorno: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String linea;
            while ((linea = in.readLine()) != null) {
                sb.append(linea);
            }
            mensaje.setMensaje(sb.toString());
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mensaje;
    }
}
