package cn.jxufe.disk.hardware.impl;

import cn.jxufe.disk.hardware.HardwareFormatter;

public class HddHardwareFormatter implements HardwareFormatter {

    @Override
    public String format(String partition) {
        return "机械硬盘分区" + partition + "格式化完毕";
    }
}