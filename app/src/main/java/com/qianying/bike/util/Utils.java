package com.qianying.bike.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	/**
	 * Save image to the SD card
	 * 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static String savePhoto(Bitmap photoBitmap, String path,
			String photoName) {
		String localPath = null;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File photoFile = new File(path, photoName + ".png");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) { // ת�����
						localPath = photoFile.getPath();
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				localPath = null;
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				localPath = null;
				e.printStackTrace();
			} finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
						fileOutputStream = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return localPath;
	}



	public static Bitmap toRoundBitmap(Bitmap bitmap, Uri tempUri) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		return output;
	}

    public static boolean isIdentityCard(EditText view) {
        boolean flag = true;
        String licenc = view.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*
         * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
         * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
         * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
         * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
         * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
         * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
         */
        String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";

        Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
        Matcher matcher = pattern.matcher(licenc);
        Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
        Matcher matcher2 = pattern2.matcher(licenc);
        // 粗略判断
        if (!matcher.find() && !matcher2.find()) {
//            view.setError("身份证号必须为15或18位数字（最后一位可以为X）");
            flag = false;
        } else {
            // 判断出生地
            if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
//                view.setError("身份证号前两位不正确！");
                flag = false;
            }

            // 判断出生日期
            if (licenc.length() == 15) {
                String birth = "19" + licenc.substring(6, 8) + "-"
                        + licenc.substring(8, 10) + "-"
                        + licenc.substring(10, 12);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
//                        view.setError("出生日期非法！");
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
//                        view.setError("出生日期不能在今天之后！");
                        flag = false;
                    }
                } catch (ParseException e) {
//                    view.setError("出生日期非法！");
                    flag = false;
                }
            } else if (licenc.length() == 18) {
                String birth = licenc.substring(6, 10) + "-"
                        + licenc.substring(10, 12) + "-"
                        + licenc.substring(12, 14);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
//                        view.setError("出生日期非法！");
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
//                        view.setError("出生日期不能在今天之后！");
                        flag = false;
                    }
                } catch (ParseException e) {
//                    view.setError("出生日期非法！");
                    flag = false;
                }
            } else {
//                view.setError("身份证号位数不正确，请确认！");
                flag = false;
            }
        }
        if (!flag) {
            view.requestFocus();
        }
        return flag;
    }

}
