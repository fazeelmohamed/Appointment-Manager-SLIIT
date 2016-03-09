package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User G on 3/9/2016.
 */
public class LoginRedirect {
    public LoginRedirect() {
    }

    public User getJSON(String result){

        JSONObject jsonObject;
        JSONArray jsonArray;
        String name="",email="";
        int id = 0,user_type = 0;
        boolean bool_result = false;

        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("server_response");

                    JSONObject JO = jsonArray.getJSONObject(0);
                    id = JO.getInt("id");
                    name = JO.getString("name");
                    email = JO.getString("email");
                    user_type = JO.getInt("user_type");
                    bool_result = JO.getBoolean("isLogin");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setUser_type(user_type);
        user.setLogin_status(bool_result);


        return user;
    }
}
