package com.ruby.domain.dto.request;

import com.ruby.domain.dto.ReviewDTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReviewRequestDTO extends ReviewDTO{
	
	public ReviewRequestDTO(Integer seq, String cont, Integer fid, String mid, Integer star) {
		super(seq, cont, fid, mid, star);
	}
}
