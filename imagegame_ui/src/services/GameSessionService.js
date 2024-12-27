import http from "../http-common"; 

class GameSessionService {
  getAllGameSessions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/gameSession/gameSessions`, searchDTO);
  }

  get(gameSessionId) {
    return this.getRequest(`/gameSession/${gameSessionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/gameSession?field=${matchData}`, null);
  }

  addGameSession(data) {
    return http.post("/gameSession/addGameSession", data);
  }

  update(data) {
  	return http.post("/gameSession/updateGameSession", data);
  }
  
  uploadImage(data,gameSessionId) {
  	return http.postForm("/gameSession/uploadImage/"+gameSessionId, data);
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

export default new GameSessionService();
