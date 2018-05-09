import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ExecuteCodeRequest} from "./practise.component";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


export class ExecuteCodeResponse {
  constructor(public content: string, public language: string, public isError: boolean, public time: String) {
  }
}

@Injectable()
export class PractiseService {
  defaultCodeUrl = "/api/default/";
  executeCodeUrl = "/api/execute";
  compilerOutputs = new BehaviorSubject<ExecuteCodeResponse[]>([]);

  constructor(private httpClient: HttpClient) {
  }

  getDefaultCode(code: String) {
    return this.httpClient.get(this.defaultCodeUrl + 'java');
  }

  executeCode(request: ExecuteCodeRequest) {
    this.httpClient.post(this.executeCodeUrl, request).subscribe((data) => {
      console.log(data);
      const compilerOutput = new ExecuteCodeResponse(data["content"], data["language"], data["error"], data["time"]);
      this.update(compilerOutput);
    });
  }

  update(output: ExecuteCodeResponse) {
    this.compilerOutputs.next([output]);
  }
}
