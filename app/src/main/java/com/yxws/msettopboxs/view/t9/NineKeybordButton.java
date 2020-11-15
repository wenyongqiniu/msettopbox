package com.yxws.msettopboxs.view.t9;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yxws.msettopboxs.R;


public class NineKeybordButton extends LinearLayout implements OnFocusChangeListener, OnClickListener {
    private TextView mTextView0;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private PopupWindow mPopWindow;


    private String VALUE_ENTER;
    private String VALUE_UP;
    private String VALUE_LEFT;
    private String VALUE_RIGHT;
    private String VALUE_DOWN;

    private final int KEYCODE_UP = KeyEvent.KEYCODE_DPAD_UP;
    private final int KEYCODE_DOWN = KeyEvent.KEYCODE_DPAD_DOWN;
    private final int KEYCODE_LEFT = KeyEvent.KEYCODE_DPAD_LEFT;
    private final int KEYCODE_RIGHT = KeyEvent.KEYCODE_DPAD_RIGHT;
    private final int KEYCODE_ENTER = KeyEvent.KEYCODE_ENTER;
    private final int KEYCODE_DPAD_CENTER = KeyEvent.KEYCODE_DPAD_CENTER;
    private final int ONLY_ONE_TEXTVIEW = -99;


    public void setTextToTextView1(String text) {
        mTextView1.setText(text);
        mTextView1.setVisibility(View.VISIBLE);
    }

    public void setTextToTextView2(String text) {
        mTextView2.setText(text);
        mTextView2.setVisibility(View.VISIBLE);

        if (mTextView1.getText().toString() != "") {
            mTextView3.setVisibility(View.VISIBLE);
            mTextView0.setVisibility(View.VISIBLE);
        }
    }

    public NineKeybordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.nine_keybord_button, this, true);
        mLinearLayout = (LinearLayout) findViewById(R.id.nineKeybordButton);
        mTextView0 = (TextView) findViewById(R.id.nineKeybordButtonTextView0);
        mTextView1 = (TextView) findViewById(R.id.nineKeybordButtonTextView1);
        mTextView2 = (TextView) findViewById(R.id.nineKeybordButtonTextView2);
        mTextView3 = (TextView) findViewById(R.id.nineKeybordButtonTextView3);
        setListener();
    }

    private void setListener() {
        mLinearLayout.setOnFocusChangeListener(this);
        mLinearLayout.setOnClickListener(this);
    }

    private void setFocued() {
        mTextView1.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextView2.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void setUnfocued() {
        mTextView1.setTextColor(getResources().getColor(R.color.colorWhite));
        mTextView2.setTextColor(getResources().getColor(R.color.colorWhite));

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.nineKeybordButton) {
            if (hasFocus) {
                setFocued();
            } else {
                setUnfocued();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nineKeybordButton) {
            showPopupWindow();
        }
    }

    private boolean isTouchInPopupWindowCircularImage(int imageViewOriginX, int imageViewOriginY, int radius, MotionEvent event) {
        boolean result = false;

        int distanceX = (int) Math.abs(imageViewOriginX - event.getRawX());
        int distanceY = (int) Math.abs(imageViewOriginY - event.getRawY());
        int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (distanceZ < radius) {
            result = true;
        }

        return result;
    }


    private int getQuadrant(int originX, int originY, float targetX, float targetY) {
        int quadrant = 1;
        if (targetX - originX >= 0 && targetY - originY >= 0) {
            quadrant = 4;
        } else if (targetX - originX <= 0 && targetY - originY >= 0) {
            quadrant = 3;
        } else if (targetX - originX <= 0 && targetY - originY <= 0) {
            quadrant = 2;
        } else if (targetX - originX > 0 && targetY - originY < 0) {
            quadrant = 1;
        }

        return quadrant;
    }

    private void popupWindowImageOnTouch(ImageView imageView, View v, MotionEvent event) {
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        int imageViewOriginX = location[0] + imageView.getWidth() / 2;
        int imageViewOriginY = location[1] + imageView.getHeight() / 2;
        int keyCode = -1;
        int bigCircularImageRadius = imageView.getWidth() / 2;
        int smallCircularImageRadius = (int) (bigCircularImageRadius * 0.4);

        if (isTouchInPopupWindowCircularImage(imageViewOriginX, imageViewOriginY, bigCircularImageRadius, event)) {
            if (isTouchInPopupWindowCircularImage(imageViewOriginX, imageViewOriginY, smallCircularImageRadius, event)) {
                keyCode = KEYCODE_ENTER;
            } else {
                int quadrant = getQuadrant(imageViewOriginX, imageViewOriginY, event.getRawX(), event.getRawY());
                float absTargetX = Math.abs(event.getRawX() - imageViewOriginX);
                float absTargetY = Math.abs(event.getRawY() - imageViewOriginY);

                switch (quadrant) {
                    case 1: {
                        if (absTargetY / absTargetX > 1) {
                            keyCode = KEYCODE_UP;
                        } else {
                            keyCode = KEYCODE_RIGHT;
                        }
                        break;
                    }
                    case 2: {
                        if (absTargetY / absTargetX > 1) {
                            keyCode = KEYCODE_UP;
                        } else {
                            keyCode = KEYCODE_LEFT;
                        }
                        break;
                    }
                    case 3: {
                        if (absTargetY / absTargetX > 1) {
                            keyCode = KEYCODE_DOWN;
                        } else {
                            keyCode = KEYCODE_LEFT;
                        }
                        break;
                    }
                    case 4: {
                        if (absTargetY / absTargetX > 1) {
                            keyCode = KEYCODE_DOWN;
                        } else {
                            keyCode = KEYCODE_RIGHT;
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }

            onKeyDown(keyCode);
        } else {
            closePopupWindow();
        }

    }

    /**
     * 显示选择输入
     */
    private void showPopupWindow() {
        if (mTextView2.getText().toString() == "") {
            onKeyDown(ONLY_ONE_TEXTVIEW);
            return;
        }

        String text = mTextView1.getText().toString() + mTextView2.getText();
        char[] letter = text.toCharArray();

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.select_popup, null);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout selectPopupLv = (LinearLayout) contentView.findViewById(R.id.select_popup_lv);

        selectPopupLv.setFocusable(true);
        selectPopupLv.setFocusableInTouchMode(true);
        mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        selectPopupLv.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                onKeyDown(keyCode);
                return false;
            }
        });

        final ImageView imageView = (ImageView) contentView.findViewById(R.id.image_view);
        contentView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindowImageOnTouch(imageView, v, event);
                return false;
            }
        });

        LayoutParams para = (LayoutParams) imageView.getLayoutParams();
        mPopWindow.showAsDropDown(this, -(para.width / 2 - getWidth() / 2), -(para.height - getHeight() / 2));
        drawImage(imageView, letter);

    }

    private void onKeyDown(int keyCode) {
        KLog.e("单键是 " + keyCode);
        switch (keyCode) {
            case KEYCODE_ENTER:
                if (mOnNineKeyListener != null) {
                    mOnNineKeyListener.onKey(VALUE_ENTER);
                }
                break;
            case KEYCODE_LEFT://左
                if (mOnNineKeyListener != null) {
                    mOnNineKeyListener.onKey(VALUE_LEFT);
                }
                break;
            case KEYCODE_UP://上
                if (mOnNineKeyListener != null) {
                    mOnNineKeyListener.onKey(VALUE_UP);
                }
                break;
            case KEYCODE_RIGHT://右
                if (mOnNineKeyListener != null) {
                    mOnNineKeyListener.onKey(VALUE_RIGHT);
                }
                break;
            case KEYCODE_DOWN://下
                if (mOnNineKeyListener != null) {
                    mOnNineKeyListener.onKey(VALUE_DOWN);
                }
                break;
            case KEYCODE_DPAD_CENTER://居中
            case ONLY_ONE_TEXTVIEW:
                if (mOnNineKeyListener != null) {
                    mOnNineKeyListener.onKey(mTextView1.getText().toString());
                }
                break;

        }
        closePopupWindow();
    }

    OnNineKeyListener mOnNineKeyListener;

    public void setOnNineKeyListener(OnNineKeyListener onNineKeyListener) {
        mOnNineKeyListener = onNineKeyListener;
    }

    public void closePopupWindow() {
        if (mPopWindow != null) {
            mPopWindow.dismiss();
            mPopWindow = null;
        }
    }

    /**
     * 话pop显示弹框
     *
     * @param imageView
     * @param letter
     */
    private void drawImage(ImageView imageView, char[] letter) {
        imageView.setBackgroundDrawable(createDrawable(R.drawable.disc, letter));
    }


    private Drawable createDrawable(int scrImageId, char[] letter) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), scrImageId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap imgTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);
        Rect rect = new Rect();
        canvas.drawBitmap(bitmap, src, dst, paint);

        paint.getTextBounds(String.valueOf(letter[0]), 0, String.valueOf(letter[0]).length(), rect);
        float letterWidth = rect.width();
        float letterHeight = rect.height();

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(25.0f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.WHITE);

        switch (letter.length) {
            case 2: {
                canvas.drawText(String.valueOf(letter[0]), width / 2 - letterWidth * 2, height / 2 + letterHeight, textPaint);//�м�
                canvas.drawText(String.valueOf(letter[1]), width / 6 - letterWidth * 2, height / 2 + letterHeight, textPaint);//��
                VALUE_ENTER = String.valueOf(letter[0]);
                VALUE_LEFT = String.valueOf(letter[1]);
                break;
            }
            case 3: {
                canvas.drawText(String.valueOf(letter[0]), width / 2 - letterWidth * 2, height / 2 + letterHeight, textPaint);//�м�
                canvas.drawText(String.valueOf(letter[1]), width / 6 - letterWidth * 2, height / 2 + letterHeight, textPaint);//��
                canvas.drawText(String.valueOf(letter[2]), width / 2 - letterWidth * 2, height / 6 + letterHeight, textPaint);//��
                VALUE_ENTER = String.valueOf(letter[0]);
                VALUE_LEFT = String.valueOf(letter[1]);
                VALUE_UP = String.valueOf(letter[2]);
                break;
            }
            case 4: {
                canvas.drawText(String.valueOf(letter[0]), width / 2 - letterWidth * 2, height / 2 + letterHeight, textPaint);//�м�
                canvas.drawText(String.valueOf(letter[1]), width / 6 - letterWidth * 2, height / 2 + letterHeight, textPaint);//��
                canvas.drawText(String.valueOf(letter[2]), width / 2 - letterWidth * 2, height / 6 + letterHeight, textPaint);//��
                canvas.drawText(String.valueOf(letter[3]), width * 5 / 6 - letterWidth * 2, height / 2 + letterHeight, textPaint);//��
                VALUE_ENTER = String.valueOf(letter[0]);
                VALUE_LEFT = String.valueOf(letter[1]);
                VALUE_UP = String.valueOf(letter[2]);
                VALUE_RIGHT = String.valueOf(letter[3]);
                break;
            }
            case 5: {
                canvas.drawText(String.valueOf(letter[0]), width / 2 - letterWidth * 2, height / 2 + letterHeight, textPaint);//�м�
                canvas.drawText(String.valueOf(letter[1]), width / 6 - letterWidth * 2, height / 2 + letterHeight, textPaint);//��
                canvas.drawText(String.valueOf(letter[2]), width / 2 - letterWidth * 2, height / 6 + letterHeight, textPaint);//��
                canvas.drawText(String.valueOf(letter[3]), width * 5 / 6 - letterWidth * 2, height / 2 + letterHeight, textPaint);//��
                canvas.drawText(String.valueOf(letter[4]), width / 2 - letterWidth * 2, height * 5 / 6 + letterHeight, textPaint);//��
                VALUE_ENTER = String.valueOf(letter[0]);
                VALUE_LEFT = String.valueOf(letter[1]);
                VALUE_UP = String.valueOf(letter[2]);
                VALUE_RIGHT = String.valueOf(letter[3]);
                VALUE_DOWN = String.valueOf(letter[4]);
                break;
            }
        }

        canvas.save();
        canvas.restore();
        return (Drawable) new BitmapDrawable(getResources(), imgTemp);

    }

}
