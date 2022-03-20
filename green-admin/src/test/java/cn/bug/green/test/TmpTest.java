package cn.bug.green.test;

import cn.bug.green.admin.GreenApplication;
import cn.bug.green.common.core.domain.entity.SysMenu;
import cn.bug.green.common.utils.AuthenticateUtils;
import cn.bug.green.system.mapper.green.SysMenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author coding-bug
 * date 2022/1/19 14:36
 */
@SpringBootTest(classes = GreenApplication.class)
public class TmpTest {
    @Autowired
    private SysMenuMapper menuMapper;

    @Test
    void test01() {
        long userId = 1L;
        List<SysMenu> menus = null;
        if (AuthenticateUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        // System.out.println("menus.size() = " + menus.size());

        // System.out.println(JSONUtil.toJsonStr(menus));
        SysMenu mainMenu = new SysMenu();
        mainMenu.setMenuId(0L);
        List<SysMenu> perms = getChildPerms(menus, 0);
        // do1(menus, mainMenu);

        perms.forEach(item -> {
            System.out.println("-------------------------------------------");
            System.out.println("item = " + item);
        });

    }


    void do1(List<SysMenu> menus, SysMenu menu) {
        List<SysMenu> childList = getChildList(menus, menu);
        if (!childList.isEmpty()) {
            for (SysMenu m : childList) {
                do1(menus, m);
            }
            menu.setChildren(childList);
        }

    }


    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }

    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<>();
        for (SysMenu m : list) {
            if (m.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(m);
            }
        }
        return tlist;
    }


}
