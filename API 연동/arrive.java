    private class ApiTask extends AsyncTask<String, Void, List<BusInfo>> {

        @Override
        protected List<BusInfo> doInBackground(String... params) {
            if (params.length < 2) {
                // 인자가 충분하지 않을 경우 처리
                return null;
            }

            String busNumber = params[0];
            String stationNumber = params[1];

            try {
                // 네트워크 요청 및 데이터 처리
                StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll");
                urlBuilder.append("?" + "serviceKey=0iVke%2BadcfD4H%2FCl0SQLOKS25wBBsVvYsg2KpHHTYRdwMva3OteufxCiQ8U9SS5A3bep91zUxeOLNkMY36q0Pg%3D%3D");
                urlBuilder.append("&" + "busRouteId=" + busNumber);
                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                Log.d("ApiTask", "Response code: " + conn.getResponseCode()); --------------------------------------------> 서울시 공공데이터 포털에서 받은 대중교통 공공 API 인증키를 토대로 네트워크 요청 및 데이터 처리

                // JSON 데이터를 읽어와서 문자열로 저장
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } finally {
                    conn.disconnect();
                }

                // 데이터를 파싱하고 필요한 정보 추출
                List<BusInfo> busInfoList = new ArrayList<>();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(sb.toString());
                JsonNode arrivals = jsonNode.path("ServiceResult").path("msgBody").path("itemList");
                Iterator<JsonNode> iterator = arrivals.elements();
                while (iterator.hasNext()) {
                    JsonNode arrival = iterator.next();
                    String busId = arrival.path("vehId1").asText();
                    String location = arrival.path("stNm").asText();
                    Log.d("ApiTask", "Bus ID: " + busId + ", Location: " + location);
                    busInfoList.add(new BusInfo(busId, location));
                } --------------------------------------------> 데이터 파싱 및 필요한 정보(버스 도착 정보)추출

                return busInfoList;

            } catch (IOException e) {
                Log.e("ApiTask", "Error fetching bus arrival info", e);
                return null;
            }
        }
