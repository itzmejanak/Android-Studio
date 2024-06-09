package com.trex.markdown;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Render extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);

        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        String filePath = getIntent().getStringExtra("selectedFilePath");
        if (filePath != null) {
            String markdownContent = readMarkdownFile(filePath);
            if (markdownContent != null) {
                renderMarkdown(markdownContent);
            }
        }
    }

    private String readMarkdownFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            inputStream.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void renderMarkdown(String markdownContent) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(document);
        textView.setText(Html.fromHtml(html));
    }
}
