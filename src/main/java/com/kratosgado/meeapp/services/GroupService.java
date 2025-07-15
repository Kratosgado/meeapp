package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.repositories.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
	private final GroupRepo groupRepo;

	@Autowired
	public GroupService(GroupRepo groupRepo) {
		this.groupRepo = groupRepo;
	}
}
