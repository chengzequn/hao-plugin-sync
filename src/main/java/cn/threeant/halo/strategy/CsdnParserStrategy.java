package cn.threeant.halo.strategy;

import cn.threeant.halo.domain.ArticleVO;
import cn.threeant.halo.domain.Content;
import cn.threeant.halo.domain.ContentUpdateParam;
import cn.threeant.halo.domain.PostRequest;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.content.Post;
import run.halo.app.core.extension.content.Snapshot;
import run.halo.app.extension.ListResult;
import run.halo.app.extension.Metadata;
import run.halo.app.extension.MetadataOperator;
import run.halo.app.infra.utils.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Slf4j
public class CsdnParserStrategy implements ParserStrategy{

    @Override
    public String parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();

            //删除空的a标签
            document.select("a").stream().filter(link->link.text().trim().isEmpty())
                .toList().forEach(Element::remove);

            Elements title = document.getElementsByClass("title-article");
            Elements tags = document.getElementsByClass("tag-link");
            Elements content = document.getElementsByClass("article_content");
            if(StringUtils.isBlank(content.toString())){
                throw new ServiceException("抓取文章失败");
            }

            String regex = "src=\"(https://img-blog\\.csdnimg\\.cn/[^\"]+)\"";
            String proxyImgUrl = "http://localhost:8090/"; //TODO
            //爬取的是HTML内容，需要转成MD格式的内容
            String newContent = content.get(0).toString().replaceAll("<code>", "<code class=\"lang-java\">")
                .replaceAll(regex,proxyImgUrl)
                .replaceAll("content_views","");
            MutableDataSet options = new MutableDataSet();

            String markdown = FlexmarkHtmlConverter.builder(options).build().convert(newContent)
                .replace("lang-java","java");

            log.info("markdown:{}",markdown);
            // inertArticleAndTags(title,tags,content,markdown,newContent,url);


            Post post = new Post();
            Post.PostSpec postSpec = new Post.PostSpec();
            postSpec.setTitle(title.text());
            postSpec.setSlug(title.text());
            postSpec.setCover("");
            postSpec.setDeleted(false);
            postSpec.setPublish(false);
            postSpec.setPinned(false);
            postSpec.setAllowComment(true);
            postSpec.setVisible(Post.VisibleEnum.PUBLIC);
            postSpec.setPriority(0);
            Post.Excerpt excerpt = new Post.Excerpt();
            excerpt.setAutoGenerate(true);
            excerpt.setRaw("");
            postSpec.setExcerpt(excerpt);
            List<String> categories = new ArrayList<>();
            postSpec.setCategories(categories);
            List<String> tags2 = new ArrayList<>();
            postSpec.setTags(tags2);
            List<Map<String, String>> htmlMetas = new ArrayList<>();
            postSpec.setHtmlMetas(htmlMetas);

            post.setSpec(postSpec);
            // post.setApiVersion();
            // post.setKind();
            // Metadata metadata = new Metadata();
            // metadata.setName();
            // Map<String, String> annotations = new HashMap<>();
            // metadata.setAnnotations(annotations);
            // post.setMetadata(metadata);


        return null;
    }


}
