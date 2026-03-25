package data;

import utils.DataGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleSheetsDataSource implements DataSource {

    private static final String API_URL = "https://script.google.com/macros/s/AKfycbzO5Cpqus6qX0XExvdHNJUzxeLJpZoP4YboosBqOZoCHB_vxrmI-GgpS3U6HhxqMlk84A/exec";

    @Override
    public List<DataGenerator.UsuarioData> obtenerDatos() {
        List<DataGenerator.UsuarioData> lista = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONArray jsonArray = new JSONArray(response.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                DataGenerator.UsuarioData usuario = new DataGenerator.UsuarioData();

                // 🔥 Conversión segura para TODOS los campos
                usuario.nombre = String.valueOf(obj.get("nombre"));
                usuario.apellido = String.valueOf(obj.get("apellido"));
                usuario.email = String.valueOf(obj.get("email"));
                usuario.celular = String.valueOf(obj.get("celular")); // ← aquí estaba el error
                usuario.direccion = String.valueOf(obj.get("direccion"));
                usuario.genero = String.valueOf(obj.get("genero"));
                usuario.fechaNacimiento = String.valueOf(obj.get("fechaNacimiento"));

                // Materias (siempre string con ;)
                usuario.materias = Arrays.asList(
                        String.valueOf(obj.get("materias")).split(";")
                );

                // Hobbies (puede venir vacío o null)
                String hobbiesStr = String.valueOf(obj.opt("hobbies"));

                if (hobbiesStr != null && !hobbiesStr.equals("null") && !hobbiesStr.isEmpty()) {
                    usuario.hobbies = Arrays.asList(hobbiesStr.split(";"));
                } else {
                    usuario.hobbies = new ArrayList<>();
                }

                usuario.estado = String.valueOf(obj.get("estado"));
                usuario.ciudad = String.valueOf(obj.get("ciudad"));

                lista.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error leyendo Google Sheets");
        }

        return lista;
    }
}