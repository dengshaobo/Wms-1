package com.hzx.wms.weight;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;
import com.hzx.wms.app.MyApplication;
import com.hzx.wms.bean.WeightBean;
import com.hzx.wms.greendao.DaoSession;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.BlueToothTool;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.GsonUtils;
import com.hzx.wms.utils.SoundPlayUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author  qinl
 * @date  2019/7/10
*/

public class WeightActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_post)
    TextView textPost;
    @Bind(R.id.edt_repeat_oderNo)
    EditText edtRepeatOderNo;
    @Bind(R.id.txt_repeat_device)
    TextView txtRepeatDevice;
    @Bind(R.id.txt_repeat_barcode)
    TextView txtRepeatBarcode;
    @Bind(R.id.txt_repeat_totalWeight)
    TextView txtRepeatTotalWeight;
    @Bind(R.id.txt_repeat_unit)
    TextView txtRepeatUnit;
    @Bind(R.id.txt_repeat_totalNum)
    TextView txtRepeatTotalNum;

    private BluetoothAdapter mBluetoothAdapter;
    private BlueToothTool client;
    static String getWeight = "";
    static String deviceName = "";
    private int scanTimes = 0;
    private MyHandler handler;
    static StringBuffer buffer = new StringBuffer();
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);
        SoundPlayUtils.init(this);
        handler = new MyHandler(this);
        daoSession = MyApplication.getDaoSession();
        EditSearchAction searchAction = new EditSearchAction();
        searchAction.searchAction(this, edtRepeatOderNo);
        searchAction.setListener(this::intentNext);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (client == null) {
            initBle();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (client != null) {
//            client.close();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.close();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    static class MyHandler extends Handler {
        WeakReference<WeightActivity> mActivity;

        MyHandler(WeightActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WeightActivity theActivity = mActivity.get();
            switch (msg.what) {
                case BlueToothTool.CONNECT_FAILED:
                    theActivity.txtRepeatDevice.setText(String.format("%s蓝牙连接失败", deviceName));
                    theActivity.check();
                    break;
                case BlueToothTool.CONNECT_SUCCESS:
                    theActivity.txtRepeatDevice.setText(String.format("%s蓝牙连接成功", deviceName));
                    break;
                case BlueToothTool.READ_FAILED:
                    break;
                case BlueToothTool.WRITE_FAILED:
                    break;
                case BlueToothTool.DATA:
                    String strReverse = new StringBuffer(msg.obj.toString()).reverse().toString();
                    if (strReverse.length() == 8) {
                        double weight = Double.parseDouble(strReverse.replace("=", ""));
                        String weight1 = String.valueOf(weight * 100).replaceAll("^(0+)", "");
                        double weight2 = Double.parseDouble(weight1) / 100;
                        theActivity.txtRepeatTotalWeight.setText(String.valueOf(weight2));
                    } else {
                        if (buffer.length() < 70) {
                            buffer.append(strReverse);
                            if (buffer.length() > 68) {
                                String strReverse1 = new StringBuffer(buffer).reverse().toString();
                                String[] weight = strReverse1.split("=");
                                if (!weight[3].equals(theActivity.txtRepeatTotalWeight.getText().toString())) {
                                    theActivity.txtRepeatTotalWeight.setText(String.valueOf(Double.parseDouble(weight[3]) * 100 / 100));
                                }
                                buffer.delete(0, buffer.length());
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }


    private void initBle() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            RxToast.error("没有蓝牙模块");
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
        } else {
            bluetooth();
        }
    }

    private void check() {
        RxDialogSureCancel dialogSureCancel = new RxDialogSureCancel(this);
        dialogSureCancel.getContentView().setText("蓝牙连接失败，请检查蓝牙设备是否被占用");
        dialogSureCancel.getSureView().setOnClickListener(view -> {
            bluetooth();
            dialogSureCancel.dismiss();
        });
        dialogSureCancel.getCancelView().setOnClickListener(view -> {
            dialogSureCancel.dismiss();
            finish();
        });
        dialogSureCancel.show();
    }

    private void bluetooth() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                //通过array adapter在列表中添加设备名称和地址
                Log.e("bluetooth", device.getName() + "\n" + device.getAddress());
                deviceName = device.getName();
                client = new BlueToothTool(device, handler);
                client.connect();
            }
        } else {
            RxDialogSureCancel dialogSureCancel = new RxDialogSureCancel(this);
            dialogSureCancel.getContentView().setText("需要先配对设备再继续操作");
            dialogSureCancel.getSureView().setOnClickListener(view -> {
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
                dialogSureCancel.dismiss();
            });
            dialogSureCancel.getCancelView().setOnClickListener(view -> {
                dialogSureCancel.dismiss();
                finish();
            });
            dialogSureCancel.show();
        }
    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        txtRepeatBarcode.setText(message);
        putWeight(message);
    }

    private void putWeight(String message) {
        List<WeightBean> list = daoSession.queryRaw(WeightBean.class, " where logistics_no = ?", message);
        if (list.size() == 1) {
            SoundPlayUtils.play(4);
            return;
        }
        WeightBean bean = new WeightBean();
        bean.setLogistics_no(message);
        bean.setWeight(txtRepeatTotalWeight.getText().toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), GsonUtils.GsonString(bean));
        HttpUtils.getInstance().createService(Api.class)
                .putWeight(requestBody)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .doFinally(() -> loading.cancel())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                            if (!listBaseBean.getCode().equals(Constants.CODE_SUCCESS)) {
                                SoundPlayUtils.play(5);
                                RxToast.error(listBaseBean.getMsg());
                                return;
                            }
                            RxToast.success(listBaseBean.getMsg());
                            daoSession.insert(bean);
                            txtRepeatTotalNum.setText(String.format(Locale.CHINA, "扫描次数：%d", daoSession.loadAll(WeightBean.class).size()));
                        }
                        , throwable -> {
                        });
    }

    @OnClick({R.id.img_back, R.id.text_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                daoSession.deleteAll(WeightBean.class);
                finish();
                break;
            case R.id.text_post:
                break;
            default:
        }
    }

}
