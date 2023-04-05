package org.optimia.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface LoadImgFromUrl {

    /**
     * Carga la imagen enlazada con el url
     */
    Function<String, Bitmap> loadImage = (url) -> {
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Callable<Bitmap> tarea = () -> {
            InputStream in = new java.net.URL(url).openStream();
            return BitmapFactory.decodeStream(in);
        };
        Future<Bitmap> future = exe.submit(tarea);
        exe.shutdown();
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    };

  ////Android*******************************************************
    /**
     * Asigna la imagen descargada del url en el visor de imagenes
     */
    BiConsumer<ImageView,String> setImageViewUrl = (iv, url) -> iv.setImageBitmap(loadImage.apply(url));

    /**
     * Asigna la imagen descargada del url en el drawable
     */
    BiConsumer<ImageView, Drawable> setImageViewDrawable = (iv, dr) -> iv.setImageBitmap(((BitmapDrawable) dr).getBitmap());
    
     /**
     * retorna una image drawable compuesta por un gradiente de dos colores de arriba hacia abajo
     */
    Function<int[], GradientDrawable> getGradiente = (colores) -> new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colores);

    
    
