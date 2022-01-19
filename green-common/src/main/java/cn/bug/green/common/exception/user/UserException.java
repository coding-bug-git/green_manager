package cn.bug.green.common.exception.user;

import cn.bug.green.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author coding-bug
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
