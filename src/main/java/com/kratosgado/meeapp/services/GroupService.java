package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.repositories.GroupRepo;
import com.kratosgado.meeapp.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
	private final GroupRepo groupRepo;
	private final UserService userService;

	@Autowired
	public GroupService(GroupRepo groupRepo, UserService userService) {
		this.groupRepo = groupRepo;
		this.userService = userService;
	}

	public Group createGroup(String name) {
		Group group = new Group(name, SecurityUtils.getCurrentUser());
		return groupRepo.save(group);
	}

	public Group getGroupById(String id) {
		return groupRepo.findById(id).orElse(null);
	}
}
