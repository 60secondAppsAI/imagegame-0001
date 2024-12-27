import http from "../http-common"; 

class KeyWordPairService {
  getAllKeyWordPairs(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/keyWordPair/keyWordPairs`, searchDTO);
  }

  get(keyWordPairId) {
    return this.getRequest(`/keyWordPair/${keyWordPairId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/keyWordPair?field=${matchData}`, null);
  }

  addKeyWordPair(data) {
    return http.post("/keyWordPair/addKeyWordPair", data);
  }

  update(data) {
  	return http.post("/keyWordPair/updateKeyWordPair", data);
  }
  
  uploadImage(data,keyWordPairId) {
  	return http.postForm("/keyWordPair/uploadImage/"+keyWordPairId, data);
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

export default new KeyWordPairService();
