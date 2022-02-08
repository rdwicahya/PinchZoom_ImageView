package rdc.pinchzoomimageview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView image;
    int SELECT_PICTURE = 200;
    private float factor = 1.0f;
    ScaleGestureDetector SGD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnChangeImage);
        image = findViewById(R.id.imageView1);
        SGD = new ScaleGestureDetector(this, new ScaleListener());

        button.setOnClickListener(view -> pilihGambar());
    }
    public void pilihGambar(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Pilih gambar"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == RESULT_OK){
         if (requestCode == SELECT_PICTURE){
             Uri gambardipilih = data.getData();
             if (null != gambardipilih) {
                 image.setImageURI(gambardipilih);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return SGD.onTouchEvent(motionEvent);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        factor *= SGD.getScaleFactor();
        image.setScaleX(factor);
        image.setScaleY(factor);

        factor = Math.max(0.1f, Math.min(factor, 1.0f));
        return super.onScale(detector);
        }
    }
}