package web.service;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    public Set<Role> getRoleByName(List<String> listStringRoles);

}
