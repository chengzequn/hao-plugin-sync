package cn.threeant.halo.service.impl;

import cn.threeant.halo.domain.ArticleVO;
import cn.threeant.halo.service.HaloSyncService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class HaloSyncServiceImpl implements HaloSyncService {

    private static final String HALO_API_URL = "http://localhost:8090/apis/api.console.halo.run/v1alpha1/posts";
    private static final String API_KEY = "";

    private final ObjectMapper objectMapper;

    @Override
    public void sync(ArticleVO vo) {
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost post = new HttpPost(HALO_API_URL);
            //设置请求头,包括API密钥
            post.setHeader("Authorization","Bearer"+API_KEY);
            post.setHeader("Content-Type","application/json");
            //设置请求体
            String jsonBody = objectMapper.writeValueAsString(vo);
            post.setEntity(new StringEntity(jsonBody));
            //发送请求并获取响应
            client.execute(post);
        }catch (Exception e){
            log.error("同步失败");
            e.printStackTrace();
        }
    }
}
