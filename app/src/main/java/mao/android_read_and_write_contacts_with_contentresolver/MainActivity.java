package mao.android_read_and_write_contacts_with_contentresolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mao.android_read_and_write_contacts_with_contentresolver.entity.Contact;

public class MainActivity extends AppCompatActivity
{

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.EditText1);
        editText2 = findViewById(R.id.EditText2);
        editText3 = findViewById(R.id.EditText3);

        findViewById(R.id.Button1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                insert(R.id.Button1);
            }
        });
    }



    /**
     * 往通讯录里插入数据
     *
     * @param requestCode 请求代码
     */
    public void insert(int requestCode)
    {
        if (checkPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS,
                requestCode % 65536))
        {
            //成功获取到权限

        }
    }




    /**
     * 往手机通讯录添加一个联系人信息（包括姓名、电话号码、电子邮箱）
     *
     * @param resolver 解析器
     * @param contact  Contact对象
     */
    public static void addContacts(ContentResolver resolver, Contact contact)
    {
        // 构建一个指向系统联系人提供器的Uri对象
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        // 创建新的配对
        ContentValues values = new ContentValues();
        // 往 raw_contacts 添加联系人记录，并获取添加后的联系人编号
        long contactId = ContentUris.parseId(resolver.insert(raw_uri, values));
        // 构建一个指向系统联系人数据的Uri对象
        Uri uri = Uri.parse("content://com.android.contacts/data");
        // 创建新的配对
        ContentValues name = new ContentValues();
        // 往配对添加联系人编号
        name.put("raw_contact_id", contactId);
        // 往配对添加“姓名”的数据类型
        name.put("mimetype", "vnd.android.cursor.item/name");
        // 往配对添加联系人的姓名
        name.put("data2", contact.getName());
        // 往提供器添加联系人的姓名记录
        resolver.insert(uri, name);
        // 创建新的配对
        ContentValues phone = new ContentValues();
        // 往配对添加联系人编号
        phone.put("raw_contact_id", contactId);
        // 往配对添加“电话号码”的数据类型
        phone.put("mimetype", "vnd.android.cursor.item/phone_v2");
        // 往配对添加联系人的电话号码
        phone.put("data1", contact.getPhone());
        // 联系类型。1表示家庭，2表示工作
        phone.put("data2", "2");
        // 往提供器添加联系人的号码记录
        resolver.insert(uri, phone);
        // 创建新的配对
        ContentValues email = new ContentValues();
        // 往配对添加联系人编号
        email.put("raw_contact_id", contactId);
        // 往配对添加“电子邮箱”的数据类型
        email.put("mimetype", "vnd.android.cursor.item/email_v2");
        // 往配对添加联系人的电子邮箱
        email.put("data1", contact.getEmail());
        // 联系类型。1表示家庭，2表示工作
        email.put("data2", "2");
        // 往提供器添加联系人的邮箱记录
        resolver.insert(uri, email);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // requestCode不能为负数，也不能大于2的16次方即65536
        if (requestCode == R.id.Button1 % 65536)
        {
            if (checkGrant(grantResults))
            {
                //用户选择了同意授权
                //todo:
            }
            else
            {
                toastShow("没有写联系人的权限");
            }
        }
    }


    /**
     * 检查权限结果数组，
     *
     * @param grantResults 授予相应权限的结果是PackageManager.PERMISSION_GRANTED
     *                     或PackageManager.PERMISSION_DENIED
     *                     从不为空
     * @return boolean 返回true表示都已经获得授权 返回false表示至少有一个未获得授权
     */
    public static boolean checkGrant(int[] grantResults)
    {
        boolean result = true;
        if (grantResults != null)
        {
            for (int grant : grantResults)
            {
                //遍历权限结果数组中的每条选择结果
                if (grant != PackageManager.PERMISSION_GRANTED)
                {
                    //未获得授权，返回false
                    result = false;
                    break;
                }
            }
        }
        else
        {
            result = false;
        }
        return result;
    }


    /**
     * 检查某个权限
     *
     * @param act         Activity对象
     * @param permission  许可
     * @param requestCode 请求代码
     * @return boolean 返回true表示已启用该权限，返回false表示未启用该权限
     */
    public static boolean checkPermission(Activity act, String permission, int requestCode)
    {
        return checkPermission(act, new String[]{permission}, requestCode);
    }


    /**
     * 检查多个权限
     *
     * @param act         Activity对象
     * @param permissions 权限
     * @param requestCode 请求代码
     * @return boolean 返回true表示已完全启用权限，返回false表示未完全启用权限
     */
    @SuppressWarnings("all")
    public static boolean checkPermission(Activity act, String[] permissions, int requestCode)
    {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int check = PackageManager.PERMISSION_GRANTED;
            //通过权限数组检查是否都开启了这些权限
            for (String permission : permissions)
            {
                check = ContextCompat.checkSelfPermission(act, permission);
                if (check != PackageManager.PERMISSION_GRANTED)
                {
                    //有个权限没有开启，就跳出循环
                    break;
                }
            }
            if (check != PackageManager.PERMISSION_GRANTED)
            {
                //未开启该权限，则请求系统弹窗，好让用户选择是否立即开启权限
                ActivityCompat.requestPermissions(act, permissions, requestCode);
                result = false;
            }
        }
        return result;
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    private void toastShow(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}