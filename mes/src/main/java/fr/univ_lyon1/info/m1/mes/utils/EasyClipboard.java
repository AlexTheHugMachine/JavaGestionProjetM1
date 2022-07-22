package fr.univ_lyon1.info.m1.mes.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public interface EasyClipboard {
    static void copy(String data) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(data);
        clipboard.setContent(content);
    }
}
