package cn.jxufe.disk.hardware.impl;

import cn.jxufe.disk.hardware.HardwareFormatter;

public class CdrHardwareFormatter implements HardwareFormatter {

    @Override
    public String format(String partition) {
        return "CDR光驱" + partition + "格式化完毕";
    }
}