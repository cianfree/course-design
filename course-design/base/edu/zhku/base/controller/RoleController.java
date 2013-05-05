package edu.zhku.base.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.service.PrivilegeService;
import edu.zhku.fr.service.RoleService;

/**
 * 处理角色信息的控制器，包括角色信息的增删改查
 * 
 * @author XJQ
 * @since 2013-1-21
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @Resource
    private PrivilegeService privilegeService;
    
    // /----------------------------------------------------------------------
    /**
     * 跳转到角色列表界面
     * 
     * @return
     */
    @RequestMapping("roleList.html")
    public ModelAndView roleList(Integer currentPage, Integer pageSize) {
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? this.getDefaultPageSize() : pageSize;

        ModelAndView mav = new ModelAndView("base/role/roleList");

        PageBean pb = this.roleService.paging(currentPage, pageSize,
                this.queryHelper.setClass(Role.class).setAlias("r"));
        mav.addObject("pb", pb);
        return mav;
    }

    /**
     * 跳转到角色编辑页面，包括角色的创建和角色信息的修改
     * 
     * @param id
     * @return
     */
    @RequestMapping("editRoleUI.html")
    public ModelAndView editRoleUI(Long id) {
        ModelAndView mav = new ModelAndView("base/role/editRoleUI");
        if (id != null) {
            mav.addObject("role", this.roleService.get(id));
        }
        return mav;
    }

    /**
     * 保存角色信息，创建角色的时候用
     * 
     * @param role
     * @return
     */
    @RequestMapping("saveRole.html")
    public ModelAndView saveRole(Role role) {
        ModelAndView mav = new ModelAndView("redirect:/role/roleList.html");
        if (role != null) {
            this.roleService.save(role);
        }
        return mav;
    }

    /**
     * 更新角色信息，如果之前是修改角色的基本信息就用这个来进行更新角色信息
     * 
     * @param role
     * @return
     */
    @RequestMapping("updateRole.html")
    public ModelAndView updateRole(Role role) {
        ModelAndView mav = new ModelAndView("redirect:/role/roleList.html");
        if (role != null && role.getId() != null) {
            Role newRole = this.roleService.get(role.getId());
            if (!newRole.getName().equals(role.getName())) {
                if (this.roleService.getByName(role.getName()) == null) {
                    newRole.setName(role.getName());
                    newRole.setDescription(role.getDescription());
                    this.roleService.update(newRole);
                }
            } else {
                newRole.setDescription(role.getDescription());
                this.roleService.update(newRole);
            }
        }
        return mav;
    }

    /**
     * 删除指定ID的角色
     * 
     * @param roleId
     *            角色ID
     * @return
     */
    @RequestMapping("deleteRole.html")
    public ModelAndView deleteRole(Role role) {
        ModelAndView mav = new ModelAndView("redirect:/role/roleList.html");
        if (role != null) {
            this.roleService.delete(role);
        }
        return mav;
    }

    /**
     * 跳转到权限设置界面
     * 
     * @return
     */
    @RequestMapping("privTreeUI.html")
    public ModelAndView privTreeUI(Long id) {
        ModelAndView mav = new ModelAndView("base/role/privTreeUI");
        // 这里应准备显示的权限数据
        // 1,
        // 获取role的所有权限，因为privs是role的一个属性，所以只需要获取Role就行了，不过要使用OpenSessionInView模式
        if (id != null) {
            Role role = this.roleService.get(id);
            mav.addObject("role", role);

            // 准备privs id
            if (role.getPrivs() != null && role.getPrivs().size() > 0) {
                Long[] privIds = new Long[role.getPrivs().size()];
                int index = 0;
                for (Privilege priv : role.getPrivs()) {
                    privIds[index++] = priv.getId();
                }
                mav.addObject("privIds", privIds);
            }
            return mav;
        }
        return new ModelAndView("redirect:/role/roleList.html");
    }

    /**
     * 保存某个角色的权限设置
     * 
     * @return
     */
    @RequestMapping("savePriv.html")
    public ModelAndView savePriv(@RequestParam("roleId") Long id,
            @RequestParam("privId") Long[] privIds) {
        // 已正常获取到roleId和所有选中的权限ID

        ModelAndView mav = new ModelAndView("redirect:/role/roleList.html");

        if (id != null) {
            Role role = this.roleService.get(id);
            List<Privilege> privs = this.privilegeService.getByIds(privIds);
            role.getPrivs().removeAll(role.getPrivs());
            role.getPrivs().addAll(privs);
            this.roleService.update(role);
        }
        return mav;
    }
}
