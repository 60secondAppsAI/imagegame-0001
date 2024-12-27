import http from "../http-common"; 

class AchievementService {
  getAllAchievements(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/achievement/achievements`, searchDTO);
  }

  get(achievementId) {
    return this.getRequest(`/achievement/${achievementId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/achievement?field=${matchData}`, null);
  }

  addAchievement(data) {
    return http.post("/achievement/addAchievement", data);
  }

  update(data) {
  	return http.post("/achievement/updateAchievement", data);
  }
  
  uploadImage(data,achievementId) {
  	return http.postForm("/achievement/uploadImage/"+achievementId, data);
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

export default new AchievementService();
