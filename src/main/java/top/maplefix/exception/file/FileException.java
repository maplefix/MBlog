package top.maplefix.exception.file;


import top.maplefix.exception.BaseException;

/**
 * @author : Maple
 * @description : 文件信息异常
 * @Date : Created in 2019/4/01 11:01
 * @editor:
 * @version: v2.1
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}