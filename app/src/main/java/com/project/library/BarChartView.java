package com.project.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarChartView extends View {
    private Map<String, Integer> src;
    private Paint painter;
    private List<String> labels;

    public BarChartView(Context ctx, Map<String, Integer> src) {
        super(ctx);
        this.src = src;
        this.painter = new Paint(Paint.ANTI_ALIAS_FLAG);
        painter.setColor(Color.GREEN);
        labels = new ArrayList<>(src.keySet());
    }

    private void drawLabel(Canvas cnv, float x1, float elemW, float pHeight, float totalH, String label) {
        painter.setColor(Color.RED);
        painter.setTextSize((float) (0.2* elemW));
        float x = x1 + elemW / 2;
        float y = (float)(0.5 * pHeight + totalH);
        cnv.rotate(270, x, y);
        cnv.drawText(label + " - " + src.get(label), x, y, painter);
        cnv.rotate(-270, x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (src != null && src.size() > 0){
            painter.setStrokeWidth((float) (getHeight() * 0.01));
            float pWidth = (float) (getWidth() * 0.1);
            float pHeight = (float) (getHeight() * 0.1);
            float totalWidth = getWidth() - 2 * pWidth;
            float totalHeight = getHeight() - 2 * pHeight;
            canvas.drawLine(pWidth, pHeight, pWidth, pHeight + totalHeight, painter);
            canvas.drawLine(pWidth, pHeight + totalHeight, pWidth + totalWidth, pHeight + totalHeight, painter);

            double maximum = 0;
            for (double value : src.values()) if (maximum < value) maximum = value;
            float elemWidth = totalWidth / src.size();
            for (int i = 0; i < labels.size(); i++) {
                int barColor = Color.argb(100, 10*i,30*i, 40*i);
                painter.setColor(barColor);
                float x1 = pWidth + i * elemWidth;
                float y1 = (float) ((1 - src.get(labels.get(i)) / maximum) * totalHeight + pHeight);
                float x2 = x1 + elemWidth;
                float y2 = pHeight + totalHeight;
                canvas.drawRect(x1, y1, x2, y2, painter);
                drawLabel(canvas, x1, elemWidth, pHeight, totalHeight, labels.get(i));
            }
        }
    }
}
