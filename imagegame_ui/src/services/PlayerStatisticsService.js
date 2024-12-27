import http from "../http-common"; 

class PlayerStatisticsService {
  getAllPlayerStatisticss(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/playerStatistics/playerStatisticss`, searchDTO);
  }

  get(playerStatisticsId) {
    return this.getRequest(`/playerStatistics/${playerStatisticsId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/playerStatistics?field=${matchData}`, null);
  }

  addPlayerStatistics(data) {
    return http.post("/playerStatistics/addPlayerStatistics", data);
  }

  update(data) {
  	return http.post("/playerStatistics/updatePlayerStatistics", data);
  }
  
  uploadImage(data,playerStatisticsId) {
  	return http.postForm("/playerStatistics/uploadImage/"+playerStatisticsId, data);
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

export default new PlayerStatisticsService();
