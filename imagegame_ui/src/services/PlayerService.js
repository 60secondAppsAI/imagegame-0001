import http from "../http-common"; 

class PlayerService {
  getAllPlayers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/player/players`, searchDTO);
  }

  get(playerId) {
    return this.getRequest(`/player/${playerId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/player?field=${matchData}`, null);
  }

  addPlayer(data) {
    return http.post("/player/addPlayer", data);
  }

  update(data) {
  	return http.post("/player/updatePlayer", data);
  }
  
  uploadImage(data,playerId) {
  	return http.postForm("/player/uploadImage/"+playerId, data);
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

export default new PlayerService();
