import http from "../http-common"; 

class ChallengeCompletionService {
  getAllChallengeCompletions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/challengeCompletion/challengeCompletions`, searchDTO);
  }

  get(challengeCompletionId) {
    return this.getRequest(`/challengeCompletion/${challengeCompletionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/challengeCompletion?field=${matchData}`, null);
  }

  addChallengeCompletion(data) {
    return http.post("/challengeCompletion/addChallengeCompletion", data);
  }

  update(data) {
  	return http.post("/challengeCompletion/updateChallengeCompletion", data);
  }
  
  uploadImage(data,challengeCompletionId) {
  	return http.postForm("/challengeCompletion/uploadImage/"+challengeCompletionId, data);
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

export default new ChallengeCompletionService();
