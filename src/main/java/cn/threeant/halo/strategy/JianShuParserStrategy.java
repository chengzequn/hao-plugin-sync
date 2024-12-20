package cn.threeant.halo.strategy;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

import static cn.threeant.halo.constant.HtmlConsant.REGEX_HTML_ID;

@Slf4j
public class JianShuParserStrategy implements ParserStrategy{
    @Override
    public String parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements title = document.getElementsByClass("article-title");
            Elements tags = document.getElementsByClass("tag-title");
            Elements content = document.getElementsByClass("article-viewer");

            //爬取的是HTML内容，需要转成MD格式的内容

            String regexImg = "src=\"(https://p3-juejin\\.byteimg\\.com/[^\"]+)\"";
            String proxyImgUrl = "http://localhost:8090/"; //TODO
            String newContent = content.get(0).toString().replaceAll("<code>", "<code class=\"lang-java\">")
                .replaceAll(REGEX_HTML_ID, "")
                .replaceAll(regexImg, proxyImgUrl);
            MutableDataSet options = new MutableDataSet();

            String markdown = FlexmarkHtmlConverter.builder(options).build().convert(newContent)
                .replace("lang-java","java");

            log.info("markdown:{}",markdown);
            log.info("newContent:{}",newContent);
            // inertArticleAndTags(title,tags,content,markdown,newContent,url);
        }catch (IOException e){
            throw new ServiceException("抓取文章失败");
        }
        return null;
    }
}
