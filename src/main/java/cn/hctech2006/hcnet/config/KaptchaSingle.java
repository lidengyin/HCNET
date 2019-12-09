package cn.hctech2006.hcnet.config;



import java.util.Properties;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class KaptchaSingle {
    private static KaptchaSingle instance;

    private KaptchaSingle() {
    };

    public static KaptchaSingle getInstance() {
        if (instance == null) {
            instance = new KaptchaSingle();
        }
        return instance;
    }

    /**
     * 生成DefaultKaptcha 默认配置
     * @return
     */
    public DefaultKaptcha produce() {
        //配置图形验证码的基本参数
        Properties properties = new Properties();
        //图片宽度
        properties.setProperty("kaptcha.image.width","150");
        //图片长度
        properties.setProperty("kaptaca.image.height","50");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length","4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}