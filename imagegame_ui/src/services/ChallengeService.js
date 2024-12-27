import http from "../http-common"; 

class ChallengeService {
  getAllChallenges(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/challenge/challenges`, searchDTO);
  }

  get(challengeId) {
    return this.getRequest(`/challenge/${challengeId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/challenge?field=${matchData}`, null);
  }

  addChallenge(data) {
    return http.post("/challenge/addChallenge", data);
  }

  update(data) {
  	return http.post("/challenge/updateChallenge", data);
  }
  
  uploadImage(data,challengeId) {
  	return http.postForm("/challenge/uploadImage/"+challengeId, data);
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

export default new ChallengeService();
