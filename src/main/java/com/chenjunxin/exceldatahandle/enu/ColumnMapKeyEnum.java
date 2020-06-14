package com.chenjunxin.exceldatahandle.enu;

/**
 * Created by chenjunxin on 2018/10/18
 */
public enum ColumnMapKeyEnum {

    // 因为已经定义了带参数的构造器，所以在列出枚举值时必须传入对应的参数
    RULE_COLUMN_INDEX("辅助核算种类"),
    DEPART_COLUMN_INDEX("部门"),
    VENDOR_CLOUMN_INDEX("供应商档案"),
    PROJECT_CLOUMN_INDEX("项目档案"),
    INDUSTRY_CLOUMN_INDEX("行业"),
    CUSTOMER_PROFILE_CLOUMN_INDEX("客户档案"),
    BANK_ACCOUNT_CLOUMN_INDEX("银行账户"),
    FIRST_RESULE_CLOUMN_INDEX("辅助核算1"),
    SECOND_RESULE_CLOUMN_INDEX("辅助核算2"),
    THIRD_RESULE_CLOUMN_INDEX("辅助核算3"),
    FOURTH_RESULE_CLOUMN_INDEX("辅助核算4");


    // 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
     ColumnMapKeyEnum(String key) {
        this.key = key;
    }

    // 定义一个 private 修饰的实例变量
    private String key;

    public String getKey() {
        return key;
    }

}
