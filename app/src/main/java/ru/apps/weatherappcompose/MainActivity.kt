  package ru.apps.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.apps.weatherappcompose.ui.theme.WeatherAppComposeTheme

const val API_KEY = "c77d8f0d8fc24406a19122925221909"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Krasnodar", this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val state = remember{
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxHeight(0.5f).fillMaxWidth()
            , contentAlignment = Alignment.Center)
            {
                Text(text = "Температура в $name = ${state.value} C°")
        }
        Box(modifier = Modifier
            .fillMaxHeight().fillMaxWidth()
            , contentAlignment = Alignment.BottomCenter){
            Button(onClick = {
                getResult(name, state, context)
            }, modifier = Modifier.padding(horizontal = 15.dp, vertical = 30.dp)
                .fillMaxWidth()
            ) {
                Text(text = "Обновить")
            }
        }
    }
}

private fun getResult(city: String,state: MutableState<String>, context: Context){
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"

    val qeue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("current").getString("temp_c")
        },
        {
            error ->
            Log.d("MyLog", "error $error")
        }

    )
    qeue.add(stringRequest)
}

