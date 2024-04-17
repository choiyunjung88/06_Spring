package edu.kh.project.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

@Mapper
public interface MyPageMapper {

	/** 회원 정보 수정
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

	/** 회원의 비밀번호 조회
	 * @param memberNo
	 * @return 암호화된 비밀번호
	 */
	String selectPw(int memberNo);

	/** 비밀번호 변경
	 * @param paramMap
	 * @return result
	 */
	int changePw(Map<String, Object> paramMap);

	/** 회원 탈퇴
	 * @param memberNo
	 * @return result
	 */
	int secession(int memberNo);

	int insertUploadFile(UploadFile uf);

	List<UploadFile> fileList();

	int profile(Member mem);
}
