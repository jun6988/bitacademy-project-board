package com.bitacademy.projectboard.Service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

// Page 기능을 만들고 controller에서 샤용 
@Service
public class PaginationService {
	
	// page bar에 나오는 숫자 값 
	private static final int BAR_LENGTH = 5;

	// 숫자 리스트를 넣어서 bar view 에 넣는다 
	public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
		// 현재 페이지 중간으로 오게하는 것 
		int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
		int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);
		
		return IntStream.range(startNumber, endNumber).boxed().toList(); 
	}
	
	public int currentBarLength() {
		return BAR_LENGTH;
	}
}
