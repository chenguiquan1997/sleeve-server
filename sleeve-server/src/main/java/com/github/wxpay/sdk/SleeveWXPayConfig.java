package com.github.wxpay.sdk;

import java.io.InputStream;

public class SleeveWXPayConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return "自己的appid";
    }

    @Override
    public String getMchID() {
        return "自己的商户号";
    }

    @Override
    public String getKey() {
        return "自己的商户密钥";
    }

    /**
     * 获取商户证书内容，在使用微信退款时，需要用到商户证书
     * @return
     */
    @Override
    public InputStream getCertStream() {
        return null;
    }

    /**
     * 获取微信支付的域名，例如专门用于商户支付的 api.mch.weixin.qq.com
     * @return
     */
    @Override
    public IWXPayDomain getWXPayDomain() {
        return null;
    }
}
