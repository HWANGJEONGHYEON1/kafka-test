// ./demo.js

import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    vus: 1000,
    duration: '1m'
}

const BASE_URL = "http://localhost:8080/like?broadcastId=12345";

export default function () {
    http.post(BASE_URL);
    sleep(1);
}