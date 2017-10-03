package br.com.edecio.applistacarros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lvCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCarros = (ListView) findViewById(R.id.lvCarros);

        BuscaCarros buscaCarros = new BuscaCarros(this, lvCarros);
        buscaCarros.execute("http://10.0.2.2/herbie_noite/lista_carros.php");
    }
}
