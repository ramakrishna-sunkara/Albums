package com.ram.album.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ram.album.R;

public class AppEditText extends LinearLayout {

    private AppEditText editText;

    private Integer fieldInputType;
    private String fieldInputHint;

    public AppEditText(Context context) {
        super(context);
    }

    public AppEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppEditText, 0, 0);

        fieldInputType = attrs.getInt(
                R.styleable.AppEditText_android_inputType,
                InputType.TYPE_TEXT_VARIATION_NORMAL
        );
        fieldInputHint = attrs.getString(
                R.styleable.AppEditText_android_hint
        );

        attrs.recycle();

        inflate(getContext(), R.layout.view_edit_text, this);

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        editText = findViewById(R.id.editText);
        editText.fieldInputType = fieldInputType;
        editText.fieldInputHint = fieldInputHint;
        editText.setFocusableInTouchMode(true);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}