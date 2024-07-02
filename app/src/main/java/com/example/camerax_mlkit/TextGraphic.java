/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.camerax_mlkit;

import static java.lang.Math.max;
import static java.lang.Math.min;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;

import com.example.camerax_mlkit.GraphicOverlay.Graphic;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.Text.Element;
import com.google.mlkit.vision.text.Text.Line;
import com.google.mlkit.vision.text.Text.Symbol;
import com.google.mlkit.vision.text.Text.TextBlock;

import java.util.Arrays;
import java.util.Locale;

/**
 * 関連するグラフィックオーバーレイビュー内で、TextBlockの位置、
 * サイズ、およびIDを描画するためのグラフィックインスタンスです。
 */
public class TextGraphic extends Graphic {

  private static final String TAG = "TextGraphic";
  private static final String TEXT_WITH_LANGUAGE_TAG_FORMAT = "%s:%s";

  private static final int TEXT_COLOR = Color.BLACK;
  private static final int MARKER_COLOR = Color.WHITE;
  private static final float TEXT_SIZE = 54.0f;
  private static final float STROKE_WIDTH = 4.0f;

  // 描画に使用するペイントオブジェクト
  private final Paint rectPaint;
  private final Paint textPaint;
  private final Paint labelPaint;
  private final Text text;
  private final boolean shouldGroupTextInBlocks;
  private final boolean showLanguageTag;
  private final boolean showConfidence;






  TextGraphic(
      GraphicOverlay overlay,
      Text text,
      boolean shouldGroupTextInBlocks,
      boolean showLanguageTag,
      boolean showConfidence) {
    super(overlay);

    //テキスト取得？
    this.text = text;
    this.shouldGroupTextInBlocks = shouldGroupTextInBlocks;
    this.showLanguageTag = showLanguageTag;
    this.showConfidence = showConfidence;

    rectPaint = new Paint();
    rectPaint.setColor(MARKER_COLOR);
    rectPaint.setStyle(Paint.Style.STROKE);
    rectPaint.setStrokeWidth(STROKE_WIDTH);

    textPaint = new Paint();
    textPaint.setColor(TEXT_COLOR);
    textPaint.setTextSize(TEXT_SIZE);

    labelPaint = new Paint();
    labelPaint.setColor(MARKER_COLOR);
    labelPaint.setStyle(Paint.Style.FILL);
    // GraphicOverlay を再描画する
    postInvalidate();
  }

  /** 「指定されたキャンバスに、テキストブロックの位置、サイズ、および生の値の注釈を描画します。」*/
  @Override
  public void draw(Canvas canvas) {
    //ログ出力を行っている。
    Log.d(TAG, "Text is: " + text.getText());
    for (TextBlock textBlock : text.getTextBlocks()) {
      // ボックスの底部にテキストをレンダリングします。
      Log.d(TAG, "TextBlock text is: " + textBlock.getText());
      //テキスト範囲をログに出力
      Log.d(TAG, "TextBlock boundingbox is: " + textBlock.getBoundingBox());
      Log.d(TAG, "TextBlock cornerpoint is: " + Arrays.toString(textBlock.getCornerPoints()));
      //テキストを画面に描画
      if (shouldGroupTextInBlocks) {
        String text =
            showLanguageTag
                ? String.format(
                    TEXT_WITH_LANGUAGE_TAG_FORMAT,
                    textBlock.getRecognizedLanguage(),
                    textBlock.getText())
                : textBlock.getText();
        //テキスト描画
        drawText(
            text,
            new RectF(textBlock.getBoundingBox()),
            TEXT_SIZE * textBlock.getLines().size() + 2 * STROKE_WIDTH,
            canvas);
      } else {
        //行で認識した文字を
        for (Line line : textBlock.getLines()) {
          Log.d(TAG, "Line text is: " + line.getText());
          Log.d(TAG, "Line boundingbox is: " + line.getBoundingBox());
          Log.d(TAG, "Line cornerpoint is: " + Arrays.toString(line.getCornerPoints()));
          Log.d(TAG, "Line confidence is: " + line.getConfidence());
          Log.d(TAG, "Line angle is: " + line.getAngle());
//          String text =
//              showLanguageTag
//                  ? String.format(
//                      TEXT_WITH_LANGUAGE_TAG_FORMAT, line.getRecognizedLanguage(), line.getText())
//                  : line.getText();
//          text =
//              showConfidence
//                  ? String.format(Locale.US, "%s (%.2f)", text, line.getConfidence())
//                  : text;
//          drawText(text, new RectF(line.getBoundingBox()), TEXT_SIZE + 2 * STROKE_WIDTH, canvas);

          //単語単位で認識した文字を出力
          for (Element element : line.getElements()) {
            Log.d(TAG, "Element text is: " + element.getText());
            Log.d(TAG, "Element boundingbox is: " + element.getBoundingBox());
            Log.d(TAG, "Element cornerpoint is: " + Arrays.toString(element.getCornerPoints()));
            Log.d(TAG, "Element language is: " + element.getRecognizedLanguage());
            Log.d(TAG, "Element confidence is: " + element.getConfidence());
            Log.d(TAG, "Element angle is: " + element.getAngle());
            String text_element =
                    showLanguageTag
                      ? String.format(
                              TEXT_WITH_LANGUAGE_TAG_FORMAT,element.getRecognizedLanguage(),element.getText())
                            : element.getText();
            text_element =
                    showConfidence
                      ? String.format(Locale.US,"%s (%.2f)",text_element,element.getConfidence())
                            :text_element;
                    drawText(text_element,new RectF(element.getBoundingBox()), TEXT_SIZE + 2 * STROKE_WIDTH, canvas);
            //一文字ずつ文字を出力
            for (Symbol symbol : element.getSymbols()) {
              Log.d(TAG, "Symbol text is: " + symbol.getText());
              Log.d(TAG, "Symbol boundingbox is: " + symbol.getBoundingBox());
              Log.d(TAG, "Symbol cornerpoint is: " + Arrays.toString(symbol.getCornerPoints()));
              Log.d(TAG, "Symbol confidence is: " + symbol.getConfidence());
              Log.d(TAG, "Symbol angle is: " + symbol.getAngle());
            }
          }
        }
      }
    }
  }



  private void drawText(String text, RectF rect, float textHeight, Canvas canvas) {
    // If the image is flipped, the left will be translated to right, and the right to left.
    float x0 = translateX(rect.left);
    float x1 = translateX(rect.right);
    rect.left = min(x0, x1);
    rect.right = max(x0, x1);
    rect.top = translateY(rect.top);
    rect.bottom = translateY(rect.bottom);
    canvas.drawRect(rect, rectPaint);
    float textWidth = textPaint.measureText(text);
    canvas.drawRect(
        rect.left - STROKE_WIDTH,
        rect.top - textHeight,
        rect.left + textWidth + 2 * STROKE_WIDTH,
        rect.top,
        labelPaint);
    // Renders the text at the bottom of the box.
    canvas.drawText(text, rect.left, rect.top - STROKE_WIDTH, textPaint);
  }
}
