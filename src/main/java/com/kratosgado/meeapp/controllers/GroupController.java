package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.services.GroupService;
import com.kratosgado.meeapp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/groups")
public class GroupController {

	private final GroupService groupService;
	private final MessageService messageService;

	@Autowired
	public GroupController(GroupService groupService, MessageService messageService) {
		this.groupService = groupService;
		this.messageService = messageService;
	}
}
