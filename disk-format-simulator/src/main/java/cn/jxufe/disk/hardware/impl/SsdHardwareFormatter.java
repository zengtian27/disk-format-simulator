package cn.jxufe.disk.hardware.impl;

import cn.jxufe.disk.hardware.HardwareFormatter;

public class SsdHardwareFormatter implements HardwareFormatter {

    @Override
    public String format(String partition) {
        return "SSD硬盘分区" + partition + "格式化完毕";
    }
}