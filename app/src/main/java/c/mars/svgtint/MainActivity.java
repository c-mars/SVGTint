package c.mars.svgtint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView=(ImageView)findViewById(R.id.img);
        loadSvgWithColor(this, R.raw.inkscape, imageView, getResources().getColor(R.color.green_tint));

        ImageView iconView=(ImageView)findViewById(R.id.icon);
        Bitmap bm= null;
        try {
            bm = loadSvgWithColorToBitmap(this, R.raw.play_black, iconView.getLayoutParams().width, iconView.getLayoutParams().height, getResources().getColor(R.color.orange_tint));
            iconView.setImageBitmap(bm);
        } catch (SVGParseException e) {
            e.printStackTrace();
        }

    }

    private static boolean loadSvgWithColor(Context context, int rawId, ImageView imageView, int color){
        try {
            SVG svg = SVG.getFromResource(context, rawId);
            Picture picture=svg.renderToPicture();
            Bitmap bitmap=Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas=new Canvas(bitmap);
            canvas.drawPicture(picture);
            imageView.setImageBitmap(bitmap);
            imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } catch (SVGParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Bitmap loadSvgWithColorToBitmap(Context context, int svgResId, int width, int height, int tintColor) throws SVGParseException {

            SVG svg = SVG.getFromResource(context, svgResId);
            Picture picture=svg.renderToPicture();

            Bitmap originalBitmap=Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas=new Canvas(originalBitmap);
            canvas.drawPicture(picture);

            Bitmap bitmapWithTint=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvasWithTint=new Canvas(bitmapWithTint);
            Paint paint = new Paint();
            paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));

            Rect dst = new Rect(0, 0, width, height);
            canvasWithTint.drawBitmap(originalBitmap, null, dst, paint);

        return bitmapWithTint;
    }

}
