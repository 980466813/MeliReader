package com.lning.melireader.ui.main.user.info;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.UpdateUserEvent;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.UserInfoContact;
import com.lning.melireader.core.app.constant.TokenInvalid;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.DialogUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.PhoneUtils;
import com.lning.melireader.presenter.UserInfoPresenter;
import com.lning.melireader.ui.login.LoginFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/2/8.
 */

public class UserInfoFragment extends BaseFragment<UserInfoPresenter>
        implements UserInfoContact.View {

    private static final String PHOTO_FILE_NAME = "temp.jpg";
    private static final int PHOTO_REQUEST_CAMERA = 0x1100;
    private static final int PHOTO_REQUEST_GALLERY = 0x1101;
    private static final int PHOTO_REQUEST_GALLERY_KITKAT = 0x1102;
    private static final int PHOTO_REQUEST_CUT = 0x1103;
    @BindView(R.id.user_info_nickname_tv)
    AppCompatTextView userInfoNicknameTv;

    @BindView(R.id.user_info_profile_iv)
    AppCompatImageView userInfoProfileIv;

    @BindView(R.id.user_info_address_tv)
    AppCompatTextView userInfoAddressIv;

    @BindView(R.id.user_info_gender_tv)
    AppCompatTextView userInfoGenderTv;

    @BindView(R.id.user_info_birthday_tv)
    AppCompatTextView userInfoBirthdayTv;

    @Inject
    RxPermissions rxPermissions;

    private UserVo mUserVo;
    private String userId;
    private Long roleId;
    private File tempFile;
    private int TAKEPAHTO = 0;
    private Uri uriClipUri;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        initToolbar(mView, getString(R.string.tips_user_info), R.mipmap.ic_navigation_back);
        initBasicData();
    }

    private void initBasicData() {
//        mUserVo = new UserVo();
//        mUserVo.setId(userId);
//        mUserVo.setRid(3L);
//        mUserVo.setProfile("123456.jpg");
//        mUserVo.setBirthday(new Date());
//        mUserVo.setAddress("福建省福州市");
//        mUserVo.setSignature(null);
//        mUserVo.setGender((byte) -1);
//        mUserVo.setNickname("测试用户名");
//        onGetUserDetailInfoSuccess(mUserVo);
        mPresenter.getUserDetailInfo();
    }

    @OnClick(value = {R.id.user_info_profile_cl, R.id.user_info_address_cl, R.id.user_info_birthday_cl,
            R.id.user_info_nickname_cl, R.id.user_info_signature_cl, R.id.user_info_gender_cl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_profile_cl:
                DialogUtils.showProfileDialog(getContext(), new SimpleItemClickListenerAdapter() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        mPresenter.requestPermission(position);
                    }
                });
                break;
            case R.id.user_info_address_cl:
                start(UpdateDialogFragment.newInstance(mUserVo.getId(), mUserVo.getRid(), UpdateUserEvent.createAddressEvent(mUserVo.getAddress())));
                break;
            case R.id.user_info_birthday_cl:
                mPresenter.showTimePickerDialog(getContext(), getString(R.string.tips_user_birthday), mUserVo.getBirthday() == null ? new Date() : mUserVo.getBirthday(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mPresenter.updateUserBirthday(mUserVo, date);
                    }
                });
                break;
            case R.id.user_info_nickname_cl:
                start(UpdateDialogFragment.newInstance(mUserVo.getId(), mUserVo.getRid(), UpdateUserEvent.createNicknameEvent(mUserVo.getNickname())));
                break;
            case R.id.user_info_signature_cl:
                start(UpdateDialogFragment.newInstance(mUserVo.getId(), mUserVo.getRid(), UpdateUserEvent.createSignatureEvent(mUserVo.getSignature())));
                break;
            case R.id.user_info_gender_cl:
                start(UpdateDialogFragment.newInstance(mUserVo.getId(), mUserVo.getRid(), UpdateUserEvent.createGenderEvent(mUserVo.getGender() + "")));
                break;
            default:
                break;
        }
    }

    public static BaseFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    public void onGetUserDetailInfoSuccess(UserVo userVo) {
        mUserVo = userVo;
        String address = TextUtils.isEmpty(mUserVo.getAddress()) || "null".equals(mUserVo.getAddress()) ? "" : mUserVo.getAddress();
        String signature = TextUtils.isEmpty(mUserVo.getSignature()) || "null".equals(mUserVo.getSignature()) ? "" : mUserVo.getSignature();
        mUserVo.setAddress(address);
        GlideUtils.loadUserHead(userInfoProfileIv, String.format(AppConstant.IMAGE_URL, userVo.getProfile()));
        userInfoNicknameTv.setText(userVo.getNickname());
        userInfoAddressIv.setText(userVo.getAddress());
        userInfoGenderTv.setText(userVo.getGender() == 0 ? getString(R.string.tips_secret) : userVo.getGender() == 1 ? getString(R.string.tips_user_male)
                : getString(R.string.tips_user_female));
        userInfoBirthdayTv.setText(userVo.getBirthday() == null ? getString(R.string.tips_secret) : CommonUtils.formatDate("yyyy-MM-dd", userVo.getBirthday()));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().post(mUserVo);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfoSuccess(UpdateUserEvent event) {
        View view = mView.findViewById(event.viewId);
        if (view instanceof AppCompatTextView) {
            if (event.viewId == UpdateUserEvent.GENDER_TYPE) {
                ((AppCompatTextView) view).setText("1".equals(event.value) ? "男" : "2".equals(event.value) ? "女" : "保密");
            } else {
                ((AppCompatTextView) view).setText(event.value);
            }
        }
        mUserVo.setNickname(event.viewId == UpdateUserEvent.NICKNAME_TYPE ? event.value : mUserVo.getNickname());
        mUserVo.setProfile(event.viewId == UpdateUserEvent.IMAGE_TYPE ? event.value : mUserVo.getProfile());
        mUserVo.setSignature(event.viewId == UpdateUserEvent.SIGNATURE_TYPE ? event.value : mUserVo.getSignature());
        mUserVo.setAddress(event.viewId == UpdateUserEvent.ADDRESS_TYPE ? event.value : mUserVo.getAddress());
        mUserVo.setGender(event.viewId == UpdateUserEvent.GENDER_TYPE ? Byte.parseByte(event.value) : mUserVo.getGender());
        mUserVo.setBirthday(event.viewId == UpdateUserEvent.BIRTHDAY_TYPE ? CommonUtils.formatDate("yyyy-MM-dd", event.value) : mUserVo.getBirthday());
        onGetUserDetailInfoSuccess(mUserVo);
    }

    @Override
    public void onGetPermissionSuccess(int type) {
        LogUtils.d("授权成功！！" + type);
        if (type == 0) {
            try {
                if (PhoneUtils.hasSdcard()) {// 判断存储卡是否可以用，可用进行存储
                    // 启动系统相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri;
                    tempFile = new File(Environment.getExternalStorageDirectory() + "/cache/", PHOTO_FILE_NAME);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // 临时添加一个拍照权限
                        TAKEPAHTO = 1;
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        uri = FileProvider.getUriForFile(getSimpleActivity(), "meli.reader", tempFile);
                    } else {
                        uri = Uri.fromFile(tempFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, PHOTO_REQUEST_CAMERA);//携带请求码
                } else {
                    showMessage("未找到存储卡，无法存储照片！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY_KITKAT);
            } else {
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (resultCode != RESULT_OK) return;
            String path = data.getData().getPath();
            Bitmap image = BitmapFactory.decodeFile(path);
            File faceFile;
            try {
                faceFile = saveBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri fileUri = Uri.fromFile(faceFile);
            crop(fileUri);
        } else if (requestCode == PHOTO_REQUEST_GALLERY_KITKAT) {
            if (resultCode != RESULT_OK) return;
            Uri contentUri = data.getData();
            if (contentUri == null) return;
            File faceFile;
            try {
                ParcelFileDescriptor parcelFileDescriptor =
                        getSimpleActivity().getContentResolver().openFileDescriptor(contentUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                faceFile = saveBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri fileUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = FileProvider.getUriForFile(getSimpleActivity(),
                        "meli.reader", faceFile);
            } else {
                fileUri = Uri.fromFile(faceFile);
            }
            crop(fileUri);
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {//从相机返回的数据
            crop(Uri.fromFile(tempFile));
        } else if (requestCode == PHOTO_REQUEST_CUT) {//从剪切图片返回的数据
            if (data == null) return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (TAKEPAHTO == 1) {
                    uriClipUri = Uri.fromFile(tempFile);
                } else {
                    //设置裁剪的图片地址Uri
                    uriClipUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "clip.jpg");
                }
            } else {
                uriClipUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "clip.jpg");
            }
            try {
                ParcelFileDescriptor parcelFileDescriptor =
                        getSimpleActivity().getContentResolver().openFileDescriptor(uriClipUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                File faceFile = saveBitmap(image);
                if (faceFile == null) {
                    LogUtils.d("获取图片失败");
                    return;
                }
                mPresenter.uploadProfile(mUserVo, faceFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    protected void onTokenError(TokenInvalid invalid) {
        mPresenter.clearAllLoginUserInfo();
        dismissDialog();
        startWithPop(LoginFragment.newInstance());
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");//裁剪的图片uri和图片类型
        intent.putExtra("crop", "true");//设置允许裁剪，如果不设置，就会跳过裁剪的过程，还可以设置putExtra("crop", "circle")
        intent.putExtra("aspectX", 1);//裁剪框的 X 方向的比例,需要为整数
        intent.putExtra("aspectY", 1);//裁剪框的 Y 方向的比例,需要为整数
        intent.putExtra("outputX", 60);//返回数据的时候的X像素大小。
        intent.putExtra("outputY", 60);//返回数据的时候的Y像素大小。
        //uritempFile为Uri类变量，实例化uritempFile
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (TAKEPAHTO == 1) {
                //开启临时访问的读和写权限
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
                uriClipUri = uri;
            } else {
                //设置裁剪的图片地址Uri
                uriClipUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "clip.jpg");
            }
        } else {
            uriClipUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "clip.jpg");
        }
        LogUtils.e("" + uriClipUri);
        //Android 对Intent中所包含数据的大小是有限制的，一般不能超过 1M，否则会使用缩略图 ,所以我们要指定输出裁剪的图片路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriClipUri);
        intent.putExtra("return-data", false);//是否将数据保留在Bitmap中返回
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出格式，一般设为Bitmap格式及图片类型
        intent.putExtra("noFaceDetection", true);//人脸识别功能
        startActivityForResult(intent, PHOTO_REQUEST_CUT);//裁剪完成的标识
    }

    private File saveBitmap(Bitmap bitmap) throws IOException {
        File file = new File(getSimpleActivity().getExternalCacheDir(), "meli-cache");
        if (!file.exists()) file.createNewFile();
        OutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        return file;
    }

//    public void compressPhto(File mFile) {
////        BitmapFactory这个类就提供了多个解析方法（decodeResource、decodeStream、decodeFile等）用于创建Bitmap。
////        比如如果图片来源于网络，就可以使用decodeStream方法；
////        如果是sd卡里面的图片，就可以选择decodeFile方法；
////        如果是资源文件里面的图片，就可以使用decodeResource方法等
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 获取当前图片的边界大小
//        //BitmapFactory.decodeResource(getResources(), R.drawable.bg, options);
//        BitmapFactory.decodeFile(mFile.getAbsolutePath(), options);
//        int outHeight = options.outHeight; //获取图片本身的高像素
//        int outWidth = options.outWidth;//获取图片本身的宽的像素
//        String outMimeType = options.outMimeType;
//        options.inJustDecodeBounds = false;
//        //inSampleSize的作用就是可以把图片的长短缩小inSampleSize倍，所占内存缩小inSampleSize的平方
//        //对于inSampleSize值的大小有要求，最好是整数且2的倍数
//        options.inSampleSize = caculateSampleSize(options, 500, 500);
//        //etPath()得到的是构造file的时候的路径。getAbsolutePath()得到的是全路径
//        String path = mFile.getPath();
//        String absPath = mFile.getAbsolutePath();
//        Bitmap bitmap = BitmapFactory.decodeFile(absPath, options);
////        ivUserPhoto.setImageBitmap(bitmap);
////        //尺寸压缩结果
////        ivSize.setImageBitmap(bitmap);
//    }
//
//    /**
//     * 计算出所需要压缩的大小
//     *
//     * @param options
//     * @param reqWidth  希望的图片宽大小
//     * @param reqHeight 希望的图片高大小
//     * @return
//     */
//    private int caculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        int sampleSize = 1;
//        int picWidth = options.outWidth;
//        int picHeight = options.outHeight;
//        if (picWidth > reqWidth || picHeight > reqHeight) {
//            int halfPicWidth = picWidth / 2;
//            int halfPicHeight = picHeight / 2;
//            while (halfPicWidth / sampleSize > reqWidth || halfPicHeight / sampleSize > reqHeight) {
//                sampleSize *= 2;
//            }
//        }
//        return sampleSize;
//    }

}
