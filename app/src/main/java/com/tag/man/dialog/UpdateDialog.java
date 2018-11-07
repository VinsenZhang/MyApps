package com.tag.man.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tag.man.config.AppConfig;
import com.tag.man.vo.UpdateVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateDialog {


    // 外存sdcard存放路径
    private static final String FILE_PATH = AppConfig.SD_ADS_PATH;
    // 下载应用存放全路径
    private static final String FILE_NAME = FILE_PATH + "songshu.apk";
    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 1;


    private ProgressBar progress;
    private Dialog dialog;
    private TextView title;
    private TextView version;
    private TextView content;
    private  TextView cancel;
    private  TextView update;

    private Activity activity;
    private  LinearLayout updateBtnContainer;
    private  TextView downloadingTips;

    public UpdateDialog(Activity activity) {
        this.activity = activity;

//        dialog = new Dialog(activity, R.style.fullscreenNotTitle);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//        dialog.setCanceledOnTouchOutside(false);
//
//
//        View contentView = LayoutInflater.from(activity).inflate(R.layout.app_update_pop, null);
//
//        updateBtnContainer = contentView.findViewById(R.id.update_btn_container);
//
//        downloadingTips = contentView.findViewById(R.id.download_bottom_tips);
//
//        title = contentView.findViewById(R.id.title);
//        version = contentView.findViewById(R.id.version);
//
//        content = contentView.findViewById(R.id.content);
//
//        progress = contentView.findViewById(R.id.update_progress);
//
//        cancel = contentView.findViewById(R.id.cancel);
//        update = contentView.findViewById(R.id.update);
//
//
//        dialog.setContentView(contentView);
    }


    public void show(UpdateVo updateVo) {

        title.setText(Html.fromHtml(updateVo.getMarked_words()));

        version.setText(Html.fromHtml(updateVo.getNew_version()));

        content.setText(Html.fromHtml(updateVo.getContent()));

        if ("1".equals(updateVo.getForce())) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadApk(updateVo.getUpdate_url());
            }
        });

        dialog.show();
    }


    private void downloadApk(String url) {
        title.setText("新版本下载中");
        updateBtnContainer.setVisibility(View.GONE);
        downloadingTips.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        new DownloadAsyncTask().execute(url);
    }

    /**
     * 下载新版本应用
     */
    class DownloadAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            URL url;
            HttpURLConnection connection = null;
            InputStream in = null;
            FileOutputStream out = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    in = connection.getInputStream();
                    long fileLength = connection.getContentLength();
                    File file_path = new File(FILE_PATH);
                    if (!file_path.exists()) {
                        file_path.mkdir();
                    }
                    out = new FileOutputStream(new File(FILE_NAME));//为指定的文件路径创建文件输出流
                    byte[] buffer = new byte[1024 * 1024];
                    int len = 0;
                    long readLength = 0;

                    while ((len = in.read(buffer)) != -1) {

                        out.write(buffer, 0, len);//从buffer的第0位开始读取len长度的字节到输出流
                        readLength += len;

                        int curProgress = (int) (((float) readLength / fileLength) * 100);

                        publishProgress(curProgress);

                        if (readLength >= fileLength) {
                            break;
                        }
                    }

                    out.flush();
                    return INSTALL_TOKEN;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == INSTALL_TOKEN) {
                dialog.dismiss();//关闭进度条
                //安装应用
                installApp();
            } else {
                downloadErr();
            }
        }
    }

    private void downloadErr() {
        title.setText("下载出错了~");
        updateBtnContainer.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        downloadingTips.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        update.setText("重新下载");

    }

    /**
     * 安装新版本应用
     */
    private void installApp() {
        File appFile = new File(FILE_NAME);
        if (!appFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(activity, "com.songshu.jucai.fileprovider",
                    appFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(appFile),
                    "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

}
