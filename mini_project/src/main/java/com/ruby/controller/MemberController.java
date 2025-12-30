package com.ruby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruby.domain.Member;
import com.ruby.domain.dto.request.MemberRequestDTO;
import com.ruby.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberService mserv;
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody MemberRequestDTO dto) {
			String msg = mserv.signup(dto);	
		return ResponseEntity.ok(msg);
	}
}
