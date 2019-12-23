import {Inject, Injectable} from '@angular/core';
import {Router} from "@angular/router";


import {CookieService} from "ngx-cookie-service";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class OauthService {


  constructor(
    private _router: Router, private _http: HttpClient ,private cookieService: CookieService){}

  obtainAccessToken(loginData){

    const param = new HttpParams()
    .set('username', loginData.username)
    .set('password', loginData.password)
    .set('grant_type', 'password');

    const httpOptions = {
      headers: new HttpHeaders( {
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'basic '+btoa('fooClientIdPassword:secret')} )
    };

    console.log(param.toString());
    this._http.post('http://localhost:8081/auth/oauth/token',
      param,httpOptions)
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials'));
  }

  saveToken(token){
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    this.cookieService.set( "access_token", token.access_token , expireDate);
    this._router.navigate(['/']);
  }
  // var headers =
  //   new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
  //     'Authorization': 'Bearer '+Cookie.get('access_token')});
  // var options = new RequestOptions({ headers: headers });
  //
  // getResource(resourceUrl) : Observable<any>{
  //   var headers =
  //     new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
  //       'Authorization': 'Bearer '+Cookie.get('access_token')});
  //   var options = new RequestOptions({ headers: headers });
  //   return this._http.get(resourceUrl, options)
  //     .map((res:Response) => res.json())
  //     .catch((error:any) =>
  //       Observable.throw(error.json().error || 'Server error'));
  // }

  checkCredentials(){
    if (!localStorage.getItem('access_token')){
      this._router.navigate(['/login']);
    }
  }

  logout() {
    this.cookieService.delete('access_token');
    this._router.navigate(['/login']);
  }


}
