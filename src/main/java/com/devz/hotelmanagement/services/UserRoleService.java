package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.UserRole;

import java.util.List;

public interface UserRoleService extends ServiceBase<UserRole> {

    List<UserRole> findByAccountId(Integer accountId);

    void delete(Integer id);
}
