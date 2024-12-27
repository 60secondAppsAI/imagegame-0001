import http from "../http-common"; 

class UserBadgeService {
  getAllUserBadges(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/userBadge/userBadges`, searchDTO);
  }

  get(userBadgeId) {
    return this.getRequest(`/userBadge/${userBadgeId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/userBadge?field=${matchData}`, null);
  }

  addUserBadge(data) {
    return http.post("/userBadge/addUserBadge", data);
  }

  update(data) {
  	return http.post("/userBadge/updateUserBadge", data);
  }
  
  uploadImage(data,userBadgeId) {
  	return http.postForm("/userBadge/uploadImage/"+userBadgeId, data);
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

export default new UserBadgeService();
