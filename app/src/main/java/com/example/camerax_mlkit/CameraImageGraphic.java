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
//このクラスは、カメラの画像を GraphicOverlay 上に描画するためのグラフィックオブジェクトを定義しています。
// カメラからのビットマップ画像を受け取り、それを Canvas 上に描画する処理が主な役割です。
// これにより、カメラ画像を実時間で表示するための準備が整います。

package com.example.camerax_mlkit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.camerax_mlkit.GraphicOverlay.Graphic;

/** Draw camera image to background. */
public class CameraImageGraphic extends GraphicOverlay.Graphic {

  private final Bitmap bitmap;

  public CameraImageGraphic(GraphicOverlay overlay, Bitmap bitmap) {
    super(overlay);
    this.bitmap = bitmap;
  }

  @Override
  public void draw(Canvas canvas) {
    canvas.drawBitmap(bitmap, getTransformationMatrix(), null);
  }
}
