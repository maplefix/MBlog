package top.maplefix.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.maplefix.model.Link;
import top.maplefix.service.LinkService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maple
 * @description 友链检测
 * @date 2020/3/21 15:06
 */
@Slf4j
@Component
public class LinkCheckTask {
    // 浏览器Agent
    public static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19";

    @Autowired
    LinkService linkService;

    /**
     * 检测友链
     */
    public void check() {
        //查询所有Link
        List<Link> linkList = linkService.selectLinkList(new Link());
        List<Link> unableAccessLinkList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer("检查友链结果:");
        for (Link link : linkList) {
            try {
                checkLinkAccessible(link.getUrl());
            } catch (Exception e) {

            }
        }
        if (ObjectUtils.isNotEmpty(unableAccessLinkList)) {
            //发送邮件
        }
    }

    /**
     * 校验Url是否可达
     *
     * @param link url地址
     */
    private void checkLinkAccessible(String link) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(link);
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        try {
            //将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet, basicHttpContext);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        new LinkCheckTask().checkLinkAccessible("https://www.maplefix.top");
    }

}
