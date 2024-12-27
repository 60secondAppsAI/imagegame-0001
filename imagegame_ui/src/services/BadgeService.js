import http from "../http-common"; 

class BadgeService {
  getAllBadges(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/badge/badges`, searchDTO);
  }

  get(badgeId) {
    return this.getRequest(`/badge/${badgeId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/badge?field=${matchData}`, null);
  }

  addBadge(data) {
    return http.post("/badge/addBadge", data);
  }

  update(data) {
  	return http.post("/badge/updateBadge", data);
  }
  
  uploadImage(data,badgeId) {
  	return http.postForm("/badge/uploadImage/"+badgeId, data);
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

export default new BadgeService();
