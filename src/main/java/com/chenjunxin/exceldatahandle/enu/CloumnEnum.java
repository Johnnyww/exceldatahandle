package com.chenjunxin.exceldatahandle.enu;



/**
 * Created by chenjunxin on 2018/10/17.
 */
public enum CloumnEnum  {

    RULE_COLUMN_NAME(1,"辅助核算种类"),
    DEPART_COLUMN_NAME(2, "部门"),
    VENDOR_CLOUMN_NAME(3, "供应商档案"),
    PROJECT_CLOUMN_NAME(4, "项目档案"),
    INDUSTRY_CLOUMN_NAME(5, "行业"),
    CUSTOMER_PROFILE_CLOUMN_NAME(6,"客户档案"),
    FIRST_RESULE_CLOUMN_NAME(7, "辅助核算1"),
    SECOND_RESULE_CLOUMN_NAME(8, "辅助核算2"),
    THIRD_RESULE_CLOUMN_NAME(9, "辅助核算3"),
    FOURTH_RESULE_CLOUMN_NAME(10, "辅助核算4"),
    ;
    CloumnEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
