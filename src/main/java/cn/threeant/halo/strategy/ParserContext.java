package cn.threeant.halo.strategy;

import lombok.Setter;

@Setter
public class ParserContext {

    private ParserStrategy strategy;

    public String parse(String url) {
        return strategy.parse(url);
    }
}
