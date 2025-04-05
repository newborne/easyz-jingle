package cn.easyz.temp.service;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TitleGeneratorService {


    private String openApiKey="ollama";

    private final Gson gson;

    public TitleGeneratorService(Gson gson) {
        this.gson = gson;
    }

    public String generateTitle(String text) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "qwen2.5:14b");
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(new HashMap<String, String>() {{
            put("role", "user");
            put("content", "Generate a title for the following article:\n" + text);
        }});
        paramMap.put("messages", messages);
        paramMap.put("max_tokens", 4000);

        try {
            String body = HttpRequest.post("http://127.0.0.1:11434/v1/chat/completions")
                    .header("Authorization", "Bearer " + openApiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonPrettyStr(paramMap))
                    .execute()
                    .body();

            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, true);
            JSONObject message = result.getJSONObject("message");

            return message.getStr("content").trim();
        } catch (HttpException | ConvertException e) {
            e.printStackTrace();
            throw new RuntimeException("HTTP or Conversion Exception while generating title", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception while generating title", e);
        }
    }
}
