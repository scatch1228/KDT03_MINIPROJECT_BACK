package com.ruby.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruby.domain.Member;
import com.ruby.domain.dto.request.MemberRequestDTO;
import com.ruby.exception.DuplicateMemberException;
import com.ruby.persistence.MemberRepo;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepo mrepo;
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public String signup(MemberRequestDTO dto) {
		if(mrepo.existsById(dto.getMid())) 
			throw new DuplicateMemberException(dto.getMid()+": ID already exists");
		if(mrepo.existsByAlias(dto.getAlias()))
			throw new DuplicateMemberException(dto.getAlias()+": alias already exists");
		
		Member member = Member.builder()
				.mid(dto.getMid())
				.pwd(encoder.encode(dto.getPwd()))
				.alias(dto.getAlias())
				.build()
				;
		mrepo.save(member);
		
		return "Signup Success";
	}
}
