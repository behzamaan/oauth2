import { Component } from '@angular/core';
import {OauthService} from "./oauth.service";


@Component({
  selector: 'app-root',
  styleUrls: ['./app.component.css'],
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'frontend';
  public loginData = {username: "", password: ""};
  constructor(private _service:OauthService) {}

  login() {
    this._service.obtainAccessToken(this.loginData);
  }
}
