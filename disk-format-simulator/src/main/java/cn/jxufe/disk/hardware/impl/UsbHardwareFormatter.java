package cn.jxufe.disk.hardware.impl;

import cn.jxufe.disk.hardware.HardwareFormatter;

public class UsbHardwareFormatter implements HardwareFormatter {

    @Override
    public String format(String partition) {
        return "U盘分区" + partition + "格式化完毕";
    }
}