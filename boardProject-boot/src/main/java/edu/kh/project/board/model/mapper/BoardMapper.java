package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {


	/**게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	int getListCount(int boardCode);
	List<Board> selectBoardList(int board, RowBounds rowBounds);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Integer> map);

	/** 좋아요 해제 (DELETE)
	 * @param map
	 * @return
	 */
	int deleteBoardLike(Map<String, Integer> map);

	/** 좋아요 체크 (INSERT)
	 * @param map
	 * @return
	 */
	int insertBoardLike(Map<String, Integer> map);

	/** 게시글 좋아요 개수 조회
	 * @param temp
	 * @return count
	 */
	int selectLikeCount(int temp);

	/**	조회수 조회
	 * @param boardNo
	 * @return
	 */
	int selectReadCount(int boardNo);

	/** 조회수 1 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);
}
