import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-session',
  templateUrl: './session.component.html',
  styleUrls: ['./session.component.css']
})
export class SessionComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      console.log(params);
    });
  }

  ngOnInit() {
  }

}
