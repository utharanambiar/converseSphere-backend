package com.conversesphere.mapper;

import java.util.ArrayList;
import java.util.List;

import com.conversesphere.dto.LikeDTO;
import com.conversesphere.dto.TweetDTO;
import com.conversesphere.dto.UserDTO;
import com.conversesphere.model.Likes;
import com.conversesphere.model.User;

public class LikeDTOMapper {

	// Converts Like entity to LikeDTO
	public static LikeDTO toLikeDTO(Likes like, User reqUser) {

		UserDTO user = UserDTOMapper.toUserDTO(like.getUser());
		UserDTO reqUserDTO = UserDTOMapper.toUserDTO(reqUser);
		TweetDTO tweet = TweetDTOMapper.toTweetDTO(like.getTweet(), reqUser);

		LikeDTO likeDTO = new LikeDTO();
		likeDTO.setId(like.getId());
		likeDTO.setTweet(tweet);
		likeDTO.setUser(user);

		return likeDTO;
	}

	//List of all likes by a user
	public static List<LikeDTO> toLikeDTOs(List<Likes> likes, User reqUser) {
		List<LikeDTO> likeDTOs = new ArrayList<>();
		for (Likes like : likes) {
			UserDTO user = UserDTOMapper.toUserDTO(like.getUser());
			TweetDTO tweet = TweetDTOMapper.toTweetDTO(like.getTweet(), reqUser);
			LikeDTO likeDTO = new LikeDTO();
			likeDTO.setId(like.getId());
			likeDTO.setTweet(tweet);
			likeDTO.setUser(user);
			likeDTOs.add(likeDTO);
		}
		return likeDTOs;
	}

}
