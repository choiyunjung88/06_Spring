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
}