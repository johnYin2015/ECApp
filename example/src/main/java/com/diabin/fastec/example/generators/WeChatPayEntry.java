package com.diabin.fastec.example.generators;

import com.diabin.latte.annotations.PayEntryGenerator;
import com.diabin.latte.core.wechat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.diabin.fastec.example",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
