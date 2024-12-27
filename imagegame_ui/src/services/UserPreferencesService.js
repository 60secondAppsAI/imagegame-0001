import http from "../http-common"; 

class UserPreferencesService {
  getAllUserPreferencess(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/userPreferences/userPreferencess`, searchDTO);
  }

  get(userPreferencesId) {
    return this.getRequest(`/userPreferences/${userPreferencesId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/userPreferences?field=${matchData}`, null);
  }

  addUserPreferences(data) {
    return http.post("/userPreferences/addUserPreferences", data);
  }

  update(data) {
  	return http.post("/userPreferences/updateUserPreferences", data);
  }
  
  uploadImage(data,userPreferencesId) {
  	return http.postForm("/userPreferences/uploadImage/"+userPreferencesId, data);
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

export default new UserPreferencesService();
