import {AfterViewChecked, Component, ElementRef, OnInit, TemplateRef, ViewChild} from '@angular/core';
import * as CodeMirror from 'codemirror';
import 'codemirror/mode/clike/clike';
import {ExecuteCodeResponse, PractiseService} from './practise.service';
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/scan';
import {ActivatedRoute} from "@angular/router";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stomp-websocket';

export class ExecuteCodeRequest {
  content: String;
  language: String;
  input: String;
}

@Component({
  selector: 'app-practise',
  templateUrl: './practise.component.html',
  styleUrls: ['./practise.component.css']
})
export class PractiseComponent implements OnInit {
  customInputBoxState = true;
  editor: any;
  input: any;
  outputs: Observable<ExecuteCodeResponse[]>;
  notebookId: String;
  stompClient: any;
  subscriptionUrl = '/topic/notebooks/';
  @ViewChild('codeEditor') codeEditor: ElementRef;

  constructor(private codeService: PractiseService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.outputs = this.codeService.compilerOutputs.asObservable()
      .scan((acc, val) => acc.concat(val));
    this.activatedRoute.params.subscribe(params => {
      this.notebookId = params["id"];
      this.subscriptionUrl += this.notebookId;
    });

    this.initCodeArea();
  }

  initCodeArea() {
    const editor = CodeMirror.fromTextArea(this.codeEditor.nativeElement, {
      lineNumbers: true,
      matchBrackets: true,
      mode: 'text/x-java',
      resetCursorOnSet: false
    });

    const subUrl = this.subscriptionUrl;

    let socket = new SockJS('/api/socket');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);

      this.subscribe(subUrl, (greeting) => {
        //todo: something awesome
        console.log("Subscribe: " + greeting);
        let cursor = editor.getCursor();
        editor.setValue(greeting.body);
        editor.setCursor(cursor);
      });
    });

    editor.on('keyup', (editor: CodeMirror.Editor) => {
      console.log("SEND: " + editor.getDoc().getValue());
      this.sendMessage();
    });

    this.input = document.getElementById("custom-input");
    this.editor = editor;
    this.getDefaultCode('java');
  }

  toggle() {
    this.customInputBoxState = !this.customInputBoxState;
  }

  getDefaultCode(code: String) {
    this.codeService.getDefaultCode('java').subscribe((data) => {
      console.log(data);
      this.editor.setValue(data["content"]);
    });
  }

  runCode() {
    const code = this.editor.getValue();
    const payload = new ExecuteCodeRequest();
    payload.content = code;
    payload.language = "java";
    if (!this.customInputBoxState) {
      payload.input = this.input.value;
    }
    this.codeService.executeCode(payload);
  }

  sendMessage() {
    //TODO: add checks for stomp client being connected
    this.stompClient.send("/app/notebooks/" + this.notebookId, {}, JSON.stringify({'content': this.editor.getValue()}));
  }
}
