package c.mars.svgtint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.PorterDuff;
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
        loadSvgWithColor(this, R.raw.sprint, imageView, Color.GREEN);
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

}
