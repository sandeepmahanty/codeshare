import {Component, ElementRef, OnInit, TemplateRef} from '@angular/core';
import * as $ from 'jquery';
import * as CodeMirror from 'codemirror';
import 'codemirror/mode/clike/clike';
import {PractiseService} from './practise.service';

export class ExecuteCodeRequest {
  content: String;
  language: String;
}

@Component({
  selector: 'app-practise',
  templateUrl: './practise.component.html',
  styleUrls: ['./practise.component.css']
})
export class PractiseComponent implements OnInit {

  customInputBoxState = true;
  editor: any;
  editorTextArea: Element = document.getElementById("code-editor");

  constructor(private codeService: PractiseService) {
  }

  ngOnInit() {
    this.initCodeArea();
  }

  initCodeArea() {
    const editorRef = CodeMirror.fromTextArea(document.getElementById("code-editor"), {
      lineNumbers: true,
      matchBrackets: true,
      mode: 'text/x-java'
    });
    this.editor = editorRef;
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
    const payload = new ExecuteCodeRequest()
    payload.content = code;
    payload.language = "java";
    this.codeService.executeCode(payload).subscribe((data) => {
      console.log(data);
      document.getElementById("output-console").innerText = "Result: " + data["content"] + "\n" + document.getElementById("output-console").innerText;
    });
  }
}
