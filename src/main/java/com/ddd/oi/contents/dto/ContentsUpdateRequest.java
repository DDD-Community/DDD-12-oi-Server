package com.ddd.oi.contents.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.enumType.ContentsTag;

import java.util.List;

public record ContentsUpdateRequest(
	String title,
	String displayDescription,
	Integer cost,
	String recommendedSchedule,
	Integer duration,
	ContentsTag contentsTag,
	String shortTitle,
	String shortDescription,
	Double recommendationScore
) {

	public ContentsUpdateRequest {
		if (title == null || title.isBlank())
			throw new OiException(ErrorCode.PARAMETER_INVALID);
		if (contentsTag == null)
			throw new OiException(ErrorCode.PARAMETER_INVALID);
		if (cost != null && cost < 0)
			throw new OiException(ErrorCode.PARAMETER_INVALID);
		if (duration != null && duration < 0)
			throw new OiException(ErrorCode.PARAMETER_INVALID);
	}
}
