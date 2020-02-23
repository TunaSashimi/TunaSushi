package com.tunasushi.tuna;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tuna.R;
import com.tunasushi.tool.DeviceTool;

import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tool.ConvertTool.pxToDp;

/**
 * @author TunaSashimi
 * @date 2020-02-23 15:37
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class PropertyView {
    public static void showProperties(final TView t) {
        View propertiesView = LayoutInflater.from(t.getContext()).inflate(R.layout.properties, null);

        final TextView text_display = propertiesView.findViewById(R.id.text_display);

        final EditText edit_width = propertiesView.findViewById(R.id.edit_width);
        final EditText edit_height = propertiesView.findViewById(R.id.edit_height);

        final EditText edit_backgroundNormal = propertiesView.findViewById(R.id.edit_backgroundNormal);
        final EditText edit_backgroundPress = propertiesView.findViewById(R.id.edit_backgroundPress);
        final EditText edit_backgroundSelect = propertiesView.findViewById(R.id.edit_backgroundSelect);

        final EditText edit_textSize = propertiesView.findViewById(R.id.edit_textSize);
        final EditText edit_textColorNormal = propertiesView.findViewById(R.id.edit_textColorNormal);

        final EditText edit_strokeWidth = propertiesView.findViewById(R.id.edit_strokeWidth);
        final EditText edit_strokeColor = propertiesView.findViewById(R.id.edit_strokeColor);

        final Button btn_width_pius = propertiesView.findViewById(R.id.btn_width_pius);
        final Button btn_width_minus = propertiesView.findViewById(R.id.btn_width_minus);
        final Button btn_height_pius = propertiesView.findViewById(R.id.btn_height_pius);
        final Button btn_height_minus = propertiesView.findViewById(R.id.btn_height_minus);

        final Button btn_textSize_pius = propertiesView.findViewById(R.id.btn_textSize_pius);
        final Button btn_textSize_minus = propertiesView.findViewById(R.id.btn_textSize_minus);

        final Button btn_strokeWidth_pius = propertiesView.findViewById(R.id.btn_strokeWidth_pius);
        final Button btn_strokeWidth_minus = propertiesView.findViewById(R.id.btn_strokeWidth_minus);

        final Button btn_backgroundNormal = propertiesView.findViewById(R.id.btn_backgroundNormal);
        final Button btn_backgroundPress = propertiesView.findViewById(R.id.btn_backgroundPress);
        final Button btn_backgroundSelect = propertiesView.findViewById(R.id.btn_backgroundSelect);

        final Button btn_textColorNormal = propertiesView.findViewById(R.id.btn_textColorNormal);

        final Button btn_strokeColor = propertiesView.findViewById(R.id.btn_strokeColor);

        final ToggleButton toogle_mark = propertiesView.findViewById(R.id.toogle_mark);
        final TextView text_mark = propertiesView.findViewById(R.id.text_mark);

        final ToggleButton toogle_thisHardwareAccelerated = propertiesView.findViewById(R.id.toogle_thisHardwareAccelerated);
        final TextView text_thisHardwareAccelerated = propertiesView.findViewById(R.id.text_thisHardwareAccelerated);

        final ToggleButton toogle_canvasHardwareAccelerated = propertiesView.findViewById(R.id.toogle_canvasHardwareAccelerated);
        final TextView text_canvasHardwareAccelerated = propertiesView.findViewById(R.id.text_canvasHardwareAccelerated);


        DeviceTool.initDisplayMetrics();
        text_display.setText(DeviceTool.stringBuffer);

        edit_width.setText(String.valueOf(pxToDp(t.width)));
        edit_height.setText(String.valueOf(pxToDp(t.height)));

        edit_backgroundNormal.setText(t.getBackgroundNormal() != 0 ? Integer.toHexString(t.getBackgroundNormal()) : "00000000");
        edit_backgroundPress.setText(t.getBackgroundPress() != 0 ? Integer.toHexString(t.getBackgroundPress()) : "00000000");
        edit_backgroundSelect.setText(t.getBackgroundSelect() != 0 ? Integer.toHexString(t.getBackgroundSelect()) : "00000000");

        edit_textSize.setText(String.valueOf(pxToDp(t.getTextSize())));
        edit_textColorNormal.setText(t.getTextColorNormal() != 0 ? Integer.toHexString(t.getTextColorNormal()) : "00000000");

        edit_strokeWidth.setText(String.valueOf(pxToDp(t.getStrokeColorNormal())));
        edit_strokeColor.setText(t.getStrokeColorNormal() != 0 ? Integer.toHexString(t.getStrokeColorNormal()) : "00000000");

        //
        edit_backgroundNormal.setTextColor(t.getBackgroundNormal());
        edit_backgroundPress.setTextColor(t.getBackgroundPress());
        edit_backgroundSelect.setTextColor(t.getBackgroundSelect());
        edit_strokeColor.setTextColor(t.getStrokeColorNormal());
        edit_textColorNormal.setTextColor(t.getTextColorNormal());

        btn_backgroundNormal.setBackgroundColor(t.getBackgroundNormal());
        btn_backgroundPress.setBackgroundColor(t.getBackgroundPress());
        btn_backgroundSelect.setBackgroundColor(t.getBackgroundSelect());
        btn_strokeColor.setBackgroundColor(t.getStrokeColorNormal());
        btn_textColorNormal.setBackgroundColor(t.getTextColorNormal());

        toogle_mark.setChecked(t.isTextMark());
        text_mark.setText(String.valueOf(t.isTextMark()));

        toogle_thisHardwareAccelerated.setChecked(t.isHardwareAccelerated());
        text_thisHardwareAccelerated.setText(String.valueOf(t.isHardwareAccelerated()));

        toogle_canvasHardwareAccelerated.setChecked(t.isHardwareAccelerated());
        text_canvasHardwareAccelerated.setText(String.valueOf(t.isHardwareAccelerated()));

        //
        toogle_mark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                text_mark.setText(String.valueOf(isChecked));
            }
        });

        //
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * adt14 will lib where R in the file ID to the final tag removed All you want to set library package, which the switch ... case statement can only be changed if ... else for the job
                 */
                int viewId = view.getId();
                if (viewId == R.id.btn_width_pius) {
                    edit_width.setText(String.valueOf(Float.parseFloat(edit_width.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_width_minus) {
                    edit_width.setText(String.valueOf(Float.parseFloat(edit_width.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_height_pius) {
                    edit_height.setText(String.valueOf(Float.parseFloat(edit_height.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_height_minus) {
                    edit_height.setText(String.valueOf(Float.parseFloat(edit_height.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_textSize_pius) {
                    edit_textSize.setText(String.valueOf(Float.parseFloat(edit_textSize.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_textSize_minus) {
                    edit_textSize.setText(String.valueOf(Float.parseFloat(edit_textSize.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_strokeWidth_pius) {
                    edit_strokeWidth.setText(String.valueOf(Float.parseFloat(edit_strokeWidth.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_strokeWidth_minus) {
                    edit_strokeWidth.setText(String.valueOf(Float.parseFloat(edit_strokeWidth.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_backgroundNormal) {
                    new ColorPickerDialog(t.getContext(), t.getBackgroundNormal(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundNormal.setBackgroundColor(color);
                            edit_backgroundNormal.setTextColor(color);
                            edit_backgroundNormal.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundPress) {
                    new ColorPickerDialog(t.getContext(), t.getBackgroundPress(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundPress.setBackgroundColor(color);
                            edit_backgroundPress.setTextColor(color);
                            edit_backgroundPress.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundSelect) {
                    new ColorPickerDialog(t.getContext(), t.getBackgroundSelect(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundSelect.setBackgroundColor(color);
                            edit_backgroundSelect.setTextColor(color);
                            edit_backgroundSelect.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_textColorNormal) {
                    new ColorPickerDialog(t.getContext(), t.getTextColorNormal(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_textColorNormal.setBackgroundColor(color);
                            edit_textColorNormal.setTextColor(color);
                            edit_textColorNormal.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_strokeColor) {
                    new ColorPickerDialog(t.getContext(), t.getStrokeColorNormal(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_strokeColor.setBackgroundColor(color);
                            edit_strokeColor.setTextColor(color);
                            edit_strokeColor.setText(Integer.toHexString(color));
                        }
                    }).show();
                }
            }
        };

        btn_width_pius.setOnClickListener(onClickListener);
        btn_width_minus.setOnClickListener(onClickListener);
        btn_height_pius.setOnClickListener(onClickListener);
        btn_height_minus.setOnClickListener(onClickListener);
        btn_textSize_pius.setOnClickListener(onClickListener);
        btn_textSize_minus.setOnClickListener(onClickListener);
        btn_strokeWidth_pius.setOnClickListener(onClickListener);
        btn_strokeWidth_minus.setOnClickListener(onClickListener);

        btn_backgroundNormal.setOnClickListener(onClickListener);
        btn_backgroundPress.setOnClickListener(onClickListener);
        btn_backgroundSelect.setOnClickListener(onClickListener);

        btn_textColorNormal.setOnClickListener(onClickListener);
        btn_strokeColor.setOnClickListener(onClickListener);

        new AlertDialog.Builder(t.getContext(), android.R.style.Theme_Holo_Light)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setView(propertiesView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                        t.setBackgroundNormal(Color.parseColor("#" + edit_backgroundNormal.getText().toString().trim()));
                        t.setBackgroundPress(Color.parseColor("#" + edit_backgroundPress.getText().toString().trim()));
                        t.setBackgroundSelect(Color.parseColor("#" + edit_backgroundSelect.getText().toString().trim()));
                        t.setStrokeColorNormal(Color.parseColor("#" + edit_strokeColor.getText().toString().trim()));
                        t.setTextColorNormal(Color.parseColor("#" + edit_textColorNormal.getText().toString().trim()));

                        t.setTextSize(dpToPx(Float.parseFloat(edit_textSize.getText().toString().trim())));

                        t.setStrokeWidthNormal(dpToPx(Float.parseFloat(edit_strokeWidth.getText().toString().trim())));

                        t.setTextMark(text_mark.getText().toString().trim().equals("true") ? true : false);

                        t.setLayout(dpToPx(Float.parseFloat(edit_width.getText().toString().trim())), dpToPx(Float.parseFloat(edit_height.getText().toString().trim())));
                    }
                }).setNegativeButton("Cancel", null).create().show();
    }
}
