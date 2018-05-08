import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ExecuteCodeRequest} from "./practise.component";

@Injectable()
export class PractiseService {
  defaultCodeUrl = "/api/default/";
  executeCodeUrl = "/api/execute";

  constructor(private httpClient: HttpClient) {
  }

  getDefaultCode(code: String) {
    return this.httpClient.get(this.defaultCodeUrl + 'java');
  }

  executeCode(request: ExecuteCodeRequest) {
    return this.httpClient.post(this.executeCodeUrl, request);
  }
}
