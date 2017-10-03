package br.com.edecio.applistacarros;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by edecio on 02/10/2017.
 */

public class BuscaCarros extends AsyncTask<String, Void, ArrayList<String>> {

    private Context ctx;
    private ListView lvCarros;

    public BuscaCarros(Context ctx, ListView lvCarros) {
        this.ctx = ctx;
        this.lvCarros = lvCarros;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> listaCarros = new ArrayList<>();
        String ws = params[0];

        try {
            URL url = new URL(ws);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                if (in != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    StringBuilder linhaJson = new StringBuilder();
                    String reg;

                    while ((reg = br.readLine()) != null) {
                        linhaJson.append(reg);
                    }

                    JSONObject json = new JSONObject(String.valueOf(linhaJson));
                    JSONArray carros = json.getJSONArray("carros");

                    for (int i = 0; i < carros.length(); i++) {
                        JSONObject carro = carros.getJSONObject(i);
                        String modelo = carro.getString("modelo");
                        String cor = carro.getString("cor");
                        int ano = carro.getInt("ano");
                        double preco = carro.getDouble("preco");

                        NumberFormat nfBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                        listaCarros.add("Modelo: " + modelo +
                                        "\nCor: " + cor +
                                        "\nAno: " + ano +
                                        "\nPreço: " + nfBr.format(preco));
                    }
                }

            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listaCarros;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_list_item_1, strings);
        lvCarros.setAdapter(arrayAdapter);
    }
}
