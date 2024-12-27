import http from "../http-common"; 

class LeaderboardService {
  getAllLeaderboards(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/leaderboard/leaderboards`, searchDTO);
  }

  get(leaderboardId) {
    return this.getRequest(`/leaderboard/${leaderboardId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/leaderboard?field=${matchData}`, null);
  }

  addLeaderboard(data) {
    return http.post("/leaderboard/addLeaderboard", data);
  }

  update(data) {
  	return http.post("/leaderboard/updateLeaderboard", data);
  }
  
  uploadImage(data,leaderboardId) {
  	return http.postForm("/leaderboard/uploadImage/"+leaderboardId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new LeaderboardService();
