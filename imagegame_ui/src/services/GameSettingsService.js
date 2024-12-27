import http from "../http-common"; 

class GameSettingsService {
  getAllGameSettingss(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/gameSettings/gameSettingss`, searchDTO);
  }

  get(gameSettingsId) {
    return this.getRequest(`/gameSettings/${gameSettingsId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/gameSettings?field=${matchData}`, null);
  }

  addGameSettings(data) {
    return http.post("/gameSettings/addGameSettings", data);
  }

  update(data) {
  	return http.post("/gameSettings/updateGameSettings", data);
  }
  
  uploadImage(data,gameSettingsId) {
  	return http.postForm("/gameSettings/uploadImage/"+gameSettingsId, data);
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

export default new GameSettingsService();
