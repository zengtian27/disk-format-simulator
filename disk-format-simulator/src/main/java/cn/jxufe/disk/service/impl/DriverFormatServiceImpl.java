package cn.jxufe.disk.service.impl;

import cn.jxufe.disk.exception.DriverFormatException;
import cn.jxufe.disk.hardware.HardwareFormatter;
import cn.jxufe.disk.model.FormatView;
import cn.jxufe.disk.service.DriverFormatService;
import cn.jxufe.disk.util.PropertyMappingParser;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

@Service
public class DriverFormatServiceImpl implements DriverFormatService {

    private final Environment environment;

    public DriverFormatServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public FormatView format(String letter) {
        String normalizedLetter = Optional.ofNullable(letter)
                .map(String::trim)
                .map(String::toUpperCase)
                .map(value -> value.replace(":", ""))
                .filter(value -> !value.isBlank())
                .orElseThrow(() -> new DriverFormatException("未接收到有效的逻辑驱动器字母"));

        Map<String, String> letter2Partition = PropertyMappingParser.parse(
                environment.getProperty("driver.letter2Partition"));

        String partition = Optional.ofNullable(letter2Partition.get(normalizedLetter))
                .orElseThrow(() -> new DriverFormatException(
                        "驱动器 " + normalizedLetter + ": 未在 driver.letter2Partition 中注册"));

        Map<String, String> partition2Hardware = PropertyMappingParser.parse(
                environment.getProperty("driver.partition2Hardware"));

        String formatterClassName = Optional.ofNullable(partition2Hardware.get(partition))
                .orElseThrow(() -> new DriverFormatException(
                        "分区 " + partition + " 未在 driver.partition2Hardware 中注册"));

        HardwareFormatter formatter = instantiateFormatter(formatterClassName);
        String message = formatter.format(partition);

        return FormatView.success(normalizedLetter + ":", partition, formatterClassName, message);
    }

    private HardwareFormatter instantiateFormatter(String formatterClassName) {
        Class<?> clazz = loadClass(formatterClassName);
        Object instance = createInstance(clazz);
        return castToFormatter(instance, formatterClassName);
    }

    private Class<?> loadClass(String formatterClassName) {
        try {
            return Class.forName(formatterClassName);
        } catch (ClassNotFoundException e) {
            throw new DriverFormatException("找不到实现类：" + formatterClassName, e);
        }
    }

    private Object createInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            throw new DriverFormatException("无法实例化实现类：" + clazz.getName(), e);
        }
    }

    private HardwareFormatter castToFormatter(Object instance, String formatterClassName) {
        try {
            return HardwareFormatter.class.cast(instance);
        } catch (ClassCastException e) {
            throw new DriverFormatException("类未实现 HardwareFormatter 接口：" + formatterClassName, e);
        }
    }
}