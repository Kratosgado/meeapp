package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.services.GroupService;
import com.kratosgado.meeapp.services.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/groups")
@Tag(name = "Groups")
public class GroupController {

	private final GroupService groupService;
	private final MessageService messageService;

	@Autowired
	public GroupController(GroupService groupService, MessageService messageService) {
		this.groupService = groupService;
		this.messageService = messageService;
	}
}
