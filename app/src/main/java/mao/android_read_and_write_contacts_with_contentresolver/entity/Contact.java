package mao.android_read_and_write_contacts_with_contentresolver.entity;

/**
 * Project name(项目名称)：android_read_and_write_contacts_with_ContentResolver
 * Package(包名): mao.android_read_and_write_contacts_with_contentresolver.entity
 * Class(类名): Contact
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/10/1
 * Time(创建时间)： 14:11
 * Version(版本): 1.0
 * Description(描述)： 联系人实体类
 */

public class Contact
{
    /**
     * 名字
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 电子邮件
     */
    private String email;

    /**
     * Instantiates a new Contact.
     */
    public Contact()
    {
    }

    /**
     * Instantiates a new Contact.
     *
     * @param name  the name
     * @param phone the phone
     * @param email the email
     */
    public Contact(String name, String phone, String email)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public Contact setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     * @return the phone
     */
    public Contact setPhone(String phone)
    {
        this.phone = phone;
        return this;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     * @return the email
     */
    public Contact setEmail(String email)
    {
        this.email = email;
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public String toString()
    {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("name：").append(name).append('\n');
        stringbuilder.append("phone：").append(phone).append('\n');
        stringbuilder.append("email：").append(email).append('\n');
        return stringbuilder.toString();
    }
}
