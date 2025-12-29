package com.ruby.domain.dto.request;

import com.ruby.domain.dto.MemberDTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MemberRequestDTO extends MemberDTO{
	public MemberRequestDTO(String mid, String pwd, String alias) {
		super(mid, pwd, alias);
	}
}
