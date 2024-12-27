import http from "../http-common"; 

class GameHistoryService {
  getAllGameHistorys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/gameHistory/gameHistorys`, searchDTO);
  }

  get(gameHistoryId) {
    return this.getRequest(`/gameHistory/${gameHistoryId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/gameHistory?field=${matchData}`, null);
  }

  addGameHistory(data) {
    return http.post("/gameHistory/addGameHistory", data);
  }

  update(data) {
  	return http.post("/gameHistory/updateGameHistory", data);
  }
  
  uploadImage(data,gameHistoryId) {
  	return http.postForm("/gameHistory/uploadImage/"+gameHistoryId, data);
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

export default new GameHistoryService();
