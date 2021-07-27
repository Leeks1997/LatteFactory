package kr.ac.dongyang.project;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TcpRequest extends StringRequest {

    // URL 서버 설정 (PhP연결)
    final static private String URL = "http://122.32.165.55/Message.php";
    private final Map<String, String> map;

    public TcpRequest(String id, Response.Listener<String> listener) {
        super(Request.Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("id",id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}