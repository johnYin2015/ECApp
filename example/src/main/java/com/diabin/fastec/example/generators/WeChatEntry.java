package com.diabin.fastec.example.generators;

import com.diabin.latte.annotations.EntryGenerator;
import com.diabin.latte.core.wechat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.diabin.fastec.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
