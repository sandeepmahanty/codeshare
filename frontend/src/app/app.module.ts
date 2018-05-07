import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {ModalModule} from 'ngx-bootstrap/modal';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {SessionComponent} from './session/session.component';
import {PractiseComponent} from './practise/practise.component';
import {ErrorComponent} from './error/error.component';
import {RouterModule, Routes} from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import {PractiseService} from './practise/practise.service';

const appRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent
  },
  {
    path: 'playground',
    component: PractiseComponent
  },
  {
    path: 'session/:id',
    component: SessionComponent
  },
  {
    path: '**',
    component: ErrorComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SessionComponent,
    PractiseComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ModalModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [PractiseService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
