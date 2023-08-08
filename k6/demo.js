// ./demo.js

import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    stages: [
        { duration: '30s', target: 5000 },  // 30초 동안 5000명의 동시 사용자
    ],
};

const BASE_URL = "http://localhost:8080/like?broadcastId=";

export default function () {
    http.get(BASE_URL);
    for (let userId = 1; userId <= 5000; userId++) {
        http.get(`${BASE_URL}/${userId}`);
        // 요청 간 간격 설정 (예: 100ms)
        sleep(0.1);
    }
}