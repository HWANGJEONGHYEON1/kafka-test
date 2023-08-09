import http from 'k6/http';
import { sleep, group } from 'k6';

export let options = {
    stages: [
        { duration: '5s', target: 10000 }, // 0~5초 동안 10,000명의 사용자로 요청 시뮬레이션
        { duration: '5s', target: 10000 }, // 5~10초 동안 10,000명의 사용자로 요청 시뮬레이션
        { duration: '5s', target: 10000 }, // 10~15초 동안 10,000명의 사용자로 요청 시뮬레이션
        { duration: '5s', target: 10000 }, // 15~20초 동안 10,000명의 사용자로 요청 시뮬레이션
        { duration: '5s', target: 10000 }, // 20~25초 동안 10,000명의 사용자로 요청 시뮬레이션
    ],
    ext: {
        loadimpact: {
            distribution: {
                '10000': { target: 1 } // 각 구간마다 10,000명의 사용자가 1명씩 동시에 요청
            }
        }
    }
};

const BASE_URL = "http://localhost:8080/like?userId=";

export default function () {
    group('User Flow', function () {
        http.get(`${BASE_URL}` + uuidv4());
    });
}

function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}