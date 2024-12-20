package cn.threeant.halo.controller;

import cn.threeant.halo.domain.PostVO;
import cn.threeant.halo.enums.WebsiteType;
import cn.threeant.halo.strategy.AliCloudParserStrategy;
import cn.threeant.halo.strategy.CnBlogsParserStrategy;
import cn.threeant.halo.strategy.CsdnParserStrategy;
import cn.threeant.halo.strategy.HuaWeiCloudParserStrategy;
import cn.threeant.halo.strategy.JianShuParserStrategy;
import cn.threeant.halo.strategy.JueJinParserStrategy;
import cn.threeant.halo.strategy.OsChinaParserStrategy;
import cn.threeant.halo.strategy.ParserContext;
import cn.threeant.halo.strategy.TencentCloudParserStrategy;
import cn.threeant.halo.strategy.WechatParserStrategy;
import cn.threeant.halo.strategy.ZhiHuParserStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/api")
public class ArticleController {

    // 接收前端提交的值并打印
    @PostMapping("/submit")
    public ResponseEntity<?> submitValue(@RequestBody PostVO formData) {
        int type = formData.getType();
        String url = formData.getUrl();
        log.info("type: {},url: {}",type,url);

        WebsiteType websiteType = WebsiteType.fromCode(type);
        ParserContext context = new ParserContext();
        switch (Objects.requireNonNull(websiteType)){
            case CSDN:
                context.setStrategy(new CsdnParserStrategy());
                break;
            case JIANSHU:
                context.setStrategy(new JianShuParserStrategy());
                break;
            case ZHIHU:
                context.setStrategy(new ZhiHuParserStrategy());
                break;
            case WECHAT:
                context.setStrategy(new WechatParserStrategy());
                break;
            case CNBLOGS:
                context.setStrategy(new CnBlogsParserStrategy());
                break;
            case ALICLOUD:
                context.setStrategy(new AliCloudParserStrategy());
                break;
            case JUEJIN:
                context.setStrategy(new JueJinParserStrategy());
                break;
            case OSCHINA:
                context.setStrategy(new OsChinaParserStrategy());
                break;
            case HUAWEICLOUD:
                context.setStrategy(new HuaWeiCloudParserStrategy());
                break;
            case TENCENTCLOUD:
                context.setStrategy(new TencentCloudParserStrategy());
                break;
            default:
                break;
        }
        String parse = context.parse(url);


        return null;
    }
}